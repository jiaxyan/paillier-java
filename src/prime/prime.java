package prime;
import java.util.Random;

import javax.swing.text.StyleContext.SmallAttributeSet;

/**
 * 
 * @author charlie
 * 
 */
public class prime {
	private static int[] smallprimes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,
            47,53,59,61,67,71,73,79,83,89,97};
	
	public prime() {
		
	}
	
	
//	private static void ipow(int test, int possible, int possible_1) {
//		long Test = test = test % possible_1;
//	}
	
	/*
	 * 返回true——possible肯定不是prime
	 * 返回false——可能是prime
	 */
	private static boolean rabin_miller_witness(int test, int possi) {
		int possi_1 = possi -1;
		long Test = test = test % possi;
		if(Test==1)
			return false;
		long tempT = 1L;
		while(tempT <= possi_1)
			tempT <<= 1;
		tempT >>=2;
		
		while(tempT>0) {
			Test = (Test*Test) % possi;
			if( (tempT&possi_1) > 0 )
				Test = (Test*test)%possi;
			if(Test == 1)
				return false;
			tempT >>= 1;
		}
		
		return true;
	}
	
	private static boolean is_probably_prime(int possible, int k) {
		if(possible==1)
			return true;
		if(k==0) {
//			k = default_k(possible);
			System.out.println("Error:prime.java.private static boolean is_probabaly_prime");
		}
		
		for(int i : smallprimes) {
			if(possible == i)
				return true;
			if(possible % i == 0)
				return false;
		}
		
		int min_bound = 2;
		int max_bound = possible-1;
		for(int i = 0; i < k; i++) {
			Random random = new Random();
			int test = random.nextInt(max_bound-min_bound)+min_bound;
			test = test | 1;
			if(rabin_miller_witness(test, possible))
				return false;
		}
		return true;
	}
	
	private static int default_k(int bits) {
		return Math.max(40,2*bits);
	}
	
	/*
	 * 产生bits位的素数（在k轮循环之后）
	 */
	public static int generate_prime(int bits, int k) {
		assert bits>8;
		
		if(k==0)
			k = default_k(bits);
		
		int possible = 0;
		int min_bound = (int) Math.pow(2, (bits-1))+1;
		int max_bound = (int) Math.pow(2, bits);
		
//		System.out.println("bound:"+(max_bound-min_bound));
		
		while(true){
			Random random = new Random();
			possible = random.nextInt(max_bound-min_bound)+min_bound;
			possible = possible | 1;
			if(is_probably_prime(possible,k))
				return possible;
		}
	}
}
