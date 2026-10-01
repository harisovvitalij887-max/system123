package dev.clienthud;

import dev.clienthud.widgets.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public final class HudRenderer {
    public static final List<HudWidget> WIDGETS = new ArrayList<>();
    static {
        WIDGETS.add(new WatermarkWidget());
        WIDGETS.add(new SystemWidget());
        WIDGETS.add(new CoordsWidget());
        WIDGETS.add(new PotionsWidget());
        WIDGETS.add(new ArmorWidget());
        WIDGETS.add(new CooldownsWidget());
        WIDGETS.add(new KeybindsWidget());
        WIDGETS.add(new TargetWidget());
        WIDGETS.add(new InventoryWidget());
        WIDGETS.add(new WaypointsWidget());
        WIDGETS.add(new PartyWidget());
        WIDGETS.add(new NotificationsWidget());
    }

    private HudRenderer() {}

    public static void tick(MinecraftClient mc) {
        if (mc.player == null) return;
        for (HudWidget w : WIDGETS) w.tick(mc);
    }

    /** @param editing true inside the editor: hidden widgets are drawn dimmed and empty ones show samples. */
    public static void render(DrawContext ctx, boolean editing) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || (!editing && mc.options.hudHidden)) return;
        HudConfig cfg = HudConfig.INSTANCE;
        TextRenderer tr = mc.textRenderer;

        ctx.getMatrices().push();
        ctx.getMatrices().scale(cfg.scale, cfg.scale, 1f);
        for (HudWidget w : WIDGETS) {
            HudConfig.State s = w.state();
            if (!s.visible && !editing) continue;
            List<String> lines = w.lines(mc);
            if (lines.isEmpty() && editing) lines = w.sample();
            boolean dim = !s.visible;

            String head = w.header(mc);
            int width = tr.getWidth(head) + 22;
            for (String l : lines) width = Math.max(width, tr.getWidth(l) + 16);
            int height = lines.isEmpty() ? 16 : 18 + lines.size() * 10 + 2;
            w.w = width; w.h = height;

            int alpha = (int) (cfg.opacity * (dim ? 0.4f : 1f) * 255) & 0xFF;
            int bg = (alpha << 24) | 0x0A0B0F;
            int accent = 0xFF000000 | cfg.accent;
            pill(ctx, s.x, s.y, width, height, lines.isEmpty() ? 8 : 5, bg);
            ctx.fill(s.x + 6, s.y + 6, s.x + 10, s.y + 10, dim ? 0xFF555555 : accent);
            ctx.drawText(tr, head, s.x + 14, s.y + 4, dim ? 0xFF888888 : 0xFFFFFFFF, false);
            int y = s.y + 18;
            for (String l : lines) {
                ctx.drawText(tr, l, s.x + 8, y, dim ? 0xFF777777 : 0xFFD6D8E0, false);
                y += 10;
            }
        }
        ctx.getMatrices().pop();
    }

    /** Rounded rectangle from plain fills (no textures needed). r = corner radius in px, max 8. */
    static void pill(DrawContext c, int x, int y, int w, int h, int r, int col) {
        r = Math.min(r, Math.min(w, h) / 2);
        c.fill(x + r, y, x + w - r, y + h, col);
        for (int i = 0; i < r; i++) {
            int inset = r - (int) Math.round(Math.sqrt((double) r * r - (double) (r - i - 0.5) * (r - i - 0.5)));
            c.fill(x + inset, y + i, x + w - inset, y + i + 1, col);
            c.fill(x + inset, y + h - i - 1, x + w - inset, y + h - i, col);
        }
        c.fill(x, y + r, x + r, y + h - r, col);
        c.fill(x + w - r, y + r, x + w, y + h - r, col);
    }
}
