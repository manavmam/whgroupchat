package net.kaupenjoe.tutorialmod.command;

import net.kaupenjoe.tutorialmod.chat.GroupChatManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.CommandDispatcher;

public class LeaveGroupCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("leavechat")
            .executes(ctx -> {
                GroupChatManager.leaveGroup(ctx.getSource().getPlayerOrException().getUUID());
                ctx.getSource().sendSuccess(() -> Component.literal("Left group chat."), false);
                return 1;
            })
        );
    }
}
