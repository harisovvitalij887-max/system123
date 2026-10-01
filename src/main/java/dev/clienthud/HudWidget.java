package dev.clienthud;

import net.minecraft.client.MinecraftClient;

import java.util.List;

public abstract class HudWidget {
    public final String id, title;
    public final int defX, defY;
    /** Bounds from the last render, in unscaled HUD coordinates (used by the editor for hit tests). */
    public int w, h;

    protected HudWidget(String id, String title, int defX, int defY) {
        this.id = id; this.title = title; this.defX = defX; this.defY = defY;
    }

    public HudConfig.State state() {
        return HudConfig.INSTANCE.widgets.computeIfAbsent(id, k -> {
            HudConfig.State s = new HudConfig.State(); s.x = defX; s.y = defY; return s;
        });
    }

    /** Text on the pill itself. */
    public String header(MinecraftClient mc) { return title; }

    /** Rows under the pill. Empty list = header-only pill. */
    public List<String> lines(MinecraftClient mc) { return List.of(); }

    /** Sample rows shown in the editor when there is nothing real to show. */
    public List<String> sample() { return List.of(); }

    public void tick(MinecraftClient mc) {}
}
