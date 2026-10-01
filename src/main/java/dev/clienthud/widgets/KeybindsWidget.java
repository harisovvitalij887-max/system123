package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/** Shows the keys that are currently held down. */
public class KeybindsWidget extends HudWidget {
    public KeybindsWidget() { super("keybinds", "Keybinds", 6, 60); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        List<String> out = new ArrayList<>();
        KeyBinding[] keys = {mc.options.forwardKey, mc.options.backKey, mc.options.leftKey, mc.options.rightKey,
                mc.options.jumpKey, mc.options.sneakKey, mc.options.sprintKey, mc.options.attackKey, mc.options.useKey};
        for (KeyBinding k : keys) {
            if (k.isPressed())
                out.add(Text.translatable(k.getTranslationKey()).getString() + "  [" + k.getBoundKeyLocalizedText().getString() + "]");
        }
        return out;
    }

    @Override
    public List<String> sample() { return List.of("Forward  [W]", "Sprint  [Left Control]"); }
}
