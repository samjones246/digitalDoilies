import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class DrawPanel extends JPanel{
    private BufferedImage doily;
    private int numberOfSectors = 12, penSize = 10;
    private boolean showLines = true, reflectPoints = true, eraseMode = false;
    private Color penColour = Color.red;
    private Stack<Point.Double> points, undonePoints;
    private Stack<Color> colors, undoneColours;
    private Stack<Integer> sizes, undoneSizes, actions, undoneActions;
    private GalleryPanel galleryPanel;
    DrawPanel(GalleryPanel galleryPanel){
        this.galleryPanel = galleryPanel;
        setPreferredSize(new Dimension(600, 600));
        setBorder(BorderFactory.createLineBorder(Color.black));
        points = new Stack<>();
        colors = new Stack<>();
        sizes = new Stack<>();
        undonePoints = new Stack<>();
        undoneColours = new Stack<>();
        undoneSizes = new Stack<>();
        addMouseListener(new PenListener());
        addMouseMotionListener(new PenListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doily = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gg = doily.createGraphics();
        drawPoints(gg);
        if(showLines){
            createSectorLines(gg);
        }
        g.drawImage(doily, 0, 0, this);
    }

    private void drawPoints(Graphics2D gg) {
        for(int i=0;i<points.size();i++){
            double x = points.get(i).x;
            double y = points.get(i).y;
            Color color = colors.get(i);
            int size = sizes.get(i);
            gg.setPaint(color);
            int centreX = doily.getWidth()/2, centreY = doily.getHeight()/2;
            for(int j=0;j<numberOfSectors;j++){
                Ellipse2D ellipse = new Ellipse2D.Double(x,y,size,size);
                double rotationAngle = 360/numberOfSectors*j;
                AffineTransform at =
                        AffineTransform.getRotateInstance(
                                Math.toRadians(rotationAngle), centreX, centreY);
                gg.fill(at.createTransformedShape(ellipse));
            }
        }
    }

    public BufferedImage getDoily() {
        return doily;
    }

    public void createSectorLines(Graphics2D gg){
        gg.setPaint(Color.white);
        int length = (doily.getWidth()/2);
        Point centre = new Point(doily.getWidth()/2, doily.getHeight()/2);
        for(int i=0; i<numberOfSectors;i++){
            Line2D line = new Line2D.Double(centre.x, centre.y, centre.x, centre.y - length);
            double rotationAngle = 360/numberOfSectors;
            double mult = i;
            if(360%numberOfSectors!=0){
                mult-=0.5;
            }
            AffineTransform at =
                    AffineTransform.getRotateInstance(
                            Math.toRadians(rotationAngle*mult), line.getX1(), line.getY1());

            gg.draw(at.createTransformedShape(line));
        }
    }

    public void addPoint(int x, int y){
        Color color;
        if(eraseMode){
            color = Color.black;
        }else{
            color = penColour;
        }
        points.add(new Point.Double(x-penSize/2, y-penSize/2));
        colors.add(color);
        sizes.add(penSize);
        if(reflectPoints){
            int x2 = doily.getWidth()/2 + (doily.getWidth()/2-x);
            int y2 = y;
            points.add(new Point.Double(x2-penSize/2, y2-penSize/2));
            colors.add(color);
            sizes.add(penSize);
        }
    }
    public void saveImage(){
        galleryPanel.saveImage(doily);
    }

    public void setEraseMode(boolean eraseMode) {
        this.eraseMode = eraseMode;
    }

    public boolean isEraseMode() {
        return eraseMode;
    }

    public void setNumberOfSectors(int numberOfSectors) {
        this.numberOfSectors = numberOfSectors;
        repaint();
    }

    public int getNumberOfSectors() {
        return numberOfSectors;
    }

    public void setPenColour(Color penColour) {
        this.penColour = penColour;
    }

    public Color getPenColour() {
        return penColour;
    }

    public void setPenSize(int penSize) {
        this.penSize = penSize;
    }

    public int getPenSize() {
        return penSize;
    }

    public void setReflectPoints(boolean reflectPoints) {
        this.reflectPoints = reflectPoints;
    }

    public boolean isReflectPoints() {
        return reflectPoints;
    }

    public void setShowLines(boolean showLines) {
        this.showLines = showLines;
        repaint();
    }
    public boolean isShowLines() {
        return showLines;
    }

    public void clear(){
        points = new Stack<>();
        colors = new Stack<>();
        sizes = new Stack<>();
        undonePoints = new Stack<>();
        undoneColours = new Stack<>();
        undoneSizes = new Stack<>();
        repaint();
    }
    public void undo(){
        if(!points.isEmpty()) {
            undonePoints.push(points.pop());
            undoneColours.push(colors.pop());
            undoneSizes.push(sizes.pop());
            repaint();
        }
    }
    public void redo(){
        if(!undonePoints.isEmpty()) {
            points.push(undonePoints.pop());
            colors.push(undoneColours.pop());
            sizes.push(undoneSizes.pop());
            repaint();
        }
    }

    class PenListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            addPoint(e.getX(), e.getY());
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            addPoint(e.getX(), e.getY());
            repaint();
        }
    }
}
