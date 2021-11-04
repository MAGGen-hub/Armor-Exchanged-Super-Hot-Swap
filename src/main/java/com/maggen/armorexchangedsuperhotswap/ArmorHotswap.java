package com.maggen.armorexchangedsuperhotswap;

import com.maggen.armorexchangedsuperhotswap.config.Config;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ArmorHotswap.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmorHotswap {
	public static final String MOD_ID = "armorexchangedsuperhotswap";

	public ArmorHotswap() {
		MinecraftForge.EVENT_BUS.register(this);

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
	}

}
