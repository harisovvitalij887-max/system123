package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.math.Vec3d;

/** FPS, ping and blocks-per-second. (TPS is not known to a client without server help, so it is omitted.) */
public class SystemWidget extends HudWidget {
    private Vec3d last;
    private double bps;

    public SystemWidget() { super("system", "System", 230, 6); }

    @Override
    public void tick(MinecraftClient mc) {
        Vec3d p = mc.player.getPos();
        if (last != null) {
            double d = Math.hypot(p.x - last.x, p.z - last.z) * 20.0;
            bps = bps * 0.8 + d * 0.2;
        }
        last = p;
    }

    @Override
    public String header(MinecraftClient mc) {
        int ms = 0;
        if (mc.getNetworkHandler() != null && mc.player != null) {
            PlayerListEntry e = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
            if (e != null) ms = e.getLatency();
        }
        return String.format("System   %d Fps   %d MS   %.1f BPS", mc.getCurrentFps(), ms, bps);
    }
}
