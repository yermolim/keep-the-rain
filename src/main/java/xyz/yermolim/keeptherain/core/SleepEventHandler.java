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
import xyz.yermolim.keeptherain.config.CommonConfig;

@EventBusSubscriber(modid = KeepTheRain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class SleepEventHandler {
    
    @SubscribeEvent
    public static void onWake(SleepFinishedTimeEvent event) {
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
                    double rainContinuationChance = CommonConfig.rainContinuationChance;   
                    double roll = Math.random();
                    KeepTheRain.LOGGER.info(String.format("Rain continuation dice rolled: %s/%s.", roll, rainContinuationChance));
                    if (roll < rainContinuationChance) {
                        serverWorldInfo.setRaining(true);
                        KeepTheRain.LOGGER.info("Rain continuation passed.");
                    } else {                
                        serverWorldInfo.setRaining(false);
                        KeepTheRain.LOGGER.info("Rain continuation not passed.");
                    }
              } else if (CommonConfig.preserveRainTime) {
                  serverWorldInfo.setRainTime(untilRain);
              }
          }
      }, 10);
  }
}
