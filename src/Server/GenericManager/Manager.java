package Server.GenericManager;

import Server.ServerComputer;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager <T extends Data> implements Cloneable {
    public ArrayList<T> dataList = new ArrayList<>();
    String relativePath;
    public Manager() {

    }

    public Manager(String relativePath, Factory<T> factory) {
        this.relativePath = relativePath;
        scanAll(relativePath, factory);
    }
    public void scan(Scanner file, Factory<T> factory) {
        T data = factory.create();
        data.scan(file);
        dataList.add(data);
    }
    public void scanAll(String relativePath, Factory<T> factory) {
        this.relativePath = relativePath;
        Scanner file = ServerComputer.openFile(relativePath);
        while (file.hasNext()) {
            T data = factory.create();
            data.scan(file);
            dataList.add(data);
        }
    }
    public void printAll() {
        for (T data : dataList)
            data.print();
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T data : dataList)
            result.append(data + "\n");

        if(!result.isEmpty())
            result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public T find(String keyword) {
        for (T data : dataList) {
            if(data.matches(keyword))
                return data;
        }
        return null;
    }
    public ArrayList<T> findAll(String keyword) {
        ArrayList<T> result = new ArrayList<>();
        for (T data : dataList) {
            if(data.matches(keyword))
                result.add(data);
        }
        return result;
    }

    public void saveFile() {
        String absolutePath = ServerComputer.getAbsolutePath(relativePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(absolutePath);
            fileWriter.write(toString());
            fileWriter.close();
        } catch (Exception e) {
            System.out.printf("파일 오픈 실패: %s\n", absolutePath);
            System.exit(0);
        }
    }
    @Override
    public Object clone() {
        Manager<T> cloneManager = new Manager<>();
        for (T data : dataList) {
            T cloneData = (T)data.clone();
            cloneManager.dataList.add(cloneData);
        }
        return cloneManager;
    }
}
