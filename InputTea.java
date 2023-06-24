import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class InputTea extends JPanel {
    public InputTea() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception { // 设置布局为空，需要设置各组件的位置
        setLayout(null);
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 19));
        jLabel1.setBounds(new Rectangle(139, 39, 231, 33));
        jLabel2.setBounds(new Rectangle(144, 125, 71, 29));
        tname.setBounds(new Rectangle(233, 128, 114, 25));
        jLabel3.setBounds(new Rectangle(140, 157, 65, 27));
        bg.add(sex1);
        bg.add(sex2);
        sex1.setBounds(new Rectangle(230, 161, 39, 23));
        sex2.setBounds(new Rectangle(315, 162, 42, 23));
        jLabel4.setBounds(new Rectangle(140, 184, 55, 34));
        post.setBounds(new Rectangle(234, 191, 113, 26));
        sure.setBounds(new Rectangle(140, 239, 82, 25));
        con.setBounds(new Rectangle(266, 239, 81, 25));
        jLabel5.setBounds(new Rectangle(140, 83, 66, 30));
        tID.setBounds(new Rectangle(233, 90, 114, 26));
        add(jLabel1); // 添加各个组件
        add(jLabel5);
        add(tID);
        add(jLabel2);
        this.add(tname);
        add(jLabel3);
        add(sex1);
        this.add(sex2);
        this.add(jLabel4);
        add(post);
        add(sure);
        add(con);
        post.addItem("讲师");// 给组合框“职称”添加项目
        post.addItem("助教");
        post.addItem("副教授");
        post.addItem("教授");
        sure.addActionListener(new Handel()); // 给按钮添加事件监视器
        con.addActionListener(new Handel());
    }

    JLabel jLabel1 = new JLabel("教  师  信  息  录  入");
    JLabel jLabel2 = new JLabel("姓名：");
    JTextField tname = new JTextField();
    JLabel jLabel3 = new JLabel("性别：");
    ButtonGroup bg = new ButtonGroup();
    JRadioButton sex1 = new JRadioButton("男");
    JRadioButton sex2 = new JRadioButton("女");
    JLabel jLabel4 = new JLabel("职称：");
    JComboBox post = new JComboBox();
    JButton sure = new JButton("添加");
    JButton con = new JButton("继续");
    JLabel jLabel5 = new JLabel("教师编号：");
    JTextField tID = new JTextField();

    class Handel implements ActionListener { // 事件处理类
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            if (e.getSource() == sure) {// 添加教师
                String id = tID.getText();
                String xx = tname.getText();
                String xb = "女", sql;
                if (sex1.isSelected()) {
                    xb = "男";
                }
                String title = (String) post.getSelectedItem();
                sql = "select * from teacher where ID='" + id + "'";
                try {// 首先从数据库中查询该教师信息，找到则不再添加，否则进行添加。
                    ResultSet rs = db.Query(sql);
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "该老师编号已存在！！");
                    } else {// 普通教师的密码为“123456”
                        sql = "insert into teacher(ID,name,psd,sex,title) values('" + id + "','" + xx + "','123456','"
                                + xb + "','" + title + "')";
                        int n = db.Update(sql);
                        if (n > 0) {
                            JOptionPane.showMessageDialog(null, "添加成功！！");
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败！！");
                        }
                    }
                } catch (Exception er) {
                }
            } else { // 处理“继续”事件
                tID.setText("");
                tname.setText("");
            }
        }
    }
}
