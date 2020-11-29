package xyz.yermolim.keeptherain;

import net.minecraft.world.IWorld;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("keeptherain")
public class KeepTheRain
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public KeepTheRain() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }    

    @SubscribeEvent
    public void onWake(SleepFinishedTimeEvent event) {
        IWorld world = event.getWorld();
        IWorldInfo worldInfo = world.getWorldInfo();
        if (!(worldInfo instanceof ServerWorldInfo)) {
            return;
        }
        ServerWorldInfo serverWorldInfo = (ServerWorldInfo)worldInfo;
        int untilRain = !worldInfo.isRaining() && !worldInfo.isThundering()
            ? serverWorldInfo.getRainTime()
            : 0;
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                if (untilRain == 0) {      
                    int random = Math.round((float)Math.random());  
                    if (random == 0) {
                        serverWorldInfo.setRaining(true);
                    } else {                
                        serverWorldInfo.setRaining(false);
                    }
                } else {
                    serverWorldInfo.setRainTime(untilRain);
                }
            }
        }, 10);
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    // @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    // public static class RegistryEvents {
    //     @SubscribeEvent
    //     public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
    //         // register a new block here
    //         LOGGER.info("HELLO from Register Block");
    //     }
    // }
}
