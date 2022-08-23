package se.tetris.component.boardlogic;

import se.tetris.blocks.Block;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class StrategyPattern {
    private ColorBlindnessStrategy colorBlindnessStrategy;

    public void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        colorBlindnessStrategy.colorBlindMode(styleSet, block);
    }

    public void colorBlock(int block, SimpleAttributeSet stylesetCur, StyledDocument boardDoc, int offset, int j) {
        colorBlindnessStrategy.colorBlock(block, stylesetCur, boardDoc, offset, j);
    }

    public void colorBlindModeCurrent(int offset, SimpleAttributeSet stylesetCur, StyledDocument boardDoc, Block curr) {
        colorBlindMode(stylesetCur, curr);
        boardDoc.setCharacterAttributes(offset, 1, stylesetCur, true);
    }

    public void setColorBlindnessStrategy(ColorBlindnessStrategy colorBlindnessStrategy) {
        this.colorBlindnessStrategy = colorBlindnessStrategy;
    }
}
