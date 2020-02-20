package domain.kortit;

public class Ohituskortti extends Kortti {

	
	public Ohituskortti(String vari) {
		super(vari);
	}

	public String toString() {
		return this.vari + " ohituskortti";
	}
	
}
