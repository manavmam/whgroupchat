package net.kaupenjoe.tutorialmod.event;

import net.kaupenjoe.tutorialmod.chat.GroupChatManager;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerLeaveHandler {
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        GroupChatManager.leaveGroup(event.getEntity().getUUID());
    }
}
