package player.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author sainthxd@gmail.com
 */
public class FileUtil {
	/**
	 * 暂时先根据后缀名进行判断吧。。。
	 * @param folder
	 * @return
	 */
	public static List<String> getMusics(File folder){
		if(!folder.isDirectory()){
			if(isMusic(folder.getName().substring(folder.getName().lastIndexOf(".")+1))){
				return Collections.singletonList(folder.toURI().toString());
			}
			else return Collections.emptyList();
		}else{
			List<String> musics=new ArrayList<String>();
			//获取本层的所有音频
			File[] files=folder.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(isMusic(pathname.getName().substring(pathname.getName().lastIndexOf(".")+1))){
						return true;
					}
					return false;
				}
			});
			for(File f:files){
				musics.add(f.toURI().toString());
			}
			//获取子目录
			File[] subFolders=folder.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(pathname.isDirectory()){
						return true;
					}
					return false;
				}
			});
			for(File f:subFolders){
				musics.addAll(getMusics(f));
			}
			return musics;
		}
	}
	/**
	 * 判断一个文件名是否是音频文件
	 * @param suffit
	 * @return
	 */
	private static boolean isMusic(String suffit){
		String[] supports=new String[]{"wav","mp3","mp4","m4a","m4v","aif","aiff","fxm","flv","m3u8"};
		for(String s:supports){
			if(suffit.toLowerCase().endsWith(s)){
				return true;
			}
		}
		return false;
	}
}
