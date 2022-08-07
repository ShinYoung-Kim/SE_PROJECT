package se.tetris.blocks;

import java.awt.Color;

import se.tetris.setting.SettingValues;

public abstract class Block {

    protected int[][] shape;
    protected Color color;
    protected Color colorBlind;
    protected int blockNum;

    final SettingValues setting = SettingValues.getInstance();

    public Block() {
        shape = new int[][]{
                {1, 1},
                {1, 1},
        };
        color = Color.YELLOW;
    }


    public int getShape(int x, int y) {
        return shape[y][x];
    }

    public Color getColor() {
        return color;
    }

    public Color getColorBlind() {
        return colorBlind;
    }


    public void rotate() {
        shape = getRotateShape();
    }

    public int height() {
        return shape.length;
    }

    public int width() {
        if (shape.length > 0)
            return shape[0].length;
        return 0;
    }

    public void setItem(int x, int y, int itemType) {
        shape[y][x] = itemType;
    }

    public int[][] getRotateShape() {
        int[][] rotate = new int[shape[0].length][shape.length];
        for (int i = 0; i < rotate.length; i++) {
            for (int j = 0; j < rotate[i].length; j++) {
                rotate[i][j] = shape[shape.length - 1 - j][i];
            }
        }
        return rotate;
    }

    public void setShape(int[][] inputShape) {
        shape = inputShape;
    }

    public void getInitBlock(Block input) {
        for (int i = 0; i < input.height(); i++) {
            for (int j = 0; j < input.width(); j++) {
                if (input.getShape(j, i) != 0)
                    input.shape[i][j] = input.getShape(j, i);
            }
        }
    }

}