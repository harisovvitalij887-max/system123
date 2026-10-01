package dev.clienthud.widgets;

import dev.clienthud.HudConfig;
import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class WaypointsWidget extends HudWidget {
    public WaypointsWidget() { super("waypoints", "Waypoints", 560, 120); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        List<String> out = new ArrayList<>();
        for (HudConfig.Wp w : HudConfig.INSTANCE.waypoints) {
            double d = Math.sqrt(mc.player.squaredDistanceTo(w.x + 0.5, w.y, w.z + 0.5));
            out.add(w.name + "  " + (d >= 1000 ? String.format("%.1f km", d / 1000) : Math.round(d) + " m"));
        }
        return out;
    }

    @Override
    public List<String> sample() { return List.of("home  212 m", "mine  540 m"); }
}
