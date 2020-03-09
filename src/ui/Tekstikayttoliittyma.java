package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
		System.out.println(" \"lataa\" ladataksesi vanhan pelin tai 'lopeta' lopettaaksesi.");
		System.out.print("> ");
		String input = this.lukija.next();
		
		switch (input) {
		case "lopeta":
			return;
		case "uusi":
			this.uusiPeli();
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
			
			try {
				PrintWriter tiedosto = new PrintWriter(new File("pelitilanne.txt"));
				tiedosto.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			Kortti ensimmainenKortti = poistopakka.annaPaallimmainenKortti();
			
			while (ensimmainenKortti instanceof Erikoiskortti) {
				ensimmainenKortti = nostopakka.nostaKortti();
				poistopakka.lisaaKortti(ensimmainenKortti);
			}
						
			boolean peliKesken = true;
			while (peliKesken) {
			
			Pelaaja peliVuorossa = peli.annaPelaaja(peli.annaSeuraavaksiVuorossa());
//			System.out.println("Pelivuorossa on: " + peliVuorossa.annaNimi() 
//			+ " (Pisteet: " + peliVuorossa.laskePisteet() + ")");
			System.out.println();
			
			
			//ihmispelaaja
			if (peli.annaSeuraavaksiVuorossa() == 0) {
				
				if (peli.annaVari() != null) {
					System.out.println("Laitettava kortti, joka on v�rilt��n " 
				+ peli.annaVari() );
				} else {
					System.out.print("Pakan p��limm�inen kortti on: ");
					System.out.println(poistopakka.annaPaallimmainenKortti());
				}
				peli.paivitaVuoronumero();
				
				boolean korttiPelattu = false;
				while (korttiPelattu == false) {
					System.out.println("Sinulla on k�dess�si kortit:");
					pelaaja.tulostaKortit();
					
					System.out.println("Kirjoita kortin numero pelataksesi kortin tai \"nosta\" nostaaksesi kortin.");
					System.out.print("> ");
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
							System.out.println(peliVuorossa.annaNimi() + " nosti yhden kortin"); //Vaikutti rikkovan jotain? En tiedä varmaksi. Nyt näyttää jo toimivan.
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
			//Tulostaminen tiedostoon
			try {
				
				File tiedosto = new File("pelitilanne.txt");
				OutputStreamWriter kirjoittaja =
			             new OutputStreamWriter(new FileOutputStream(tiedosto), StandardCharsets.UTF_8);
				
				kirjoittaja.append(peli.annaPelaaja(0).toString());
				kirjoittaja.append("\n");
				kirjoittaja.append(peli.annaPelaaja(1).toString());
				kirjoittaja.append("\n");
				kirjoittaja.append(peli.annaPelaaja(2).toString());
				kirjoittaja.append("\n");
				kirjoittaja.append(peli.annaPelaaja(3).toString());
				kirjoittaja.append("\n");
				kirjoittaja.append(nostopakka.toString());
				kirjoittaja.append("\n");
				kirjoittaja.append(poistopakka.toString());
				
				kirjoittaja.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}	
}
