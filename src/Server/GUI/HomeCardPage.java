package Server.GUI;

import Server.ExerciseLog;
import Server.GenericManager.Data;
import Server.Inbody;
import Server.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HomeCardPage extends JPanel {
    void setup(String cardName,JPanel cardPanel, CardLayout homeCards){
        Font middleFont = new Font("맑은 고딕", Font.BOLD, 30);
        Font bigFont = new Font("맑은 고딕", Font.BOLD, 40);
        setLayout(new BorderLayout());
        JLabel cardNameLabel = new JLabel(cardName, SwingConstants.CENTER);
        cardNameLabel.setPreferredSize(new Dimension(500,50));
        cardNameLabel.setOpaque(true);
        cardNameLabel.setBackground(Color.GRAY);
        cardNameLabel.setForeground(Color.BLACK);
        cardNameLabel.setFont(bigFont);

        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        ArrayList<Data> dataList = getDataList(cardName);

        for (Data data : dataList) {
            JLabel label = new JLabel(data.toGUIString(), SwingConstants.LEFT);
            label.setFont(middleFont);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);//이거 없으면 boxlayout때문에 가운데 정렬이 안돼.
            dataPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(dataPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 비활성화
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonPanel = new JPanel();

        JButton goBackButton = new JButton("뒤로가기");
        goBackButton.setFont(middleFont);
        goBackButton.setPreferredSize(new Dimension(200,70));
        goBackButton.setBackground(Color.BLACK);
        goBackButton.setForeground(Color.WHITE);
        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                homeCards.show(cardPanel, "홈 화면");
            }
        });
        buttonPanel.add(goBackButton);

        add(cardNameLabel, BorderLayout.NORTH);
        // Jtable 만들어서 add
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    ArrayList<Data> getDataList(String cardName) {
        ArrayList<Data> dataList = new ArrayList<>();
        if (cardName.contentEquals("내 프로그램")) {
            for (Program program : GUIMain.me.getProgramManager().dataList) {
                if (!program.isPT()) {
                    dataList.add(program);
                }
            }
        } else if (cardName.contentEquals("내 PT")) {
            for (Program program : GUIMain.me.getProgramManager().dataList) {
                if(program.isPT())
                    dataList.add(program);
            }
        } else if (cardName.contentEquals("내 인바디")) {
            for (int i = GUIMain.me.getInbodyManager().dataList.size() - 1; i >= 0; i--)
                dataList.add(GUIMain.me.getInbodyManager().dataList.get(i));
        } else if (cardName.contentEquals("내 운동기록")) {
            for (int i = GUIMain.me.getExerciseLogManager().dataList.size() - 1; i >= 0; i--)
                dataList.add(GUIMain.me.getExerciseLogManager().dataList.get(i));
        }
        return dataList;
    }
}
