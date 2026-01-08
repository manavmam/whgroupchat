package net.kaupenjoe.tutorialmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GroupHelpCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ghelp").executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();

            player.sendSystemMessage(Component.literal("§a/groupchat Help Menu"));
            player.sendSystemMessage(Component.literal("/joingroup [groupname] [optional password] §7- Create or join a group"));
            player.sendSystemMessage(Component.literal("/leavegroup §7- Leave your current group"));
            player.sendSystemMessage(Component.literal("/glist §7- List all group names and who's in them"));
            player.sendSystemMessage(Component.literal("/gwho §7- List people in your group"));
            player.sendSystemMessage(Component.literal("/gtoggle §7- Toggle group-only chat"));

            return 1;
        }));
    }
}
