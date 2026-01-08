package net.kaupenjoe.tutorialmod.chat;

import java.util.*;

public class GroupChatManager {
    private static final Map<UUID, String> playerGroups = new HashMap<>();
    private static final Map<String, Set<UUID>> groups = new HashMap<>();
    private static final Map<String, String> groupPasswords = new HashMap<>();

    public static void joinGroup(UUID playerUUID, String groupName, @org.jetbrains.annotations.Nullable String password) {
        String actualPassword = groupPasswords.get(groupName);

        if (groups.containsKey(groupName)) {
            if (actualPassword != null && !actualPassword.equals(password)) {
                throw new IllegalArgumentException("Incorrect password.");
            }
        } else {
            groupPasswords.put(groupName, password);
            groups.put(groupName, new HashSet<>());
        }

        leaveGroup(playerUUID);
        groups.get(groupName).add(playerUUID);
        playerGroups.put(playerUUID, groupName);
    }

    public static void leaveGroup(UUID playerUUID) {
        String currentGroup = playerGroups.get(playerUUID);
        if (currentGroup != null && groups.containsKey(currentGroup)) {
            groups.get(currentGroup).remove(playerUUID);
            if (groups.get(currentGroup).isEmpty()) {
                groups.remove(currentGroup);
                groupPasswords.remove(currentGroup);
            }
        }
        playerGroups.remove(playerUUID);
    }

    public static Set<UUID> getGroupMembers(String groupName) {
        return groups.getOrDefault(groupName, Collections.emptySet());
    }

    public static String getPlayerGroup(UUID playerUUID) {
        return playerGroups.get(playerUUID);
    }

    public static Set<String> getActiveGroups() {
        return groups.keySet();
    }
    
    private static final Set<UUID> toggledPlayers = new HashSet<>();

    public static boolean isToggled(UUID playerUUID) {
        return toggledPlayers.contains(playerUUID);
    }

    public static void toggleGroupChat(UUID playerUUID) {
        if (toggledPlayers.contains(playerUUID)) {
            toggledPlayers.remove(playerUUID);
        } else {
            toggledPlayers.add(playerUUID);
        }
    }
}
