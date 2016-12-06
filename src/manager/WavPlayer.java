package manager;


public interface WavPlayer {


    /*
        плеер способен проигрывать только wav формат
        url трека передается в метод
     */
    void playWav(final String mURL);

    /*
        останавливаем воспроизведение трека
     */
    void stopWav();
}
