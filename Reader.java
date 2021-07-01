import java.io.BufferedReader;
import java.io.FileReader;

public class Reader
{
    String[] texts = {"", "", "", "", ""};

    public Reader()
    {
        String[] filePaths = {"Project3TestFiles/test1.txt", "Project3TestFiles/test2.txt", "Project3TestFiles/test3.txt", "Project3TestFiles/test4.txt", "Project3TestFiles/test5.txt"};

        FileReader reader;
        BufferedReader bufferedReader;
        StringBuilder tmpString;
        String line; 

        try 
        {
            for (int i = 0; i < texts.length; i++) 
            {
                reader = new FileReader(filePaths[i]);
                bufferedReader = new BufferedReader(reader);
                tmpString = new StringBuilder();

                //Loops through file until no lines remain
                while ((line = bufferedReader.readLine()) != null) 
                {
                    //Adds the line to a stringbuilder
                    tmpString.append(line.toLowerCase());
                }
                
                //Stores the stringbuilder in an array
                texts[i] = texts[i] + tmpString;

                //Regex statement to remove all punctuation and double spaces
                texts[i] = texts[i].replaceAll("[^a-z\" ]", "");
                texts[i] = texts[i].replaceAll("  ", " ");
            }         

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String[] getTexts()
    {
        return(texts);
    }
}