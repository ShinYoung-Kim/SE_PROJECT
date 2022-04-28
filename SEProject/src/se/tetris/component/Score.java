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
import java.util.Vector;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import se.tetris.component.*;
import se.tetris.data.DBCalls;

class tabViewBox extends JFrame {

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

class stdTable extends JPanel {
    DBCalls dataCalls = new DBCalls();

    int Count = 0;
    int FocusMode = 0;

    String[] title = { "랭킹", "모드", "닉네임", "점수" };

    ArrayList<ScoreItem> list = null;
    String[][] data = null;
    String[][] data2 = null;

    JTable table = new JTable();
    JTable table2 = new JTable();
    JTable table3 = new JTable();

    public String[][] CheckMode(int mode) {
        list = dataCalls.StdScoreList;

        dataCalls.get10StdScoreData(mode);

        data = new String[list.size()][4];

        return data;
    }

    public void UpdateData() {
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = String.valueOf(list.get(i).getRank());

//			System.out.println("아이템모드 데이터 랭크: " + data[i][0]);
            if (list.get(i).getLevel() == 0) {
                data[i][1] = "Normal";

            } else if (list.get(i).getLevel() == 1) {
                data[i][1] = "Easy";
            } else if (list.get(i).getLevel() == 2) {
                data[i][1] = "Hard";
            }

            data[i][2] = list.get(i).getNickName();
            data[i][3] = String.valueOf(list.get(i).getScore());

        }
    }

    stdTable() {

        JPanel p1 = new JPanel();
        p1.setBackground(Color.white);

        JButton b1 = new JButton("Easy");
        JButton b2 = new JButton("NormL");
        JButton b3 = new JButton("Hard");

        p1.add(b1);
        p1.add(b2);
        p1.add(b3);


        JPanel p2 = new JPanel(new FlowLayout());
        p2.setBackground(Color.black);

        String Mode = "";

        if (ScoreItem.STD_SCORE_RECENT[0] == "0")
            Mode = "Normal";
        else if (ScoreItem.STD_SCORE_RECENT[0] == "1")
            Mode = "Easy";
        else
            Mode = "Hard";


        JLabel l0 = new JLabel("업데이트 된 스코어 >> ");
        JLabel l1 = new JLabel("모드 : "+ Mode+ "\t");
        JLabel l2 = new JLabel("닉네임 : "+ ScoreItem.STD_SCORE_RECENT[1]+ "\t");
        JLabel l3 = new JLabel("점수 : "+ ScoreItem.STD_SCORE_RECENT[2]);

        l0.setForeground(new Color(106,215,255));
        l1.setForeground(new Color(106,215,255));
        l2.setForeground(new Color(106,215,255));
        l3.setForeground(new Color(106,215,255));



        System.out.println("Mode : "+ Mode);
        System.out.println("Name : "+ ScoreItem.STD_SCORE_RECENT[1]);
        System.out.println("Score : "+ ScoreItem.STD_SCORE_RECENT[2]);

        p2.add(l0);
        p2.add(l1);
        p2.add(l2);
        p2.add(l3);




        if (Count == 0) {
            CheckMode(0);
            UpdateData();

            DefaultTableModel model = new DefaultTableModel(data, title);
            table.setModel(model);

            JScrollPane scrollview = new JScrollPane(table);

            scrollview.setPreferredSize(new Dimension(350, 450));
            scrollview.setBackground(Color.white);

            add(p1, BorderLayout.NORTH);
            add(scrollview, BorderLayout.CENTER);
            if(ScoreItem.CODE_RECENT == 1)
                add(p2, BorderLayout.SOUTH);

            setBackground(Color.white);
        } else {
            if (FocusMode == 0) {
                CheckMode(FocusMode);
                UpdateData();
                DefaultTableModel model = new DefaultTableModel(data, title);
                table.setModel(model);

                JScrollPane scrollview = new JScrollPane(table);

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if(ScoreItem.CODE_RECENT == 1)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            } else if (FocusMode == 1) {
                CheckMode(FocusMode);
                UpdateData();
                DefaultTableModel model = new DefaultTableModel(data, title);
                table2.setModel(model);

                JScrollPane scrollview = new JScrollPane(table);

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if(ScoreItem.CODE_RECENT == 1)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            } else {
                CheckMode(FocusMode);
                UpdateData();
                DefaultTableModel model = new DefaultTableModel(data, title);
                table3.setModel(model);

                JScrollPane scrollview = new JScrollPane(table);

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if(ScoreItem.CODE_RECENT == 1)
                    add(p2, BorderLayout.SOUTH);


                setBackground(Color.white);
            }
        }

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 1;
                Count++;
                CheckMode(FocusMode);
                UpdateData();

                table.hide();
                table2.show();
                table3.hide();
            }
        });

        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 0;
                Count++;
                CheckMode(FocusMode);
                UpdateData();

                table.show();
                table2.hide();
                table3.hide();
            }
        });

        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 2;
                Count++;
                CheckMode(FocusMode);
                UpdateData();

                table.hide();
                table2.hide();
                table3.show();
            }
        });

    }
}

class itemTable extends JPanel {
    DBCalls dataCalls = new DBCalls();

    int Count = 0;
    int FocusMode = 0;

    String[] title = { "랭킹", "모드", "닉네임", "점수" };

    ArrayList<ScoreItem> list = null;
    String[][] data = null;
    String[][] data2 = null;

    JTable table = new JTable();
    JTable table2 = new JTable();
    JTable table3 = new JTable();

    public String[][] CheckMode(int mode) {
        list = dataCalls.ItemScoreList;

        dataCalls.get10ItemScoreData(mode);

        data = new String[list.size()][4];

        return data;
    }

    public void UpdateData() {
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = String.valueOf(list.get(i).getRank());

//			System.out.println("아이템모드 데이터 랭크: " + data[i][0]);
            if (list.get(i).getLevel() == 0) {
                data[i][1] = "Normal";

            } else if (list.get(i).getLevel() == 1) {
                data[i][1] = "Easy";
            } else if (list.get(i).getLevel() == 2) {
                data[i][1] = "Hard";
            }

            data[i][2] = list.get(i).getNickName();
            data[i][3] = String.valueOf(list.get(i).getScore());

        }
    }

    itemTable() {
        JPanel p1 = new JPanel();
        p1.setBackground(Color.white);

        JButton b1 = new JButton("Easy");
        JButton b2 = new JButton("NormL");
        JButton b3 = new JButton("Hard");

        p1.add(b1);
        p1.add(b2);
        p1.add(b3);

        JPanel p2 = new JPanel(new FlowLayout());
        p2.setBackground(Color.black);

        String Mode = "";

        if (ScoreItem.ITEM_SCORE_RECENT[0] == "0")
            Mode = "Normal";
        else if (ScoreItem.ITEM_SCORE_RECENT[0] == "1")
            Mode = "Easy";
        else
            Mode = "Hard";


        JLabel l0 = new JLabel("업데이트 된 스코어 >> ");
        JLabel l1 = new JLabel("모드 : "+ Mode+ "\t");
        JLabel l2 = new JLabel("닉네임 : "+ ScoreItem.ITEM_SCORE_RECENT[1]+ "\t");
        JLabel l3 = new JLabel("점수 : "+ ScoreItem.ITEM_SCORE_RECENT[2]);

        if (Count == 0) {
            CheckMode(0);
            UpdateData();

            DefaultTableModel model = new DefaultTableModel(data, title);

            table.setModel(model);

            JScrollPane scrollview = new JScrollPane(table);

            scrollview.setPreferredSize(new Dimension(350, 450));
            scrollview.setBackground(Color.white);

            add(p1, BorderLayout.NORTH);
            add(scrollview, BorderLayout.CENTER);
            if(ScoreItem.CODE_RECENT == 2)
                add(p2, BorderLayout.SOUTH);

            setBackground(Color.white);
        } else {
            if (FocusMode == 0) {
                CheckMode(FocusMode);
                UpdateData();
                DefaultTableModel model = new DefaultTableModel(data, title);
                table.setModel(model);

                JScrollPane scrollview = new JScrollPane(table);

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if(ScoreItem.CODE_RECENT == 2)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            } else if (FocusMode == 1) {
                CheckMode(FocusMode);
                UpdateData();
                DefaultTableModel model = new DefaultTableModel(data, title);
                table2.setModel(model);

                JScrollPane scrollview = new JScrollPane(table);

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if(ScoreItem.CODE_RECENT == 2)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            } else {
                CheckMode(FocusMode);
                UpdateData();
                DefaultTableModel model = new DefaultTableModel(data, title);
                table3.setModel(model);

                JScrollPane scrollview = new JScrollPane(table);

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if(ScoreItem.CODE_RECENT == 2)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            }
        }

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 1;
                Count++;
                CheckMode(FocusMode);
                UpdateData();

                table.hide();
                table2.show();
                table3.hide();
            }
        });

        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 0;
                Count++;
                CheckMode(FocusMode);
                UpdateData();

                table.show();
                table2.hide();
                table3.hide();
            }
        });

        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 2;
                Count++;
                CheckMode(FocusMode);
                UpdateData();

                table.hide();
                table2.hide();
                table3.show();
            }
        });

    }
}

public class Score extends tabViewBox {

    public Score() {

        JLabel Title = new JLabel("SeoulTech SE Tettris Score");
        Title.setFont(new Font("Serif", Font.BOLD, 17));
        Title.setHorizontalAlignment(JLabel.CENTER);
        Title.setVerticalAlignment(JLabel.CENTER);

        JLabel Team = new JLabel("Team one");
        Team.setHorizontalAlignment(JLabel.CENTER);
        Team.setVerticalAlignment(JLabel.CENTER);

        // JPanel btnGroup = new JPanel();
        // btnGroup.setBackground(Color.white);
        // btnGroup.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        tabViewBox tabScoreView = new tabViewBox();

    }

}