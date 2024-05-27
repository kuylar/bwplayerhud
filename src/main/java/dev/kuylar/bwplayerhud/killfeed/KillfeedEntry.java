package dev.kuylar.bwplayerhud.killfeed;

import dev.kuylar.bwplayerhud.Utils;
import dev.kuylar.bwplayerhud.regex.BedWarsKillRegexResult;
import net.minecraft.client.Minecraft;

public class KillfeedEntry {
	public long timestamp;
	public BedWarsKillRegexResult entry;
	public final String finalKillString = "§b§lFINAL KILL";

	public KillfeedEntry(BedWarsKillRegexResult entry) {
		this.timestamp = System.currentTimeMillis();
		this.entry = entry;
	}

	public void render(int x, int y, Minecraft mc) {
		// margin
		x -= 4;
		y += 4;
		Utils.drawRect(x, y, x + calculateWidth(mc), y + 16, 0xFF000000);
		mc.fontRendererObj.drawStringWithShadow(entry.getKillerRenderString(), x + 4, y + 4, 0xFFFFFFFF);
		mc.fontRendererObj.drawStringWithShadow(entry.getVictimRenderString(), x + 24 + mc.fontRendererObj.getStringWidth(entry.getKillerRenderString()), y + 4, 0xFFFFFFFF);
		if (entry.isFinal)
			mc.fontRendererObj.drawStringWithShadow(finalKillString, x + 28 + mc.fontRendererObj.getStringWidth(entry.getKillerRenderString()) + mc.fontRendererObj.getStringWidth(entry.getVictimRenderString()), y + 4, 0xFFFFFFFF);
	}

	public int calculateWidth(Minecraft mc) {
		int width = 0;
		// left padding
		width += 4;

		// killer text
		width += mc.fontRendererObj.getStringWidth(entry.getKillerRenderString());
		width += 4;

		// icon
		width += 16;
		width += 4;

		// victim text
		width += mc.fontRendererObj.getStringWidth(entry.getVictimRenderString());

		if (entry.isFinal) {
			width += 4;
			width += mc.fontRendererObj.getStringWidth(finalKillString);
		}
		return width;
	}
}
