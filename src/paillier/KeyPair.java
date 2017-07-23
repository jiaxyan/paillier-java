package paillier;

import java.math.BigInteger;

import prime.prime;

public class KeyPair {
	private PaillierPublicKey public_key;
	private PaillierPrivateKey private_key;
	
	public KeyPair(int bits) {
		BigInteger p,q;
		//生成两个大素数,
		p = prime.generate_prime(bits,0,0);
		q = prime.generate_prime(bits,0,0);
		
//		System.out.println(" **** p= \t"+p);
//		System.out.println(" **** q= \t"+q);
		//大素数乘积n
		BigInteger n = p.multiply(q);
		
//		System.out.println("n:\t\t"+n);
//		System.out.println("sub:"+n.doubleValue().);
		
		//生成公钥和私钥
		public_key = new PaillierPublicKey(n);
		private_key = new PaillierPrivateKey(p, q, n);
		
	}

	public PaillierPublicKey getPublic_key() {
		return public_key;
	}


	public PaillierPrivateKey getPrivate_key() {
		return private_key;
	}
	
}
