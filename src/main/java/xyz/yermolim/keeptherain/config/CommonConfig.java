package xyz.yermolim.keeptherain.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class CommonConfig {

  public final BooleanValue preserveRainTime;
  public final DoubleValue rainContinuationChance;
  public final DoubleValue thunderContinuationChance;

  public CommonConfig(ForgeConfigSpec.Builder builder) {
    preserveRainTime = builder
      .comment("Enable preserving the time left to next rain or thunder after players woke up")
      .define("preserveRainTime", true);

    rainContinuationChance = builder
      .comment("Change the chance of rain continuation after players woke up from 0(never) to 1(always)")
      .defineInRange("rainContinuationChance", 0.5, 0.0, 1.0);

    thunderContinuationChance = builder
      .comment("Change the chance of thunder continuation after players woke up from 0(never) to 1(always when rain continuation is passed)")
      .defineInRange("thunderContinuationChance", 0.5, 0.0, 1.0);
  }
}  
