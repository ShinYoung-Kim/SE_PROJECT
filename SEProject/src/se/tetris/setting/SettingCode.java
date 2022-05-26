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

    JPanel rightRight = new JPanel();
    JPanel buttonPanel = new JPanel();

    Font fontSmall = new Font(null, Font.PLAIN, 10);
    Font fontBig = new Font(null, Font.PLAIN, 20);
    Font fontFull = new Font(null, Font.PLAIN, 25);

    Color grayMade = new Color(238, 238, 238);

    JLabel settingTitle = new JLabel("테트리스 게임 설정");

    JButton scoreReset = new JButton("<html><body style='text-align:center;'>스코어보드<br>초기화</body></html>");
    JButton BackToGame = new JButton("<html><body style='text-align:center;'>일반<br>게임으로</body></html>");
    JButton BackToItemGame = new JButton("<html><body style='text-align:center;'>아이템 모드<br>게임으로</body></html>");
    JButton BackToStart = new JButton("<html><body style='text-align:center;'>시작 메뉴</body></html>");
    JButton BackToBattle = new JButton("<html><body style='text-align:center;'>대전 모드</body></html>");
    JButton settingReset = new JButton("<html><body style='text-align:center;'>설정<br>초기화</body></html>");

    JLabel screenSizeTitle = new JLabel("화면 크기 조절");
    JLabel keyTitle = new JLabel("방향키 선택");
    JLabel colorBlindTitle = new JLabel("색맹모드");
    JLabel modeTitle = new JLabel("모드 선택");

    public JRadioButton sizeOne = new JRadioButton("표준");
    public JRadioButton sizeTwo = new JRadioButton("크게");
    public JRadioButton sizeThree = new JRadioButton("전체 화면 모드");

    JRadioButton keyOne = new JRadioButton("방향키");
    JRadioButton keyTwo = new JRadioButton("WASD");

    public JRadioButton colorBlindOne = new JRadioButton("Off");
    public JRadioButton colorBlindTwo = new JRadioButton("On");

    public JRadioButton modeOne = new JRadioButton("Easy");
    public JRadioButton modeTwo = new JRadioButton("Normal");
    public JRadioButton modeThree = new JRadioButton("Hard");

    int KeyCount = 0;
    int KeyFoucus = 0;

    DBCalls dataCalls = new DBCalls();

    int Window = dataCalls.getWindowSetting();

    public SettingCode() {

        super("SeoulTech SE Tetris");
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

        JPanel screenSizeArea = new JPanel();
        screenSizeTitle.setForeground(Color.BLACK);
        screenSizeArea.setLayout(new BoxLayout(screenSizeArea, BoxLayout.Y_AXIS));
        screenSizeArea.add(screenSizeTitle);
        screenSizeArea.add(Box.createVerticalStrut(20));
        ButtonGroup sizeGroup = new ButtonGroup();

        sizeOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeOnefun();
            }
        });

        sizeTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeTwofun();
            }
        });

        sizeThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeThreefun();
            }
        });
        sizeOne.setSelected(true);
        sizeGroup.add(sizeOne);
        sizeGroup.add(sizeTwo);
        sizeGroup.add(sizeThree);
        screenSizeArea.add(sizeOne);
        screenSizeArea.add(sizeTwo);
        screenSizeArea.add(sizeThree);
        screenSizeArea.add(Box.createVerticalStrut(20));
        // screenSizeArea.setPreferredSize(new Dimension(250, 70));
        screenSizeArea.setAlignmentX(LEFT_ALIGNMENT);
        nextArea.add(screenSizeArea);
        screenSizeArea.setBackground(grayMade);
        screenSizeArea.setBackground(grayMade);

        JPanel keyArea = new JPanel();
        keyTitle.setForeground(Color.black);
        keyArea.add(keyTitle);
        keyArea.add(Box.createVerticalStrut(20));
        keyArea.setLayout(new BoxLayout(keyArea, BoxLayout.Y_AXIS));
        ButtonGroup keyGroup = new ButtonGroup();
        keyOne.setSelected(true);
        keyGroup.add(keyOne);
        keyGroup.add(keyTwo);
        keyArea.add(keyOne);
        keyArea.add(keyTwo);
        keyArea.add(Box.createVerticalStrut(20));
        // keyArea.setPreferredSize(new Dimension(250, 70));
        keyArea.setAlignmentX(LEFT_ALIGNMENT);
        nextArea.add(keyArea);
        keyArea.setBackground(grayMade);

        keyOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyOnefun();
            }
        });

        keyTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyTwofun();
            }
        });

        JPanel colorBlindArea = new JPanel();
        colorBlindTitle.setForeground(Color.black);
        colorBlindArea.add(colorBlindTitle);
        colorBlindArea.add(Box.createVerticalStrut(20));
        colorBlindArea.setBackground(grayMade);
        colorBlindArea.setLayout(new BoxLayout(colorBlindArea, BoxLayout.Y_AXIS));

        ButtonGroup colorBlindGroup = new ButtonGroup();
        colorBlindOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBlindOneFun();
            }
        });
        colorBlindTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBlindTwoFun();
            }
        });
        colorBlindOne.setSelected(true);
        colorBlindGroup.add(colorBlindOne);
        colorBlindGroup.add(colorBlindTwo);
        colorBlindArea.add(colorBlindOne);
        colorBlindArea.add(colorBlindTwo);
        colorBlindArea.add(Box.createVerticalStrut(20));
        // colorBlindArea.setPreferredSize(new Dimension(250, 70));
        colorBlindArea.setAlignmentX(LEFT_ALIGNMENT);
        nextArea.add(colorBlindArea);

        JPanel modeArea = new JPanel();
        modeTitle.setForeground(Color.black);
        modeArea.add(modeTitle);
        modeArea.add(Box.createVerticalStrut(20));
        modeArea.setBackground(grayMade);
        ButtonGroup modeGroup = new ButtonGroup();
        modeOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeOnefun();
            }
        });
        modeTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeTwofun();
            }
        });
        modeThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeThreefun();
            }
        });
        modeTwo.setSelected(true);
        modeGroup.add(modeOne);
        modeGroup.add(modeTwo);
        modeGroup.add(modeThree);
        modeArea.add(modeOne);
        modeArea.add(modeTwo);
        modeArea.add(modeThree);
        modeArea.add(Box.createVerticalStrut(20));
        modeArea.setLayout(new BoxLayout(modeArea, BoxLayout.Y_AXIS));
        // modeArea.setPreferredSize(new Dimension(250, 70));
        modeArea.setAlignmentX(LEFT_ALIGNMENT);
        nextArea.add(modeArea);

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
                if(Window == 0) {
                    battleMode.setSize(400,600);
                }else if(Window == 1) {
                    battleMode.setSize(800,800);
                }else {
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
                dataCalls.UpdateColorSetting(0);
                dataCalls.UpdateLevelSetting(0);

                sizeOnefun();
                keyOnefun();
                colorBlindOneFun();
                modeTwofun();

            }
        });

        scoreReset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dataCalls.refreshScoreData();

                int Result = JOptionPane.showConfirmDialog(null, "초기화 되었습니다!", "스코어 보드 초기화",
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
        keyArea.setPreferredSize(new Dimension(250, 110));
        colorBlindArea.setPreferredSize(new Dimension(250, 110));
        modeArea.setPreferredSize(new Dimension(250, 110));

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
                                modeThree.setBackground(Color.gray);
                                sizeOne.setBackground(grayMade);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 3) {
                                sizeThree.setBackground(grayMade);
                                sizeTwo.setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                sizeThree.setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                keyOne.setBackground(Color.gray);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                keyTwo.setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                colorBlindOne.setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                colorBlindTwo.setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                modeOne.setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                modeTwo.setBackground(Color.gray);
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
                                sizeOne.setBackground(grayMade);
                                sizeTwo.setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                sizeThree.setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 3) {
                                keyOne.setBackground(Color.gray);
                                sizeThree.setBackground(grayMade);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                keyTwo.setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                colorBlindOne.setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                colorBlindTwo.setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                modeOne.setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                modeTwo.setBackground(Color.gray);
                                KeyFoucus = 9;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                modeThree.setBackground(Color.gray);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
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
                                sizeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                sizeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (KeyFoucus == 1) {
                                sizeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                sizeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case 10:
                            if (KeyFoucus == 1) {
                                sizeOnefun();
                            }
                            if (KeyFoucus == 2) {
                                sizeTwofun();
                            }
                            if (KeyFoucus == 3) {
                                sizeThreefun();
                            }
                            if (KeyFoucus == 4) {
                                keyOnefun();
                            }
                            if (KeyFoucus == 5) {
                                keyTwofun();
                            }
                            if (KeyFoucus == 6) {
                                colorBlindOneFun();
                            }
                            if (KeyFoucus == 7) {
                                colorBlindTwoFun();
                            }
                            if (KeyFoucus == 8) {
                                modeOnefun();
                            }
                            if (KeyFoucus == 9) {
                                modeTwofun();
                            }
                            if (KeyFoucus == 10) {
                                modeThreefun();
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
                                modeThree.setBackground(Color.gray);
                                sizeOne.setBackground(grayMade);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 3) {
                                sizeThree.setBackground(grayMade);
                                sizeTwo.setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                sizeThree.setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                keyOne.setBackground(Color.gray);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                keyTwo.setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                colorBlindOne.setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                colorBlindTwo.setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                modeOne.setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                modeTwo.setBackground(Color.gray);
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
                                sizeOne.setBackground(grayMade);
                                sizeTwo.setBackground(Color.gray);
                                KeyFoucus = 2;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                sizeThree.setBackground(Color.gray);
                                KeyFoucus = 3;
                            } else if (KeyFoucus == 3) {
                                keyOne.setBackground(Color.gray);
                                sizeThree.setBackground(grayMade);
                                KeyFoucus = 4;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                keyTwo.setBackground(Color.gray);
                                KeyFoucus = 5;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                colorBlindOne.setBackground(Color.gray);
                                KeyFoucus = 6;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                colorBlindTwo.setBackground(Color.gray);
                                KeyFoucus = 7;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                modeOne.setBackground(Color.gray);
                                KeyFoucus = 8;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                modeTwo.setBackground(Color.gray);
                                KeyFoucus = 9;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                modeThree.setBackground(Color.gray);
                                KeyFoucus = 10;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
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
                                sizeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                sizeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case KeyEvent.VK_D:
                            if (KeyFoucus == 1) {
                                sizeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 2) {
                                sizeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 3) {
                                sizeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 4) {
                                keyOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 5) {
                                keyTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 6) {
                                colorBlindOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 7) {
                                colorBlindTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 8) {
                                modeOne.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 9) {
                                modeTwo.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 10) {
                                modeThree.setBackground(grayMade);
                                scoreReset.setBackground(Color.gray);
                                KeyFoucus = 11;
                            } else if (KeyFoucus == 11) {
                                scoreReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 12) {
                                BackToGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 13) {
                                BackToItemGame.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 14) {
                                BackToBattle.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 15) {
                                BackToStart.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            } else if (KeyFoucus == 16) {
                                settingReset.setBackground(grayMade);
                                sizeOne.setBackground(Color.gray);
                                KeyFoucus = 1;
                            }
                            KeyCount++;
                            break;
                        case 10:
                            if (KeyFoucus == 1) {
                                sizeOnefun();
                            }
                            if (KeyFoucus == 2) {
                                sizeTwofun();
                            }
                            if (KeyFoucus == 3) {
                                sizeThreefun();
                            }
                            if (KeyFoucus == 4) {
                                keyOnefun();
                            }
                            if (KeyFoucus == 5) {
                                keyTwofun();
                            }
                            if (KeyFoucus == 6) {
                                colorBlindOneFun();
                            }
                            if (KeyFoucus == 7) {
                                colorBlindTwoFun();
                            }
                            if (KeyFoucus == 8) {
                                modeOnefun();
                            }
                            if (KeyFoucus == 9) {
                                modeTwofun();
                            }
                            if (KeyFoucus == 10) {
                                modeThreefun();
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
    }

    private void keyTwofun() {
        SettingValues.getInstance().keyChoose = 2;
        keyTwo.setSelected(true);
    }

    private void keyOnefun() {
        SettingValues.getInstance().keyChoose = 1;
        keyOne.setSelected(true);
    }

    private void modeThreefun() {
        SettingValues.getInstance().intervalNumber = 800;
        SettingValues.getInstance().modeChoose = 3;

        dataCalls.UpdateLevelSetting(SettingValues.getInstance().modeChoose - 1);

        System.out.println("3번");

        modeThree.setSelected(true);
    }

    private void modeTwofun() {
        SettingValues.getInstance().intervalNumber = 1000;
        SettingValues.getInstance().modeChoose = 2;

        dataCalls.UpdateLevelSetting(SettingValues.getInstance().modeChoose - 1);

        modeTwo.setSelected(true);
    }

    private void modeOnefun() {
        SettingValues.getInstance().intervalNumber = 2000;
        SettingValues.getInstance().modeChoose = 1;

        dataCalls.UpdateLevelSetting(SettingValues.getInstance().modeChoose - 1);

        modeOne.setSelected(true);
    }

    private void sizeOnefun() {
        SettingValues.getInstance().sizeNumber = 1;

        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);

        changeSize(SettingValues.getInstance().sizeNumber);
        sizeOne.setSelected(true);
    }

    private void sizeTwofun() {
        SettingValues.getInstance().sizeNumber = 2;

        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);

        changeSize(SettingValues.getInstance().sizeNumber);
        sizeTwo.setSelected(true);
    }

    private void sizeThreefun() {
        SettingValues.getInstance().sizeNumber = 3;

        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);

        changeSize(SettingValues.getInstance().sizeNumber);
        sizeThree.setSelected(true);
    }

    private void colorBlindOneFun() {
        SettingValues.getInstance().colorBlindModeCheck = 0;

        dataCalls.UpdateColorSetting(SettingValues.getInstance().colorBlindModeCheck);

        colorBlindOne.setSelected(true);
    }

    private void colorBlindTwoFun() {
        SettingValues.getInstance().colorBlindModeCheck = 1;

        dataCalls.UpdateColorSetting(SettingValues.getInstance().colorBlindModeCheck);

        colorBlindTwo.setSelected(true);
    }

    public void changeSize(int sizeNumber){
        switch (sizeNumber) {
            case 1:
                setSize(400, 600);
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

                screenSizeTitle.setFont(fontSmall);
                keyTitle.setFont(fontSmall);
                colorBlindTitle.setFont(fontSmall);
                modeTitle.setFont(fontSmall);

                scoreReset.setFont(fontSmall);
                BackToItemGame.setFont(fontSmall);
                BackToGame.setFont(fontSmall);
                BackToBattle.setFont(fontSmall);
                BackToStart.setFont(fontSmall);
                settingReset.setFont(fontSmall);

                sizeOne.setFont(fontSmall);
                sizeTwo.setFont(fontSmall);
                sizeThree.setFont(fontSmall);
                keyOne.setFont(fontSmall);
                keyTwo.setFont(fontSmall);
                colorBlindOne.setFont(fontSmall);
                colorBlindTwo.setFont(fontSmall);
                modeOne.setFont(fontSmall);
                modeTwo.setFont(fontSmall);
                modeThree.setFont(fontSmall);
                sizeOne.setSelected(true);
                break;
            case 2:
                setSize(800, 800);
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

                screenSizeTitle.setFont(fontBig);
                keyTitle.setFont(fontBig);
                colorBlindTitle.setFont(fontBig);
                modeTitle.setFont(fontBig);

                scoreReset.setFont(fontBig);
                BackToItemGame.setFont(fontBig);
                BackToGame.setFont(fontBig);
                BackToBattle.setFont(fontBig);
                BackToStart.setFont(fontBig);
                settingReset.setFont(fontBig);

                sizeOne.setFont(fontBig);
                sizeTwo.setFont(fontBig);
                sizeThree.setFont(fontBig);
                keyOne.setFont(fontBig);
                keyTwo.setFont(fontBig);
                colorBlindOne.setFont(fontBig);
                colorBlindTwo.setFont(fontBig);
                modeOne.setFont(fontBig);
                modeTwo.setFont(fontBig);
                modeThree.setFont(fontBig);
                sizeTwo.setSelected(true);
                break;
            case 3:
                setSize(screenWidth, screenHeight);
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

                screenSizeTitle.setFont(fontFull);
                keyTitle.setFont(fontFull);
                colorBlindTitle.setFont(fontFull);
                modeTitle.setFont(fontFull);

                sizeOne.setFont(fontFull);
                sizeTwo.setFont(fontFull);
                sizeThree.setFont(fontFull);
                keyOne.setFont(fontFull);
                keyTwo.setFont(fontFull);
                colorBlindOne.setFont(fontFull);
                colorBlindTwo.setFont(fontFull);
                modeOne.setFont(fontFull);
                modeTwo.setFont(fontFull);
                modeThree.setFont(fontFull);

                scoreReset.setFont(fontFull);
                BackToItemGame.setFont(fontFull);
                BackToGame.setFont(fontFull);
                BackToBattle.setFont(fontFull);
                BackToStart.setFont(fontFull);
                settingReset.setFont(fontFull);
                sizeThree.setSelected(true);
                break;
            default:
                setSize(400, 600);
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

                screenSizeTitle.setFont(fontSmall);
                keyTitle.setFont(fontSmall);
                colorBlindTitle.setFont(fontSmall);
                modeTitle.setFont(fontSmall);

                scoreReset.setFont(fontSmall);
                BackToItemGame.setFont(fontSmall);
                BackToGame.setFont(fontSmall);
                BackToBattle.setFont(fontSmall);
                BackToStart.setFont(fontSmall);
                settingReset.setFont(fontSmall);

                sizeOne.setFont(fontSmall);
                sizeTwo.setFont(fontSmall);
                sizeThree.setFont(fontSmall);
                keyOne.setFont(fontSmall);
                keyTwo.setFont(fontSmall);
                colorBlindOne.setFont(fontSmall);
                colorBlindTwo.setFont(fontSmall);
                modeOne.setFont(fontSmall);
                modeTwo.setFont(fontSmall);
                modeThree.setFont(fontSmall);
                sizeOne.setSelected(true);
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