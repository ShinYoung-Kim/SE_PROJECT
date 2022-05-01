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

    stdTable() {

        dataCalls.get10StdNormalScoreData();
        dataCalls.get10StdEasyScoreData();
        dataCalls.get10StdHardScoreData();

        ArrayList<ScoreItem> listEasy = dataCalls.StdEasyScoreList;
        ArrayList<ScoreItem> listNormal = dataCalls.StdNormalScoreList;
        ArrayList<ScoreItem> listHard = dataCalls.StdHardScoreList;

        String[][] data = new String[listNormal.size()][4];
        String[][] data2 = new String[listEasy.size()][4];
        String[][] data3 = new String[listHard.size()][4];

        for (int i = 0; i < listNormal.size(); i++) {
            data[i][0] = String.valueOf(listNormal.get(i).getRank());
            data[i][1] = "Normal";
            data[i][2] = listNormal.get(i).getNickName();
            data[i][3] = String.valueOf(listNormal.get(i).getScore());
        }

        for (int i = 0; i < listEasy.size(); i++) {
            data2[i][0] = String.valueOf(listEasy.get(i).getRank());
            data2[i][1] = "Easy";
            data2[i][2] = listEasy.get(i).getNickName();
            data2[i][3] = String.valueOf(listEasy.get(i).getScore());
        }

        for (int i = 0; i < listHard.size(); i++) {
            data3[i][0] = String.valueOf(listHard.get(i).getRank());
            data3[i][1] = "Hard";
            data3[i][2] = listHard.get(i).getNickName();
            data3[i][3] = String.valueOf(listHard.get(i).getScore());
        }

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


        JLabel l0 = new JLabel("업데이트 된 스코어 >> ");
        JLabel l1 = new JLabel("모드 : " + ScoreItem.STD_SCORE_RECENT[0] + "\t");
        JLabel l2 = new JLabel("닉네임 : " + ScoreItem.STD_SCORE_RECENT[1] + "\t");
        JLabel l3 = new JLabel("점수 : " + ScoreItem.STD_SCORE_RECENT[2]);

        l0.setForeground(new Color(106, 215, 255));
        l1.setForeground(new Color(106, 215, 255));
        l2.setForeground(new Color(106, 215, 255));
        l3.setForeground(new Color(106, 215, 255));

        System.out.println("Mode : " + ScoreItem.STD_SCORE_RECENT[0]);
        System.out.println("Name : " + ScoreItem.STD_SCORE_RECENT[1]);
        System.out.println("Score : " + ScoreItem.STD_SCORE_RECENT[2]);

        p2.add(l0);
        p2.add(l1);
        p2.add(l2);
        p2.add(l3);

        JTable table = new JTable(data, title);
        JTable table2 = new JTable(data2, title);
        JTable table3 = new JTable(data3, title);

        JScrollPane scrollview = new JScrollPane(table);
        JScrollPane scrollview2 = new JScrollPane(table2);
        JScrollPane scrollview3 = new JScrollPane(table3);

        if (Count == 0) {
            scrollview.setPreferredSize(new Dimension(350, 450));
            scrollview.setBackground(Color.white);

            add(p1, BorderLayout.NORTH);
            add(scrollview, BorderLayout.CENTER);

            scrollview.show();
            scrollview2.hide();
            scrollview3.hide();

            if (ScoreItem.CODE_RECENT == 1)
                add(p2, BorderLayout.SOUTH);

            setBackground(Color.white);
        }

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 1;
                Count++;

                table.hide();
                table2.show();
                table3.hide();

                scrollview.hide();
                scrollview2.show();
                scrollview3.hide();

                scrollview2.setPreferredSize(new Dimension(350, 450));
                scrollview2.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview2, BorderLayout.CENTER);
                if (ScoreItem.CODE_RECENT == 1)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            }
        });

        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 0;
                Count++;

                table.show();
                table2.hide();
                table3.hide();

                scrollview.show();
                scrollview2.hide();
                scrollview3.hide();

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if (ScoreItem.CODE_RECENT == 1)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            }
        });

        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 2;
                Count++;

                table.hide();
                table2.hide();
                table3.show();

                scrollview.hide();
                scrollview2.hide();
                scrollview3.show();

                scrollview3.setPreferredSize(new Dimension(350, 450));
                scrollview3.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview3, BorderLayout.CENTER);
                if (ScoreItem.CODE_RECENT == 1)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            }
        });

    }
}

class itemTable extends JPanel {
    DBCalls dataCalls = new DBCalls();

    int Count = 0;
    int FocusMode = 0;

    String[] title = { "랭킹", "모드", "닉네임", "점수" };

    itemTable() {

        dataCalls.get10ItemNormalScoreData();
        dataCalls.get10ItemEasyScoreData();
        dataCalls.get10ItemHardScoreData();

        ArrayList<ScoreItem> listEasy = dataCalls.ItemEasyScoreList;
        ArrayList<ScoreItem> listNormal = dataCalls.ItemNormalScoreList;
        ArrayList<ScoreItem> listHard = dataCalls.ItemHardScoreList;

        String[][] data = new String[listNormal.size()][4];
        String[][] data2 = new String[listEasy.size()][4];
        String[][] data3 = new String[listHard.size()][4];

        for (int i = 0; i < listNormal.size(); i++) {
            data[i][0] = String.valueOf(listNormal.get(i).getRank());
            data[i][1] = "Normal";
            data[i][2] = listNormal.get(i).getNickName();
            data[i][3] = String.valueOf(listNormal.get(i).getScore());
        }

        for (int i = 0; i < listEasy.size(); i++) {
            data2[i][0] = String.valueOf(listEasy.get(i).getRank());
            data2[i][1] = "Easy";
            data2[i][2] = listEasy.get(i).getNickName();
            data2[i][3] = String.valueOf(listEasy.get(i).getScore());
        }

        for (int i = 0; i < listHard.size(); i++) {
            data3[i][0] = String.valueOf(listHard.get(i).getRank());
            data3[i][1] = "Hard";
            data3[i][2] = listHard.get(i).getNickName();
            data3[i][3] = String.valueOf(listHard.get(i).getScore());
        }

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



        JLabel l0 = new JLabel("업데이트 된 스코어 >> ");
        JLabel l1 = new JLabel("모드 : " + ScoreItem.ITEM_SCORE_RECENT[0] + "\t");
        JLabel l2 = new JLabel("닉네임 : " + ScoreItem.ITEM_SCORE_RECENT[1] + "\t");
        JLabel l3 = new JLabel("점수 : " + ScoreItem.ITEM_SCORE_RECENT[2]);

        l0.setForeground(new Color(106, 215, 255));
        l1.setForeground(new Color(106, 215, 255));
        l2.setForeground(new Color(106, 215, 255));
        l3.setForeground(new Color(106, 215, 255));

        System.out.println("Mode : " +ScoreItem.STD_SCORE_RECENT[0]);
        System.out.println("Name : " + ScoreItem.ITEM_SCORE_RECENT[1]);
        System.out.println("Score : " + ScoreItem.ITEM_SCORE_RECENT[2]);

        p2.add(l0);
        p2.add(l1);
        p2.add(l2);
        p2.add(l3);

        JTable table = new JTable(data, title);
        JTable table2 = new JTable(data2, title);
        JTable table3 = new JTable(data3, title);

        JScrollPane scrollview = new JScrollPane(table);
        JScrollPane scrollview2 = new JScrollPane(table2);
        JScrollPane scrollview3 = new JScrollPane(table3);

        if (Count == 0) {
            scrollview.setPreferredSize(new Dimension(350, 450));
            scrollview.setBackground(Color.white);

            add(p1, BorderLayout.NORTH);
            add(scrollview, BorderLayout.CENTER);

            scrollview.show();
            scrollview2.hide();
            scrollview3.hide();

            if (ScoreItem.CODE_RECENT == 2)
                add(p2, BorderLayout.SOUTH);

            setBackground(Color.white);
        }

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 1;
                Count++;

                table.hide();
                table2.show();
                table3.hide();

                scrollview.hide();
                scrollview2.show();
                scrollview3.hide();

                scrollview2.setPreferredSize(new Dimension(350, 450));
                scrollview2.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview2, BorderLayout.CENTER);
                if (ScoreItem.CODE_RECENT == 2)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            }
        });

        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 0;
                Count++;

                table.show();
                table2.hide();
                table3.hide();

                scrollview.show();
                scrollview2.hide();
                scrollview3.hide();

                scrollview.setPreferredSize(new Dimension(350, 450));
                scrollview.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview, BorderLayout.CENTER);
                if (ScoreItem.CODE_RECENT == 2)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
            }
        });

        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FocusMode = 2;
                Count++;

                table.hide();
                table2.hide();
                table3.show();

                scrollview.hide();
                scrollview2.hide();
                scrollview3.show();

                scrollview3.setPreferredSize(new Dimension(350, 450));
                scrollview3.setBackground(Color.white);

                add(p1, BorderLayout.NORTH);
                add(scrollview3, BorderLayout.CENTER);
                if (ScoreItem.CODE_RECENT == 2)
                    add(p2, BorderLayout.SOUTH);

                setBackground(Color.white);
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