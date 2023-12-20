package Server.GUI;

import Server.Facade.DataEngineImpl;
import Server.Inbody;
import Server.Program;

public class InbodyGUIManager extends DataEngineImpl<Inbody> {
    private static InbodyGUIManager engine = null;
    private InbodyGUIManager() { setLabels(header); }

    public static InbodyGUIManager getInstance() {
        if (engine == null)
            engine = new InbodyGUIManager();
        return engine;
    }
    private String[] header = { "date","time","nickname" };
}
