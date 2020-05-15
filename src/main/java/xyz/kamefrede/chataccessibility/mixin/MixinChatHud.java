package xyz.kamefrede.chataccessibility.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import xyz.kamefrede.chataccessibility.config.ConfigHandler;

@Mixin(ChatHud.class)
public class MixinChatHud {

	@ModifyArg(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;IIZ)V"))
	private Text changeColor(Text text){
		Text newText = new LiteralText("");
		text.forEach(el -> {
			if(el.getStyle().getHoverEvent() != null && el.getStyle().getHoverEvent().getAction().equals(HoverEvent.Action.SHOW_ENTITY)) {
				if(el.getStyle().getColor() == null) {
					el.getStyle().setColor(ConfigHandler.getInstance().getPlayerNameColor());
				}
			} else {
				if(el.getStyle().getColor() == null && !(el.getString().trim().equals("<") || el.getString().trim().equals(">"))) {
					el.getStyle().setColor(ConfigHandler.getInstance().getChatColor());
				}
			}
			newText.append(el);
		});
		return newText;
	}
}
