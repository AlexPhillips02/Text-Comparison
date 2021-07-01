import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;

public class GUI implements ActionListener
{
    private String[] texts;
    private WordFrequency WordFrequency = new WordFrequency();
    private PhraseFrequency phraseFrequencyText1 = new PhraseFrequency();
    private PhraseFrequency phraseFrequencyText2 = new PhraseFrequency();
    private ArrayList<String> phraseLocations = new ArrayList<String>();

    private JFrame userInface = new JFrame("Plagiarism Checker");
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JButton viewText1Button = new JButton("View");
    private JButton viewText2Button = new JButton("View");
    private JComboBox<String> comboText1;
    private JComboBox<String> comboText2;

    private JTable wordFrequencyTable = new JTable();
    private DefaultTableModel frequencyTableModel = new DefaultTableModel();
    private TableRowSorter<DefaultTableModel> sorter;

    private JComboBox<String> phraseLength;
    private JTextField wordSearch;
    private JButton wordSearchBut = new JButton("Search");

    private JTable text1PhraseTable = new JTable();
    private JTable text2PhraseTable = new JTable();
    private DefaultTableModel phraseTableModel = new DefaultTableModel();
    private DefaultTableModel phraseTableModel2 = new DefaultTableModel();
    private String[] textPhrasesHeaders = {"Pair", "Phrase Match (%)"};
    
    public GUI(String[] texts)
    {
        this.texts = texts;
        String[] wordFrequencyHeaders = {"Words", "Text 1", "Text 2"};

        for (int i = 0; i < wordFrequencyHeaders.length; i++) 
        {
            frequencyTableModel.addColumn(wordFrequencyHeaders[i]);
        }

        for (int i = 0; i < textPhrasesHeaders.length; i++) 
        {
            phraseTableModel.addColumn(textPhrasesHeaders[i]);
            phraseTableModel2.addColumn(textPhrasesHeaders[i]);
        }

        sorter = new TableRowSorter<DefaultTableModel>(frequencyTableModel);
        wordFrequencyTable.setModel(frequencyTableModel);
        wordFrequencyTable.setRowSorter(sorter);
        sorter.setRowFilter(null);

        text1PhraseTable.setModel(phraseTableModel);
        text2PhraseTable.setModel(phraseTableModel2);

        createGUI();
        updateTable();
    }

    public void createGUI()
    {
        String[] textNames = {"Text 1", "Text 2", "Text 3", "Text 4", "Text 5"};
        comboText1 = new JComboBox<>(textNames);
        comboText2 = new JComboBox<>(textNames);

        //Creates window and sets size
        userInface.setSize(600,600);
        userInface.setResizable(false);
        userInface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInface.setLayout(new GridLayout(3, 1, 5, 5));

        comboText1.addActionListener(this);
        comboText2.addActionListener(this);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 0;
        
        panel.add(comboText1, gbc);
        panel.add(viewText1Button, gbc);
        panel.add(comboText2, gbc);
        panel.add(viewText2Button, gbc);
        
        viewText1Button.addActionListener(this);
        viewText2Button.addActionListener(this);
        
        gbc.gridy = 1;
        panel.add(new JLabel("Phrase Length: "), gbc);
        String[] phraseLengthText = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        phraseLength = new JComboBox<>(phraseLengthText);
        phraseLength.addActionListener(this);
        panel.add(phraseLength, gbc);
        
        gbc.gridy = 2;
        panel.add(new JLabel("Search Word :"), gbc);
        wordSearch = new JTextField(18);

        gbc.gridwidth = 2;
        panel.add(wordSearch, gbc);
        wordSearchBut.addActionListener(this);

        gbc.gridwidth = 1;
        panel.add(wordSearchBut, gbc);

        panel2.add(new JScrollPane(wordFrequencyTable));

        panel3.setLayout(new GridLayout(1, 2, 5, 0));
        panel3.add(new JScrollPane(text1PhraseTable));
        panel3.add(new JScrollPane(text2PhraseTable));

        userInface.add(panel);
        userInface.add(panel2);
        userInface.add(panel3);

        userInface.setVisible(true);
    }

    public void updateTable()
    {
        sorter.setRowFilter(null);
        
        WordFrequency.wordFrequecy(texts[comboText1.getSelectedIndex()], texts[comboText2.getSelectedIndex()]);

        ArrayList<String> words = WordFrequency.getWords();
        ArrayList<Integer> text1 = WordFrequency.getText1();
        ArrayList<Integer> text2 = WordFrequency.getText2();

        frequencyTableModel.setRowCount(0);

        TableColumnModel columnModel = wordFrequencyTable.getColumnModel();
        TableColumn tableColumn = columnModel.getColumn(1);
        tableColumn.setHeaderValue(comboText1.getSelectedItem().toString());
        tableColumn = columnModel.getColumn(2);
        tableColumn.setHeaderValue(comboText2.getSelectedItem().toString());

        Object[] data = new Object[words.size()];

        for (int j = 0; j < words.size(); j++) 
        {
            data[0] = words.get(j);
            data[1] = text1.get(j);
            data[2] = text2.get(j);

            frequencyTableModel.addRow(data);
        }

        phraseTableModel.setRowCount(0);
        phraseTableModel2.setRowCount(0);

        Object[] columnData = new Object[2];
        Object[] columnData2 = new Object[2];

        int phraseLengthInt = phraseLength.getSelectedIndex() + 2;

        for (int i = 0; i < texts.length; i++) 
        {
            if (i != comboText1.getSelectedIndex()) 
            {
                columnData[0] = "" + comboText1.getSelectedItem().toString() + " -- " + comboText2.getItemAt(i);
                columnData[1] = phraseFrequencyText1.getFrequency(texts[comboText1.getSelectedIndex()], texts[i], phraseLengthInt);

                phraseTableModel.addRow(columnData);
            }

            if (i != comboText2.getSelectedIndex()) 
            {
                columnData2[0] = "" + comboText2.getSelectedItem().toString() + " -- " + comboText1.getItemAt(i);
                columnData2[1] = phraseFrequencyText2.getFrequency(texts[comboText2.getSelectedIndex()], texts[i], phraseLengthInt);

                phraseTableModel2.addRow(columnData2);
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == comboText1 || e.getSource() == comboText2) 
        {
            updateTable();
        }
        else if (e.getSource() == viewText1Button)
        {
            int phraseLengthInt = phraseLength.getSelectedIndex() + 2;

            phraseFrequencyText1.findPhraseFrequency(texts[comboText1.getSelectedIndex()],texts[comboText2.getSelectedIndex()], phraseLengthInt);
            phraseLocations = phraseFrequencyText1.getLocations();

            TextViewer textViewer1 = new TextViewer();
            textViewer1.createTextViewer(texts[comboText1.getSelectedIndex()], phraseLocations);
        }
        else if (e.getSource() == viewText2Button)
        {
            int phraseLengthInt = phraseLength.getSelectedIndex() + 2;

            phraseFrequencyText2.findPhraseFrequency(texts[comboText2.getSelectedIndex()],texts[comboText1.getSelectedIndex()], phraseLengthInt);
            phraseLocations = phraseFrequencyText2.getLocations();

            TextViewer textViewer2 = new TextViewer();
            textViewer2.createTextViewer(texts[comboText2.getSelectedIndex()], phraseLocations);
        }
        else if (e.getSource() == phraseLength) 
        {
            updateTable();
        }
        else if (e.getSource() == wordSearchBut) 
        {
            String text = wordSearch.getText();
            if (text.trim().length() == 0) 
            {
                sorter.setRowFilter(null);
            } else 
            {
                sorter.setRowFilter(RowFilter.regexFilter(text));
            }
        }
    }
}