package domain.kortit;

public class NostaKaksikortti extends Kortti implements Erikoiskortti {
	
	
	public NostaKaksikortti(String vari) {
		super(vari);
	}

	public String toString() {
		return this.vari + " nosta kaksi -kortti";
	}
}
