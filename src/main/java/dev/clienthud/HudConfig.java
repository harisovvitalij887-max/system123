package dev.clienthud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Saved to config/clienthud.json */
public class HudConfig {
    public static class State { public int x, y; public boolean visible = true; }

    public static class Wp { public String name; public int x, y, z; }

    public List<Wp> waypoints = new ArrayList<>();
    public List<String> party = new ArrayList<>();
    public float scale = 1.0f;
    public float opacity = 0.78f;
    public int accent = 0x4ADE80;
    public Map<String, State> widgets = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("clienthud.json");
    public static HudConfig INSTANCE = new HudConfig();

    public static void load() {
        try (Reader r = Files.newBufferedReader(FILE)) {
            HudConfig c = GSON.fromJson(r, HudConfig.class);
            if (c != null) INSTANCE = c;
        } catch (Exception ignored) { /* first launch: defaults */ }
    }

    public static void save() {
        try (Writer w = Files.newBufferedWriter(FILE)) {
            GSON.toJson(INSTANCE, w);
        } catch (Exception e) { ClientHudMod.LOG.warn("Cannot save config", e); }
    }
}
