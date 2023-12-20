package Server;

import java.util.Scanner;

public class Cardio extends Exercise {
    int speed;
    int time;

    public Cardio() {
        exerciseType = 1;
    }

    @Override

    public void scan(Scanner file) {
        super.scan(file);
        exerciseType = 1;
        speed = file.nextInt();
        time = file.nextInt();
    }

    @Override
    public void print() {
        super.print();
        System.out.printf("%dkm/h %d분\n", speed, time, cal);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString() + " " + String.format("%d %d", speed, time));
        return result.toString();
    }

    @Override
    public String toGUIString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toGUIString() + String.format("%dkm/h * %d분", speed, time));
        return result.toString();
    }

    public void input(String name, int speed, int time) {
        this.name = name;
        this.speed = speed;
        this.time = time;
    }

    @Override
    public Object clone() {
        try {
            Cardio cloneCardio = new Cardio();
            cloneCardio.name = name;
            cloneCardio.exerciseType = exerciseType;
            cloneCardio.speed = speed;
            cloneCardio.time = time;

            return cloneCardio;
        } catch (Exception e) {
            return null;
        }
    }
}
