package xyz.yermolim.keeptherain.core;

import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.world.IWorld;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import xyz.yermolim.keeptherain.KeepTheRain;
import xyz.yermolim.keeptherain.config.ConfigManager;

@EventBusSubscriber(modid = KeepTheRain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class SleepEventHandler {
    
    @SubscribeEvent
    public static void onWake(SleepFinishedTimeEvent event) {
        IWorld world = event.getWorld();
        IWorldInfo worldInfo = world.getLevelData();
        if (!(worldInfo instanceof ServerWorldInfo)) {
            return;
        }

        ServerWorldInfo serverWorldInfo = (ServerWorldInfo)worldInfo;
        int untilRain = !worldInfo.isRaining()
            ? serverWorldInfo.getRainTime()
            : 0;
        int untilThunder = !worldInfo.isThundering()
            ? serverWorldInfo.getThunderTime()
            : 0;

        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                if (untilRain == 0) {   
                        double rainContinuationChance = ConfigManager.rainContinuationChance;   
                        double rainRoll = Math.random();
                        KeepTheRain.LOGGER.info(String.format("Rain continuation dice rolled: %s/%s.", rainRoll, rainContinuationChance));
                        if (rainRoll < rainContinuationChance) {
                            serverWorldInfo.setRaining(true);
                            KeepTheRain.LOGGER.info("Rain continuation passed.");
                            if (untilThunder == 0) {
                                double thunderContinuationChance = ConfigManager.thunderContinuationChance;  
                                double thunderRoll = Math.random();
                                KeepTheRain.LOGGER.info(String.format("Thunder continuation dice rolled: %s/%s.", thunderRoll, thunderContinuationChance));
                                if (thunderRoll < thunderContinuationChance) {                                    
                                    serverWorldInfo.setThundering(true);
                                    KeepTheRain.LOGGER.info("Thunder continuation passed.");
                                } else {                                    
                                    KeepTheRain.LOGGER.info("Thunder continuation not passed.");
                                }
                            }
                        } else {                
                            serverWorldInfo.setRaining(false);
                            serverWorldInfo.setThundering(false);
                            KeepTheRain.LOGGER.info("Rain continuation not passed.");
                        }
                } else if (ConfigManager.preserveRainTime) {
                    serverWorldInfo.setRainTime(untilRain);
                    serverWorldInfo.setThunderTime(untilThunder);
                }
            }
        }, 10);
  }
}
