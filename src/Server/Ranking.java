package Server;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Ranking {
    static ArrayList<Rank> rankList = new ArrayList<>();

    Ranking() {
        rankList.add(new Rank("3대왕", "3대", "kg 달성",Comparator.comparingInt(User::getBig3).reversed()));
        rankList.add(new Rank("득근왕", "골격근", "kg 증량", Comparator.comparingInt(User::getPowerUp).reversed()));
        rankList.add(new Rank("연소왕", "체지방", "kg 감량",Comparator.comparingInt(User::getBurnFat).reversed()));
        rankList.add(new Rank("출석왕", "총", "일 출석", Comparator.comparingInt(User::getSincerity).reversed()));
    }
    void addUser(User user)
    {
        for (Rank rank : rankList)
            rank.userRankList.add(user);
    }
    void sort() {
        for (Rank rank : rankList)
            rank.sort();
    }
    void print() {
        for (Rank rank : rankList)
            rank.print();
    }

    public static int getIdxByRankName(String rankName) {
        for (int i = 0; i < rankList.size(); i++) {
            if (rankList.get(i).rankName.contentEquals(rankName))
                return i;
        }
        return -1;
    }
    public static String getIDByRanker(String rankName, int idx) {
        int rankIdx = getIdxByRankName(rankName);
        return rankList.get(rankIdx).userRankList.get(idx).id;
    }
    public static String getNicknameByRanker(String rankName, int idx) {
        int rankIdx = getIdxByRankName(rankName);
        return rankList.get(rankIdx).userRankList.get(idx).nickname;
    }
    public static String getValueByRanker(String rankName, int idx) {
        int rankIdx = getIdxByRankName(rankName);

        String result = rankList.get(rankIdx).comment1
                + rankList.get(rankIdx).userRankList.get(idx).rankValue.get(rankName)
                + rankList.get(rankIdx).comment2;
        return result;
    }
    public static int getRankByID(String rankName, String userID) {
        int rankIdx = getIdxByRankName(rankName);
        for (int i = 0; i < rankList.get(rankIdx).userRankList.size(); i++) {
            if(rankList.get(rankIdx).userRankList.get(i).id.contentEquals(userID))
                return i;
        }
        return -1;
    }
}
