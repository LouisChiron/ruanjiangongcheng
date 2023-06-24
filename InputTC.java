import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Vector;

public class InputTC extends JFrame {
    public InputTC() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setTitle("任课登录");
        this.getContentPane().setLayout(null);
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 24));
        jLabel1.setBounds(new Rectangle(56, 9, 359, 42));//设置各组件的位置
        jLabel2.setBounds(new Rectangle(56, 69, 85, 32));
        tea.setBounds(new Rectangle(131, 69, 147, 31));
        tea.addItem("请选择教师");
        jLabel3.setBounds(new Rectangle(56, 120, 84, 38));
        course.setBounds(new Rectangle(131, 123, 147, 29));
        course.addItem("请选择课程");
        jLabel4.setBounds(new Rectangle(56, 170, 82, 40));
        grade.setBounds(new Rectangle(132, 173, 146, 29));
        jLabel5.setBounds(new Rectangle(56, 219, 94, 38));
        term.setBounds(new Rectangle(132, 222, 146, 29));
        sure.setBounds(new Rectangle(303, 69, 75, 31));
        edit.setBounds(new Rectangle(303, 123, 75, 29));
        browse.setBounds(new Rectangle(303, 173, 75, 29));
        del.setBounds(new Rectangle(303, 222, 75, 29));
        jScrollPane1.setBounds(new Rectangle(29, 268, 373, 144));
        this.getContentPane().add(jLabel1);  //添加各组件
        this.getContentPane().add(jLabel2);
        this.getContentPane().add(tea);
        this.getContentPane().add(sure);
        this.getContentPane().add(jLabel3);
        this.getContentPane().add(course);
        this.getContentPane().add(edit);
        this.getContentPane().add(jLabel4);
        this.getContentPane().add(grade);
        this.getContentPane().add(browse);
        this.getContentPane().add(jLabel5);
        this.getContentPane().add(term);
        this.getContentPane().add(del);
        this.getContentPane().add(jScrollPane1);
        model.addColumn("课程名称");  //为表格添加各列
        model.addColumn("任课教师");
        model.addColumn("任课班级");
        model.addColumn("任课学期");
        sure.addActionListener(new EventHandel());  //为各按钮添加监视器
        edit.addActionListener(new EventHandel());
        browse.addActionListener(new EventHandel());
        del.addActionListener(new EventHandel());
        del.setEnabled(false);  //“删除”与“修改”按钮设置为不可编辑
        edit.setEnabled(false);
        dbtable.addMouseListener(new MouseAdapter() {  //为表格添加监视器
            @Override
            public void mouseClicked(MouseEvent e) {
                int j = dbtable.getSelectedRow();
                a1 = (String) model.getValueAt(j, 0);
                a2 = (String) model.getValueAt(j, 1);
                a3 = (String) model.getValueAt(j, 2);
                a4 = (String) model.getValueAt(j, 3);
                tea.setSelectedItem(a1);
                course.setSelectedItem(a2);
                grade.setText(a3);
                term.setText(a4);
            }
        });
        Dbconn db = new Dbconn();
        rs = db.Query("select cname from course");
        while (rs.next()) {
            course.addItem(rs.getString(1));  //添加课程项
        }
        rs = db.Query("select name from teacher");  //添加教师项
        while (rs.next()) {
            tea.addItem(rs.getString(1));
        }
        db.close();
    }

    JLabel jLabel1 = new JLabel("教   师   任   课   登   记");
    JLabel jLabel2 = new JLabel("教师姓名：");
    JComboBox tea = new JComboBox();
    JLabel jLabel3 = new JLabel("课程名称：");
    JComboBox course = new JComboBox();
    JLabel jLabel4 = new JLabel("任课班级：");
    JTextField grade = new JTextField();
    JButton sure = new JButton("添加");
    JButton edit = new JButton("修改");
    JButton browse = new JButton("浏览");
    JButton del = new JButton("删除");
    ResultSet rs = null;
    JLabel jLabel5 = new JLabel("任课学期：");
    JTextField term = new JTextField();
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);
    String sql = "", a1, a2, a3, a4;

    class EventHandel implements ActionListener {  //事件处理类
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            if (e.getSource() == browse) {//处理“查询”事件
                sql = "select teacher.name,T_C.cname,T_C.Cgrade,T_C.Term from teacher,T_C where T_C.tID=teacher.ID";
                rs = db.Query(sql);
                int j = model.getRowCount();//删除原dbtable中的所有数据
                if (j > 0) {
                    for (int i = 0; i < j; i++) {
                        model.removeRow(0);
                    }
                }
                try {
                    while (rs.next()) {
                        tempvector = new Vector(1, 1);
                        tempvector.add(rs.getString(1));
                        tempvector.add(rs.getString(2));
                        tempvector.add(rs.getString(3));
                        tempvector.add(rs.getString(4));
                        model.addRow(tempvector);
                    }
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
                del.setEnabled(true);
                edit.setEnabled(true);
            } else {
                String js = (String) tea.getSelectedItem();
                String bj = grade.getText();
                String kc = (String) course.getSelectedItem();
                String xq = term.getText();
                String tid = "";
                try {
                    sql = "select ID  from teacher where name='" + js + "'";
                    rs = db.Query(sql);
                    if (rs.next()) {
                        tid = rs.getString(1);
                        if (e.getSource() == edit) {  //建立修改SQL语句
                            sql = "update T_C set cname='" + kc + "',tID='" + tid + "',Cgrade='" + bj + "',Term='" + xq + "' where cname='" + a2 + "' and tID='" + tid + "' and Cgrade='" + a3 + "' and Term='" + a4 + "'";
                        } else if (e.getSource() == del) {//建立删除SQL语句
                            sql = "delete * from T_C where cname='" + a2 + "' and tID='" + tid + "' and Cgrade='" + a3 + "' and Term='" + a4 + "'";
                        } else {  //建立插入SQL语句
                            sql = "insert into T_C values('" + kc + "','" + tid + "','" + bj + "','" + xq + "') ";
                        }
                        int i = db.Update(sql);  //执行相应SQL语句
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "操作成功!");
                        } else {
                            JOptionPane.showMessageDialog(null, "操作失败！");
                        }
                    }
                } catch (Exception er) {
                    System.out.println(er.toString());
                }
            }
        }
    }
}
