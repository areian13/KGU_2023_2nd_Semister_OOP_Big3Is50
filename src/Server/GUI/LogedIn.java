package Server.GUI;

import javax.swing.*;
import java.awt.*;

public class LogedIn extends JPanel {
    private static LogedIn logedIn = null;
    public static LogedIn getInstance(){
        if(logedIn == null)
            logedIn = new LogedIn();
        return logedIn;
    }

    public void updateCard(JPanel cardPanel){
        createAndShowGUI(cardPanel);
    }

    JTabbedPane topTab;
    void createAndShowGUI(JPanel cardPanel) {
        // 탭을 생성하고 home, ranking, program, trainer
        topTab = new JTabbedPane();

        setupHomePane();
        setupRankingPane();
        setupProgramPane();
        setupTrainerPane();

        topTab.add("홈", homePane);
        topTab.add("랭킹", rankingPane);
        topTab.add("프로그램", programPane);
        topTab.add("트레이너", trainerPane);

        cardPanel.add(topTab, "로그인후 페이지");
    }

    private JPanel homePane;
    private CardLayout homeCards;
    JPanel hcard;
    HomePage homePage;
    HomeCardPanel homeCardPanel;
    private void setupHomePane(){
        homePane = new JPanel(new BorderLayout());
        homeCards = new CardLayout();
        hcard = new JPanel(homeCards);

        homeCardPanel = new HomeCardPanel();
        homePage = new HomePage();
        //"홈 화면" , "내 운동기록", "내 골격근량", "내 체지방", "내 체중", "내 트레이너", "내 프로그램"
        homePage.setup(hcard, homeCards);
        homeCardPanel.setup(hcard, homeCards);
        homePane.add(hcard, BorderLayout.CENTER);
    }

    private JPanel rankingPane;
    private CardLayout rankingCards;
    private JPanel rcard;
    RankingCardPanel rankingCardPanel;
    RankingBottomPanel rankingBottomPanel;
    private void setupRankingPane(){
        rankingPane = new JPanel(new BorderLayout());
        rankingCards = new CardLayout();
        rankingCardPanel = new RankingCardPanel();
        rankingBottomPanel = new RankingBottomPanel();
        rcard = new JPanel(rankingCards);
        rankingCardPanel.setup(rcard);
        rankingBottomPanel.setup(rankingCards, rcard);

        rankingPane.add(rankingBottomPanel, BorderLayout.SOUTH);
        rankingPane.add(rcard, BorderLayout.CENTER);
    }

    private JPanel programPane;
    private CardLayout programCards;
    private JPanel pcard;
    ProgramCardPanel programCardPanel;

    private void setupProgramPane(){
        programPane = new JPanel(new BorderLayout());
        programCards = new CardLayout();
        programCardPanel = new ProgramCardPanel();
        pcard = new JPanel(programCards);

        programCardPanel.setup(pcard, programCards, "!pt");

        programPane.add(pcard, BorderLayout.CENTER);
    }

    private JPanel trainerPane;
    private CardLayout trainerCards;
    private JPanel tcard;
    ProgramCardPanel trainerCardPanel;
    private void setupTrainerPane(){
        trainerPane = new JPanel(new BorderLayout());
        trainerCards = new CardLayout();
        tcard = new JPanel(trainerCards);
        trainerCardPanel = new ProgramCardPanel();
        trainerCardPanel.setup(tcard, trainerCards, "pt");

        trainerPane.add(tcard, BorderLayout.CENTER);
    }
    public void reRankUp() {
        setupRankingPane();
    }
}