import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class scuffed {
    static String res = "";
    static List<String> reserved_words = List.of(
            "match",
            "some",
            "of",
            "to"

    );

    static List<String> identifiers = List.of(
            "<word>",
            "<space>",
            "<digit>",
            "<dot>"
    );

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(Path.of("C:\\Users\\zachr\\IdeaProjects\\plaintextToRegex\\test.txt"));
        while (sc.hasNextLine())
            handle(sc.nextLine());

        System.out.println(res);
    }

    public static void handle(String line) {
        String input[] = line.split(" ");
        for (int i = 0; i < input.length; i++) {
            String s = input[i];

            if (reserved_words.contains(s)) {
                switch (s) {
                    case "some" -> {
                        if (input[i + 1].equals("of")) {
                            res += String.format("%s+", resolve(input[i + 2]));
                            i += 2;
                        }
                    }
                    case "of" -> {
                        res += String.format("%s{%s}", resolve(input[i + 1]), input[i - 1]);
                        i++;
                    }
                    case "to" -> {
                        res += String.format("[%s-%s]", input[i - 1], input[i + 1]);
                        i++;
                    }
                }
            } else {
                if (!reserved_words.contains(input[i + 1]))
                    res += resolve(s);
            }
        }
    }

    public static String resolve(String s) {
        s = s.replaceAll("\"", "");
        return switch (s) {
            case "<word>" -> "\\w";
            case "<space>" -> "\\s";
            case "<digit>" -> "\\d";
            case "<dot>" -> "\\.";
            default -> s.length() == 1 ? s : "(?:" +  s + ")";
        };

    }

}
