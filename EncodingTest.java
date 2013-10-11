package com.encoding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class EncodingTest {
//  String userID = "22049";
//  String userKey = "8d938467da2327efe55f43f420c3dc86";
  String mediaID = "";
    
    private static String readFile(Class<?> srcClass, String filePath){
        ClassLoader classLoader = srcClass.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        if (inputStream == null) {
            System.out.println("Couldn't xml file "+filePath);
        }
        BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder result = new StringBuilder();
        try {
            while ((line = buf.readLine()) != null) {
                result.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    
    private static int startEncodingWorkflow(String xmlFilePath) {
        return execute(xmlFilePath);
    }
    
    private static int getStatus(String xmlFilePath) {
        return execute(xmlFilePath);
    }
    
    private static int execute(String xmlFilePath) {
        String xml = readFile(EncodingTest.class, xmlFilePath);
        URL server = null;

        try {
            String url = "http://manage.encoding.com";
            System.out.println("Connecting to:" + url);
            server = new URL(url);

        } catch (MalformedURLException mfu) {
            mfu.printStackTrace();
            return 0;
        }

        try {
            String sRequest = "xml=" + URLEncoder.encode(xml, "UTF8");
            System.out.println("Open new connection to tunnel");
            HttpURLConnection urlConnection = (HttpURLConnection) server.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(60000);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            out.write(sRequest);
            out.flush();
            out.close();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            String str = urlConnection.getResponseMessage();
            System.out.println("Response:" + urlConnection.getResponseCode());
            System.out.println("Response:" + urlConnection.getResponseMessage());
            StringBuffer strbuf = new StringBuffer();
            byte[] buffer = new byte[1024 * 4];

            try {
                int n = 0;
                while (-1 != (n = is.read(buffer))) {
                    strbuf.append(new String(buffer, 0, n));
                }

                is.close();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            System.out.println(strbuf.toString());

        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
//        startEncodingWorkflow("com/encoding/addFile_customPreset.xml");
//        getStatus("com/encoding/GetStatus.xml");
//        getStatus("com/encoding/RestartFailedTask.xml");
        
//        startEncodingWorkflow("com/encoding/addMedia.xml");
        getStatus("com/encoding/GetStatus.xml");
    }
}
