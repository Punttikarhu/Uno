package domain.korttipakat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.kortit.Jokerikortti;
import domain.kortit.Kortti;
import domain.kortit.Nosta4Jokerikortti;
import domain.kortit.NostaKaksikortti;
import domain.kortit.Ohituskortti;
import domain.kortit.Peruskortti;
import domain.kortit.Suunnanvaihtokortti;

public class Korttipakka {
	
	List<Kortti> kortit;
	
	public Korttipakka() {
		this.kortit = new ArrayList<>();
		this.generoiKorttipakka();
		this.sekoita();	
	}
	
	public void generoiKorttipakka() {
		for (int i = 0; i < 4; i++) {
			this.kortit.add(new Jokerikortti(null));
		}
		for (int i = 0; i < 4; i++) {
			this.kortit.add(new Nosta4Jokerikortti(null));
		}
		
		generoiSamaaVariaOlevatKortit("sininen");
		generoiSamaaVariaOlevatKortit("vihreä");	
		generoiSamaaVariaOlevatKortit("punainen");	
		generoiSamaaVariaOlevatKortit("keltainen");	
	}
	
	public void generoiSamaaVariaOlevatKortit(String vari) {
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j < 10; j++) {
				this.kortit.add(new Peruskortti(vari, j));
			}
			this.kortit.add(new NostaKaksikortti(vari));
			this.kortit.add(new Suunnanvaihtokortti(vari));
			this.kortit.add(new Ohituskortti(vari));		
		}
		this.kortit.add(new Peruskortti(vari, 0));
	}
	
	public void sekoita() {
		Collections.shuffle(this.kortit);
	}
	
	public Kortti jaaKortti() {
		Kortti k = this.kortit.get(0);
		this.kortit.remove(0);
		return k;
	}
	
	public List<Kortti> annaKortit() {
		return this.kortit;
	}
}
