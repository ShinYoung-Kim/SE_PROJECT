package se.tetris.blocks;

public class LRemoveBlock extends ItemBlock {

	public LRemoveBlock() {
		itemType = 8;
		setItemCoor();
	}

	public LRemoveBlock(Block input) {
		super(input);
		itemType = 8;
		setItemCoor();
	}

}