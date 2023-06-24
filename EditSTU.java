import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Vector;

public class EditSTU extends JPanel {
    public EditSTU() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 20));
        bj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sgrade.removeAllItems();
                if (bj.isSelected())
                    try {
                        Dbconn db = new Dbconn();
                        ResultSet rs = db.Query("select distinct grade from student");
                        sgrade.addItem("   ");
                        while (rs.next())
                            sgrade.addItem(rs.getString(1));
                    } catch (Exception er) {
                        System.out.println(er.toString());
                    }
            }
        });
        jScrollPane1.setBounds(new Rectangle(65, 154, 370, 161));  //设置各组件的位置
        jLabel1.setBounds(new Rectangle(111, 13, 250, 36));
        bj.setBounds(new Rectangle(64, 74, 65, 27));
        xm.setBounds(new Rectangle(259, 74, 63, 27));
        sgrade.setBounds(new Rectangle(132, 74, 116, 23));
        sname.setBounds(new Rectangle(322, 74, 113, 23));
        del.setBounds(new Rectangle(347, 112, 87, 27));
        edit.setBounds(new Rectangle(216, 112, 87, 27));
        query.setBounds(new Rectangle(68, 112, 87, 27));
        model.addColumn("学号");
        model.addColumn("姓名");
        model.addColumn("性别");
        model.addColumn("出生年月");
        model.addColumn("所在班级");
        dbtable.addMouseListener(new MouseAdapter() {  //给表格添加监视器
            public void mouseClicked(MouseEvent e) {
                int j = dbtable.getSelectedRow();
                xh1 = (String) model.getValueAt(j, 0);
                xm1 = (String) model.getValueAt(j, 1);
                xb1 = (String) model.getValueAt(j, 2);
                sr1 = (java.sql.Date) model.getValueAt(j, 3);
                bj1 = (String) model.getValueAt(j, 4);
                del.setEnabled(true);
                edit.setEnabled(true);
            }
        });
        this.add(jLabel1);  //添加各组件
        this.add(bj);
        this.add(sgrade);
        this.add(xm);
        this.add(sname);
        this.add(query);
        this.add(edit);
        this.add(del);
        this.add(jScrollPane1);
        query.addActionListener(new Handel());  //给各按钮添加事件监视器
        edit.addActionListener(new Handel());
        del.addActionListener(new Handel());
        del.setEnabled(false);
        edit.setEnabled(false);
    }

    class Handel implements ActionListener {  //事件处理类
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            if (e.getSource() == edit) {  //对“修改”事件进行处理
                Toolkit tk = Toolkit.getDefaultToolkit();
                Dimension sSize = tk.getScreenSize();
                int sh = sSize.height;
                int sw = sSize.width;
                StudentQuery sq = new StudentQuery(xm1, xh1, xb1, sr1, bj1, "teacher");
                sq.setTitle("修改学生信息");
                sq.setSize(sh / 2 + 100, sh / 2);
                sq.setLocation(sh / 4, sh / 4);
                sq.setVisible(true);
            }
            if (e.getSource() == del) { //对“删除”事件进行处理
                int y = JOptionPane.showConfirmDialog(null, "你确定要删除吗？", "删除学生对话框", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                String sql = "delete * from student where ID='" + xh1 + "' and name='" + xm1 + "' and sex='" + xb1 + "' and  birthday=#" + sr1 + "#  and grade='" + bj1 + "'";
                if (y == 0) {
                    int i = db.Update(sql);  //执行删除SQL语句
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "删除成功！！");
                    } else {
                        JOptionPane.showMessageDialog(null, "删除失败！！");
                    }
                }
            } else {  //处理“查询”事件
                String sbj, sxm;
                String sql = "select * from student";
                if (xm.isSelected() && bj.isSelected()) {  //构建相应的SQL语句
                    sxm = sname.getText();
                    sql = sql + " where grade='" + (String) sgrade.getSelectedItem() + "' and name='" + sxm + "'";
                } else if (bj.isSelected()) {
                    sbj = (String) sgrade.getSelectedItem();
                    sql = sql + " where grade='" + sbj + "'";
                } else if (xm.isSelected()) {
                    sql = sql + " where name='" + (String) sname.getText() + "'";
                }
                int j = model.getRowCount();  //首先删除表格原有数据
                if (j > 0) {
                    for (int i = 0; i < j; i++) {
                        model.removeRow(0);
                    }
                }
                dbtable.setVisible(true);
                try {
                    ResultSet rs = db.Query(sql);  //执行查询SQL语句
                    while (rs.next()) {
                        tempvector = new Vector(1, 1);
                        tempvector.add(rs.getString(1));
                        tempvector.add(rs.getString(2));
                        tempvector.add(rs.getString(3));
                        tempvector.add(rs.getDate(4));
                        tempvector.add(rs.getString(5));
                        model.addRow(tempvector);  //将查询的结果添加到表格中
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }
    }

    JLabel jLabel1 = new JLabel("学 生 基 本 信 息 管 理");//初始化各组件
    JComboBox sgrade = new JComboBox();
    JCheckBox bj = new JCheckBox("班级：");
    JCheckBox xm = new JCheckBox("姓名：");
    JButton query = new JButton("查询");
    JButton del = new JButton("删除");
    JButton edit = new JButton("修改");
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);
    JTextField sname = new JTextField();
    String xh1, xm1, xb1, bj1;
    java.sql.Date sr1;
}
