package player.console;
/**
 * @author sainthxd@gmail.com
 */
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
import javafx.util.Duration;
import player.Config;
import player.OnlineConfig;
import player.PlayLists;
import player.PlayerCore;
import player.PlayerListener;
import player.util.FileUtil;
import player.util.MyTimeUtil;

public class ConsolePlayer extends Application implements PlayerListener{

	/**
	 * 这是专门为控制台准备的，启动项目的时候 可以指定一个文件夹爱，作为扫描的文件夹，该文件夹将被加入到“默认”列表中
	 */
	static String scanFile;

	
	public static void main(String[] args) {
		if(args.length>=1){
			scanFile=args[0];
		}
		launch(args);
	}
	PlayerCore player;


	@Override
	public void start(Stage primaryStage) throws Exception {
		player=new PlayerCore();
		Runtime run=Runtime.getRuntime();//当前 Java 应用程序相关的运行时对象。  
        run.addShutdownHook(new Thread(){ //注册新的虚拟机来关闭钩子  
            @Override  
            public void run() {  
                //程序结束时进行的操作  
                try {
                	player.save();
                } catch (IOException e) {
					e.printStackTrace();
				}
            }  
        });
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            public void uncaughtException(Thread t, Throwable e)
            {
              System.err.println("some thing error...:( . will quit");
            //程序结束时进行的操作  
              try {
              	player.save();
              } catch (IOException ee) {
					ee.printStackTrace();
				}

              System.exit(1);
            }
        });
		
		if(scanFile!=null){
			File folder=new File(scanFile);
			player.addList(PlayLists.DEFAULT, FileUtil.getMusics(folder));
		}
		
		player.nextList();
		if(player.getCurrentList().size()==0){
			System.err.println("no songs. will quit...");
			System.exit(0);
		}
		 
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				char in;
				int hz=0;//32hz
				while (true) {
					InputStream inputStream=System.in;
					try {
						in=(char) inputStream.read();
						if( in=='h'){//help
							System.out.println("\n"
									+"p\tplay or pause 播放或者暂停\n"
									+"s\t stop停止\n" +
									"n\t next song下一首\n" +
									"b\t pre song上一首\n" +
									"=\t increase volume增大音量\n" +
									"-\t reduce volume减少音量\n" +
									",\t left balance左平衡\n" +
									".\t right balance右平衡\n" +
									"0~9\t set the equalizer Hz设置均衡器频段\n" +
									";\t reduce equalizer降低该频段的均衡器\n" +
									"'\t increase equalizer增加该频段的均衡器\n" +
									"d\t set defalut(volume balance equalizer)设置为默认播放效果（音量 平衡 均衡器）\n" +
									"\\\t next 5 seconds 前进5秒\n" +
									"/\t pre 5 seconds 后退5秒\n" +
									"q\t quit 退出\n" +
									"z\t pre playlist上一个播放列表\n" +
									"x\t next playlist下一个播放列表（目前没提供命令行创建播放列表:)\n" +
									"if you use java -jar *.jar folderPath_or_filePath, program will add a playlist for all the songs in this folder.(启动时添加一个文件夹参数或者文件参数，将自动扫描这个文件夹下的所有音乐)\n" +
									"for example: java -jar jmusic.jar /User/hxd/Music 比如： java -jar jmusic.jar e:/users/hxd/musics");
						}
						if (in=='p'){//播放暂停
							player.playOrPause();
						}else if(in=='s'){//停止
							player.stopPlay();
						}else if (in=='n'){//下一首
							player.nextSong(true);
						}else if (in=='b'){//上一首
							player.preSong(true);
						}else if(in=='='){//增加音量
							player.addVolume(0.1);
						}else if(in=='-'){//减少音量
							player.desVolume(0.1);
						}else if(in==','){//左平衡
							player.desBalance(0.1);
						}else if(in=='.'){//右平衡
							player.addBalance(0.1);
						}else if(Character.isDigit(in)){//选择频域
							hz=Integer.valueOf(in+"");
							assert hz>=0 && hz<=9;
						}else if(in==';'){//频域减弱
							player.desEqualizerBand(hz,0.5);
						}else if (in=='\''){//频域增强
							player.addEqualizerBand(hz,0.5);
						}else if(in=='d'){//还原默认
							player.setEffectDefault();
						}else if(in=='/'){//后退5s
							player.back(5000);
						}else if(in=='\\'){//快进5s
							player.front(5000);
						}else if(in=='q'){//退出
							player.stopPlay();
							System.exit(0);
						}else if(in=='z'){//上一个播放列表
							player.preList();
							player.nextSong(true);
						}else if(in=='x'){//下一个播放列表
							player.nextList();
							player.nextSong(true);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		});
		player.addListener(this);
		player.nextSong(true);
		thread.start();

	}


	@Override
	public void onPlay(MediaPlayer player) {
		// TODO Auto-generated method stub
		System.out.println(player.getCurrentTime());
	}


	@Override
	public void onReady(MediaPlayer player) {
		// TODO Auto-generated method stub
		System.out.println("\n"+player.getMedia().getSource()+"\t"+player.getTotalDuration());					
	}


	@Override
	public void onStop(MediaPlayer player) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTiming(ObservableValue<? extends Duration> arg0,Duration arg1, Duration arg2,MediaPlayer player) {
		// TODO Auto-generated method stub
		System.out.print("\r");
		System.out.print(MyTimeUtil.formatTime(arg2, player.getTotalDuration()));
	}


	@Override
	public void onError(MediaPlayer player) {
		// TODO Auto-generated method stub
		
	}


}
