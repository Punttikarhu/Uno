package logiikka;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import domain.kortit.Jokerikortti;
import domain.kortit.Kortti;
import domain.kortit.Nosta4Jokerikortti;
import domain.kortit.NostaKaksikortti;
import domain.kortit.Ohituskortti;
import domain.kortit.Peruskortti;
import domain.kortit.Suunnanvaihtokortti;
import domain.korttipakat.Korttipakka;
import domain.korttipakat.Nostopakka;
import domain.korttipakat.Poistopakka;
import domain.pelaajat.Ihmispelaaja;
import domain.pelaajat.Pelaaja;
import domain.pelaajat.Tietokonepelaaja;

public class Peli {

	private List<Pelaaja> pelaajat;
	private Nostopakka nostopakka;
	private Poistopakka poistopakka;
	private String vari;
	private int seuraavaksiVuorossa;
	private boolean suuntaKasvava;
	private Random random;
	
	public Peli() {
		this.pelaajat = new ArrayList<>();
		this.pelaajat.add(new Ihmispelaaja("Pelaaja")); // pelaaja nro 0
		this.pelaajat.add(new Tietokonepelaaja("Tietokone 1"));
		this.pelaajat.add(new Tietokonepelaaja("Tietokone 2"));		
		this.pelaajat.add(new Tietokonepelaaja("Tietokone 3"));

		
		this.nostopakka = new Nostopakka();
		this.poistopakka = new Poistopakka();
		
		Korttipakka korttipakka = new Korttipakka();
		this.jaaKortit(korttipakka);
		this.vari = null;
		this.seuraavaksiVuorossa = 0;
		this.suuntaKasvava = true;
		this.random = new Random();
		}
	
	public void jaaKortit(Korttipakka k) {
		for (Pelaaja p : this.pelaajat) {
			for (int i = 0; i < 7; i++) {
				p.lisaaKortti(k.jaaKortti());
				k.sekoita();
			}
		}
		
		this.poistopakka.lisaaKortti(k.jaaKortti());
		
		this.nostopakka.lisaaKortit(k.annaKortit());	
	}
	
	public Pelaaja annaPelaaja(int numero) {
		return this.pelaajat.get(numero);
	}
	
	public Nostopakka annaNostopakka() {
		return this.nostopakka;
	}
	
	public Poistopakka annaPoistopakka() {
		return this.poistopakka;
	}
	
	public void kasvataVuoronumeroa() {
		if (this.seuraavaksiVuorossa == this.pelaajat.size()-1) {
			this.seuraavaksiVuorossa = 0;
			return;
		}
		this.seuraavaksiVuorossa++;
	}
	
	public void pienennaVuoronumeroa() {
		if (this.seuraavaksiVuorossa == 0) {
			this.seuraavaksiVuorossa = this.pelaajat.size()-1;
			return;
		}
		this.seuraavaksiVuorossa--;
	}
	
	public boolean annaSuuntaKasvava() {
		return this.suuntaKasvava;
	}
	
	public int annaSeuraavaksiVuorossa() {
		return this.seuraavaksiVuorossa;
	}
	
	public String annaVari() {
		return this.vari;
	}
	
	public void paivitaVuoronumero() {
		if (this.annaSuuntaKasvava()) {
			this.kasvataVuoronumeroa();
		} else {
			this.pienennaVuoronumeroa();
		}
	}
	
	public boolean pelaaKortti(Scanner lukija, Kortti kortti, Kortti edellinenKortti, Pelaaja pelaaja) {
			
		boolean kelvollinenKortti = false;
		
		// Edellinen pelaaja on valinnut v�rin
		
		if (this.vari != null) {
			if (kortti.annaVari() != null 
					&& kortti.annaVari().equals(this.vari)) {
				return false;
			}
			this.vari = null;
			return true;
		}
		
		// Jos edellinen kortti on Jokerikortti tai Nosta 4 -jokerikortti,
		//niin kortin pit�� olla annettua v�ri� tai jokerikortti tai Nosta 4 -jokerikortti.
		// Nosta 4 -jokerikortin voi k�ytt�� vain jos pelaajalla ei ole kyseist� v�ri�.
		
		if (edellinenKortti instanceof Jokerikortti || edellinenKortti instanceof Nosta4Jokerikortti) {
			if (kortti.annaVari() != null 
					&& !kortti.annaVari().equals(this.vari) 
					&& !(kortti instanceof Jokerikortti)
					&& !(kortti instanceof Nosta4Jokerikortti)) {
				System.out.println("Sinun tulee laittaa kortti, joka on v�rilt��n " 
						+ this.vari + " tai jokerikortti tai Nosta 4- jokerikortti "
								+ "(vain jos k�dess� ei ole kyseist� v�ri�)");
				return false;
			}
			if (kortti instanceof Nosta4Jokerikortti) {
				boolean pelaajallaSopivanVarinenKortti = false;
				for (Kortti k : pelaaja.annaKortit()) {
					if (k.annaVari() != null &&
							k.annaVari().equals(edellinenKortti.annaVari())) {
						pelaajallaSopivanVarinenKortti = true;
					}
				} 
				if (pelaajallaSopivanVarinenKortti) {
					System.out.println("Et voi k�ytt�� Nosta 4 -korttia, jos sinulla on sopivan v�rinen kortti.");
					return false;
				}
			}
		}
		/*
		 * Jokeri � Kortin ly�nyt pelaaja saa valita seuraavaksi pelattavan v�rin. 
		 * Kortin saa ly�d� milloin tahansa paitsi silloin, kun sen ly�j� joutuu juuri 
		 * ottamaan vastaan Nosta kaksi -korttia tai Nosta 4-jokeri -korttia.
		 */
		if (kortti instanceof Jokerikortti) {
			do{
				System.out.println("Valitse väri (sininen, punainen, keltainen, vihreä):");
				System.out.print(">");
				String vari = lukija.next();
				this.vari = vari;
			}while (!(this.vari.equals("sininen")||this.vari.equals("punainen")||this.vari.equals("keltainen")||this.vari.equals("vihreä")));
			this.paivitaVuoronumero();			
			kelvollinenKortti = true;
		} 
		/*
		 * Nosta 4 -jokeri � Kortin ly�neest� pelaajasta seuraava menett�� vuoronsa 
		 * ja nostaa pakasta nelj� korttia. Lis�ksi kortin ly�nyt pelaaja saa valita 
		 * seuraavaksi pelattavan v�rin. Pelaaja ei saa kuitenaan ly�d� nosta 4-jokeri 
		 * korttia silloin, kun h�nell� on k�dess��n my�s samanv�rinen kortti kuin 
		 * poistopakan p��llimm�inen kortti on. Muulloin kortin voi ly�d� koska vain.
		 */
		
		else if (kortti instanceof Nosta4Jokerikortti) {
			boolean pelaajallaSopivanVarinenKortti = false;
			for (Kortti k : pelaaja.annaKortit()) {
				if (k.annaVari() != null && 
						k.annaVari().equals(edellinenKortti.annaVari())) {
					pelaajallaSopivanVarinenKortti = true;
				}
			} 
			if (pelaajallaSopivanVarinenKortti) {
				System.out.println("Et voi k�ytt�� Nosta 4 -korttia, jos sinulla on sopivan v�rinen kortti.");
				return false;
			}
			this.annaPelaaja(seuraavaksiVuorossa).nostaNeljaKorttia(this.nostopakka);
			do{
				System.out.println("Valitse väri (sininen, punainen, keltainen, vihreä):");
				System.out.print(">");
				String vari = lukija.next();
				this.vari = vari;
			}while (!(this.vari.equals("sininen")||this.vari.equals("punainen")||this.vari.equals("keltainen")||this.vari.equals("vihreä")));
			this.paivitaVuoronumero();
			kelvollinenKortti = true;
		} 
		
		/*
		 * Nosta kaksi � Kortin ly�neen pelaajan j�lkeen seuraavana vuorossa oleva 
		 * menett�� vuoronsa ja joutuu nostamaan pakasta kaksi korttia k�teen. Nosta 
		 * kaksi -kortin saa ly�d� joko samanv�risen kortin tai my�s eriv�risen Nosta 
		 * kaksi -kortin p��lle.
		 */
		
		else if (kortti instanceof NostaKaksikortti) {
			if (!edellinenKortti.annaVari().equals(kortti.annaVari())
					|| edellinenKortti instanceof NostaKaksikortti) {
				System.out.println("Nosta 2 -kortin voi ly�d� samanv�risen kortin p��lle tai"
						+ " toisen nosta 2 -kortin p��lle.");
				return false;
			}
			this.annaPelaaja(seuraavaksiVuorossa).nostaKaksiKorttia(this.nostopakka);
			this.paivitaVuoronumero();
			kelvollinenKortti = true;
		} 
		
		/*
		 * Ohituskortti � Kortin ly�neest� pelaajasta seuraava menett�� vuoronsa. Kortin
		 * saa ly�d� joko samanv�risen kortin tai eriv�risen ohituskortin p��lle.
		 */
		
		else if (kortti instanceof Ohituskortti) {
			if (!edellinenKortti.annaVari().equals(kortti.annaVari())
					|| edellinenKortti instanceof Ohituskortti) {
				System.out.println("Ohituskortin saa ly�d� samanv�risen kortin p��lle tai"
						+ " toisen ohituskortin p��lle.");
				return false;
			}
			this.paivitaVuoronumero();
			kelvollinenKortti = true;
		} 
		
		/*
		 * Suunnanvaihtokortti � Kortti vaihtaa pelaajien vuoroj�rjestyksen suunnan. 
		 * Kortin saa ly�d� joko samanv�risen kortin tai eriv�risen Suunnanvaihtokortin 
		 * p��lle. 
		 */
		
		else if (kortti instanceof Suunnanvaihtokortti) {
			if (!edellinenKortti.annaVari().equals(kortti.annaVari())
					|| edellinenKortti instanceof Suunnanvaihtokortti) {
				System.out.println("Suunnanvaihtokortin saa ly�d� samanv�risen kortin p��lle tai"
						+ " toisen suunnanvaihtokortin p��lle.");
			}
			this.suuntaKasvava = !this.suuntaKasvava;
			paivitaVuoronumero();
			paivitaVuoronumero();
			kelvollinenKortti = true;
		} 
		
		else if (kortti instanceof Peruskortti) {
			if (edellinenKortti.annaVari() != null) {
				if (!kortti.annaVari().equals(edellinenKortti.annaVari())
						&& edellinenKortti instanceof Peruskortti) {
					if (((Peruskortti) kortti).annaNumero() != ((Peruskortti) edellinenKortti).annaNumero()) {
						//Kortti on peruskortti, mutta eriv�rinen ja erinumeroinen kuin edellinen kortti
						System.out.println("Kortin tulee olla samaa v�ri� tai sama numero.");
						return false;		
					}
				}
				if(!kortti.annaVari().equals(edellinenKortti.annaVari())) {
					System.out.println("Kortin tulee olla samaa v�ri� tai sama numero.");
					return false;					
				} else {
					kelvollinenKortti = true;				
				}	
			} 		
		}
		
		this.vari = null;
		return kelvollinenKortti;
	}
	
	public boolean pelaaKorttiTietokone(Scanner lukija, Kortti kortti, Kortti edellinenKortti, Pelaaja pelaaja) {
		
		boolean kelvollinenKortti = false;
		
		// Edellinen pelaaja on valinnut v�rin
		
		if (this.vari != null) {
			if (kortti.annaVari() != null
					&& kortti.annaVari().equals(this.vari)) {
				return false;
			}
			this.vari = null;
			System.out.println(pelaaja.annaNimi() + " pelasi kortin: " + kortti);
			return true;
		}
		
		
		// Jos edellinen kortti on Jokerikortti tai Nosta 4 -jokerikortti,
		//niin kortin pit�� olla annettua v�ri� tai jokerikortti tai Nosta 4 -jokerikortti.
		// Nosta 4 -jokerikortin voi k�ytt�� vain jos pelaajalla ei ole kyseist� v�ri�.
		
		if (edellinenKortti instanceof Jokerikortti || edellinenKortti instanceof Nosta4Jokerikortti) {
			if (kortti.annaVari() != null 
					&& !kortti.annaVari().equals(this.vari) && !(kortti instanceof Jokerikortti)
					&& !(kortti instanceof Nosta4Jokerikortti)) {
				return false;
			}
			if (kortti instanceof Nosta4Jokerikortti) {
				boolean pelaajallaSopivanVarinenKortti = false;
				for (Kortti k : pelaaja.annaKortit()) {
					if (k.annaVari() != null 
							&& k.annaVari().equals(edellinenKortti.annaVari())) {
						pelaajallaSopivanVarinenKortti = true;
					}
				} 
				if (pelaajallaSopivanVarinenKortti) {
					return false;
				}
			}
		}
		/*
		 * Jokeri � Kortin ly�nyt pelaaja saa valita seuraavaksi pelattavan v�rin. 
		 * Kortin saa ly�d� milloin tahansa paitsi silloin, kun sen ly�j� joutuu juuri 
		 * ottamaan vastaan Nosta kaksi -korttia tai Nosta 4-jokeri -korttia.
		 */
		if (kortti instanceof Jokerikortti) {		
			String[] varit = {"sininen", "punainen", "keltainen", "vihre�"}; 
			int varinNumero = this.random.nextInt(4);
			String vari = varit[varinNumero];
			this.vari = vari;
			this.paivitaVuoronumero();			
			kelvollinenKortti = true;
			System.out.println(pelaaja.annaNimi() + " pelasi kortin: " + kortti);
		} 
		/*
		 * Nosta 4 -jokeri � Kortin ly�neest� pelaajasta seuraava menett�� vuoronsa 
		 * ja nostaa pakasta nelj� korttia. Lis�ksi kortin ly�nyt pelaaja saa valita 
		 * seuraavaksi pelattavan v�rin. Pelaaja ei saa kuitenaan ly�d� nosta 4-jokeri 
		 * korttia silloin, kun h�nell� on k�dess��n my�s samanv�rinen kortti kuin 
		 * poistopakan p��llimm�inen kortti on. Muulloin kortin voi ly�d� koska vain.
		 */
		
		else if (kortti instanceof Nosta4Jokerikortti) {
			boolean pelaajallaSopivanVarinenKortti = false;
			for (Kortti k : pelaaja.annaKortit()) {
				if (k.annaVari() != null 
						&& k.annaVari().equals(edellinenKortti.annaVari())) {
					pelaajallaSopivanVarinenKortti = true;
				}
			} 
			if (pelaajallaSopivanVarinenKortti) {
				System.out.println("Et voi k�ytt�� Nosta 4 -korttia, jos sinulla on sopivan v�rinen kortti.");
				return false;
			}
			this.annaPelaaja(seuraavaksiVuorossa).nostaNeljaKorttia(this.nostopakka);
			String[] varit = {"sininen", "punainen", "keltainen", "vihre�"};
			int varinNumero = this.random.nextInt(4);
			String vari = varit[varinNumero];
			this.vari = vari;
			this.paivitaVuoronumero();
			kelvollinenKortti = true;
			System.out.println(pelaaja.annaNimi() + " pelasi kortin: " + kortti);
		} 
		
		/*
		 * Nosta kaksi � Kortin ly�neen pelaajan j�lkeen seuraavana vuorossa oleva 
		 * menett�� vuoronsa ja joutuu nostamaan pakasta kaksi korttia k�teen. Nosta 
		 * kaksi -kortin saa ly�d� joko samanv�risen kortin tai my�s eriv�risen Nosta 
		 * kaksi -kortin p��lle.
		 */
		
		else if (kortti instanceof NostaKaksikortti) {
			if (!edellinenKortti.annaVari().equals(kortti.annaVari())
					|| edellinenKortti instanceof NostaKaksikortti) {
				return false;
			}
			this.annaPelaaja(seuraavaksiVuorossa).nostaKaksiKorttia(this.nostopakka);
			this.paivitaVuoronumero();
			kelvollinenKortti = true;
			System.out.println(pelaaja.annaNimi() + " pelasi kortin: " + kortti);
		} 
		
		/*
		 * Ohituskortti � Kortin ly�neest� pelaajasta seuraava menett�� vuoronsa. Kortin
		 * saa ly�d� joko samanv�risen kortin tai eriv�risen ohituskortin p��lle.
		 */
		
		else if (kortti instanceof Ohituskortti) {
			if (!edellinenKortti.annaVari().equals(kortti.annaVari())
					|| edellinenKortti instanceof Ohituskortti) {
				return false;
			}
			this.paivitaVuoronumero();
			kelvollinenKortti = true;
			System.out.println(pelaaja.annaNimi() + " pelasi kortin: " + kortti);
		} 
		
		/*
		 * Suunnanvaihtokortti � Kortti vaihtaa pelaajien vuoroj�rjestyksen suunnan. 
		 * Kortin saa ly�d� joko samanv�risen kortin tai eriv�risen Suunnanvaihtokortin 
		 * p��lle. 
		 */
		
		else if (kortti instanceof Suunnanvaihtokortti) {
			if (!edellinenKortti.annaVari().equals(kortti.annaVari())
					|| edellinenKortti instanceof Suunnanvaihtokortti) {
			}
			this.suuntaKasvava = !this.suuntaKasvava;
			paivitaVuoronumero();
			paivitaVuoronumero();
			kelvollinenKortti = true; 
			System.out.println(pelaaja.annaNimi() + " pelasi kortin: " + kortti);
		} 
		
		else if (kortti instanceof Peruskortti) {
			if (edellinenKortti.annaVari() != null) {
				if (!kortti.annaVari().equals(edellinenKortti.annaVari()) 
						&& edellinenKortti instanceof Peruskortti) {
					if (((Peruskortti) kortti).annaNumero() != ((Peruskortti) edellinenKortti).annaNumero()) {
						//Kortti on peruskortti, mutta eriv�rinen ja erinumeroinen kuin edellinen kortti
						return false;		
					}
				}
				if(!kortti.annaVari().equals(edellinenKortti.annaVari())) {
					return false;					
				}
			} else {
				kelvollinenKortti = true;
				System.out.println(pelaaja.annaNimi() + " pelasi kortin: " + kortti);				
			} 
		}
		
		this.vari = null;
		return kelvollinenKortti;
	}

}
