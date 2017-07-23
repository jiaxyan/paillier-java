package benchmark;
import java.math.BigInteger;
import java.util.Random;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import SS.BLS01;
import paillier.*;

public class demo {

	/**
	 * short signature: signature length-160 bits
	 * paillier encryption: plaintext length-1024 bits; 2018 bits;
	 */
	public static void main(String[] args) {
		KeyPair keypair;
		
		System.out.println("Generating keypair...");
		//生成512位密钥对
		keypair = new KeyPair(512);
		
		//加解密示例——5
		System.out.println("\n\ncryption and decryption example of  Number 5, 6");
		BigInteger example_a = BigInteger.valueOf(5);
		BigInteger example_b = BigInteger.valueOf(6);

		BigInteger example_a_encry = paillier.encrypt(keypair, example_a);;
		BigInteger example_b_encry = paillier.encrypt(keypair, example_b);;
		
		System.out.println(" **** cipher_Of_5: \t"+example_a_encry);
		System.out.println(" **** cipher_Of_6: \t"+example_b_encry);
		
		BigInteger example_sum = paillier.e_add(keypair, example_a_encry, example_b_encry);
		System.out.println(" **** Add cipher_a & cipher_b sum: "+paillier.decrypt(keypair, example_sum));
		System.out.println(" **** after dectyption separately: "+paillier.decrypt(keypair, example_a_encry)
		+"\t"+paillier.decrypt(keypair, example_b_encry));

		
		//随机生成一个1024位的大数
		System.out.println("\n\nrandomly generate an Number of 1024 bits");
		BigInteger text = new BigInteger(1024,new Random());
		System.out.println(" **** A_Number_Of_1024bits:\n\t"+text);
		BigInteger cipher = paillier.encrypt(keypair, text);
		System.out.println(" **** cipher_Of_Number1024bits:\n\t"+ cipher);
		System.out.println(" **** bits_length_Of_cipher:\n\t"+cipher.bitLength());
		BigInteger decipher = paillier.decrypt(keypair, cipher);
		System.out.println(" **** decipher_Of_Number1024bits:\n\t"+decipher);
		
		//对1024bit大数进行签名
		System.out.println("\n\nsign on the Number");
		BLS01 bls01 = new BLS01();
		AsymmetricCipherKeyPair AsykeyPair = bls01.keyGen(bls01.setup());
		byte[] signature = bls01.sign(text.toString(), AsykeyPair.getPrivate());
		
		byte[] result = signature;
		
		boolean verify_result = bls01.verify(signature, text.toString(), AsykeyPair.getPublic());
		System.out.println(verify_result);
	}

}
