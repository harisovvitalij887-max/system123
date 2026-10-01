package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class PotionsWidget extends HudWidget {
    public PotionsWidget() { super("potions", "Potions", 60, 34); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        List<String> out = new ArrayList<>();
        for (StatusEffectInstance e : mc.player.getStatusEffects()) {
            int sec = e.isInfinite() ? -1 : e.getDuration() / 20;
            String time = sec < 0 ? "inf" : String.format("%d:%02d", sec / 60, sec % 60);
            out.add(e.getEffectType().value().getName().getString() + " " + (e.getAmplifier() + 1) + "  " + time);
        }
        return out;
    }

    @Override
    public List<String> sample() { return List.of("Speed 2  0:48", "Strength 1  1:12"); }
}
