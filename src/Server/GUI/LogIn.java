package Server.GUI;

import Server.ServerComputer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;

public class LogIn extends JPanel {
    private JPanel logInPane;
    private JTextField idField;
    private JPasswordField passwordField;
    private JLabel imageLabel;

    void setupLogInPage(JPanel cardPanel, CardLayout startCards) {
        logInPane = new JPanel(new BorderLayout());
        Font font = new Font("맑은 고딕", Font.PLAIN, 15);

        TopBanner topBanner = new TopBanner();
        logInPane.add(topBanner, BorderLayout.NORTH);

        JPanel informationEnterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel idLabel = new JLabel("아이디 : ");
        idLabel.setFont(font);
        idField = new JTextField();
        idField.setColumns(30);
        idPanel.add(idLabel);
        idPanel.add(idField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel passwordLabel = new JLabel("비밀번호 : ");
        passwordLabel.setFont(font);
        passwordField = new JPasswordField();
        passwordField.setColumns(30);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton logInButton = new JButton("로그인");
        logInButton.setBackground(Color.BLACK);
        logInButton.setForeground(Color.WHITE);
        logInButton.setFont(font);
        JButton joinButton = new JButton("회원가입");
        joinButton.setBackground(Color.BLACK);
        joinButton.setForeground(Color.WHITE);
        joinButton.setFont(font);
        buttonPanel.add(logInButton);
        buttonPanel.add(joinButton);

        String myInfoPath = "..\\Client\\my-info.txt";
        if (new File(ServerComputer.getAbsolutePath(myInfoPath)).exists()) {
            Scanner file = ServerComputer.openFile(myInfoPath);
            idField.setText(file.next());
            passwordField.setText(file.next());

        }

        informationEnterPanel.add(idPanel);
        informationEnterPanel.add(passwordPanel);
        informationEnterPanel.add(buttonPanel);

        logInPane.add(informationEnterPanel, BorderLayout.CENTER);

        String impagePath = ServerComputer.getAbsolutePath("data\\icon\\헬스장내부사진.png");
        Image image = new ImageIcon(impagePath).getImage().getScaledInstance(650,600, Image.SCALE_SMOOTH);
        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));

        logInPane.add(imageLabel, BorderLayout.SOUTH);

        cardPanel.add(logInPane, "로그인 페이지");
        logInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tryLogIn(cardPanel, startCards);
            }
        });

        joinButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startCards.show(cardPanel, "회원가입 페이지");
            }
        });
    }

    void tryLogIn(JPanel cardPanel, CardLayout startCards) {
        if (idField.getText().contentEquals("")
                || passwordField.getText().contentEquals("")) {
            JOptionPane.showMessageDialog(logInPane, "올바른 입력이 아닙니다.");
            return;
        }

        int logInType = ServerComputer.getAccessType(new String[]{ idField.getText(),passwordField.getText() });
        if (logInType == -1) {
            JOptionPane.showMessageDialog(logInPane, "등록된 회원이 아닙니다.");
            return;
        } else if (logInType == 1) {
            JOptionPane.showMessageDialog(logInPane, "존재하지 않는 게정입니다.");
            return;
        } else if (logInType == 2) {
            JOptionPane.showMessageDialog(logInPane, "비밀번호가 맞지 않습니다.");
            return;
        }
        GUIMain.me = ServerComputer.getUser(idField.getText());
        ServerComputer.save();

        GUIMain.cardPanel.removeAll();

        GUIMain.loginedCard = new LogedIn();
        GUIMain.loginedCard.createAndShowGUI(cardPanel);
        startCards.show(cardPanel, "로그인후 페이지");
    }
}
