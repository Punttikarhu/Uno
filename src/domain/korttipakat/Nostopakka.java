package domain.korttipakat;

import java.util.ArrayList;
import java.util.List;

import domain.kortit.Kortti;

public class Nostopakka {
	
	List<Kortti> kortit;
	
	public Nostopakka() {
		this.kortit = new ArrayList<>();
	}

	public void lisaaKortit(List<Kortti> kortit) {
		this.kortit.addAll(kortit);
	}
	
	public Kortti nostaKortti() {
		try {
			Kortti k = this.kortit.get(0);
			this.kortit.remove(0);
			return k;
		} catch (Exception e) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder("");
		str.append("Nostopakassa on kortit:\n");
		for (Kortti kortti : this.kortit) {
			str.append(kortti + "\n");
		}
		return str.toString();
	}
}
