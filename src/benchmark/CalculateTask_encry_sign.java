package benchmark;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import SS.BLS01;
import paillier.KeyPair;

public class CalculateTask_encry_sign extends RecursiveTask<Integer>{

//	private File file;
	private int Times;//总任务数量（次数）
	public static final int threshold = 10;//每次至多跑的任务数量（次数）
	private KeyPair keypair;
	private BigInteger plaintext;
	private AsymmetricCipherKeyPair AsykeyPair;
	private BLS01 bls01;
	
	
	public CalculateTask_encry_sign(int Times,KeyPair keypair, BigInteger plaintext, BLS01 bls01, AsymmetricCipherKeyPair AsykeyPair) {
		// TODO Auto-generated constructor stub
		this.Times = Times;
		this.keypair = keypair;
		this.plaintext = plaintext;
		this.bls01 = bls01;
		this.AsykeyPair = AsykeyPair;
	}
	
	@Override
	protected Integer compute() {
		boolean canCompute = Times < threshold;
		if(canCompute) {
			for(int i = 0; i < Times; i++) {
				Task.AdvanceTask_encry_sign(keypair, plaintext, bls01, AsykeyPair);
			}
		}else {
			int middle = Times / 2;
			CalculateTask_encry_sign taskLeft = new CalculateTask_encry_sign( middle , keypair,  plaintext, bls01, AsykeyPair);
			CalculateTask_encry_sign taskRight = new CalculateTask_encry_sign(Times - middle, keypair,  plaintext, bls01,AsykeyPair);
			
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
