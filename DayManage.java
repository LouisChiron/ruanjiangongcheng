import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayManage extends JFrame {
    public DayManage(String uname, String psd) {
        try {
            user = uname;
            password = psd;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 20));
        jLabel1.setBounds(new Rectangle(120, 15, 289, 36)); // 设置各组件的位置
        stumis.setBounds(new Rectangle(199, 69, 125, 23));
        inputscore.setBounds(new Rectangle(321, 69, 85, 25));
        editscore.setBounds(new Rectangle(323, 118, 85, 23));
        addadmin.setBounds(new Rectangle(199, 162, 85, 23));
        editpassword.setBounds(new Rectangle(323, 163, 85, 23));
        jLabel2.setBounds(new Rectangle(99, 67, 102, 27));
        jLabel5.setBounds(new Rectangle(99, 160, 86, 23));
        jLabel3.setBounds(new Rectangle(99, 192, 45, 46));
        aname.setBounds(new Rectangle(141, 205, 65, 24));
        addmanager.setBounds(new Rectangle(331, 204, 60, 25));
        psd.setBounds(new Rectangle(251, 206, 68, 23));
        jLabel4.setBounds(new Rectangle(214, 210, 42, 15));
        quit.setBounds(new Rectangle(286, 254, 83, 25));
        sure.setBounds(new Rectangle(142, 254, 83, 25));
        this.getContentPane().add(jLabel1); // 添加各个组件
        this.getContentPane().add(jLabel2);
        this.getContentPane().add(stumis);
        this.getContentPane().add(inputscore);
        this.getContentPane().add(editscore);
        this.getContentPane().add(jLabel5);
        this.getContentPane().add(addadmin);
        this.getContentPane().add(editpassword);
        this.getContentPane().add(jLabel3);
        this.getContentPane().add(aname);
        this.getContentPane().add(jLabel4);
        this.getContentPane().add(psd);
        this.getContentPane().add(addmanager);
        this.getContentPane().add(sure);
        this.getContentPane().add(quit);
        sure.addActionListener(new Handel());// 给按钮添加监视器
        addadmin.addActionListener(new Handel());
        editpassword.addActionListener(new Handel());
        addmanager.addActionListener(new Handel());
        quit.addActionListener(new Handel());
        aname.setEditable(false); // 最初设置名字与密码文本框为不可编辑状态
        psd.setEditable(false);
    }

    class Handel implements ActionListener { // 监听类，用于进行事件处理
        @Override
        public void actionPerformed(ActionEvent e) {
            Dbconn db = new Dbconn();
            String sql = "";
            if (e.getSource() == addadmin) {// 添加管理员时姓名和密码框设置为可编辑
                if (addadmin.isSelected()) {
                    addmanager.setText("添加");
                    psd.setEditable(true);
                    aname.setEditable(true);
                } else {
                    psd.setText("");
                    aname.setEditable(false);
                    aname.setText("");
                    psd.setEditable(false);
                }
            } else if (e.getSource() == editpassword) {
                if (editpassword.isSelected()) {
                    addmanager.setText("修改");
                    psd.setEditable(true);
                    aname.setEditable(false);
                } else {
                    psd.setText("");
                    psd.setEditable(false);
                }
            } else if (e.getSource() == addmanager) {
                if (addadmin.isSelected()) {// 添加管理员
                    String xm = aname.getText();
                    String mm = psd.getText();
                    sql = "insert into teacher(ID,name,psd,isAdmin) values('admin" + xm + "','" + xm + "','" + mm
                            + "',true)";
                    int i = db.Update(sql);
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "管理员添加成功！！");
                        addadmin.setSelected(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "管理员添加失败！！");
                    }
                } else if (editpassword.isSelected()) {// 修改密码
                    String jmm = aname.getText();
                    String xmm = psd.getText();
                    sql = "update teacher set psd='" + xmm + "' where name='" + user + "' and psd='" + password + "'";
                    int i = db.Update(sql);
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "密码修改成功！！");
                        editpassword.setSelected(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败，请仔细检查原因！");
                    }
                }
            } else if (e.getSource() == sure) {// 设置教师权限
                boolean lr = inputscore.isSelected();
                boolean xg = editscore.isSelected();
                boolean gl = stumis.isSelected();
                boolean mm = editpassword.isSelected();
                sql = "update quanxian set scoreinput=" + lr + " , scoreedit=" + xg + " , stumis=" + gl;
                int i = db.Update(sql);
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "设置权限成功！！");
                } else {
                    JOptionPane.showMessageDialog(null, "设置失败，请仔细查找原因！");
                }
            } else if (e.getSource() == quit) {// 处理“退出”事件
                setVisible(false);
            }
        }
    }

    JLabel jLabel1 = new JLabel("系   统   日   常   管   理");
    JLabel jLabel2 = new JLabel("教师权限管理");
    JCheckBox stumis = new JCheckBox("学生信息管理");
    JCheckBox addadmin = new JCheckBox("添加管理员");
    JCheckBox inputscore = new JCheckBox("成绩录入");
    JCheckBox editscore = new JCheckBox("成绩修改");
    JLabel jLabel3 = new JLabel("姓名：");
    JTextField aname = new JTextField();
    JLabel jLabel4 = new JLabel("密码：");
    JTextField psd = new JTextField();
    JCheckBox editpassword = new JCheckBox("修改密码");
    JButton addmanager = new JButton("添加");
    JButton quit = new JButton("退出");
    JLabel jLabel5 = new JLabel("管理员管理：");
    JButton sure = new JButton("应用");
    String user, password;
}
