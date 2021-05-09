package ui;

import libs.Member;
import libs.Relation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
public class Main {
    public static RFrame rf= new RFrame();

        private static final String[] choices = {"Find relations", "Add A new member", "Update the member","Delet the member"};
        public static void main(String[] args) {
                Member mem = new Member();
                mem.addexamples();
                JFrame frame = new JFrame("Options");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 150);
                frame.setLocationRelativeTo(null);

                JPanel panel = new JPanel();

                frame.add(panel);

                JLabel lbl = new JLabel("Select one of the options and click OK");
                JLabel lbl1 = new JLabel("Please click OK if you finished, if you want to leave, click exit");
                lbl.setVisible(true);

                panel.add(lbl);
                panel.add(lbl1);
                final JComboBox<String> cb = new JComboBox<String>(choices);

                panel.add(cb);
                JButton btn = new JButton("OK");
                panel.add(btn);
                btn.setVisible(true);
                frame.setVisible(true);
                cb.setVisible(true);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        String option = Objects.requireNonNull(cb.getSelectedItem()).toString();
                        switch (option) {
                            case "Quit" -> frame.dispose();
                            case "Find relations" -> {
                               rf.findrelations();
                            }
                            case "Add A new member" -> {
                                rf.addmem();
                            }
                            case "Update the member" -> {
                                rf.updatemem();
                            }
                            case "Delet the member" -> {
                                rf.deletmem();
                            }
                            default -> frame.dispose();
                        }
                    }
                });
            }


}
