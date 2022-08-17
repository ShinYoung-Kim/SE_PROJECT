package se.tetris.component.itemboardui;

import se.tetris.blocks.Block;
import se.tetris.blocks.OBlock;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ItemBoardTetrisTextPane extends JTextPane {
    private final JTextPane tetrisTextPane;
    private static final char BORDER_CHAR = 'X';
    private int[][] board = new int[10][10];
    private static StyledDocument boardDoc;
    private static Block curr;
    private static SimpleAttributeSet stylesetBr;
    private static SimpleAttributeSet stylesetCur;
    private static int x = 3; //Default Position.
    private static int y = 0;
    private final SettingValues setting = SettingValues.getInstance();

    ItemBoardTetrisTextPane() {
        tetrisTextPane = new JTextPane();
        tetrisTextPane.setEditable(false);
        tetrisTextPane.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        tetrisTextPane.setBorder(border);
        tetrisTextPane.setAlignmentX(CENTER_ALIGNMENT);
        tetrisTextPane.setAlignmentY(CENTER_ALIGNMENT);
        tetrisTextPane.setPreferredSize(new Dimension(250, 460));

        curr = new OBlock();

        stylesetBr = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetBr, 20);
        StyleConstants.setFontFamily(stylesetBr, "Courier New");
        StyleConstants.setBold(stylesetBr, true);
        StyleConstants.setForeground(stylesetBr, Color.WHITE);
        StyleConstants.setAlignment(stylesetBr, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetBr, -0.45f);

        stylesetCur = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetCur, 20);
        StyleConstants.setFontFamily(stylesetCur, "Courier New");
        StyleConstants.setBold(stylesetCur, true);
        StyleConstants.setAlignment(stylesetCur, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetCur, -0.45f);

        boardDoc = tetrisTextPane.getStyledDocument();

        //placeBlock();
        //drawBoard();
    }

    public void placeBlock() {
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                board[j][i] = 1;
            }
        }
    }

    public void drawBoard() {
        StringBuffer sb = new StringBuffer();
        for (int t = 0; t < WIDTH + 2; t++) sb.append(BORDER_CHAR);
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            sb.append(BORDER_CHAR);
            for (int j = 0; j < board[i].length; j++) {
                int blockType = board[i][j];
                switch (blockType) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 13:
                        sb.append("■");
                        break;
                    case 8:
                        sb.append("L");
                        break;
                    case 9:
                        sb.append("●");
                        break;
                    case 10:
                        sb.append("×");
                        break;
                    case 11:
                        sb.append("C");
                        break;
                    case 12:
                        sb.append("O");
                        break;
                    default:
                        sb.append(" ");

                }
            }
            sb.append(BORDER_CHAR);
            sb.append("\n");
        }
        for (int t = 0; t < WIDTH + 2; t++) sb.append(BORDER_CHAR);
        tetrisTextPane.setText(sb.toString());
        boardDoc.setParagraphAttributes(0, boardDoc.getLength(), stylesetBr, false);


        for (int j = 0; j < curr.height(); j++) {
            int rows = y + j == 0 ? 1 : y + j + 1;
            int offset = rows * (WIDTH + 3) + x + 1;
            for (int i = 0; i < curr.width(); i++) {
                if (curr.getShape(i, j) > 0 && curr.getShape(i, j) < 8) {
                    colorBlindModeCurrent(offset + i);
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            int offset = (i + 1) * (WIDTH + 3) + 1;
            for (int j = 0; j < board[0].length; j++) {
                int block = board[i][j];
                switch (block) {
                    case 1:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(0, 58, 97));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.CYAN);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 2:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(126, 98, 61));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.BLUE);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 3:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(165, 148, 159));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.PINK);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 4:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(187, 190, 242));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.YELLOW);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 5:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(247, 193, 121));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.GREEN);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 6:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(154, 127, 112));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.MAGENTA);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 7:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(99, 106, 141));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.RED);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                }
            }
        }
    }

    private void colorBlindModeCurrent(int offset) {
        colorBlindMode(stylesetCur, curr);
        boardDoc.setCharacterAttributes(offset, 1, stylesetCur, true);
    }

    private void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        if (setting.colorBlindModeCheck == 1) {
            StyleConstants.setForeground(styleSet, block.getColorBlind());
        } else {
            StyleConstants.setForeground(styleSet, block.getColor());
        }
    }



    public static void main(String[] args) {
        ItemBoardTetrisTextPane itemBoardTetrisTextPane = new ItemBoardTetrisTextPane();
        JFrame main = new JFrame();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.add(itemBoardTetrisTextPane);
        main.setPreferredSize(new Dimension(250, 460));
        main.setVisible(true);
    }
}
