package se.tetris.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartBattle extends JFrame {
    JButton general = new JButton("일반모드");
    JButton item = new JButton("아이템 모드");
    JButton timeLimit = new JButton("시간제한 모드");

    int KeyCount = 0;
    int KeyFoucus = 0;

    public StartBattle(){
        setTitle("SeoulTech SE Tettris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout grid = new GridLayout(5,1,0,10);
        Container settingView = getContentPane();

        settingView.setLayout(grid);
        settingView.setBackground(Color.WHITE);

        JLabel Title = new JLabel("SeoulTech SE Tettris");
        Title.setFont(new Font("Serif",Font.BOLD,17));
        Title.setHorizontalAlignment(JLabel.CENTER);
        Title.setVerticalAlignment(JLabel.CENTER);

        JLabel Team = new JLabel("Team one");
        Team.setHorizontalAlignment(JLabel.CENTER);
        Team.setVerticalAlignment(JLabel.CENTER);

        general.setBackground(null);
        item.setBackground(null);
        timeLimit.setBackground(null);

        settingView.add(Title);
        settingView.add(general);
        settingView.add(item);
        settingView.add(timeLimit);
        settingView.add(Team);

        general.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                BattleBoard battle = new BattleBoard();
                battle.setSize(800, 600);
                battle.setVisible(true);
            }
        });

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        timeLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        settingView.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(KeyCount == 0) {
                    general.setBackground(new Color(106,215,255));
                    KeyFoucus = 0;
                }

                if(e.getKeyCode() == 40) {
                    if(KeyFoucus == 0 && KeyCount>0) {
                        item.setBackground(new Color(106,215,255));
                        general.setBackground(null);
                        timeLimit.setBackground(null);
                        KeyFoucus = 1;
                    }else if(KeyFoucus ==1) {
                        timeLimit.setBackground(new Color(106,215,255));
                        general.setBackground(null);
                        item.setBackground(null);
                        KeyFoucus = 2;
                    }
                    KeyCount++;
                }

                if(e.getKeyCode() == 38) {
                    if(KeyFoucus == 0) {
                        general.setBackground(new Color(106,215,255));
                        item.setBackground(null);
                        timeLimit.setBackground(null);
                        KeyFoucus = 0;
                    }else if(KeyFoucus ==1) {
                        general.setBackground(new Color(106,215,255));
                        item.setBackground(null);
                        timeLimit.setBackground(null);
                        KeyFoucus = 0;
                    }else if(KeyFoucus ==2) {
                        general.setBackground(null);
                        item.setBackground(new Color(106,215,255));
                        timeLimit.setBackground(null);
                        KeyFoucus = 1;
                    }else if(KeyFoucus ==3) {
                        general.setBackground(null);
                        item.setBackground(null);
                        timeLimit.setBackground(new Color(106,215,255));
                        KeyFoucus = 2;
                    }
                    KeyCount++;
                }

                if(e.getKeyCode() == 10) {
                    if(KeyFoucus == 0) {
                        general.doClick();
                    }else if(KeyFoucus ==1) {
                        item.doClick();
                    }else if(KeyFoucus ==2) {
                        timeLimit.doClick();
                    }
                }

            }
        });

        settingView.setFocusable(true);
    }
}
