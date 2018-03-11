import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel controlPanel, mainPanel;
    private GalleryPanel galleryPanel;
    private DrawPanel drawPanel;
    int sectors = 12;
    MainWindow(String title){
        super(title);

        galleryPanel = new GalleryPanel();
        drawPanel = new DrawPanel(galleryPanel);
        controlPanel = new ControlPanel(drawPanel);

        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        mainPanel.add(galleryPanel, BorderLayout.EAST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow("Digital Doilies");
    }
}
