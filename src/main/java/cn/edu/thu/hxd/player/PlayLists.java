package cn.edu.thu.hxd.player;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class PlayLists implements Serializable{
	static String LOCAL_LIST="本地音乐";
	public static String DEFAULT="默认";
	static String MY_FAVORATE="我的收藏";
	private Map<String, List<String>> lists=new HashMap<String,List<String>>();
	transient private boolean changed=false;
	transient private ListIterator<String> iterator;
	transient private String current;
	public PlayLists() {
		super();
		changed=true;
		iterator=new ArrayList<String>(lists.keySet()).listIterator();
	}
	public PlayLists(Map<String, List<String>> lists) {
		super();
		this.lists = lists;
		iterator=new ArrayList<String>(lists.keySet()).listIterator();

	}
	public Map<String, List<String>> getLists() {
		return lists;
	}
	public void setLists(Map<String, List<String>> lists) {
		this.lists = lists;
	}
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
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
	public static PlayLists diserialize(File file) throws ClassNotFoundException, IOException{
		if(file.exists()){
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
			try{
				PlayLists lists= (PlayLists) in.readObject();

				lists.iterator=new ArrayList<String>(lists.lists.keySet()).listIterator();	
//				if(lists.current!=null){
//					while (lists.iterator.hasNext()) {
//						String tmp=lists.iterator.next();
//						if(tmp.equals(lists.current)){
//							break;
//						}
//					}
//				}
				return lists;
			}finally{
				in.close();
			}
		}else{
			return new PlayLists();
		}
	}
	public List<String> nextList(){
		if(iterator.hasNext()){
			current=iterator.next();
			return lists.get(current);
		}
		else if(lists.size()!=0){
			iterator=new ArrayList<String>(lists.keySet()).listIterator();
			current=iterator.next();
			return lists.get(current);
		}else{
			return new ArrayList<String>();
		}
	}
	public List<String> preList(){
		if(iterator.hasPrevious()){
			current=iterator.previous();
			return lists.get(current);
		}
		else if(lists.size()!=0){
			List<String> tmp=new ArrayList<String>(lists.keySet());
			iterator=tmp.listIterator(tmp.size());	
			current=iterator.previous();
			return lists.get(current);
		}else{
			return new ArrayList<String>();
		}
	}
	/**
	 * 增加list（会自动标注change状态）
	 * @param name
	 * @param list
	 */
	public void addList(String name,List<String> list,String current){
		changed=true;
		if(lists.containsKey(name)){
			List<String> list2=lists.get(name);
			List<String> tmp=new ArrayList<String>(list);
			List<String> list3=new ArrayList<String>(list);
			tmp.retainAll(list2);//l3中只保存1、2共有的数据
			list3.removeAll(tmp);
			tmp.clear();
			tmp.addAll(list3);
			tmp.addAll(list2);
			lists.put(name, tmp);
			return;
		}
		lists.put(name, list);
		iterator=new ArrayList<String>(lists.keySet()).listIterator();
		if(current!=null){
			while (iterator.hasNext()) {
				String tmp=iterator.next();
				if(tmp.equals(current)){
					this.current=tmp;
					break;
				}
			}
		}

	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	

}
