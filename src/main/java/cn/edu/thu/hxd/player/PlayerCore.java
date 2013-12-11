package cn.edu.thu.hxd.player;
/**
 * @author sainthxd@gmail.com
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class PlayerCore{
	/**
	 * 软件设置，包括音量等设置
	 */
	private Config config;
	/**
	 * 在线设置的信息
	 */
	private OnlineConfig onlineConfig;
	/**
	 * 播放者
	 */
	MediaPlayer mediaPlayer;
	/**
	 * 当前歌曲的内存数据
	 */
	Media media;
	/**
	 * 当前列表
	 */
	private List<String> playList; 
	/**
	 * 记录最后的播放列表和播放记录
	 */
	private LastPlay playLoc;
	/**
	 * 全部列表
	 */
	private PlayLists playLists;

	/**
	 * 这是专门为控制台准备的，启动项目的时候 可以指定一个文件夹爱，作为扫描的文件夹，该文件夹将被加入到“默认”列表中
	 */
	static String scanFile;

	/**
	 * 监听者们
	 */
	List<PlayerListener> listeners=new ArrayList<PlayerListener>();
	
	public PlayerCore() throws IOException, ClassNotFoundException {
		config=Config.diserialize(new File(System.getProperty("user.home")+File.pathSeparator+".jmusic","config"));
		playLists=PlayLists.diserialize(new File(config.getConfigFolder(),"playLists"));
		onlineConfig=OnlineConfig.diserialize(new File(config.getConfigFolder(),"online"));
		playLoc=LastPlay.diserialize(new File(config.getConfigFolder(),"lastPlay"));
	}
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	/**
	 * 前一个列表
	 * @return
	 */
	public void nextList(){
		playList=playLists.nextList();
		playLoc.setPlayList(playLists.getCurrent());
		playLoc.setPlay(-1);
	}
	/**
	 * 后一个列表
	 */
	public void preList(){
		playList=playLists.preList();
		playLoc.setPlayList(playLists.getCurrent());
		playLoc.setPlay(-1);
	}
	
	/**
	 * 后一首歌
	 * @param cycle
	 * @return
	 */
	public boolean nextSong(boolean cycle){
		if(playLoc.getPlay()==playList.size()-1){
			if(cycle){
				playLoc.play=0;		
			}else{
				return false;
			}
		}else{
			playLoc.play++;
		}
		if(mediaPlayer!=null&&mediaPlayer.getStatus().equals(Status.PLAYING)){
			mediaPlayer.stop();
		}
		media=new Media(playList.get(playLoc.play));
		mediaPlayer=new MediaPlayer(media);
		play();

		return true;
	}
	/**
	 * 前一首歌
	 * @param cycle
	 * @return
	 */
	public boolean preSong(boolean cycle){
		if(playLoc.play==0){
			if(cycle){
				playLoc.play= playList.size()-1;
			}else{
				return false;				
			}
		}else{
			playLoc.play--;
		}
		if(mediaPlayer!=null&&mediaPlayer.getStatus().equals(Status.PLAYING)){
			mediaPlayer.stop();
		}
		media=new Media(playList.get(playLoc.play));
		mediaPlayer=new MediaPlayer(media);
		play();
		return true;
	}
	/**
	 * 播放某一首音乐
	 */
	private void play(){
		if(mediaPlayer!=null){
			applyEffect();
			mediaPlayer.play();
			mediaPlayer.setOnReady(new Runnable() {		
				@Override
				public void run() {
					for(PlayerListener listener:listeners){
						listener.onReady(mediaPlayer);
					}
				}
			});
			mediaPlayer.setOnPlaying(new Runnable() {
				@Override
				public void run() {
					for(PlayerListener listener:listeners){
						listener.onPlay(mediaPlayer);
					}
				}
			});
			mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
				@Override
				public void changed(ObservableValue<? extends Duration> arg0,Duration arg1, Duration arg2) {
					for(PlayerListener listener:listeners){
						listener.onTiming(arg0,arg1,arg2,mediaPlayer);
					}
				}
			});
			mediaPlayer.setOnStopped(new Runnable() {
				@Override
				public void run() {
					for(PlayerListener listener:listeners){
						listener.onPlay(mediaPlayer);
					}
				}
			});
			mediaPlayer.setOnError(new Runnable() {
				@Override
				public void run() {
					for(PlayerListener listener:listeners){
						listener.onError(mediaPlayer);
					}
				}
			});
		}
	}
	/**
	 * 让mediaPlayer应用config的设置
	 * @param mediaPlayer2
	 * @param config2
	 */
	private void applyEffect() {
		mediaPlayer.setBalance(config.getBalance());
		ObservableList<EqualizerBand> bands=mediaPlayer.getAudioEqualizer().getBands();
		double[] dd=config.getEqualizrbands();
		for(int i=0;i<dd.length;i++){
			bands.get(i).setGain(dd[i]);
		}
		mediaPlayer.setVolume(config.getVolume());
	}

	/**
	 * 增加一个list到总列表中，注意如果列表名字已经存在，则合并列表
	 * @param dEFAULT
	 * @param musics
	 */
	public void addList(String dEFAULT, List<String> musics) {
		playLists.addList(dEFAULT, musics, playLoc.getPlayList());
	}

	public List<String> getCurrentList(){
		return playList;
	}
	public List<String> getPlayList() {
		return playList;
	}
	
	/**
	 * 保存配置
	 * @throws IOException
	 */
	public void save() throws IOException {
		config.serialize(new File(config.getConfigFolder(),"config"));
		playLists.serialize(new File(config.getConfigFolder(),"playLists"));
		onlineConfig.serialize(new File(config.getConfigFolder(),"online"));
		playLoc.serialize(new File(config.getConfigFolder(),"lastPlay"));
	}
	/**
	 * 播放或者暂停
	 */
	public void playOrPause() {
		if(mediaPlayer!=null&&mediaPlayer.getStatus().equals(Status.PLAYING)){
			mediaPlayer.pause();
		}else if(mediaPlayer==null||mediaPlayer.getStatus().equals(Status.STOPPED)){
			nextSong(true);
		}else if(mediaPlayer!=null&& mediaPlayer.getStatus().equals(Status.PAUSED)){
			mediaPlayer.play();
		}
	}
	/**
	 * 停止播放音乐
	 */
	public void stopPlay() {
		if(mediaPlayer!=null){
			mediaPlayer.stop();
		}
	}
	/**
	 * 增加音量
	 * @param d
	 */
	public void addVolume(double d) {
		config.setVolume(Math.min(1,config.getVolume()+d));
		config.setChanged(true);
		mediaPlayer.setVolume(Math.min(1, mediaPlayer.getVolume()+d));
		System.out.println("\nvolumn:"+mediaPlayer.getVolume());
	}
	/**
	 * 减少音量
	 * @param d >0
	 */
	public void desVolume(double d) {
		config.setVolume(Math.max(0, config.getVolume()-d));
		config.setChanged(true);
		mediaPlayer.setVolume(Math.max(0, mediaPlayer.getVolume()-d));
		System.out.println("\nvolumn:"+mediaPlayer.getVolume());
	}
	/**
	 * 增加左平衡
	 * @param d >0
	 */
	public void desBalance(double d) {
		config.setBalance(Math.max(-1,config.getBalance()-d));
		config.setChanged(true);
		mediaPlayer.setBalance(Math.max(-1,mediaPlayer.getBalance()-d));
		System.out.println("\nbalance:"+mediaPlayer.getBalance());

	}
	/**
	 * 增加右平衡
	 * @param d >0
	 */
	public void addBalance(double d) {
		config.setBalance(Math.min(1,config.getBalance()+d));
		config.setChanged(true);
		mediaPlayer.setBalance(Math.min(1,mediaPlayer.getBalance()+d));
		System.out.println("\nbalance:"+mediaPlayer.getBalance());
	}
	/**
	 * 减少某一个频段的增益
	 * @param hz 0~9
	 * @param d >0
	 */
	public void desEqualizerBand(int hz, double d) {
		EqualizerBand band=mediaPlayer.getAudioEqualizer().getBands().get(hz);
		config.getEqualizrbands()[hz]=(Math.max(EqualizerBand.MIN_GAIN,band.getGain()-d));
		config.setChanged(true);
		band.setGain(Math.max(EqualizerBand.MIN_GAIN,band.getGain()-d));
	}
	/**
	 * 增加某一个频段的增益
	 * @param hz 0~9
	 * @param d >0
	 */
	public void addEqualizerBand(int hz, double d) {
		EqualizerBand band=mediaPlayer.getAudioEqualizer().getBands().get(hz);
		config.getEqualizrbands()[hz]=(Math.max(EqualizerBand.MIN_GAIN,band.getGain()+d));
		config.setChanged(true);
		band.setGain(Math.min(EqualizerBand.MAX_GAIN,band.getGain()+d));
	}
	/**
	 * 将音效设置成默认
	 */
	public void setEffectDefault() {
		config.setEffect(1, 0, new double[12]);
		config.setChanged(true);
		mediaPlayer.setBalance(0);
		ObservableList<EqualizerBand> bands=mediaPlayer.getAudioEqualizer().getBands();
		for(EqualizerBand band:bands){
			band.setGain(0);
		}
		mediaPlayer.setVolume(1);
	}
	
	/**
	 * 后退
	 * @param i >0 毫秒
	 */
	public void back(int i) {
		// mediaPlayer.seek(media.getDuration().multiply( 100.0));
		mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(new Duration(i)));
	}
	/**
	 * 快进
	 * @param i >0 毫秒
	 */
	public void front(int i) {
		mediaPlayer.seek(mediaPlayer.getCurrentTime().add(new Duration(i)));
	}

	public void addListener(PlayerListener l){
		listeners.add(l);
	}
	public boolean removeListener(PlayerListener l){
		return listeners.remove(l);
	}
	public void clearListener(){
		listeners.clear();
	}
	
}


