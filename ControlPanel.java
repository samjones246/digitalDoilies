import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    JButton clearButton, undoButton, redoButton, saveButton, colorButton;
    JSpinner penSizeInput, sectorsSpinner;
    JCheckBox toggleShowLines, toggleReflectPoints, toggleErase;
    DrawPanel drawPanel;
    ControlPanel(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
        setLayout(new GridLayout(2, 5));
        penSizeInput = new JSpinner(new SpinnerNumberModel(drawPanel.getPenSize(), 0, 99, 1));
        toggleErase = new JCheckBox("Erase Mode", drawPanel.isEraseMode());
        toggleShowLines = new JCheckBox("Show Lines", drawPanel.isShowLines());
        toggleReflectPoints = new JCheckBox("Reflect Points", drawPanel.isReflectPoints());
        sectorsSpinner = new JSpinner(new SpinnerNumberModel(drawPanel.getNumberOfSectors(), 0, 99, 1));
        clearButton = new JButton("CLEAR");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        saveButton = new JButton("SAVE");
        colorButton = new JButton();
        colorButton.setPreferredSize(new Dimension(20, 20));
        colorButton.setBackground(drawPanel.getPenColour());

        JPanel penSizePanel = new JPanel();
        penSizePanel.add(new JLabel("Pen Size: "));
        penSizePanel.add(penSizeInput);

        JPanel penColourPanel = new JPanel();
        penColourPanel.add(new JLabel("Pen Colour: "));
        penColourPanel.add(colorButton);

        JPanel sectorsPanel = new JPanel();
        sectorsPanel.add(new JLabel("Sectors: "));
        sectorsPanel.add(sectorsSpinner);

        add(penSizePanel);
        add(toggleErase);
        add(toggleReflectPoints);
        add(undoButton);
        add(saveButton);
        add(penColourPanel);
        add(sectorsPanel);
        add(toggleShowLines);
        add(redoButton);
        add(clearButton);

        clearButton.addActionListener(e -> drawPanel.clear());
        undoButton.addActionListener(e -> drawPanel.undo());
        redoButton.addActionListener(e -> drawPanel.redo());
        colorButton.addActionListener(e -> {
            drawPanel.setPenColour(JColorChooser.showDialog(null, "Choose Colour", drawPanel.getPenColour()));
            colorButton.setBackground(drawPanel.getPenColour());
        });
        toggleShowLines.addItemListener(e -> drawPanel.setShowLines(toggleShowLines.isSelected()));
        toggleReflectPoints.addItemListener(e -> drawPanel.setReflectPoints(toggleReflectPoints.isSelected()));
        toggleErase.addItemListener(e -> drawPanel.setEraseMode(toggleErase.isSelected()));
        penSizeInput.addChangeListener(e -> drawPanel.setPenSize((int)penSizeInput.getValue()));
        sectorsSpinner.addChangeListener(e -> drawPanel.setNumberOfSectors((int)sectorsSpinner.getValue()));
        saveButton.addActionListener(e -> drawPanel.saveImage());
    }
}
