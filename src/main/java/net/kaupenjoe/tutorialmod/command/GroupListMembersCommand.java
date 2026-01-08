package net.kaupenjoe.tutorialmod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.kaupenjoe.tutorialmod.chat.GroupChatManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.CommandDispatcher;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class GroupListMembersCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gwho")
            .executes(GroupListMembersCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player;
        try {
            player = context.getSource().getPlayerOrException();
        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(Component.literal("Only players can use this command."));
            return 0;
        }        UUID playerId = player.getUUID();

        String group = GroupChatManager.getPlayerGroup(playerId);
        if (group == null) {
            context.getSource().sendFailure(Component.literal("You are not in a group."));
            return 0;
        }

        Set<UUID> members = GroupChatManager.getGroupMembers(group);
        if (members.isEmpty()) {
            context.getSource().sendSuccess(() -> Component.literal("No members in your group."), false);
            return 1;
        }

        MinecraftServer server = player.getServer();
        if (server == null) {
            context.getSource().sendFailure(Component.literal("Server not available."));
            return 0;
        }

        List<String> memberNames = members.stream()
            .map(server.getPlayerList()::getPlayer)
            .filter(m -> m != null)
            .map(p -> p.getDisplayName().getString())
            .collect(Collectors.toList());

        context.getSource().sendSuccess(() -> Component.literal("Members in your group: " + String.join(", ", memberNames)), false);
        return Command.SINGLE_SUCCESS;
    }
}
