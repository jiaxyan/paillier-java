package TimeCounter;

public class IntervalTime {
	private long startTime;
	private long endTime;
	
	
	public void setStartTime() {
		this.startTime = System.nanoTime();
	}
	
	public void setEndTime() {
		this.endTime = System.nanoTime();
	}
	
	/*
	 * get task run time
	 */
	public long getIntervalTime() {
		return (endTime - startTime) / 1000;
	}
}
