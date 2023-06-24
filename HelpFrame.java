import javax.swing.*;
import java.awt.*;

public class HelpFrame extends JFrame {
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel l = new JLabel("                           帮助说明");
    JTextArea ta = new JTextArea(5, 20);

    public HelpFrame() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        String intr = "      本系统用于学生成绩管理，可实现教师任课登记、学生信息录入与查询、成绩录入及查询、成绩正态分布分析等功能。为我们系统科学地管理学生成绩提供了一条便捷的途径。\n";
        intr = intr + "使用说明：\n      该系统通过不同用户登录，可进行不同操作。在整个系统使用之前，首先，应该以管理员身份登录系统，进行课程添加、教师人员管理、教师任课登录等操作";
        intr = intr + "然后根据需要授予教师操作权限，比如：学生人员管理，成绩录入与修改。其次，以教师身份登录系统，进行学生信息管理、学生成绩管理等操作。";
        intr = intr + "最后，使用学生身份登录系统，可进行学生个人信息核对及个人成绩查询操作。\n ";
        ta.setLineWrap(true);
        ta.setText(intr);
        ta.setEditable(false);
        getContentPane().add(l, "North");
        getContentPane().add(ta, "Center");
        getContentPane().add(new JLabel("版权所有：Jack魏", JLabel.CENTER), "South");
    }
}
