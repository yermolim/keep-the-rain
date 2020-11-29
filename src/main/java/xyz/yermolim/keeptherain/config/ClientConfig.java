package xyz.yermolim.keeptherain.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class ClientConfig {

  public final BooleanValue preserveRainTime;
  public final DoubleValue rainContinuationChance;

  public ClientConfig(ForgeConfigSpec.Builder builder) {
    preserveRainTime = builder
      .comment("preserve the time left to next rain after players woke up")
      .define("preserveRainTime", true);

    rainContinuationChance = builder
      .comment("chance of rain continuation after players woke up from 0(never) to 1(always)")
      .defineInRange("rainContinuationChance", 0.5, 0.0, 1.0);
  }
}  
