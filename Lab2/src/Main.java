package Lab2.src;

public class Main {
    public static void main(String[] args) {
        try {
            String input = "Bob level apple. Harry Potter was a highly unusual boy in many ways.";

            StringBuffer sb = new StringBuffer(input);

            System.out.println("Original text:");
            System.out.println(sb);

            processText(sb);

            System.out.println("Processed text:");
            System.out.println(sb);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processText(StringBuffer text) {
        int length = text.length();
        int i = 0;

        while (i < length) {
            if (Character.isLetter(text.charAt(i))) {
                int start = i;
                
                while (i < length && Character.isLetter(text.charAt(i))) {
                    i++;
                }
                int end = i;

                char firstChar = text.charAt(start);
                char lowerFirst = Character.toLowerCase(firstChar);
                
                for (int k = start + 1; k < end; k++) {
                    char currentChar = text.charAt(k);
                    if (Character.toLowerCase(currentChar) == lowerFirst) {
                        text.deleteCharAt(k);
                        k--;   
                        end--; 
                        length--; 
                        i--; 
                    }
                }
            } else {
                i++;
            }
        }
    }
}