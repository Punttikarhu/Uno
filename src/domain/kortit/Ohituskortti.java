package domain.kortit;

public class Ohituskortti extends Kortti implements Erikoiskortti {

	
	public Ohituskortti(String vari) {
		super(vari);
	}

	public String toString() {
		return this.vari + " ohituskortti";
	}
	
}
