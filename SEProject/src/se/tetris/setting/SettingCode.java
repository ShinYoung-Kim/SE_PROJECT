package se.tetris.setting;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.tetris.main.Tetris;
import se.tetris.component.Board;
import se.tetris.component.Start;

public class SettingCode extends JFrame {
    private JPanel tetrisArea;
    private JPanel nextArea;
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel scorePanel;

    JRadioButton sizeOne = new JRadioButton("표준(400 * 600)");
    JRadioButton sizeTwo = new JRadioButton("크게(800 * 1200)");
    JRadioButton sizeThree = new JRadioButton("전체 화면 모드");

    int score = 0;
    public static int intervalNumber = 1000;
    public static int sizeNumber;
    public static int colorBlindModeCheck;
    public static int modeChoose = 2;
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int)(dimension.getWidth());
    int screenHeight = (int)(dimension.getHeight());


    public static SettingCode setting;

    public SettingCode() {

        super("SeoulTech SE Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Board display setting.
        tetrisArea = new JPanel();

        tetrisArea.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        tetrisArea.setBorder(border);
        tetrisArea.setPreferredSize(new Dimension(350, 50));
        JLabel settingTitle = new JLabel("테트리스 게임 설정");
        settingTitle.setForeground(Color.WHITE);
        tetrisArea.add(settingTitle);


        nextArea = new JPanel();
        nextArea.setPreferredSize(new Dimension(250, 400));
        nextArea.setBackground(Color.BLACK);
        nextArea.setBorder(border);

        JPanel screenSizeArea = new JPanel();
        JLabel screenSizeTitle = new JLabel("화면 크기 조절");
        screenSizeTitle.setForeground(Color.WHITE);
        screenSizeArea.add(screenSizeTitle);
        ButtonGroup sizeGroup = new ButtonGroup();

        sizeOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeNumber = 1;
                changeSize(sizeNumber);
            }
        });

        sizeTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeNumber = 2;
                changeSize(sizeNumber);
            }
        });
        JRadioButton sizeThree = new JRadioButton("전체 화면 모드");
        sizeThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeNumber = 3;
                changeSize(sizeNumber);
            }
        });
        sizeOne.setSelected(true);
        sizeGroup.add(sizeOne);
        sizeGroup.add(sizeTwo);
        sizeGroup.add(sizeThree);
        screenSizeArea.add(sizeOne);
        screenSizeArea.add(sizeTwo);
        screenSizeArea.add(sizeThree);
        screenSizeArea.setPreferredSize(new Dimension(250, 70));
        screenSizeArea.setAlignmentX(RIGHT_ALIGNMENT);
        nextArea.add(screenSizeArea);

        JPanel keyArea = new JPanel();
        JLabel keyTitle = new JLabel("방향키 선택");
        keyTitle.setForeground(Color.WHITE);
        keyArea.add(keyTitle);
        ButtonGroup keyGroup = new ButtonGroup();
        JRadioButton keyOne = new JRadioButton("WASD");
        JRadioButton keyTwo = new JRadioButton("화살표");
        keyOne.setSelected(true);
        keyGroup.add(keyOne);
        keyGroup.add(keyTwo);
        keyArea.add(keyOne);
        keyArea.add(keyTwo);
        keyArea.setPreferredSize(new Dimension(250, 70));
        keyArea.setAlignmentX(RIGHT_ALIGNMENT);
        nextArea.add(keyArea);

        JPanel colorBlindArea = new JPanel();
        JLabel colorBlindTitle = new JLabel("색맹모드");
        colorBlindTitle.setForeground(Color.WHITE);
        colorBlindArea.add(colorBlindTitle);
        ButtonGroup colorBlindGroup = new ButtonGroup();
        JRadioButton colorBlindOne = new JRadioButton("Off");
        JRadioButton colorBlindTwo = new JRadioButton("On");
        colorBlindOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBlindModeCheck = 0;
            }
        });
        colorBlindTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBlindModeCheck = 1;
            }
        });
        colorBlindOne.setSelected(true);
        colorBlindGroup.add(colorBlindOne);
        colorBlindGroup.add(colorBlindTwo);
        colorBlindArea.add(colorBlindOne);
        colorBlindArea.add(colorBlindTwo);
        colorBlindArea.setPreferredSize(new Dimension(250, 70));
        colorBlindArea.setAlignmentX(RIGHT_ALIGNMENT);
        nextArea.add(colorBlindArea);

        JPanel modeArea = new JPanel();
        JLabel modeTitle = new JLabel("모드 선택");
        modeTitle.setForeground(Color.WHITE);
        modeArea.add(modeTitle);
        ButtonGroup modeGroup = new ButtonGroup();
        JRadioButton modeOne = new JRadioButton("Easy");
        modeOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intervalNumber = 2000;
                modeChoose = 1;
            }
        });
        JRadioButton modeTwo = new JRadioButton("Normal");
        modeTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intervalNumber = 1000;
                modeChoose = 2;
            }
        });
        JRadioButton modeThree = new JRadioButton("Hard");
        modeThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intervalNumber = 500;
                modeChoose = 3;
            }
        });
        modeTwo.setSelected(true);
        modeGroup.add(modeOne);
        modeGroup.add(modeTwo);
        modeGroup.add(modeThree);
        modeArea.add(modeOne);
        modeArea.add(modeTwo);
        modeArea.add(modeThree);
        modeArea.setPreferredSize(new Dimension(250, 70));
        modeArea.setAlignmentX(RIGHT_ALIGNMENT);
        nextArea.add(modeArea);

        scorePanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        scorePanel.setBorder(scoreBorder);
        scorePanel.setPreferredSize(new Dimension(80, 100));
        JLabel scoreLb1 = new JLabel("Scores");
        scoreLb1.setForeground(Color.darkGray);
        scoreLb1.setAlignmentX(CENTER_ALIGNMENT);
        JLabel scoreLb2 = new JLabel(Integer.toString(score));
        scoreLb2.setForeground(Color.RED);
        scoreLb2.setAlignmentX(CENTER_ALIGNMENT);
        JButton scoreReset = new JButton("스코어보드 초기화");
        scoreReset.setAlignmentX(CENTER_ALIGNMENT);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(scoreLb1);
        scorePanel.add(Box.createVerticalStrut(5));
        scorePanel.add(scoreLb2);
        scorePanel.add(Box.createVerticalStrut(5));
        scorePanel.add(scoreReset);

        JPanel buttonPanel = new JPanel();
        EtchedBorder buttonBorder = new EtchedBorder();
        buttonPanel.setBorder(buttonBorder);
        buttonPanel.setPreferredSize(new Dimension(80, 300));
        JButton BackToGame = new JButton("게임으로");
        JButton BackToStart = new JButton("시작 메뉴");
        JButton settingReset = new JButton("설정초기화");

        BackToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board.boardMain.setVisible(true);
                Board.boardMain.reset();
                Board.boardMain.timer.restart();
                Board.boardMain.score = 0;
                Board.boardMain.level = 0;
                setVisible(false);
            }
        });

        BackToStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start.start.setVisible(true);
                setVisible(false);
            }
        });

        settingReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeOne.doClick();
                keyOne.doClick();
                colorBlindOne.doClick();
                modeTwo.doClick();
            }
        });

        buttonPanel.add(BackToGame);
        buttonPanel.add(BackToStart);
        buttonPanel.add(settingReset);

        nextArea.setLayout(new BoxLayout(nextArea, BoxLayout.Y_AXIS));
        nextArea.setAlignmentX(RIGHT_ALIGNMENT);

        JPanel rightRight = new JPanel();
        rightRight.add(scorePanel);
        rightRight.add(Box.createVerticalStrut(2));
        rightRight.add(buttonPanel);
        rightRight.setAlignmentX(CENTER_ALIGNMENT);
        rightRight.setLayout(new BoxLayout(rightRight, BoxLayout.Y_AXIS));

        leftPanel = new JPanel();
        leftPanel.add(tetrisArea);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.add(nextArea);
        rightPanel.add(Box.createVerticalStrut(2));
        rightPanel.add(rightRight);



        panel = new JPanel();
        panel.add(leftPanel);
        panel.add(rightPanel);

        add(panel);

        //Initialize board for the game.

        setFocusable(true);
        requestFocus();

    }

    public static int getInitInterval(){
        return intervalNumber;
    }

    public static int getSizeNumber(){
        return sizeNumber;
    }

    public void changeSize(int SizeNumber){
        switch (sizeNumber) {
            case 1:
                setting.setSize(400, 600);
                Start.start.setSize(400, 600);
                Board.boardMain.setSize(400, 600);
                Board.setSize(30);
                Board.setRtSize(150, 50);
                Board.setLbSize(0);
                sizeOne.setSelected(true);
                break;
            case 2:
                setting.setSize(800, 1200);
                Start.start.setSize(800, 1200);
                Board.boardMain.setSize(800, 1200);
                Board.setSize(30);
                Board.setRtSize(175, 55);
                Board.setLbSize(15);
                sizeTwo.setSelected(true);
                break;
            case 3:
                setting.setSize(screenWidth, screenHeight);
                Start.start.setSize(screenWidth, screenHeight);
                Board.boardMain.setSize(screenWidth, screenHeight);
                sizeNumber = 3;
                Board.setSize(30);
                Board.setRtSize(200, 60);
                Board.setLbSize(17);
                sizeThree.setSelected(true);
                break;
            default:
                setSize(400, 600);
                break;
        }
    }

    public static SettingCode getSettingCode() {
        return setting;
    }

    public static int getColorBlindModeCheck() {
        return colorBlindModeCheck;
    }

    public static void main(String[] args) {
        //SettingCode setting = new SettingCode();
        int sizeNumber = setting.getSizeNumber();
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