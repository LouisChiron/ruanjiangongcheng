import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainUI extends JFrame {
    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
    Dimension sSize = tk.getScreenSize();
    int sh = sSize.height;
    int sw = sSize.width;
    BorderLayout borderLayout1 = new BorderLayout();
    MenuBar mb = new MenuBar();  //菜单的初始化
    Menu sys = new Menu("系统管理");
    MenuItem scopy = new MenuItem("数据备份");
    MenuItem sback = new MenuItem("数据恢复");
    MenuItem sdayadmin = new MenuItem("日常管理");
    MenuItem squit = new MenuItem("退出");
    Menu peo = new Menu("人员管理");
    MenuItem pstu = new MenuItem("学生管理");
    Menu cou = new Menu("课程管理");
    MenuItem clogin = new MenuItem("课程注册");
    MenuItem inputc = new MenuItem("任课登录");
    Menu scr = new Menu("成绩管理");
    MenuItem sinput = new MenuItem("成绩录入");
    MenuItem squery = new MenuItem("成绩查询");
    MenuItem sedit = new MenuItem("成绩修改");
    Menu menu5 = new Menu("帮助");
    MenuItem sintro = new MenuItem("软件介绍");
    MenuItem ptec = new MenuItem("教师管理");
    JPanel p = new JPanel();
    private String user, psd, sf;

    public MainUI(String name, String psd, String sf) {
        try {  //接收各项参数
            user = name;
            this.psd = psd;
            this.sf = sf;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setTitle("大中专院校学生成绩管理系统");  //设置主窗体的标题
        setSize(sw * 3 / 5, sh * 3 / 5);  //设置主窗体的尺寸大小
        setLocation(sw / 4, sh / 4);  //设置主窗体的位置
        setResizable(false);  //设置主窗体不可改变大小
        Container c = getContentPane();
        setMenuBar(mb);  //添加菜单
        scopy.addActionListener(new Beifen());  //给各菜单项添加事件监视器
        sback.addActionListener(new Beifen());
        sdayadmin.addActionListener(new ActionListener() {//处理“日常管理”
            @Override
            public void actionPerformed(ActionEvent e) {
                DayManage ti = new DayManage(user, psd);
                ti.setSize(sw / 2, sh / 2);
                ti.setLocation(sw / 4, sh / 4);
                ti.setVisible(true);
            }
        });
        squit.addActionListener(new ActionListener() {  //处理“退出”
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        ptec.addActionListener(new ActionListener() {  //对“教师管理”处理
            @Override
            public void actionPerformed(ActionEvent e) {
                TeaUI ti = new TeaUI();
                ti.setSize(sw / 2, sh / 2);
                ti.setLocation(sw / 4, sh / 4);
                ti.setVisible(true);
            }
        });
        sintro.addActionListener(new ActionListener() {  //对“系统介绍”处理
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpFrame hf = new HelpFrame();
                hf.setSize(400, 400);
                hf.setLocation(sw / 4, sh / 4);
                hf.setVisible(true);
                hf.setResizable(false);
            }
        });
        pstu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sf.equals("学生")) {
                    StudentQuery s = new StudentQuery(user, psd);
                    s.setTitle("学生个人信息");
                    s.setSize(sw / 2, sh / 2);
                    s.setLocation(sw / 4, sh / 4);
                    s.setVisible(true);
                } else {
                    StuUI sui = new StuUI();
                    sui.setSize(sw / 2, sh / 2);
                    sui.setLocation(sw / 4, sh / 4);
                    sui.setVisible(true);
                }
            }
        });
        clogin.addActionListener(new ActionListener() {//对“课程注册”处理
            @Override
            public void actionPerformed(ActionEvent e) {
                InputCourse ic = new InputCourse();
                ic.setSize(380, 300);
                ic.setLocation(sw / 3, sh / 3);
                ic.setResizable(false);
                ic.setVisible(true);
            }
        });
        cou.addActionListener(new ActionListener() { //对“任课登录”处理
            @Override
            public void actionPerformed(ActionEvent e) {
                InputTC ic = new InputTC();
                ic.setSize(440, 500);
                ic.setLocation(sw / 3, sh / 3);
                ic.setVisible(true);
            }
        });
        sinput.addActionListener(new ActionListener() {  //处理“成绩录入”
            @Override
            public void actionPerformed(ActionEvent e) {
                InputScore is = new InputScore(user, psd);
                is.setTitle("成绩录入");
                is.setSize(sw - 350, sh - 300);
                is.setLocation(sw / 6, sh / 6);
                is.setVisible(true);
            }
        });
        squery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sf.equals("学生")) {
                    SScoreQuery scq = new SScoreQuery(user, psd);
                    scq.setTitle("学生成绩查询");
                    scq.setSize(sw - 350, sh - 300);
                    scq.setLocation(sw / 6, sh / 6);
                    scq.setResizable(false);
                    scq.setVisible(true);
                } else {
                    QueryScore is = null;
                    if (sf.equals("管理员")) {
                        is = new QueryScore(user, psd, "管理员");
                    } else {
                        is = new QueryScore(user, psd, "教师");
                    }
                    is.setTitle("成绩查询");
                    is.setSize(sw - 350, sh - 300);
                    is.setLocation(sw / 6, sh / 6);
                    is.setResizable(false);
                    is.setVisible(true);
                }
            }
        });
        sedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditScore es = new EditScore(user, psd);
                es.setTitle("成绩修改");
                es.setSize(sw - 350, sh - 300);
                es.setLocation(sw / 6, sh / 6);
                es.setResizable(false);
                es.setVisible(true);
            }
        });  //以下为添加各菜单项
        mb.add(sys);
        mb.add(peo);
        mb.add(cou);
        mb.add(scr);
        mb.add(menu5);
        sys.add(scopy);
        sys.add(sback);
        sys.addSeparator();
        sys.add(sdayadmin);
        sys.addSeparator();
        sys.add(squit);
        peo.add(pstu);
        peo.add(ptec);
        cou.add(clogin);
        cou.add(inputc);
        scr.add(sinput);
        scr.add(squery);
        scr.add(sedit);
        menu5.add(sintro);
        p.add(new JLabel(new ImageIcon("image\\2.jpg")));
        c.add(p, "Center");
        c.add(new JLabel("版权所有：Jack魏", JLabel.CENTER), "South");
    }

    class Beifen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser backup = new JFileChooser();
            backup.showDialog(null, "打开");
            File backfile = backup.getSelectedFile();
            FileInputStream fr = null;
            FileOutputStream fw = null;
            try {
                if (e.getSource() == scopy) {
                    File f1 = new File("achievement3.mdb");
                    fr = new FileInputStream(f1.getAbsolutePath());
                    fw = new FileOutputStream(backfile);
                } else if (e.getSource() == sback) {
                    File f1 = new File("achievement3.mdb");
                    fr = new FileInputStream(backfile);
                    fw = new FileOutputStream(f1.getAbsolutePath());
                }
                JProgressBar b = new JProgressBar();
                b.setIndeterminate(true);
                b.setVisible(true);
                byte c[] = new byte[1024 * 5];
                int i1;
                while ((i1 = fr.read(c)) != -1) {
                    fw.write(c, 0, i1);
                }
                fw.flush();
                fr.close();
                fw.close();
                if (e.getSource() == scopy) {
                    JOptionPane.showMessageDialog(null, "备份完成");
                } else {
                    JOptionPane.showMessageDialog(null, "恢复完成");
                }
            } catch (IOException e1) {
                System.out.println(e1.toString());
            }
        }
    }
}
