package Server.GUI;

import Server.Facade.DataEngineImpl;
import Server.Program;
import Server.UserData;

public class UserDataGUIManager extends DataEngineImpl<UserData> {
    private static UserDataGUIManager engine = null;
    private UserDataGUIManager() { setLabels(header); }

    public static UserDataGUIManager getInstance() {
        if (engine == null)
            engine = new UserDataGUIManager();
        return engine;
    }
    private String[] header = { "user type","nickname" };
}
