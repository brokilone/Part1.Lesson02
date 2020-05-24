package Task06_2;

import java.util.Random;

/**
 * Libraru
 * created by Ksenya_Ushakova at 12.05.2020
 */
public class Library {
    private String[] words;


    public Library() {
        words = new String[]{"the", "and", "end", "have", "for", "that", "with", "this", "from", "they", "which",
                "say", "will", "what", "there", "make", "who", "know", "time", "take", "some", "year",
                "into", "more", "about", "now", "last", "other", "people", "also", "only", "when", "look",
                "use", "day", "between", "tell", "down", "back", "over", "work", "become", "part", "each",
                "ask", "yes", "world", "earth", "shall"};

    }

    public String[] getWords(){
        return words;
    }
}