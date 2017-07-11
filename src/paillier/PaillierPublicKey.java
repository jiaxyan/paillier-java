package paillier;

/**
 * 
 * @author charlie
 *
 */
public final class PaillierPublicKey {
	private long n;
	private long g;//在p和q等长的情况下可以令g=n+1
	private long n_sq;
	
	public PaillierPublicKey(long n) {
		g = n + 1;
		n_sq = n * n;
	}
}
