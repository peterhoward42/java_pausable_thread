package pausable;

import java.util.Timer;

public class Main {

	public static void main(String[] args) {
		PausableTickerTape ticker = new PausableTickerTape();
		ticker.startTicking(); // async

		// Pause and resume the ticker based on time 
		for (;;) {
			long secondsInHour = (System.currentTimeMillis() / 1000) % 60;
			int tenSecondSlot = (int) (secondsInHour % 6);
			if (tenSecondSlot % 2 == 0)
				ticker.pause();
			else
				ticker.resume();
		}
	}

}
