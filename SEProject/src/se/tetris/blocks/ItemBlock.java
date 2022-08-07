package se.tetris.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ItemBlock extends Block {
    Block blockShape;
    int[] itemCoor = new int[2];
    int itemType;
    ArrayList<int[]> coordinates = new ArrayList<int[]>();

    public ItemBlock() {

    }

    public ItemBlock(Block input) {
        blockShape = input;
        coorColl();
    }


    public void coorColl() {
        for (int i = 0; i < blockShape.height(); i++) {
            for (int j = 0; j < blockShape.width(); j++) {
                if (blockShape.getShape(j, i) > 0) {
                    coordinates.add(new int[]{i, j});
                }
            }
        }
    }

    public void setItemCoor() {
        Random rnd = new Random(System.currentTimeMillis());
        int item = rnd.nextInt(coordinates.size());
        itemCoor[0] = coordinates.get(item)[0];
        itemCoor[1] = coordinates.get(item)[1];
        blockShape.shape[itemCoor[0]][itemCoor[1]] = itemType;
    }

    public Block getItemBlock() {
        return blockShape;
    }

    public int getItemType() {
        return itemType;
    }
}