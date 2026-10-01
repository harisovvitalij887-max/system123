package dev.clienthud;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHudMod implements ClientModInitializer {
    public static final Logger LOG = LoggerFactory.getLogger("clienthud");
    public static KeyBinding openEditor;

    @Override
    public void onInitializeClient() {
        HudConfig.load();
        openEditor = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.clienthud.editor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.clienthud"));

        ClientCommandRegistrationCallback.EVENT.register((d, reg) -> Commands.register(d));
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            HudRenderer.tick(mc);
            while (openEditor.wasPressed()) mc.setScreen(new HudEditorScreen());
        });
        HudRenderCallback.EVENT.register((ctx, tickCounter) -> {
            // The editor draws the widgets itself, avoid drawing them twice.
            if (net.minecraft.client.MinecraftClient.getInstance().currentScreen instanceof HudEditorScreen) return;
            HudRenderer.render(ctx, false);
        });
    }
}
