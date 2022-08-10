package se.tetris.component.boardlogic;

import se.tetris.blocks.*;

public class RandomBlock {
    int min;
    int max;
    double percentage;
    int blockNumber;

    Block randomBlock;

    public Block getRandomBlock(int modeChoose) {
        switch (modeChoose) {
            case 1:
                min = 1;
                max = 100;
                percentage = Math.random() * (max - min) + min;
                if (percentage <= (double) 100 / 720 * 100 * 1.2)
                    return new IBlock();
                else {
                    blockNumber = (int) (Math.random() * 6);
                    switch (blockNumber) {
                        case 0:
                            return new JBlock();
                        case 1:
                            return new LBlock();
                        case 2:
                            return new ZBlock();
                        case 3:
                            return new SBlock();
                        case 4:
                            return new TBlock();
                        case 5:
                            return new OBlock();
                    }
                }
            case 2:
                blockNumber = (int) (Math.random() * 7);
                switch (blockNumber) {
                    case 0:
                        return new IBlock();
                    case 1:
                        return new JBlock();
                    case 2:
                        return new LBlock();
                    case 3:
                        return new ZBlock();
                    case 4:
                        return new SBlock();
                    case 5:
                        return new TBlock();
                    case 6:
                        return new OBlock();
                }
            case 3:
                min = 1;
                max = 100;
                percentage = Math.random() * (max - min) + min;
                if (percentage <= (double) 100 / 680 * 100 * 0.8)
                    return new IBlock();
                else {
                    blockNumber = (int) (Math.random() * 6);
                    switch (blockNumber) {
                        case 0:
                            return new JBlock();
                        case 1:
                            return new LBlock();
                        case 2:
                            return new ZBlock();
                        case 3:
                            return new SBlock();
                        case 4:
                            return new TBlock();
                        case 5:
                            return new OBlock();
                    }
                }
                break;
            default:
                break;
        }
        return new IBlock();
    }
}
