package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.Strings.*;

public class ColorBlindnessSettingPanel extends SettingPanel {
    JLabel colorBlindTitle = new JLabel(colorBlindTitleString);

    public JRadioButton getColorBlindOne() {
        return colorBlindOne;
    }

    public JRadioButton getColorBlindTwo() {
        return colorBlindTwo;
    }

    private JRadioButton colorBlindOne = new JRadioButton(colorBlindOneString);
    private JRadioButton colorBlindTwo = new JRadioButton(colorBlindTwoString);

    DBCalls dataCalls = new DBCalls();
    int setColor = dataCalls.getColorSetting();

    private int KeyFoucus;
    private final boolean canMoveLeft = false;
    private final boolean canMoveRight = true;
    private boolean canPanelMoveDown = true;
    private boolean canPanelMoveUp = true;
    private int firstPanelComponent = 6;
    private int lastPanellComponent = 7;

    boolean colorOnOff;

    void reload() {
        if (setColor == 0) {
            //Off
            colorOnOff = false;
            colorBlindOneFun();
        } else if (setColor == 1) {
            //On
            colorOnOff = true;
            colorBlindTwoFun();
        }
    }

    void update() {
        if (colorOnOff == true) {
            SettingValues.getInstance().colorBlindModeCheck = 1;
            dataCalls.UpdateColorSetting(SettingValues.getInstance().colorBlindModeCheck);
            colorBlindTwo.setSelected(true);
        } else if (colorOnOff == false) {
            SettingValues.getInstance().colorBlindModeCheck = 0;
            dataCalls.UpdateColorSetting(SettingValues.getInstance().colorBlindModeCheck);
            colorBlindOne.setSelected(true);
        }
    }

    public ColorBlindnessSettingPanel() {
        this.setPreferredSize(new Dimension(250, 110));

        colorBlindTitle.setForeground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(colorBlindTitle);
        this.add(Box.createVerticalStrut(20));

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
        add(colorBlindOne);
        add(colorBlindTwo);
        add(Box.createVerticalStrut(20));
        // colorBlindArea.setPreferredSize(new Dimension(250, 70));
        setAlignmentX(LEFT_ALIGNMENT);
        setBackground(SettingValues.getInstance().backgroundColoring(false));

        reload();

        setCanMoveLeft(canMoveLeft);
        setCanMoveRight(canMoveRight);
        setCanPanelMoveUp(canPanelMoveUp);
        setCanPanelMoveDown(canPanelMoveDown);
        setFirstPanelComponent(firstPanelComponent);
        setLastPanellComponent(lastPanellComponent);
    }

    public void colorBlindOneFun() {
        SettingValues.getInstance().colorBlindModeCheck = 0;
        dataCalls.UpdateColorSetting(SettingValues.getInstance().colorBlindModeCheck);
        colorBlindOne.setSelected(true);
    }

    public void colorBlindTwoFun() {
        SettingValues.getInstance().colorBlindModeCheck = 1;
        dataCalls.UpdateColorSetting(SettingValues.getInstance().colorBlindModeCheck);
        colorBlindTwo.setSelected(true);
    }

    void changeFontSize(Font font) {
        colorBlindTitle.setFont(font);

        colorBlindOne.setFont(font);
        colorBlindTwo.setFont(font);
    }

    void foucusDoClick(int foucus) {
        switch (foucus) {
            case 5:
                colorBlindOne.setSelected(true);
                colorBlindOne.doClick();
                break;
            case 6:
                colorBlindTwo.setSelected(true);
                colorBlindTwo.doClick();
                break;
        }
    }

    void foucusColoring(int foucus, Color color) {
        switch (foucus) {
            case 5:
                colorBlindOne.setBackground(color);
                break;
            case 6:
                colorBlindTwo.setBackground(color);
                break;
        }
    }
}
