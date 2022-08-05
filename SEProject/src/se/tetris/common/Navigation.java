package se.tetris.common;

import se.tetris.component.*;
import se.tetris.setting.SettingCode;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
    public static final int START_BATTLE = 8;

    private Component current = null;
    private final SettingCode setting = new SettingCode();
    private final Start start = new Start();
    private final Score score = new Score();
    //private Board board = new Board();
    //board.timer.start();
    //private final ItemBoard itemBoard = new ItemBoard();
    //private final BattleBoard battleBoard = new BattleBoard();
    //private final ItemBattleBoard itemBattleBoard = new ItemBattleBoard();
    //private final TimeBattleBoard timeBattleBoard = new TimeBattleBoard();
    //private final StartBattle startBattle = new StartBattle();

    public void navigate(int screen) {
        if (current != null) {
            current.setVisible(false);
        }
        current = switch (screen){
            case START_SCREEN -> start;
            case SETTING_SCREEN -> setting;
            case SCOREBOARD_SCREEN -> score;
            case BOARD_SCREEN -> new Board();
            case ITEM_BOARD_SCREEN -> new ItemBoard();
            case BATTLE_BOARD_SCREEN -> new BattleBoard();
            case BATTLE_ITEM_BOARD_SCREEN -> new ItemBattleBoard();
            case BATTLE_TIME_BOARD_SCREEN -> new TimeBattleBoard();
            case START_BATTLE -> new StartBattle();
            default -> throw new RuntimeException("유효하지 않은 스크린을 넘겼습니다.");
            //default -> current;
        };
        current.setVisible(true);
        if (current instanceof Sizeable) {
            int sizeNumber = SettingValues.getInstance().sizeNumber;
            ((Sizeable)current).changeSize(sizeNumber);
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        int[] array = new int[5];
        array[1] = 0;
        arrayList.set(0, 1);
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
