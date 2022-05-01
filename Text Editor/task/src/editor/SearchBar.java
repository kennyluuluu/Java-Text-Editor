package editor;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.regex.*;

public class SearchBar extends JPanel {
    JTextArea textArea;
    JTextField searchText;
    JButton search;
    JButton prev;
    JButton next;
    JCheckBox useRegex;

    Pattern pattern;
    Matcher matcher;
    ArrayList<Integer> matchesBegin = new ArrayList<>();
    ArrayList<Integer> matchesEnd = new ArrayList<>();
    int numberOfMatches;
    int matchPosition;

    ActionListener searchAction = e -> {
        // initialize new search
        matchesBegin.clear();
        matchesEnd.clear();
        matchPosition = 0;

        if (useRegex.isSelected()) {
            pattern = Pattern.compile(searchText.getText());
        } else {
            pattern = Pattern.compile(searchText.getText(), Pattern.LITERAL);
        }
        matcher = pattern.matcher(textArea.getText());

        while (matcher.find()) {
            matchesBegin.add(matcher.start());
            matchesEnd.add(matcher.end());
        }
        numberOfMatches = matchesBegin.size();
        if (numberOfMatches > 0) {
            textArea.setCaretPosition(matchesEnd.get(matchPosition));
            textArea.select(matchesBegin.get(matchPosition), matchesEnd.get(matchPosition));
            textArea.grabFocus();
        }
    };

    ActionListener prevAction = e -> {
        matchPosition--;
        if (matchPosition < 0) {
            matchPosition = numberOfMatches - 1;
        }
        textArea.setCaretPosition(matchesEnd.get(matchPosition));
        textArea.select(matchesBegin.get(matchPosition), matchesEnd.get(matchPosition));
        textArea.grabFocus();
    };

    ActionListener nextAction = e -> {
        matchPosition++;
        if (matchPosition >= numberOfMatches) {
            matchPosition = 0;
        }
        textArea.setCaretPosition(matchesEnd.get(matchPosition));
        textArea.select(matchesBegin.get(matchPosition), matchesEnd.get(matchPosition));
        textArea.grabFocus();
    };

    public SearchBar(JTextArea textArea) {
        this.textArea = textArea;
        searchText = new JTextField(16);
        searchText.setName("SearchField");

        search = new JButton();
        try {
            search.setIcon(new ImageIcon(new ImageIcon(
                    new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Search_Icon.svg/1024px-Search_Icon.svg.png"))
                    .getImage()
                    .getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH)));
        } catch (MalformedURLException e) {
            search.setText("Search");
        }
        search.setName("StartSearchButton");
        search.addActionListener(searchAction);

        prev = new JButton();
        try {
            prev.setIcon(new ImageIcon(new ImageIcon(
                    new URL("https://www.clipartmax.com/png/middle/177-1773536_arrow-left-icon-next-and-previous-buttons-png.png"))
                    .getImage()
                    .getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH)));
        } catch (MalformedURLException e) {
            prev.setText("Prev");
        }
        prev.setName("PreviousMatchButton");
        prev.addActionListener(prevAction);

        next = new JButton();
        try {
            next.setIcon(new ImageIcon(new ImageIcon(
                    new URL("https://cdn-icons-png.flaticon.com/512/130/130884.png"))
                    .getImage()
                    .getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH)));
        } catch (MalformedURLException e) {
            next.setText("Next");
        }
        next.setName("NextMatchButton");
        next.addActionListener(nextAction);

        useRegex = new JCheckBox("Use regex", false);
        useRegex.setName("UseRegExCheckbox");

        add(searchText);
        add(search);
        add(prev);
        add(next);
        add(useRegex);
    }
}
