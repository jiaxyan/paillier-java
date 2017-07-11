package paillier;

/**
 * 
 * @author charlie
 *
 */
public final class PaillierPublicKey {
	private long n;
	private long g;//��p��q�ȳ�������¿�����g=n+1
	private long n_sq;
	
	public PaillierPublicKey(long n) {
		g = n + 1;
		n_sq = n * n;
	}
}
