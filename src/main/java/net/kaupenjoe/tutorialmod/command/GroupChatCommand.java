package net.kaupenjoe.tutorialmod.command;

import java.util.UUID;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.kaupenjoe.tutorialmod.chat.GroupChatManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GroupChatCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("g")
            .then(Commands.argument("message", StringArgumentType.greedyString())
                .executes(ctx -> {
                    ServerPlayer sender = ctx.getSource().getPlayerOrException();
                    String group = GroupChatManager.getPlayerGroup(sender.getUUID());

                    if (group == null) {
                        sender.sendSystemMessage(Component.literal("You're not in a group chat."));
                        return 0;
                    }

                    String msg = StringArgumentType.getString(ctx, "message");

                    for (UUID uuid : GroupChatManager.getGroupMembers(group)) {
                        ServerPlayer player = sender.server.getPlayerList().getPlayer(uuid);
                        if (player != null) {
                            player.sendSystemMessage(Component.literal("[Group] ")
                                .append(sender.getDisplayName().getString())
                                .append(": ")
                                .append(msg));
                        }
                    }
                    return 1;
                })
            )
        );
    }
}
