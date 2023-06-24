import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.util.Date;

public class Instu extends JPanel {
    public Instu() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 20));
        jLabel1.setBounds(new Rectangle(132, 4, 239, 32)); // 设置各组件的位置
        jLabel2.setBounds(new Rectangle(86, 53, 104, 37));
        sid.setBounds(new Rectangle(150, 57, 109, 28));
        jLabel3.setBounds(new Rectangle(86, 98, 102, 28));
        sname.setBounds(new Rectangle(150, 100, 109, 27));
        sex1.setBounds(new Rectangle(157, 140, 49, 29));
        sex2.setBounds(new Rectangle(225, 141, 59, 25));
        jLabel4.setBounds(new Rectangle(86, 168, 76, 37));
        syear.setBounds(new Rectangle(150, 175, 57, 25));
        jLabel5.setBounds(new Rectangle(211, 173, 31, 27));
        smonth.setBounds(new Rectangle(232, 175, 59, 25));
        jLabel6.setBounds(new Rectangle(298, 173, 39, 26));
        sdate.setBounds(new Rectangle(318, 175, 57, 25));
        jLabel7.setBounds(new Rectangle(383, 172, 46, 27));
        jLabel8.setBounds(new Rectangle(86, 214, 65, 30));
        sgrade.setBounds(new Rectangle(149, 215, 162, 26));
        jLabel9.setBounds(new Rectangle(86, 254, 67, 33));
        photo.setBounds(new Rectangle(150, 259, 160, 26));
        scan.setBounds(new Rectangle(327, 259, 68, 26));
        sure.setBounds(new Rectangle(129, 300, 82, 29));
        con.setBounds(new Rectangle(278, 300, 79, 29));
        jLabel10.setBounds(new Rectangle(86, 140, 66, 29));
        photoshow.setBorder(BorderFactory.createLineBorder(Color.black));
        photoshow.setText("照片");
        photoshow.setBounds(new Rectangle(300, 51, 95, 113));
        this.add(jLabel1); // 添加各组件
        this.add(jLabel2);
        this.add(sid);
        this.add(jLabel3);
        this.add(sname);
        this.add(jLabel10);
        this.add(sex1);
        this.add(sex2);
        this.add(jLabel4);
        this.add(syear);
        this.add(jLabel5);
        this.add(smonth);
        this.add(jLabel6);
        this.add(sdate);
        this.add(jLabel7);
        this.add(jLabel8);
        Dbconn db = new Dbconn();
        ResultSet rs = db.Query("select distinct  Cgrade from T_C");
        sgrade.addItem("   ");
        while (rs.next()) {
            sgrade.addItem(rs.getString(1));
        }
        this.add(sgrade);
        this.add(jLabel9);
        this.add(photo);
        this.add(scan);
        this.add(photoshow);
        this.add(con);
        this.add(sure);
        sure.addActionListener(new Handel()); // 给各按钮添加事件监视器
        con.addActionListener(new Handel());
        scan.addActionListener(new Handel());
    }

    class Handel implements ActionListener { // 事件处理类
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            if (e.getSource() == sure) { // 处理“添加”按钮
                String xh = sid.getText();
                String xm = sname.getText();
                String year = syear.getText();
                String month = smonth.getText();
                String day = sdate.getText();
                String bj = (String) sgrade.getSelectedItem();
                Date brith = java.sql.Date.valueOf(year + "-" + month + "-" + day);
                String xb = "女";
                if (sex1.isSelected()) {
                    xb = "男";
                }
                String zp = photo.getText();
                String sql = "insert into student(ID,name,sex,birthday,grade,photo) values('" + xh + "','" + xm + "','"
                        + xb + "',#" + brith + "#,'" + bj + "','" + zp + "')";
                int i = db.Update(sql);
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "添加成功！！");
                } else {
                    JOptionPane.showMessageDialog(null, "添加失败！！");
                }
            } else if (e.getSource() == con) { // 处理“继续”按钮
                sid.setText("");
                sname.setText("");
                syear.setText("");
                smonth.setText("");
                sdate.setText("");
                photo.setText("");
            } else if (e.getSource() == scan) { // 处理“浏览”按钮
                JFileChooser cfile = new JFileChooser();
                cfile.showDialog(null, "打开");
                File f = cfile.getSelectedFile();
                String lj = f.getAbsolutePath();
                photo.setText(lj);
                photoshow.setIcon(new ImageIcon(f.toString()));
            }
            db.close();
        }
    }

    JLabel jLabel1 = new JLabel("学 生 信 息 录 入 ");
    JLabel jLabel2 = new JLabel("学号：");
    JTextField sid = new JTextField();
    JLabel jLabel3 = new JLabel("姓名：");
    JTextField sname = new JTextField();
    JRadioButton sex1 = new JRadioButton("男");
    JRadioButton sex2 = new JRadioButton("女");
    JLabel jLabel4 = new JLabel("出生日期");
    JTextField syear = new JTextField();
    JLabel jLabel5 = new JLabel("年");
    JTextField smonth = new JTextField();
    JLabel jLabel6 = new JLabel("月");
    JTextField sdate = new JTextField();
    JLabel jLabel7 = new JLabel("日");
    JLabel jLabel8 = new JLabel("班级：");
    JComboBox sgrade = new JComboBox();
    JLabel jLabel9 = new JLabel("照片：");
    JTextField photo = new JTextField();
    JButton scan = new JButton("浏览");
    JButton sure = new JButton("添加");
    JButton con = new JButton("继续");
    JLabel jLabel10 = new JLabel("性别：");
    JLabel photoshow = new JLabel(new ImageIcon());
}
