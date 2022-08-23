package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.Strings.*;

public class KeySettingPanel extends JPanel {
    JLabel keyTitle = new JLabel(keyTitleString);

    public JRadioButton getKeyOne() {
        return keyOne;
    }

    public JRadioButton getKeyTwo() {
        return keyTwo;
    }

    private JRadioButton keyOne = new JRadioButton(keyOneString);
    private JRadioButton keyTwo = new JRadioButton(keyTwoString);

    DBCalls dataCalls = new DBCalls();
    int settingKey = dataCalls.getKeySetting();

    private int KeyFoucus;
    private final boolean canMoveLeft = false;
    private final boolean canMoveRight = true;

    public enum KeyMode {
        Arrow,
        WASD;
    }

    void update(KeyMode keyMode) {
        if (keyMode == KeyMode.Arrow) {
            SettingValues.getInstance().keyChoose = 1;
            dataCalls.UpdateKeySetting(0);
            keyOne.setSelected(true);
        } else if (keyMode == KeyMode.WASD) {
            SettingValues.getInstance().keyChoose = 2;
            dataCalls.UpdateKeySetting(1);
            keyTwo.setSelected(true);
        }
    }

    void reload() {
        if (settingKey == 0) {
            //방향키
            keyOnefun();
        } else if (settingKey == 1) {
            //WASD
            keyTwofun();
        }
    }

    public KeySettingPanel() {
        this.setPreferredSize(new Dimension(250, 110));

        keyTitle.setForeground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(keyTitle);
        this.add(Box.createVerticalStrut(20));

        ButtonGroup keyGroup = new ButtonGroup();
        keyOne.setSelected(true);
        keyGroup.add(keyOne);
        keyGroup.add(keyTwo);
        add(keyOne);
        add(keyTwo);
        add(Box.createVerticalStrut(20));
        // keyArea.setPreferredSize(new Dimension(250, 70));
        setAlignmentX(LEFT_ALIGNMENT);
        setBackground(SettingValues.getInstance().backgroundColoring(false));

        keyOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(KeyMode.Arrow);
            }
        });

        keyTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(KeyMode.WASD);
            }
        });

        reload();
    }

    public void keyTwofun() {
        SettingValues.getInstance().keyChoose = 2;

        dataCalls.UpdateKeySetting(1);

        keyTwo.setSelected(true);
    }

    public void keyOnefun() {
        SettingValues.getInstance().keyChoose = 1;

        dataCalls.UpdateKeySetting(0);

        keyOne.setSelected(true);
    }

    void changeFontSize(Font font) {
        keyTitle.setFont(font);

        keyOne.setFont(font);
        keyTwo.setFont(font);
    }

    void foucusDoClick(int foucus) {
        switch (foucus) {
            case 3:
                keyOne.setSelected(true);
                keyOne.doClick();
                break;
            case 4:
                keyTwo.setSelected(true);
                keyTwo.doClick();
                break;
        }
    }

    void foucusColoring(int foucus, Color color) {
        switch (foucus) {
            case 3:
                keyOne.setBackground(color);
                break;
            case 4:
                keyTwo.setBackground(color);
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
        if (KeyFoucus < 4) {
            return false;
        }
        return true;
    }

    public boolean canMoveDown() {
        foucusMoveDown();
        if (KeyFoucus > 5) {
            return false;
        }
        return true;
    }

    public void panelFirstFoucus() {
        KeyFoucus = 4;
    }

    public void panelLastFoucus() {
        KeyFoucus = 5;
    }
}
