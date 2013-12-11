package cn.edu.thu.hxd.baidu;
/**
 * copy from http://pml346680914.iteye.com/blog/1850805
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class BaiduCloudMusic {

	public static void main(String[] args) throws Exception {
		BaiduCloudMusic bcm = new BaiduCloudMusic();
		bcm.login("username", "password");
		bcm.downloadAll();
	}

	private String cookieValue = "";
	private String downloadDirectory = "d:/baidumusic";

	/**
	 * 登陆百度,其他方法调用之前需要先登陆
	 * @param username
	 * @param password
	 */
	public void login(String username, String password){
		try { 
			URL url=new URL("http://www.baidu.com/");
			HttpURLConnection httpUrlConnection=(HttpURLConnection)url.openConnection(); 
			httpUrlConnection.setRequestMethod("GET");
			String cookie1=httpUrlConnection.getHeaderField("Set-Cookie"); 
			//System.out.println("cookie1:"+cookie1); 
			cookie1 = cookie1.substring(0,45);

			url=new URL("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true");
			httpUrlConnection=(HttpURLConnection)url.openConnection(); 
			httpUrlConnection.setRequestMethod("GET"); 
			httpUrlConnection.setRequestProperty("Cookie", cookie1);
			//httpUrlConnection.connect();
			String cookie2 = httpUrlConnection.getHeaderField("Set-Cookie"); 
			System.out.println("cookie2:"+cookie2); 
			cookie2 = cookie2.substring(0,11);
			String response = getResponse(httpUrlConnection.getInputStream());
			//System.out.println(response);
			Pattern pattern = Pattern.compile("token='(\\w+)'");
			Matcher matcher = pattern.matcher(response);
			matcher.find();
			String token = matcher.group(1);

			url=new URL("https://passport.baidu.com/v2/api/?login");
			httpUrlConnection=(HttpURLConnection)url.openConnection(); 
			httpUrlConnection.setRequestMethod("POST"); 
			//System.out.println(cookie1+"; "+cookie2);
			httpUrlConnection.setRequestProperty("Cookie", cookie1+"; "+cookie2);
			httpUrlConnection.setDoOutput(true);
			//System.out.println(token);

			String querystring = "loginType=1&tpl=mn&token="+token+"&username="+username+
					"&password="+password+"&mem_pass=on";
			httpUrlConnection.getOutputStream().write(querystring.getBytes());
			httpUrlConnection.getOutputStream().flush();
			httpUrlConnection.getOutputStream().close();

			response = getResponse(httpUrlConnection.getInputStream());
			//System.out.println(response);

			//String cookie3=httpUrlConnection.getHeaderField("Set-Cookie"); 
			//System.out.println("cookie3:"+cookie3); 

			//获取登陆后的cookie
			Map<String, List<String>> hfs=httpUrlConnection.getHeaderFields(); 
			List<String> loginCookies = hfs.get("Set-Cookie");
			for(String cookie:loginCookies){ 
				cookieValue += cookie.substring(0,cookie.indexOf(";")+1);
			} 

		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}

	/**
	 * 获得歌曲列表
	 * @return [{ngId:'',songName:'',artistName:''},...]
	 */
	public List<JSONObject> getSongList(){
		List<JSONObject> songList = new ArrayList<JSONObject>();
		//解析歌曲列表
		String link = "http://yinyueyun.baidu.com/data/cloud/collection?type=song&start=0&size=200";
		String response = getResponse(link);

		JSONArray songIds = JSONObject.fromObject(response).getJSONObject("data").getJSONArray("songList");
		for(int i=0;i<songIds.size();i++){
			JSONObject songInfo = getSongInfo(songIds.getJSONObject(i).getString("id"));
			songList.add(songInfo);
		}
		return songList;
	}

	public void downloadAll(){
		List<JSONObject> songList = getSongList();
		for(int i=0;i<songList.size();i++){
			String songId = songList.get(i).getString("songId");
			download(songId);
		}
	}

	public File download(String songId){
		try { 
			String maxRate = getMaxRate(songId);
			JSONObject songInfo =  getSongInfo(songId);
			//以歌手名字+歌曲名称组成文件名，格式：歌手 - 歌曲名称
			String filename = songInfo.getString("artistName")+" - "+songInfo.getString("songName");

			String link = "http://yinyueyun.baidu.com/data/cloud/downloadsongfile?songIds="+songId+"&rate="+maxRate;

			URL urlObject=new URL(link);
			HttpURLConnection httpUrlConnection=(HttpURLConnection)urlObject.openConnection();
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setRequestProperty("Cookie", cookieValue); 
			String disposition = httpUrlConnection.getHeaderField("Content-Disposition");
			disposition = disposition.replaceAll("\"", "");
			//此转码经测试发现有些是UTF-8编码，有些是GBK编码,所以文件名采用另外方式获得
			//disposition = new String(disposition.getBytes("iso-8859-1"),"UTF-8");

			//根据disposition中信息确定歌曲格式
			String suffix = disposition.substring(disposition.lastIndexOf("."));
			//System.out.println(disposition);

			InputStream inputStream= httpUrlConnection.getInputStream();

			File file = new File(downloadDirectory+"/"+filename+suffix);
			FileOutputStream fos = new FileOutputStream(file);

			byte[] buf = new byte[4096];
			int read=0;
			while((read=inputStream.read(buf)) > 0){
				fos.write(buf,0,read);
			}
			fos.flush();
			fos.close();
			inputStream.close();
			//System.out.println("完成<"+file.getName()+">歌曲下载！");
			return file;

		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获得歌曲的最大码率
	 * @param songId
	 * @return
	 */
	public String getMaxRate(String songId){
		String link = "http://yinyueyun.baidu.com/data/cloud/download?songIds="+songId;
		String response = getResponse(link);
		JSONObject rates = JSONObject.fromObject(response).getJSONObject("data").getJSONObject("data");
		String maxRate = (rates.getString("320").length()>0?"320":(rates.getString("192").length()>0?"192":"128"));
		return maxRate;
	}

	/**
	 * 获得歌曲详细信息
	 * @param songId
	 * @return {songId:'',songName:'',artistName:''}
	 */
	public JSONObject getSongInfo(String songId){
		String link = "http://yinyueyun.baidu.com/data/cloud/songinfo?songIds="+songId;
		String response = getResponse(link);
		JSONObject songInfo = JSONObject.fromObject(response).getJSONObject("data").getJSONArray("songList").getJSONObject(0);
		return songInfo;
	}

	private String getResponse(String link){
		try { 
			URL urlObject=new URL(link);
			HttpURLConnection httpUrlConnection=(HttpURLConnection)urlObject.openConnection();
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setRequestProperty("Cookie", cookieValue); 

			return getResponse(httpUrlConnection.getInputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getResponse(InputStream in){
		StringBuilder response=new StringBuilder();
		try{
			BufferedReader  rd = new BufferedReader(new InputStreamReader(in));
			char[] buf = new char[1024];
			int read=0;
			while((read=rd.read(buf)) > 0){
				response.append(buf,0,read);
			}
			rd.close();
			in.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response.toString();
	}
}
