package Server;

import Server.GenericManager.Data;
import Server.GenericManager.Manager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class User implements Data {
    int userType;

    String id; // id가 기본키다.
    String password;

    String name;
    String nickname;
    String phone;
    int gender; // 남자 0, 여자 1

    Manager<ExerciseLog> myExerciseLogManager = new Manager<>();
    Manager<Inbody> myInbodyManager = new Manager<>();
    Manager<Program> myProgramManager = new Manager<>();

    public HashMap<String, Integer> rankValue = new HashMap<>();

    public void scan(String id, String password, String name, String nickname, String phone, String gender) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.gender = (gender.contentEquals("남성") ? 0 : 1);

        ServerComputer.userHashMap.put(id, this);
        ServerComputer.rankingSystem.addUser(this);
    }
    @Override
    public void scan(Scanner file) {
        id = file.next();
        password = file.next();
        name = file.next();
        nickname = file.next();
        phone = file.next();
        gender = file.nextInt();

        ServerComputer.userHashMap.put(id, this);
        ServerComputer.rankingSystem.addUser(this);
    }
    @Override
    public void print() {
        System.out.printf("[%s] %s %s %s %s %s %s\n",
                (userType == 1 ? "일반 회원" : "트레이너"), id, password,
                name, nickname, phone, (gender == 0 ? "남성" : "여성"));
    }
    @Override
    public String toString() {
        return String.format("%d %s %s %s %s %s %d",
                userType, id, password, name, nickname, phone, gender);
    }
    @Override
    public String toGUIString() {
        return String.format("[%s] %s %s %s %s %s %s",
                (userType == 1 ? "일반 회원" : "트레이너"), id, password,
                name, nickname, phone, (gender == 0 ? "남성" : "여성"));
    }
    @Override
    public boolean matches(String keyword) {
        if(id.contains(keyword) || name.contains(keyword) || nickname.contains(keyword))
            return true;
        if(phone.contains(keyword) || (gender == 0 ? "남자" : "여자").contains(keyword))
            return true;
        return false;
    }

    void printMyInfo() {
        printMyExerciseLog();
        printMyInbodyLog();
        printMyProgram();
    }
    void printMyExerciseLog() {
        System.out.printf("%s 회원님의 운동 기록\n", nickname);
        myExerciseLogManager.printAll();
    }
    void printMyInbodyLog() {
        System.out.printf("%s 회원님의 인바디 기록\n", nickname);
        myInbodyManager.printAll();
    }
    public void printMyProgram(){
        System.out.printf("%s 회원님의 프로그램\n", nickname);
        myProgramManager.printAll();
    }
    public String getInfo(String need) {
        if(need.contentEquals("id"))
            return id;
        if(need.contentEquals("name"))
            return name;
        if(need.contentEquals("nickname"))
            return nickname;
        return null;
    }

    public int getBig3() {
        int squat = 0;
        int benchPress = 0;
        int deadLift = 0;

        for (ExerciseLog exerciseLog : myExerciseLogManager.dataList) {
            if(!ServerComputer.isThisMonth(exerciseLog.logDate))
                continue;

            for (Exercise exercise : exerciseLog.exerciseManager.dataList) {
                if (exercise.name.contentEquals("스쿼트"))
                    squat = Math.max(squat, ((Anaerobic)exercise).kg);
                if (exercise.name.contentEquals("벤치프레스"))
                    benchPress = Math.max(squat, ((Anaerobic)exercise).kg);
                if (exercise.name.contentEquals("데드리프트"))
                    deadLift = Math.max(squat, ((Anaerobic)exercise).kg);
            }
        }
        int result = squat + benchPress + deadLift;
        rankValue.put("3대왕", result);
        return result;
    }
    public int getPowerUp() {
        int firstMuscle = -1;
        int lastMuscle = 0;

        for (Inbody inbody : myInbodyManager.dataList) {
            if(!ServerComputer.isThisMonth(inbody.date))
                continue;

            if (firstMuscle == -1)
                firstMuscle = inbody.muscle;
            lastMuscle = inbody.muscle;
        }

        int result = (firstMuscle == -1 ? 0 : lastMuscle - firstMuscle);
        rankValue.put("득근왕", result);
        return result;
    }
    public int getBurnFat() {
        int firstFat = -1;
        int lastFat = 0;

        for (Inbody inbody : myInbodyManager.dataList) {
            if(!ServerComputer.isThisMonth(inbody.date))
                continue;

            if (firstFat == -1)
                firstFat = inbody.fat;
            lastFat = inbody.fat;
        }

        int result = (firstFat == -1 ? 0 : firstFat - lastFat);
        rankValue.put("연소왕", result);
        return result;
    }
    public int getSincerity() {
        ArrayList<Boolean> isVisited = new ArrayList<>(32);
        for (int i = 0; i < 32; i++)
            isVisited.add(false);

        for (ExerciseLog exerciseLog : myExerciseLogManager.dataList)
        {
            if(ServerComputer.isThisMonth(exerciseLog.logDate))
                isVisited.set(Integer.parseInt(exerciseLog.logDate.substring(8, 10)), true);
        }
        for (Inbody inbody : myInbodyManager.dataList)
        {
            if(ServerComputer.isThisMonth(inbody.date))
                isVisited.set(Integer.parseInt(inbody.date.substring(8, 10)), true);
        }

        int result = 0;
        for (int i = 1; i <= 31; i++) {
            if(isVisited.get(i))
                result++;
        }
        rankValue.put("출석왕", result);
        return result;
    }
    public int getLastWeight() {
        int size = myInbodyManager.dataList.size();
        if(size == 0)
            return -1;

        return myInbodyManager.dataList.get(size - 1).weight;
    }
    public Program getNextProgram(boolean isPT) {
        LocalDateTime now = LocalDateTime.now();
        String nowTime = ServerComputer.convertDate(now.getDayOfWeek().toString()) + now.getHour() + ":" + now.getMinute();
        for (Program program : myProgramManager.dataList) {
            if(program.isPT != isPT)
                continue;

            if (ServerComputer.compareDate(nowTime, program.getStartDate()) <= 0) { // 현재 시간이 프로그램 시작시간보다 작다면
                return program;
            }
            if (ServerComputer.compareDate(nowTime, program.getStartDate()) >= 0 && ServerComputer.compareDate(nowTime, program.getEndDate()) <= 0) { // 현재 프로그램이 진행중이라면
                return program;
            }
        }

        for (Program program : myProgramManager.dataList) { // 지금 이후에 수업이 없다면 다음주 첫수업을 바로 return한다.
            if(program.isPT != isPT)
                continue;

            return program;
        }
        return null; // 등록한 프로그램이 없다.
    }
    public ExerciseLog getLastExerciseLog() {
        return (myExerciseLogManager.dataList.size() != 0 ? myExerciseLogManager.dataList.get(myExerciseLogManager.dataList.size() - 1) : null);
    }

    public void participateProgram(Program program) {
        myProgramManager.dataList.add(program);
    }
    public void cancelProgram(Program program) {
        myProgramManager.dataList.remove(program);
    }
    public void sortProgram() {
        Collections.sort(myProgramManager.dataList);
    }
    public boolean isAbleToParticipateProgram(Program program) {
        for (Program myProgram : myProgramManager.dataList) {
            if(ServerComputer.compareDate(myProgram.getStartDate(), program.getStartDate()) <= 0
                    && ServerComputer.compareDate(program.getStartDate(), myProgram.getEndDate()) < 0)
                return false;

            if(ServerComputer.compareDate(myProgram.getStartDate(), program.getEndDate()) < 0
                    && ServerComputer.compareDate(program.getEndDate(), myProgram.getEndDate()) <= 0)
                return false;
        }
        return true;
    }
    public Program overlappedProgram(Program program) {
        for (Program myProgram : myProgramManager.dataList) {
            if(ServerComputer.compareDate(myProgram.getStartDate(), program.getStartDate()) <= 0
                    && ServerComputer.compareDate(program.getStartDate(), myProgram.getEndDate()) < 0)
                return myProgram;

            if(ServerComputer.compareDate(myProgram.getStartDate(), program.getEndDate()) < 0
                    && ServerComputer.compareDate(program.getEndDate(), myProgram.getEndDate()) <= 0)
                return myProgram;
        }
        return null;
    }

    public Manager<ExerciseLog> getExerciseLogManager() {
        return myExerciseLogManager;
    }
    public Manager<Inbody> getInbodyManager() {
        return myInbodyManager;
    }
    public Manager<Program> getProgramManager() {
        return myProgramManager;
    }

    @Override
    public Object clone() {
        return null;
    }
}