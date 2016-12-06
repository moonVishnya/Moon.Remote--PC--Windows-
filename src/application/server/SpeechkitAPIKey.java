package application.server;

import java.net.SocketTimeoutException;

public final class SpeechkitAPIKey {

    private static final String key_url = ServerInformation.SERVER_NAME +
            ServerInformation.GET_API_KEY;

    public static String getAPIKeyFromServer() {
        String key = null;
        try {
            key = Connecter.getDataFromURL(key_url);
            key = Connecter.parseJSONFromConnection(key, "status");
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        return key;
    }

}
