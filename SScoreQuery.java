import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Vector;

public class SScoreQuery extends JFrame {
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel jLabel1 = new JLabel("个人成绩查询");
    ResultSet rs = null;
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);
    String sql, sxm, sxh;

    public SScoreQuery(String xm, String id) {
        try {
            sxm = xm;
            sxh = id;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 18));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        getContentPane().add(jLabel1, "North");
        model.addColumn("学期");
        model.addColumn("科目");
        model.addColumn("成绩");
        getContentPane().add(jScrollPane1, "Center");
        Dbconn db = new Dbconn();
        sql = "select * from S_C where Sname='" + sxm + "' and SID='" + sxh + "'";
        try {
            int j = model.getRowCount();//删除表格中原有的数据
            if (j > 0) {
                for (int i = 0; i < j; i++) {
                    model.removeRow(0);
                }
            }
            rs = db.Query(sql); //从数据库中查询相应的数据
            while (rs.next()) {
                tempvector = new Vector(1, 1);
                tempvector.add(rs.getString("Term"));
                tempvector.add(rs.getString("Cname"));
                tempvector.add(rs.getFloat("score"));
                model.addRow(tempvector);
            }
            dbtable.setEnabled(false); //表格中的数据不能修改
        } catch (Exception e2) {
            System.out.println(e2.toString());
        }
    }
}
