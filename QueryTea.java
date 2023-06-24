import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Vector;

public class QueryTea extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel l1 = new JLabel("值:");
    JLabel jLabel1 = new JLabel("查询条件：");
    JComboBox tiaojian = new JComboBox();
    JTextField tname = new JTextField(10);
    JButton query = new JButton("查询");
    JButton scan = new JButton("浏览");
    JButton del = new JButton("删除");
    JButton edit = new JButton("修改");
    JTextField show = new JTextField(10);
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();//定义表格，用于数据显示
    JTable dbtable = new JTable(model);
    JScrollPane sp = new JScrollPane(dbtable);
    ResultSet rs = null;
    String bh = "", zc = "", xb = "", xm = "";

    public QueryTea() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {//添加各个组件
        add(jLabel1);
        add(tiaojian);
        tiaojian.addItem("");
        tiaojian.addItem("编号");
        tiaojian.addItem("姓名");
        tiaojian.addItem("性别");
        tiaojian.addItem("职称");
        add(l1);
        add(tname);
        add(query);
        add(scan);
        add(edit);
        add(del);
        add(sp, "Center");
        add(show, "North");
        query.addActionListener(new EventHadel());//给各按钮添加监视器
        scan.addActionListener(new EventHadel());
        edit.addActionListener(new EventHadel());
        del.addActionListener(new EventHadel());
        edit.setEnabled(false);//设置“删除”按钮为不可编辑状态
        del.setEnabled(false);
        ;//设置“修改”按钮为不可编辑状态
        model.addColumn("编号");//给表格添加各列
        model.addColumn("姓名");
        model.addColumn("性别");
        model.addColumn("职称");
        dbtable.addMouseListener(new MouseAdapter() { //监视表格
            @Override
            public void mouseClicked(MouseEvent e) { //给表格添加监视器，当鼠标选中某条记录时，进行修改和删除
                int j = dbtable.getSelectedRow();
                bh = (String) model.getValueAt(j, 0);
                xm = (String) model.getValueAt(j, 1);
                xb = (String) model.getValueAt(j, 2);
                zc = (String) model.getValueAt(j, 3);
                edit.setEnabled(true);//“修改”按钮设置为可编辑状态
                del.setEnabled(true); //“删除”按钮设置为可编辑状态
            }
        });
    }

    class EventHadel implements ActionListener { // 设置监视类
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            String sql = "";
            if (e.getSource() == scan || e.getSource() == query) { //处理“浏览”、“查询”事件
                dbtable.setVisible(true); //首先设置表格可见
                int j = model.getRowCount(); //获取表格中原有的数据
                if (j > 0) { //如果有记录的话，删除所有的记录
                    for (int i = 0; i < j; i++) {
                        model.removeRow(0);
                    }
                }
                if (e.getSource() == query) {  //处理“查询”事件，首先确定查询SQL语句
                    String tj = (String) tiaojian.getSelectedItem();
                    String mingcheng = "";
                    if (tj.equals("编号")) {
                        mingcheng = "ID";
                    } else if (tj.equals("姓名")) {
                        mingcheng = "name";
                    } else if (tj.equals("性别")) {
                        mingcheng = "sex";
                    } else {
                        mingcheng = "title";
                    }
                    sql = "select * from teacher where " + mingcheng + "='" + tname.getText() + "'";
                } else if (e.getSource() == scan) { //“浏览”中的SQL语句
                    sql = "select * from teacher ";
                }
                try {
                    rs = db.Query(sql); //执行查询操作
                    int i = 0;
                    while (rs.next()) {
                        i++;
                        String tbh = rs.getString(1);
                        if (!tbh.startsWith("admin")) {
                            tempvector = new Vector(1, 1);
                            tempvector.add(tbh);
                            tempvector.add(rs.getString(2));
                            tempvector.add(rs.getString(4));
                            tempvector.add(rs.getString(5));
                            model.addRow(tempvector);//将查询的数据添加到表格中
                        }
                    }
                    if (i == 0) {
                        JOptionPane.showMessageDialog(null, "数据库中尚未录入教师！！");
                    }
                    dbtable.setRowSelectionAllowed(true);
                    int sr = dbtable.getSelectedRow();
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            } else if (e.getSource() == edit) { //处理“修改”事件
                Toolkit tk = Toolkit.getDefaultToolkit();
                Dimension sSize = tk.getScreenSize();
                int sh = sSize.height;
                int sw = sSize.width;
                EditTea er = new EditTea(bh, xm, xb, zc);
                JFrame editframe = new JFrame();
                editframe.add(er);
                editframe.setSize(sh / 2, sh / 2);  //确定窗口的尺寸大小
                editframe.setLocation(sh / 4, sh / 4);
                editframe.setVisible(true);
            } else if (e.getSource() == del) {  //处理“删除”事件
                try {
                    dbtable.setVisible(false);
                    int e1 = JOptionPane.showConfirmDialog(null, "确定要删除吗？", "确认", JOptionPane.OK_CANCEL_OPTION);
                    if (e1 == 0) {
                        String sql2 = "", sql3 = "";
                        if (!tname.getText().equals("")) {
                            sql = "select ID from teacher where name='" + tname.getText() + "'";
                            ResultSet rs = db.Query(sql);
                            if (rs.next()) {
                                bh = rs.getString(1);
                            }
                        }
                        sql = "delete * from teacher where ID='" + bh + "'";  //从教师表中删除
                        sql2 = "select * from T_C where tID='" + bh + "'";
                        sql3 = "delete * from T_C where  tID='" + bh + "'";//从任课表中删除
                        int i = db.Update(sql); //执行删除操作
                        if (i > 0) {
                            ResultSet rs = db.Query(sql2);
                            boolean have = false;
                            if (rs.next()) {
                                have = true;
                            }
                            if (have == true) {
                                int j = db.Update(sql3);
                                if (j > 0) {
                                    JOptionPane.showMessageDialog(null, "删除成功！！");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "删除成功！！");
                            }

                        }
                    }
                } catch (Exception er) {
                    System.out.println(e.toString());
                }
            }
        }
    }
}
