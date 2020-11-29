package xyz.yermolim.keeptherain.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

import org.apache.commons.lang3.tuple.Pair;

import xyz.yermolim.keeptherain.KeepTheRain;

@EventBusSubscriber(modid = KeepTheRain.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonConfig {
  
  public static final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);  
  public static final ClientConfig CLIENT = specPair.getLeft();
  public static final ForgeConfigSpec CLIENT_SPEC = specPair.getRight();

  public static boolean preserveRainTime;     
  public static double rainContinuationChance;     

  public static void bakeConfig() {
    preserveRainTime = CLIENT.preserveRainTime.get();
    rainContinuationChance = CLIENT.rainContinuationChance.get();
  }  

  @SubscribeEvent
  public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
      if (configEvent.getConfig().getSpec() == CommonConfig.CLIENT_SPEC) {
          bakeConfig();
      }
  }

}
