package com.maggen.armorexchangedsuperhotswap.config;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;

	public static Boolean preventCurses;
	public static List<? extends String> itemBlacklist;

	static {
		final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	public static void bakeConfig() {
		preventCurses = ServerConfig.preventCurses.get();
		itemBlacklist = ServerConfig.itemBlacklist.get();
	}

}
