package se.tetris.component;

import se.tetris.data.DBCalls;
import se.tetris.setting.SettingCode;
import se.tetris.setting.SettingValues;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.text.StyleConstants;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class BattleBoard extends JFrame implements Sizeable {

    private static InnerBoard player1;
    private static InnerBoard player2;
    private JPanel panel;
    private KeyListener playerKeyListener;
    private static boolean restart;

    final SettingValues setting = SettingValues.getInstance();

    public BattleBoard() {
        super("SeoulTech SE Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DBCalls dataCalls = new DBCalls();
        int sizeNumber = SettingValues.getInstance().sizeNumber;
        player1 = new InnerBoard(sizeNumber);
        player2 = new InnerBoard(sizeNumber);

        InnerBoard.BattleMode = "Battle";

        player1.setName("Player1");
        player2.setName("Player2");

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(player1);
        panel.add(player2);

        add(panel);

        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocus();

        Timer player1Timer = player1.getTimer();
        player1Timer.setDelay(10000);

        Timer player2Timer = player2.getTimer();
        player2Timer.setDelay(10000);

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
                            player1.lineRemove();
                            player1.collisionOccur();
                            if (player1.whoAttacked) {
                                player1.attackedFunction();
                                drawEmptyAttack();
                            }
                            if (!player1.isGameOver()) {
                                player1.placeBlock();
                                player1.drawBoard();
                            }
                            break;
                        } else {
                            player1.y++;
                            player1.lineRemove();
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
                            player2.lineRemove();
                            player2.collisionOccur();
                            if (player2.whoAttacked) {
                                player2.attackedFunction();
                                drawEmptyAttack();
                            }
                            if (!player2.isGameOver()) {
                                player2.placeBlock();
                                player2.drawBoard();
                            }
                            break;
                        } else {
                            player2.y++;
                            player2.lineRemove();
                        }
                        player2.placeBlock();
                        player2.drawBoard();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    player1.timer.stop();
                    player2.timer.stop();
                    String[] stopOption = {"재시작", "계속", "종료"};
                    int choice = JOptionPane.showOptionDialog(null, "무엇을 선택하시겠습니까?", "일시정지", 0, 0, null, stopOption, stopOption[1]);
                    switch (choice) {
                        case 0:
                            int confirm1 = JOptionPane.showConfirmDialog(null, "정말 게임을 재시작 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                            if (confirm1 == 0) {
                                gameReset();
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
                            int confirm2 = JOptionPane.showConfirmDialog(null, "정말 게임을 종료하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                            if (confirm2 == 0) {
                                dispose();
                            } else {
                                player1.timer.restart();
                                player2.timer.restart();
                            }
                            break;
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

    public static void gameReset() {
        player1.reset();
        player2.reset();
        Timer player1Timer = player1.getTimer();
        Timer player2Timer = player2.getTimer();
        player1Timer.restart();
        player2Timer.restart();
        player1.whoIs = false;
        player2.whoIs = false;
        player1.whoAttacked = false;
        player2.whoAttacked = false;
        restart = true;
        drawAttack();
        restart = false;
    }

    public static void gameClose() {
        System.exit(0);
    }

    public static void placeAttack(ArrayList<Integer> attack) {
        if (player1.whoIs == true) {
            int[][] player1AttackBoard = player1.getAttackBoard();
            int difference = attack.size();
            if (player1.attackBoardFull == false) {
                if (player1.alreadyAttacked == true) {
                    System.out.println("counted");
                    player1.attackLineCount = lineCounter(player1AttackBoard);
                    System.out.println(player2.attackLineCount);
                    if (player1.attackLineCount + attack.size() >= 10) {
                        difference = player1.attackLineCount + attack.size() - 10;
                        for (int i = attack.size() - difference; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                player1AttackBoard[i - attack.size() + difference][j] = player1AttackBoard[i][j];
                            }
                        }
                        for (int i = 0; i < attack.size() - difference; i++) {
                            player1.attackLine.add(attack.get(i) - player1.lastY);
                        }
                        player1.attackY -= attack.size() - difference;
                        player1.attackBoardFull = true;
                    } else {
                        for (int i = attack.size(); i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                player1AttackBoard[i - attack.size()][j] = player1AttackBoard[i][j];
                            }
                        }
                        for (int i = 0; i < attack.size(); i++) {
                            player1.attackLine.add(attack.get(i) - player1.lastY);
                        }
                        player1.attackY -= attack.size();
                    }
                    int firstY = player1.attackY;
                    for (int i = 9; i > 9 - difference; i--) {
                        for (int j = 0; j < player1AttackBoard[0].length; j++) {
                            player1AttackBoard[i][j] = 1;
                        }
                    }
                } else {
                    if (attack.size() > 10) {
                        difference = attack.size() - 10;
                        for (int i = 0; i < difference; i++) {
                            player1.attackLine.add(attack.get(i) - player1.lastY);
                        }
                        int firstY = player1.attackY;
                        for (int i = firstY; i > firstY - difference; i--, player1.attackY--) {
                            for (int j = 0; j < player1AttackBoard[0].length; j++) {
                                player1AttackBoard[i][j] = 1;
                            }
                        }
                        player1.alreadyAttacked = true;
                    } else {
                        for (int i = 0; i < attack.size(); i++) {
                            player1.attackLine.add(attack.get(i) - player1.lastY);
                        }
                        int firstY = player1.attackY;
                        for (int i = firstY; i > firstY - attack.size(); i--, player1.attackY--) {
                            for (int j = 0; j < player1AttackBoard[0].length; j++) {
                                player1AttackBoard[i][j] = 1;
                            }
                        }
                        player1.alreadyAttacked = true;
                    }
                }
                if (lineCounter(player1AttackBoard) == 10) {
                    player1.attackBoardFull = true;
                }
            } else {
                System.out.println("this");
            }
        }
        if (player2.whoIs == true) {
            int[][] player2AttackBoard = player2.getAttackBoard();
            int difference = attack.size();
            if (player2.attackBoardFull == false) {
                if (player2.alreadyAttacked == true) {
                    System.out.println("counted");
                    player2.attackLineCount = lineCounter(player2AttackBoard);
                    System.out.println(player2.attackLineCount);
                    if (player2.attackLineCount + attack.size() > 10) {
                        difference = player2.attackLineCount + attack.size() - 10;
                        for (int i = attack.size() - difference; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                player2AttackBoard[i - attack.size() + difference][j] = player2AttackBoard[i][j];
                            }
                        }
                        for (int i = 0; i < attack.size() - difference; i++) {
                            player2.attackLine.add(attack.get(i) - player2.lastY);
                        }
                        player2.attackY -= attack.size() - difference;
                        player2.attackBoardFull = true;
                    } else {
                        for (int i = attack.size(); i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                player2AttackBoard[i - attack.size()][j] = player2AttackBoard[i][j];
                            }
                        }
                        for (int i = 0; i < attack.size(); i++) {
                            player2.attackLine.add(attack.get(i) - player2.lastY);
                        }
                        player2.attackY -= attack.size();
                    }
                    //int firstY = player2.attackY - player2.attackLineCount;
                    int firstY = player2.attackY;
                    for (int i = 9; i > 9 - difference; i--) {
                        for (int j = 0; j < player2AttackBoard[0].length; j++) {
                            player2AttackBoard[i][j] = 1;
                        }
                    }
                } else {
                    if (attack.size() > 10) {
                        difference = attack.size() - 10;
                        for (int i = 0; i < difference; i++) {
                            player2.attackLine.add(attack.get(i) - player2.lastY);
                        }
                        int firstY = player2.attackY;
                        for (int i = firstY; i > firstY - difference; i--, player2.attackY--) {
                            for (int j = 0; j < player2AttackBoard[0].length; j++) {
                                player2AttackBoard[i][j] = 1;
                            }
                        }
                        player2.alreadyAttacked = true;
                    } else {
                        for (int i = 0; i < attack.size(); i++) {
                            player2.attackLine.add(attack.get(i) - player2.lastY);
                        }
                        int firstY = player2.attackY;
                        for (int i = firstY; i > firstY - attack.size(); i--, player2.attackY--) {
                            for (int j = 0; j < player2AttackBoard[0].length; j++) {
                                player2AttackBoard[i][j] = 1;
                            }
                        }
                        player2.alreadyAttacked = true;
                    }
                }
            }
            if (lineCounter(player2AttackBoard) == 10) {
                player2.attackBoardFull = true;
            }
        } else {
            System.out.println("this");
        }
        //drawAttack();
    }

    public static void drawAttack() {
        if (restart) {
            int[][] attackP1 = player1.getAttackBoard();
            int[][] attackP2 = player2.getAttackBoard();
            player1.sbByAttack = new StringBuffer();
            player2.sbByAttack = new StringBuffer();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    attackP1[i][j] = 0;
                    attackP2[i][j] = 0;
                    player1.sbByAttack.append(" ");
                    player2.sbByAttack.append(" ");
                }
                player1.sbByAttack.append("\n");
                player2.sbByAttack.append("\n");
            }
            player2.attackArea.setText(player1.sbByAttack.toString());
            player1.attackArea.setText(player2.sbByAttack.toString());

        }
        if (player1.whoIs == true) {
            player1.sbByAttack = new StringBuffer();
            int[][] player1AttackBoard = player1.getAttackBoard();
            for (int i = 0; i < player1AttackBoard.length; i++) {
                for (int j = 0; j < player1AttackBoard[i].length; j++) {
                    if (player1AttackBoard[i][j] == 1) {
                        player1.sbByAttack.append("■");
                    } else {
                        player1.sbByAttack.append(" ");
                    }
                }
                player1.sbByAttack.append("\n");
            }
            player2.attackArea.setText(player1.sbByAttack.toString());
            player2.attackDoc.setParagraphAttributes(0, player2.attackDoc.getLength(), player2.stylesetAk, false);
            player2.whoAttacked = true;
            lineCountByPlayer();
            player1.whoIs = false;
        } else if (player2.whoIs == true) {
            player2.sbByAttack = new StringBuffer();
            player2.sbByAttack.append("\n");
            int[][] player2AttackBoard = player2.getAttackBoard();
            for (int i = 0; i < player2AttackBoard.length; i++) {
                for (int j = 0; j < player2AttackBoard[i].length; j++) {
                    if (player2AttackBoard[i][j] == 1) {
                        player2.sbByAttack.append("■");
                    } else {
                        player2.sbByAttack.append(" ");
                    }
                }
                player2.sbByAttack.append("\n");
            }
            player1.attackArea.setText(player2.sbByAttack.toString());
            player1.attackDoc.setParagraphAttributes(0, player1.attackDoc.getLength(), player1.stylesetAk, false);
            player1.whoAttacked = true;
            lineCountByPlayer();
            player2.whoIs = false;
        }
    }

    public static void drawEmptyAttack() {
        if (player1.whoIs == true) {
            player1.sbByAttack = new StringBuffer();
            int[][] player1AttackBoard = player1.getAttackBoard();
            for (int i = 0; i < player1AttackBoard.length; i++) {
                for (int j = 0; j < player1AttackBoard[i].length; j++) {
                    if (player1AttackBoard[i][j] == 1) {
                        player1.sbByAttack.append("■");
                    } else {
                        player1.sbByAttack.append(" ");
                    }
                }
                player1.sbByAttack.append("\n");
            }
            player2.attackArea.setText(player1.sbByAttack.toString());
            player2.attackDoc.setParagraphAttributes(0, player2.attackDoc.getLength(), player2.stylesetAk, false);
            player2.whoAttacked = true;
            lineCountByPlayer();
            player1.whoIs = false;
        } else if (player2.whoIs == true) {
            player2.sbByAttack = new StringBuffer();
            player2.sbByAttack.append("\n");
            int[][] player2AttackBoard = player2.getAttackBoard();
            for (int i = 0; i < player2AttackBoard.length; i++) {
                for (int j = 0; j < player2AttackBoard[i].length; j++) {
                    if (player2AttackBoard[i][j] == 1) {
                        player2.sbByAttack.append("■");
                    } else {
                        player2.sbByAttack.append(" ");
                    }
                }
                player2.sbByAttack.append("\n");
            }
            player1.attackArea.setText(player2.sbByAttack.toString());
            player1.attackDoc.setParagraphAttributes(0, player1.attackDoc.getLength(), player1.stylesetAk, false);
            player1.whoAttacked = true;
            lineCountByPlayer();
            player2.whoIs = false;
        }
    }

    public static int lineCountByPlayer() {
        if (player1.whoIs == true) {
            int[][] player1AttackBoard = player1.getAttackBoard();
            player2.attackLineCount = lineCounter(player1AttackBoard);
            return player2.attackLineCount;
        }
        if (player2.whoIs == true) {
            int[][] player2AttackBoard = player2.getAttackBoard();
            player1.attackLineCount = lineCounter(player2AttackBoard);
            return player1.attackLineCount;
        }
        return 0;
    }

    public static void forAttack() {
        if (player1.whoAttacked) {
            if (player1.whoIs) {
                player2.alreadyAttacked = false;
                int[][] player2AttackBoard = player2.getAttackBoard();
                int[][] player1Board = player1.getBoard();
                player1.attackLineCount = lineCounter(player2AttackBoard);
                for (int a = player1.attackLineCount; a < HEIGHT; a++) {
                    for (int b = 0; b < WIDTH; b++) {
                        player1Board[a - player1.attackLineCount][b] = player1Board[a][b];
                    }
                }
                for (int a = 0; a < player1.attackLineCount; a++) {
                    for (int b = 0; b < player2AttackBoard[0].length; b++) {
                        player1Board[a + player1.HEIGHT - player1.attackLineCount][b] = player2AttackBoard[a + player1.HEIGHT - player1.attackLineCount - 10][b] * 13;
                    }
                }
                player1.drawBoard();
                player2.sbByAttack.delete(0, player2.sbByAttack.toString().length());
                for (int a = 0; a < 10; a++) {
                    for (int b = 0; b < 10; b++) {
                        player2AttackBoard[a][b] = 0;
                    }
                }
                player1.attackArea.setText(player2.sbByAttack.toString());
                player1.attackDoc.setParagraphAttributes(0, player1.attackDoc.getLength(), player1.stylesetAk, false);
                player1.whoAttacked = false;
                //Timer player1Timer = player1.getTimer();
                //player1Timer.setDelay(10000);
            } else {
                player2.alreadyAttacked = false;
                int[][] player2AttackBoard = player2.getAttackBoard();
                int[][] player1Board = player1.getBoard();
                player1.attackLineCount = lineCounter(player2AttackBoard);
                for (int a = player1.attackLineCount; a < HEIGHT; a++) {
                    for (int b = 0; b < WIDTH; b++) {
                        player1Board[a - player1.attackLineCount][b] = player1Board[a][b];
                    }
                }
                for (int a = 0; a < player1.attackLineCount; a++) {
                    for (int b = 0; b < player2AttackBoard[0].length; b++) {
                        player1Board[a + player1.HEIGHT - player1.attackLineCount][b] = player2AttackBoard[a + player1.HEIGHT - player1.attackLineCount - 10][b] * 13;
                    }
                }
                player1.drawBoard();
                player2.sbByAttack.delete(0, player2.sbByAttack.toString().length());
                for (int a = 0; a < 10; a++) {
                    for (int b = 0; b < 10; b++) {
                        player2AttackBoard[a][b] = 0;
                    }
                }
                player1.attackArea.setText(player2.sbByAttack.toString());
                player1.attackDoc.setParagraphAttributes(0, player1.attackDoc.getLength(), player1.stylesetAk, false);
                player1.whoAttacked = false;
                //Timer player1Timer = player1.getTimer();
                //player1Timer.setDelay(10000);
            }
        } else if (player2.whoAttacked) {
            player1.alreadyAttacked = false;
            int[][] player1AttackBoard = player1.getAttackBoard();
            int[][] player2Board = player2.getBoard();
            player2.attackLineCount = lineCounter(player1AttackBoard);
            for (int a = player2.attackLineCount; a < HEIGHT; a++) {
                for (int b = 0; b < WIDTH; b++) {
                    player2Board[a - player2.attackLineCount][b] = player2Board[a][b];
                }
            }
            for (int a = 0; a < player2.attackLineCount; a++) {
                for (int b = 0; b < player1AttackBoard[0].length; b++) {
                    player2Board[a + player2.HEIGHT - player2.attackLineCount][b] = player1AttackBoard[a + player2.HEIGHT - player2.attackLineCount - 10][b] * 13;
                }
            }
            player2.drawBoard();
            player1.sbByAttack.delete(0, player1.sbByAttack.toString().length());
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 10; b++) {
                    player1AttackBoard[a][b] = 0;
                }
            }
            player2.attackArea.setText(player1.sbByAttack.toString());
            player2.attackDoc.setParagraphAttributes(0, player2.attackDoc.getLength(), player2.stylesetAk, false);
            player2.whoAttacked = false;
        }

    }

    public static int lineCounter(int[][] playerAttackBoard) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerAttackBoard[i][j] == 1) {
                    return 10 - i;
                }
            }
        }
        return 0;
    }
}