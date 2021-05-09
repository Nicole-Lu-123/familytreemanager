package ui;

import libs.Member;
import libs.Relation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RFrame {
    public Map<Integer, Member> family = new HashMap<>();
    JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
    Member mem = new Member();
    public void findrelations() {
        JFrame frame = new JFrame("Find relations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 20, 40);

        JPanel panel = new JPanel();
        frame.add(panel);
//        panel.setBackground(Color.green);
        JLabel lbl = new JLabel("Please insert 2 members' ids and names and click OK");
        lbl.setVisible(true);
        panel.add(lbl);
        panel.setLayout(fl);
        JLabel mem1 = new JLabel("          ");
        jtf1 = new JTextField(10);
        jtf1.setText("6");
        jtf2 = new JTextField(10);
        jtf2.setText("王小明");
        jtf3= new JTextField(10);
        jtf3.setText("2");
        jtf4 = new JTextField(10);
        jtf4.setText("王尼美");

        JButton btn = new JButton("OK");

        panel.add(mem1);
        panel.add(jtf1);
        panel.add(jtf2);
        panel.add(jtf3);
        panel.add(jtf4);
        panel.add(btn);
        btn.setVisible(true);
        frame.setVisible(true);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int mem1id = Integer.parseInt(jtf1.getText());
                String mem1name = jtf2.getText();
                int mem2id = Integer.parseInt(jtf3.getText());
                String mem2name = jtf4.getText();
                if (!mem.checkid(mem1id)){
                    frame.dispose();
                    showerr("成员1 id不存在");
                } else if (!mem.checkid(mem2id)) {
                    frame.dispose();
                    showerr("成员2 id不存在");
                } else if(mem.checkmem(mem1id,mem1name) && mem.checkmem(mem2id,mem2name)){
                    String result = mem1name + "叫" + mem2name + mem.findcall(mem1id,mem2id);
                    showresult(result);
                }else{
                    frame.dispose();
                    showerr("id与姓名不符");
                }
                frame.dispose();
            }
        });
    }
    public void addmem() {
        JFrame frame = new JFrame("Add a member");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 20, 40);

        JPanel panel = new JPanel();
        frame.add(panel);
//        panel.setBackground(Color.green);
        JLabel lbl = new JLabel("Please insert the member's info and click OK");
        lbl.setVisible(true);
        panel.add(lbl);
        panel.setLayout(fl);
        jtf1 = new JTextField(10);
        jtf1.setText("成员ID");
        jtf2 = new JTextField(10);
        jtf2.setText("成员姓名");
        jtf3= new JTextField(5);
        jtf3.setText("成员性别");
        jtf4 = new JTextField(8);
        jtf4.setText("成员生日");
        jtf5 = new JTextField(10);
        jtf5.setText("父亲ID");
        jtf6 = new JTextField(10);
        jtf6.setText("母亲ID");

        JButton btn = new JButton("OK");

        panel.add(jtf1);
        panel.add(jtf2);
        panel.add(jtf3);
        panel.add(jtf4);
        panel.add(jtf5);
        panel.add(jtf6);
        panel.add(btn);
        btn.setVisible(true);
        frame.setVisible(true);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                int memid = Integer.parseInt(jtf1.getText());
                String memname = jtf2.getText();
                String memgender = jtf3.getText();
                Date membirth = null;
                try {
                    membirth = format.parse(jtf4.getText());
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                Integer memfather =  transint(jtf5.getText());
                Integer memmother =  transint(jtf6.getText());
                if(!mem.checkid(memid)){// true if the id already exists
                    mem.addmember(memid,memname,memgender,membirth,memfather,memmother);
                    showresult("Successful");
                }
                else{
                    frame.dispose();
                    showerr("列表中已有此人");
                }
                frame.dispose();
            }
        });

    }

    private Integer transint(String text) {
        if (text.equals("null")){
            return 0;
        }else{
            return Integer.parseInt(text);
        }
    }

    public void updatemem() {
        JFrame frame = new JFrame("Update member");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 20, 40);

        JPanel panel = new JPanel();
        frame.add(panel);
//        panel.setBackground(Color.green);
        JLabel lbl = new JLabel("Please insert member's info that you want to update and click OK");
        lbl.setVisible(true);
        panel.add(lbl);
        panel.setLayout(fl);
        jtf1 = new JTextField(10);
        jtf1.setText("成员ID");
        jtf2 = new JTextField(10);
        jtf2.setText("成员姓名");
        jtf3= new JTextField(5);
        jtf3.setText("成员性别");
        jtf4 = new JTextField(8);
        jtf4.setText("成员生日");
        jtf5 = new JTextField(10);
        jtf5.setText("父亲ID");
        jtf6 = new JTextField(10);
        jtf6.setText("母亲ID");

        JButton btn = new JButton("OK");

        panel.add(jtf1);
        panel.add(jtf2);
        panel.add(jtf3);
        panel.add(jtf4);
        panel.add(jtf5);
        panel.add(jtf6);
        panel.add(btn);
        btn.setVisible(true);
        frame.setVisible(true);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                int memid = Integer.parseInt(jtf1.getText());
                String memname = jtf2.getText();
                String memgender = jtf3.getText();
                Date membirth = null;
                try {
                    membirth = format.parse(jtf4.getText());
                } catch (ParseException parseException) {
                    showerr("日期输入错误");
                }
                Integer memfather =  transint(jtf5.getText());
                Integer memmother =  transint(jtf6.getText());
                if(mem.checkid(memid)){// true if the id already exists
                    mem.updateMem(memid,memname,memgender,membirth,memfather,memmother);
                    showresult("Successful");
                }
                else{
                    frame.dispose();
                    showerr("列表中没有此人");
                }
                frame.dispose();
            }
        });

    }

    public void deletmem() {
        JFrame frame = new JFrame("Delet the member from tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 20, 40);

        JPanel panel = new JPanel();
        frame.add(panel);
//        panel.setBackground(Color.green);
        JLabel lbl = new JLabel("Please member's id and click OK");
        lbl.setVisible(true);
        panel.add(lbl);
        panel.setLayout(fl);
        JLabel mem1 = new JLabel("Member ID");
        jtf1 = new JTextField(10);
        jtf1.setText("ID");

        JButton btn = new JButton("OK");

        panel.add(mem1);
        panel.add(jtf1);
        panel.add(btn);
        btn.setVisible(true);
        frame.setVisible(true);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int memid = Integer.parseInt(jtf1.getText());
                if(mem.checkid(memid)){// true if the id already exists
                    mem.deleteMem(memid);
                    showresult("Successful");
                }
                else{
                    frame.dispose();
                    showerr("列表中没有此人");
                }
                frame.dispose();
            }
        });
    }
    private void showresult(String result) {
        JFrame frame = new JFrame("Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 20, 40);

        JPanel panel = new JPanel();
        frame.add(panel);
        JLabel r = new JLabel(result);
        JButton btn = new JButton("OK");
        panel.add(r);
        panel.add(btn);
        btn.setVisible(true);
        frame.setVisible(true);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
            }
        });

    }

    public void showerr(String info){
        JFrame frame = new JFrame("Error");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 20, 40);

        JPanel panel = new JPanel();
        frame.add(panel);
        JLabel r = new JLabel("重新填写,请检查输入信息:"+info);
        JButton btn = new JButton("OK");
        panel.add(r);
        panel.add(btn);
        btn.setVisible(true);
        frame.setVisible(true);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
            }
        });

    }


}
