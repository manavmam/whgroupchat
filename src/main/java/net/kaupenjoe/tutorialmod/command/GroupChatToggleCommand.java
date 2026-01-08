package net.kaupenjoe.tutorialmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.kaupenjoe.tutorialmod.chat.GroupChatManager;

public class GroupChatToggleCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gtoggle")
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                GroupChatManager.toggleGroupChat(player.getUUID());

                boolean isNowToggled = GroupChatManager.isToggled(player.getUUID());
                context.getSource().sendSuccess(() ->
                    Component.literal("Group chat toggled " + (isNowToggled ? "ON" : "OFF")), false
                );
                return 1;
            }));
    }
}
