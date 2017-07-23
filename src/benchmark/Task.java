package benchmark;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import SS.BLS01;
import TimeCounter.IntervalTime;
import paillier.KeyPair;
import paillier.paillier;

public class Task {
	
	
	
	public static Muti one_encry(int key_bits) {
		
		IntervalTime encry_sign = new IntervalTime();
		IntervalTime si_verify = new IntervalTime();
		IntervalTime si_verify_decry = new IntervalTime();
		KeyPair keypair = new KeyPair(key_bits);
		BigInteger plaintext = new BigInteger(key_bits*2,new Random());
		
		encry_sign.setStartTime();
		BigInteger cipher = paillier.encrypt(keypair, plaintext);
		BLS01 bls01 = new BLS01();
		AsymmetricCipherKeyPair AsykeyPair = bls01.keyGen(bls01.setup());
		byte[] signature = bls01.sign(cipher.toString(), AsykeyPair.getPrivate());
		encry_sign.setEndTime();
		
		//ǩ����֤+����
		si_verify.setStartTime();
		si_verify_decry.setStartTime();
		boolean verify_result = bls01.verify(signature, cipher.toString(), AsykeyPair.getPublic());
		si_verify.setEndTime();
		BigInteger decipher = paillier.decrypt(keypair, cipher);
		si_verify_decry.setEndTime();
//		System.out.println("\t\tʱ��(����)");
//		System.out.println("����+ǩ��\t:"+encry_sign.getIntervalTime());
//		System.out.println("ǩ����֤\t\t:"+si_verify.getIntervalTime());
//		System.out.println("ǩ����֤+����\t:"+si_verify_decry.getIntervalTime());
		
		return new Muti(encry_sign.getIntervalTime(), si_verify.getIntervalTime(), si_verify_decry.getIntervalTime());
	}
	
	
	
	public static Double two_encry(int key_bits) {
		IntervalTime encry = new IntervalTime();
		IntervalTime decry = new IntervalTime();
		
		KeyPair keypair = new KeyPair(key_bits);
		BigInteger plaintext = new BigInteger(key_bits*2,new Random());
		encry.setStartTime();
		BigInteger cipher = paillier.encrypt(keypair, plaintext);
		encry.setEndTime();
		decry.setStartTime();
		BigInteger decipher = paillier.decrypt(keypair, cipher);
		decry.setEndTime();
		
//		System.out.println("����\t\t:"+encry.getIntervalTime());
//		System.out.println("����\t\t:"+decry.getIntervalTime());
//		System.out.println(encry.getIntervalTime()+":\t:"+decry.getIntervalTime());
		return new Double(encry.getIntervalTime(), decry.getIntervalTime());
	}
	
	/*
	 * ����+ǩ��
	 */
	public static void AdvanceTask_encry_sign( KeyPair keypair, BigInteger plaintext, BLS01 bls01, AsymmetricCipherKeyPair AsykeyPair) {
//		System.out.print("1");
		BigInteger cipher = paillier.encrypt(keypair, plaintext);
		byte[] signature = bls01.sign(cipher.toString(), AsykeyPair.getPrivate());
	}
	
	/*
	 * ǩ����֤
	 */
	public static void AdvanceTask_si_verify( KeyPair keypair, BigInteger plaintext, BLS01 bls01, AsymmetricCipherKeyPair AsykeyPair,
			BigInteger cipher, byte[] signature) {
//		System.out.print("1");
//		BigInteger cipher = paillier.encrypt(keypair, plaintext);
//		byte[] signature = bls01.sign(cipher.toString(), AsykeyPair.getPrivate());
		//ǩ����֤+����
		boolean verify_result = bls01.verify(signature, cipher.toString(), AsykeyPair.getPublic());
	}

	/*
	 * ǩ����֤+����
	 */
	public static void AdvanceTask_si_verify_decry( KeyPair keypair, BigInteger plaintext, BLS01 bls01, AsymmetricCipherKeyPair AsykeyPair,
			BigInteger cipher, byte[] signature) {
//		System.out.print("1");
//		BigInteger cipher = paillier.encrypt(keypair, plaintext);
//		byte[] signature = bls01.sign(cipher.toString(), AsykeyPair.getPrivate());
		//ǩ����֤+����s
		boolean verify_result = bls01.verify(signature, cipher.toString(), AsykeyPair.getPublic());
		BigInteger decipher = paillier.decrypt(keypair, cipher);
	}
	
	/*
	 * 6.	Homomorphic operation over two paillier encryption��̬ͬ����ʱ�䣩
	 */
	public static long paillier_calculate_e_add(int key_bits, KeyPair keypair, IntervalTime intervaltime) {
		BigInteger plaintext_A = new BigInteger(key_bits*2,new Random());
		BigInteger plaintext_B = new BigInteger(key_bits*2,new Random());
		BigInteger cipher_A = paillier.encrypt(keypair, plaintext_A);
		BigInteger cipher_B = paillier.encrypt(keypair, plaintext_B);
		intervaltime.setStartTime();
		//̬ͬ������+���ģ�
		paillier.e_add(keypair, cipher_A, cipher_B);
		intervaltime.setEndTime();
		return intervaltime.getIntervalTime();
	}
	
	public static long paillier_calculate_e_add_const(int key_bits, KeyPair keypair, IntervalTime intervaltime) {
		BigInteger plaintext_A = new BigInteger(key_bits*2,new Random());
		BigInteger plaintext_B = new BigInteger(key_bits*2,new Random());
		BigInteger cipher_A = paillier.encrypt(keypair, plaintext_A);
		intervaltime.setStartTime();
		//̬ͬ������+���ģ�
		paillier.e_add_const(keypair, cipher_A, plaintext_B);
		intervaltime.setEndTime();
		return intervaltime.getIntervalTime();
	}
	
	public static long paillier_calculate_e_mul_const(int key_bits, KeyPair keypair, IntervalTime intervaltime) {
		BigInteger plaintext_A = new BigInteger(key_bits*2,new Random());
		BigInteger plaintext_B = new BigInteger(key_bits*2,new Random());
		BigInteger cipher_A = paillier.encrypt(keypair, plaintext_A);
		intervaltime.setStartTime();
		//̬ͬ������*���ģ�
		paillier.e_mul_const(keypair, cipher_A, plaintext_B);
		intervaltime.setEndTime();
		return intervaltime.getIntervalTime();
	}
	
	/*
		public static void AdvanceTask(KeyPair keypair, BigInteger plaintext, BLS01 bls01, AsymmetricCipherKeyPair AsykeyPair) {
		
//		IntervalTime encry_sign = new IntervalTime();
//		IntervalTime si_verify = new IntervalTime();
//		IntervalTime si_verify_decry = new IntervalTime();
//		KeyPair keypair = new KeyPair(512);
//		BigInteger plaintext = new BigInteger(1024,new Random());
		
//		encry_sign.setStartTime();
		BigInteger cipher = paillier.encrypt(keypair, plaintext);
//		BLS01 bls01 = new BLS01();
//		AsymmetricCipherKeyPair AsykeyPair = bls01.keyGen(bls01.setup());
		byte[] signature = bls01.sign(cipher.toString(), AsykeyPair.getPrivate());
//		encry_sign.setEndTime();
		
		//ǩ����֤+����
//		si_verify.setStartTime();
//		si_verify_decry.setStartTime();
		boolean verify_result = bls01.verify(signature, cipher.toString(), AsykeyPair.getPublic());
//		si_verify.setEndTime();
		BigInteger decipher = paillier.decrypt(keypair, cipher);
//		si_verify_decry.setEndTime();

//		System.out.println("����+ǩ��\t:"+encry_sign.getIntervalTime());
//		System.out.println("ǩ����֤\t\t:"+si_verify.getIntervalTime());
//		System.out.println("ǩ����֤+����\t:"+si_verify_decry.getIntervalTime());
		System.out.println("1");
	}
	 */
}
