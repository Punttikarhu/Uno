package domain.kortit;

public abstract class Kortti {
	
	protected String vari;
	protected int numero;
	
	public Kortti(String vari) {
		this.vari = vari;
	}

	public String annaVari() {
		return this.vari;
	}
	
	public int annaNumero() {
		return -1;
	}
	
}
