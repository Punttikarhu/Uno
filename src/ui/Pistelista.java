package ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class Pistelista {

	private FileInputStream stream;
	private Scanner sc;
	
	public Pistelista() {
		try {
			this.stream = new FileInputStream("pistelista.txt");
			this.sc = new Scanner(stream);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void haeTiedot(String nimi) {
		int riviNum = etsiTeksti(nimi);
		
		if (!(riviNum == -1)) {
			for (int i = 0; i < 2; i++) {
				System.out.println(sc.nextLine());
			}
		} else {
			System.out.println("Kyseistä pelaajaa ei löytynyt listasta");
		}
		
		sc.close();
	}
	
	public void tulostaLista() {
		while(sc.hasNextLine()) {
			System.out.println(sc.nextLine());
		}
	}
	
	public void uusiPelaaja(String nimi) {
		try {
			File file = new File("pistelista.txt");
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write("\n" + "nimi: " + nimi + "\n");
			br.write("pelit: 0" + "\n");
			br.write("voitot: 0" + "\n");
			//br.write("-----");
			
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void lisaaVoitto(String nimi) {
		try {
			while (new Pistelista().etsiTeksti(nimi) == -1) {
				uusiPelaaja(nimi);
			}
			int rivi = etsiTeksti(nimi);
			
			int uudetPelit = Integer.parseInt(sc.nextLine().replaceAll("[\\D]", "")) + 1;
			int uudetVoitot = Integer.parseInt(sc.nextLine().replaceAll("[\\D]", "")) + 1;
			
			File file = new File("pistelista.txt");
			List<String> rivit = Files.readAllLines(file.toPath());
			rivit.set(rivi + 1, "pelit: " + uudetPelit);
			rivit.set(rivi + 2, "voitot: " + uudetVoitot);
			Files.write(file.toPath(), rivit);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sc.close();
	}
	
	public void lisaaHavio(String nimi) {
		try {
			while (new Pistelista().etsiTeksti(nimi) == -1) {
				uusiPelaaja(nimi);
			}
			int rivi = etsiTeksti(nimi);
			
			int uudetPelit = Integer.parseInt(sc.nextLine().replaceAll("[\\D]", "")) + 1;
			
			File file = new File("pistelista.txt");
			List<String> rivit = Files.readAllLines(file.toPath());
			rivit.set(rivi + 1, "pelit: " + uudetPelit);
			Files.write(file.toPath(), rivit);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sc.close();
	}
	
	//etsii ja palauttaa tekstitiedostosta rivin, jolta syötteenä saatu teksti löytyy
	//palauttaa luvun -1, jos tekstiä ei löytynyt
	private int etsiTeksti(String teksti) {
		int riviNum = 0;
		
		while(sc.hasNextLine()) {
			if (sc.nextLine().equals("nimi: " + teksti)) {
				System.out.println("Teksti löytyi riviltä: " + (riviNum + 1));
				return riviNum;
			}
			
			riviNum += 1;
		}
		
		return -1;
	}
}
