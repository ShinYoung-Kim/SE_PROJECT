package se.tetris.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import java.util.TimerTask;

import se.tetris.component.*;
import se.tetris.data.DBCalls;
import se.tetris.setting.SettingCode;

public class TimeBattleBoard extends JFrame implements Sizeable {

    private static InnerTimeBoard player1;
    private static InnerTimeBoard player2;
    private JPanel panel;
    private KeyListener playerKeyListener;
    public static JLabel timerCount;
    public static GameTimer GameT;
    public static boolean IsCollision;
    public static String ColPlayer;


    public TimeBattleBoard() {
        super("SeoulTech SE Tetris - TimeBattle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelTotal = new JPanel();
        JPanel panelTop = new JPanel();
        timerCount = new JLabel("3:00");

        timerCount.setFont(new Font("고딕", Font.CENTER_BASELINE, 20));

        panelTotal.setLayout(new BorderLayout());
        panelTotal.add(panelTop, BorderLayout.NORTH);


        ImageIcon timerIcon = new ImageIcon("./SEProject/img/timeicon.png");
        JLabel LtimerIcon = new JLabel(timerIcon);


        panelTop.add(LtimerIcon);
        panelTop.add(timerCount);

        DBCalls dataCalls = new DBCalls();
        player1 = new InnerTimeBoard(dataCalls.getWindowSetting() + 1);
        player2 = new InnerTimeBoard(dataCalls.getWindowSetting() + 1);

        InnerBoard.BattleMode = "TimeBattle";

        player1.setName("Player1");
        player2.setName("Player2");

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(player1);
        panel.add(player2);

        panelTotal.add(panel, BorderLayout.CENTER);

        add(panelTotal);

        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocus();

        // 카운트 다운 쓰레드 실행
        IsCollision = false;
        startGameTimer();
        //inputCheck = true;

    }

    public void startGameTimer() {
        GameT = new GameTimer();
        GameT.start();
    }

    public static void stopGameTimer() {
        GameT.stop = true;
        GameT.setStop(true);
    }

    @Override
    public void changeSize(int sizeNumber) {
        if (sizeNumber == 1) {
            setSize(400, 600);
        } else if (sizeNumber == 2) {
            setSize(800, 800);
        } else {
            setSize(SettingCode.screenWidth, SettingCode.screenHeight);
        }
    }


    public class PlayerKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_S:
                    player1.moveDown();
                    player1.drawBoard();
                    break;
                case KeyEvent.VK_D:
                    player1.moveRight();
                    player1.drawBoard();
                    break;
                case KeyEvent.VK_A:
                    player1.moveLeft();
                    player1.drawBoard();
                    break;
                case KeyEvent.VK_W:
                    player1.blockRotate();
                    player1.drawBoard();
                    break;
                case KeyEvent.VK_TAB:
                    while (true) {
                        player1.eraseCurr();
                        if (player1.collisionBottom()) {
                            player1.collisionOccur();
                            player1.lineRemove();
                            player1.placeBlock();
                            player1.drawBoard();
                            break;
                        } else {
                            player1.y++;
                        }
                        player1.placeBlock();
                        player1.drawBoard();
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    player2.moveDown();
                    player2.drawBoard();
                    break;
                case KeyEvent.VK_RIGHT:
                    player2.moveRight();
                    player2.drawBoard();
                    break;
                case KeyEvent.VK_LEFT:
                    player2.moveLeft();
                    player2.drawBoard();
                    break;
                case KeyEvent.VK_UP:
                    player2.blockRotate();
                    player2.drawBoard();
                    break;
                case KeyEvent.VK_ENTER:
                    while (true) {
                        player2.eraseCurr();
                        if (player2.collisionBottom()) {
                            player2.collisionOccur();
                            player2.lineRemove();
                            player2.placeBlock();
                            player2.drawBoard();
                            break;
                        } else {
                            player2.y++;
                        }
                        player2.placeBlock();
                        player2.drawBoard();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    player1.timer.stop();
                    player2.timer.stop();
                    int choice = JOptionPane.showConfirmDialog(null, "게임을 종료하시겠습니까?", "게임 종료", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (choice) {
                        case 0:
                            int confirm = JOptionPane.showConfirmDialog(null, "정말 종료하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                            if (confirm == 0) {
                                dispose();
                            } else {
                                player1.timer.restart();
                                player2.timer.restart();
                            }
                            break;
                        case 1:
                            player1.timer.restart();
                            player2.timer.restart();
                            break;
                        case 2:
                            player1.timer.restart();
                            player2.timer.restart();
                    }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public static void collisionStop() {
        IsCollision = true;
        stopGameTimer();
    }

    public static void gameStop() {
        Timer player1Timer = player1.getTimer();
        Timer player2Timer = player2.getTimer();
        player1Timer.stop();
        player2Timer.stop();
    }

    public static void gameReset() {
        player1.reset();
        player2.reset();
        Timer player1Timer = player1.getTimer();
        Timer player2Timer = player2.getTimer();
        player1Timer.restart();
        player2Timer.restart();
        IsCollision = false;
    }

    public static void gameClose() {
        System.exit(0);
    }

    /**
     * 게임 타이머
     */
    class GameTimer extends Thread {

        private boolean stop = false;

        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {

            for (int i = 20; i >= 0; i--) {
                if (stop) {
                    break;
                }

                int Min = i / 60;
                int Sec = i % 60;

                String timerTxt = Min >= 10 ? Integer.toString(Min) : "0" + Integer.toString(Min);
                timerTxt += " : " + (Sec >= 10 ? Integer.toString(Sec) : "0" + Integer.toString(Sec));

                if (Min == 0) {
                    timerCount.setForeground(Color.RED);
                }

                timerCount.setText(timerTxt);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopGameTimer();
            gameStop();


            String[] overOption = {"종료하기", "다시하기"};
            int over = 0;


            if (IsCollision) {
                //블록 더이상 쌓을 수 없음
                over = JOptionPane.showOptionDialog(null, ColPlayer + " 이(가) 게임에서 승리했습니다!", "종료", 0, 0, null, overOption, overOption[0]);
            } else {
                //점수 비교
                if (player1.getNowScore() > player2.getNowScore()) {
                    over = JOptionPane.showOptionDialog(null, "Player1 이(가) 게임에서 승리했습니다!", "종료", 0, 0, null, overOption, overOption[0]);
                } else if (player1.getNowScore() == player2.getNowScore()) {
                    over = JOptionPane.showOptionDialog(null, "무승부입니다!", "종료", 0, 0, null, overOption, overOption[0]);
                } else {
                    over = JOptionPane.showOptionDialog(null, "Player2 이(가) 게임에서 승리했습니다!", "종료", 0, 0, null, overOption, overOption[0]);
                }
            }

            if (over == 0) {
                gameClose();
            }
            if (over == 1) {
                startGameTimer();
                gameReset();
            }

        }
    }
}