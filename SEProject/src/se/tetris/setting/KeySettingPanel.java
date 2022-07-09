package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.SettingCode.grayMade;

public class KeySettingPanel extends JPanel {
    JLabel keyTitle = new JLabel("방향키 선택");

    public JRadioButton getKeyOne() {
        return keyOne;
    }

    public JRadioButton getKeyTwo() {
        return keyTwo;
    }

    private JRadioButton keyOne = new JRadioButton("방향키");
    private JRadioButton keyTwo = new JRadioButton("WASD");

    DBCalls dataCalls = new DBCalls();
    int setKey = dataCalls.getKeySetting();

    public enum KeyMode{
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
        if (setKey == 0) {
            //방향키
            keyOnefun();
        } else if (setKey == 1) {
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
        setBackground(grayMade);

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
}
