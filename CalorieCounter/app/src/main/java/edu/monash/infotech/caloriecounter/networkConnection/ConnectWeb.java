package edu.monash.infotech.caloriecounter.networkConnection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sen on 2016/4/17.
 */
public class ConnectWeb {
    public static String getContent(String urls){
        try{
            String urls1 = urls.replace(" ","%20");
            URL url = new URL(urls1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine())!=null){
                sb.append(line);
            }
            conn.disconnect();
            return sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
