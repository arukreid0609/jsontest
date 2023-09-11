package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import model.Human;
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader reader = null;
		HttpURLConnection con = null;
		try {
			// URLを指定、接続、GETリクエストを設定
			URL url = new URL("https://livlog.xyz/hoshimiru/constellation?lat=35.6581&lng=139.7414&date=2020-01-15&hour=20&min=00");
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			
			// GETリクエストして返ってきたレスポンス内容読み込む
			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			
			// レスポンス内容をJsonとして読み込む
			reader = new JsonReader(isr);
			
			// Jsonからroot要素を取得（今回は「{ }」から始まるオブジェクト）
			JsonObject root = new Gson().fromJson(reader,JsonObject.class);
			// root要素からresultプロパティを取得
			JsonArray result = root.get("result").getAsJsonArray();
			
			for(int i = 0;i<result.size();i++) {
				JsonObject sObj = result.get(i).getAsJsonObject();
				String jpName = sObj.get("jpName").getAsString();
				String enName = sObj.get("enName").getAsString();
				double directionNum = sObj.get("directionNum").getAsDouble();
				System.out.printf("%s,%s,%.2f\n", jpName,enName,directionNum);
			}
			System.out.println(new Gson().toJson(new Human("佐々木",32)));
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				try {
					reader.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				con.disconnect();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
