package Server;

import Server.GenericManager.Data;

import java.util.Scanner;

public class Exercise implements Data {
    String name;
    int cal;
    int exerciseType;

    @Override
    public void scan(Scanner file) {
        name = file.next();
    }
    @Override
    public void print() {
        System.out.printf("[%s산소] %s: ", (exerciseType == 1 ? "유" : "무"), name);
    }
    @Override
    public String toString() {
        return String.format("%d %s", exerciseType, name);
    }
    @Override
    public String toGUIString() {
        return String.format("[%s산소] %s: ", (exerciseType == 1 ? "유" : "무"), name);
    }
    @Override
    public boolean matches(String keyword) {

        if ((exerciseType == 1 ? "유산소" : "무산소").contains(keyword))
            return true;
        return name.contains(keyword);
    }
    @Override
    public Object clone() {
        return new Exercise();
    }
}
