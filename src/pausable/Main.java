package pausable;

/**
 * A program to show how the demonstration class PausableTickerTape,
 * can be used to implement perpetual activity in a separate thread,
 * which can be paused and resumed robustly by the original thread.
 *
 */
public class Main {

	public static void main(String[] args) {
		final PausableTickerTape ticker = new PausableTickerTape();
		new Thread(new Runnable() {
			public void run() {
				ticker.OutputTicksForever();
			}
		}).start();

		// Pause and resume the ticker every <N> seconds
		final long n = 5;
		for (;;) {
			long oddEvenToggle = (System.currentTimeMillis() / 1000L) / n;
			
			if (oddEvenToggle % 2 == 0)
				ticker.Pause();
			else
				ticker.Resume();
		}
	}
}
