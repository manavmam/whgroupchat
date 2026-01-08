package net.kaupenjoe.tutorialmod.event;

import net.kaupenjoe.tutorialmod.chat.GroupChatManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber
public class GroupChatEvents {

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        UUID uuid = player.getUUID();

        if (GroupChatManager.isToggled(uuid)) {
            String groupName = GroupChatManager.getPlayerGroup(uuid);
            if (groupName != null) {
                Set<UUID> members = GroupChatManager.getGroupMembers(groupName);

                String message = "<" + player.getDisplayName().getString() + "> " + event.getMessage().getString();

                for (UUID memberUUID : members) {
                    ServerPlayer member = player.getServer().getPlayerList().getPlayer(memberUUID);
                    if (member != null) {
                        member.sendSystemMessage(net.minecraft.network.chat.Component.literal("[Group] " + message));
                    }
                }

                // Cancel the original message from going to global chat
                event.setCanceled(true);
            }
        }
    }
}
