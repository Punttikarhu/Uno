package domain.kortit;

public class Suunnanvaihtokortti extends Kortti implements Erikoiskortti {
	
	public Suunnanvaihtokortti(String vari) {
		super(vari);
	}

	public String toString() {
		return "[" + this.vari + ", <->]";
	}
	
	public int annaNumero() {
		return 13;
	}
}
