public class Plagiarism 
{
    public static void main(String[] argument)
    {
        //Reads in the texts
        Reader dataReader = new Reader();
        String[] texts = dataReader.getTexts();

        //Creates the GUI
        GUI graphics = new GUI(texts);
    }
}