package Server.GUI;

import Server.Program;
import Server.ServerComputer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ProgramCustomPage extends JPanel {
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel dateLabel;

    static String week = "월화수목금토일";
    boolean[] isWorking;
    private StringBuilder workingDate;

    ArrayList<Program> programList;

    public ProgramCustomPage(String title, CardLayout programCards, JPanel cardPanel, String keyword, int price){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(350,150));

        String imagePath = "data\\program-image\\" + title + ".png";
        if(keyword.contentEquals("pt"))
            imagePath = "data\\user-image\\" + Program.trainerHashMap.get(title) + ".png";
        Image image = ServerComputer.getImage(imagePath, false, 100, 100, Image.SCALE_SMOOTH);

        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));
        add(imageLabel, BorderLayout.LINE_START);

        titleLabel = new JLabel(title, SwingConstants.CENTER);
        Font titleLabelFont = new Font("맑은 고딕", Font.BOLD, 50);
        titleLabel.setFont(titleLabelFont);
        add(titleLabel, BorderLayout.CENTER);

        descriptionLabel = new JLabel("회당 " + new DecimalFormat("###,###").format(price) + "원", SwingConstants.SOUTH_EAST);
        Font descriptionLabelFont = new Font("맑은 고딕", Font.BOLD, 15);
        descriptionLabel.setFont(descriptionLabelFont);
        add(descriptionLabel, BorderLayout.SOUTH);

        dateLabel = new JLabel();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 클릭되었을 때 다른 화면으로 이동
                //showPanel(detailPageName, programCards, programPane);
                programCards.show(cardPanel, title);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // 마우스가 패널에 들어왔을 때의 동작
                ProgramCustomPage.this.setBackground(Color.GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // 마우스가 패널을 벗어났을 때의 동작
                ProgramCustomPage.this.setBackground(null); // 기본 색상으로 돌아가기
            }
        });

        programList = new ArrayList<>();
        isWorking = new boolean[7];
        workingDate = new StringBuilder();
        workingDate.append("요일 : ");

    }
    public void add(Program program) {
        programList.add(program);
        for (int i = 0; i < 7; i++) {
            if (program.date.contentEquals(week.charAt(i) + "") && !isWorking[i]) {
                isWorking[i] = true;
                workingDate.append(week.charAt(i) + " ");
                break;
            }
        }
        dateLabel.setText(workingDate.toString());
        Font dateLabelFont = new Font("맑은 고딕", Font.BOLD, 20);
        dateLabel.setFont(dateLabelFont);
        add(dateLabel, BorderLayout.LINE_END);
    }

    public void getStartTime(){

    }
}