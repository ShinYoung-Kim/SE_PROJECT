package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.SettingCode.grayMade;

public class DifficultySettingPanel extends JPanel {
    JLabel modeTitle = new JLabel("모드 선택");

    public JRadioButton getModeOne() {
        return modeOne;
    }

    public JRadioButton getModeTwo() {
        return modeTwo;
    }

    public JRadioButton getModeThree() {
        return modeThree;
    }

    private JRadioButton modeOne = new JRadioButton("Easy");
    private JRadioButton modeTwo = new JRadioButton("Normal");
    private JRadioButton modeThree = new JRadioButton("Hard");

    DBCalls dataCalls = new DBCalls();
    int setLevel = dataCalls.getLevelSetting();

    enum Difficulty{
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

    void reload(){
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
        setBackground(grayMade);

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
}
