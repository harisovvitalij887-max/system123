package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class CooldownsWidget extends HudWidget {
    private static final Item[] TRACKED = {Items.ENDER_PEARL, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE,
            Items.CHORUS_FRUIT, Items.SHIELD, Items.TRIDENT, Items.FIREWORK_ROCKET};

    public CooldownsWidget() { super("cooldowns", "Cooldowns", 420, 34); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        List<String> out = new ArrayList<>();
        for (Item it : TRACKED) {
            float p = mc.player.getItemCooldownManager().getCooldownProgress(new ItemStack(it), 0f);
            if (p > 0f) out.add(new ItemStack(it).getName().getString() + "  " + Math.round(p * 100) + "%");
        }
        return out;
    }

    @Override
    public List<String> sample() { return List.of("Ender Pearl  60%", "Golden Apple  30%"); }
}
