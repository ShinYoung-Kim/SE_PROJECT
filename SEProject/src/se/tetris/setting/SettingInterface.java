package se.tetris.setting;

import se.tetris.data.DBCalls;

import javax.swing.*;
import java.awt.*;

public interface SettingInterface<T> {
    abstract void update(T type);
    abstract void reload();
}
