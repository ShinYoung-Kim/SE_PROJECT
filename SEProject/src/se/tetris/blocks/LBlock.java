package se.tetris.blocks;

import java.awt.Color;

public class LBlock extends Block {

    public LBlock() {
        shape = new int[][]{
                {3, 3, 3},
                {3, 0, 0}
        };
        color = Color.PINK;
        colorBlind = new Color(165, 148, 159);
    }
}