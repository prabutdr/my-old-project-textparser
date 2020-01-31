package commom;

import java.util.Comparator;
import java.util.StringTokenizer;

public class NGramDescComparator implements Comparator<String> {
    public int compare(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        } else if (word1 == null) {
            return 1;
        } else if (word2 == null) {
            return -1;
        }

        int len1 = new StringTokenizer(word1, " ").countTokens();
        int len2 = new StringTokenizer(word2, " ").countTokens();
        
        if(len1 < len2) {
        	return 1;
        }
        else if(len1 > len2) {
        	return -1;
        }
        
        return word2.length() - word1.length();
    }
}