package Server.GUI;

import Server.ServerComputer;

import javax.swing.*;
import java.awt.*;

public class TopBanner extends JPanel{
    private JLabel imageLabel;

    TopBanner(){
        String imagePath = ServerComputer.getAbsolutePath("data\\icon\\3대50배너.png");
        Image image = new ImageIcon(imagePath).getImage().getScaledInstance(600, 100, Image.SCALE_SMOOTH);
        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));
        add(imageLabel);
    }


}
