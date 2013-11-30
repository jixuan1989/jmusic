package player;
/**
 * @author sainthxd@gmail.com
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import secure.SecretUtils;

public class OnlineConfig {
	/**
	 * 是否使用高质量音乐，仅限百度音乐的设置
	 */
	private boolean isHighQuality=true;

	private String user;

	private String passwd;
	private boolean changed;
	public OnlineConfig(){

	}
	public OnlineConfig(boolean isHighQuality, String user, String passwd) {
		super();
		this.isHighQuality = isHighQuality;
		this.user = user;
		this.passwd = passwd;
	}
	public boolean isHighQuality() {
		return isHighQuality;
	}
	public void setHighQuality(boolean isHighQuality) {
		this.isHighQuality = isHighQuality;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void serialize(File file) throws IOException{
		if(changed&&user!=null && passwd!=null){
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			DataOutputStream out =new DataOutputStream(new FileOutputStream(file));
			try{
				out.writeBoolean(isHighQuality);
				byte[] bytes=SecretUtils.encryptMode(user.getBytes());
				out.writeInt(bytes.length);
				out.write(bytes);
				bytes=SecretUtils.encryptMode(passwd.getBytes());
				out.writeInt(bytes.length);
				out.write(bytes);
			}finally{
				out.close();
			}
		}
	}
	public static OnlineConfig diserialize(File file) throws IOException{
		OnlineConfig onlineConfig=new OnlineConfig();
		
		if(file.exists()){
			DataInputStream in=new DataInputStream(new FileInputStream(file));
			try{
				onlineConfig.isHighQuality=in.readBoolean();
				int length=in.readInt();
				byte[] bytes=new byte[length];
				in.read(bytes,0, length);
				onlineConfig.user=new String(SecretUtils.decryptMode(bytes));
				length=in.readInt();
				bytes=new byte[length];
				in.read(bytes,0, length);
				onlineConfig.passwd=new String(SecretUtils.decryptMode(bytes));
			}finally{
				in.close();
			}
		}else{
			onlineConfig.changed=true;
		}
		return onlineConfig;

	}
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}
