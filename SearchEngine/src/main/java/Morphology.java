import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Morphology {
    private static LuceneMorphology luceneMorphology;

    static {
        try {
            luceneMorphology = new RussianLuceneMorphology();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void morphologyList(String text) {
        String[] words = text.split("\\s");
        Map<String, Integer> checkRepeatWords = new HashMap<>();
        String pattern = "[^а-яё]";
        for (String word : words) {
            word = word.toLowerCase().replaceAll(pattern, "");
            List<String> checkMorphInfo = luceneMorphology.getMorphInfo(word);
            if (!checkMorphInfo.get(0).contains("МЕЖД") &&
                    !checkMorphInfo.get(0).contains("ПРЕДЛ") &&
                    !checkMorphInfo.get(0).contains("СОЮЗ")) {
                List<String> wordBaseForm = luceneMorphology.getNormalForms(word);
                if (checkRepeatWords.containsKey(word)) {
                    checkRepeatWords.put(wordBaseForm.get(0), checkRepeatWords.get(word) + 1);
                } else {
                    checkRepeatWords.put(wordBaseForm.get(0), 1);
                }
            }
        }
        System.out.println(checkRepeatWords);
    }

}
