package extraCredit;

public class Philosopher implements Runnable{
	int philosopherNumber;
	long createTime;
	DiningPhilosophers dp;
	
	public Philosopher(int philosopherNumber, DiningPhilosophers dp) {
		this.philosopherNumber = philosopherNumber;
		this.dp = dp;
		createTime = System.currentTimeMillis();
	}
	
	private void eat() {
		long seconds = 0;
		while(seconds <= 5) {
			long ms = System.currentTimeMillis() - createTime;
			seconds = ms / 1000;
		}
	}
	
	@Override
	public void run() {
		while(true) {
			dp.takeForks(philosopherNumber);
			eat();
			dp.returnForks(philosopherNumber);
		}
		
	}

}
