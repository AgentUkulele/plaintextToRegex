import java.util.*;

public class Lexer {
    static class Token {
        public enum Type {
            MatchStart,
            MatchEnd,
            Some,
            Any,
            Range,
            Identifier,
            EOF
        }

        private Type t;
        private String value = null;

        public Token(Type t) {
            this.t = t;
        }

        public Token(Type t, String v) {
            this.t = t;
            this.value = v;
        }

        public String value() {
            return this.value;
        }

        public Type type() {
            return this.t;
        }

    }
    static List<String> reserved_words = List.of(
            "match",
            "some",
            "to",
            "any"
    );

    static Map<String, String> identifiers = Map.of(
            "<word>", "\\w",
            "<space>", "\\s",
            "<digit>", "\\d",
            "<dot>", "\\."
    );

    private static int pos = 0;
    public static Queue<Token> tokenized = new PriorityQueue<Token>();

    public Lexer(String src) { Lexer(src); }

    public Token next() {
        if (tokenized.size() == 0)
            return new Token(Token.Type.EOF);
        return tokenized.poll();
    }


    public static void Lexer(String src) {
        String[] source = src.split(";");

        for (String entry : source) {
            for (String s : entry.split(" ")) {
                handle(s);
            }

        }
    }

    private static void handle(String s) {
        if (reserved_words.contains(s)) {
            switch (s) {
                case "match" -> tokenized.add(new Token(Token.Type.MatchStart));
                case "some" -> tokenized.add(new Token(Token.Type.Some));
                case "any" -> tokenized.add(new Token(Token.Type.Any));
                case "to" -> tokenized.add(new Token(Token.Type.Range));
            }
        } else {
            s = process(s);
            tokenized.add(new Token(Token.Type.Identifier, s));
        }
        pos++;
    }

    private static String process(String s) {
        return identifiers.getOrDefault(s, s);
    }
}
