import java.util.*;

public class WordFrequency
{
    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<Integer> text1Occurances = new ArrayList<Integer>();
    private ArrayList<Integer> text2Occurances = new ArrayList<Integer>();

    public void wordFrequecy(String text1, String text2)
    {
        //Removes speach marks
        text1 = text1.replaceAll("[^a-z ]", "");
        text2 = text2.replaceAll("[^a-z ]", "");

        //Splits the text into two strings
        String[] words1 = text1.split("\\s+");
        String[] words2 = text2.split("\\s+");

        //List that stores the words and number of occurances in each text
        words.clear();
        text1Occurances.clear();
        text2Occurances.clear();

        //Counts how many occurances looping through text 1
        int text1Count;
        int text2Count;

        for (int i = 0; i < words1.length; i++) 
        {
            text1Count = 0;
            text2Count = 0;

            for (int j = 0; j < words1.length; j++) 
            {
                if (words1[i].equals(words1[j]) && !words.contains(words1[i]))
                {
                    text1Count++;
                }
            }

            for (int x = 0; x < words2.length; x++) 
            {
                if (words1[i].equals(words2[x]) && !words.contains(words1[i])) 
                {
                    text2Count++;
                }
            }

            if (!words.contains(words1[i])) 
            {
                words.add(words1[i]);
                text1Occurances.add(text1Count);
                text2Occurances.add(text2Count);
            }
        }

        //Counts how many occurances looping through text 2
        for (int i = 0; i < words2.length; i++) 
        {
            text2Count = 0;
            
            if (!words.contains(words2[i]))
            {
                for (int j = 0; j < words2.length; j++) 
                {
                    if (words2[i].equals(words2[j]))
                    {
                        text2Count++;
                    }
                }
    
                words.add(words2[i]);
                text1Occurances.add(0);
                text2Occurances.add(text2Count);
            }
        }
    }

    public ArrayList<String> getWords()
    {
        return words;
    }

    public ArrayList<Integer> getText1()
    {
        return text1Occurances;
    }

    public ArrayList<Integer> getText2()
    {
        return text2Occurances;
    }
}