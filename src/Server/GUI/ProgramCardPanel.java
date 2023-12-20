package Server.GUI;

import Server.Program;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


public class ProgramCardPanel extends JPanel {
    HashMap<String, ProgramCustomPage> programHashMap = new HashMap<>();

    void setup(JPanel cardPanel, CardLayout programCards, String keyword) {
        for (Program program : ProgramGUIManager.getInstance().dataList) {
            if (!program.matches(keyword))
                continue;

            if (programHashMap.get(program.name) == null)
                programHashMap.put(program.name,
                        new ProgramCustomPage(program.name, programCards, cardPanel, keyword, program.price)
                );
            programHashMap.get(program.name).add(program);
        }

        JScrollPane programMainPanel = createProgramCustomPage("프로그램 메인화면");
        cardPanel.add(programMainPanel, "프로그램 메인화면");

        for (String programName : programHashMap.keySet()) {
            JPanel detailPanel = createProgramDetailPage(programName, programCards, cardPanel);
            cardPanel.add(detailPanel, programName);
        }
    }

    private JScrollPane createProgramCustomPage(String panelName) {

        Box containerBox = Box.createVerticalBox();

        for (ProgramCustomPage programCustomPage : programHashMap.values())
            containerBox.add(programCustomPage);

        JScrollPane scrollPane = new JScrollPane(containerBox);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 비활성화
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //int visiblePages = 4;

        //이게 스크롤 속도 조절하는 부분.
        scrollPane.getVerticalScrollBar().setUnitIncrement(containerBox.getComponent(0).getPreferredSize().height / 8);
        //scrollPane.getVerticalScrollBar().setBlockIncrement(visiblePages * containerBox.getComponent(0).getPreferredSize().height);
        return scrollPane;
    }
    private JPanel createProgramDetailPage(String panelName, CardLayout programCards, JPanel programPane) {
        JPanel panel = new JPanel(new BorderLayout());
        ProgramDetailPage detailPage = new ProgramDetailPage();
        detailPage.setup(panelName, programHashMap.get(panelName), programCards, programPane);
        panel.add(detailPage);
        return panel;
    }
}