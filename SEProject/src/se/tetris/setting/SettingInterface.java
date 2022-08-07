package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;

public interface SettingInterface<T> {

    JLabel Title = null;
    JRadioButton selectOne = null;
    JRadioButton selectTwo = null;
    JRadioButton selectThree = null;

    abstract void update(T type);

    abstract void reload();

    abstract void changeEachSettingSize();
}
