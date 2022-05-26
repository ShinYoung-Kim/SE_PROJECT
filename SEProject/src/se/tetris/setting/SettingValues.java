package se.tetris.setting;

public class SettingValues {

    private static final SettingValues instance = new SettingValues();

    public int intervalNumber = 1000;
    public int sizeNumber = 1;
    public int colorBlindModeCheck = 0;
    public int modeChoose = 2;
    public int keyChoose = 1;

    private SettingValues() {}

    public static SettingValues getInstance() {
        return instance;
    }
    /*
    public static void setIntervalNumber(int intervalNumber) {
        instance.intervalNumber = intervalNumber;
    }
    */
}