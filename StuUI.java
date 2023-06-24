import javax.swing.*;
import java.awt.*;

public class StuUI extends JFrame {
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    Instu is = new Instu();
    EditSTU qs = new EditSTU();

    public StuUI() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setTitle("学生管理");
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension sSize = tk.getScreenSize();
        int sh = sSize.height;
        int sw = sSize.width;
        setSize(sw / 2, sh / 2);
        setLocation(sw / 4, sh / 4);
        this.setResizable(false);
        jTabbedPane1.addTab("添加学生", is);
        jTabbedPane1.addTab("学生查询", qs);
        getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
    }
}
