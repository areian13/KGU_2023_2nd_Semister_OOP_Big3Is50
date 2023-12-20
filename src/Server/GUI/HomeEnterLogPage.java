package Server.GUI;

import Server.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HomeEnterLogPage extends JPanel {
    JButton saveButton;
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

        JPanel middlePane = new JPanel();

        saveButton = new JButton("저장하기");
        saveButton.setFont(middleFont);
        saveButton.setPreferredSize(new Dimension(200,70));
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);

        if(cardName.equals("운동기록 추가"))
            middlePane = setupExerciseMid();
        else
            middlePane = setupInbodyMid();


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
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
        buttonPanel.add(saveButton);

        add(cardNameLabel, BorderLayout.NORTH);
        add(middlePane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private JPanel setupInbodyMid() {
        Font bigFont = new Font("맑은 고딕", Font.BOLD, 30);
        Font middleFont = new Font("맑은 고딕", Font.BOLD, 18);

        JPanel inbodyMidPanel = new JPanel(new GridLayout(3,1));

        JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel weightLabel = new JLabel("체중");
        weightLabel.setFont(bigFont);
        JTextField weightField = new JTextField();
        weightField.setFont(bigFont);
        weightField.setColumns(20);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);

        JPanel musclePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel muscleLabel = new JLabel("근육량");
        muscleLabel.setFont(bigFont);
        JTextField muscleField = new JTextField();
        muscleField.setColumns(20);
        muscleField.setFont(bigFont);
        musclePanel.add(muscleLabel);
        musclePanel.add(muscleField);

        JPanel fatPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel fatLabel = new JLabel("지방량");
        fatLabel.setFont(bigFont);
        JTextField fatField = new JTextField();
        fatField.setColumns(20);
        fatField.setFont(bigFont);
        fatPanel.add(fatLabel);
        fatPanel.add(fatField);

        inbodyMidPanel.add(weightPanel);
        inbodyMidPanel.add(musclePanel);
        inbodyMidPanel.add(fatPanel);

        JPanel pane = new JPanel();
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (weightField.getText().contentEquals("")
                        || muscleField.getText().contentEquals("")
                        || fatField.getText().contentEquals("")) {
                    JOptionPane.showMessageDialog(pane, "입력이 잘못되었습니다."); // 팝업 띄우기용으로 그냥 아무 pane 만듦;
                    return;
                }

                JOptionPane.showMessageDialog(pane, "저장되었습니다.");

                Inbody inbody = new Inbody();
                inbody.set(Integer.parseInt(weightField.getText()), Integer.parseInt(muscleField.getText()), Integer.parseInt(fatField.getText()));
                GUIMain.me.getInbodyManager().dataList.add(inbody);
                InbodyGUIManager.getInstance().addElement(inbody);

                //파일에 저장하는 코드
            }
        });
        return inbodyMidPanel;
    }

    private JPanel setupExerciseMid() {
        Font bigFont = new Font("맑은 고딕", Font.BOLD, 30);
        Font middleFont = new Font("맑은 고딕", Font.BOLD, 18);

        JPanel exerciseMidPanel = new JPanel(new BorderLayout());

        // 북쪽 유산소 무산소 라벨
        JPanel typeOfExercise = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel cardioLabel = new JLabel("유산소", SwingConstants.CENTER);
        cardioLabel.setFont(bigFont);
        cardioLabel.setBackground(Color.GRAY);
        cardioLabel.setPreferredSize(new Dimension(300,50));
        cardioLabel.setOpaque(true);
        cardioLabel.setForeground(Color.BLACK);

        JLabel anaerobic = new JLabel("무산소", SwingConstants.CENTER);
        anaerobic.setFont(bigFont);
        anaerobic.setBackground(Color.GRAY);
        anaerobic.setPreferredSize(new Dimension(300, 50));
        anaerobic.setOpaque(true);
        anaerobic.setForeground(Color.BLACK);

        typeOfExercise.add(cardioLabel);
        typeOfExercise.add(anaerobic);

        // 중앙쪽 입력란
        JPanel mid = new JPanel(new BorderLayout());

        // 운동선택, 입력, 추가버튼
        JPanel selectExercise = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        String[] cardioExercise = {
                "런닝머신",
                "사이클링",
                "마이마운틴",
                "스텝밀",
                "아크트레이너"
        };
        JComboBox<String> cardioExerciseComboBox = new JComboBox<>(cardioExercise);
        cardioExerciseComboBox.setFont(middleFont);
        cardioExerciseComboBox.setPreferredSize(new Dimension(250,50));
        selectExercise.add(cardioExerciseComboBox);

        String[] anaerobicExercise = {
                "데드리프트",
                "랫풀다운",
                "턱걸이",
                "딥스",
                "벤치프레스",
                "팔굽혀펴기",
                "업라이트루오",
                "레터럴레이즈",
                "밀리터리프레스",
                "스쿼트",
                "카프레이즈",
                "레그프레스",
                "크런치",
                "윗몸일으키기",
                "스컬크러셔",
                "트라이셉스푸시다운",
                "바벨컬",
                "덤벨컬"
        };
        JComboBox<String> anaerobicExerciseComboBox = new JComboBox<>(anaerobicExercise);
        anaerobicExerciseComboBox.setFont(middleFont);
        anaerobicExerciseComboBox.setPreferredSize(new Dimension(250,50));
        selectExercise.add(anaerobicExerciseComboBox);



        //입력

        JPanel enterPane = new JPanel(new GridLayout(3, 3));

        JPanel speedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel speedLabel = new JLabel("속도");
        speedLabel.setFont(middleFont);
        JTextField speedField = new JTextField();
        speedField.setColumns(10);
        speedPanel.add(speedLabel);
        speedPanel.add(speedField);


        JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel weightLabel = new JLabel("무게");
        weightLabel.setFont(middleFont);
        JTextField weightField = new JTextField();
        weightField.setColumns(10);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel timeLabel = new JLabel("시간");
        timeLabel.setFont(middleFont);
        JTextField timeField = new JTextField();
        timeField.setColumns(10);
        timePanel.add(timeLabel);
        timePanel.add(timeField);


        JPanel cntPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel cntLabel = new JLabel("횟수");
        cntLabel.setFont(middleFont);
        JTextField cntField = new JTextField();
        cntField.setColumns(10);
        cntPanel.add(cntLabel);
        cntPanel.add(cntField);

        enterPane.add(speedPanel);
        enterPane.add(weightPanel);
        enterPane.add(timePanel);
        enterPane.add(cntPanel);

        ExerciseLog exerciseLog = new ExerciseLog();
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JOptionPane.showMessageDialog(new JPanel(), "저장되었습니다.");

                exerciseLog.setLog();
                ExerciseLog cloneExerciseLog = (ExerciseLog)exerciseLog.clone();

                GUIMain.me.getExerciseLogManager().dataList.add(exerciseLog);
                ExerciseLogGUIManager.getInstance().addElement(cloneExerciseLog);

                //파일에 저장하는 코드
            }
        });

        //moseClick을 위해 먼저 선언
        JPanel logPane = new JPanel();
        //추가버튼
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        JButton addCardioButton = new JButton("유산소 기록 추가");
        //addCardioButton.setHorizontalTextPosition(CENTER_ALIGNMENT);
        addCardioButton.setFont(middleFont);
        addCardioButton.setPreferredSize(new Dimension(200,70));
        addCardioButton.setBackground(Color.GRAY);
        addCardioButton.setForeground(Color.BLACK);

        addCardioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel unitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                unitPanel.setPreferredSize(new Dimension(630,50));

                if (speedField.getText().contentEquals("") || speedField.getText().contentEquals("")) {
                    JOptionPane.showMessageDialog(logPane, "입력이 잘못되었습니다.");
                    return;
                }

                Cardio exercise = new Cardio();
                exercise.input((String)cardioExerciseComboBox.getSelectedItem(), Integer.parseInt(speedField.getText()), Integer.parseInt(timeField.getText()));
                exerciseLog.addExercise(exercise);

                JLabel label = new JLabel(exercise.toGUIString(), SwingConstants.CENTER);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                unitPanel.add(label);

                JButton deleteButton = new JButton("삭제하기");
                deleteButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //해당 unitPanel 삭제하기
                        logPane.remove(unitPanel);
                        logPane.revalidate();
                        logPane.repaint();

                        exerciseLog.deleteExercise(exercise);
                    }
                });
                unitPanel.add(deleteButton);

                logPane.add(unitPanel);
                logPane.revalidate();
                //logPane.repaint();
            }
        });

        JButton addAnaerobicButton = new JButton("무산소 기록 추가");
        //addAnaerobicButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addAnaerobicButton.setFont(middleFont);
        addAnaerobicButton.setPreferredSize(new Dimension(200,70));
        addAnaerobicButton.setBackground(Color.GRAY);
        addAnaerobicButton.setForeground(Color.BLACK);
        addAnaerobicButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel unitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                unitPanel.setPreferredSize(new Dimension(630,50));

                if (weightField.getText().contentEquals("") || weightField.getText().contentEquals("")) {
                    JOptionPane.showMessageDialog(logPane, "입력이 잘못되었습니다.");
                    return;
                }

                Anaerobic exercise = new Anaerobic();
                exercise.input((String)anaerobicExerciseComboBox.getSelectedItem(), Integer.parseInt(weightField.getText()), Integer.parseInt(cntField.getText()));
                exerciseLog.addExercise(exercise);

                JLabel label = new JLabel(exercise.toGUIString(), SwingConstants.CENTER);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                unitPanel.add(label);

                JButton deleteButton = new JButton("삭제하기");
                deleteButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //해당 unitPanel 삭제하기
                        logPane.remove(unitPanel);
                        logPane.revalidate();
                        logPane.repaint();

                        exerciseLog.deleteExercise(exercise);
                    }
                });
                unitPanel.add(deleteButton);

                logPane.add(unitPanel);
                logPane.revalidate();
                //logPane.repaint();
            }
        });

        buttonPanel.add(addCardioButton);
        buttonPanel.add(addAnaerobicButton);

        // 남쪽 String 보여주기 + 초기화 버튼.

        //textPane에는 currLogLabel, scrollPane
        JPanel textPane = new JPanel(new BorderLayout());

        textPane.setPreferredSize(new Dimension(650,300));
        textPane.setBackground(Color.BLACK);

        JLabel currLogLabel = new JLabel("운동기록 생성", SwingConstants.CENTER);
        currLogLabel.setFont(middleFont);
        currLogLabel.setForeground(Color.WHITE);

        // 유산소 기록 추가 버튼리알 무산소 기록 추가 버튼 클리 시 currLog 라벨에 실시간으로 기록 적기. --> txt 파일에 저장되는 형식이면 됨.
        logPane.setLayout(new BoxLayout(logPane, BoxLayout.Y_AXIS));



        JScrollPane scrollPane = new JScrollPane(logPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 비활성화
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //textPane.add(scrollPane);
        /*JLabel currLog = new JLabel("ㅁㄴㅇㄻㄴㅇㄻㅇㄹ", SwingConstants.CENTER);
        currLog.setFont(middleFont);*/

        /*JPanel buttonPane = new JPanel();
        JButton refreshButton = new JButton("기록 초기화");
        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //currLog 초기화
            }
        });
        buttonPane.add(refreshButton);*/

        textPane.add(currLogLabel, BorderLayout.NORTH);
        textPane.add(scrollPane, BorderLayout.CENTER);
        //textPane.add(buttonPane, BorderLayout.SOUTH);


        mid.add(selectExercise, BorderLayout.NORTH);
        mid.add(enterPane, BorderLayout.CENTER);
        mid.add(buttonPanel, BorderLayout.SOUTH);

        exerciseMidPanel.add(typeOfExercise, BorderLayout.NORTH);
        exerciseMidPanel.add(mid, BorderLayout.CENTER);
        exerciseMidPanel.add(textPane, BorderLayout.SOUTH);

        return exerciseMidPanel;
    }
}
