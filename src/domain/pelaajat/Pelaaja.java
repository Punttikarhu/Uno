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
import domain.kortit.Erikoiskortti;

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
	
	//J‰rjest‰‰ kortit v‰ri ja numero/merkki j‰rjestykseen, 0-9-erikoiskortit
	public void jarjestaKortit() {
		ArrayList<Kortti> cardholder = new ArrayList<Kortti>();
		ArrayList<Kortti> otherholder = new ArrayList<Kortti>();
		Object placeholder = new Object();
		String[] varivalikoima = {"punainen", "sininen", "keltainen", "vihre‰"};
		for(int k = 0; k<4 ;k++) {
			for(int i = 0; i<kortit.size(); i++) {
			try {
				if ((kortit.get(i).annaVari()).equals(varivalikoima[k])) {
					otherholder.add(kortit.get(i));
				}				
			}catch(NullPointerException e) {} //Jokerin null v‰riarvo...
			}
			for(int j = 0; j<10 ;j++) {
				for(int x = 0; x<otherholder.size(); x++) {
					placeholder = otherholder.get(x);
					if(placeholder instanceof Peruskortti) {
						if(((Peruskortti) placeholder).annaNumero()==j) {
							cardholder.add(otherholder.get(x));
						}
					}
				}
			}
			for(int x = 0; x<otherholder.size(); x++) {
				if(otherholder.get(x) instanceof Erikoiskortti) {
					cardholder.add(otherholder.get(x));
				}
			}
			otherholder.clear();
		}
		for(int i = 0; i<kortit.size() ;i++) {
			if(kortit.get(i) instanceof Jokerikortti) {
				cardholder.add(kortit.get(i));
			}
		}
		for(int i = 0; i<kortit.size() ;i++) {
			if(kortit.get(i) instanceof Nosta4Jokerikortti) {
				cardholder.add(kortit.get(i));
			}
		}
		this.kortit = cardholder;
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
