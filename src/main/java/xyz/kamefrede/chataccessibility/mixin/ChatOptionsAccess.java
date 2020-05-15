package xyz.kamefrede.chataccessibility.mixin;

import net.minecraft.client.gui.screen.options.ChatOptionsScreen;
import net.minecraft.client.options.Option;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatOptionsScreen.class)
public interface ChatOptionsAccess {
	@Accessor @Mutable
	static Option[] getOPTIONS(){
		throw new RuntimeException("h");
	};

	@Accessor @Mutable
	static void setOPTIONS(Option[] OPTIONS){
		//NO-OP
	}
}
