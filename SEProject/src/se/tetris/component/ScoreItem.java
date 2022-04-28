package se.tetris.component;

import java.util.Date;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import se.tetris.data.DBCalls;
import se.tetris.setting.SettingCode;
import se.tetris.component.*;

public class ScoreItem {

    public static int CODE_RECENT = 0;
    public static String[] STD_SCORE_RECENT = new String[3];
    public static String[] ITEM_SCORE_RECENT = new String[3];

    DBCalls dataCalls = new DBCalls();

    public int ranking;
    public int mode;
    public int level;
    public String nickname;
    public long Scroe;
    public String date;

    public ScoreItem() {

    }

    public ScoreItem(int r, int l, String n, long s) {
        Scroe = s;

        setNickName(n);
        setRank(r);
        setLevel(l);
    }

    public void setScore(int score) {
        this.Scroe = score;
    }

    public long getScore() {
        return Scroe;
    }

    public void setRank(int rank) {
        this.ranking = rank;
    }

    public int getRank() {
        return ranking;
    }

    public void setMode(int mode) {
        /// Level Info
        /// 0 : Nomal
        /// 1 : Item
        this.mode = mode;
    }

    public int getMode() {
        return level;
    }

    public void setLevel(int level) {
        /// Level Info
        /// 0 : Nomal
        /// 1 : Easy
        /// 2 : Hard
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setNickName(String name) {
        this.nickname = name;
    }

    public String getNickName() {
        return nickname;
    }

    public void clearScore() {
        // 설정에서 초기화
    }

    public boolean showDialog(int sc, int mode, int level) {
        boolean View = false;
        int result = JOptionPane.showConfirmDialog(null, "게임이 종료되었습니다! 점수를 저장하시겠어요?\n 취소 버튼을 클릭시 프로그램이 종료됩니다.", "게임종료",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == 0) {
            getScoreInfo(sc, mode, level);
        } else {
            System.exit(0);
        }

        return View;
    }

    public void getRecentScore(int sc, int mode, int level, String name) {
        Score scoreView = new Score();

        int Window = dataCalls.getWindowSetting();

        if(Window == 0) {
            scoreView.setSize(400,600);
        }else if(Window == 1) {
            scoreView.setSize(800,1200);
        }else {
            scoreView.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
        }
        scoreView.setVisible(true);
    }

    public void getScoreInfo(int sc, int mode, int level) {
        String name = JOptionPane.showInputDialog(null, "점수에 기록될 닉네임을 입력해주세요.","스코어 기록",JOptionPane.OK_CANCEL_OPTION);
//		System.out.println("이름 : " +name);
//		System.out.println("점수 : " +sc);
//		System.out.println("모드 : " +mode);
//		System.out.println("레벨 : " +level);

        //String name, int score, int type, int mode

        if(name == null) {
            System.exit(0);
        }else {

            dataCalls.InsertScoreData(name, sc, level, mode);

            if(mode == 0) {
                CODE_RECENT = 1;
                STD_SCORE_RECENT[0] = String.valueOf(level);
                STD_SCORE_RECENT[1] = name;
                STD_SCORE_RECENT[2] = String.valueOf(sc);
            }else {
                CODE_RECENT = 2;
                ITEM_SCORE_RECENT[0] = String.valueOf(level);
                ITEM_SCORE_RECENT[1] = name;
                ITEM_SCORE_RECENT[2] = String.valueOf(sc);
            }


            getRecentScore(sc, mode, level, name);


        }

    }



}