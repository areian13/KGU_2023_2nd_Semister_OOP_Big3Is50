package Server.GUI;

import Server.ExerciseLog;
import Server.Program;
import Server.ServerComputer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

// 홈화면은 맨 위에 home이라는 글자 있는 배너 (JLabel)
// 중간에 사진이랑 이름, 운동 몇 일 째 정보 하나의 pane
// 나의 인바디, 나의 운동기록 임티랑 글씨 하나의 pane
// 체중, 운동기록, 골격근량, 나의 프로그램, 체지방량, 나의 트레이너 부분 하나의 pane (그리드layout)
public class HomePage extends JPanel {
    public void setup(JPanel cardPanel, CardLayout homeCards){
        Font font = new Font("맑은 고딕", Font.PLAIN, 15);
        Font middleFont = new Font("맑은 고딕", Font.BOLD, 20);
        Font bigFont = new Font("맑은 고딕", Font.BOLD, 25);
        setLayout(new BorderLayout());

        JPanel homeTopPane = new JPanel(new BorderLayout());

        JLabel homeLabel = new JLabel("3대50 헬스장 홈 화면", SwingConstants.CENTER);
        homeLabel.setPreferredSize(new Dimension(650,50));
        homeLabel.setOpaque(true);
        homeLabel.setBackground(Color.BLACK);
        homeLabel.setFont(bigFont);
        homeLabel.setForeground(Color.white);

        //사진, 이름, 출석현황
        //로그인 한 유저 프로필 사진으로 바꾸기
        JPanel homeMiddlePane = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        JLabel imageLabel = new JLabel();
        String imagePath = "data\\user-image\\" + GUIMain.me.getInfo("id") + ".png";
        Image image = ServerComputer.getImage(imagePath, true, 200, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));

        homeMiddlePane.add(imageLabel);

        //로그인 한 유저로 이름 바꾸기
        JPanel informationPane = new JPanel(new BorderLayout());

        JPanel namePart = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        JLabel name = new JLabel(String.format("%s(%s)", GUIMain.me.getInfo("name"), GUIMain.me.getInfo("nickname")));
        name.setFont(bigFont);
        JLabel nameMent = new JLabel("회원");
        nameMent.setFont(middleFont);
        namePart.add(name);
        namePart.add(nameMent);

        informationPane.add(namePart, BorderLayout.NORTH);

        //로그인 한 유저로 출석 세팅하기
        JPanel attendencePart = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        JLabel attendenceMent = new JLabel("출석");
        attendenceMent.setFont(middleFont);
        JLabel attendence = new JLabel(GUIMain.me.rankValue.get("출석왕") + "일째");
        attendence.setFont(bigFont);
        attendencePart.add(attendenceMent);
        attendencePart.add(attendence);

        informationPane.add(attendencePart, BorderLayout.CENTER);

        homeMiddlePane.add(informationPane);
        homeTopPane.add(homeLabel, BorderLayout.NORTH);
        homeTopPane.add(homeMiddlePane, BorderLayout.CENTER);

        //인바디(골격근량, 체지방, 체중), 운동기록, 프로그램, 트레이너
        JPanel homeBottomPane = new JPanel(new GridLayout(3,2));

        JPanel inbodyPart = new JPanel(new BorderLayout());
        JLabel inbodyLabel = new JLabel("내 인바디", SwingConstants.CENTER);
        inbodyLabel.setFont(bigFont);
        inbodyLabel.setOpaque(true);
        inbodyLabel.setBackground(Color.BLACK);
        inbodyLabel.setForeground(Color.WHITE);
        inbodyPart.add(inbodyLabel, BorderLayout.NORTH);

        JPanel inbodyImagePane = new JPanel();
        JLabel inbodyImageLabel = new JLabel();
        String inbodyImagePath = "data\\home-image\\" + "체중기록" + ".png";
        Image inbodyImage = ServerComputer.getImage(inbodyImagePath, false, 120, 120, Image.SCALE_SMOOTH);
        inbodyImageLabel.setIcon(new ImageIcon(inbodyImage));
        inbodyImagePane.add(inbodyImageLabel);
        inbodyPart.add(inbodyImagePane, BorderLayout.CENTER);

        int weight = GUIMain.me.getLastWeight();
        String kg = (weight != -1 ? "현재 " + weight + "kg" : "인바디 기록 없음");
        JLabel inbody = new JLabel(kg, SwingConstants.CENTER);
        inbody.setFont(bigFont);
        inbodyPart.add(inbody, BorderLayout.SOUTH);

        inbodyPart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                inbodyPart.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("내 인바디", homeCards, cardPanel);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                inbodyPart.setBackground(null);
            }
        });


        JPanel exercisePart = new JPanel(new BorderLayout());
        JLabel exerciseLabel = new JLabel("내 운동기록", SwingConstants.CENTER);
        exerciseLabel.setFont(bigFont);
        exerciseLabel.setOpaque(true);
        exerciseLabel.setBackground(Color.BLACK);
        exerciseLabel.setForeground(Color.WHITE);
        exercisePart.add(exerciseLabel, BorderLayout.NORTH);

        ExerciseLog exerciseLog = GUIMain.me.getLastExerciseLog();
        String exerciseLogMent = (exerciseLog != null ? ("마지막 운동: " + (LocalDate.now().toString().contentEquals(exerciseLog.getDate()) ? "오늘" : exerciseLog.getDate())) : "운동 기록 없음");

        JPanel exerciseImagePane = new JPanel();
        JLabel exerciseImageLabel = new JLabel();
        String exerciseImagePath = "data\\home-image\\" + "운동기록" + ".png";
        Image exerciseImage = ServerComputer.getImage(exerciseImagePath, false, 120, 120, Image.SCALE_SMOOTH);
        exerciseImageLabel.setIcon(new ImageIcon(exerciseImage));
        exerciseImagePane.add(exerciseImageLabel);
        exercisePart.add(exerciseImagePane, BorderLayout.CENTER);



        JLabel exercise = new JLabel(exerciseLogMent, SwingConstants.CENTER);
        exercise.setFont(bigFont);
        exercisePart.add(exercise, BorderLayout.SOUTH);

        exercisePart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exercisePart.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("내 운동기록", homeCards, cardPanel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exercisePart.setBackground(null);
            }
        });


        JPanel programPart = new JPanel(new BorderLayout());
        JLabel programLabel = new JLabel("내 프로그램", SwingConstants.CENTER);
        programLabel.setFont(bigFont);
        programLabel.setOpaque(true);
        programLabel.setBackground(Color.BLACK);
        programLabel.setForeground(Color.WHITE);
        programPart.add(programLabel, BorderLayout.NORTH);

        Program nextProgram = GUIMain.me.getNextProgram(false);

        JPanel programImagePane = new JPanel();
        JLabel programImageLabel = new JLabel();
        String programImagePath = "data\\program-image\\" + (nextProgram != null ? nextProgram.name : "no image") + ".png";
        Image programImage = ServerComputer.getImage(programImagePath, false, 120, 120, Image.SCALE_SMOOTH);
        programImageLabel.setIcon(new ImageIcon(programImage));
        programImagePane.add(programImageLabel);
        programPart.add(programImagePane, BorderLayout.CENTER);

        String programMent = (nextProgram != null ? String.format("%s %s~%s %s", nextProgram.date, nextProgram.startTime, nextProgram.endTime, nextProgram.name) : "등록된 프로그램 없음");
        JLabel program = new JLabel(programMent, SwingConstants.CENTER);
        program.setFont(bigFont);
        programPart.add(program, BorderLayout.SOUTH);

        programPart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                programPart.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("내 프로그램", homeCards, cardPanel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                programPart.setBackground(null);
            }
        });



        JPanel trainerPart = new JPanel(new BorderLayout());
        JLabel trainerLabel = new JLabel("내 PT", SwingConstants.CENTER);
        trainerLabel.setFont(bigFont);
        trainerLabel.setOpaque(true);
        trainerLabel.setBackground(Color.BLACK);
        trainerLabel.setForeground(Color.WHITE);
        trainerPart.add(trainerLabel, BorderLayout.NORTH);

        Program nextPT = GUIMain.me.getNextProgram(true);

        JPanel trainerImagePane = new JPanel();
        JLabel trainerImageLabel = new JLabel();
        String trainerImagePath = "data\\user-image\\" + (nextPT != null ? Program.trainerHashMap.get(nextPT.name) : "no image") + ".png";
        Image trainerImage = ServerComputer.getImage(trainerImagePath, true, 120, 120, Image.SCALE_SMOOTH);
        trainerImageLabel.setIcon(new ImageIcon(trainerImage));
        trainerImagePane.add(trainerImageLabel);
        trainerPart.add(trainerImagePane, BorderLayout.CENTER);

        String ptMent = (nextPT != null ? String.format("%s %s~%s %s", nextPT.date, nextPT.startTime, nextPT.endTime, nextPT.name) : "등록된 PT 없음");
        JLabel trainer = new JLabel(ptMent, SwingConstants.CENTER);
        trainer.setFont(bigFont);
        trainerPart.add(trainer, BorderLayout.SOUTH);

        trainerPart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                trainerPart.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("내 PT", homeCards, cardPanel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                trainerPart.setBackground(null);
            }
        });

        homeBottomPane.add(programPart);
        homeBottomPane.add(trainerPart);
        homeBottomPane.add(inbodyPart);
        homeBottomPane.add(exercisePart);

        JPanel homeButtonPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton enterInbody = new JButton("인바디 기록 입력하기");
        enterInbody.setPreferredSize(new Dimension(250,50));
        enterInbody.setBackground(Color.BLACK);
        enterInbody.setForeground(Color.WHITE);
        enterInbody.setFont(middleFont);
        enterInbody.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("인바디기록 추가", homeCards, cardPanel);
            }

        });

        JButton enterExerciseLog = new JButton("운동 기록 입력하기");
        enterExerciseLog.setPreferredSize(new Dimension(250,50));
        enterExerciseLog.setBackground(Color.BLACK);
        enterExerciseLog.setForeground(Color.WHITE);
        enterExerciseLog.setFont(middleFont);
        enterExerciseLog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("운동기록 추가", homeCards, cardPanel);
            }

        });

        homeButtonPane.add(enterInbody);
        homeButtonPane.add(enterExerciseLog);

        add(homeTopPane, BorderLayout.NORTH);
        add(homeBottomPane, BorderLayout.CENTER);
        add(homeButtonPane, BorderLayout.SOUTH);

        cardPanel.add(this, "홈 화면");
    }

    private void showPanel(String cardName, CardLayout homeCards, JPanel cardPanel){
        homeCards.show(cardPanel, cardName);
    }

}
