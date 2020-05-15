package xyz.kamefrede.chataccessibility.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import xyz.kamefrede.chataccessibility.config.ConfigHandler;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud {

	@Inject(method = "getPlayerName(Lnet/minecraft/client/network/PlayerListEntry;)Lnet/minecraft/text/Text;", at = @At(value = "RETURN"), cancellable = true)
	private void changePlayerNameColor(PlayerListEntry entry, CallbackInfoReturnable<Text> callbackInfoReturnable){
		Text playerName = callbackInfoReturnable.getReturnValue();
		if(playerName.getStyle().getColor() == null){
			playerName.getStyle().setColor(ConfigHandler.getInstance().getPlayerNameColor());
			callbackInfoReturnable.setReturnValue(playerName);
		}
	}
}
