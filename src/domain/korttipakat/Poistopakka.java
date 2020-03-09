package domain.korttipakat;

import java.util.ArrayList;
import java.util.List;

import domain.kortit.Kortti;

public class Poistopakka {

	List<Kortti> kortit;
	
	public Poistopakka() {
		this.kortit = new ArrayList<>();
	}
	
	public void lisaaKortti(Kortti k) {
		this.kortit.add(k);
	}
	
	public Kortti annaPaallimmainenKortti() {
		return this.kortit.get(this.kortit.size()-1);
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder("");
		str.append("Poistopakassa olevat kortit:\n");
		for (Kortti kortti : this.kortit) {
			str.append(kortti + "\n");
		}
		return str.toString();
	}
}
