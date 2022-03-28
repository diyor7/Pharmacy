package uz.com.MyDb;

import uz.com.Models.*;

import java.util.ArrayList;

public class db {

    public static User session;
    public static Organization sessionOrg;
    public static ArrayList<User> users = new ArrayList<>(0);
    public static ArrayList<Organization> organizations = new ArrayList<>(0);
    public static Location location = new Location();
    public static ArrayList<String> colors = new ArrayList<>();
    public static int mapSize = 20;

    /**
     *   Bu method mapni yaratib beradi, mainda run() dan oldin chaqirib qo'yamiz
     *   Undan tashqari super admin ni ham set qilib beradi
     */
    public static void setMap(int sizeOfRows, int sizeOfColumns) {
        ArrayList<Row> rows = location.getRows();
        for (int i = 0; i < sizeOfRows; i++) {
            rows.add(new Row());
            Row row = rows.get(i);
            ArrayList<Column> columns = row.getColumns();
            for (int j = 0; j < sizeOfColumns; j++) {
                columns.add(new Column());
            }
        }
        location.setRows(rows);

        colors.add("\u001b[31m");
        colors.add("\u001b[33m");
        colors.add("\u001b[35m");
        colors.add("\u001b[0m");
        colors.add("\u001b[30m");
        colors.add("\u001b[32m");
        colors.add("\u001b[34m");
        colors.add("\u001b[36m");

        users.add(new User("121", "121", 1));
    }

    public static   void supadmin(){
        users.add(new User("123","123",1));
        users.add(new User("0","0",2));

    }

}
