package Server;

import Server.Facade.UIData;
import Server.GenericManager.Data;
import Server.GenericManager.Manager;

import java.text.DecimalFormat;
import java.util.*;

public class Program implements Data, UIData, Comparable {
    public static HashMap<String, String> trainerHashMap = new HashMap<>();

    static int count = 0;
    int id;
    boolean isPT;
    public String name;
    public String date;
    public String startTime;
    public String endTime;
    public int price;
    int n;

    public Manager<User> membersManager = new Manager<>();

    @Override
    public void scan(Scanner file) {
        id = ++count;

        isPT = (file.nextInt() == 1);
        name = file.next();
        date = file.next();
        startTime = file.next();
        endTime = file.next();
        price = file.nextInt();

        n = file.nextInt();

        for (int i = 0; i < n; i++) {
            String userID = file.next();
            User user = ServerComputer.userHashMap.get(userID);
            membersManager.dataList.add(user);
            user.myProgramManager.dataList.add(this);
        }
    }
    @Override
    public void print() {
        System.out.printf("%s %s %s %s\n", name, date, startTime, endTime);
        System.out.print("참여자: ");
        for (User user : membersManager.dataList)
            System.out.printf("%s ", user.nickname);
        System.out.println();
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%d %s %s %s %s %d %d ", (isPT ? 1 : 0), name, date, startTime, endTime, price, n));
        for(User user : membersManager.dataList)
            result.append(user.id + " ");
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
    @Override
    public String toGUIString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("<html>%s %s %s~%s 회당 %s원<br>", name, date, startTime, endTime, new DecimalFormat("###,###").format(price)));
        for (User user : membersManager.dataList) {
            result.append("&nbsp;- " + user.nickname + "<br>");
        }
        result.append("<br></html>");
        return result.toString();
    }
    @Override
    public boolean matches(String keyword) {
        if (keyword.contentEquals("pt") && isPT)
            return true;
        if (keyword.contentEquals("!pt") && !isPT)
            return true;

        if(String.valueOf(id).contentEquals(keyword))
            return true;
        if(name.contains(keyword))
            return true;
        if(date.contains(keyword))
            return true;
        if(startTime.compareTo(keyword) <= 0 && endTime.compareTo(keyword) >= 0)
            return true;
        return false;
    }
    @Override
    public void set(Object[] uitexts) {
        // TODO Auto-generated method stub
    }
    @Override
    public String[] getSimpleData() {
        String[] result = new String[4];
        result[0] = name;
        result[1] = date;
        result[2] = startTime;
        result[3] = endTime;
        return result;
    }
    @Override
    public String[] getDetailData() {
        String[] result = new String[3];
        result[0] = String.format("프로그램명: %s", name);
        result[1] = String.format("시간: %s %s ~ %s", date, startTime, endTime);
        result[2] = "참여자: ";

        for (int i = 0; i < n; i++)
            result[2] += membersManager.dataList.get(i).nickname + " ";

        return result;
    }

    public void addNewUser(User user) {
        n++;
        membersManager.dataList.add(user);
    }
    public void deleteUser(User user) {
        membersManager.dataList.remove(user);
        n--;
    }
    public String getStartDate()
    {
        return date + startTime;
    }
    public String getEndDate() {
        return date + endTime;
    }

    @Override
    public int compareTo(Object o) {
        Program program = (Program)o;

        return ServerComputer.compareDate(getStartDate(), program.getStartDate());
    }
    public boolean isPT() {
        return isPT;
    }

    @Override
    public Object clone() {
        Program cloneProgram = new Program();
        cloneProgram.id = id;
        cloneProgram.isPT = isPT;
        cloneProgram.name = name;
        cloneProgram.date = date;
        cloneProgram.startTime = startTime;
        cloneProgram.endTime = endTime;
        cloneProgram.n = n;

        cloneProgram.membersManager = (Manager<User>)membersManager.clone();

        return cloneProgram;
    }
}