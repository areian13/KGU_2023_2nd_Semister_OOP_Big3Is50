package Server;

import Server.GenericManager.Manager;
import Server.GUI.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class ServerComputer {
    static Scanner scanner = new Scanner(System.in);

    static String today = LocalDate.now().toString();

    static HashMap<String, User> userHashMap = new HashMap<>();
    static Ranking rankingSystem = new Ranking();

    //Me me = new Me("..\\Client\\my-info.txt");

    public ServerComputer() {
        startProgram();
    }
    public void run() {
        runProgram();
    }
    public void close() {
        finishProgram();
    }
    void startProgram() {
        UserDataGUIManager.getInstance().scanAll("data\\user-data.txt", () -> new UserData());
        ExerciseLogGUIManager.getInstance().scanAll("data\\user-exercise-log.txt", () -> new ExerciseLog());
        InbodyGUIManager.getInstance().scanAll("data\\user-inbody.txt", () -> new Inbody());
        ProgramGUIManager.getInstance().scanAll("data\\program-data.txt", () -> new Program());
        rankingSystem.sort();
        userProgramSort();
    }
    void runProgram() {
        boolean isRunning = true;
        /*while (isRunning) {
            System.out.println("1. 유저 출력  2. 운동 기록 출력");
            System.out.println("3. 인바디 기록 출력  4. 프로그램 출력");
            System.out.println("5. 이 달의 랭킹  0. 종료");

            int option = Integer.parseInt(input("번호를"));
            switch (option) {
                case 1 -> userDataManager.printAll();
                case 2 -> exerciseLogManager.printAll();
                case 3 -> inbodyManager.printAll();
                case 4 -> programManager.printAll();
                case 5 -> rankingSystem.print();
                case 0 -> isRunning = false;
            }
        }*/
    }
    void finishProgram() {
        save();
    }

    static String input(String comment) {
        System.out.print(comment + " 입력하십시오 > ");
        String str = scanner.next();
        return str;
    }
    static boolean isThisMonth(String date) {
        return today.substring(0, 7).contentEquals(date.substring(0, 7));
    }
    public static String getAbsolutePath(String relativePath) {
        return System.getProperty("user.dir") + "\\src\\Server\\" + relativePath;
    }
    public static Scanner openFile(String relativePath) {
        String absolutePath = getAbsolutePath(relativePath);
        Scanner file = null;
        try {
            file = new Scanner(new File(absolutePath));
        } catch (Exception e) {
            System.out.printf("파일 오픈 실패: %s\n", absolutePath);
            System.exit(0);
        }
        return file;
    }

    public static int getAccessType(String[] keys) {
        if(keys.length != 2 || keys[0].contentEquals("") || keys[1].contentEquals(""))
            return -1;

        User user = userHashMap.get(keys[0]);
        if(user == null)
            return 1;
        if(!user.password.contentEquals(keys[1]))
            return 2;
        return 0;
    }

    public static User getUser(String id) {
        return userHashMap.get(id);
    }
    public static boolean isExistedUserNickname(String nickname) {
        for (User user : userHashMap.values()) {
            if(user.nickname.contentEquals(nickname))
                return true;
        }
        return false;
    }
    public static void save() {
        rankingSystem.sort();

        if (GUIMain.me != null) {
            String absolutePath = ServerComputer.getAbsolutePath("..\\Client\\my-info.txt");
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(absolutePath);
                fileWriter.write(GUIMain.me.id + "\n" + GUIMain.me.password);
                fileWriter.close();
            } catch (Exception e) {
                System.out.printf("파일 오픈 실패: %s\n", absolutePath);
                System.exit(0);
            }
        }

        UserDataGUIManager.getInstance().saveFile();
        ExerciseLogGUIManager.getInstance().saveFile();
        InbodyGUIManager.getInstance().saveFile();
        ProgramGUIManager.getInstance().saveFile();

        GUIMain.getInstance().updateCard();
    }

    public static Image getImage(String relativePath, boolean isUser, int width, int height, int type) {
        String absolutePath = getAbsolutePath(relativePath);
        if (!new File(absolutePath).exists())
            absolutePath = getAbsolutePath("\\data\\" + (isUser ? "user-image" : "program-image") + "\\no image.png");

        return new ImageIcon(absolutePath).getImage().getScaledInstance(width, height, type);
    }
    static public String convertDate(String engDate) {
        String korDate = "월화수목금토일";
        for (int i = 0; i < 7; i++) {
            if (engDate.contentEquals(DayOfWeek.values()[i].toString())) {
                return "" + korDate.charAt(i);
            }
        }
        return null;
    }

    static public int compareDate(String a, String b) {
        String korDate = "월화수목금토일";
        HashMap<Character, Integer> dateValue = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            dateValue.put(korDate.charAt(i), i);
        }

        if(a.charAt(0) != b.charAt(0))
            return dateValue.get(a.charAt(0)) - dateValue.get(b.charAt(0));

        return a.substring(1, a.length()).compareTo(b.substring(1, b.length()));
    }
    void userProgramSort() {
        for (UserData userData : UserDataGUIManager.getInstance().dataList) {
            for (User user : userData.userManager.dataList)
                user.sortProgram();
        }
    }
}