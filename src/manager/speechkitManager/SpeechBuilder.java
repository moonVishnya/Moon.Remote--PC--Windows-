package manager.speechkitManager;


import application.server.ServerInformation;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SpeechBuilder {


    private static final String format = "wav";
    private static final String lang = "ru-RU";
    private static final String speaker = "zahar";
    private static final String emotion = "evil";
    private static final String speechKitServer = "https://tts.voicetech.yandex.net/generate?";
    public static String YANDEX_API_SPEECHKIT_KEY = "8aec41e3-d08a-42c5-93dd-ad96b9cb2934";

    public static String generateSpeechLink(String sentence) throws UnsupportedEncodingException {
        StringBuilder requestBuilder = new StringBuilder();
                requestBuilder.append(speechKitServer).
                        append("text=" + URLEncoder.encode(sentence, "UTF-8")).
                        append("&format=" + URLEncoder.encode(format, "UTF-8")).
                        append("&lang=" + URLEncoder.encode(lang, "UTF-8")).
                        append("&speaker=" + URLEncoder.encode(speaker,"UTF-8")).
                        append("&emotion=" + URLEncoder.encode(emotion, "UTF-8")).
                        append("&key=" + URLEncoder.encode(YANDEX_API_SPEECHKIT_KEY, "UTF-8"));
        return requestBuilder.toString();
    }

    public static void setAPIKey(String key) {
        YANDEX_API_SPEECHKIT_KEY = key;
    }

}
