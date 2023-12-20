package Server.GUI;

import Server.User;

import javax.swing.*;
import java.awt.*;

public class GUIMain {
    static public User me;

    private static GUIMain guiMain = null;
    static JFrame mainFrame = new JFrame("3대50 헬스장");

    private GUIMain(){}
    public static GUIMain getInstance(){
        if (guiMain == null)
            guiMain = new GUIMain();
        return guiMain;
    }

    public static void startGUI(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUIMain.getInstance().setupStartPane();
            }
        });
    }

    private JPanel startPane;
    private CardLayout startCards;
    static JPanel cardPanel;
    LogIn loginCard = new LogIn();
    static LogedIn loginedCard;
    Join joinCard = new Join();
    private void setupStartPane(){
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startPane = new JPanel(new BorderLayout());
        startCards = new CardLayout();
        cardPanel = new JPanel(startCards);


        loginCard.setupLogInPage(cardPanel, startCards);
        joinCard.setupJoinPage(cardPanel, startCards);
        startPane.add(cardPanel, BorderLayout.CENTER);

        mainFrame.getContentPane().add(startPane);
        mainFrame.pack();
        mainFrame.setSize(650, 1000);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
    }

    public void updateCard(){
        if (loginedCard != null) {
            cardPanel.remove(loginedCard.topTab);
            loginedCard.updateCard(cardPanel);
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}