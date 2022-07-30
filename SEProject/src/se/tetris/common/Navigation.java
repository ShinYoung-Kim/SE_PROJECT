package se.tetris.common;

import se.tetris.component.Sizeable;
import se.tetris.component.Start;
import se.tetris.setting.SettingCode;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import java.awt.*;

public class Navigation {
    //세팅, 시작, 스코어보드,
    //일반 보드, 아이템보드, 배틀일반보드, 배틀아이템보드, 배틀시간보드

    //네비게이션에 화면 요청 -> visible처리 + setsize
    public static final int START_SCREEN = 0;
    public static final int SETTING_SCREEN = 1;
    public static final int SCOREBOARD_SCREEN = 2;
    public static final int BOARD_SCREEN = 3;
    public static final int ITEM_BOARD_SCREEN = 4;
    public static final int BATTLE_BOARD_SCREEN = 5;
    public static final int BATTLE_ITEM_BOARD_SCREEN = 6;
    public static final int BATTLE_TIME_BOARD_SCREEN = 7;

    private Component current = null;
    private final SettingCode setting = new SettingCode();

    public void navigate(int screen) {
        if (current != null) {
            current.setVisible(false);
        }
        current = switch (screen){
            case START_SCREEN -> new Start();
            case SETTING_SCREEN -> setting;
            default -> throw new RuntimeException("유효하지 않은 스크린을 넘겼습니다.");
            //default -> current;
        };
        current.setVisible(true);
        if (current instanceof Sizeable) {
            int sizeNumber = SettingValues.getInstance().sizeNumber;
            ((Sizeable)current).changeSize(sizeNumber);
        }
    }

    private Navigation() {

    }

    public static Navigation instance = null;
    public static Navigation getInstance(){
        if (instance == null) {
            instance = new Navigation();
        }
        return instance;
    }
}
