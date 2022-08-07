package se.tetris.blocks;

import java.awt.Color;

public class JBlock extends Block {

    public JBlock() {
        shape = new int[][]{
                {2, 2, 2},
                {0, 0, 2}
        };
        color = Color.BLUE;
        colorBlind = new Color(126, 98, 61);
    }
}