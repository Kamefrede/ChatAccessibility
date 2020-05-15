package xyz.kamefrede.chataccessibility;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.util.Formatting;

import java.util.Locale;
import java.util.function.Supplier;
import xyz.kamefrede.chataccessibility.config.ConfigHandler;

public class AccessibilityCyclingOption extends Option {
	public static final AccessibilityCyclingOption CHAT_COLOR_OPTION = new AccessibilityCyclingOption("chataccessibility.options.chat_color", () ->  ConfigHandler.getInstance().cycleChatColor(), () -> ConfigHandler.getInstance().getChatColor());
	public static final AccessibilityCyclingOption NAME_COLOR_OPTION = new AccessibilityCyclingOption("chataccessibility.options.name_color", () -> ConfigHandler.getInstance().cycleNameColor(), () -> ConfigHandler.getInstance().getPlayerNameColor());
	protected final Runnable setter;
	protected final Supplier<Formatting> getter;


	public AccessibilityCyclingOption(String key, Runnable setter, Supplier<Formatting> getter) {
		super(key);
		this.setter = setter;
		this.getter = getter;
	}

	@Override
	public AbstractButtonWidget createButton(GameOptions options, int x, int y, int width) {
		return new OptionButtonWidget(x, y, width, 20, this, this.getText(), (button) -> {
			setter.run();
			button.setMessage(getText());
		});
	}

	public String getText(){
		return getDisplayPrefix() + getter.get().toString() + getter.get().name().toUpperCase(Locale.ROOT);
	}

}
