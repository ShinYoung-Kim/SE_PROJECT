package se.tetris.main;

import se.tetris.component.Start;
import se.tetris.setting.SettingCode;
import javax.swing.*;

public class Tetris extends JFrame {

    public static int sizeNumber = SettingCode.getSizeNumber();

    public static void main(String[] args) {

        Start startView = new Start();

        switch (sizeNumber) {
            case 1:
                startView.setSize(400, 600);
                break;
            case 2:
                startView.setSize(800, 1200);
                break;
            case 3:
                startView.setExtendedState(JFrame.MAXIMIZED_BOTH);
                startView.setUndecorated(true);
                break;
            default:
                startView.setSize(400, 600);
                break;
        }
        startView.setVisible(true);

        //Board main = new Board();
        //main.setSize(400, 600);
        //main.setVisible(true);
    }
}