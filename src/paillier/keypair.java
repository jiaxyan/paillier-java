package paillier;
import prime.prime;

public class keypair {
	private int public_key;

	private int private_key;
	
	public keypair(int bits) {
		int p,q;
		p = prime.generate_prime(bits,8);
		
	}
	
	public int getPublic_key() {
		return public_key;
	}
	public void setPublic_key(int public_key) {
		this.public_key = public_key;
	}
	public int getPrivate_key() {
		return private_key;
	}
	public void setPrivate_key(int private_key) {
		this.private_key = private_key;
	}
}
