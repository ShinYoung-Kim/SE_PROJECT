package se.tetris.setting;

import se.tetris.common.Navigation;
import se.tetris.component.*;
import se.tetris.data.DBCalls;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static se.tetris.setting.SettingCode.grayMade;
import static se.tetris.setting.Strings.*;

public class SettingButtonPanel extends JPanel {

    final SettingCode settingCode;

    public JButton getScoreReset() {
        return scoreReset;
    }

    public JButton getBackToGame() {
        return BackToGame;
    }

    public JButton getBackToItemGame() {
        return BackToItemGame;
    }

    public JButton getBackToStart() {
        return BackToStart;
    }

    public JButton getBackToBattle() {
        return BackToBattle;
    }

    public JButton getSettingReset() {
        return settingReset;
    }

    private JButton scoreReset = new JButton(addingHTMLfun(scoreResetString));
    private JButton BackToGame = new JButton(addingHTMLfun(BackToGameString));
    private JButton BackToItemGame = new JButton(addingHTMLfun(BackToItemGameString));
    private JButton BackToStart = new JButton(addingHTMLfun(BackToStartString));
    private JButton BackToBattle = new JButton(addingHTMLfun(BackToBattleString));
    private JButton settingReset = new JButton(addingHTMLfun(settingResetString));

    DBCalls dataCalls = new DBCalls();

    int sizeNumber = SettingValues.getInstance().sizeNumber;

    public SettingButtonPanel(SettingCode settingCode) {
        this.setPreferredSize(new Dimension(80, 300));

        this.settingCode = settingCode;

        add(scoreReset);
        add(BackToGame);
        add(BackToItemGame);
        add(BackToBattle);
        add(BackToStart);
        add(settingReset);

        scoreReset.setBackground(grayMade);
        BackToGame.setBackground(grayMade);
        BackToBattle.setBackground(grayMade);
        BackToItemGame.setBackground(grayMade);
        BackToStart.setBackground(grayMade);
        settingReset.setBackground(grayMade);

        EtchedBorder scoreBorder = new EtchedBorder();
        setBorder(scoreBorder);
        scoreReset.setAlignmentX(CENTER_ALIGNMENT);
        BackToGame.setAlignmentX(CENTER_ALIGNMENT);
        BackToBattle.setAlignmentX(CENTER_ALIGNMENT);
        BackToItemGame.setAlignmentX(CENTER_ALIGNMENT);
        BackToStart.setAlignmentX(CENTER_ALIGNMENT);
        settingReset.setAlignmentX(CENTER_ALIGNMENT);

        scoreReset.setPreferredSize(new Dimension(80, 75));
        BackToItemGame.setPreferredSize(new Dimension(80, 75));
        BackToGame.setPreferredSize(new Dimension(80, 75));
        BackToBattle.setPreferredSize(new Dimension(80, 75));
        BackToStart.setPreferredSize(new Dimension(80, 75));
        settingReset.setPreferredSize(new Dimension(80, 75));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        BackToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigation.getInstance().navigate(Navigation.BOARD_SCREEN);
            }
        });

        BackToBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigation.getInstance().navigate(Navigation.START_BATTLE);
            }
        });

        BackToItemGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigation.getInstance().navigate(Navigation.ITEM_BOARD_SCREEN);
            }
        });

        BackToStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigation.getInstance().navigate(Navigation.START_SCREEN);
            }
        });

        settingReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingCode.screenSizeArea.sizeOnefun();
                settingCode.keySettingPanel.keyOnefun();
                settingCode.colorBlindnessSettingPanel.colorBlindOneFun();
                settingCode.difficultySettingPanel.modeTwofun();
                //settingCode 안에 reset을 만들기
            }
        });

        scoreReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataCalls.refreshScoreData();
                int Result = JOptionPane.showConfirmDialog(null, resetMessageString, resetTitleString,
                        JOptionPane.DEFAULT_OPTION);
            }
        });
    }
}
