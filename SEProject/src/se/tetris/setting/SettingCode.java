package se.tetris.setting;

import se.tetris.component.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import se.tetris.data.*;

import static java.awt.event.KeyEvent.VK_UP;
import static se.tetris.setting.Strings.*;

public class SettingCode extends JFrame implements Sizeable {
    enum KeyChoose {
        ARROW(VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT),
        WASD(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);

        final int up;
        final int down;
        final int left;
        final int right;
        KeyChoose(int up, int down, int left, int right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }

        static KeyChoose getKeyChoose(int keyChoose) {
            if (keyChoose == 1) {
                return ARROW;
            }
            else {
                return WASD;
            }
        }
    }
    public static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screenWidth = (int) (dimension.getWidth());
    public static int screenHeight = (int) (dimension.getHeight());

    private JPanel tetrisArea;
    private JPanel nextArea;
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    public ScreenSizeSettingPanel screenSizeArea;
    public ColorBlindnessSettingPanel colorBlindnessSettingPanel;
    public DifficultySettingPanel difficultySettingPanel;
    public KeySettingPanel keySettingPanel;
    public SettingButtonPanel buttonPanel;

    JPanel rightRight = new JPanel();

    Font fontSmall = new Font(null, Font.PLAIN, 10);
    Font fontBig = new Font(null, Font.PLAIN, 20);
    Font fontFull = new Font(null, Font.PLAIN, 25);

    final static Color grayMade = new Color(238, 238, 238);

    JLabel settingTitle = new JLabel(settingTitleString);

    int KeyCount = 0;
    int KeyFoucus = 0;

    DBCalls dataCalls = new DBCalls();

    interface OnReloadingScreenSizeUIListener{
        void OnReloadingScreenSizeUI(ScreenSizeSettingPanel.Resolution value);
    }

    public void setOnReloadingScreenSizeUIListener(OnReloadingScreenSizeUIListener onReloadingScreenSizeUIListener) {
        this.onReloadingScreenSizeUIListener = onReloadingScreenSizeUIListener;
    }

    private OnReloadingScreenSizeUIListener onReloadingScreenSizeUIListener = null;

    AbstractButton[] buttonArray;

    //keyCode 상수처리
    final int vkUpKeycode = KeyEvent.VK_UP;
    final int vkDownKeycode = KeyEvent.VK_DOWN;
    final int vkLeftKeycode = KeyEvent.VK_LEFT;
    final int vkRightKeycode = KeyEvent.VK_RIGHT;
    final int vkWKeycode = KeyEvent.VK_W;
    final int vkSKeycode = KeyEvent.VK_S;
    final int vkAKeycode = KeyEvent.VK_A;
    final int vkDKeycode = KeyEvent.VK_D;
    final int vkEnterKeycode = 10;

    //keyFoucus 상수 처리
    final int screensizeStandard = 0;
    final int screensizeBig = 1;
    final int screensizeFull = 2;
    final int keyArrow = 3;
    final int keyWASD = 4;
    final int colorBlindActive = 5;
    final int colorBlindInactive = 6;
    final int difficultyEasy = 7;
    final int difficultyNormal = 8;
    final int difficultyHard = 9;

    final int scoreResetButton = 10;
    final int backToGameButton = 11;
    final int backToItemGameButton = 12;
    final int backToBattleButton = 13;
    final int backToStartButton = 14;
    final int settingResetButton = 15;

    int relation[][] = new int[16][4];

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

        screenSizeArea = new ScreenSizeSettingPanel(this);
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

        buttonPanel = new SettingButtonPanel(this);

        nextArea.setLayout(new BoxLayout(nextArea, BoxLayout.Y_AXIS));
        nextArea.setAlignmentX(LEFT_ALIGNMENT);

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(leftPanel);
        panel.add(rightPanel);

        settingView.add(panel);

        add(panel);

        tetrisArea.setPreferredSize(new Dimension(350, 50));
        rightRight.setPreferredSize(new Dimension(100, 450));
        nextArea.setPreferredSize(new Dimension(250, 450));
        screenSizeArea.setPreferredSize(new Dimension(250, 110));
        keySettingPanel.setPreferredSize(new Dimension(250, 110));
        colorBlindnessSettingPanel.setPreferredSize(new Dimension(250, 110));
        difficultySettingPanel.setPreferredSize(new Dimension(250, 110));

        buttonArray = new AbstractButton[]{screenSizeArea.getSizeOne(), screenSizeArea.getSizeTwo(), screenSizeArea.getSizeThree(), keySettingPanel.getKeyOne(),
                keySettingPanel.getKeyTwo(), colorBlindnessSettingPanel.getColorBlindOne(), colorBlindnessSettingPanel.getColorBlindTwo(),
                difficultySettingPanel.getModeOne(), difficultySettingPanel.getModeTwo(), difficultySettingPanel.getModeThree(), buttonPanel.getScoreReset(), buttonPanel.getBackToGame(), buttonPanel.getBackToItemGame(),
                buttonPanel.getBackToBattle(), buttonPanel.getBackToStart(), buttonPanel.getSettingReset()};

        // Initialize board for the game.

        settingView.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyCount == 0) {
                    settingView.requestFocusInWindow();
                    foucusColoringRemove();
                    buttonArray[KeyFoucus].setBackground(Color.gray);
                    KeyCount++;
                } else {
                    foucusMove(e);
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

        for (int i = 0; i < 16; i++) {
            int j = 0;
            if (i != 0 && i != 10) {
                relation[i][j] = i - 1;
            } else {
                relation[i][j] = i;
            }
            j++;
            if (i != 9 && i != 15) {
                relation[i][j] = i + 1;
            } else {
                relation[i][j] = i;
            }
            j++;
            if (i > 9) {
                relation[i][j] = 0;
                relation[i][j + 1] = i;
            } else {
                relation[i][j] = i;
                relation[i][j + 1] = 10;
            }
        }
        System.out.println(Arrays.deepToString(relation));
    }

    void foucusMove(KeyEvent e) {
        int enteredKey = e.getKeyCode();
        int keyChoose = SettingValues.getInstance().keyChoose;
        KeyChoose currentKeyChoose = KeyChoose.getKeyChoose(keyChoose);
        if (currentKeyChoose.up == enteredKey) {
            KeyFoucus = relation[KeyFoucus][0];
        } else if (currentKeyChoose.down == enteredKey) {
            KeyFoucus = relation[KeyFoucus][1];
        } else if (currentKeyChoose.left == enteredKey) {
            KeyFoucus = relation[KeyFoucus][2];
        } else if (currentKeyChoose.right == enteredKey) {
            KeyFoucus = relation[KeyFoucus][3];
        } else if (enteredKey == vkEnterKeycode) {
            foucusDoClick();
        }
        foucusColoringRemove();
        foucusColoring();
    }
/*
    void foucusMoveDown() {
        if (KeyFoucus != 9 && KeyFoucus != 15) {
            KeyFoucus++;
        }
    }

    void foucusMoveUp() {
        if (KeyFoucus != 0 && KeyFoucus != 10) {
            KeyFoucus--;
        }
    }

    void foucusMoveLeft() {
        if (9 < KeyFoucus) {
            KeyFoucus = 0;
        }
    }

    void foucusMoveRight() {
        if (KeyFoucus < 10) {
            KeyFoucus = 10;
        }
    }

 */

    void foucusColoring() {
        buttonArray[KeyFoucus].setBackground(Color.gray);
    }

    void foucusDoClick() {
        buttonArray[KeyFoucus].setSelected(true);
        buttonArray[KeyFoucus].doClick();
    }

    void foucusColoringRemove() {
        for(int i = 0; i < 16; i++) {
            buttonArray[i].setBackground(grayMade);
        }
    }

    public void setFontSize(Font font) {
        screenSizeArea.screenSizeTitle.setFont(font);
        keySettingPanel.keyTitle.setFont(font);
        colorBlindnessSettingPanel.colorBlindTitle.setFont(font);
        difficultySettingPanel.modeTitle.setFont(font);

        buttonPanel.getScoreReset().setFont(font);
        buttonPanel.getBackToItemGame().setFont(font);
        buttonPanel.getBackToGame().setFont(font);
        buttonPanel.getBackToBattle().setFont(font);
        buttonPanel.getBackToStart().setFont(font);
        buttonPanel.getSettingReset().setFont(font);

        screenSizeArea.getSizeOne().setFont(font);
        screenSizeArea.getSizeTwo().setFont(font);
        screenSizeArea.getSizeThree().setFont(font);
        keySettingPanel.getKeyOne().setFont(font);
        keySettingPanel.getKeyTwo().setFont(font);
        colorBlindnessSettingPanel.getColorBlindOne().setFont(font);
        colorBlindnessSettingPanel.getColorBlindTwo().setFont(font);
        difficultySettingPanel.getModeOne().setFont(font);
        difficultySettingPanel.getModeTwo().setFont(font);
        difficultySettingPanel.getModeThree().setFont(font);

        settingTitle.setFont(font);
    }

    public void setAreaSize(ScreenSizeSettingPanel.Resolution resolution) {
        setSize(resolution.width, resolution.height);
        tetrisArea.setPreferredSize(new Dimension(resolution.width / 4, 50));
        rightRight.setPreferredSize(new Dimension(resolution.width / 4, 3 * resolution.height / 4));
        nextArea.setPreferredSize(new Dimension(3 * resolution.width / 4, 3 * resolution.height / 4));
        buttonPanel.setPreferredSize(new Dimension(resolution.width / 4, 1 * resolution.height / 2));
        buttonPanel.getScoreReset().setPreferredSize(new Dimension(resolution.width / 4, 1 * resolution.height / 4));
        buttonPanel.getBackToItemGame().setPreferredSize(new Dimension(resolution.width / 4, 1 * resolution.height / 8));
        buttonPanel.getBackToGame().setPreferredSize(new Dimension(resolution.width / 4, 1 * resolution.height / 8));
        buttonPanel.getBackToBattle().setPreferredSize(new Dimension(resolution.width / 4, 1 * resolution.height / 8));
        buttonPanel.getBackToStart().setPreferredSize(new Dimension(resolution.width / 4, 1 * resolution.height / 8));
        buttonPanel.getSettingReset().setPreferredSize(new Dimension(resolution.width / 4, 1 * resolution.height / 8));
    }

    public void changeSize(ScreenSizeSettingPanel.Resolution resolution) {
        switch (resolution.ordinal()) {
            case 1:
                setAreaSize(resolution);
                setFontSize(fontBig);
                screenSizeArea.getSizeTwo().setSelected(true);
                break;
            case 2:
                setAreaSize(resolution);
                setFontSize(fontFull);
                screenSizeArea.getSizeThree().setSelected(true);
                break;
            case 0:
            default:
                setAreaSize(resolution);
                setFontSize(fontSmall);
                screenSizeArea.getSizeOne().setSelected(true);
                break;
        }
    }

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

    @Override
    public void changeSize(int sizeNumber) {
        this.changeSize(ScreenSizeSettingPanel.Resolution.values()[sizeNumber - 1]);
    }

    public class MockStart implements IStart {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class MockGame implements IGame {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    /*
    public static SettingCode getSettingCode() {
        return this;
    }
    */


}
