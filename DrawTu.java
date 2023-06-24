import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class DrawTu extends JPanel {
    String tname;
    String tgrade;
    int count = 0; //存放总人数
    int dengji[] = new int[10]; //存放各等级人数
    JPanel p1 = new JPanel();

    public DrawTu(String tname, String tgrade) {
        try {
            this.tname = tname;
            this.tgrade = tgrade;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() {
        try {
            setLayout(new BorderLayout());
            for (int i = 0; i < 10; i++) {//将各等级人数清零
                dengji[i] = 0;
            }
            Dbconn db = new Dbconn();
            ResultSet rs = db.Query("select * from S_C where CName='" + tname + "' and Tgrade='" + tgrade + "'");
            while (rs.next()) {
                float fen = rs.getFloat("score");
                count++;
                switch ((int) fen / 10) {
                    case 10:
                    case 9:
                        dengji[9] = dengji[9] + 1;
                        break;
                    case 8:
                        dengji[8] = dengji[8] + 1;
                        break;
                    case 7:
                        dengji[7] = dengji[7] + 1;
                        break;
                    case 6:
                        dengji[6] = dengji[6] + 1;
                        break;
                    case 5:
                        dengji[5] = dengji[5] + 1;
                        break;
                    case 4:
                        dengji[4] = dengji[4] + 1;
                        break;
                    case 3:
                        dengji[3] = dengji[3] + 1;
                        break;
                    case 2:
                        dengji[2] = dengji[2] + 1;
                        break;
                    case 1:
                        dengji[1] = dengji[1] + 1;
                        break;
                    case 0:
                        dengji[0] = dengji[0] + 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        int x = 0, y = 0, x1 = 0, y1 = 10;
        g.drawLine(40, 30, 40, 300); //输出坐标轴(x,y)
        g.drawLine(40, 300, 380, 300);
        for (int i = 0; i <= 10; i++) { //输出x、y坐标的刻度及数值
            g.setColor(Color.red);
            g.drawLine(40 + x, 298, 40 + x, 302);
            g.drawString(String.valueOf(y), 38 + x, 315);
            g.drawLine(38, 280 - x1, 41, 280 - x1);
            g.drawString(String.valueOf(y1), 20, 285 - x1);
            x = x + 30;//每刻度的间隔
            y = y + 10;
            x1 = x1 + 25;
            y1 = y1 + 10;
        }
        g.drawString("分数", 405, 310);
        g.drawString("百分比 单位%", 20, 25);
        for (int i = 1; i <= 10; i++) {
            g.setColor(Color.blue);//填充外图
            g.fillRect(30 + (i - 1) * 30, 300 - (int) ((float) dengji[i - 1] / count * 100 * 2.5), 15, (int) ((float) dengji[i - 1] / count * 100 * 2.5));
            g.setColor(Color.blue);//写百分比
            g.drawString(String.valueOf((int) ((float) dengji[i - 1] / count * 100)) + '%', 30 + (i - 1) * 30, 290 - (int) ((float) dengji[i - 1] / count * 100 * 2.5));
        }
    }
}
