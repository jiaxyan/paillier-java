package benchmark;

public class Double {
	public long encry;
	public long decry;
	
	public Double() {
		this.encry = 0;
		this.decry = 0;
	}
	
	public Double(long encry, long decry) {
		this.encry = encry;
		this.decry = decry;
	}
	
	public Double add(Double dou) {
		this.encry += dou.encry;
		this.decry += dou.decry;
		return this;
	}
}
