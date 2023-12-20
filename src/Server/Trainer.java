package Server;

import Server.GenericManager.Data;

import java.util.*;

public class Trainer extends User implements Data {
    ArrayList<String> awards = new ArrayList<>();
    int n;

    public Trainer() {
        userType = 2;
    }

    @Override

    public void scan(Scanner file) {
        super.scan(file);
        userType = 2;

        n = file.nextInt();
        for (int i = 0; i < n; i++) {
            awards.add(file.next());
        }

        Program.trainerHashMap.put("PT-" + name, id);
    }

    @Override
    public void print() {
        super.print();

        if (!awards.isEmpty())
            System.out.print("\t");

        for (String award : awards)
            System.out.print(award + " ");

        if (!awards.isEmpty())
            System.out.println();
        printMyInfo();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString() + String.format(" %d ", n));
        for (String award : awards)
            result.append(award + " ");
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}