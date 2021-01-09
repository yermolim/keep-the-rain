package xyz.yermolim.keeptherain.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

import org.apache.commons.lang3.tuple.Pair;

import xyz.yermolim.keeptherain.KeepTheRain;

@EventBusSubscriber(modid = KeepTheRain.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigManager {  
  private static final Pair<CommonConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);  
  private static final Pair<ClientConfig, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);  

  public static final CommonConfig COMMON = commonPair.getLeft();
  public static final ForgeConfigSpec COMMON_SPEC = commonPair.getRight();

  public static final ClientConfig CLIENT = clientPair.getLeft();
  public static final ForgeConfigSpec CLIENT_SPEC = clientPair.getRight();

  public static boolean preserveRainTime;     
  public static double rainContinuationChance;     
  public static double thunderContinuationChance;     

  private static void bakeCommonConfig() {
    preserveRainTime = COMMON.preserveRainTime.get();
    rainContinuationChance = COMMON.rainContinuationChance.get();
    thunderContinuationChance = COMMON.thunderContinuationChance.get();
  }  
  
  private static void bakeClientConfig() {
  }  

  @SubscribeEvent
  public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
    if (configEvent.getConfig().getSpec() == ConfigManager.COMMON_SPEC) {
      bakeCommonConfig();
    }
    if (configEvent.getConfig().getSpec() == ConfigManager.CLIENT_SPEC) {
      bakeClientConfig();
    }
  }

}
