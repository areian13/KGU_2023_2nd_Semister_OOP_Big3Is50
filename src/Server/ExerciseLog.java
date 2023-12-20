package Server;

import Server.GUI.GUIMain;
import Server.GenericManager.Data;
import Server.GenericManager.Manager;

import javax.swing.text.html.HTML;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ExerciseLog implements Data, Cloneable {
    String logDate;
    String logTime;
    String userID;
    int n;

    Manager<Exercise> exerciseManager = new Manager<>();

    @Override
    public void scan(Scanner file) {
        logDate = file.next();
        logTime = file.next();
        userID = file.next();

        n = file.nextInt();
        for (int i = 0; i < n; i++) {
            int exerciseType = file.nextInt();
            exerciseManager.scan(file, () -> (exerciseType == 1 ? new Cardio() : new Anaerobic()));
        }

        User user = ServerComputer.userHashMap.get(userID);
        user.myExerciseLogManager.dataList.add(this);
    }
    @Override
    public void print() {
        System.out.printf("[%s] %s %s\n", logDate, logTime, userID);
        exerciseManager.printAll();
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%s %s %s %d", logDate, logTime, userID, n) + " ");
        for (Exercise exercise : exerciseManager.dataList)
            result.append(exercise + " ");
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
    @Override
    public String toGUIString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("<html>[%s] %s<br>", logDate, logTime));
        for (Exercise exercise : exerciseManager.dataList) {
            result.append(exercise.toGUIString() + "<br>");
        }
        result.append("<br></html>");
        return result.toString();
    }
    @Override
    public boolean matches(String keyword) {
        if(logDate.contains(keyword) || logTime.contains(keyword))
            return true;

        User user = ServerComputer.userHashMap.get(userID);
        return user.matches(keyword);
    }

    public void addExercise(Exercise exercise) {
        exerciseManager.dataList.add(exercise);
        n++;
    }
    public void deleteExercise(Exercise exercise) {
        exerciseManager.dataList.remove(exercise);
        n--;
    }
    public void setLog() {
        LocalDateTime now = LocalDateTime.now();
        logDate = now.toLocalDate().toString();
        logTime = now.toLocalTime().toString().substring(0, 8);
        userID = GUIMain.me.id;
    }
    @Override
    public Object clone() {
        try {
            ExerciseLog cloneExerciseLog = new ExerciseLog();
            cloneExerciseLog.logDate = logDate;
            cloneExerciseLog.logTime = logTime;
            cloneExerciseLog.userID = userID;
            cloneExerciseLog.exerciseManager = (Manager<Exercise>) exerciseManager.clone();
            cloneExerciseLog.n = n;
            return cloneExerciseLog;
        } catch (Exception e) {
            return null;
        }
    }
    public String getDate() {
        return logDate;
    }
}
