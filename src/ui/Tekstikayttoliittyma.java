package ui;

import java.util.List;
import java.util.Scanner;

import domain.kortit.Erikoiskortti;
import domain.kortit.Kortti;
import domain.korttipakat.Nostopakka;
import domain.korttipakat.Poistopakka;
import domain.pelaajat.Pelaaja;
import logiikka.Peli;

public class Tekstikayttoliittyma {
	
	Scanner lukija;
	
	public Tekstikayttoliittyma() {
		lukija = new Scanner(System.in);
	}
	
	public void start() {
		
		System.out.println("Tervetuloa pelaamaan Uunoa. Kirjoita \"uusi\" aloittaaksesi uuden pelin,");
		System.out.println(" \"lataa\" ladataksesi vanhan pelin, \"ohjeet\" lukeaksesi peliohjeita tai 'lopeta' lopettaaksesi.");
		System.out.print("> ");
		String input = this.lukija.next();
		
		switch (input) {
		case "lopeta":
			return;
		case "uusi":
			this.uusiPeli();
			break;
		case "lataa":
//			lataaPeli();
			break;
		case "ohjeet":
//			naytaPeliohjeet();
			break;
		default:
			this.uusiPeli();
		}
	}
		
	public void uusiPeli() {
		
			System.out.println("Pelaat Unoa kolmea tietokonepelaajaa vastaan.");
			System.out.println();
			Peli peli = new Peli();
			Pelaaja pelaaja = peli.annaPelaaja(0);
			Poistopakka poistopakka = peli.annaPoistopakka();
			Nostopakka nostopakka = peli.annaNostopakka();
		
			Kortti ensimmainenKortti = poistopakka.annaPaallimmainenKortti();
			
			while (ensimmainenKortti instanceof Erikoiskortti) {
				ensimmainenKortti = nostopakka.nostaKortti();
				poistopakka.lisaaKortti(ensimmainenKortti);
			}
			
//			peli.pelaaKorttiTietokone(lukija, ensimmainenKortti, null, pelaaja);
			
			boolean peliKesken = true;
			while (peliKesken) {
			
			Pelaaja peliVuorossa = peli.annaPelaaja(peli.annaSeuraavaksiVuorossa());
			System.out.println("Pelivuorossa on: " + peliVuorossa.annaNimi() 
			+ " (Pisteet: " + peliVuorossa.laskePisteet() + ")");
			System.out.println();
			
			
			//ihmispelaaja
			if (peli.annaSeuraavaksiVuorossa() == 0) {
				
				if (peli.annaVari() != null) {
					System.out.println("Laitettava kortti, joka on väriltään " 
				+ peli.annaVari() );
				} else {
					System.out.print("Pakan päälimmäinen kortti on: ");
					System.out.println(poistopakka.annaPaallimmainenKortti());
				}
				peli.paivitaVuoronumero();
				
				boolean korttiPelattu = false;
				while (korttiPelattu == false) {
					System.out.println("Sinulla on kädessäsi kortit:");
					pelaaja.tulostaKortit();
					
					System.out.println("Kirjoita kortin numero pelataksesi kortin tai \"nosta\" nostaaksesi kortin.");
					System.out.print(">");
					String input = this.lukija.next();
					List<Kortti> kortit = pelaaja.annaKortit();
					if (input.equals("nosta")) {
						pelaaja.nostaKortti(nostopakka);
						break;
					}
					int kortinNumero = Integer.valueOf(input);
					Kortti k = kortit.get(kortinNumero);
					Kortti edellinenKortti = poistopakka.annaPaallimmainenKortti();
				
				
					korttiPelattu = peli.pelaaKortti(lukija, k, edellinenKortti, pelaaja);
					if (korttiPelattu) {
						poistopakka.lisaaKortti(k);
						kortit.remove(kortinNumero);
					}				
				}
					
			} else { // tietokonepelaaja
				peli.paivitaVuoronumero();
				
				boolean kelvollinenKortti = false;
				int kortinNumero = 0;
				while (kelvollinenKortti == false) {
					
					List<Kortti> kortit = peliVuorossa.annaKortit();
					Kortti k = kortit.get(kortinNumero);
					Kortti edellinenKortti = poistopakka.annaPaallimmainenKortti();
					
					kelvollinenKortti = peli.pelaaKorttiTietokone(lukija, k, edellinenKortti, peliVuorossa);
					
					if (!kelvollinenKortti) {
						if (kortinNumero == peliVuorossa.annaKortit().size()-1) {
							peliVuorossa.nostaKortti(nostopakka); //ei sopivaa korttia
							kelvollinenKortti = true;
						} else {
							kortinNumero++;
						}
					} else {
						poistopakka.lisaaKortti(k);
						kortit.remove(kortinNumero);
					}
				}
				
			}
		}
	}	
}
