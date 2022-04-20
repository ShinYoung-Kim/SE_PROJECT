package se.tetris.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import se.tetris.blocks.Block;
import se.tetris.blocks.IBlock;
import se.tetris.blocks.JBlock;
import se.tetris.blocks.LBlock;
import se.tetris.blocks.OBlock;
import se.tetris.blocks.SBlock;
import se.tetris.blocks.TBlock;
import se.tetris.blocks.ZBlock;

import se.tetris.setting.SettingCode;

public class Board extends JFrame {

	public static Board boardMain;
	//public static SettingCode setting;

	private static final long serialVersionUID = 2434035659171694595L;

	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final char BORDER_CHAR = 'X';

	double min;
	double max;
	double weighted;
	Random rnd;
	int block;

	private static JTextPane tetrisArea;
	private static JTextPane nextArea;
	private JPanel panel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private static JPanel scorePanel;
	private static JPanel levelPanel;
	private static int[][] board;
	private static int[][] nextBoard;
	private KeyListener playerKeyListener;
	private static SimpleAttributeSet stylesetBr;
	private static SimpleAttributeSet stylesetNx;
	private static SimpleAttributeSet stylesetCur;
	private static StyledDocument boardDoc;
	private static StyledDocument nextDoc;
	public static Timer timer;
	private static Block curr;
	private static Block next;
	static int x = 3; //Default Position.
	static int y = 0;
	int nextX = 1;
	int nextY = 1;
	public static int score = 0;
	public static int level = 0;

	static JLabel scoreLb1 = new JLabel("Scores");
	static JLabel scoreLb2 = new JLabel(Integer.toString(score));
	static JLabel levelLb1 = new JLabel("Level");
	static JLabel levelLb2 = new JLabel(Integer.toString(level));

	//initInterval 난이도에 따라 조절
	//public static int initEasyInterval = 2000;
	//public static int initNormalInterval = 1000;
	//public static int initHardInterval = 500;
	public static int initInterval = SettingCode.getInitInterval();

	//만들어진 블럭 개수 세기
	private static int blockNumber = 0;

	public Board() {
		super("SeoulTech SE Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Board display setting.
		tetrisArea = new JTextPane();
		tetrisArea.setEditable(false);
		tetrisArea.setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
		tetrisArea.setBorder(border);

		nextArea = new JTextPane();
		nextArea.setEditable(false);
		nextArea.setBackground(Color.BLACK);
		nextArea.setBorder(border);


		scorePanel = new JPanel();
		EtchedBorder scoreBorder = new EtchedBorder();
		scorePanel.setBorder(scoreBorder);
		scorePanel.setPreferredSize(new Dimension(150, 50));

		scoreLb1.setForeground(Color.darkGray);
		scoreLb1.setAlignmentX(CENTER_ALIGNMENT);

		scoreLb2.setForeground(Color.RED);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.add(scoreLb1);
		scorePanel.add(Box.createVerticalStrut(5));
		scorePanel.add(scoreLb2);


		levelPanel = new JPanel();
		levelPanel.setBorder(scoreBorder);
		levelPanel.setPreferredSize(new Dimension(150, 50));

		levelLb1.setForeground(Color.darkGray);
		levelLb1.setAlignmentX(CENTER_ALIGNMENT);

		levelLb2.setForeground(Color.BLUE);
		levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
		levelPanel.add(levelLb1);
		levelPanel.add(Box.createVerticalStrut(5));
		levelPanel.add(levelLb2);


		leftPanel = new JPanel();
		leftPanel.add(tetrisArea);
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(nextArea);
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(scorePanel);
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(levelPanel);

		panel = new JPanel();
		panel.add(leftPanel);
		panel.add(rightPanel);

		add(panel);


		//Set timer for block drops.
		//timer 수정
		timer = new Timer(getInterval(blockNumber), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				drawBoard();
			}
		});

		//Initialize board for the game.
		board = new int[HEIGHT][WIDTH];
		nextBoard = new int[4][5];
		playerKeyListener = new PlayerKeyListener();
		addKeyListener(playerKeyListener);
		setFocusable(true);
		requestFocus();

		//Create the first block and draw
		curr = getRandomBlock(SettingCode.modeChoose);
		next = getRandomBlock(SettingCode.modeChoose);

		//Document default style.
		stylesetBr = new SimpleAttributeSet();
		StyleConstants.setFontSize(stylesetBr, 20);
		StyleConstants.setFontFamily(stylesetBr, "Courier New");
		StyleConstants.setBold(stylesetBr, true);
		StyleConstants.setForeground(stylesetBr, Color.WHITE);
		StyleConstants.setAlignment(stylesetBr, StyleConstants.ALIGN_CENTER);

		stylesetCur = new SimpleAttributeSet();
		StyleConstants.setFontSize(stylesetCur, 20);
		StyleConstants.setFontFamily(stylesetCur, "Courier New");
		StyleConstants.setBold(stylesetCur, true);
		StyleConstants.setAlignment(stylesetCur, StyleConstants.ALIGN_CENTER);

		stylesetNx = new SimpleAttributeSet();
		StyleConstants.setFontSize(stylesetNx, 25);
		StyleConstants.setFontFamily(stylesetNx, "Courier New");
		StyleConstants.setBold(stylesetNx, true);
		StyleConstants.setAlignment(stylesetNx, StyleConstants.ALIGN_CENTER);

		boardDoc = tetrisArea.getStyledDocument();
		nextDoc = nextArea.getStyledDocument();

		placeBlock();
		drawBoard();
		placeNext();
		drawNext();

		timer.start();
	}

	private Block getRandomBlock(int modeChoose) {
        /*
        Random rnd = new Random(System.currentTimeMillis());
        int block = rnd.nextInt(6);
        switch(block) {
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
        return new LBlock();
        */
		switch (modeChoose) {
			case 1:
				min = 1;
				max = 100;
				weighted = Math.random() * (max - min) + min;
				if (weighted <= (80/7 + 20))
					return new IBlock();
				else
				{
					rnd = new Random(System.currentTimeMillis());
					block = rnd.nextInt(7);
					switch(block) {
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
				rnd = new Random(System.currentTimeMillis());
				block = rnd.nextInt(7);
				switch(block) {
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
				if (weighted <= (120/7 - 20))
					return new IBlock();
				else
				{
					rnd = new Random(System.currentTimeMillis());
					block = rnd.nextInt(7);
					switch(block) {
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
		return new LBlock();
	}


	private void placeBlock() {
		for(int j=0; j<curr.height(); j++) {
			for(int i=0; i<curr.width(); i++) {
				board[y+j][x+i] = curr.getShape(i, j);
			}
		}
	}

	private void placeNext() {
		for(int j = 0; j < next.height(); j++) {
			for(int i=0; i<next.width(); i++) {
				nextBoard[nextY+j][nextX+i] = next.getShape(i, j);
			}
		}
	}

	private void eraseCurr() {
		for(int i=x; i<x+curr.width(); i++) {
			for(int j=y; j<y+curr.height(); j++) {
				board[j][i] = 0;
			}
		}
	}

	private void eraseNext() {
		for(int i = nextX; i < nextX + next.width(); i++) {
			for(int j=nextY; j< nextY + next.height(); j++) {
				nextBoard[j][i] = 0;
			}
		}
	}

	protected void moveDown() {
		eraseCurr();
		if(y < HEIGHT - curr.height()) y++;
		else {
			placeBlock();
			curr = next;
			eraseNext();
			next = getRandomBlock(SettingCode.modeChoose);
			placeNext();
			drawNext();
			x = 3;
			y = 0;
		}
		placeBlock();
	}

	protected void moveRight() {
		eraseCurr();
		if(x < WIDTH - curr.width()) x++;
		placeBlock();
	}

	protected void moveLeft() {
		eraseCurr();
		if(x > 0) {
			x--;
		}
		placeBlock();
	}

	public static void drawBoard() {
		StringBuffer sb = new StringBuffer();
		for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
		sb.append("\n");
		for(int i=0; i < board.length; i++) {
			sb.append(BORDER_CHAR);
			for(int j=0; j < board[i].length; j++) {
				if(board[i][j] == 1) {
					sb.append("■");
				} else {
					sb.append(" ");
				}
			}
			sb.append(BORDER_CHAR);
			sb.append("\n");
		}
		for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
		tetrisArea.setText(sb.toString());
		boardDoc.setCharacterAttributes(0, boardDoc.getLength(), stylesetBr, false);

		for(int j = 0; j < curr.height(); j++) {
			int rows = y+j == 0 ? 1 : y+j+1;
			int offset = rows * (WIDTH+3) + x + 1;
			if (SettingCode.setting.colorBlindModeCheck == 1) {
				StyleConstants.setForeground(stylesetCur, curr.getColorBlind());
			} else {
				StyleConstants.setForeground(stylesetCur, curr.getColor());
			}
			boardDoc.setCharacterAttributes(offset, curr.width(), stylesetCur, true);
		}
	}
	//blockNumber 증가 + timer 변경
	public static void drawNext() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		blockNumber++;
		timer.setDelay(getInterval(blockNumber));
		for(int i=0; i < nextBoard.length; i++) {
			for(int j=0; j < nextBoard[i].length; j++) {
				if(nextBoard[i][j] == 1) {
					sb.append("■");
				} else {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		nextArea.setText(sb.toString());
		if (SettingCode.setting.colorBlindModeCheck == 1) {
			StyleConstants.setForeground(stylesetNx, next.getColorBlind());
		} else {
			StyleConstants.setForeground(stylesetNx, next.getColor());
		}

		nextDoc.setParagraphAttributes(0, nextDoc.getLength(), stylesetNx, false);
	}

	//interval 함수
	static int getInterval(int blockNumber) {
		switch (blockNumber) {
			case 2:
				initInterval *= 0.9;
				break;
			case 4:
				initInterval *= 0.95;
				break;
			case 6:
				initInterval *= 0.95;
				break;
			case 8:
				initInterval *= 0.95;
				break;
			case 10:
				initInterval *= 0.95;
				break;
		}
		return initInterval;
	}

	public void reset() {
		this.board = new int[20][10];
	}

	public class PlayerKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					moveDown();
					drawBoard();
					break;
				case KeyEvent.VK_RIGHT:
					moveRight();
					drawBoard();
					break;
				case KeyEvent.VK_LEFT:
					moveLeft();
					drawBoard();
					break;
				case KeyEvent.VK_UP:
					eraseCurr();
					curr.rotate();
					drawBoard();
					break;
				case KeyEvent.VK_ESCAPE:
					timer.stop();
					String[] stopOption = {"Restart", "Play", "Exit"};
					int choice = JOptionPane.showOptionDialog(null, "What Do You Want?", "Stop", 0, 0, null, stopOption,stopOption[1]);
					switch(choice) {
						case 0:
							int confirm1 = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
							if (confirm1 == 0) {
								reset();
								score = 0;
								level = 0;
								timer.restart();
							}
							else {
								timer.start();
							}
							break;
						case 1:
							timer.start();
							break;
						case 2:
							int confirm2 = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
							if (confirm2 == 0) {
								dispose(); //or save score and move to score board.
							}
							else {
								timer.start();
							}
							break;
					}
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}
	public static void setSize(int size) {
		StyleConstants.setFontSize(stylesetBr, size);
		StyleConstants.setFontSize(stylesetCur, size);
		StyleConstants.setFontSize(stylesetNx, size+5);
		drawBoard();
		drawNext();
	}


	//max - (200, 60), default - (150, 50)
	public static void setRtSize(int xSize, int ySize) {
		scorePanel.setPreferredSize(new Dimension(xSize, ySize));
		levelPanel.setPreferredSize(new Dimension(xSize, ySize));
		nextArea.setPreferredSize(new Dimension(xSize, ySize * 4));
	}

	//max - 17, default - nothing,
	public static void setLbSize(int size) {
		scoreLb1.setFont(new Font(null, Font.BOLD, size));
		scoreLb2.setFont(new Font(null, Font.BOLD, size));
		levelLb1.setFont(new Font(null, Font.BOLD, size));
		levelLb2.setFont(new Font(null, Font.BOLD, size));
	}
	public static Board getBoard(){
		return boardMain;
	}
}