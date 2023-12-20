package Server.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class RankingCardPanel extends JPanel{
    void setup(JPanel cardPanel){
        JPanel muscleKingPanel = createRankingPanel("득근왕");
        JPanel big3KingPanel = createRankingPanel("3대왕");
        JPanel attendanceKingPanel = createRankingPanel("출석왕");
        JPanel slimKingPanel = createRankingPanel("연소왕");

        cardPanel.add(muscleKingPanel, "득근왕");
        cardPanel.add(big3KingPanel, "3대왕");
        cardPanel.add(attendanceKingPanel, "출석왕");
        cardPanel.add(slimKingPanel, "연소왕");

    }

    private JPanel createRankingPanel(String rankingName) {
        RankingPage rankingpage = new RankingPage();
        rankingpage.setup(rankingName);
        return rankingpage;
    }

}