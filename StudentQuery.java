import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;

public class StudentQuery extends JFrame {
    String sql = "", sf = "";

    public StudentQuery(String xm, String xh, String xb, Date sr, String bj, String sf) {
        try {
            sid = xh;
            sname = xm;
            ssex = xb;
            sbrith = sr;
            sclass = bj;
            SID.setText(xh);
            Sname.setText(xm);
            sex.setText(ssex);
            sgrade.setText(sclass);
            this.sf = sf;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public StudentQuery(String xm, String xh) {
        try {
            sid = xh;
            sname = xm;
            edit.setEnabled(false);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 20)); //初始化各组件
        jLabel1.setToolTipText("");
        photo.setBorder(BorderFactory.createLineBorder(Color.black));
        photo.setText("暂无照片");
        brith.setBounds(new Rectangle(160, 215, 239, 25));
        photo.setBounds(new Rectangle(308, 79, 89, 120));
        SID.setBounds(new Rectangle(160, 78, 115, 26));
        sgrade.setBounds(new Rectangle(160, 175, 115, 25));
        Sname.setBounds(new Rectangle(160, 109, 114, 27));
        sex.setBounds(new Rectangle(160, 141, 115, 27));
        jLabel1.setBounds(new Rectangle(141, 24, 192, 38));
        quit.setBounds(new Rectangle(276, 257, 83, 25));
        edit.setBounds(new Rectangle(129, 257, 83, 25));
        jLabel2.setBounds(new Rectangle(93, 82, 42, 15));
        jLabel3.setBounds(new Rectangle(93, 114, 42, 18));
        jLabel4.setBounds(new Rectangle(93, 146, 42, 15));
        jLabel6.setBounds(new Rectangle(93, 178, 42, 15));
        jLabel5.setBounds(new Rectangle(93, 220, 68, 15));
        this.getContentPane().add(jLabel1);  //添加各组件
        this.getContentPane().add(jLabel2);
        this.getContentPane().add(SID);
        this.getContentPane().add(jLabel3);
        this.getContentPane().add(Sname);
        this.getContentPane().add(jLabel4);
        this.getContentPane().add(sex);
        this.getContentPane().add(jLabel6);
        this.getContentPane().add(sgrade);
        this.getContentPane().add(jLabel5);
        this.getContentPane().add(brith);
        this.getContentPane().add(photo);
        this.getContentPane().add(edit);
        this.getContentPane().add(quit);
        edit.addActionListener(new ActionListener() { //给“保存”添加事件监视器
            @Override
            public void actionPerformed(ActionEvent e) {
                Dbconn db = new Dbconn();
                String sxh = SID.getText();
                String sxm = Sname.getText();
                String sxb = sex.getText();
                String sbj = sgrade.getText();
                String ssr = brith.getText();
                sql = "update student set ID='" + sxh + "',name='" + sxm + "',sex='" + sxb + "',birthday=#" + ssr + "#,grade='" + sbj + "' where  ID='" + sid + "'and name='" + sname + "' and sex='" + ssex + "'and birthday=#" + sbrith + "# and grade='" + sclass + "' ";
                int i = db.Update(sql);
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "修改成功！！");
                } else {
                    JOptionPane.showMessageDialog(null, "修改失败！！");
                }
            }
        });
        quit.addActionListener(new ActionListener() {  //给“退出”添加事件监视器
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        sql = "select * from student where ID='" + sid + "' and name='" + sname + "'";
        ResultSet rs = null;
        try {
            String zp = "";
            Dbconn db = new Dbconn();
            rs = db.Query(sql);  //查询相应记录
            if (rs.next()) {  //如果是学生，则不能修改
                SID.setText(rs.getString("ID"));
                SID.setEditable(false);
                Sname.setText(rs.getString("name"));
                Sname.setEditable(false);
                sex.setText(rs.getString("sex"));
                sex.setEditable(false);
                brith.setText((rs.getDate("birthday")).toString());
                brith.setEditable(false);
                sgrade.setText(rs.getString("grade"));
                sgrade.setEditable(false);
                zp = rs.getString("photo");
                if (!zp.equals("")) {
                    photo.setIcon(new ImageIcon(zp));
                }
            }
            if (sf.equals("teacher")) { //如果是老师，可以进行修改
                SID.setEditable(true);
                Sname.setEditable(true);
                sex.setEditable(true);
                brith.setEditable(true);
                sgrade.setEditable(true);
            }
            db.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    JLabel jLabel1 = new JLabel("学 生 基 本 信 息");
    JLabel jLabel2 = new JLabel("学号：");
    JTextField SID = new JTextField();
    JLabel jLabel3 = new JLabel("姓名：");
    JTextField Sname = new JTextField();
    JLabel jLabel4 = new JLabel("性别：");
    JTextField sex = new JTextField();
    JLabel jLabel5 = new JLabel("出生日期：");
    JTextField brith = new JTextField();
    JLabel jLabel6 = new JLabel("班级：");
    JTextField sgrade = new JTextField();
    JLabel photo = new JLabel(new ImageIcon());
    JButton edit = new JButton("保存");
    JButton quit = new JButton("退出");
    String sid, sname, ssex, sclass;
    Date sbrith;
}
