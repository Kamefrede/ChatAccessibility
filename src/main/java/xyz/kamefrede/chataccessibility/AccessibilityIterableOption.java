package xyz.kamefrede.chataccessibility;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class AccessibilityIterableOption extends AbstractOption {
	public static final AccessibilityIterableOption CHAT_COLOR_OPTION = new AccessibilityIterableOption("chataccessibility.options.chat_color", ConfigHandler.CLIENT.chatColor);
	public static final AccessibilityIterableOption NAME_COLOR_OPTION = new AccessibilityIterableOption("chataccessibility.options.name_color", ConfigHandler.CLIENT.nameColor);

	private final ForgeConfigSpec.ConfigValue<TextFormatting> configValue;

	public AccessibilityIterableOption(String translationKeyIn, ForgeConfigSpec.ConfigValue<TextFormatting> configValue) {
		super(translationKeyIn);
		this.configValue = configValue;
	}

	@Override
	public Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn) {
		return new OptionButton(xIn, yIn, widthIn, 20, this, this.getText(), (button) -> {
			if(configValue.get().ordinal() + 1 == TextFormatting.values().length){
				configValue.set(TextFormatting.values()[0]);
			} else {
				configValue.set(TextFormatting.values()[configValue.get().ordinal() + 1]);
			}
			button.setMessage(getText());
		});
	}


	public String getText(){
		return getDisplayString() + configValue.get().toString() + configValue.get().name().toUpperCase(Locale.ROOT);
	}


}
