package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;

public class CoordsWidget extends HudWidget {
    public CoordsWidget() { super("coords", "Coords", 6, 230); }

    @Override
    public String header(MinecraftClient mc) {
        return String.format("X %d  Y %d  Z %d", (int) Math.floor(mc.player.getX()),
                (int) Math.floor(mc.player.getY()), (int) Math.floor(mc.player.getZ()));
    }
}
