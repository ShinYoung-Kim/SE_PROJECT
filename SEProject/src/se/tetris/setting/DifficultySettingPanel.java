package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.Strings.*;

public class DifficultySettingPanel extends JPanel {
    JLabel modeTitle = new JLabel(modeTitleString);

    public JRadioButton getModeOne() {
        return modeOne;
    }

    public JRadioButton getModeTwo() {
        return modeTwo;
    }

    public JRadioButton getModeThree() {
        return modeThree;
    }

    private JRadioButton modeOne = new JRadioButton(modeOneString);
    private JRadioButton modeTwo = new JRadioButton(modeTwoString);
    private JRadioButton modeThree = new JRadioButton(modeThreeString);

    DBCalls dataCalls = new DBCalls();
    int setLevel = dataCalls.getLevelSetting();

    private int KeyFoucus;
    private final boolean canMoveLeft = false;
    private final boolean canMoveRight = true;

    enum Difficulty {
        Easy,
        Normal,
        Hard;
    }

    void update(Difficulty difficulty) {
        if (difficulty == Difficulty.Easy) {
            SettingValues.getInstance().intervalNumber = 2000;
            SettingValues.getInstance().modeChoose = 1;
            dataCalls.UpdateLevelSetting(1);
            modeOne.setSelected(true);
        } else if (difficulty == Difficulty.Normal) {
            SettingValues.getInstance().intervalNumber = 1000;
            SettingValues.getInstance().modeChoose = 2;
            dataCalls.UpdateLevelSetting(0);
            modeTwo.setSelected(true);
        } else if (difficulty == Difficulty.Hard) {
            SettingValues.getInstance().intervalNumber = 800;
            SettingValues.getInstance().modeChoose = 3;
            dataCalls.UpdateLevelSetting(2);
            modeThree.setSelected(true);
        }
    }

    void reload() {
        if (setLevel == 0) {
            //Normal
            modeTwofun();
        } else if (setLevel == 1) {
            //Easy
            modeOnefun();
        } else if (setLevel == 2) {
            //Hard
            modeThreefun();
        }
    }

    public DifficultySettingPanel() {
        this.setPreferredSize(new Dimension(250, 110));

        modeTitle.setForeground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(modeTitle);
        this.add(Box.createVerticalStrut(20));

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
        add(modeOne);
        add(modeTwo);
        add(modeThree);
        add(Box.createVerticalStrut(20));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // modeArea.setPreferredSize(new Dimension(250, 70));
        setAlignmentX(LEFT_ALIGNMENT);
        setBackground(SettingValues.getInstance().backgroundColoring(false));

        reload();
    }

    public void modeThreefun() {
        SettingValues.getInstance().intervalNumber = 800;
        SettingValues.getInstance().modeChoose = 3;
        dataCalls.UpdateLevelSetting(2);
        modeThree.setSelected(true);
    }

    public void modeTwofun() {
        SettingValues.getInstance().intervalNumber = 1000;
        SettingValues.getInstance().modeChoose = 2;
        dataCalls.UpdateLevelSetting(0);
        modeTwo.setSelected(true);
    }

    public void modeOnefun() {
        SettingValues.getInstance().intervalNumber = 2000;
        SettingValues.getInstance().modeChoose = 1;
        dataCalls.UpdateLevelSetting(1);
        modeOne.setSelected(true);
    }

    void changeFontSize(Font font) {
        modeTitle.setFont(font);

        modeOne.setFont(font);
        modeTwo.setFont(font);
        modeThree.setFont(font);
    }

    void foucusDoClick(int foucus) {
        switch (foucus) {
            case 7:
                modeOne.setSelected(true);
                modeOne.doClick();
                break;
            case 8:
                modeTwo.setSelected(true);
                modeTwo.doClick();
                break;
            case 9:
                modeThree.setSelected(true);
                modeThree.doClick();
                break;
        }
    }

    void foucusColoring(int foucus, Color color) {
        switch (foucus) {
            case 7:
                modeOne.setBackground(color);
                break;
            case 8:
                modeTwo.setBackground(color);
                break;
            case 9:
                modeThree.setBackground(color);
                break;
        }
    }

    public int getKeyFoucus() {
        return KeyFoucus - 1;
    }

    public void foucusMoveUp() {
        KeyFoucus -= 1;
    }

    public void foucusMoveDown() {
        KeyFoucus += 1;
    }

    public boolean canMoveUp() {
        foucusMoveUp();
        if (KeyFoucus < 8) {
            return false;
        }
        return true;
    }

    public boolean canMoveDown() {
        foucusMoveDown();
        if (KeyFoucus > 10) {
            return false;
        }
        return true;
    }

    public void panelFirstFoucus() {
        KeyFoucus = 8;
    }

    public void panelLastFoucus() {
        KeyFoucus = 10;
    }
}
