import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class InputScore extends JFrame {
    String kc, bj;
    JPanel p = new JPanel();
    JLabel l1 = new JLabel("课程:");
    JComboBox course = new JComboBox();
    JLabel l2 = new JLabel("班级:");
    JComboBox tgrade = new JComboBox();
    JLabel l3 = new JLabel("学期:");
    JComboBox term = new JComboBox();
    JButton sure = new JButton("查询");
    JButton tijiao = new JButton("提交");
    JButton quit = new JButton("退出");
    String tname = "", psd, sql = "";
    ResultSet rs = null;
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);

    public InputScore(String uname, String psd) {
        try {
            tname = uname;
            this.psd = psd;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(new BorderLayout());
        p.add(l1);
        p.add(course);
        p.add(l2);
        p.add(tgrade);
        p.add(l3);
        p.add(term);
        p.add(sure);
        p.add(tijiao);
        p.add(quit);
        sure.addActionListener(new chaxun());
        tijiao.addActionListener(new chaxun());
        quit.addActionListener(new chaxun());
        getContentPane().add(p, "North");
        model.addColumn("学号");
        model.addColumn("姓名");
        model.addColumn("成绩");
        getContentPane().add(jScrollPane1, "Center");
        course.addItem("请选择课程");
        course.addActionListener(new cquery());
        tgrade.addItem("请选择班级");
        term.addItem("请选择学期");
        sql = "select distinct T_C.cname  from T_C,teacher where teacher.name='" + tname + "' and teacher.ID=T_C.tID ";
        try {
            Dbconn db = new Dbconn();
            rs = db.Query(sql);
            while (rs.next()) {
                course.addItem(rs.getString(1));
            }
        } catch (Exception er) {
            System.out.println(er.toString());
        }
    }

    class cquery implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            if (e.getSource() == course) {
                kc = (String) course.getSelectedItem();
                sql = "select  T_C.Cgrade,T_C.Term  from T_C,teacher where teacher.name='" + tname + "' and teacher.ID=T_C.tID and  T_C.cname='" + kc + "'";
                try {
                    tgrade.removeAllItems();
                    tgrade.addItem("请选择班级");
                    rs = db.Query(sql);
                    while (rs.next()) {
                        tgrade.addItem(rs.getString(1));
                        term.addItem(rs.getString(2));
                    }
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            }
        }
    }

    class chaxun implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            kc = (String) course.getSelectedItem();
            bj = (String) tgrade.getSelectedItem();
            sql = "select * from S_C where Cname='" + kc + "' and Tgrade='" + bj + "'";
            boolean luru = false;
            try {
                ResultSet rs = db.Query(sql);
                if (rs.next()) {
                    luru = true;
                }
            } catch (Exception e1) {
                System.out.println(e1.toString());
            }
            if (e.getSource() == sure) {
                if (luru == true) {
                    JOptionPane.showMessageDialog(null, "成绩已经录入，不能再重新录入");
                } else {
                    sql = "select ID,name from student where grade='" + bj + "'";
                    try {   //删除表格中原有的数据
                        int j = model.getRowCount();
                        if (j > 0) {
                            for (int i = 0; i < j; i++)
                                model.removeRow(0);
                        }
                        rs = db.Query(sql);
                        while (rs.next()) {
                            tempvector = new Vector(1, 1);
                            tempvector.add(rs.getString(1));
                            tempvector.add(rs.getString(2));
                            model.addRow(tempvector);
                        }
                    } catch (Exception e2) {
                        System.out.println(e2.toString());
                    }
                }
            } else if (e.getSource() == tijiao) {
                int n = model.getRowCount();
                PreparedStatement updatestmt;
                String xq = (String) term.getSelectedItem();
                if (luru == false) {
                    sql = "insert into  S_C values( ?,?,'" + bj + "','" + xq + "','" + kc + "',?)";
                    try {
                        if (model.getValueAt(n - 1, 2) != null) {
                            updatestmt = db.conn.prepareStatement(sql);
                            for (int i = 0; i < n; i++) {
                                updatestmt.clearParameters();
                                updatestmt.setString(1, (String) model.getValueAt(i, 0));
                                updatestmt.setString(2, (String) model.getValueAt(i, 1));
                                updatestmt.setFloat(3, Float.parseFloat((String) model.getValueAt(i, 2)));
                                updatestmt.executeUpdate();
                            }
                            JOptionPane.showMessageDialog(null, "添加成功");
                        } else {
                            JOptionPane.showMessageDialog(null, "未录入完数据");
                        }
                        db.close();
                    } catch (Exception e3) {
                        System.out.println(e3.toString());
                    }
                }
            } else if (e.getSource() == quit) {
                setVisible(false);
            }
        }
    }
}
