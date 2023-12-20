package Server.GUI;

import javax.swing.*;
import java.awt.*;

public class HomeCardPanel extends JPanel {
    void setup(JPanel cardPanel, CardLayout homeCards){
        JPanel myInbody = createCard("내 인바디", cardPanel, homeCards);
        JPanel myExerciseLog = createCard("내 운동기록", cardPanel, homeCards);
        JPanel myProgram = createCard("내 프로그램", cardPanel, homeCards);
        JPanel myTrainer = createCard("내 PT", cardPanel, homeCards);

        JPanel enterExerciseLog = createEnterCard("운동기록 추가", cardPanel, homeCards);
        JPanel enterInbodyLog = createEnterCard("인바디기록 추가", cardPanel, homeCards);

        cardPanel.add(myInbody, "내 인바디");
        cardPanel.add(myExerciseLog, "내 운동기록");
        cardPanel.add(myProgram, "내 프로그램");
        cardPanel.add(myTrainer, "내 PT");
        cardPanel.add(enterExerciseLog, "운동기록 추가");
        cardPanel.add(enterInbodyLog, "인바디기록 추가");
    }

    private JPanel createCard(String cardName, JPanel cardPanel, CardLayout homeCards){
        HomeCardPage homeCardPage = new HomeCardPage();
        homeCardPage.setup(cardName, cardPanel, homeCards);
        return homeCardPage;
    }

    private JPanel createEnterCard(String cardName, JPanel cardPanel, CardLayout homeCards){
        HomeEnterLogPage homeEnterLogPage = new HomeEnterLogPage();
        homeEnterLogPage.setup(cardName, cardPanel, homeCards);
        return homeEnterLogPage;
    }

}
