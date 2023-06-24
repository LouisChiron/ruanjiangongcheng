import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame {  //构造各组件
    JPanel contentPane;
    JLabel jLabel1 = new JLabel("用户名：");
    JTextField uname = new JTextField();
    JLabel jLabel2 = new JLabel("密码：");
    JPasswordField upsd = new JPasswordField();
    JButton submit = new JButton("登录");
    JButton cancel = new JButton("取消");
    JLabel jLabel3 = new JLabel("大中专院校学生成绩");
    JLabel jLabel4 = new JLabel("身份：");
    JComboBox identity = new JComboBox();

    public Login() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);//设置右上角关闭
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {       //组件初始化
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(680, 500));  //设置尺寸大小
        setResizable(false);  //设置是否可改变大小
        setTitle("大中专院校学生成绩管理系统");  //设置标题
        jLabel1.setBounds(new Rectangle(49, 69, 61, 32));  //设置各组件的初始位置
        uname.setBounds(new Rectangle(122, 77, 102, 27));
        jLabel2.setBounds(new Rectangle(49, 128, 45, 31));
        upsd.setBounds(new Rectangle(122, 130, 102, 28));
        submit.setBounds(new Rectangle(49, 229, 69, 27));
        cancel.setBounds(new Rectangle(160, 229, 64, 27));
        jLabel3.setFont(new Font("宋体-方正超大字符集", Font.BOLD, 21));
        jLabel3.setBounds(new Rectangle(49, 10, 231, 41));
        jLabel4.setBounds(new Rectangle(49, 182, 55, 26));
        identity.setBounds(new Rectangle(122, 179, 102, 30));
        identity.addItem(new String("学生"));
        identity.addItem(new String("教师"));
        identity.addItem(new String("管理员"));
        contentPane.add(jLabel2);
        contentPane.add(jLabel4);
        contentPane.add(identity);
        contentPane.add(upsd);
        contentPane.add(uname);
        contentPane.add(jLabel1);
        contentPane.add(submit);
        contentPane.add(cancel);
        contentPane.add(jLabel3);
        submit.addActionListener(new yanzheng());
        cancel.addActionListener(new yanzheng());
    }

    class yanzheng implements ActionListener {//验证

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {
                String xm = uname.getText();
                String mm = new String(upsd.getPassword());
                String sf = (String) identity.getSelectedItem();
                Dbconn db = new Dbconn();//调用自定义类dbconn来连接数据库，并查找输入的账号密码是否正确
                ResultSet rs = null;
                String sql = "";
                boolean login = false;
                if (sf.equals("学生")) {//数据库语句查询
                    sql = "select * from student where name='" + xm + "' and ID='" + mm + "'";
                } else if (sf.equals("管理员")) {
                    sql = "select * from teacher where name='" + xm + "' and psd='" + mm + "' and isAdmin=true ";
                } else {
                    sql = "select * from teacher where name='" + xm + "' and psd='" + mm + "' ";
                }
                try {
                    rs = db.Query(sql);
                    if (rs.next()) {
                        login = true;
                    } else {//设置弹窗。
                        JOptionPane.showMessageDialog(null, "用户/密码错误，请重新输入！");
                    }
                    if (login == true) {
                        if (sf.equals("学生")) {
                            MainUI mui = new MainUI(xm, mm, sf);
                            mui.scopy.setEnabled(false);
                            mui.sback.setEnabled(false);
                            mui.sdayadmin.setEnabled(false);
                            mui.ptec.setEnabled(false);
                            mui.cou.setEnabled(false);
                            mui.sinput.setEnabled(false);
                            mui.inputc.setEnabled(false);
                            mui.clogin.setEnabled(false);
                            mui.sedit.setEnabled(false);
                            mui.setVisible(true);
                        } else if (sf.equals("管理员")) {
                            MainUI admin = new MainUI(xm, mm, sf);//调用自定义类MAINUI加载下面的程序
                            admin.sedit.setEnabled(false);
                            admin.sinput.setEnabled(false);
                            setVisible(false);
                            admin.setVisible(true);
                        } else if (sf.equals("教师")) {
                            rs = db.Query("select * from quanxian");
                            if (rs.next()) {
                                MainUI teac = new MainUI(xm, mm, sf);
                                teac.scopy.setEnabled(false);
                                teac.sback.setEnabled(false);
                                teac.sdayadmin.setEnabled(false);
                                teac.cou.setEnabled(false);
                                teac.sinput.setEnabled(rs.getBoolean(1));
                                teac.sedit.setEnabled(rs.getBoolean(2));
                                teac.pstu.setEnabled(rs.getBoolean(3));
                                teac.ptec.setEnabled(rs.getBoolean(4));
                                setVisible(false);
                                teac.setVisible(true);
                            }
                        }
                    }
                } catch (Exception er) {
                    System.out.println(er.toString());
                }
            }
        }
    }
}
