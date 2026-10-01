package dev.clienthud.widgets;

import dev.clienthud.HudConfig;
import dev.clienthud.HudWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.List;

/** Names come from /hud party add <name>. Shows ping if the player is online (tab list). */
public class PartyWidget extends HudWidget {
    public PartyWidget() { super("party", "Party", 6, 190); }

    @Override
    public List<String> lines(MinecraftClient mc) {
        List<String> out = new ArrayList<>();
        for (String n : HudConfig.INSTANCE.party) {
            String state = "offline";
            if (mc.getNetworkHandler() != null) {
                for (PlayerListEntry e : mc.getNetworkHandler().getPlayerList()) {
                    if (e.getProfile().getName().equalsIgnoreCase(n)) { state = e.getLatency() + " ms"; break; }
                }
            }
            out.add(n + "  " + state);
        }
        return out;
    }

    @Override
    public List<String> sample() { return List.of("Alex  42 ms", "Nova  offline"); }
}
