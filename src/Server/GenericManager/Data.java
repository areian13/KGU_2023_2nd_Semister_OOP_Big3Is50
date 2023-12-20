package Server.GenericManager;

import java.util.Scanner;

public interface Data {
    public void scan(Scanner scanner);
    public void print();
    // print()와 toString을 분리하자.
    // print()는 콘솔창에 예쁘게 찍어내기 위함이고, toString은 파일에 일자로 찍어낼 때 쓰자.
    public String toGUIString(); // 얘는 GUI리스트에 예쁘게 찍기 위함이다.
    public boolean matches(String keyword);

    public Object clone();
}