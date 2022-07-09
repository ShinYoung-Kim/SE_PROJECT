package se.tetris.setting;

import se.tetris.component.Board;
import se.tetris.component.ItemBoard;
import se.tetris.component.Start;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_UP;

import se.tetris.component.StartBattle;
import se.tetris.data.*;
import static se.tetris.setting.Strings.*;

public class SettingCode extends JFrame {
    public static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screenWidth = (int) (dimension.getWidth());
    public static int screenHeight = (int) (dimension.getHeight());

    private JPanel tetrisArea;
    private JPanel nextArea;
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    public JPanel scorePanel;
    public ScreenSizeSettingPanel screenSizeArea;
    public ColorBlindnessSettingPanel colorBlindnessSettingPanel;
    public DifficultySettingPanel difficultySettingPanel;
    public KeySettingPanel keySettingPanel;

    JPanel rightRight = new JPanel();
    JPanel buttonPanel = new JPanel();

    Font fontSmall = new Font(null, Font.PLAIN, 10);
    Font fontBig = new Font(null, Font.PLAIN, 20);
    Font fontFull = new Font(null, Font.PLAIN, 25);

    final static Color grayMade = new Color(238, 238, 238);

    JLabel settingTitle = new JLabel(settingTitleString);

    JButton scoreReset = new JButton(addingHTMLfun(scoreResetString));
    JButton BackToGame = new JButton(addingHTMLfun(BackToGameString));
    JButton BackToItemGame = new JButton(addingHTMLfun(BackToItemGameString));
    JButton BackToStart = new JButton(addingHTMLfun(BackToStartString));
    JButton BackToBattle = new JButton(addingHTMLfun(BackToBattleString));
    JButton settingReset = new JButton(addingHTMLfun(settingResetString));

    int KeyCount = 0;
    int KeyFoucus = 0;

    DBCalls dataCalls = new DBCalls();

    int Window = dataCalls.getWindowSetting();
    int setColor = dataCalls.getColorSetting();
    int setKey = dataCalls.getKeySetting();
    int setLevel = dataCalls.getLevelSetting();

    public SettingCode() {

        super(frameTitleString);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container settingView = getContentPane();

        // Board display setting.
        tetrisArea = new JPanel();

        tetrisArea.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        tetrisArea.setBorder(border);
        // tetrisArea.setPreferredSize(new Dimension(350, 50));

        settingTitle.setForeground(Color.WHITE);
        tetrisArea.add(settingTitle);

        nextArea = new JPanel();
        // nextArea.setPreferredSize(new Dimension(250, 400));
        nextArea.setBackground(grayMade);
        nextArea.setBorder(border);

        screenSizeArea = new ScreenSizeSettingPanel();
        screenSizeArea.setOnUpdateListener(new ScreenSizeSettingPanel.OnUpdateListener() {
            @Override
            public void onUpdate(ScreenSizeSettingPanel.Resolution value) {
                changeSize(value);
            }
        });

        nextArea.add(screenSizeArea);

        keySettingPanel = new KeySettingPanel();
        nextArea.add(keySettingPanel);

        colorBlindnessSettingPanel = new ColorBlindnessSettingPanel();
        nextArea.add(colorBlindnessSettingPanel);

        difficultySettingPanel = new DifficultySettingPanel();
        nextArea.add(difficultySettingPanel);

        scorePanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        scorePanel.setBorder(scoreBorder);
        // scorePanel.setPreferredSize(new Dimension(80, 100));

        System.out.println(scoreReset.getPreferredSize()); // 136, 26
        // scoreReset.setPreferredSize(new Dimension(80, 100));
        scoreReset.setAlignmentX(CENTER_ALIGNMENT);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(scoreReset);
        // scorePanel.setPreferredSize(new Dimension(80, 100));

        // buttonPanel.setPreferredSize(new Dimension(80, 280));
        EtchedBorder buttonBorder = new EtchedBorder();
        buttonPanel.setBorder(buttonBorder);
        // BackToGame.setPreferredSize(new Dimension(80, 70));
        // BackToItemGame.setPreferredSize(new Dimension(80, 70));
        // BackToStart.setPreferredSize(new Dimension(80, 70));
        // settingReset.setPreferredSize(new Dimension(80, 70));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        System.out.println(BackToGame.getPreferredSize()); // 111, 26
        System.out.println(BackToItemGame.getPreferredSize()); /// 151, 26
        System.out.println(BackToStart.getPreferredSize()); /// 87, 26
        System.out.println(settingReset.getPreferredSize()); /// 96, 26

        BackToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board main = new Board();

                Window = dataCalls.getWindowSetting();

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
        });

        BackToBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartBattle battleMode = new StartBattle();
                Window = dataCalls.getWindowSetting();
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
        });

        BackToItemGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ItemBoard itemBoard = new ItemBoard();

                Window = dataCalls.getWindowSetting();

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
        });

        BackToStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start startView = new Start();

                Window = dataCalls.getWindowSetting();

                if (Window == 0) {
                    startView.setSize(400, 600);
                } else if (Window == 1) {
                    startView.setSize(800, 800);
                } else {
                    startView.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
                }

                startView.setVisible(true);
//				Start.start.setVisible(true);

                setVisible(false);
            }
        });

        settingReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dataCalls.UpdateWindowSetting(0);
                dataCalls.UpdateKeySetting(0);
                dataCalls.UpdateColorSetting(0);
                dataCalls.UpdateLevelSetting(0);

                screenSizeArea.sizeOnefun();
                keySettingPanel.keyOnefun();
                colorBlindnessSettingPanel.colorBlindOneFun();
                difficultySettingPanel.modeTwofun();

            }
        });

        scoreReset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dataCalls.refreshScoreData();

                int Result = JOptionPane.showConfirmDialog(null, resetMessageString, resetTitleString,
                        JOptionPane.DEFAULT_OPTION);
            }
        });

        buttonPanel.add(BackToGame);
        buttonPanel.add(BackToItemGame);
        buttonPanel.add(BackToBattle);
        buttonPanel.add(BackToStart);
        buttonPanel.add(settingReset);

        nextArea.setLayout(new BoxLayout(nextArea, BoxLayout.Y_AXIS));
        nextArea.setAlignmentX(LEFT_ALIGNMENT);

        rightRight.add(scorePanel);
        rightRight.add(Box.createVerticalStrut(2));
        rightRight.add(buttonPanel);
        rightRight.setAlignmentX(CENTER_ALIGNMENT);
        rightRight.setLayout(new BoxLayout(rightRight, BoxLayout.Y_AXIS));
        // rightRight.setPreferredSize(new Dimension(100, 200));

        leftPanel = new JPanel();
        leftPanel.add(tetrisArea);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.add(nextArea);
        rightPanel.add(Box.createVerticalStrut(2));
        rightPanel.add(rightRight);
        // rightPanel.setPreferredSize(new Dimension(350, 450));
        System.out.println(rightPanel.getPreferredSize());
        System.out.println(nextArea.getPreferredSize());
        System.out.println(rightRight.getPreferredSize());
        System.out.println(scorePanel.getPreferredSize());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(leftPanel);
        panel.add(rightPanel);

        settingView.add(panel);

        add(panel);

        tetrisArea.setPreferredSize(new Dimension(350, 50));
        rightRight.setPreferredSize(new Dimension(100, 450));
        nextArea.setPreferredSize(new Dimension(250, 450));
        scoreReset.setPreferredSize(new Dimension(80, 75));
        scorePanel.setPreferredSize(new Dimension(80, 75));
        buttonPanel.setPreferredSize(new Dimension(80, 300));
        BackToItemGame.setPreferredSize(new Dimension(80, 75));
        BackToGame.setPreferredSize(new Dimension(80, 75));
        BackToBattle.setPreferredSize(new Dimension(80, 75));
        BackToStart.setPreferredSize(new Dimension(80, 75));
        settingReset.setPreferredSize(new Dimension(80, 75));
        screenSizeArea.setPreferredSize(new Dimension(250, 110));
        keySettingPanel.setPreferredSize(new Dimension(250, 110));
        colorBlindnessSettingPanel.setPreferredSize(new Dimension(250, 110));
        difficultySettingPanel.setPreferredSize(new Dimension(250, 110));

        scoreReset.setBackground(grayMade);
        BackToGame.setBackground(grayMade);
        BackToBattle.setBackground(grayMade);
        BackToItemGame.setBackground(grayMade);
        BackToStart.setBackground(grayMade);
        settingReset.setBackground(grayMade);

        // Initialize board for the game.

        settingView.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                System.out.println(e);
                System.out.println(KeyCount);

                Point point = settingView.getLocation();
                System.out.println(point);

                if (KeyCount == 0) {
                    settingView.requestFocusInWindow();
                    KeyFoucus = 1;
                }

                if (SettingValues.getInstance().keyChoose == 1) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (KeyFoucus == 1) {
                                difficultySettingPanel.getModeThree().setBackground(Color.gray);
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 3) {
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                screenSizeArea.getSizeTwo().setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                screenSizeArea.getSizeThree().setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                keySettingPanel.getKeyOne().setBackground(Color.gray);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                keySettingPanel.getKeyTwo().setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                difficultySettingPanel.getModeOne().setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                difficultySettingPanel.getModeTwo().setBackground(Color.gray);
                                KeyFoucus = 9;
                            } else if (KeyFoucus == 11) {
                                settingReset.setBackground(Color.gray);
                                scoreReset.setBackground(grayMade);
                                KeyFoucus = 16;
                            } else if (KeyFoucus == 12) {
                                scoreReset.setBackground(Color.gray);
                                BackToGame.setBackground(grayMade);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 13) {
                                BackToGame.setBackground(Color.gray);
                                BackToItemGame.setBackground(grayMade);
                                KeyFoucus = 12;
                            } else if (KeyFoucus == 14) {
                                BackToItemGame.setBackground(Color.gray);
                                BackToBattle.setBackground(grayMade);
                                KeyFoucus = 13;
                            } else if (KeyFoucus == 15) {
                                BackToBattle.setBackground(Color.gray);
                                BackToStart.setBackground(grayMade);
                                KeyFoucus = 14;
                            } else if (KeyFoucus == 16) {
                                BackToStart.setBackground(Color.gray);
                                settingReset.setBackground(grayMade);
                                KeyFoucus = 15;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_DOWN:
                            if (KeyFoucus == 1) {
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                screenSizeArea.getSizeTwo().setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                screenSizeArea.getSizeThree().setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 3) {
                                keySettingPanel.getKeyOne().setBackground(Color.gray);
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                keySettingPanel.getKeyTwo().setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                difficultySettingPanel.getModeOne().setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                difficultySettingPanel.getModeTwo().setBackground(Color.gray);
                                KeyFoucus = 9;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                difficultySettingPanel.getModeThree().setBackground(Color.gray);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                BackToGame.setBackground(Color.gray);
                                KeyFoucus = 12;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                BackToItemGame.setBackground(Color.gray);
                                KeyFoucus = 13;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                BackToBattle.setBackground(Color.gray);
                                KeyFoucus = 14;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                BackToStart.setBackground(Color.gray);
                                KeyFoucus = 15;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                settingReset.setBackground(Color.gray);
                                KeyFoucus = 16;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_LEFT:
                            if (KeyFoucus == 1) {
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (KeyFoucus == 1) {
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case 10:
                            if (KeyFoucus == 1) {
                                screenSizeArea.sizeOnefun();
                            }
                            if (KeyFoucus == 2) {
                                screenSizeArea.sizeTwofun();
                            }
                            if (KeyFoucus == 3) {
                                screenSizeArea.sizeThreefun();
                            }
                            if (KeyFoucus == 4) {
                                keySettingPanel.keyOnefun();
                            }
                            if (KeyFoucus == 5) {
                                keySettingPanel.keyTwofun();
                            }
                            if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.colorBlindOneFun();
                            }
                            if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.colorBlindTwoFun();
                            }
                            if (KeyFoucus == 8) {
                                difficultySettingPanel.modeOnefun();
                            }
                            if (KeyFoucus == 9) {
                                difficultySettingPanel.modeTwofun();
                            }
                            if (KeyFoucus == 10) {
                                difficultySettingPanel.modeThreefun();
                            }

                            if (KeyFoucus == 11) {
                                scoreReset.doClick();
                            }
                            if (KeyFoucus == 12) {
                                BackToGame.doClick();
                            }
                            if (KeyFoucus == 13) {
                                BackToItemGame.doClick();
                            }
                            if (KeyFoucus == 14) {
                                BackToBattle.doClick();
                            }
                            if (KeyFoucus == 15) {
                                BackToStart.doClick();
                            }
                            if (KeyFoucus == 16) {
                                settingReset.doClick();
                            }
                            KeyCount++;
                            break;
                    }
                } else if (SettingValues.getInstance().keyChoose == 2) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                            if (KeyFoucus == 1) {
                                difficultySettingPanel.getModeThree().setBackground(Color.gray);
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 3) {
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                screenSizeArea.getSizeTwo().setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                screenSizeArea.getSizeThree().setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                keySettingPanel.getKeyOne().setBackground(Color.gray);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                keySettingPanel.getKeyTwo().setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                difficultySettingPanel.getModeOne().setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                difficultySettingPanel.getModeTwo().setBackground(Color.gray);
                                KeyFoucus = 9;
                            } else if (KeyFoucus == 11) {
                                settingReset.setBackground(Color.gray);
                                scoreReset.setBackground(grayMade);
                                KeyFoucus = 16;
                            } else if (KeyFoucus == 12) {
                                scoreReset.setBackground(Color.gray);
                                BackToGame.setBackground(grayMade);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 13) {
                                BackToGame.setBackground(Color.gray);
                                BackToItemGame.setBackground(grayMade);
                                KeyFoucus = 12;
                            } else if (KeyFoucus == 14) {
                                BackToItemGame.setBackground(Color.gray);
                                BackToBattle.setBackground(grayMade);
                                KeyFoucus = 13;
                            } else if (KeyFoucus == 15) {
                                BackToBattle.setBackground(Color.gray);
                                BackToStart.setBackground(grayMade);
                                KeyFoucus = 14;
                            } else if (KeyFoucus == 16) {
                                BackToStart.setBackground(Color.gray);
                                settingReset.setBackground(grayMade);
                                KeyFoucus = 15;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_S:
                            if (KeyFoucus == 1) {
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                screenSizeArea.getSizeTwo().setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                screenSizeArea.getSizeThree().setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 3) {
                                keySettingPanel.getKeyOne().setBackground(Color.gray);
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                keySettingPanel.getKeyTwo().setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                difficultySettingPanel.getModeOne().setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                difficultySettingPanel.getModeTwo().setBackground(Color.gray);
                                KeyFoucus = 9;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                difficultySettingPanel.getModeThree().setBackground(Color.gray);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                BackToGame.setBackground(Color.gray);
                                KeyFoucus = 12;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                BackToItemGame.setBackground(Color.gray);
                                KeyFoucus = 13;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                BackToBattle.setBackground(Color.gray);
                                KeyFoucus = 14;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                BackToStart.setBackground(Color.gray);
                                KeyFoucus = 15;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                settingReset.setBackground(Color.gray);
                                KeyFoucus = 16;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_A:
                            if (KeyFoucus == 1) {
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_D:
                            if (KeyFoucus == 1) {
                                screenSizeArea.getSizeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                screenSizeArea.getSizeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                screenSizeArea.getSizeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keySettingPanel.getKeyOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keySettingPanel.getKeyTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.getColorBlindOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.getColorBlindTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                difficultySettingPanel.getModeOne().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                difficultySettingPanel.getModeTwo().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                difficultySettingPanel.getModeThree().setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                screenSizeArea.getSizeOne().setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case 10:
                            if (KeyFoucus == 1) {
                                screenSizeArea.sizeOnefun();
                            }
                            if (KeyFoucus == 2) {
                                screenSizeArea.sizeTwofun();
                            }
                            if (KeyFoucus == 3) {
                                screenSizeArea.sizeThreefun();
                            }
                            if (KeyFoucus == 4) {
                                keySettingPanel.keyOnefun();
                            }
                            if (KeyFoucus == 5) {
                                keySettingPanel.keyTwofun();
                            }
                            if (KeyFoucus == 6) {
                                colorBlindnessSettingPanel.colorBlindOneFun();
                            }
                            if (KeyFoucus == 7) {
                                colorBlindnessSettingPanel.colorBlindTwoFun();
                            }
                            if (KeyFoucus == 8) {
                                difficultySettingPanel.modeOnefun();
                            }
                            if (KeyFoucus == 9) {
                                difficultySettingPanel.modeTwofun();
                            }
                            if (KeyFoucus == 10) {
                                difficultySettingPanel.modeThreefun();
                            }

                            if (KeyFoucus == 11) {
                                scoreReset.doClick();
                            }
                            if (KeyFoucus == 12) {
                                BackToGame.doClick();
                            }
                            if (KeyFoucus == 13) {
                                BackToItemGame.doClick();
                            }
                            if (KeyFoucus == 14) {
                                BackToBattle.doClick();
                            }
                            if (KeyFoucus == 15) {
                                BackToStart.doClick();
                            }
                            if (KeyFoucus == 16) {
                                settingReset.doClick();
                            }
                            KeyCount++;
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        settingView.setFocusable(true);

        //기존 화면 설정 가져오기
        screenSizeArea.reload();

        //기존 난이도 설정 가져오기
        difficultySettingPanel.reload();

        //기존 색상 설정 가져오기
        colorBlindnessSettingPanel.reload();

        //기존 키보드 설정 가져오기
        keySettingPanel.reload();
    }

    public void changeSize(ScreenSizeSettingPanel.Resolution resolution) {
        switch (resolution.ordinal()) {
            case 2:
                setSize(resolution.width, resolution.height);
                tetrisArea.setPreferredSize(new Dimension(350, 50));
                rightRight.setPreferredSize(new Dimension(200, 600));
                nextArea.setPreferredSize(new Dimension(600, 600));
                scorePanel.setPreferredSize(new Dimension(160, 100));
                buttonPanel.setPreferredSize(new Dimension(160, 400));
                scoreReset.setPreferredSize(new Dimension(160, 100));
                BackToItemGame.setPreferredSize(new Dimension(160, 100));
                BackToGame.setPreferredSize(new Dimension(160, 100));
                BackToBattle.setPreferredSize(new Dimension(160, 100));
                BackToStart.setPreferredSize(new Dimension(160, 100));
                settingReset.setPreferredSize(new Dimension(160, 100));

                screenSizeArea.screenSizeTitle.setFont(fontBig);
                keySettingPanel.keyTitle.setFont(fontBig);
                colorBlindnessSettingPanel.colorBlindTitle.setFont(fontBig);
                difficultySettingPanel.modeTitle.setFont(fontBig);

                scoreReset.setFont(fontBig);
                BackToItemGame.setFont(fontBig);
                BackToGame.setFont(fontBig);
                BackToBattle.setFont(fontBig);
                BackToStart.setFont(fontBig);
                settingReset.setFont(fontBig);

                screenSizeArea.getSizeOne().setFont(fontBig);
                screenSizeArea.getSizeTwo().setFont(fontBig);
                screenSizeArea.getSizeThree().setFont(fontBig);
                keySettingPanel.getKeyOne().setFont(fontBig);
                keySettingPanel.getKeyTwo().setFont(fontBig);
                colorBlindnessSettingPanel.getColorBlindOne().setFont(fontBig);
                colorBlindnessSettingPanel.getColorBlindTwo().setFont(fontBig);
                difficultySettingPanel.getModeOne().setFont(fontBig);
                difficultySettingPanel.getModeTwo().setFont(fontBig);
                difficultySettingPanel.getModeThree().setFont(fontBig);
                screenSizeArea.getSizeTwo().setSelected(true);
                break;
            case 3:
                setSize(resolution.width, resolution.height);
                tetrisArea.setPreferredSize(new Dimension(350, 50));
                rightRight.setPreferredSize(new Dimension(screenWidth / 4, 3 * screenHeight / 4));
                nextArea.setPreferredSize(new Dimension(3 * screenWidth / 4, 3 * screenHeight / 4));
                scorePanel.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 4));
                buttonPanel.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 2));
                scoreReset.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 4));
                BackToItemGame.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 8));
                BackToGame.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 8));
                BackToBattle.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 8));
                BackToStart.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 8));
                settingReset.setPreferredSize(new Dimension(screenWidth / 4, 1 * screenHeight / 8));

                screenSizeArea.screenSizeTitle.setFont(fontFull);
                keySettingPanel.keyTitle.setFont(fontFull);
                colorBlindnessSettingPanel.colorBlindTitle.setFont(fontFull);
                difficultySettingPanel.modeTitle.setFont(fontFull);

                screenSizeArea.getSizeOne().setFont(fontFull);
                screenSizeArea.getSizeTwo().setFont(fontFull);
                screenSizeArea.getSizeThree().setFont(fontFull);
                keySettingPanel.getKeyOne().setFont(fontFull);
                keySettingPanel.getKeyTwo().setFont(fontFull);
                colorBlindnessSettingPanel.getColorBlindOne().setFont(fontFull);
                colorBlindnessSettingPanel.getColorBlindTwo().setFont(fontFull);
                difficultySettingPanel.getModeOne().setFont(fontFull);
                difficultySettingPanel.getModeTwo().setFont(fontFull);
                difficultySettingPanel.getModeThree().setFont(fontFull);

                scoreReset.setFont(fontFull);
                BackToItemGame.setFont(fontFull);
                BackToGame.setFont(fontFull);
                BackToBattle.setFont(fontFull);
                BackToStart.setFont(fontFull);
                settingReset.setFont(fontFull);
                screenSizeArea.getSizeThree().setSelected(true);
                break;
            case 1:
            default:
                setSize(resolution.width, resolution.height);
                tetrisArea.setPreferredSize(new Dimension(350, 50));
                rightRight.setPreferredSize(new Dimension(100, 450));
                nextArea.setPreferredSize(new Dimension(250, 450));
                scoreReset.setPreferredSize(new Dimension(80, 75));
                scorePanel.setPreferredSize(new Dimension(80, 75));
                buttonPanel.setPreferredSize(new Dimension(80, 300));
                BackToItemGame.setPreferredSize(new Dimension(80, 75));
                BackToGame.setPreferredSize(new Dimension(80, 75));
                BackToBattle.setPreferredSize(new Dimension(80, 75));
                BackToStart.setPreferredSize(new Dimension(80, 75));
                settingReset.setPreferredSize(new Dimension(80, 75));

                screenSizeArea.screenSizeTitle.setFont(fontSmall);
                keySettingPanel.keyTitle.setFont(fontSmall);
                colorBlindnessSettingPanel.colorBlindTitle.setFont(fontSmall);
                difficultySettingPanel.modeTitle.setFont(fontSmall);

                scoreReset.setFont(fontSmall);
                BackToItemGame.setFont(fontSmall);
                BackToGame.setFont(fontSmall);
                BackToBattle.setFont(fontSmall);
                BackToStart.setFont(fontSmall);
                settingReset.setFont(fontSmall);

                screenSizeArea.getSizeOne().setFont(fontSmall);
                screenSizeArea.getSizeTwo().setFont(fontSmall);
                screenSizeArea.getSizeThree().setFont(fontSmall);
                keySettingPanel.getKeyOne().setFont(fontSmall);
                keySettingPanel.getKeyTwo().setFont(fontSmall);
                colorBlindnessSettingPanel.getColorBlindOne().setFont(fontSmall);
                colorBlindnessSettingPanel.getColorBlindTwo().setFont(fontSmall);
                difficultySettingPanel.getModeOne().setFont(fontSmall);
                difficultySettingPanel.getModeTwo().setFont(fontSmall);
                difficultySettingPanel.getModeThree().setFont(fontSmall);
                screenSizeArea.getSizeOne().setSelected(true);
                break;
        }
    }
    /*
     * private void addKeyListener(new KeyListener() {
     *
     * @Override public void keyTyped(KeyEvent e) {}
     *
     * @Override public void keyReleased(KeyEvent e) {}
     *
     * @Override public void keyPressed(KeyEvent e) {}) { }
     */

    public static void main(String[] args) {
        SettingCode setting = new SettingCode();
        int sizeNumber = SettingValues.getInstance().sizeNumber;
        switch (sizeNumber) {
            case 1:
                setting.setSize(400, 600);
                break;
            case 2:
                setting.setSize(800, 1200);
                break;
            case 3:
                setting.setExtendedState(JFrame.MAXIMIZED_BOTH);
                setting.setUndecorated(true);
                break;
            default:
                setting.setSize(400, 600);
                break;
        }
        setting.setVisible(true);
    }
}