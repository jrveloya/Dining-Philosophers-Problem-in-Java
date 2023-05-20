package extraCredit;

public interface DiningServer {
	/*
	 * Called by philosopher when they want to eat
	 */
	public void takeForks(int philosopherNumber);
	
	/*
	 * Called by philosopher when they are finished eating 
	 */
	public void returnForks(int philosopherNumber);
	
}
