package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class TargetWidget extends HudWidget {
    public TargetWidget() { super("target", "Target", 420, 120); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        if (mc.targetedEntity instanceof LivingEntity e) {
            return List.of(e.getName().getString(),
                    String.format("%.1f / %.1f HP", e.getHealth(), e.getMaxHealth()),
                    String.format("%.1f m", mc.player.distanceTo(e)));
        }
        return List.of();
    }

    @Override
    public List<String> sample() { return List.of("Zombie", "14.0 / 20.0 HP", "3.2 m"); }
}
