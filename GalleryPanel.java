import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

public class GalleryPanel extends JPanel{
    JButton removeButton;
    Queue<BufferedImage> images = new ArrayDeque<>();
    GalleryPanel(){
        setLayout(new BorderLayout());
        JPanel images = new JPanel(new GridLayout(6, 2));
        for(int i=0;i<12;i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            panel.setPreferredSize(new Dimension(100, 100));
            images.add(panel);
        }
        removeButton = new JButton("REMOVE SELECTED");
        add(removeButton, BorderLayout.NORTH);
        add(images, BorderLayout.CENTER);
    }
    public void saveImage(BufferedImage image){
        if(images.size()>12){
            images.add(image);
        }
    }
}
