package domain.kortit;

public class Peruskortti extends Kortti {
	
	private int numero;

	public Peruskortti(String vari, int numero) {
		super(vari);
		this.numero = numero;
	}
	
	public int annaNumero() {
		return this.numero;
	}
	
	public String toString() {
		return this.vari + " peruskortti numero " + this.numero;
	}
	

}
