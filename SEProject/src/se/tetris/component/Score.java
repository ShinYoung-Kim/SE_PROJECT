package se.tetris.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import se.tetris.component.*;
import se.tetris.data.DBCalls;
import se.tetris.setting.SettingCode;
import se.tetris.setting.SettingValues;

public class Score extends tabViewBox {

    DBCalls dataCalls = new DBCalls();

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

class SButton extends JButton {

    Color Impact = new Color(106, 215, 255);
    Color Normal = new Color(0, 0, 0);

    public SButton() {
        super();
        Default();
    }

    @ConstructorProperties({ "text" })
    public SButton(String text) {
        super(text, null);
        Default();
    }

    public void Active() {
        setBackground(Impact);
        setBorder(new LineBorder(Impact));
        setForeground(Color.BLACK);
    }

    public void Default() {
        setBackground(Color.BLACK);
        setBorder(new LineBorder(Impact));

        setForeground(Impact);

        setPreferredSize(new Dimension(100, 35));
        setFont(new Font("고딕", Font.CENTER_BASELINE, 12));
    }
}



class tabViewBox extends JFrame implements Sizeable {

    public tabViewBox() {
        setTitle("SeoulTech SE Tettris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);

        setBackground(Color.white);

        display();
    }

    DBCalls dataCalls = new DBCalls();

    int Window = dataCalls.getWindowSetting();
    SettingCode setting = new SettingCode();

    public void startMode() {
        int sizeNumber = SettingValues.getInstance().sizeNumber;
        Start start = new Start();
        start.changeSize(sizeNumber);
        start.setVisible(true);

        setVisible(false);
    }

    public void display() {
        JPanel AllPane = new JPanel();

        AllPane.setLayout(new BorderLayout());


        JPanel btnPane = new JPanel();

        SButton startBtn = new SButton("시작화면");
        SButton endBtn = new SButton("게임종료");

        btnPane.setBackground(Color.BLACK);
        btnPane.add(startBtn);
        btnPane.add(endBtn);

        AllPane.add(btnPane, BorderLayout.SOUTH);


        JTabbedPane tabView = new JTabbedPane();

        tabView.setBackground(Color.white);

        stdTable stdScoreView = new stdTable();
        itemTable itemScoreView = new itemTable();
        tabView.addTab("기본 모드 랭킹", stdScoreView);
        tabView.addTab("아이템 모드 랭킹", itemScoreView);

        AllPane.add(tabView, BorderLayout.CENTER);

        add(AllPane);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMode();
            }
        });

        endBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void changeSize(int sizeNumber) {
        if (sizeNumber == 1) {
            setSize(400, 600);
        } else if (sizeNumber == 2) {
            setSize(800, 800);
        } else {
            setSize(SettingCode.screenWidth, SettingCode.screenHeight);
        }
    }
}

class stdTable extends JPanel {
    DBCalls dataCalls = new DBCalls();

    int Count = 0;
    int FocusMode = 0;

    String[] title = { "랭킹", "모드", "닉네임", "점수" };

    SButton b1;
    SButton b2;
    SButton b3;

    public void allBtnDefault() {
        b1.Default();
        b2.Default();
        b3.Default();
    }

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


        setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLACK);

        b1 = new SButton("Easy");
        b2 = new SButton("NormL");
        b3 = new SButton("Hard");

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
            //scrollview.setPreferredSize(new Dimension(350, 450));
            scrollview.setBackground(Color.white);

            add(p1, BorderLayout.NORTH);
            add(scrollview2, BorderLayout.CENTER);

            allBtnDefault();
            b1.Active();

            table.hide();
            table2.show();
            table3.hide();

            scrollview.hide();
            scrollview2.show();
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

                allBtnDefault();
                b1.Active();

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

                allBtnDefault();
                b2.Active();

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

                allBtnDefault();
                b3.Active();

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

    SButton b1;
    SButton b2;
    SButton b3;

    public void allBtnDefault() {
        b1.Default();
        b2.Default();
        b3.Default();
    }

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

        setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLACK);

        b1 = new SButton("Easy");
        b2 = new SButton("NormL");
        b3 = new SButton("Hard");

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

//		System.out.println("Mode : " +ScoreItem.STD_SCORE_RECENT[0]);
//		System.out.println("Name : " + ScoreItem.ITEM_SCORE_RECENT[1]);
//		System.out.println("Score : " + ScoreItem.ITEM_SCORE_RECENT[2]);

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
            //scrollview.setPreferredSize(new Dimension(350, 450));
            scrollview.setBackground(Color.white);

            add(p1, BorderLayout.NORTH);
            add(scrollview2, BorderLayout.CENTER);

            allBtnDefault();
            b1.Active();

            table.hide();
            table2.show();
            table3.hide();

            scrollview.hide();
            scrollview2.show();
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

                allBtnDefault();
                b1.Active();

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

                allBtnDefault();
                b2.Active();

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

                allBtnDefault();
                b3.Active();

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
