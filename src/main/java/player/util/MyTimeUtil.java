package player.util;

import javafx.util.Duration;
/**
 * based on javafx demo.
 * @author sainthxd@gmail.com
 * 
 */
public class MyTimeUtil {
	/**
	 * 格式化时间
	 * @param elapsed 当前时间
	 * @param duration 总时间
	 * @return
	 */
	public static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int)Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int)Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d",
						elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d",
						elapsedMinutes, elapsedSeconds,
						durationMinutes, durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d",
						elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d",
						elapsedMinutes, elapsedSeconds);
			}
		}
	}
	/**
	 * 格式化时间
	 * @param 
	 * @param duration 总时间
	 * @return
	 */
	public static String formatTime(Duration duration) {
		
		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int)Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

			if (durationHours > 0) {
				return String.format("%d:%02d:%02d",
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d",
						durationMinutes, durationSeconds);
			}
		} else {
			return String.format("%02d:%02d",0, 0);
		
		}
	}
	/**
	 * 格式化时间
	 * @param 
	 * @param duration 总时间
	 * @return
	 */
	public static String formatTime(int duration) {
		
		if (duration>0) {
			int intDuration = duration;
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

			if (durationHours > 0) {
				return String.format("%d:%02d:%02d",
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d",
						durationMinutes, durationSeconds);
			}
		} else {
			return String.format("%02d:%02d",0, 0);
		
		}
	}

}
