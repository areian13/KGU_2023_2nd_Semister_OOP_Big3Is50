package Server.GUI;

import javax.swing.*;
import java.awt.*;

public class RankingBottomPanel extends JPanel {
    void setup(CardLayout cards, JPanel cardPanel){
        Font middleFont = new Font("맑은 고딕", Font.BOLD, 20);
        JPanel bottomButtonPanel = new JPanel(new GridLayout(1,4));

        JButton muscleKingButton = new JButton("득근왕");
        muscleKingButton.setPreferredSize(new Dimension(100,30));
        muscleKingButton.setFont(middleFont);
        muscleKingButton.setBackground(Color.pink);
        muscleKingButton.setForeground(Color.BLACK);
        JButton big3KingButton = new JButton("3대왕");
        big3KingButton.setPreferredSize(new Dimension(100,30));
        big3KingButton.setFont(middleFont);
        big3KingButton.setBackground(Color.pink);
        big3KingButton.setForeground(Color.BLACK);
        JButton attendanceKingButton = new JButton("출석왕");
        attendanceKingButton.setPreferredSize(new Dimension(100,30));
        attendanceKingButton.setFont(middleFont);
        attendanceKingButton.setBackground(Color.pink);
        attendanceKingButton.setForeground(Color.BLACK);
        JButton slimKingButton = new JButton("연소왕");
        slimKingButton.setPreferredSize(new Dimension(100,30));
        slimKingButton.setFont(middleFont);
        slimKingButton.setBackground(Color.pink);
        slimKingButton.setForeground(Color.BLACK);


        bottomButtonPanel.add(muscleKingButton);
        bottomButtonPanel.add(big3KingButton);
        bottomButtonPanel.add(attendanceKingButton);
        bottomButtonPanel.add(slimKingButton);

        add(bottomButtonPanel, BorderLayout.PAGE_END);

        muscleKingButton.addActionListener(e -> showPanel("득근왕", cards, cardPanel));
        big3KingButton.addActionListener(e -> showPanel("3대왕", cards, cardPanel));
        attendanceKingButton.addActionListener(e -> showPanel("출석왕", cards, cardPanel));
        slimKingButton.addActionListener(e -> showPanel("연소왕", cards, cardPanel));
    }


    private void showPanel(String panelName, CardLayout cards, JPanel cardPanel) {
        //cardPanel.show((Container) rankingPane.getComponent(1), panelName);
        cards.show(cardPanel, panelName);
    }
}