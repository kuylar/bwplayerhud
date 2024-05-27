package dev.kuylar.bwplayerhud.mixin;

import dev.kuylar.bwplayerhud.killfeed.KillfeedEntry;
import dev.kuylar.bwplayerhud.killfeed.KillfeedManager;
import dev.kuylar.bwplayerhud.regex.BedWarsKillRegexResult;
import dev.kuylar.bwplayerhud.regex.Regexes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
	@Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
	public void handleChat(S02PacketChat packetIn, CallbackInfo ci) {
		IChatComponent message = ForgeEventFactory.onClientChat(packetIn.getType(), packetIn.getChatComponent());
		if (message != null) {
			if (packetIn.getType() != 2) {
				System.out.println("Chat Message: " + message.getFormattedText());
				BedWarsKillRegexResult result = Regexes.matchKill(message.getFormattedText());
				if (result != null) {
					KillfeedManager.addEntry(new KillfeedEntry(result));
					ci.cancel();
				}
			}
		}
	}
}
