package pausable;

/** An extension to Thread  that provides pause() and resume() methods.
 * 
 */
public class PausableThread extends Thread {
	
	private Object pauseLock;
	private boolean paused;
	

	private Runnable runnable;

	public PausableThread(Runnable runnable) {
		this.runnable = runnable;
		this.pauseLock = new Object();
		this.paused = false;
	}

	@Override
	public void run() {
		for (;;) {

			
		}
	}
}
