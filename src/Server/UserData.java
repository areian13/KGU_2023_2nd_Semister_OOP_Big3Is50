package Server;
import Server.Facade.UIData;
import Server.GenericManager.Data;
import Server.GenericManager.Manager;

import java.util.*;

public class UserData implements Data {
    Manager<User> userManager = new Manager<>();
    @Override
    public void scan(Scanner file) {
        int userType = file.nextInt();
        userManager.scan(file, () -> (userType == 1 ? new Normal() : new Trainer()));
    }
    @Override
    public void print() {
        userManager.printAll();
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (User user : userManager.dataList)
            result.append(user + "\n");
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
    @Override
    public String toGUIString() {
        return String.format("");
    }
    @Override
    public boolean matches(String keyword) {
        return true;
    }

    public void addUser(User user) {
        userManager.dataList.add(user);

    }
    @Override
    public Object clone() {
        return null;
    }
}