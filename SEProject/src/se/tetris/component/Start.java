package se.tetris.component;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.beans.ConstructorProperties;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import se.tetris.component.Board;
import se.tetris.setting.SettingCode;
import se.tetris.component.*;
import se.tetris.data.*;

import se.tetris.setting.SettingValues;

public class Start extends JFrame {
	public static Start start;
	int KeyCount = 0;
	int KeyFoucus = 0;

	DBCalls dataCalls = new DBCalls();

	int Window = dataCalls.getWindowSetting();
	int colorMode = dataCalls.getColorSetting();
	int levelMode = dataCalls.getLevelSetting();
	SettingCode setting = new SettingCode();

	public void startStdMode() {
		Board main = new Board();
		if (Window == 0) {
			main.setSize(400, 600);
			main.changeSize(1);
		} else if (Window == 1) {
			main.setSize(800, 800);
			main.changeSize(2);
		} else {
			main.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
			main.changeSize(3);
		}

		main.setVisible(true);

		setVisible(false);
	}

	public void startItemMode() {
		ItemBoard itemBoard = new ItemBoard();

		if (Window == 0) {
			itemBoard.setSize(400, 600);
			itemBoard.changeSize(1);
		} else if (Window == 1) {
			itemBoard.setSize(800, 800);
			itemBoard.changeSize(2);
		} else {
			itemBoard.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
			itemBoard.changeSize(3);
		}

		itemBoard.setVisible(true);
		setVisible(false);
	}

	public void startScoreMode() {
		Score scoreView = new Score();
		if (Window == 0) {
			scoreView.setSize(400, 600);
		} else if (Window == 1) {
			scoreView.setSize(800, 800);
		} else {
			scoreView.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
		}
		scoreView.setVisible(true);
		setVisible(false);
	}

	public void startBattleMode() {
		StartBattle battleMode = new StartBattle();
		if (Window == 0) {
			battleMode.setSize(400, 600);
		} else if (Window == 1) {
			battleMode.setSize(800, 800);
		} else {
			battleMode.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
		}
		battleMode.setVisible(true);
		setVisible(false);
	}

	public void startSettingMode() {
		if (Window == 0) {
			setting.setSize(400, 600);
		} else if (Window == 1) {
			setting.setSize(800, 800);
		} else {
			setting.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
		}
		setting.setVisible(true);
		setVisible(false);
	}

	public void startExitMode() {
		System.exit(0);
	}

	ImageIcon backImg = new ImageIcon("./SEProject/img/bg_one.png");
	Image rebackFill = backImg.getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH);

	ImageIcon backImg2 = new ImageIcon("./SEProject/img/bg_two.png");
	Image rebackFill2 = backImg.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);

	ImageIcon backImg3 = new ImageIcon("./SEProject/img/tettrisbg.png");
	Image rebackFill3 = backImg.getImage().getScaledInstance(SettingCode.screenWidth, SettingCode.screenHeight,
			Image.SCALE_SMOOTH);

	int backX = 0;

	class BackPanel extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (Window == 0) {
				g.drawImage(rebackFill, 0, 0, this);
			} else if (Window == 1) {
				g.drawImage(rebackFill2, 0, 0, this);
			} else {
				g.drawImage(rebackFill3, 0, 0, this);
			}

		}
	}

	class SButton extends JButton {

		Color Impact = new Color(106, 215, 255);
		Color Normal = new Color(0, 0, 0);

		public SButton() {
			super();
			Default();
		}

		@ConstructorProperties({ "text" })
		public SButton(String text) {
			super(text, null);
			Default();
		}

		public void Active() {
			setBackground(Impact);
			setBorder(new LineBorder(Impact));
			setForeground(Color.BLACK);
		}

		public void Default() {
			setBackground(Color.BLACK);
			setBorder(new LineBorder(Impact));

			setForeground(Impact);

			if (Window == 0) {
				setPreferredSize(new Dimension(200, 35));
				setFont(new Font("고딕", Font.CENTER_BASELINE, 12));
			} else if (Window == 1) {
				setPreferredSize(new Dimension(220, 45));
				setFont(new Font("고딕", Font.CENTER_BASELINE, 14));
			} else {
				setPreferredSize(new Dimension(300, 50));
				setFont(new Font("고딕", Font.CENTER_BASELINE, 16));
			}

		}
	}

	public Start() {

		setTitle("SeoulTech SE Tettris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLayeredPane layerPane = new JLayeredPane();

		BackPanel backPanel = new BackPanel();

		ImageIcon titleImg = new ImageIcon("./SEProject/img/title.png");

		Image retitleFill = null;

		if (Window == 0) {
			layerPane.setSize(400, 600);
			backPanel.setSize(400, 600);
			retitleFill = titleImg.getImage().getScaledInstance(160, 150, Image.SCALE_SMOOTH);
		} else if (Window == 1) {
			layerPane.setSize(800, 800);
			backPanel.setSize(800, 800);
			titleImg = new ImageIcon("./SEProject/img/title2.png");
			retitleFill = titleImg.getImage().getScaledInstance(320, 300, Image.SCALE_SMOOTH);
		} else {
			layerPane.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
			backPanel.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
			titleImg = new ImageIcon("./SEProject/img/title3.png");
			retitleFill = titleImg.getImage().getScaledInstance(480, 450, Image.SCALE_SMOOTH);
		}

		if (Window == 0) {
			setting.setSize(400, 600);
			setting.sizeOne.setSelected(true);
			setting.sizeOne.doClick();
		} else if (Window == 1) {
			setting.setSize(800, 800);
			setting.sizeTwo.setSelected(true);
			setting.sizeTwo.doClick();
		} else {
			setting.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
			setting.sizeThree.setSelected(true);
			setting.sizeThree.doClick();
		}
		if (colorMode == 0) {
			setting.colorBlindOne.setSelected(true);
			setting.colorBlindOne.doClick();
		} else if (colorMode == 1) {
			setting.colorBlindTwo.setSelected(true);
			setting.colorBlindTwo.doClick();
		}
		if (levelMode == 0) {
			setting.modeOne.doClick();
			setting.modeOne.setSelected(true);
		} else if (levelMode == 1) {
			setting.modeTwo.doClick();
			setting.modeTwo.setSelected(true);
		} else if (levelMode == 2) {
			setting.modeThree.doClick();
			setting.modeThree.setSelected(true);
		}

		layerPane.setLayout(null);

		ImageIcon retitleImg = new ImageIcon(retitleFill);
		JLabel title = new JLabel(retitleImg);

		JPanel buttonPanel = new JPanel();
		JPanel innerPane = new JPanel();

		layerPane.add(backPanel);

		backPanel.setLayout(new BorderLayout());

		backPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));

		backPanel.add(title, BorderLayout.NORTH);

		buttonPanel.setOpaque(true);
		buttonPanel.setBackground(new Color(0, 0, 0, 0));

		innerPane.setOpaque(true);
		innerPane.setBackground(new Color(0, 0, 0, 0));

		GridLayout grid = new GridLayout(6, 1, 0, 10);

		buttonPanel.setSize(380, 400);

		buttonPanel.add(innerPane);

		innerPane.setLayout(grid);

		SButton stdBtn = new SButton("기본 모드");
		SButton itemBtn = new SButton("아이템 모드");
		SButton scoreBtn = new SButton("스코어");
		SButton settingBtn = new SButton("설정");
		SButton endBtn = new SButton("종료");
		SButton battleBtn = new SButton("대전 모드");

		innerPane.add(stdBtn);
		innerPane.add(itemBtn);
		innerPane.add(battleBtn);
		innerPane.add(scoreBtn);
		innerPane.add(settingBtn);
		innerPane.add(endBtn);

		backPanel.add(buttonPanel, BorderLayout.CENTER);

		setLayout(null);

		add(layerPane);

		if (Board.getBoard() == null) {
			Board.boardMain = new Board();
			Board.boardMain.timer.stop();
			Board.boardMain.setSize(400, 600);
		}
		if (ItemBoard.getItemBoard() == null) {
			ItemBoard.itemBoardMain = new ItemBoard();
			ItemBoard.itemBoardMain.timer.stop();
			ItemBoard.itemBoardMain.setSize(400, 600);
		}

		stdBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startStdMode();
			}
		});

		itemBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startItemMode();
			}
		});

		settingBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startSettingMode();
			}
		});

		scoreBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startScoreMode();
			}
		});

		endBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startExitMode();
			}
		});

		battleBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startBattleMode();
			}
		});

		innerPane.addKeyListener(new KeyListener() {

			public void allBtnNotActive() {
				stdBtn.Default();
				itemBtn.Default();
				battleBtn.Default();
				scoreBtn.Default();
				settingBtn.Default();
				endBtn.Default();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// System.out.println(e);
				// System.out.println(KeyCount);

				if (KeyCount == 0) {
					allBtnNotActive();
					stdBtn.Active();
					KeyFoucus = 0;
				}

				if (e.getKeyCode() == 40) {
					if (KeyFoucus == 0 && KeyCount > 0) {
						allBtnNotActive();
						itemBtn.Active();
						KeyFoucus = 1;
					} else if (KeyFoucus == 1) {
						allBtnNotActive();
						battleBtn.Active();
						KeyFoucus = 2;
					} else if (KeyFoucus == 2) {
						allBtnNotActive();
						scoreBtn.Active();
						KeyFoucus = 3;
					} else if (KeyFoucus == 3) {
						allBtnNotActive();
						settingBtn.Active();
						KeyFoucus = 4;
					} else if (KeyFoucus == 4) {
						allBtnNotActive();
						endBtn.Active();
						KeyFoucus = 5;
					} else if (KeyFoucus == 5) {
						allBtnNotActive();
						endBtn.Active();
						KeyFoucus = 5;
					}
					KeyCount++;
				}

				if (e.getKeyCode() == 38) {
					if (KeyFoucus == 0) {
						allBtnNotActive();
						stdBtn.Active();
						KeyFoucus = 0;
					} else if (KeyFoucus == 1) {
						allBtnNotActive();
						stdBtn.Active();
						KeyFoucus = 0;
					} else if (KeyFoucus == 2) {
						allBtnNotActive();
						itemBtn.Active();
						KeyFoucus = 1;
					} else if (KeyFoucus == 3) {
						allBtnNotActive();
						battleBtn.Active();
						KeyFoucus = 2;
					} else if (KeyFoucus == 4) {
						allBtnNotActive();
						scoreBtn.Active();
						KeyFoucus = 3;
					} else if (KeyFoucus == 5) {
						allBtnNotActive();
						settingBtn.Active();
						KeyFoucus = 4;
					}
					KeyCount++;
				}

				if (e.getKeyCode() == 10) {
					if (KeyFoucus == 0) {
						startStdMode();
					} else if (KeyFoucus == 1) {
						startItemMode();
					} else if (KeyFoucus == 2) {
						startBattleMode();
					} else if (KeyFoucus == 3) {
						startScoreMode();
					} else if (KeyFoucus == 4) {
						startSettingMode();
					} else if (KeyFoucus == 5) {
						startExitMode();
					}
				}

			}
		});

		innerPane.setFocusable(true);
	}

	public static Start getStart() {
		return start;
	}
}