package dev.clienthud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Left mouse: drag a widget. Right mouse: show/hide it.
 * Mouse wheel: HUD scale. Shift + wheel: opacity. Esc: save and close.
 */
public class HudEditorScreen extends Screen {
    private HudWidget dragging;
    private int offX, offY;

    public HudEditorScreen() { super(Text.literal("HUD editor")); }

    private HudWidget at(double mx, double my) {
        float sc = HudConfig.INSTANCE.scale;
        double x = mx / sc, y = my / sc;
        for (int i = HudRenderer.WIDGETS.size() - 1; i >= 0; i--) {
            HudWidget w = HudRenderer.WIDGETS.get(i);
            HudConfig.State s = w.state();
            if (x >= s.x && x <= s.x + w.w && y >= s.y && y <= s.y + w.h) return w;
        }
        return null;
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        ctx.fill(0, 0, width, height, 0x66000000);
        HudRenderer.render(ctx, true);
        HudConfig c = HudConfig.INSTANCE;
        String tip = String.format("Drag: move   RMB: show/hide   Wheel: scale %.0f%%   Shift+Wheel: opacity %.0f%%   Esc: save",
                c.scale * 100, c.opacity * 100);
        ctx.drawCenteredTextWithShadow(textRenderer, tip, width / 2, height - 16, 0xFFFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        HudWidget w = at(mx, my);
        if (w == null) return super.mouseClicked(mx, my, button);
        HudConfig.State s = w.state();
        if (button == 1) { s.visible = !s.visible; return true; }
        if (button == 0) {
            dragging = w;
            offX = (int) (mx / HudConfig.INSTANCE.scale) - s.x;
            offY = (int) (my / HudConfig.INSTANCE.scale) - s.y;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
        if (dragging == null) return false;
        float sc = HudConfig.INSTANCE.scale;
        HudConfig.State s = dragging.state();
        s.x = Math.max(0, Math.min((int) (width / sc) - dragging.w, (int) (mx / sc) - offX));
        s.y = Math.max(0, Math.min((int) (height / sc) - dragging.h, (int) (my / sc) - offY));
        return true;
    }

    @Override
    public boolean mouseReleased(double mx, double my, int button) {
        dragging = null;
        return super.mouseReleased(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double h, double v) {
        HudConfig c = HudConfig.INSTANCE;
        if (hasShiftDown()) c.opacity = Math.max(0.2f, Math.min(1f, c.opacity + (float) v * 0.05f));
        else c.scale = Math.max(0.6f, Math.min(1.6f, c.scale + (float) v * 0.05f));
        return true;
    }

    @Override
    public void removed() { HudConfig.save(); }

    @Override
    public boolean shouldPause() { return false; }
}
