package se.tetris.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class ScoreItem {

    public int ranking;
    public int level;
    public String nickname;
    public long Scroe;

    public ScoreItem(int r, int l, String n, long s) {
        Scroe = s;

        setNickName(n);
        setRank(r);
        setLevel(l);
    }
    public void setScore(int score)
    {
        this.Scroe=score;
    }

    public long getScore()
    {
        return Scroe;
    }
    public void setRank(int rank)
    {
        this.ranking = rank;
    }

    public int getRank()
    {
        return ranking;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }
    public void setNickName(String name)
    {
        this.nickname = name;
    }

    public String getNickName()
    {
        return nickname;
    }

    public void clearScore()
    {
        //설정에서 초기화
    }

}

class tabViewBox extends JFrame{

    public tabViewBox() {
        setTitle("SeoulTech SE Tettris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);

        setBackground(Color.white);
        display();
    }

    public void display() {
        JTabbedPane tabView = new JTabbedPane();

        stdTable stdScoreView = new stdTable();
        itemTable itemScoreView = new itemTable();
        tabView.addTab("기본 모드 랭킹", stdScoreView);
        tabView.addTab("아이템 모드 랭킹", itemScoreView);

        add(tabView);
    }
}

class stdTable extends JPanel{
    stdTable(){
        String[] title = {"랭킹", "닉네임", "점수"};
        String[][] data = {
                {"1", "임영뭉","10000"},
                {"2", "임영뭉","10000"},
                {"3", "임영뭉","10000"},
        };
        JTable table = new JTable(data, title);
        JScrollPane scrollview = new JScrollPane(table);
        scrollview.setPreferredSize(new Dimension(350,500));
        scrollview.setBackground(Color.white);
        add(scrollview, BorderLayout.CENTER);
        setBackground(Color.white);
    }
}

class itemTable extends JPanel{
    itemTable(){
        String[] title = {"랭킹", "닉네임", "점수"};
        String[][] data = {
                {"1", "임영뭉1","10000"},
                {"2", "임영뭉2","10000"},
                {"3", "임영뭉3","10000"},
        };

        JTable table = new JTable(data, title);
        JScrollPane scrollview = new JScrollPane(table);
        scrollview.setPreferredSize(new Dimension(350,500));
        scrollview.setBackground(Color.white);
        add(scrollview, BorderLayout.CENTER);
        setBackground(Color.white);
    }
}

public class Score extends tabViewBox {

    public Score() {
        JLabel Title = new JLabel("SeoulTech SE Tettris Score");
        Title.setFont(new Font("Serif",Font.BOLD,17));
        Title.setHorizontalAlignment(JLabel.CENTER);
        Title.setVerticalAlignment(JLabel.CENTER);

        JLabel Team = new JLabel("Team one");
        Team.setHorizontalAlignment(JLabel.CENTER);
        Team.setVerticalAlignment(JLabel.CENTER);

        //JPanel btnGroup = new JPanel();
        //btnGroup.setBackground(Color.white);
        //btnGroup.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        tabViewBox tabScoreView = new tabViewBox();
    }
}