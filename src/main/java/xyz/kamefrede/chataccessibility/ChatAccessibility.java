package xyz.kamefrede.chataccessibility;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.options.Option;

import java.util.Arrays;
import xyz.kamefrede.chataccessibility.mixin.ChatOptionsAccess;

public class ChatAccessibility implements ModInitializer {

	@Environment(EnvType.CLIENT)
	@Override
	public void onInitialize() {
		Option[] oldOptions = ChatOptionsAccess.getOPTIONS();
		int optionCount = oldOptions.length;
		Option[] newOptions = Arrays.copyOf(oldOptions, optionCount + 2);
		newOptions[optionCount] = AccessibilityCyclingOption.CHAT_COLOR_OPTION;
		newOptions[optionCount + 1] = AccessibilityCyclingOption.NAME_COLOR_OPTION;
		ChatOptionsAccess.setOPTIONS(newOptions);
	}
}
