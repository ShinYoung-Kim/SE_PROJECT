package se.tetris.component.boardlogic;

import se.tetris.blocks.Block;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ColorBlindnessColorBlindStrategy implements ColorBlindnessStrategy {
    @Override
    public void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        StyleConstants.setForeground(styleSet, block.getColorBlind());
    }

    @Override
    public void colorBlock(int block, SimpleAttributeSet stylesetCur, StyledDocument boardDoc, int offset, int j) {
        switch (block) {
            case 1:
                StyleConstants.setForeground(stylesetCur, new Color(0, 58, 97));
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 2:
                StyleConstants.setForeground(stylesetCur, new Color(126, 98, 61));
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 3:
                StyleConstants.setForeground(stylesetCur, new Color(165, 148, 159));
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 4:
                StyleConstants.setForeground(stylesetCur, new Color(187, 190, 242));
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 5:
                StyleConstants.setForeground(stylesetCur, new Color(247, 193, 121));
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 6:
                StyleConstants.setForeground(stylesetCur, new Color(154, 127, 112));
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
            case 7:
                StyleConstants.setForeground(stylesetCur, new Color(99, 106, 141));
                boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                break;
        }
    }
}
