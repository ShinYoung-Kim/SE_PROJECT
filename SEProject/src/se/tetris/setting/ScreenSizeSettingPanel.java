package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.SettingCode.grayMade;

public class ScreenSizeSettingPanel extends JPanel {

    public enum Resolution {
        Standard(400, 600),
        Large(800, 800),
        Full(SettingCode.screenWidth, SettingCode.screenHeight);

        public final int width;
        public final int height;
        Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    interface OnUpdateListener{
        void onUpdate(Resolution value);
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    private OnUpdateListener onUpdateListener = null;

    JLabel screenSizeTitle = new JLabel("화면 크기 조절");

    public JRadioButton sizeOne = new JRadioButton("표준");
    public JRadioButton sizeTwo = new JRadioButton("크게");
    public JRadioButton sizeThree = new JRadioButton("전체 화면 모드");

    DBCalls dataCalls = new DBCalls();
    int Window = dataCalls.getWindowSetting();

    public ScreenSizeSettingPanel() {

        this.setPreferredSize(new Dimension(250, 110));

        screenSizeTitle.setForeground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(screenSizeTitle);
        this.add(Box.createVerticalStrut(20));
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
        this.add(sizeOne);
        this.add(sizeTwo);
        this.add(sizeThree);
        add(Box.createVerticalStrut(20));
        // screenSizeArea.setPreferredSize(new Dimension(250, 70));
        setAlignmentX(LEFT_ALIGNMENT);
        setBackground(grayMade);

        if (Window == 0) {
            sizeOnefun();
        } else if (Window == 1) {
            sizeTwofun();
        } else if (Window == 2) {
            sizeThreefun();
        }
    }
    public void sizeOnefun() {
        SettingValues.getInstance().sizeNumber = 1;

        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);

        if (onUpdateListener != null) {
            onUpdateListener.onUpdate(Resolution.Standard);
        }
        sizeOne.setSelected(true);
    }

    public void sizeTwofun() {
        SettingValues.getInstance().sizeNumber = 2;

        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);

        if (onUpdateListener != null) {
            onUpdateListener.onUpdate(Resolution.Large);
        }
        sizeTwo.setSelected(true);
    }

    public void sizeThreefun() {
        SettingValues.getInstance().sizeNumber = 3;

        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);

        if (onUpdateListener != null) {
            onUpdateListener.onUpdate(Resolution.Full);
        }
        sizeThree.setSelected(true);
    }
}

