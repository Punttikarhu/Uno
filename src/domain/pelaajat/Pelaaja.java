package domain.pelaajat;

import java.util.ArrayList;
import java.util.List;

import domain.kortit.Jokerikortti;
import domain.kortit.Kortti;
import domain.kortit.Nosta4Jokerikortti;
import domain.kortit.NostaKaksikortti;
import domain.kortit.Ohituskortti;
import domain.kortit.Peruskortti;
import domain.kortit.Suunnanvaihtokortti;
import domain.korttipakat.Nostopakka;

public abstract class Pelaaja {
	
	private String nimi;
	List<Kortti> kortit;
	
	public Pelaaja(String nimi) {
		this.nimi = nimi;
		this.kortit = new ArrayList<>();
	}
	
	public void lisaaKortti(Kortti k) {
		this.kortit.add(k);
	}
	
	public void tulostaKortit() {
		int i = 0;
		for (Kortti k : this.kortit) {
			System.out.println("(" + i + ")" + " " + k.toString());
			i++;
		}
	}
	
	public List<Kortti> annaKortit() {
		return this.kortit;
	}
	
	public String annaNimi() {
		return this.nimi;
	}
	
	public void nostaKortti(Nostopakka nostopakka) {
		this.kortit.add(nostopakka.nostaKortti());
	}
	
	public void nostaKaksiKorttia(Nostopakka nostopakka) {
		this.nostaKortti(nostopakka);
		this.nostaKortti(nostopakka);
	}
	
	public void nostaNeljaKorttia(Nostopakka nostopakka) {
		this.nostaKortti(nostopakka);
		this.nostaKortti(nostopakka);
		this.nostaKortti(nostopakka);
		this.nostaKortti(nostopakka);
	}
	
	public int laskePisteet() {
		int summa = 0;
		for (Kortti k : this.kortit) {
			if (k instanceof Ohituskortti || k instanceof Suunnanvaihtokortti
					|| k instanceof NostaKaksikortti) {
				summa += 20;
			} else if (k instanceof Jokerikortti || k instanceof Nosta4Jokerikortti) {
				summa += 50;
			} else {
				summa += ((Peruskortti) k).annaNumero();
			}
		}
		return summa;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder("");
		str.append("Pelaajan " + this.nimi + " kortit:\n");
		for (Kortti kortti : this.kortit) {
			str.append(kortti + "\n");
		}
		return str.toString();
	}
}
