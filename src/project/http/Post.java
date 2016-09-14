package project.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import lejos.hardware.lcd.LCD;

public class Post {
    public static void executePost(String url, String text) {
    	try
		{
			URL TestURL = new URL(url); // http://***/reciever.rb など
   	 		URLConnection con = TestURL.openConnection();

			//	送信するよ！指定
			con.setDoOutput(true);

			//--------------------
			//送信する
			//--------------------
			OutputStreamWriter	ow1 = new OutputStreamWriter(con.getOutputStream());
			BufferedWriter bw1 = new BufferedWriter(ow1);

			//POSTの内容を書き出す
			bw1.write(text); // plots=-1,-1,3,-1,5,-1など


			//	クローズ
			bw1.close();
			ow1.close();

			//--------------------
			//受信する
			//--------------------
			InputStreamReader ir1 = new InputStreamReader(con.getInputStream());
			BufferedReader	br1 = new BufferedReader(ir1);

			//	１行ずつ書き出す
			String line;
			while((line=br1.readLine()) != null)
			{
				//改行がカットされてるので、printlnになる。
				LCD.drawString(line, 0, 3);
			}

			//	クローズ
			br1.close();
			ir1.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
}
