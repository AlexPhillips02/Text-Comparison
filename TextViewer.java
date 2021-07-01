import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;

public class TextViewer implements ActionListener
{
    private JFrame userInterface;
    private JEditorPane textArea = new JEditorPane();
    private JButton backButton;
    private String text;
    private Highlighter high = textArea.getHighlighter();

    public void createTextViewer(String text, ArrayList<String> phraseLocations)
    {
        userInterface = new JFrame("Text Viewer");
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();

        backButton = new JButton("Back");

        this.text = text;

        userInterface.setSize(600,600);

        backButton.addActionListener(this);
        panel.add(backButton);

        textArea.setText(text);
        textArea.setEditable(false);
        textArea.setSize(500,500);

        highlightText(phraseLocations);

        panel2.add(new JScrollPane(textArea));

        userInterface.add(panel, BorderLayout.NORTH);
        userInterface.add(panel2, BorderLayout.CENTER);
        userInterface.setVisible(true);
    }

    public void highlightText(ArrayList<String> phraseLocations)
    {
        for (int i = 0; i < phraseLocations.size(); i++) 
        {
            try 
            {
                String phrase = phraseLocations.get(i);
                
                int startPostion = text.indexOf(phrase);
                int endPosition = (startPostion + phrase.length());

                high.addHighlight(startPostion, endPosition, new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));


            } catch (BadLocationException e) 
            {
                System.out.println(e);
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == backButton) 
        {
            userInterface.setVisible(false);
            userInterface.dispose();
        }
    }
}
