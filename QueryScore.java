import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

public class QueryScore extends JFrame {
    String kc, bj, xq, sql;
    JPanel p = new JPanel();  //初始化各组件
    JLabel l1 = new JLabel("课程:");
    JComboBox course = new JComboBox();
    JLabel l2 = new JLabel("班级:");
    JComboBox tgrade = new JComboBox();
    JLabel l3 = new JLabel("学期:");
    JComboBox term = new JComboBox();
    JButton sure = new JButton("查询");
    JButton quxian = new JButton("查看成绩分布");
    ResultSet rs = null;
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);
    String tname, psd, sf;

    public QueryScore(String name, String psd, String sf) {
        try {
            tname = name;
            this.psd = psd;
            this.sf = sf;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setLayout(new BorderLayout()); //以下为添加各组件
        p.add(l1);
        p.add(course);
        p.add(l2);
        p.add(tgrade);
        p.add(l3);
        p.add(term);
        p.add(sure);
        p.add(quxian);
        add(p, "North");
        model.addColumn("学号");
        model.addColumn("姓名");
        model.addColumn("成绩");
        add(jScrollPane1, "Center");
        sure.addActionListener(new chaxun());  //给各按钮添加事件监视器
        quxian.addActionListener(new chaxun());
        quxian.setEnabled(false);
        course.addItem("请选择课程");
        course.addActionListener(new cquery());
        tgrade.addItem("请选择班级");
        term.addItem("请选择学期");
        if (sf.equals("教师")) {  //给课程添加项目
            sql = "select distinct T_C.cname  from T_C,teacher where teacher.name='" + tname + "' and teacher.ID=T_C.tID ";
        } else {
            sql = "select   distinct T_C.cname  from  T_C";
        }
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

    class cquery implements ActionListener {  //给班级和学期添加项目
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            if (e.getSource() == course) {
                kc = (String) course.getSelectedItem();
                if (sf.equals("教师")) {
                    sql = "select T_C.Cgrade from T_C,teacher where teacher.name='" + tname + "' and teacher.ID=T_C.tID and  T_C.cname='" + kc + "'";
                } else {
                    sql = "select  T_C.Cgrade  from T_C";
                }
                try {
                    tgrade.removeAllItems();
                    tgrade.addItem("请选择班级");
                    rs = db.Query(sql);
                    while (rs.next()) {
                        tgrade.addItem(rs.getString(1));
                    }
                    sql = "select distinct Term from T_C where cname='" + kc + "'";
                    term.removeAllItems();
                    term.addItem("请选择学期");
                    rs = db.Query(sql);
                    while (rs.next()) {
                        term.addItem(rs.getString(1));
                    }
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            }
        }
    }

    class chaxun implements ActionListener { //事件处理类
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            kc = (String) course.getSelectedItem();
            bj = (String) tgrade.getSelectedItem();
            xq = (String) term.getSelectedItem();
            if (e.getSource() == sure) { //对“查询”按钮进行处理
                sql = "select SID,Sname,score from  S_C  where Tgrade='" + bj + "'  and Cname='" + kc + "' and Term='" + xq + "'";
                try { //删除表格中原有的数据
                    int j = model.getRowCount();
                    if (j > 0) {
                        for (int i = 0; i < j; i++) {
                            model.removeRow(0);
                        }
                    }
                    rs = db.Query(sql); //从数据库中查询相应的数据
                    int i = 0;
                    while (rs.next()) {
                        tempvector = new Vector(1, 1);
                        tempvector.add(rs.getString(2));
                        tempvector.add(rs.getString(1));
                        tempvector.add(rs.getFloat(3));
                        model.addRow(tempvector);
                        i++;
                    }
                    if (i == 0) {
                        JOptionPane.showMessageDialog(null, "未录入该门课程成绩！！");
                    } else {
                        quxian.setEnabled(true);
                    }
                    dbtable.setEnabled(false); //表格中的数据不能修改
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            } else if (e.getSource() == quxian) { //对“查询成绩分布”进行处理
                String k = kc;
                String b = bj;
                JFrame f = new JFrame();
                Toolkit tk = Toolkit.getDefaultToolkit();
                Dimension sSize = tk.getScreenSize();
                int sh = sSize.height;
                int sw = sSize.width;
                f.setSize(sw / 2, sh / 2);
                f.setLocation(sw / 4, sh / 4);
                DrawTu d = new DrawTu(k, b);
                f.add(d);
                f.setVisible(true);
            }
        }
    }
}
