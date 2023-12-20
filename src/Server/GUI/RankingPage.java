package Server.GUI;

import Server.Rank;
import Server.Ranking;
import Server.ServerComputer;
import Server.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilePermission;

public class RankingPage extends JPanel {
    //JPanel rankingPanel;
    JLabel rankingLabel;
    void setup(String rankingName){
        setLayout(new BorderLayout());
        Font font = new Font("맑은 고딕", Font.PLAIN, 15);
        Font middleFont = new Font("맑은 고딕", Font.BOLD, 20);
        Font bigFont = new Font("맑은 고딕", Font.BOLD, 25);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel imageLabel = new JLabel();
        String imagePath = ServerComputer.getAbsolutePath("data\\ranking-image\\" + rankingName + ".png");
        Image image = new ImageIcon(imagePath).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));

        JLabel trophyImageLabel = new JLabel();
        String trophyImagePath = ServerComputer.getAbsolutePath("data\\ranking-image\\트로피.png");
        Image trophyImage = new ImageIcon(trophyImagePath).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        trophyImageLabel.setIcon(new ImageIcon(trophyImage));

        rankingLabel = new JLabel(rankingName, SwingConstants.CENTER);
        rankingLabel.setPreferredSize(new Dimension(500,50));
        rankingLabel.setOpaque(true); // 배경 불투명하게 해야 배경색 보임
        rankingLabel.setBackground(Color.ORANGE);
        rankingLabel.setForeground(Color.BLACK);
        rankingLabel.setFont(bigFont);

        //update
        JPanel midPanel = new JPanel(new GridLayout(6,1));
        //1등~5등

        for (int i = 0; i < Rank.CNT_RANK; i++) {
            JPanel personPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30,30));
            JLabel personImageLabel = new JLabel();

            //각 랭킹별로 아이디가 제목인 이미지 파일 불러옴.
            String relativePath = "data\\user-image\\" + Ranking.getIDByRanker(rankingName, i) + ".png";
            Image personImage = ServerComputer.getImage(relativePath, true, 100, 100, Image.SCALE_SMOOTH);
            personImageLabel.setIcon(new ImageIcon(personImage));

            personPanel.add(personImageLabel);

            //알맞은 이름으로 수정필요.
            JLabel name = new JLabel(Ranking.getNicknameByRanker(rankingName, i), SwingConstants.CENTER);
            name.setFont(bigFont);

            personPanel.add(name);

            //알맞은 기록으로 수정필요.
            JLabel spec = new JLabel(Ranking.getValueByRanker(rankingName, i), SwingConstants.CENTER);
            spec.setFont(middleFont);

            personPanel.add(spec);

            //알맞은 그림으로 수정필요.-->지윤님이 이미지 파일 주면 가능.
            JLabel medalImageLabel = new JLabel();
            String medalImagePath = "data\\ranking-image\\" + (i + 1) + ".png";
            Image medalImage = ServerComputer.getImage(medalImagePath, false, 70, 70, Image.SCALE_SMOOTH);
            medalImageLabel.setIcon(new ImageIcon(medalImage));

            personPanel.add(medalImageLabel);

            midPanel.add(personPanel);
        }


        //내 등수
        int myRank = Ranking.getRankByID(rankingName, GUIMain.me.getInfo("id"));

        JPanel mePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,30,30));
        JLabel meImageLabel = new JLabel();
        String meImagePath = "data\\user-image\\" + Ranking.getIDByRanker(rankingName, myRank) + ".png";

        Image meImage = ServerComputer.getImage(meImagePath, true, 100, 100, Image.SCALE_SMOOTH);
        meImageLabel.setIcon(new ImageIcon(meImage));

        mePanel.add(meImageLabel);

        JLabel nickname = new JLabel(GUIMain.me.getInfo("nickname"), SwingConstants.CENTER);
        nickname.setFont(bigFont);

        mePanel.add(nickname);

        // update
        JLabel spec = new JLabel(Ranking.getValueByRanker(rankingName, myRank), SwingConstants.CENTER);
        spec.setFont(middleFont);

        mePanel.add(spec);

        // update
        JLabel myRanking = new JLabel((myRank + 1) + "등");
        myRanking.setFont(middleFont);
        mePanel.add(myRanking);

        midPanel.add(mePanel);

        topPanel.add(trophyImageLabel);
        topPanel.add(rankingLabel);
        topPanel.add(imageLabel);

        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
    }
}
