package net.kaupenjoe.tutorialmod;

import com.mojang.logging.LogUtils;
import net.kaupenjoe.tutorialmod.command.GroupChatCommand;
import net.kaupenjoe.tutorialmod.command.GroupChatToggleCommand;
import net.kaupenjoe.tutorialmod.command.GroupHelpCommand;
import net.kaupenjoe.tutorialmod.command.GroupListCommand;
import net.kaupenjoe.tutorialmod.command.GroupListMembersCommand;
import net.kaupenjoe.tutorialmod.command.JoinGroupCommand;
import net.kaupenjoe.tutorialmod.command.LeaveGroupCommand;
import net.kaupenjoe.tutorialmod.event.PlayerLeaveHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutorialMod.MOD_ID)
public class TutorialMod {
    public static final String MOD_ID = "tutorialmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TutorialMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);        
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PlayerLeaveHandler.class);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("GroupChatMod setup complete.");
    }
    
    private void onRegisterCommands(RegisterCommandsEvent event) {
        JoinGroupCommand.register(event.getDispatcher());
        LeaveGroupCommand.register(event.getDispatcher());
        GroupChatCommand.register(event.getDispatcher());
        GroupListCommand.register(event.getDispatcher());
        GroupListMembersCommand.register(event.getDispatcher());
        GroupHelpCommand.register(event.getDispatcher());
        GroupChatToggleCommand.register(event.getDispatcher());
    }
    
	//private <T extends Event> void setup(T t1) {
	//}
}
