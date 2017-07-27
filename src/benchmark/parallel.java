package benchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import org.json.*;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import SS.BLS01;
import TimeCounter.IntervalTime;
import paillier.KeyPair;
import paillier.paillier;

public class parallel {

	/**
	 * @param args[0] key_bits
	 * 		  args[1] parallel_granularity 10
	 * 		  args[2] 
	 */
	public static void main(String[] args) {
		IntervalTime encry_sign = new IntervalTime();
		IntervalTime si_verify = new IntervalTime();
		IntervalTime si_verify_decry = new IntervalTime();
		int countbase = 2000;
		int count = 0;//运行次数
		int key_bits = Integer.parseInt(args[0]);
		int parallel_granularity = Integer.parseInt(args[1]);
		KeyPair keypair = new KeyPair(key_bits);
		BigInteger plaintext = new BigInteger(key_bits*2,new Random());
		BigInteger cipher = paillier.encrypt(keypair, plaintext);
		BLS01 bls01 = new BLS01();
		AsymmetricCipherKeyPair AsykeyPair = bls01.keyGen(bls01.setup());
		byte[] signature = bls01.sign(cipher.toString(), AsykeyPair.getPrivate());
		System.out.println("start to run task 1,2,3 " );
		
		
		/*
		 * 绘图json
		 */
		JSONObject jsonobj_encry_sign = new JSONObject();
		JSONObject jsonobj_si_verify = new JSONObject();
		JSONObject jsonobj_si_verify_decry = new JSONObject();
//		String path_encry_sign = "D:\\paillierExp\\result_encry_sign.json";
//		String path_si_verify = "D:\\paillierExp\\result_si_verify.json";
//		String path_si_verify_decry = "D:\\paillierExp\\result_si_verify_decry.json";
		
		String path_encry_sign = "/home/guadan2001/paillier/result/result_encry_sign.json";
		String path_si_verify = "/home/guadan2001/paillier/result/result_si_verify.json";
		String path_si_verify_decry = "/home/guadan2001/paillier/result/result_si_verify_decry.json";
		
		
		File file_encry_sign = new File(path_encry_sign);
		File file_si_verify = new File(path_si_verify);
		File file_si_verify_decry = new File(path_si_verify_decry);
		if(file_encry_sign.exists())
			file_encry_sign.delete();
		try {
			file_encry_sign.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(file_si_verify.exists())
			file_si_verify.delete();
		try {
			file_si_verify.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(file_si_verify_decry.exists())
			file_si_verify_decry.delete();
		try {
			file_si_verify_decry.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(int core = 8; core < 33; core *= 2) {

			for(int i = 1; i < 6; i++ ) {
				count = countbase * i;
				/*
				 * 加密+签名的forkjoin
				 */
				encry_sign.setStartTime();
				ForkJoinPool forkjoinPool_encry_sign = new ForkJoinPool(core);
				CalculateTask_encry_sign task_encry_sign = new CalculateTask_encry_sign(parallel_granularity, count, keypair, plaintext, bls01, AsykeyPair );
				
				Future<Integer> result_encry_sign = forkjoinPool_encry_sign.submit(task_encry_sign);
				try {
					System.out.println("\nencry_sign(0 is ok)\t"+result_encry_sign.get());
					encry_sign.setEndTime();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonobj_encry_sign.put("encry_sign_avgTime"+count+"Times"+core+"core", encry_sign.getIntervalTime());
				System.out.println("encry_sign_avgTime("+count+"Times)"+core+"core:"+encry_sign.getIntervalTime()+"\n");
				
				/*
				 * 签名验证的forkjoin
				 */
				si_verify.setStartTime();
				ForkJoinPool forkjoinPool_si_verify = new ForkJoinPool(core);
				CalculateTask_si_verify task_si_verify = new CalculateTask_si_verify(parallel_granularity, count, keypair, plaintext, bls01, AsykeyPair,
						cipher, signature);
				Future<Integer> result_si_verify = forkjoinPool_si_verify.submit(task_si_verify);
				try {
					System.out.println("\nsi_verify(0 is ok)\t"+result_si_verify.get());
					si_verify.setEndTime();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonobj_si_verify.put("si_verify_avgTime"+count+"Times"+core+"core", si_verify.getIntervalTime());
				System.out.println("si_verify_avgTime("+count+"Times)"+core+"core:"+si_verify.getIntervalTime()+"\n");
				
				/*
				 * 签名验证+解密forkjoin
				 */
				si_verify_decry.setStartTime();
				ForkJoinPool forkjoinPool_si_verify_decry = new ForkJoinPool(core);
				CalculateTask_si_verify_decry task_si_verify_decry = new CalculateTask_si_verify_decry(parallel_granularity, count, keypair, plaintext, bls01, AsykeyPair,
						cipher, signature);
				Future<Integer> result_si_verify_decry = forkjoinPool_si_verify_decry.submit(task_si_verify_decry);
				try {
					System.out.println("\nsi_verify_decry(0 is ok)\t"+result_si_verify_decry.get());
					si_verify_decry.setEndTime();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonobj_si_verify_decry.put("si_verify_decry_avgTime"+count+"Times"+core+"core", si_verify_decry.getIntervalTime());
				System.out.println("si_verify_decry_avgTime("+count+"Times)"+core+"core:"+si_verify_decry.getIntervalTime()+"\n\n\n");
			}
			
		}
		
		/*
		 * 将json写入文件
		 */
		try {
			System.out.println("jsonobj_encry_sign.toString():\n"+jsonobj_encry_sign.toString()+"\n");
			Writer write1 = new OutputStreamWriter(new FileOutputStream(file_encry_sign), "UTF-8");
            write1.write(jsonobj_encry_sign.toString());
            write1.flush();
            write1.close();
	           
            System.out.println("jsonobj_si_verify.toString():\n"+jsonobj_si_verify.toString()+"\n");
            Writer write2 = new OutputStreamWriter(new FileOutputStream(file_si_verify), "UTF-8");
            write2.write(jsonobj_si_verify.toString());
            write2.flush();
            write2.close();
            
            System.out.println("jsonobj_si_verify_decry.toString():\n"+jsonobj_si_verify_decry.toString()+"\n");
          	Writer write3 = new OutputStreamWriter(new FileOutputStream(file_si_verify_decry), "UTF-8");
            write3.write(jsonobj_si_verify_decry.toString());
            write3.flush();
            write3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}




