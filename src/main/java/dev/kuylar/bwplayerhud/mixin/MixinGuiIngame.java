package dev.kuylar.bwplayerhud.mixin;

import dev.kuylar.bwplayerhud.killfeed.KillfeedEntry;
import dev.kuylar.bwplayerhud.killfeed.KillfeedManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngame {
	@Inject(method = "renderGameOverlay", at = @At("TAIL"))
	public void renderGameOverlay(float partialTicks, CallbackInfo ci) {
		// tab list @ line 281
		// getting heads is @ GuiPlayerTabOverlay.class L149 (loads to renderer at L152)
		// Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap(); to get `list` from above
		// MIGHT NOT HAVE SPECTATORS
		// renderBossHealth - move it down a bit
		renderKillFeed();
	}

	private void renderPlayersHud() {

	}

	private void renderKillFeed() {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		int x = scaledResolution.getScaledWidth();
		int y = 0;
		for (KillfeedEntry entry : KillfeedManager.getRenderableEntries()) {
			entry.render(x - entry.calculateWidth(mc), y, mc);
			y += 16 + 2;
		}
	}

}
