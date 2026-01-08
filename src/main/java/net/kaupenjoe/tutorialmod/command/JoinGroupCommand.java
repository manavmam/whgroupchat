package net.kaupenjoe.tutorialmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.kaupenjoe.tutorialmod.chat.GroupChatManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

public class JoinGroupCommand {
    // Suggestion provider for tab completion of group names
    private static final SuggestionProvider<CommandSourceStack> GROUP_NAME_SUGGESTIONS = (context, builder) -> {
        Set<String> groupNames = GroupChatManager.getActiveGroups();
        for (String group : groupNames) {
            builder.suggest(group);
        }
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("joingroup")
            .then(Commands.argument("groupname", StringArgumentType.word())
                .suggests(GROUP_NAME_SUGGESTIONS)
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    String groupName = StringArgumentType.getString(context, "groupname");
                    GroupChatManager.joinGroup(player.getUUID(), groupName, null);
                    context.getSource().sendSuccess(() -> Component.literal("Joined group: " + groupName), false);
                    return 1;
                })
                .then(Commands.argument("password", StringArgumentType.word())
                    .executes(context -> {
                        ServerPlayer player = context.getSource().getPlayerOrException();
                        String groupName = StringArgumentType.getString(context, "groupname");
                        String password = StringArgumentType.getString(context, "password");
                        GroupChatManager.joinGroup(player.getUUID(), groupName, password);
                        context.getSource().sendSuccess(() -> Component.literal("Joined group: " + groupName), false);
                        return 1;
                    })
                )
            )
        );
    }
}
