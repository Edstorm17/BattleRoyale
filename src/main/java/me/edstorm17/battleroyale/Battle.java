package me.edstorm17.battleroyale;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
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
    private final WorldType worldType;
    private final long wall;

    private BattleStage currentStage;

    private final Scoreboard scoreboard;
    private final Objective objective;

    private Location spawnLocation;
    private Location blueSpawnLocation;
    private Location redSpawnLocation;

    public Map<Location, BlockData> blocks;

    public Battle(BattleType type, WorldType worldType, long wall) {
        this.type = type;
        this.worldType = worldType;
        this.wall = wall;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("battle", Criteria.DUMMY, "Battle");
        objective.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BATTLE");
    }

    private BukkitTask tick;

    public void start() {
        this.start(Bukkit.getOnlinePlayers());
    }

    public void start(Collection<? extends Player> players) {
        if (Battle.getActive() != null) {
            Battle.getActive().finish();
            Battle.getActive().exit();
        }

        active = this;
        createWorld();

        for (Player player : players) {
            switch (type) {
                case TEAMS -> {
                    this.currentStage = BattleStage.WALL;
                    if (random.nextInt(2) == 0) {
                        ScoreManager.blue.addEntry(player.getName());
                        player.teleport(blueSpawnLocation);
                    } else {
                        ScoreManager.red.addEntry(player.getName());
                        player.teleport(redSpawnLocation);
                    }
                }
                case SOLO -> {
                    this.currentStage = BattleStage.WAR;
                    player.teleport(spawnLocation);
                }
            }
            player.setScoreboard(scoreboard);
            clearModifiers(player, Attribute.GENERIC_MAX_HEALTH);
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.start = System.currentTimeMillis();
        tick = Bukkit.getScheduler().runTaskTimer(BattleRoyale.getInstance(), this::tick, 0, 1);
    }

    public void finish() {
        if (this.tick != null)
            tick.cancel();
        this.currentStage = BattleStage.DONE;
    }

    public void exit() {
        active = null;

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
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
    }

    private String latest;
    private boolean wallDropped = false;

    private void tick() {
        long time;
        switch (this.type) {
            case TEAMS -> {
                if (!wallDropped && System.currentTimeMillis() - start >= wall) {
                    onWallDrop();
                    wallDropped = true;
                }
                if (!wallDropped) {
                    time = (start + wall) - System.currentTimeMillis();
                } else {
                    time = System.currentTimeMillis() - (start + wall);
                }
            }
            case SOLO -> {
                if (!wallDropped) {
                    shrink();
                    wallDropped = true;
                }
                time = System.currentTimeMillis() - start;
            }
            default -> time = 0L;
        }


        Duration d = Duration.ofMillis(time);
        int minutes = d.toMinutesPart();
        int seconds = d.toSecondsPart();
        if (latest != null) {
            scoreboard.resetScores(latest);
        }
        latest = (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
        objective.getScore(latest).setScore(0);
    }

    private void onWallDrop() {
        BlockUtils.fill(blocks);
        shrink();
        this.currentStage = BattleStage.WAR;
    }

    private void shrink() {
        gameWorld.getWorldBorder().setSize(21, TimeUnit.MINUTES, 5);
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
        wc.type(this.worldType);
        wc.seed(random.nextLong());
        gameWorld = Bukkit.createWorld(wc);

        assert gameWorld != null;
        gameWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        gameWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        gameWorld.getWorldBorder().setCenter(0.5, 0.5);
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

        getSpawnLocation();
        getBlueSpawnLocation();
        getRedSpawnLocation();
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

    public static void clearModifiers(Player player, Attribute attribute) {
        for (AttributeModifier modifier : player.getAttribute(attribute).getModifiers()) {
            player.getAttribute(attribute).removeModifier(modifier);
        }
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
//        if (!spawnLocation.add(0, 1, 0).getBlock().isPassable() || !spawnLocation.add(0, 2, 0).getBlock().isPassable())
        spawnLocation = gameWorld.getHighestBlockAt(0, 0).getLocation().add(0.5, 1, 0.5);
        return spawnLocation;
    }

    public Location getBlueSpawnLocation() {
//        if (!blueSpawnLocation.add(0, 1, 0).getBlock().isPassable() || !blueSpawnLocation.add(0, 2, 0).getBlock().isPassable())
        blueSpawnLocation = gameWorld.getHighestBlockAt(0, -50).getLocation().add(0.5, 1, 0.5);
        return blueSpawnLocation;
    }

    public Location getRedSpawnLocation() {
//        if (!redSpawnLocation.add(0, 1, 0).getBlock().isPassable() || !redSpawnLocation.add(0, 2, 0).getBlock().isPassable())
        redSpawnLocation = gameWorld.getHighestBlockAt(0, 50).getLocation().add(0.5, 1, 0.5);
        redSpawnLocation.setYaw(180f);
        return redSpawnLocation;
    }

    public BattleStage getCurrentStage() {
        return currentStage;
    }
}
