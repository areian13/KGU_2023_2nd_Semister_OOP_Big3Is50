package Server;

import java.util.Scanner;

public class Anaerobic extends Exercise {
    int kg;
    int count;

    public Anaerobic() {
        exerciseType = 2;
    }
    @Override
    public void scan(Scanner file) {
        super.scan(file);
        exerciseType = 2;
        kg = file.nextInt();
        count = file.nextInt();
    }
    @Override
    public void print() {
        super.print();
        System.out.printf("%dkg %d번\n", kg, count, cal);
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString() + " " + String.format("%d %d", kg, count));
        return result.toString();
    }
    @Override
    public String toGUIString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toGUIString() + String.format("%dkg * %d번", kg, count));
        return result.toString();
    }

    public void input(String name, int kg, int count) {
        this.name = name;
        this.kg = kg;
        this.count = count;
    }
    @Override
    public Object clone() {
        try {
            Anaerobic cloneAnaerobic = new Anaerobic();
            cloneAnaerobic.name = name;
            cloneAnaerobic.exerciseType = exerciseType;
            cloneAnaerobic.kg = kg;
            cloneAnaerobic.count = count;

            return cloneAnaerobic;
        } catch (Exception e) {
            return null;
        }
    }

}