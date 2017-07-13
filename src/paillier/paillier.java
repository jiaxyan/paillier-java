package paillier;

import java.math.BigInteger;
import prime.prime;
/**
 * 
 * @author charlie
 * ¼ÓÃÜ½âÃÜ²Ù×÷
 */
public class paillier {
	private static final double LOG2 = Math.log(2.0);
	public paillier() {
		
	}
	
	/*
	 * from stackOverflow
	 * Computer the antural logarithm of a BigInteger.
	 * @param: val Argument, positive integer
	 * @return: Natural logarithm, as in Math.log()
	 * 
	 */
	public static double logBigInteger(BigInteger val) {
	    int blex = val.bitLength() - 1022; // any value in 60..1023 is ok
	    if (blex > 0)
	        val = val.shiftRight(blex);
	    double res = Math.log(val.doubleValue());
	    return blex > 0 ? res + blex * LOG2 : res;
	}
	
	public static double log2BigInteger(BigInteger val) {
		return logBigInteger(val)/logBigInteger(BigInteger.valueOf(2));
	}
	
	/*
	 * encryption
	 */
	public static BigInteger encrypt(KeyPair keypair, BigInteger plain) {
		BigInteger cipher, r;
		while(true) {
			
			r = prime.generate_prime((int)( Math.round(log2BigInteger(keypair.getPublic_key().getN())) ), 0, 0);
			if(r.compareTo(BigInteger.valueOf(0))==1 && r.compareTo( keypair.getPublic_key().getN() )==-1)
				break;
		}
		BigInteger x = r.modPow(keypair.getPublic_key().getN(), keypair.getPublic_key().getN_SQ());
		cipher = ( (keypair.getPublic_key().getG().modPow(plain, keypair.getPublic_key().getN_SQ())).multiply(x) ).mod(keypair.getPublic_key().getN_SQ());
		return cipher;
	}
	
	
	/*
	 * decryption
	 */
	public static BigInteger decrypt(KeyPair keypair, BigInteger cipher) {
		BigInteger plain,x;
		x = cipher.modPow(keypair.getPrivate_key().getL(), keypair.getPublic_key().getN_SQ()).subtract(BigInteger.valueOf(1));
		plain = ( ( x.divide(keypair.getPublic_key().getN()) ).multiply(keypair.getPrivate_key().getM()) ).mod(keypair.getPublic_key().getN());
		return plain;
	}
	
	
	/*
	 * Add one encrypted number to another
	 */
	public static BigInteger e_add(KeyPair keypair, BigInteger cry_a, BigInteger cry_b) {
		return ( cry_a.multiply(cry_b) ).mod(keypair.getPublic_key().getN_SQ());
	}
	
	
	/*
	 * Add constant n to an encrypted number
	 */
	public static BigInteger e_add_const(KeyPair keypair, BigInteger cry_a, BigInteger con_n) {
		return (cry_a.multiply( ( keypair.getPublic_key().getG() ).modPow(con_n, keypair.getPublic_key().getN_SQ()) ) ).mod(keypair.getPublic_key().getN_SQ());
	}
	
	
	/* 
	 * Multiply am encrypted number by a constant
	 */
	public static BigInteger e_mul_const(KeyPair keypair, BigInteger cry_a, BigInteger con_n) {
		return cry_a.modPow(con_n, keypair.getPublic_key().getN_SQ());
	}
}
