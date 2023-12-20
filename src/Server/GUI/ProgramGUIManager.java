package Server.GUI;

import Server.Facade.DataEngineImpl;
import Server.Program;

public class ProgramGUIManager extends DataEngineImpl<Program> {
    private static ProgramGUIManager engine = null;
    private ProgramGUIManager() { setLabels(header); }

    public static ProgramGUIManager getInstance() {
        if (engine == null)
            engine = new ProgramGUIManager();
        return engine;
    }
    private String[] header = {"name","date","start time","end time"};
}
