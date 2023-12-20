package Server;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Rank {
    public static final int CNT_RANK = 5;

    String rankName;
    String comment1;
    String comment2;
    ArrayList<User> userRankList = new ArrayList<>();
    Comparator<User> comparator;

    Rank(String rankName, String comment1, String comment2, Comparator<User> comparator) {
        this.rankName = rankName;
        this.comment1 = comment1;
        this.comment2 = comment2;
        this.comparator = comparator;
    }

    void sort() {
        userRankList.sort(comparator);
    }

    void print() {
        System.out.printf("이 달의 %s\n", rankName);
        for (int i = 0; i < CNT_RANK; i++) {
            User user = userRankList.get(i);
            System.out.printf("[%d등] %s %s %d%s\n",
                    i + 1, user.nickname, comment1, user.rankValue.get(rankName), comment2);
        }
    }
}
