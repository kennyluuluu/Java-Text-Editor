package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ToolBar extends JPanel {
    JButton save = new JButton();
    JButton load = new JButton();
    JFileChooser jfc = new JFileChooser(".");;
    JTextArea textArea;

    SearchBar searchBar;

    ActionListener saveAction = e -> {
        jfc.setVisible(true);
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try (FileWriter writer = new FileWriter(selectedFile)){
                writer.write(textArea.getText());
            } catch (IOException exception) {
                System.out.printf("An exception occurred %s", exception.getMessage());
            }
        }
    };

    ActionListener openAction = e -> {
        jfc.setVisible(true);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                textArea.setText(new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath()))));
            } catch (IOException exception) {
                textArea.setText("");
                System.out.println("Cannot open file");
            }
        }
    };

    private void initializeOpenButton() {
        try {
            load.setIcon(new ImageIcon(new ImageIcon(
                    new URL("https://www.kindpng.com/picc/m/335-3354776_computer-icons-document-file-folders-microsoft-word-open.png"))
                    .getImage()
                    .getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH)));
        } catch (MalformedURLException e) {
            load.setText("Open");
        }
        load.addActionListener(openAction);
        add(load);
    }

    private void initializeSaveButton() {
        try {
            save.setIcon(new ImageIcon(new ImageIcon(
                    new URL("https://cdn1.iconfinder.com/data/icons/ios-11-glyphs/30/save-512.png"))
                    .getImage()
                    .getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH)));
        } catch (MalformedURLException e) {
            save.setText("Save");
        }
        save.addActionListener(saveAction);
        add(save);
    }

    public ToolBar(JTextArea textArea) {
        this.textArea = textArea;

        initializeOpenButton();
        initializeSaveButton();

        jfc.setVisible(false);
        add(jfc);
        jfc.setName("FileChooser");
        load.setName("OpenButton");
        save.setName("SaveButton");

        searchBar = new SearchBar(textArea);
        add(searchBar);
    }
}
