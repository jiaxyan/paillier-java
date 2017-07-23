package paillier;

import java.math.BigInteger;

import exception.InvModException;

/**
 * 
 * @author charlie
 * 
 * The private key is (\lambda ,\mu ).
 * lambda (p和q等比特长度情况下) =  l = (p-1)(q-1)
 * 
 */
public class PaillierPrivateKey {
	private BigInteger l;
	private BigInteger m;
	
	
	public PaillierPrivateKey(BigInteger p, BigInteger q, BigInteger n) {
		l = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
		m = l.modInverse(n);
//		System.out.println("modeInverse"+m);
//		try {
//			m = l.mo
//			m = invmod( l, n);
//		} catch (InvModException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
//	private long invmod(long l, long n) throws InvModException{
//		if(l == 0)
//			throw new InvModException("==Error Message==:l=0");
//		long r = l;
//		long d = 1;
//		long min_iter = Math.min(n, 1000000);
//		int i = 0;
//		for(; i < min_iter; i++) {
//			System.out.println(i+"  r的值:"+r);
//			d = (( n / r + 1) * d) % n;
//			r = (d * l) % n;
//			if(r==1)
//				break;
//		}
//		if(i==min_iter)
//			throw new InvModException("==Error Message==:l=0");
//		return 0;
//	}
	
	public BigInteger getL() {
		return l;
	}
	
	public BigInteger getM() {
		return m;
	}
	
	
}
