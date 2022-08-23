package se.tetris.setting;

import se.tetris.common.Navigation;
import se.tetris.component.*;
import se.tetris.data.DBCalls;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private int KeyFoucus;
    private final boolean canMoveLeft = true;
    private final boolean canMoveRight = false;

    public SettingButtonPanel(SettingCode settingCode) {
        this.setPreferredSize(new Dimension(80, 300));

        this.settingCode = settingCode;

        add(scoreReset);
        add(BackToGame);
        add(BackToItemGame);
        add(BackToBattle);
        add(BackToStart);
        add(settingReset);

        scoreReset.setBackground(SettingValues.getInstance().backgroundColoring(false));
        BackToGame.setBackground(SettingValues.getInstance().backgroundColoring(false));
        BackToBattle.setBackground(SettingValues.getInstance().backgroundColoring(false));
        BackToItemGame.setBackground(SettingValues.getInstance().backgroundColoring(false));
        BackToStart.setBackground(SettingValues.getInstance().backgroundColoring(false));
        settingReset.setBackground(SettingValues.getInstance().backgroundColoring(false));

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

    void changeButtonSize(int width, int height) {
        scoreReset.setPreferredSize(new Dimension(width / 4, 1 * height / 4));
        BackToItemGame.setPreferredSize(new Dimension(width / 4, 1 * height / 8));
        BackToGame.setPreferredSize(new Dimension(width / 4, 1 * height / 8));
        BackToBattle.setPreferredSize(new Dimension(width / 4, 1 * height / 8));
        BackToStart.setPreferredSize(new Dimension(width / 4, 1 * height / 8));
        settingReset.setPreferredSize(new Dimension(width / 4, 1 * height / 8));
    }

    void changeFontSize(Font font) {
        scoreReset.setFont(font);
        BackToItemGame.setFont(font);
        BackToGame.setFont(font);
        BackToBattle.setFont(font);
        BackToStart.setFont(font);
        settingReset.setFont(font);
    }

    void foucusDoClick(int foucus) {
        switch (foucus) {
            case 10:
                scoreReset.setSelected(true);
                scoreReset.doClick();
                break;
            case 11:
                BackToGame.setSelected(true);
                BackToGame.doClick();
                break;
            case 12:
                BackToItemGame.setSelected(true);
                BackToItemGame.doClick();
                break;
            case 13:
                BackToBattle.setSelected(true);
                BackToBattle.doClick();
                break;
            case 14:
                BackToStart.setSelected(true);
                BackToStart.doClick();
                break;
            case 15:
                settingReset.setSelected(true);
                settingReset.doClick();
                break;
        }
    }

    void foucusColoring(int foucus, Color color) {
        switch (foucus) {
            case 10:
                scoreReset.setBackground(color);
                break;
            case 11:
                BackToGame.setBackground(color);
                break;
            case 12:
                BackToItemGame.setBackground(color);
                break;
            case 13:
                BackToBattle.setBackground(color);
                break;
            case 14:
                BackToStart.setBackground(color);
                break;
            case 15:
                settingReset.setBackground(color);
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
        if (KeyFoucus < 11) {
            return false;
        }
        return true;
    }

    public boolean canMoveDown() {
        foucusMoveDown();
        if (KeyFoucus > 16) {
            return false;
        }
        return true;
    }

    public void panelFirstFoucus() {
        KeyFoucus = 11;
    }

    public void panelLastFoucus() {
        KeyFoucus = 16;
    }
}
