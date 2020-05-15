package xyz.kamefrede.chataccessibility;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {

	public static class Client {

		public final ForgeConfigSpec.ConfigValue<TextFormatting> chatColor;
		public final ForgeConfigSpec.ConfigValue<TextFormatting> nameColor;

		public Client(ForgeConfigSpec.Builder builder) {
			chatColor = builder.comment("Change here for the chat color you want to use, default minecraft TextFormatting colors.")
					.defineEnum("client.chatColor", TextFormatting.WHITE);
			nameColor = builder.comment("Change here for the name color you want to use, default minecraft TextFormatting colors.")
					.defineEnum("client.nameColor", TextFormatting.BLUE);
		}

	}

	public static final Client CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}
}
