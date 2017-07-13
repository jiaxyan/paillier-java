package paillier;

import java.math.BigInteger;

/**
 * 
 * @author charlie
 * 
 * ——The public (encryption) key is  (n,g).
 */
public final class PaillierPublicKey {
	private BigInteger n;
	private BigInteger g;//在p和q等长的情况下可以令g=n+1
	private BigInteger n_sq;
	
	public PaillierPublicKey(BigInteger n) {
		this.n = n;
		g = n.add(BigInteger.valueOf(1));
		n_sq = n.multiply(n);
	}
	
	public BigInteger getN() {
		return n;
	}
	
	
	public BigInteger getG() {
		return g;
	}
	
	public BigInteger getN_SQ() {
		return n_sq;
	}
	
	public String toString() {
		return "PubKey:\n\tn:"+ n + "\n\tg:"+g+"\n\tn_sq:"+n_sq;
	}
}
