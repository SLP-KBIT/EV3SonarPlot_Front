package project.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import lejos.hardware.lcd.LCD;

public class Get {
    public static void executeGet(String str) {
        LCD.drawString("===== HTTP GET Start =====", 0, 0);
        try {
            URL url = new URL(str); // http://***/reciever.rb?plots=3,-1,5,-1" など

            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                	LCD.drawString("HTTP OK", 0, 1);
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LCD.drawString("===== HTTP GET End =====", 0, 0);
    }
}
