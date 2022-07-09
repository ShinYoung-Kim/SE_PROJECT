package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.ScreenSizeSettingPanel.Resolution.*;
import static se.tetris.setting.SettingCode.grayMade;
import static se.tetris.setting.Strings.*;

public class ScreenSizeSettingPanel extends JPanel implements SettingInterface{

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

    JLabel screenSizeTitle = new JLabel(screenSizeTitleString);

    public JRadioButton getSizeOne() {
        return sizeOne;
    }

    public JRadioButton getSizeTwo() {
        return sizeTwo;
    }

    public JRadioButton getSizeThree() {
        return sizeThree;
    }

    private JRadioButton sizeOne = new JRadioButton(sizeOneString);
    private JRadioButton sizeTwo = new JRadioButton(sizeTwoString);
    private JRadioButton sizeThree = new JRadioButton(sizeThreeString);

    DBCalls dataCalls = new DBCalls();
    int Window = dataCalls.getWindowSetting();

    @Override
    public void update(Object type) {
        if (type == Standard) {
            SettingValues.getInstance().sizeNumber = 1;
            dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);
            if (onUpdateListener != null) {
                onUpdateListener.onUpdate(Standard);
            }
            sizeOne.setSelected(true);
        } else if(type == Large) {
            SettingValues.getInstance().sizeNumber = 2;
            dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);
            if (onUpdateListener != null) {
                onUpdateListener.onUpdate(Large);
            }
            sizeTwo.setSelected(true);
        } else if(type == Full) {
            SettingValues.getInstance().sizeNumber = 3;
            dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);
            if (onUpdateListener != null) {
                onUpdateListener.onUpdate(Full);
            }
            sizeThree.setSelected(true);
        }
    }

    @Override
    public void reload() {
        if (Window == 0) {
            update(Standard);
        } else if (Window == 1) {
            update(Large);
        } else if (Window == 2) {
            update(Full);
        }
    }

    @Override
    public void changeEachSettingSize() {
        //interface에 title과 각 radiobutton들을 만들어놓고 class에서 지정을 해주려했고 + settingcode에서 changesize로 한 번에 조정하는 대신
        //이 함수를 통해 각 사이즈를 조절해주려했는데 인터페이스가 상수만을 가질 수 있어서 값 바꾸는 게 안 됩니다.ㅠㅠ
    }

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

        reload();
    }
    public void sizeOnefun() {
        SettingValues.getInstance().sizeNumber = 1;
        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate(Standard);
        }
        sizeOne.setSelected(true);
    }

    public void sizeTwofun() {
        SettingValues.getInstance().sizeNumber = 2;
        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate(Large);
        }
        sizeTwo.setSelected(true);
    }

    public void sizeThreefun() {
        SettingValues.getInstance().sizeNumber = 3;
        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate(Full);
        }
        sizeThree.setSelected(true);
    }
}

