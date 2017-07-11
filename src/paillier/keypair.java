package paillier;

import prime.prime;

public class keypair {
	private PaillierPublicKey public_key;

	private int private_key;
	
	public keypair(int bits) {
		int p,q;
		//��������������
		p = prime.generate_prime(bits,8);
		q = prime.generate_prime(bits, 8);
		
		System.out.println("p= "+p);
		System.out.println("q= "+q);
		
		long n = (long)p * (long)q;
		System.out.println(n);
		
		//���ɹ�Կ��˽Կ
		public_key = new PaillierPublicKey(n);
	}
	
}
