package domain.kortit;

public abstract class Kortti {
	
	protected String vari;
	
	public Kortti(String vari) {
		this.vari = vari;
	}

	public String annaVari() {
		return this.vari;
	}
}
