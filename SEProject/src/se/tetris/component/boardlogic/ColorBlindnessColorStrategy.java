package se.tetris.component.boardlogic;

import se.tetris.blocks.Block;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ColorBlindnessColorStrategy implements ColorBlindnessStrategy {
    @Override
    public void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        StyleConstants.setForeground(styleSet, block.getColor());
    }

    @Override
    public void colorBlock(int block, SimpleAttributeSet stylesetCur, StyledDocument boardDoc, int offset, int j) {
        switch (block) {
            case 1:
                StyleConstants.setForeground(stylesetCur, Color.CYAN);
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 2:
                StyleConstants.setForeground(stylesetCur, Color.BLUE);
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 3:
                StyleConstants.setForeground(stylesetCur, Color.PINK);
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 4:
                StyleConstants.setForeground(stylesetCur, Color.YELLOW);
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);

                break;
            case 5:
                StyleConstants.setForeground(stylesetCur, Color.GREEN);
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 6:
                StyleConstants.setForeground(stylesetCur, Color.MAGENTA);
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 7:
                StyleConstants.setForeground(stylesetCur, Color.RED);
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
        }
    }
}
