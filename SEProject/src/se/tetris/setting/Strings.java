package se.tetris.setting;

import javax.swing.*;

public class Strings {
    String screenSizeTitleString = "화면 크기 조절";
    String sizeOneString = "표준";
    String sizeTwoString = "크게";
    String sizeThreeString = "전체 화면 모드";

    String keyTitleString = "방향키 선택";
    String keyOneString = "방향키";
    String keyTwoString = "WASD";

    String modeTitleString = "모드 선택";
    String modeOneString = "Easy";
    String modeTwoString = "Normal";
    String modeThreeString = "Hard";

    String colorBlindTitleString = "색맹모드";
    String colorBlindOneString = "Off";
    String colorBlindTwoString = "On";

    String settingTitleString = "테트리스 게임 설정";
    String scoreResetString = "스코어보드<br>초기화";
    String BackToGameString = "일반<br>게임으로";
    String BackToItemGameString = "아이템 모드<br>게임으로";
    String BackToStartString = "시작 메뉴";
    String BackToBattleString = "대전 모드";
    String settingResetString = "설정<br>초기화";
    String frameTitleString = "SeoulTech SE Tetris";


    static String addingHTMLfun(String string) {
        return "<html><body style='text-align:center;'>" + string + "</body></html>";
    }
}
