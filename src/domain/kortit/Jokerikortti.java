package domain.kortit;

public class Jokerikortti extends Kortti implements Erikoiskortti {
	
	public Jokerikortti(String vari) {
		super(vari);
	}

	public String toString() {
		return "[Jokeri, väri]";
	}

}
