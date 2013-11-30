package player;
/**
 * @author sainthxd@gmail.com
 */
import javafx.beans.value.ObservableValue;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public interface PlayerListener {
	/**
	 * 开始播放了
	 * @param player
	 */
	void onPlay(MediaPlayer player);
	/**
	 * 准备好播放了（解码出必要信息了）
	 * @param player
	 */
	void onReady(MediaPlayer player);
	/**
	 * 播放完毕了
	 * @param player
	 */
	void onStop(MediaPlayer player);
	/**
	 * 播放进行中（当前播放位置改变时，也就是说几乎每100ms就会调用一次）
	 * @param player
	 */
	void onTiming(ObservableValue<? extends Duration> arg0,Duration arg1, Duration arg2,MediaPlayer player);
	
	void onError(MediaPlayer player);
}
