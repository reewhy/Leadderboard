public class TagGenerator {
    public static String generateTag(String firstName, String lastName){
        StringBuilder result = new StringBuilder();
        String[] nameletters = firstName.toUpperCase().split("");
        int i = 0;
        int numbersOfConsonants = 0;
        String el = "";
        for(String letter: nameletters){
            switch (letter) {
                case "A", "E", "I", "O", "U" -> {
                    if (el.equals("")) {
                        el = letter.toUpperCase();
                    }
                }
                default -> {
                    if (i != 3) {
                        result.append(letter.toUpperCase());
                        i++;
                        numbersOfConsonants++;
                    }
                }
            }
        }
        if(numbersOfConsonants == 2){
            result.append(el);
        }

        i = 0;
        numbersOfConsonants = 0;
        el = "";
        nameletters = lastName.toUpperCase().split("");
        for(String letter: nameletters){
            switch (letter) {
                case "A", "E", "I", "O", "U" -> {
                    if (el.equals("")) {
                        el = letter.toUpperCase();
                    }
                }
                default -> {
                    if (i != 3) {
                        result.append(letter.toUpperCase());
                        i++;
                        numbersOfConsonants++;
                    }
                }
            }
        }
        if(numbersOfConsonants == 2){
            result.append(el);
        }
        return result.toString();
    }
}
