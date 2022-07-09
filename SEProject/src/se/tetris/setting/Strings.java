package se.tetris.setting;

public class Strings {
    //ScreenSizeSettingPanel
    public static final String screenSizeTitleString = "화면 크기 조절";
    public static final String sizeOneString = "표준";
    public static final String sizeTwoString = "크게";
    public static final String sizeThreeString = "전체 화면 모드";

    //KeySettingPanel
    public static final String keyTitleString = "방향키 선택";
    public static final String keyOneString = "방향키";
    public static final String keyTwoString = "WASD";

    //DifficultySettingPanel
    public static final String modeTitleString = "모드 선택";
    public static final String modeOneString = "Easy";
    public static final String modeTwoString = "Normal";
    public static final String modeThreeString = "Hard";

    //ColorBlindnessSettingPanel
    public static final String colorBlindTitleString = "색맹모드";
    public static final String colorBlindOneString = "Off";
    public static final String colorBlindTwoString = "On";

    //SettingCode
    public static final String settingTitleString = "테트리스 게임 설정";
    public static final String scoreResetString = "스코어보드<br>초기화";
    public static final String BackToGameString = "일반<br>게임으로";
    public static final String BackToItemGameString = "아이템 모드<br>게임으로";
    public static final String BackToStartString = "시작 메뉴";
    public static final String BackToBattleString = "대전 모드";
    public static final String settingResetString = "설정<br>초기화";
    public static final String frameTitleString = "SeoulTech SE Tetris";
    public static final String resetMessageString = "초기화 되었습니다!";
    public static final String resetTitleString = "스코어 보드 초기화";

    public static String addingHTMLfun(String string) {
        return "<html><body style='text-align:center;'>" + string + "</body></html>";
    }
}
