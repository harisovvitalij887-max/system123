package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.util.*;

/** Top 6 item types in the inventory (hotbar + main), with total counts. */
public class InventoryWidget extends HudWidget {
    public InventoryWidget() { super("inventory", "Inventory", 560, 34); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (int i = 0; i < 36; i++) {
            ItemStack st = mc.player.getInventory().getStack(i);
            if (!st.isEmpty()) counts.merge(st.getName().getString(), st.getCount(), Integer::sum);
        }
        List<String> out = new ArrayList<>();
        counts.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue()).limit(6)
                .forEach(e -> out.add(e.getKey() + "  x" + e.getValue()));
        return out;
    }

    @Override
    public List<String> sample() { return List.of("Cobblestone  x128", "Ender Pearl  x16"); }
}
