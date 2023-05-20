package extraCredit;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers implements DiningServer{
	enum State {EATING, THINKING, HUNGRY};
	Condition[] self = new Condition[5];
	State[] state = new State[5];
	Lock lock = new ReentrantLock();
	
	public DiningPhilosophers() {
		/* 
		 * create condition 
		 */
		for(int i = 0; i < 5; i++) {
			self[i] =  lock.newCondition();
		}
		
		/*
		 * initialize all philosophers to THINKING
		 */
		for(int i = 0; i < 5; i++) {
			state[i] = State.THINKING;
			System.out.println("Philosopher " + i + " is THINKING.");
		}
		
		/*
		 * initialize all threads
		 */
		for(int i = 0; i < 5; i++) {
			Philosopher ph = new Philosopher(i, this);
			Thread thread = new Thread(ph);
			thread.start();
		}
	}
	
	@Override
	public void takeForks(int philosopherNumber) {
		lock.lock();
		
		state[philosopherNumber] = State.HUNGRY;
		/*
		 * check if we can eat, if not, wait.
		 */
		test(philosopherNumber);
		if(state[philosopherNumber] != State.EATING) {
			try {
				self[philosopherNumber].await();
			} catch(InterruptedException e) {
				System.out.println("Philosopher "+ philosopherNumber + " is waiting for forks and unable to eat.");
			}
		}
		
		lock.unlock();
		
		
	}
	@Override
	public void returnForks(int philosopherNumber) {
		lock.lock();
		
		state[philosopherNumber] = State.THINKING;
		System.out.println("Philosopher " + philosopherNumber + " is done EATING.");
		System.out.println("Philosopher " + philosopherNumber + " is now THINKING.");
		// check if left and right neighbors need forks
		test((philosopherNumber + 4) % 5);
		test((philosopherNumber + 1) % 5);
		
		lock.unlock();
	}
	
	public void test(int philosopherNumber) {
		if((state[(philosopherNumber + 4) % 5] != State.EATING) &&
			state[philosopherNumber] == State.HUNGRY &&
			state[(philosopherNumber + 1) % 5] != State.EATING){
			
			state[philosopherNumber] = State.EATING;
			System.out.println("Philosopher " + philosopherNumber + " is EATING.");
			self[philosopherNumber].signal(); // signal other philosopher that a change occured.
			}
	}
	

}
