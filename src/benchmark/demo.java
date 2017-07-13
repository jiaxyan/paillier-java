package benchmark;
import java.math.BigInteger;

import paillier.*;

public class demo {

	public static void main(String[] args) {
		KeyPair keypair;
		
		System.out.println("Generating keypair...");
		keypair = new KeyPair(512);
		
		BigInteger text = BigInteger.valueOf(5);
		
		BigInteger cipher = paillier.encrypt(keypair, text);
		System.out.println("*****"+paillier.decrypt(keypair, cipher));
//		System.out.println(keypair.getPublic_key());
//		System.out.println(keypair.getPrivate_key());
	}

}
