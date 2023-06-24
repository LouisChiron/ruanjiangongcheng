import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditTea extends JPanel {
    JLabel jLabel1 = new JLabel("教师信息修改");
    JLabel jLabel2 = new JLabel("姓名：");
    JTextField tname = new JTextField();
    JLabel jLabel3 = new JLabel("性别：");
    ButtonGroup bg = new ButtonGroup();
    JRadioButton sex1 = new JRadioButton("男");
    JRadioButton sex2 = new JRadioButton("女");
    JLabel jLabel4 = new JLabel("职称：");
    JComboBox post = new JComboBox();
    JButton sure = new JButton("保存");
    JLabel jLabel5 = new JLabel("教师编号：");
    JTextField tID = new JTextField();
    String bh, xm, xb, zc;

    public EditTea(String bh, String xm, String xb, String zc) {
        try {
            this.bh = bh;
            this.xm = xm;
            this.xb = xb;
            this.zc = zc;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 20));
        jLabel1.setBounds(new Rectangle(133, 14, 149, 33));
        jLabel5.setBounds(new Rectangle(80, 60, 66, 40));
        tID.setBounds(new Rectangle(154, 70, 79, 21));
        tID.setText(bh);
        jLabel2.setBounds(new Rectangle(100, 98, 47, 29));
        tname.setBounds(new Rectangle(154, 103, 78, 25));
        tname.setText(xm);
        jLabel3.setBounds(new Rectangle(100, 133, 40, 27));
        bg.add(sex1);
        bg.add(sex2);
        sex1.setBounds(new Rectangle(151, 136, 39, 23));
        sex2.setBounds(new Rectangle(200, 136, 39, 23));
        if (xb.equals("男")) {
            sex1.setSelected(true);
        } else {
            sex2.setSelected(true);
        }
        jLabel4.setBounds(new Rectangle(100, 162, 41, 34));
        post.setBounds(new Rectangle(155, 166, 96, 26));
        post.addItem("讲师");
        post.addItem("助教");
        post.addItem("副教授");
        post.addItem("教授");
        post.setSelectedItem(zc);
        sure.setBounds(new Rectangle(145, 215, 73, 25));
        sure.addActionListener(new Handel());  //给按钮添加监视器
        this.add(jLabel1);
        this.add(jLabel5);
        this.add(tID);
        this.add(jLabel2);
        this.add(tname);
        this.add(jLabel3);
        this.add(sex1);
        this.add(sex2);
        this.add(jLabel4);
        this.add(post);
        this.add(sure);
    }

    class Handel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            if (e.getSource() == sure) {
                String id = tID.getText();
                String xx = tname.getText();
                String xb = "";
                String sql = "";
                if (sex1.isSelected()) {
                    xb = "男";
                } else {
                    xb = "女";
                }
                String title = (String) post.getSelectedItem();
                sql = "update teacher set ID='" + id + "', name='" + xx + "',sex='" + xb + "', title='" + title + "'  where ID='" + bh + "' and name='" + xm + "' and sex='" + xb + "' and title='" + zc + "'";
                try {
                    int n = db.Update(sql);
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "修改成功！！");
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败！！");
                    }
                } catch (Exception er) {
                }
            }
        }
    }
}
