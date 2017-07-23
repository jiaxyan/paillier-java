package benchmark;

import java.util.List;
import java.util.ArrayList;

import TimeCounter.IntervalTime;
import paillier.KeyPair;

public class sequential {

	public static void main(String[] args) {
		
		Muti muti = new Muti();
		Double dou = new Double();
		long time_e_add  = 0;
		long time_e_add_const = 0;
		long time_e_mul_const = 0;
		int key_bits = 512;
		KeyPair keypair = new KeyPair(key_bits);
		//����
		int count = 100;
		for(int i = 0; i < count; i++) {
			System.out.print("=");
		}
		System.out.println();
		for(int i = 0; i < count; i++) {
			System.out.print("-");
			muti.add(Task.one_encry(key_bits));
			dou.add(Task.two_encry(key_bits));
			time_e_add += Task.paillier_calculate_e_add(key_bits, keypair, new IntervalTime());
//			System.out.println(time_e_add);
			time_e_add_const += Task.paillier_calculate_e_add_const(key_bits, keypair, new IntervalTime());
			time_e_mul_const += Task.paillier_calculate_e_mul_const(key_bits, keypair, new IntervalTime());
		}
		System.out.println();
		System.out.println("\t\tʱ��(΢��)"+count+"��ƽ��");
		System.out.println("����+ǩ��\t"+muti.encry_sign/count);
		System.out.println("ǩ����֤\t\t"+muti.si_verify/count);
		System.out.println("ǩ����֤+����\t"+muti.si_verify_decry/count);
		System.out.println("����\t\t"+dou.decry/count);
		System.out.println("����\t\t"+dou.encry/count);
		System.out.println("̬ͬ����\t\t"+time_e_add/count+"���������)");
		System.out.println("̬ͬ����\t\t"+time_e_add_const/count+"(���ļ�����)");
		System.out.println("̬ͬ����\t\t"+time_e_mul_const/count+"(���ĳ�����)");
	}
}
