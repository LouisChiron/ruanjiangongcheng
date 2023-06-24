import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputCourse extends JFrame {
    JLabel jLabel1 = new JLabel("课 程 基 本 信 息 注 册");
    JLabel jLabel2 = new JLabel("课程名称：");
    JTextField course = new JTextField();
    JLabel jLabel3 = new JLabel("学时：");
    JTextField period = new JTextField();
    JLabel jLabel4 = new JLabel("学分：");
    JTextField credit = new JTextField();
    JButton sure = new JButton("确定");
    JButton con = new JButton("继续");
    JButton cancel = new JButton("退出");
    String sql = "";

    public InputCourse() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setTitle("课程注册");
        getContentPane().setLayout(null);  // 设置布局管理器为“空”布局
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 20)); //设置各组件的位置
        jLabel1.setBounds(new Rectangle(67, 15, 253, 47));
        jLabel2.setBounds(new Rectangle(71, 66, 69, 35));
        course.setBounds(new Rectangle(145, 71, 148, 30));
        jLabel3.setBounds(new Rectangle(71, 106, 40, 33));
        period.setBounds(new Rectangle(145, 108, 148, 29));
        jLabel4.setBounds(new Rectangle(71, 140, 44, 33));
        credit.setBounds(new Rectangle(145, 145, 148, 28));
        sure.setBounds(new Rectangle(71, 224, 71, 27));
        con.setBounds(new Rectangle(156, 223, 64, 28));
        cancel.setBounds(new Rectangle(234, 222, 59, 29));
        sure.addActionListener(new EventHandel());//给按钮添加监视器
        con.addActionListener(new EventHandel());
        cancel.addActionListener(new EventHandel());
        getContentPane().add(jLabel1);    //添加各个组件
        getContentPane().add(jLabel2);
        getContentPane().add(course);
        getContentPane().add(jLabel3);
        getContentPane().add(period);
        getContentPane().add(jLabel4);
        getContentPane().add(credit);
        getContentPane().add(sure);
        getContentPane().add(con);
        getContentPane().add(cancel);
    }

    class EventHandel implements ActionListener {//事件处理类

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == con) {  //处理“继续”事件
                course.setText("");
                period.setText("");
                credit.setText("");
            } else if (e.getSource() == cancel) { //处理“退出”事件
                setVisible(false);
            } else {  //处理“确定添加”事件
                Dbconn db = new Dbconn();
                String kc = course.getText();
                String xs = period.getText();
                String xf = credit.getText();
                sql = "insert into course(cname,period,credit) values('" + kc + "','" + xs + "','" + xf + "')";
                int i = db.Update(sql);
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "课程录入成功！！");
                } else {
                    JOptionPane.showMessageDialog(null, "录入失败！！");
                }
            }
        }
    }
}
