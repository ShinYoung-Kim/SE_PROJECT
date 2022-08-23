package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.ScreenSizeSettingPanel.Resolution.*;
import static se.tetris.setting.Strings.*;

public class ScreenSizeSettingPanel extends JPanel implements SettingInterface {
    private int KeyFoucus;
    private final boolean canMoveLeft = false;
    private final boolean canMoveRight = true;

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

    interface OnUpdateListener {
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

    final SettingCode settingCode;

    @Override
    public void update(Object type) {
        if (type == Standard) {
            SettingValues.getInstance().sizeNumber = 1;
            if (onUpdateListener != null) {
                onUpdateListener.onUpdate(Standard);
            }
        } else if (type == Large) {
            SettingValues.getInstance().sizeNumber = 2;
            if (onUpdateListener != null) {
                onUpdateListener.onUpdate(Large);
            }
            sizeTwo.setSelected(true);
        } else if (type == Full) {
            SettingValues.getInstance().sizeNumber = 3;
            if (onUpdateListener != null) {
                onUpdateListener.onUpdate(Full);
            }
            sizeThree.setSelected(true);
        }
        dataCalls.UpdateWindowSetting(SettingValues.getInstance().sizeNumber - 1);
    }

    @Override
    public void reload() {
        int Window = dataCalls.getWindowSetting();
        if (Window == 0) {
            SettingValues.getInstance().sizeNumber = 1;
            sizeOne.setSelected(true);
            /*
            SettingCode.setOnReloadingScreenSizeUIListener(new SettingCode.OnReloadingScreenSizeUIListener() {
                @Override
                public void OnReloadingScreenSizeUI(ScreenSizeSettingPanel.Resolution value) {
                    SettingCode.getSettingCode().changeSize(value);
                }
            });
            */
            settingCode.changeSize(Standard);
        } else if (Window == 1) {
            SettingValues.getInstance().sizeNumber = 2;
            sizeTwo.setSelected(true);
            settingCode.changeSize(Large);
        } else if (Window == 2) {
            SettingValues.getInstance().sizeNumber = 3;
            sizeThree.setSelected(true);
            settingCode.changeSize(Full);
        }
        int sizeNumber = Window + 1;
        //SettingCode.changeSize(sizeNumber);
    }

    public ScreenSizeSettingPanel(SettingCode settingCode) {
        this.settingCode = settingCode;
        setPreferredSize(new Dimension(250, 110));

        screenSizeTitle.setForeground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(screenSizeTitle);
        add(Box.createVerticalStrut(20));

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
        add(sizeOne);
        add(sizeTwo);
        add(sizeThree);
        add(Box.createVerticalStrut(20));
        // screenSizeArea.setPreferredSize(new Dimension(250, 70));
        setAlignmentX(LEFT_ALIGNMENT);
        setBackground(SettingValues.getInstance().backgroundColoring(false));
    }

    public void sizeOnefun() {
        update(Standard);
    }

    public void sizeTwofun() {
        update(Large);
    }

    public void sizeThreefun() {
        update(Full);
    }

    void changeFontSize(Font font) {
        screenSizeTitle.setFont(font);

        sizeOne.setFont(font);
        sizeTwo.setFont(font);
        sizeThree.setFont(font);
    }

    void foucusDoClick(int foucus) {
        switch (foucus) {
            case 0:
                sizeOne.setSelected(true);
                sizeOne.doClick();
                break;
            case 1:
                sizeTwo.setSelected(true);
                sizeTwo.doClick();
                break;
            case 2:
                sizeThree.setSelected(true);
                sizeThree.doClick();
                break;
        }
    }

    void foucusColoring(int foucus, Color color) {
        switch (foucus) {
            case 0:
                sizeOne.setBackground(color);
                break;
            case 1:
                sizeTwo.setBackground(color);
                break;
            case 2:
                sizeThree.setBackground(color);
                break;
        }
    }

    public int getKeyFoucus() {
        return KeyFoucus - 1;
    }

    public void setKeyFoucus(int KeyFoucus) {
        this.KeyFoucus = KeyFoucus;
    }

    public void foucusMoveUp() {
        KeyFoucus -= 1;
    }

    public void foucusMoveDown() {
        KeyFoucus += 1;
    }

    public boolean canMoveUp() {
        foucusMoveUp();
        if (KeyFoucus < 1) {
            return false;
        }
        return true;
    }

    public boolean canMoveDown() {
        foucusMoveDown();
        if (KeyFoucus > 3) {
            return false;
        }
        return true;
    }

    public void panelFirstFoucus() {
        KeyFoucus = 1;
    }

    public void panelLastFoucus() {
        KeyFoucus = 3;
    }

    public boolean getCanMoveLeft() {
        return canMoveLeft;
    }

    public boolean getCanMoveRight() {
        return canMoveRight;
    }
}

