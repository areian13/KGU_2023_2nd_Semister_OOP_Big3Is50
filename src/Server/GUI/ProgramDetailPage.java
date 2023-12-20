package Server.GUI;

import Server.Program;
import Server.ServerComputer;
import Server.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class ProgramDetailPage extends JPanel {
    private JTable schedule;
    private DefaultTableModel tableModel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private ProgramCustomPage programCustomPage;
    private Program[][] timeTable;
    private String participants;
    private JLabel nameLabel;
    private JButton applyButton;
    private Program selectedProgram;
    private boolean isParticipatedProgram;

    void setup(String panelName, ProgramCustomPage programCustomPage, CardLayout programCards, JPanel cardPanel) {
        this.programCustomPage = programCustomPage;
        //setupTopPanel(panelName);
        setupTable(panelName, programCustomPage);
        setupMiddlePanel(panelName);
        setupBottomPanel(programCards, cardPanel);
    }
    void setupTable(String panelName, ProgramCustomPage programCustomPage) {

        setLayout(new BorderLayout());
        String[] header = {"시간대", "월", "화", "수", "목", "금", "토", "일"};
        tableModel = new DefaultTableModel(header, 12) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        timeTable = new Program[12][8];
        for (Program program : programCustomPage.programList) {
            int dateIdx = 0;
            for (int i = 0; !header[i].contentEquals(program.date); i++)
                dateIdx++;

            int startIdx = Integer.parseInt(program.startTime.substring(0, 2)) - 9;
            int endIdx = Integer.parseInt(program.endTime.substring(0, 2)) - 9;

            for (int i = startIdx; i < endIdx; i++) {
                timeTable[i][dateIdx] = program;
            }
        }

        tableModel.setValueAt("09:00 ~ 10:00", 0, 0);
        tableModel.setValueAt("10:00 ~ 11:00", 1, 0);
        tableModel.setValueAt("11:00 ~ 12:00", 2, 0);
        tableModel.setValueAt("12:00 ~ 13:00", 3, 0);
        tableModel.setValueAt("13:00 ~ 14:00", 4, 0);
        tableModel.setValueAt("14:00 ~ 15:00", 5, 0);
        tableModel.setValueAt("15:00 ~ 16:00", 6, 0);
        tableModel.setValueAt("16:00 ~ 17:00", 7, 0);
        tableModel.setValueAt("17:00 ~ 18:00", 8, 0);
        tableModel.setValueAt("18:00 ~ 19:00", 9, 0);
        tableModel.setValueAt("19:00 ~ 20:00", 10, 0);
        tableModel.setValueAt("20:00 ~ 21:00", 11, 0);

        schedule = new JTable(tableModel) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };

        schedule.getTableHeader().setReorderingAllowed(false);

        // 열 크기 조정
        for (int i = 1; i <= 7; i++) {
            TableColumn column = schedule.getColumnModel().getColumn(i);
            column.setPreferredWidth(15);
        }

        for (int i = 0; i < 12; i++) {
            schedule.setRowHeight(i, 30);
        }

        schedule.setFillsViewportHeight(true);
        schedule.setCellSelectionEnabled(true);

        class CustomTableCellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(timeTable[row][column] != null)
                    component.setBackground(Color.CYAN);
                else
                    component.setBackground(null);
                return component;
            }
        }

        // 셀 렌더러 설정
        schedule.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(schedule);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.PAGE_START);

        schedule.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = schedule.getSelectedRow();
                int selectedColumn = schedule.getSelectedColumn();

                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                        if (isSelected && selectedRow == row && selectedColumn == column)
                            component.setBackground(Color.RED);
                        else if(timeTable[row][column] != null)
                            component.setBackground(Color.CYAN);
                        else
                            component.setBackground(null);
                        return component;
                    }
                };

                schedule.setDefaultRenderer(Object.class, renderer);
                schedule.repaint();

                selectedProgram = timeTable[selectedRow][selectedColumn];
                updateParticipants();
                isParticipatedProgram = isParticipatedProgram();
                applyButton.setText(isParticipatedProgram ? "신청취소" : "신청하기");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // 마우스가 패널에 들어왔을 때의 동작
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // 마우스가 패널을 벗어났을 때의 동작
            }
        });
    }
    void setupMiddlePanel(String panelName){
        JLabel programNameLabel = new JLabel(panelName + " 프로그램 시간표", SwingConstants.CENTER);
        programNameLabel.setPreferredSize(new Dimension(650,50));
        programNameLabel.setBackground(Color.DARK_GRAY);
        programNameLabel.setOpaque(true); // 배경 불투명하게 해야 배경색 보임
        programNameLabel.setForeground(Color.WHITE); //글씨색 하얀색
        Font programNameLabelFont = new Font("맑은 고딕", Font.PLAIN, 25);
        programNameLabel.setFont(programNameLabelFont);

        middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(programNameLabel, BorderLayout.NORTH);

        JLabel participantsLabel = new JLabel("참여자 : ", SwingConstants.CENTER);
        Font participantsFont = new Font("맑은 고딕", Font.PLAIN, 30);
        participantsLabel.setFont(participantsFont);
        middlePanel.add(participantsLabel, BorderLayout.LINE_START);

        nameLabel = new JLabel(participants, SwingConstants.CENTER);
        Font nameLabelFont = new Font("맑은 고딕", Font.PLAIN, 20);
        nameLabel.setFont(nameLabelFont);
        middlePanel.add(nameLabel, BorderLayout.CENTER);
        add(middlePanel,BorderLayout.CENTER);
    }
    void setupBottomPanel(CardLayout programCards, JPanel cardPanel){
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        Font buttonFont = new Font("맑은 고딕", Font.PLAIN, 15);
        JButton goBackButton = new JButton("뒤로가기");
        goBackButton.setFont(buttonFont);
        goBackButton.setBackground(Color.BLACK);
        goBackButton.setForeground(Color.WHITE);

        applyButton = new JButton("신청하기");
        applyButton.setFont(buttonFont);
        applyButton.setBackground(Color.WHITE);
        applyButton.setForeground(Color.BLACK);

        bottomPanel.add(goBackButton);
        bottomPanel.add(applyButton);

        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                programCards.show(cardPanel, "프로그램 메인화면");
            }
        });

        applyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isParticipatedProgram) {
                    if (GUIMain.me.isAbleToParticipateProgram(selectedProgram)) {
                        joinProgram();
                    } else {
                        Program overlappedProgram = GUIMain.me.overlappedProgram(selectedProgram);
                        String programInfo = String.format("%s(%s%s~%s)", overlappedProgram.name, overlappedProgram.date, overlappedProgram.startTime, overlappedProgram.endTime);
                        JOptionPane.showMessageDialog(bottomPanel,programInfo + "와 시간이 겹쳐 신청이 불가능합니다.");
                        return;
                    }
                } else {
                    cancelProgram();
                }

                updateParticipants();

                isParticipatedProgram = isParticipatedProgram();
                applyButton.setText(isParticipatedProgram ? "신청취소" : "신청하기");
                GUIMain.me.sortProgram();

                ServerComputer.save();
            }
        });
        add(bottomPanel, BorderLayout.SOUTH);
    }
    void joinProgram() {
        selectedProgram.addNewUser(GUIMain.me);
        GUIMain.me.participateProgram(selectedProgram);

        JOptionPane.showMessageDialog(bottomPanel, "신청되었습니다!");
    }
    void cancelProgram() {
        selectedProgram.deleteUser(GUIMain.me);
        GUIMain.me.cancelProgram(selectedProgram);

        JOptionPane.showMessageDialog(bottomPanel, "취소되었습니다..");
    }
    void updateParticipants() {
        StringBuilder result = new StringBuilder();

        if (selectedProgram != null) {
            for (User user : selectedProgram.membersManager.dataList)
                result.append(user.getInfo("nickname") + " ");
        }
        participants = result.toString();
        nameLabel.setText(participants);
    }
    boolean isParticipatedProgram() {
        if(selectedProgram == null)
            return false;

        for (User user : selectedProgram.membersManager.dataList) {
            if(user == GUIMain.me)
                return true;
        }
        return false;
    }
}

