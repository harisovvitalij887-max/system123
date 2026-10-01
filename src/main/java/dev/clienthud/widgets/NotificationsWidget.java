package dev.clienthud.widgets;

import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.*;

/** Short-lived messages: effect gained / effect ended / low health. */
public class NotificationsWidget extends HudWidget {
    private record Note(String text, long until) {}
    private final Deque<Note> notes = new ArrayDeque<>();
    private Set<String> prevEffects = new HashSet<>();
    private boolean lowHp;

    public NotificationsWidget() { super("notifications", "Notifications", 560, 190); }

    private void push(String t) {
        notes.addFirst(new Note(t, System.currentTimeMillis() + 5000));
        while (notes.size() > 5) notes.removeLast();
    }

    @Override
    public void tick(MinecraftClient mc) {
        Set<String> now = new HashSet<>();
        for (StatusEffectInstance e : mc.player.getStatusEffects()) now.add(e.getEffectType().value().getName().getString());
        for (String n : now) if (!prevEffects.contains(n)) push("+ " + n);
        for (String n : prevEffects) if (!now.contains(n)) push("- " + n + " ended");
        prevEffects = now;

        boolean low = mc.player.getHealth() <= 6f;
        if (low && !lowHp) push("Low health!");
        lowHp = low;
    }

    @Override
    public List<String> lines(MinecraftClient mc) {
        long t = System.currentTimeMillis();
        notes.removeIf(n -> n.until < t);
        List<String> out = new ArrayList<>();
        for (Note n : notes) out.add(n.text);
        return out;
    }

    @Override
    public List<String> sample() { return List.of("+ Speed", "Low health!"); }
}
