package domain.kortit;

public class NostaKaksikortti extends Kortti {
	
	
	public NostaKaksikortti(String vari) {
		super(vari);
	}

	public String toString() {
		return "[" + this.vari + ", +2]";
	}
}
