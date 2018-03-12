import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class GalleryPanel extends JPanel{
    JButton removeButton;
    ArrayList<BufferedImage> images;
    SavedPanel[] savedPanels;
    SavedPanel selected = null;
    GalleryPanel(){
        images = new ArrayList<>();
        savedPanels = new SavedPanel[12];
        setLayout(new BorderLayout());
        JPanel images = new JPanel(new GridLayout(6, 2));
        for(int i=0;i<12;i++) {
            SavedPanel panel = new SavedPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            panel.setPreferredSize(new Dimension(100, 100));
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selected = panel;
                    repaint();
                }
            });
            images.add(panel);
            savedPanels[i] = panel;
        }
        removeButton = new JButton("REMOVE SELECTED");
        removeButton.addActionListener(e -> removeImage());
        add(removeButton, BorderLayout.NORTH);
        add(images, BorderLayout.CENTER);
    }
    public void saveImage(BufferedImage image){
        if(images.size()<12) {
            images.add(image);
        }else{
            JOptionPane.showMessageDialog(this, "Cannot save any more images. Please remove one first.");
        }
        repaint();
    }
    public void removeImage(){
        try {
            BufferedImage toRemove = selected.getOriginal();
            images.remove(toRemove);
            selected = null;
            repaint();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Please select an image to remove.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i=0;i<12;i++){
            try {
                savedPanels[i].setImage(images.get(i));
            }catch (Exception e){
                savedPanels[i].setImage(null);
            }
        }
        for(SavedPanel panel : savedPanels){
            if(panel.equals(selected)){
                panel.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
            }else{
                panel.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        }
    }
}
