package application.server;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public final class Connecter {

    private Connecter() {}

    private static final int CONN_TIMEOUT = 15000;

    private static HttpURLConnection createConnection(final String connURL) {
        HttpURLConnection connection = null;
        URL url = null;
        try {
            url = new URL(connURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONN_TIMEOUT);
            connection.setReadTimeout(CONN_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static String getInputStreamFromConnection(final HttpURLConnection connection) {

        String inputStream = null;

            if (connection != null) {
                try {

                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    while ((inputStream = br.readLine()) != null) {
                        sb.append(inputStream);
                    }
                    is.close();
                    br.close();
                    inputStream = sb.toString();
                } catch (Exception e) {
                    getInputStreamFromConnection(createConnection(ServerInformation.SERVER_NAME
                            + ServerInformation.ON_CHECK_FOR_SIGNAL_OPERATION));
                }
            }

        return inputStream;
    }

    public static String parseJSONFromConnection(String JSON, final String what_to_str) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse("{" + "\"msg\" :" + JSON + "}");
            JSONArray items = (JSONArray) object.get("msg");
            JSONObject s = (JSONObject) items.get(0);
            JSON = s.get(what_to_str).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return JSON;
    }


    private static void closeConnection(HttpURLConnection connection) {
        connection.disconnect();
    }

    public static String getDataFromURL(final String url) throws SocketTimeoutException {
        HttpURLConnection connection = createConnection(url);
        String response = getInputStreamFromConnection(connection);
        connection.disconnect();
        if (response != null)
            return response;
        else {
            response = getInputStreamFromConnection(createConnection(ServerInformation.SERVER_NAME
                    + ServerInformation.ON_CHECK_FOR_SIGNAL_OPERATION));
            return response;
        }
    }

}
