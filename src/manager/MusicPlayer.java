package manager;



import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import java.net.MalformedURLException;
import java.net.URL;


public class MusicPlayer implements WavPlayer {

    private BasicPlayer player;

    /*
        1 -- если что-то играет
     */
    public static boolean isPlaying = false;

    @Override
    public void playWav(String mURL) {
        player = new BasicPlayer();
        try {
            URL url = new URL(mURL);
            player.open(url);
            player.play();
            isPlaying = true;
        } catch (BasicPlayerException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopWav() {
        if (isPlaying) {
            try {
                player.stop();
                isPlaying = false;
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            }
        }
    }


}
