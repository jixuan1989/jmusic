package player;
/**
 * @author sainthxd@gmail.com
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LastPlay implements Serializable{
	 String playList=PlayLists.DEFAULT;
	 int play=-1;
	
	public void serialize(File file) throws IOException{
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
	public static LastPlay diserialize(File file) throws ClassNotFoundException, IOException{
		if(file.exists()){
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
			try{
				LastPlay lists= (LastPlay) in.readObject();
				return lists;
			}finally{
				in.close();
			}
		}else{
			return  new LastPlay();
		}
	}
	public String getPlayList() {
		return playList;
	}
	public void setPlayList(String playList) {
		this.playList = playList;
	}
	public int getPlay() {
		return play;
	}
	public void setPlay(int play) {
		this.play = play;
	}

}
