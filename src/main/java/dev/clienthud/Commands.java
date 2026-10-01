package dev.clienthud;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

/**
 *  /hud wp add <name> | remove <name> | list    - waypoints at your current position
 *  /hud party add <name> | remove <name>         - players shown in the Party widget
 */
final class Commands {
    private Commands() {}

    static void register(CommandDispatcher<FabricClientCommandSource> d) {
        d.register(literal("hud")
            .then(literal("wp")
                .then(literal("add").then(argument("name", StringArgumentType.word()).executes(c -> {
                    var p = MinecraftClient.getInstance().player;
                    if (p == null) return 0;
                    HudConfig.Wp w = new HudConfig.Wp();
                    w.name = StringArgumentType.getString(c, "name");
                    w.x = p.getBlockX(); w.y = p.getBlockY(); w.z = p.getBlockZ();
                    HudConfig.INSTANCE.waypoints.removeIf(o -> o.name.equalsIgnoreCase(w.name));
                    HudConfig.INSTANCE.waypoints.add(w);
                    HudConfig.save();
                    c.getSource().sendFeedback(Text.literal("Waypoint '" + w.name + "' saved"));
                    return 1;
                })))
                .then(literal("remove").then(argument("name", StringArgumentType.word()).executes(c -> {
                    String n = StringArgumentType.getString(c, "name");
                    boolean ok = HudConfig.INSTANCE.waypoints.removeIf(o -> o.name.equalsIgnoreCase(n));
                    HudConfig.save();
                    c.getSource().sendFeedback(Text.literal(ok ? "Removed " + n : "No such waypoint"));
                    return ok ? 1 : 0;
                })))
                .then(literal("list").executes(c -> {
                    for (HudConfig.Wp w : HudConfig.INSTANCE.waypoints)
                        c.getSource().sendFeedback(Text.literal(w.name + ": " + w.x + " " + w.y + " " + w.z));
                    return 1;
                })))
            .then(literal("party")
                .then(literal("add").then(argument("name", StringArgumentType.word()).executes(c -> {
                    String n = StringArgumentType.getString(c, "name");
                    if (HudConfig.INSTANCE.party.stream().noneMatch(n::equalsIgnoreCase)) HudConfig.INSTANCE.party.add(n);
                    HudConfig.save();
                    c.getSource().sendFeedback(Text.literal("Added " + n));
                    return 1;
                })))
                .then(literal("remove").then(argument("name", StringArgumentType.word()).executes(c -> {
                    String n = StringArgumentType.getString(c, "name");
                    boolean ok = HudConfig.INSTANCE.party.removeIf(n::equalsIgnoreCase);
                    HudConfig.save();
                    c.getSource().sendFeedback(Text.literal(ok ? "Removed " + n : "Not in party list"));
                    return ok ? 1 : 0;
                })))));
    }
}
