package Server.GUI;

import Server.Normal;
import Server.ServerComputer;
import Server.User;
import Server.UserData;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

//1 mingmingmon asdf1234! 전민주 밍밍몬 010-5387-0834 1
public class Join extends JLabel {
    private JPanel joinPane;
    private JTextField idField;
    private JTextField passwordField;
    private JTextField nameField;
    private JTextField nickNameField;
    private JFormattedTextField phoneNumberField;
    private JComboBox<String> genderComboBox;
    private JButton joinInButton;

    void setupJoinPage(JPanel cardPanel, CardLayout startCards){
        joinPane = new JPanel(new BorderLayout());
        Font font = new Font("맑은 고딕", Font.PLAIN, 15);
        Font middleFont = new Font("맑은 고딕", Font.BOLD, 20);
        Font bigFont = new Font("맑은 고딕", Font.BOLD, 30);

        TopBanner topBanner = new TopBanner();
        joinPane.add(topBanner, BorderLayout.NORTH);

        JPanel enterPanel = new JPanel();

        JPanel joinLabelPane = new JPanel();
        JLabel joinLabel = new JLabel("3대 50 헬스장 회원가입 페이지", SwingConstants.CENTER);
        //joinLabel.setPreferredSize(new Dimension(400, 50));
        joinLabel.setBackground(Color.BLACK);
        joinLabel.setFont(bigFont);
        joinLabel.setForeground(Color.WHITE);
        joinLabel.setOpaque(true); // 배경 불투명하게 해야 배경색 보임
        joinLabelPane.add(joinLabel);

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel idLabel = new JLabel("아이디 : ");
        idLabel.setFont(middleFont);
        idField = new JTextField();
        idField.setColumns(20);
        idField.setFont(middleFont);
        idPanel.add(idLabel);
        idPanel.add(idField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel passwordLabel = new JLabel("비밀번호 : ");
        passwordLabel.setFont(middleFont);
        passwordField = new JPasswordField();
        passwordField.setFont(middleFont);
        passwordField.setColumns(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel nameLabel = new JLabel("성명 : ");
        nameLabel.setFont(middleFont);
        nameField = new JTextField();
        nameField.setColumns(20);
        nameField.setFont(middleFont);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JPanel nickNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel nickNameLabel = new JLabel("닉네임 : ");
        nickNameLabel.setFont(middleFont);
        nickNameField = new JTextField();
        nickNameField.setColumns(20);
        nickNameField.setFont(middleFont);
        nickNamePanel.add(nickNameLabel);
        nickNamePanel.add(nickNameField);

        JPanel phoneNumberPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel phoneNumberLabel = new JLabel("전화번호 : ");
        phoneNumberLabel.setFont(middleFont);
        try{
            MaskFormatter phoneFormatter = new MaskFormatter("###-####-####");
            phoneNumberField = new JFormattedTextField(phoneFormatter);
        }catch (ParseException e){
            e.printStackTrace();
        }
        phoneNumberField.setColumns(11);
        phoneNumberField.setFont(middleFont);
        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberPanel.add(phoneNumberField);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel genderLabel = new JLabel("성별 : ");
        genderLabel.setFont(middleFont);
        String[] genderOptions = {"남성", "여성"};
        genderComboBox = new JComboBox<>(genderOptions);
        genderComboBox.setFont(middleFont);
        genderPanel.add(genderLabel);
        genderPanel.add(genderComboBox);

        joinInButton = new JButton("회원가입");
        joinInButton.setPreferredSize(new Dimension(250,70));
        joinInButton.setBackground(Color.PINK);
        joinInButton.setForeground(Color.BLACK);
        joinInButton.setFont(bigFont);
        joinInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tryJoinIn(cardPanel, startCards);
            }
        });

        JButton goBackButton = new JButton("뒤로가기");
        goBackButton.setPreferredSize(new Dimension(250, 70));
        goBackButton.setBackground(Color.BLACK);
        goBackButton.setForeground(Color.WHITE);
        goBackButton.setFont(bigFont);
        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startCards.show(cardPanel, "로그인 페이지");
            }
        });


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel imageLabel = new JLabel();
        JLabel imageLabel1 = new JLabel();
        JLabel imageLabel2 = new JLabel();
        String exercisingBearImagePath = ServerComputer.getAbsolutePath("data\\icon\\끄아짤.png");
        String goingToGymBearImagePath = ServerComputer.getAbsolutePath("data\\icon\\운동다녀올게짤.png");
        String eatingProteinBearImagePath = ServerComputer.getAbsolutePath("data\\icon\\프로틴짤.png");
        Image exercisingBearImage = new ImageIcon(exercisingBearImagePath).getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
        Image goingToGymBearImage = new ImageIcon(goingToGymBearImagePath).getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
        Image eatingProteinBearImage = new ImageIcon(eatingProteinBearImagePath).getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);

        imageLabel.setIcon(new ImageIcon(exercisingBearImage));
        imageLabel1.setIcon(new ImageIcon(goingToGymBearImage));
        imageLabel2.setIcon(new ImageIcon(eatingProteinBearImage));

        bottomPanel.add(imageLabel1);
        bottomPanel.add(imageLabel);
        bottomPanel.add(imageLabel2);

        enterPanel.add(joinLabelPane);
        enterPanel.add(idPanel);
        enterPanel.add(passwordPanel);
        enterPanel.add(namePanel);
        enterPanel.add(nickNamePanel);
        enterPanel.add(phoneNumberPanel);
        enterPanel.add(genderPanel);
        enterPanel.add(goBackButton);
        enterPanel.add(joinInButton);

        joinPane.add(enterPanel, BorderLayout.CENTER);
        joinPane.add(bottomPanel, BorderLayout.SOUTH);

        cardPanel.add(joinPane, "회원가입 페이지");

    }

    void tryJoinIn(JPanel cardPanel, CardLayout startCards) {
        if (idField.getText().contentEquals("")
                || passwordField.getText().contentEquals("")
                || nameField.getText().contentEquals("")
                || nickNameField.getText().contentEquals("")
                || phoneNumberField.getText().contentEquals("   -    -    ")) {
            JOptionPane.showMessageDialog(joinPane, "올바른 입력이 아닙니다.");
            return;
        }

        User user = ServerComputer.getUser(idField.getText());
        if (user != null) {
            JOptionPane.showMessageDialog(joinPane, "이미 존재하는 아이디입니다.");
            return;
        }
        if (ServerComputer.isExistedUserNickname(nickNameField.getText())) {
            JOptionPane.showMessageDialog(joinPane, "이미 존재하는 닉네임입니다.");
            return;
        }
        User newUser = new Normal();
        newUser.scan(idField.getText(), passwordField.getText(),
                nameField.getText(),nickNameField.getText(),phoneNumberField.getText(),(String)genderComboBox.getSelectedItem());

        UserData userData = new UserData();
        userData.addUser(newUser);
        UserDataGUIManager.getInstance().addElement(userData);

        JOptionPane.showMessageDialog(joinPane, "회원가입이 성공적으로 되었습니다!\n 득 근 득 근!");
        startCards.show(cardPanel, "로그인 페이지");
    }
}