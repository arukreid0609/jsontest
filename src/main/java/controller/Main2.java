package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

/**
 * Servlet implementation class Main2
 */
@WebServlet("/Main2")
public class Main2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader reader = null;
		try {
			ServletContext app = this.getServletContext();
			String path = app.getRealPath("test.json");
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			
//			FileInputStream fis1 = new FileInputStream("text.txt");
//			InputStreamReader isr1 = new InputStreamReader(fis1,"UTF-8");
//			System.out.println(isr1.read());
//			isr1.close();
			reader = new JsonReader(isr);
			JsonObject root = new Gson().fromJson(reader,JsonObject.class);
			String name = root.get("name").getAsString();
			JsonArray array = root.get("array").getAsJsonArray();
			System.out.println(array.get(0).getAsInt());
			System.out.println(name);
			
//			FileOutputStream fos = new FileOutputStream("text.txt");
//			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
//			File file = new File("text.txt");
			File file = new File(path);
			System.out.println(file.getAbsolutePath());
//			osw.write(name);
//			osw.close();

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
		}
	}

}
