package domain.kortit;

public class Suunnanvaihtokortti extends Kortti {
	
	public Suunnanvaihtokortti(String vari) {
		super(vari);
	}

	public String toString() {
		return this.vari + " suunnanvaihtokortti";
	}
}
