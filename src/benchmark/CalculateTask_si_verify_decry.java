package benchmark;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import SS.BLS01;
import paillier.KeyPair;

public class CalculateTask_si_verify_decry extends RecursiveTask<Integer>{

//	private File file;
	private int Times;//总任务数量（次数）
	public static final int threshold = 10;//每次至多跑的任务数量（次数）
	private KeyPair keypair;
	private BigInteger plaintext;
	private AsymmetricCipherKeyPair AsykeyPair;
	private BLS01 bls01;
	private BigInteger cipher;
	private byte[] signature;
	
	
	public CalculateTask_si_verify_decry(int Times,KeyPair keypair, BigInteger plaintext, BLS01 bls01, AsymmetricCipherKeyPair AsykeyPair,
			BigInteger cipher, byte[] signature) {
		// TODO Auto-generated constructor stub
		this.Times = Times;
		this.keypair = keypair;
		this.plaintext = plaintext;
		this.bls01 = bls01;
		this.AsykeyPair = AsykeyPair;
		this.cipher = cipher;
		this.signature = signature;
	}
	
	@Override
	protected Integer compute() {
		boolean canCompute = Times < threshold;
		if(canCompute) {
			for(int i = 0; i < Times; i++) {
				Task.AdvanceTask_si_verify_decry(keypair, plaintext, bls01, AsykeyPair, cipher, signature);
			}
		}else {
			int middle = Times / 2;
			CalculateTask_si_verify_decry taskLeft = new CalculateTask_si_verify_decry( middle , keypair,  plaintext, bls01, AsykeyPair, cipher, signature);
			CalculateTask_si_verify_decry taskRight = new CalculateTask_si_verify_decry(Times - middle, keypair,  plaintext, bls01,AsykeyPair,cipher, signature);
			
			taskLeft.fork();
			taskRight.fork();
			int left = taskLeft.join();
			int right = taskRight.join();
//			time_total.add(sumLeft);
//			time_total.add(sumRight);
		}
		return 0;
	}
	
}
