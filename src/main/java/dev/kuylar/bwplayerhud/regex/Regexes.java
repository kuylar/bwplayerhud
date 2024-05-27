package dev.kuylar.bwplayerhud.regex;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexes {
	// i, too, hate this regex
	private static final Pattern bwKillPattern = Pattern.compile("^§r§(?<victim>[§\\w]+?) ?§r§7(?:'s)? ?(?:was|met|lost|died|fought|fell|stumbled|tangoed|slipped|howled|caught|played|hit|got|stepped|squeaked|be|took|tripped|had|forgot|didn't|heart).+?§r§(?<killer>\\w+?)§r");
	private static final Pattern bwFinalAnnouncePattern = Pattern.compile("§r§e#([\\d.,]+)§r");
	private static final Pattern bwDeathPattern = Pattern.compile("^§r§(?<victim>[§\\w]+?) §r§7(fell|died)");

	@Nullable
	public static BedWarsKillRegexResult matchKill(String chatMessage) {
		Matcher normalMatch = bwKillPattern.matcher(chatMessage);
		boolean isFinal = chatMessage.contains("FINAL KILL");
		if (normalMatch.find()) {
			int finalCount = 0;
			Matcher finalCountMatcher = bwFinalAnnouncePattern.matcher(chatMessage);
			if (finalCountMatcher.find())
				finalCount = Integer.parseInt(finalCountMatcher.group(1).replace(",", ""));
			return new BedWarsKillRegexResult(normalMatch.group("killer"), normalMatch.group("victim"), finalCount, isFinal);
		} else {
			Matcher deathMatch = bwDeathPattern.matcher(chatMessage);
			if (deathMatch.find()) {
				String killer;
				switch (deathMatch.group(2)) {
					case "died":
						killer = "!SPECIAL;death";
						break;
					case "fell":
						killer = "!SPECIAL;void";
						break;
					default:
						killer = "!SPECIAL;unknown";
						break;
				}
				return new BedWarsKillRegexResult(killer, deathMatch.group("victim"), 0, isFinal);
			} else if (chatMessage.contains("!kill ")) {
				try {
					String[] args = chatMessage.substring(chatMessage.indexOf("!kill ") + 6).split(" ");
					int numberFinals = args.length > 3 ? Integer.parseInt(args[3].replace("§r", "")) : 0;
					return new BedWarsKillRegexResult(args[0], args[1], numberFinals, args.length > 2);
				} catch (Exception e) {
					Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("you blithering idiot"));
					Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(e.getMessage()));
				}
			}
			return null;
		}
	}
}
