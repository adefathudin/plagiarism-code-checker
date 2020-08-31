package Proses;

public class Token implements Comparable<Token>{

    private String token;
    private String lexeme;

    public Token(String token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    public String getTokenType() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return token + "\t" + lexeme;
    }

    @Override
    public int compareTo(Token o) {
        if (this.token == null || o.getTokenType() == null) {
            return 0;
        }
        return this.token.compareTo(o.getTokenType());
    }
}
