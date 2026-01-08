package net.kaupenjoe.tutorialmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.kaupenjoe.tutorialmod.chat.GroupChatManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;
import java.util.UUID;

public class GroupListCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("glist").executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();

            Set<String> groups = GroupChatManager.getActiveGroups();

            if (groups.isEmpty()) {
                player.sendSystemMessage(Component.literal("§cThere are no active groups."));
                return 1;
            }

            player.sendSystemMessage(Component.literal("§aActive Groups:"));

            for (String group : groups) {
                Set<UUID> members = GroupChatManager.getGroupMembers(group);
                StringBuilder memberNames = new StringBuilder();

                for (UUID memberId : members) {
                    ServerPlayer member = player.getServer().getPlayerList().getPlayer(memberId);
                    if (member != null) {
                        if (memberNames.length() > 0) memberNames.append(", ");
                        memberNames.append(member.getDisplayName().getString());
                    }
                }

                player.sendSystemMessage(Component.literal("§e" + group + " §7(" + members.size() + "): " + memberNames));
            }

            return 1;
        }));
    }
}
