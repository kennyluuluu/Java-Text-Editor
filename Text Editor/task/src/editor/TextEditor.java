package editor;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {
    public TextEditor() {
        setTitle("TextEditor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");

        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollableTextArea, BorderLayout.CENTER);

        ToolBar toolBar = new ToolBar(textArea);
        add(toolBar, BorderLayout.NORTH);

        // MENU BAR
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setName("MenuOpen");
        loadMenuItem.addActionListener(toolBar.openAction);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(toolBar.saveAction);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(e -> {
            dispose();
        });

        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem startSearch = new JMenuItem("Start search");
        startSearch.setName("MenuStartSearch");
        startSearch.addActionListener(toolBar.searchBar.searchAction);

        JMenuItem prevMatch = new JMenuItem("Previous Match");
        prevMatch.setName("MenuPreviousMatch");
        prevMatch.addActionListener(toolBar.searchBar.prevAction);

        JMenuItem nextMatch = new JMenuItem("Next Match");
        nextMatch.setName("MenuNextMatch");
        nextMatch.addActionListener(toolBar.searchBar.nextAction);

        JMenuItem useRegExp = new JMenuItem("Use regular expressions");
        useRegExp.setName("MenuUseRegExp");
        useRegExp.addActionListener(e -> {
            toolBar.searchBar.useRegex.setSelected(true);
        });

        searchMenu.add(startSearch);
        searchMenu.add(prevMatch);
        searchMenu.add(nextMatch);
        searchMenu.add(useRegExp);
        menuBar.add(searchMenu);

        setVisible(true);
    }
}
