package player;
/**
 * @author sainthxd@gmail.com
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Config implements Serializable{
	/**
	 * 音量大小
	 * 0~1
	 */
	private double volume=1;
	/**
	 * 左右声道的平衡
	 * -1~1
	 */
	private double balance=0;
	/**
	 * 均衡器.
		@see javafx.scene.media.AudioEqualizerBand.getBands()
		Band Index	Center Frequency (Hz)	Bandwidth (Hz)
		0	32	19
		1	64	39
		2	125	78
		3	250	156
		4	500	312
		5	1000	625
		6	2000	1250
		7	4000	2500
		8	8000	5000
		9	16000	10000
		EqualizerBand.MIN_GAIN ≤ EqualizerBand.gain ≤ EqualizerBand.MAX_GAIN(每个的最小值为-24,最大值为12)
	 */
	private double[] equalizrbands=new double[10];
	/**
	 * 默认的数据文件存储地址
	 */
	private File configFolder=new File(System.getProperty("user.home"),".jmusic");
	/**
	 * 默认的音乐文件存储地址
	 */
	private File musicFolder=new File(System.getProperty("user.home")+File.pathSeparator+"music","jmusic");

	/**
	 * 默认的临时音乐文件存储地址
	 */
	private File tmpMusicFolder=new File(System.getProperty("user.home")+File.pathSeparator+"music","jmusic_tmp");

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double[] getEqualizrbands() {
		return equalizrbands;
	}

	public void setEqualizrbands(double[] equalizrbands) {
		this.equalizrbands = equalizrbands;
	}

	public File getConfigFolder() {
		return configFolder;
	}

	public void setConfigFolder(File configFolder) {
		this.configFolder = configFolder;
	}

	public File getMusicFolder() {
		return musicFolder;
	}

	public void setMusicFolder(File musicFolder) {
		this.musicFolder = musicFolder;
	}

	public File getTmpMusicFolder() {
		return tmpMusicFolder;
	}

	public void setTmpMusicFolder(File tmpMusicFolder) {
		this.tmpMusicFolder = tmpMusicFolder;
	}

	public Config(double volume, double balance, double[] equalizrbands,
			File configFolder, File musicFolder, File tmpMusicFolder) {
		super();
		this.volume = volume;
		this.balance = balance;
		this.equalizrbands = equalizrbands;
		this.configFolder = configFolder;
		this.musicFolder = musicFolder;
		this.tmpMusicFolder = tmpMusicFolder;
	}

	public Config() {
		super();
		changed=true;
	}

	transient private boolean changed=false;
	public void serialize(File file) throws IOException{
		if(changed){
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
			try{
				out.writeObject(this);
			}finally{
				out.close();
			}
		}
	}
	public static Config diserialize(File ins) throws ClassNotFoundException, IOException{
		if(ins.exists()){
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(ins));
			try{
				return (Config) in.readObject();
			}finally{
				in.close();
			}
		}else{
			return new Config();
		}

	}
	
	/**
	 * 设置效果
	 * @param volumn -~1
	 * @param balance -1~1
	 * @param bands 长度为10的数组，大小为-24到12之间
	 */
	public void setEffect(double volumn, double balance, double[] bands){
		this.volume=volumn;
		this.balance=balance;
		this.equalizrbands=bands;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	

}
