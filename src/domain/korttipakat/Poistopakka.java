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
}
