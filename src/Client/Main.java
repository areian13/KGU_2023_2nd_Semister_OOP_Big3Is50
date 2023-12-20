package Client;

import Server.ServerComputer;
import Server.GUI.GUIMain;

public class Main {
    public static void main(String[] args) {
        ServerComputer program = new ServerComputer();
        program.run();
        GUIMain.startGUI();
        program.close();
    }
}
