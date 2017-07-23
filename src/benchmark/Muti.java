package benchmark;

public class Muti {
	public long encry_sign;
	public long si_verify;
	public long si_verify_decry;
	
	public Muti() {
		this.encry_sign = 0;
		this.si_verify = 0;
		this.si_verify_decry = 0;
	}
	
	public Muti(long encry_sign, long si_verify, long si_verify_decry) {
		this.encry_sign = encry_sign;
		this.si_verify = si_verify;
		this.si_verify_decry = si_verify_decry;
	}
	
	public Muti add(Muti muti) {
		this.encry_sign += muti.encry_sign;
		this.si_verify += muti.si_verify;
		this.si_verify_decry += muti.si_verify_decry;
		return this;
	}
}
