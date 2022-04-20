package se.tetris.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import se.tetris.component.Board;
import se.tetris.setting.SettingCode;


public class Start extends JFrame {
	public static Start start = new Start();
	//public static SettingCode settingMain;

	public Start() {

		setTitle("SeoulTech SE Tettris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridLayout grid = new GridLayout(7,1,0,10);
		Container startView = getContentPane();



		startView.setLayout(grid);
		startView.setBackground(Color.WHITE);

		JLabel Title = new JLabel("SeoulTech SE Tettris");
		Title.setFont(new Font("Serif",Font.BOLD,17));
		Title.setHorizontalAlignment(JLabel.CENTER);
		Title.setVerticalAlignment(JLabel.CENTER);

		JLabel Team = new JLabel("Team one");
		Team.setHorizontalAlignment(JLabel.CENTER);
		Team.setVerticalAlignment(JLabel.CENTER);

		//JPanel btnGroup = new JPanel();
		//btnGroup.setBackground(Color.white);
		//btnGroup.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


		JButton stdBtn = new JButton("기본 모드");
		JButton itemBtn = new JButton("아이템 모드");
		JButton scoreBtn = new JButton("스코어");
		JButton settingBtn = new JButton("설정");
		JButton endBtn = new JButton("종료");

		startView.add(Title);
		//startView.add(btnGroup);

		startView.add(stdBtn);
		startView.add(itemBtn);
		startView.add(scoreBtn);
		startView.add(settingBtn);
		startView.add(endBtn);

		startView.add(Team);

		stdBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Board.getBoard() == null) {
					Board.boardMain = new Board();
					Board.boardMain.setSize(400, 600);
				}
                /*
                int sizeNumber = SettingCode.getSizeNumber();
                switch (sizeNumber) {
                    case 1:
                        main.setSize(400, 600);
                        break;
                    case 2:
                        main.setSize(800, 1200);
                        break;
                    case 3:
                        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        main.setUndecorated(true);
                        break;
                    default:
                        main.setSize(400, 600);
                        break;
                }
                 */
				Board.boardMain.setVisible(true);
				setVisible(false);
			}
		});

		settingBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (SettingCode.getSettingCode() == null) {
					SettingCode.setting = new SettingCode();
					SettingCode.setting.setSize(400, 600);
				}
                /*
                int sizeNumber = SettingCode.getSizeNumber();
                switch (sizeNumber) {
                    case 1:
                        settingMain.setSize(400, 600);
                        break;
                    case 2:
                        settingMain.setSize(800, 1200);
                        break;
                    case 3:
                        settingMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        settingMain.setUndecorated(true);
                        break;
                    default:
                        settingMain.setSize(400, 600);
                        break;
                }
                */
				SettingCode.setting.setVisible(true);
				setVisible(false);
			}
		});

		class MyActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		}
	}

	public static Start getStart(){
		return start;
	}
}