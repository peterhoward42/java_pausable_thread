package pausable;

/**
 * This class exposes a method OutputTicksForever() that prints an incrementing
 * number to the console in a forever loop. It is designed to be launched in a new
 * thread, and also offers methods that should be called from a different thread to pause
 * and resume the output. This is implemented by the wait() / notifyAll() signalling protocol that is
 * the recommended way to do it, since Thread.Suspend() was deprecated in Java.
 *
 */
public class PausableTickerTape {
	
	private Object SyncLock; // The object we synchronize on, and wait() on while paused.
	private Boolean IsPaused;
	
	public PausableTickerTape() {
		this.SyncLock = new Object();
		this.IsPaused = false;
	}
	
	public void OutputTicksForever() {
		int counter = 0;
		for (;;) {
			synchronized(SyncLock) { // We must acquire the lock if we wish to wait() on it.
				while (IsPaused == true) { // You might expect an "if" statement, but the loop is required to cope with this thread being woken spuriously.
					try {
						SyncLock.wait(); // Releases the lock - by definition and puts the thread to sleep.
						// Dropping through the wait re-acquires the lock - by definition
					} catch (InterruptedException e) {
						throw new RuntimeException("PausableTickerTape was in wait state, but got interrupted");
					}
				}
			} // End of sync block relinquishes the lock by definition.
			System.out.println(counter); // Output one tick and iterate the forever-loop. This the "day job"
			counter++;
		}
	}
	
	/**
	 * This will normally be called by a different thread from the one in which
	 * OutputTicksForever() was called.
	 */
	public void Pause() {
		synchronized(SyncLock) { // We must acquire the lock to safely write to the field.
			IsPaused = true;
			// No notifyAll() is needed for a pause because it does not need to stimulate a wake up
			// from the wait() in the OutputTicksForever() method.
			
		}  // End of sync block relinquishes the lock by definition.
	}
	
	/**
	 * See above.
	 */
	public void Resume() {
		synchronized(SyncLock) {
			IsPaused = false;
			SyncLock.notifyAll(); // Wake up call.
		}
	}
}
