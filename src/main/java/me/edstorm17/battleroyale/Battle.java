package me.edstorm17.battleroyale;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Battle {

    private static Battle active;

    public static final String worldName = "game_world";
    public static World gameWorld;
    private static final Random random = new Random();

    private long start;
    private final BattleType type;
    private final long wall;

    private final Scoreboard scoreboard;
    private final Objective objective;

    private Location spawnLocation;
    private Location blueSpawnLocation;
    private Location redSpawnLocation;

    public Map<Location, BlockData> blocks;

    public Battle(BattleType type, long wall) {
        this.type = type;
        this.wall = wall;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("battle", Criteria.DUMMY, "Battle");
        objective.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BATTLE");
    }

    private BukkitTask tick;

    public void start(Collection<? extends Player> players) {
        active = this;
        createWorld();

        for (Player player : players) {
            switch (type) {
                case TEAMS -> {
                    if (random.nextInt(2) == 0) {
                        ScoreManager.blue.addEntry(player.getName());
                        player.teleport(blueSpawnLocation);
                    } else {
                        ScoreManager.red.addEntry(player.getName());
                        player.teleport(redSpawnLocation);
                    }
                }
                case SOLO -> player.teleport(spawnLocation);
            }
            player.setScoreboard(scoreboard);
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.start = System.currentTimeMillis();
        tick = Bukkit.getScheduler().runTaskTimer(BattleRoyale.getInstance(), this::tick, 0, 1);
    }

    public void finish() {
        tick.cancel();
        active = null;
    }

    public void exit() {
        World wait = Bukkit.getWorld("world");
        assert wait != null;
        Location loc = wait.getSpawnLocation();

        System.out.println("Deleting old world...");

        if (gameWorld != null) {
            for (Player player : gameWorld.getPlayers()) {
                player.teleport(loc);
            }
            Bukkit.unloadWorld(gameWorld, true);
        }

        File folder = new File(Bukkit.getWorldContainer() + "/" + worldName);
        deleteDirectory(folder);

        System.out.println("Deleted old world.");

        for (String s : ScoreManager.red.getEntries()) {
            ScoreManager.red.removeEntry(s);
        }
        for (String s : ScoreManager.blue.getEntries()) {
            ScoreManager.blue.removeEntry(s);
        }
    }

    private String latest;
    private boolean wallDropped = false;

    private void tick() {
        long time;
        if (!wallDropped && System.currentTimeMillis() - start >= wall) {
            onWallDrop();
            wallDropped = true;
        }
        if (!wallDropped) {
            time = (start + wall) - System.currentTimeMillis();
        } else {
            time = System.currentTimeMillis() - (start + wall);
        }

        Duration d = Duration.ofMillis(time);
        int minutes = d.toMinutesPart();
        int seconds = d.toSecondsPart();
        if (latest != null) {
            scoreboard.resetScores(latest);
        }
        latest = minutes + ":" + seconds;
        objective.getScore(latest).setScore(10);
    }

    private void onWallDrop() {
        BlockUtils.fill(blocks);
        gameWorld.getWorldBorder().setSize(9, TimeUnit.MINUTES, 5);

    }

    private void createWorld() {
        World wait = Bukkit.getWorld("world");
        assert wait != null;
        Location loc = wait.getSpawnLocation();

        if (Arrays.asList(Bukkit.getWorldContainer().list()).contains(worldName)) {
            System.out.println("Deleting old world...");

            if (gameWorld != null) {
                for (Player player : gameWorld.getPlayers()) {
                    player.teleport(loc);
                }
                Bukkit.unloadWorld(gameWorld, true);
            }

            File folder = new File(Bukkit.getWorldContainer() + "/" + worldName);
            deleteDirectory(folder);

            System.out.println("Deleted old world.");
        }

        System.out.println("Creating new world...");

        WorldCreator wc = new WorldCreator(worldName);
        wc.seed(random.nextLong());
        gameWorld = Bukkit.createWorld(wc);

        assert gameWorld != null;
        gameWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        gameWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        gameWorld.getWorldBorder().setCenter(0, 0);
        gameWorld.getWorldBorder().setSize(201);

        Block def = gameWorld.getHighestBlockAt(0, 0);
        Block blue = gameWorld.getHighestBlockAt(0, -50);
        Block red = gameWorld.getHighestBlockAt(0, 50);

        switch (type) {
            case TEAMS -> {
                blue.setType(Material.BEDROCK);
                red.setType(Material.BEDROCK);
                blocks = BlockUtils.replace(gameWorld, -100, -64, -1, 100, 320, 1, Material.BEDROCK);
            }
            case SOLO -> def.setType(Material.BEDROCK);
        }

        spawnLocation = def.getLocation().add(0.5, 1, 0.5);
        blueSpawnLocation = blue.getLocation().add(0.5, 1, 0.5);
        redSpawnLocation = red.getLocation().add(0.5, 1, 0.5);
    }

    @SuppressWarnings("all")
    public static void deleteDirectory(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDirectory(f);
            }
        }
        file.delete();
    }

    public long getStart() {
        return start;
    }

    public BattleType getType() {
        return type;
    }

    public long getWall() {
        return wall;
    }

    public static Battle getActive() {
        return active;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Location getBlueSpawnLocation() {
        return blueSpawnLocation;
    }

    public Location getRedSpawnLocation() {
        return redSpawnLocation;
    }
}
