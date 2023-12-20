package Server.GUI;

import Server.ExerciseLog;
import Server.Facade.DataEngineImpl;
import Server.Program;

public class ExerciseLogGUIManager extends DataEngineImpl<ExerciseLog> {
    private static ExerciseLogGUIManager engine = null;
    private ExerciseLogGUIManager() { setLabels(header); }

    public static ExerciseLogGUIManager getInstance() {
        if (engine == null)
            engine = new ExerciseLogGUIManager();
        return engine;
    }
    private String[] header = { "date","time","nickname" };
}
