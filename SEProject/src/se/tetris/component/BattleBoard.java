package se.tetris.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import se.tetris.component.*;
import se.tetris.setting.SettingValues;

public class BattleBoard extends JFrame {

    private static InnerBoard player1;
    private static InnerBoard player2;
    private JPanel panel;
    private KeyListener playerKeyListener;

    public BattleBoard() {
        super("SeoulTech SE Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        player1 = new InnerBoard();
        player2 = new InnerBoard();

        player1.setName("Player1");
        player2.setName("Player2");

        player1.setAttackBoard(player2.getAttackBoard());
        player2.setAttackBoard(player1.getAttackBoard());

        panel = new JPanel();

        panel.add(player1);
        panel.add(player2);

        add(panel);

        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        requestFocus();
    }


    public class PlayerKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
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
                case KeyEvent.VK_SHIFT:
                    while(true){
                        player1.eraseCurr();
                        if(player1.collisionBottom()) {
                            player1.collisionOccur();
                            player1.lineRemove();
                            player1.placeBlock();
                            player1.drawBoard();
                            break;
                        }
                        else {
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
                    while(true){
                        player2.eraseCurr();
                        if(player2.collisionBottom()) {
                            player2.collisionOccur();
                            player2.lineRemove();
                            player2.placeBlock();
                            player2.drawBoard();
                            break;
                        }
                        else {
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
                    switch(choice) {
                        case 0:
                            int confirm = JOptionPane.showConfirmDialog(null, "정말 종료하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                            if (confirm == 0) {
                                dispose();
                            }
                            else {
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

    public static void gameStop() {
        Timer player1Timer = player1.getTimer();
        Timer player2Timer = player2.getTimer();
        player1Timer.stop();
        player2Timer.stop();
    }

    public static void drawAttack() {
        int[][] player1AttackBoard = player1.getAttackBoard();
        int[][] player2AttackBoard = player2.getAttackBoard();

        StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < player1AttackBoard.length; i++) {
            for (int j = 0; j < player1AttackBoard[i].length; j++) {
                if (player1AttackBoard[i][j] == 1) {
                    sb1.append("■");
                }
                else {
                    sb1.append(" ");
                }
            }
            sb1.append("\n");
        }
        player2.attackArea.setText(sb1.toString());
        player2.attackDoc.setParagraphAttributes(0, player2.attackDoc.getLength(), player2.stylesetAk, false);

        StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < player2AttackBoard.length; i++) {
            for (int j = 0; j < player2AttackBoard[i].length; j++) {
                if (player2AttackBoard[i][j] == 1) {
                    sb2.append("O");
                }
                else {
                    sb2.append(" ");
                }
            }
            sb2.append("\n");
        }
        player1.attackArea.setText(sb2.toString());
        player1.attackDoc.setParagraphAttributes(0, player1.attackDoc.getLength(), player1.stylesetAk, false);
    }
}