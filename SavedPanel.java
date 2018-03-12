import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SavedPanel extends JPanel{
    BufferedImage image, original;

    public void setImage(BufferedImage image) {
        original = image;
        if(image!=null) {
            this.image = resize(image, getWidth(), getHeight());
        }else{
            this.image = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getOriginal() {
        return original;
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
}
