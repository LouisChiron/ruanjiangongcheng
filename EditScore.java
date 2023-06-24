import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class EditScore extends JFrame {
    JPanel p = new JPanel();
    JLabel l1 = new JLabel("课程:");
    JComboBox course = new JComboBox();
    JLabel l2 = new JLabel("班级:");
    JComboBox tgrade = new JComboBox();
    JLabel l3 = new JLabel("学期:");
    JComboBox term = new JComboBox();
    JButton sure = new JButton("查询");
    JButton xgai = new JButton("修改");
    JButton tijiao = new JButton("提交");
    ResultSet rs = null;
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);
    String kc, bj, xq, tname, psd, sql = "";

    public EditScore(String name, String psd) {
        try {
            tname = name;
            this.psd = psd;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception { //添加各组件
        p.add(l1);
        p.add(course);
        p.add(l2);
        p.add(tgrade);
        p.add(l3);
        p.add(term);
        p.add(sure);
        p.add(xgai);
        p.add(tijiao);
        add(p, "North");
        add(jScrollPane1, "Center");
        sure.addActionListener(new chaxun());  //给各按钮添加事件监视器
        xgai.addActionListener(new chaxun());
        tijiao.addActionListener(new chaxun());
        model.addColumn("学号");  //给表格添加各列
        model.addColumn("姓名");
        model.addColumn("成绩");
        course.addItem("请选择课程");
        tgrade.addItem("请选择班级");
        term.addItem("请选择学期");
        sql = "select cname,Cgrade from T_C,teacher  where T_C.tID=teacher.ID and teacher.name='" + tname + "' ";
        try {  //给“课程”、“班级”添加项目
            Dbconn db = new Dbconn();
            rs = db.Query(sql);
            while (rs.next()) {
                course.addItem(rs.getString(1));
                tgrade.addItem(rs.getString(2));
            }
            sql = "select distinct Term from T_C ,teacher where T_C.tID=teacher.ID and teacher.name='" + tname + "' ";
            rs = db.Query(sql);   //给学期添加项目
            while (rs.next())
                term.addItem(rs.getString(1));
        } catch (Exception er) {
            System.out.println(er.toString());
        }
    }

    class chaxun implements ActionListener {  //事件处理类
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            kc = (String) course.getSelectedItem();
            bj = (String) tgrade.getSelectedItem();
            xq = (String) term.getSelectedItem();
            if (e.getSource() == sure) { //处理“查询”事件
                int j = model.getRowCount();  //删除表格中原有的数据
                if (j > 0) {
                    for (int i = 0; i < j; i++) {
                        model.removeRow(0);
                    }
                }
                sql = "select SID,Sname,score from S_C where Tgrade='" + bj + "' and Cname='" + kc + "' and Term='" + xq + "'";
                try {
                    rs = db.Query(sql);
                    int i = 0;
                    while (rs.next()) {
                        i++;
                        tempvector = new Vector(1, 1);
                        tempvector.add(rs.getString(1));
                        tempvector.add(rs.getString(2));
                        tempvector.add(rs.getFloat(3));
                        model.addRow(tempvector);
                    }
                    if (i == 0) {
                        JOptionPane.showMessageDialog(null, "该门课程成绩未录入，请录入成绩！");
                    }
                    dbtable.setEnabled(false);
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            } else if (e.getSource() == xgai) { //处理“修改”事件
                dbtable.setEnabled(true);
            } else if (e.getSource() == tijiao) {//处理“提交”事件
                int n = model.getRowCount();
                PreparedStatement updatestmt;
                sql = "update S_C set score=? where SID=? and Term='" + xq + "'";
                try {
                    if (model.getValueAt(n - 1, 2) != null) {
                        updatestmt = db.conn.prepareStatement(sql);
                        for (int i = 0; i < n; i++) {
                            updatestmt.setString(2, (String) model.getValueAt(i, 0));
                            updatestmt.setFloat(1, Float.parseFloat((String) model.getValueAt(i, 2)));
                            updatestmt.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(null, "修改成功");
                    }
                } catch (Exception e3) {
                }
            }
        }
    }
}
