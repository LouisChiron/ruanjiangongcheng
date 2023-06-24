import javax.swing.*;
import java.awt.*;

public class TeaUI extends JFrame {//添加两个选项卡：“添加教师”和“教师信息管理”
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    InputTea it = new InputTea(); //创建“添加教师”选项卡对象
    QueryTea qt = new QueryTea(); //创建“教师信息管理”选项卡对象

    public TeaUI() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setTitle("教师管理");
        setResizable(false);
        getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
        jTabbedPane1.addTab("添加教师", it);
        jTabbedPane1.addTab("教师信息管理", qt);
    }
}
