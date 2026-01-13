package Lab4.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Головний клас.
 * Запускає обробку тексту через об'єкти.
 */
public class Main {
    public static void main(String[] args) {
        try {
            String rawText = "Bob  level   apple.    Harry   Potter  was a   highly\tunusual boy.";

            System.out.println("--- Original ---");
            System.out.println(rawText);

            Text textObject = new Text(rawText);

            System.out.println("\n--- Parsed ---");
            System.out.println(textObject);

            System.out.println("\n--- Result ---");
            textObject.processVariant();
            System.out.println(textObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Клас Літера.
 */
class Letter {
    private final char value;

    public Letter(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

/**
 * Інтерфейс для частин речення.
 */
interface SentenceMember {
    String toString();
}

/**
 * Клас Розділовий знак.
 */
class Punctuation implements SentenceMember {
    private final String symbol;

    public Punctuation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

/**
 * Клас Слово.
 */
class Word implements SentenceMember {
    private final List<Letter> letters;

    public Word(String wordString) {
        letters = new ArrayList<>();
        for (char c : wordString.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    /**
     * Логіка Лаб.2 .
     */
    public void removeSubsequentFirstLetter() {
        if (letters.isEmpty()) return;

        char firstChar = Character.toLowerCase(letters.get(0).getValue());

        for (int i = letters.size() - 1; i > 0; i--) {
            char currentChar = Character.toLowerCase(letters.get(i).getValue());
            if (currentChar == firstChar) {
                letters.remove(i);
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Letter l : letters) {
            sb.append(l.toString());
        }
        return sb.toString();
    }
}

/**
 * Клас Речення.
 */
class Sentence {
    private final List<SentenceMember> members;

    public Sentence(String sentenceString) {
        members = new ArrayList<>();
        String[] parts = sentenceString.trim().split("(?=[,.!?])|\\s+");

        for (String part : parts) {
            if (part.matches("[,.!?]")) {
                members.add(new Punctuation(part));
            } else if (!part.isEmpty()) {
                members.add(new Word(part));
            }
        }
    }

    public void processWords() {
        for (SentenceMember member : members) {
            if (member instanceof Word) {
                ((Word) member).removeSubsequentFirstLetter();
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < members.size(); i++) {
            SentenceMember member = members.get(i);
            sb.append(member.toString());
            if (member instanceof Word && i < members.size() - 1 && members.get(i+1) instanceof Word) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}

/**
 * Клас Текст.
 */
class Text {
    private final List<Sentence> sentences;

    public Text(String rawText) {
        sentences = new ArrayList<>();
        String cleanedText = rawText.replaceAll("[\\t ]+", " ");
        String[] splitSentences = cleanedText.split("(?<=[.!?]) ");

        for (String s : splitSentences) {
            sentences.add(new Sentence(s));
        }
    }

    public void processVariant() {
        for (Sentence sentence : sentences) {
            sentence.processWords();
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Sentence sentence : sentences) {
            sb.append(sentence.toString()).append(" ");
        }
        return sb.toString().trim();
    }
}