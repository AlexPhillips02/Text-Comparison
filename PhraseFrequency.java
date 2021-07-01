import java.util.*;

public class PhraseFrequency
{
    private String[] text1;
    private String[] text2;
    private ArrayList<String> phraseLocations = new ArrayList<String>();
    private int phrases;

    public int getFrequency(String text1, String text2, int phraseLength)
    {
        findPhraseFrequency(text1, text2, phraseLength);
        return phrases;
    }

    public void findPhraseFrequency(String text1, String text2, int phraseLength)
    {
        //Sets number of phrases to 0
        phrases = 0;
        phraseLocations.clear();

        //Splits the string
        this.text1 = text1.split("\\s+");
        this.text2 = text2.split("\\s+");
        
        //Calculates how many phrases there are
        phrases = loopText(phraseLength);
    }

    public int loopText(int phraseLength  )
    {
        int tempPhrases = 0;
        int count = 0;

        int i = 0;
        
        //Loops through the words of text 1
        while (i < text1.length) 
        {   
            int j = 0;
            //Loops through text 2 comparing it to text 1
            while (j < text2.length)
            {
                //Skips over quote marks in text 1
                if (text1[i].startsWith("\"")) 
                {
                    int quoteLengthText1 = 0;

                    while (!text1[i + quoteLengthText1].endsWith("\"")) 
                    {
                        quoteLengthText1++;
                    }

                    i = (i + quoteLengthText1 + 1);
                }
                else if (text1[i].endsWith("\""))
                {
                    i++;
                }


                if (text1[i].equals(text2[j]) && i < text1.length && j < text2.length) 
                {
                    count = checkForPhrase(i, j);

                    if (count > phraseLength) 
                    {
                        tempPhrases = tempPhrases + count;

                        String phrase = text1[i];
                        for (int k = i + 1; k < i + count; k++) 
                        {
                            phrase = phrase.concat(" " + text1[k]); 
                        }

                        i = i + count;
         
                        phraseLocations.add(phrase);
                        j = j + count;
                    }
                }
                else
                {
                    count = 0;
                }

                j++;
            }
            i++;
        }

        int phrasesPercentage = (int)((tempPhrases * 100) / text1.length);

        return phrasesPercentage;
    }

    public int checkForPhrase(int i, int j)
    {
        int tempi = i;
        int tempj = j; 
        
        int count = 0;

        while (tempi < text1.length && tempj < text2.length && text1[tempi].equals(text2[tempj])) 
        {
            if (text1[tempi].startsWith("\"") || text2[tempj].startsWith("\""))
            {
                return count;
            }
            
            count++;
            tempi++;
            tempj++;
        }

        return count;
    }

    public ArrayList<String> getLocations()
    {
        return phraseLocations;
    }
}