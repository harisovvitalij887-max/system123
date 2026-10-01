package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorWidget extends HudWidget {
    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public ArmorWidget() { super("armor", "Armor", 6, 120); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        List<String> out = new ArrayList<>();
        for (EquipmentSlot s : SLOTS) {
            ItemStack st = mc.player.getEquippedStack(s);
            if (st.isEmpty()) continue;
            String dur = st.getMaxDamage() > 0
                    ? (100 * (st.getMaxDamage() - st.getDamage()) / st.getMaxDamage()) + "%" : "-";
            out.add(st.getName().getString() + "  " + dur);
        }
        return out;
    }

    @Override
    public List<String> sample() { return List.of("Helmet  98%", "Chestplate  72%"); }
}
