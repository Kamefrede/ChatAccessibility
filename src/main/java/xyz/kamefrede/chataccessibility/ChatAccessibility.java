package xyz.kamefrede.chataccessibility;


import net.minecraft.client.gui.screen.ChatOptionsScreen;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Arrays;


@Mod(ChatAccessibility.MOD_ID)
public class ChatAccessibility {
	
	public static final String MOD_ID = "chataccessibility";

	public ChatAccessibility() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			int optionCount = ChatOptionsScreen.CHAT_OPTIONS.length;
			AbstractOption[] newOptions = Arrays.copyOf(ChatOptionsScreen.CHAT_OPTIONS,  optionCount+ 2);
			newOptions[optionCount] = AccessibilityIterableOption.CHAT_COLOR_OPTION;
			newOptions[optionCount + 1] = AccessibilityIterableOption.NAME_COLOR_OPTION;
			ChatOptionsScreen.CHAT_OPTIONS = newOptions;
		});
	}

	public static ITextComponent setChatColor(ITextComponent component){
		ITextComponent newComp = new StringTextComponent("");
		component.forEach(el -> {
			if(el.getStyle().getHoverEvent() != null && el.getStyle().getHoverEvent().getAction().equals(HoverEvent.Action.SHOW_ENTITY)) {
				if(el.getStyle().getColor() == null) {
					el.getStyle().setColor(ConfigHandler.CLIENT.nameColor.get());
				}
			} else {
				if(el.getStyle().getColor() == null && !(el.getUnformattedComponentText().trim().equals("<") || el.getUnformattedComponentText().trim().equals(">"))) {
					el.getStyle().setColor(ConfigHandler.CLIENT.chatColor.get());
				}
			}
			newComp.appendSibling(el);
		});
		return newComp;
	}

	public static ITextComponent setPlayerlistColor(ITextComponent component) {
		if(component.getStyle().getColor() == null){
			component.getStyle().setColor(ConfigHandler.CLIENT.nameColor.get());
		}
		return component;
	}



}
