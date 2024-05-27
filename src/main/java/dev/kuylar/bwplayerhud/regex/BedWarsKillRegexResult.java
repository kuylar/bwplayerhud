package dev.kuylar.bwplayerhud.regex;

public class BedWarsKillRegexResult {
	public String killer;
	public String victim;
	public char killerTeam;
	public char victimTeam;
	public int numberFinals;
	public boolean isFinal;

	public BedWarsKillRegexResult(String killer, String victim, int numberFinals, boolean isFinal) {
		this.killer = killer.substring(1);
		this.victim = victim.substring(1);
		this.killerTeam = killer.charAt(0);
		this.victimTeam = victim.charAt(0);
		this.numberFinals = numberFinals;
		this.isFinal = isFinal;
	}

	public String getKillerRenderString() {
		if (numberFinals == 0) {
			return "§" + killerTeam + killer;
		} else {
			return String.format("§%s%s §r§d§l> #%,d", killerTeam, killer, numberFinals);
		}
	}

	public String getVictimRenderString() {
		return "§" + victimTeam + victim;
	}
}
