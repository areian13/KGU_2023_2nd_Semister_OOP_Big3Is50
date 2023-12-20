package Server;

import Server.GUI.GUIMain;
import Server.GenericManager.Data;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Inbody implements Data {
    String date;
    String time;
    String userID;

    int weight;
    int muscle;
    int fat;

    @Override
    public void scan(Scanner file) {
        date = file.next();
        time = file.next();
        userID = file.next();

        weight = file.nextInt();
        muscle = file.nextInt();
        fat = file.nextInt();

        User user = ServerComputer.userHashMap.get(userID);
        user.myInbodyManager.dataList.add(this);
    }
    @Override
    public void print() {
        System.out.printf("[%s] %s %s체중(%dkg) 골격근량(%dkg) 체지방량(%dkg)\n",
                date, time, userID, weight, muscle, fat);
    }
    @Override
    public String toString() {
        return String.format("%s %s %s %d %d %d", date, time, userID, weight, muscle, fat);
    }
    @Override
    public String toGUIString() {
        return String.format("<html>[%s] %s<br>체중: %dkg<br>골격근량: %dkg<br>체지방량: %dkg<br><br></html>", date, time, weight, muscle, fat);
    }
    @Override
    public boolean matches(String keyword) {
        if(date.contains(keyword) || time.contains(keyword))
            return true;

        User user = ServerComputer.userHashMap.get(userID);
        return user.matches(keyword);
    }
    @Override
    public Object clone() {
        return null;
    }

    public void set(int weight, int muscle, int fat) {
        LocalDateTime now = LocalDateTime.now();
        date = now.toLocalDate().toString();
        time = now.toLocalTime().toString().substring(0, 8);

        userID = GUIMain.me.id;

        this.weight = weight;
        this.muscle = muscle;
        this.fat = fat;
    }
}
