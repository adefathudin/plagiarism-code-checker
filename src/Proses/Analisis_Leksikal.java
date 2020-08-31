/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Analisis_Leksikal {

    private BufferedReader reader;
    private char curr;
    private static final char EOF = (char) (-1);

    public Analisis_Leksikal(String file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        curr = read();
    }

    private char read() {
        try {
            return (char) (reader.read());
        } catch (IOException e) {
            e.printStackTrace();
            return EOF;
        }
    }

    private boolean isNumeric(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    public boolean isAlpha(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        return false;
    }

    public Token nextToken() {
        int state = 1;
        int numBuffer = 0;
        String alphaBuffer = "";
        int decBuffer = 0;
        boolean skipped = false;
        while (true) {
            if (curr == EOF && !skipped) {
                skipped = true;
            } else if (skipped) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            switch (state) {
                case 1:
                    switch (curr) {
                        case ' ':
                        case '\n':
                        case '\b':
                        case '\f':
                        case '\r':
                        case '\t':
                            curr = read();
                            continue;
                        case ';':
                            curr = read();
                            return new Token("S.M", ";");
                        case '+':
                            curr = read();
                            return new Token("P.O", "+");
                        case '-':
                            curr = read();
                            return new Token("M.O", "-");
                        case '*':
                            curr = read();
                            return new Token("T.O", "*");
                        case '/':
                            curr = read();
                            state = 14;
                            continue;
                        case ',':
                            curr = read();
                            return new Token("F.A", ",");
                        case '(':
                            curr = read();
                            return new Token("L.P", "(");
                        case ')':
                            curr = read();
                            return new Token("R.P", ")");
                        case '{':
                            curr = read();
                            return new Token("L.B", "{");
                        case '}':
                            curr = read();
                            return new Token("R.B", "}");
                        case '[':
                            curr = read();
                            return new Token("L.S", "[");
                        case '<':
                            curr = read();
                            return new Token("L.T", "<");
                        case '>':
                            curr = read();
                            return new Token("G.T", ">");
                        case ']':
                            curr = read();
                            return new Token("R.S", "]");
                        case '.':
                            curr = read();
                            return new Token("D.T", ".");
                        case '%':
                            curr = read();
                            return new Token("M.D", "%");
                        case '=':
                            curr = read();
                            state = 8;
                            continue;
                        case '!':
                            curr = read();
                            state = 9;
                            continue;
                        case '&':
                            curr = read();
                            state = 10;
                            continue;
                        case '|':
                            curr = read();
                            state = 11;
                            continue;
                        case '"':
                            curr = read();
                            state = 13;
                            alphaBuffer = "";
                            continue;
                        default:
                            state = 2;
                            continue;
                    }
                case 2:
                    if (isNumeric(curr)) {
                        numBuffer = 0;
                        numBuffer += (curr - '0');
                        state = 3;
                        curr = read();
                    } else {
                        state = 5;
                    }
                    continue;
                case 3:
                    if (isNumeric(curr)) {
                        numBuffer *= 10;
                        numBuffer += (curr - '0');
                        curr = read();
                    } else if (curr == '.') {
                        curr = read();
                        state = 4;
                    } else {
                        return new Token("N.M", "" + numBuffer);
                    }
                    continue;
                case 4:
                    if (isNumeric(curr)) {
                        decBuffer = 0;
                        decBuffer += (curr - '0');
                        state = 7;
                        curr = read();
                    } else {
                        return new Token("ERROR", "Invalid input: " + numBuffer + ".");
                    }
                    continue;
                case 7:
                    if (isNumeric(curr)) {
                        decBuffer *= 10;
                        decBuffer += (curr - '0');
                        curr = read();
                    } else {
                        return new Token("N.M", "" + numBuffer + "." + decBuffer);
                    }
                    continue;
                case 5:
                    if (isAlpha(curr) || curr == '_') {
                        alphaBuffer = "";
                        alphaBuffer += curr;
                        state = 6;
                        curr = read();
                    } else {
                        alphaBuffer = "";
                        alphaBuffer += curr;
                        curr = read();
                        return new Token("ERROR", "Invalid input:" + alphaBuffer);
                    }
                    continue;
                case 6:
                    if ((isAlpha(curr) || isNumeric(curr) || curr == '_')) {
                        alphaBuffer += curr;
                        curr = read();
                    } else {
                        if (alphaBuffer.equals("class") || alphaBuffer.equals("abstract") || alphaBuffer.equals("assert") || alphaBuffer.equals("break") || alphaBuffer.equals("const") || alphaBuffer.equals("boolean") || alphaBuffer.equals("byte") || alphaBuffer.equals("case") || alphaBuffer.equals("catch")
                                || alphaBuffer.equals("char") || alphaBuffer.equals("continue") || alphaBuffer.equals("default") || alphaBuffer.equals("do") || alphaBuffer.equals("double") || alphaBuffer.equals("else") || alphaBuffer.equals("enum") || alphaBuffer.equals("extends") || alphaBuffer.equals("final") || alphaBuffer.equals("finally") || alphaBuffer.equals("float") || alphaBuffer.equals("for") || alphaBuffer.equals("goto") || alphaBuffer.equals("if") || alphaBuffer.equals("implements") || alphaBuffer.equals("import") || alphaBuffer.equals("instanceof") || alphaBuffer.equals("int") || alphaBuffer.equals("interface") || alphaBuffer.equals("long") || alphaBuffer.equals("native") || alphaBuffer.equals("new") || alphaBuffer.equals("package") || alphaBuffer.equals("private") || alphaBuffer.equals("protected") || alphaBuffer.equals("public") || alphaBuffer.equals("return") || alphaBuffer.equals("short") || alphaBuffer.equals("static") || alphaBuffer.equals("super") || alphaBuffer.equals("switch") || alphaBuffer.equals("synchronized") || alphaBuffer.equals("String") || alphaBuffer.equals("strictfp") || alphaBuffer.equals("this") || alphaBuffer.equals("throw") || alphaBuffer.equals("throws") || alphaBuffer.equals("transient") || alphaBuffer.equals("try") || alphaBuffer.equals("void") || alphaBuffer.equals("volatile") || alphaBuffer.equals("false") || alphaBuffer.equals("true") || alphaBuffer.equals("null") || alphaBuffer.equals("while")) {
                            return new Token("K.W", "" + alphaBuffer);
                        } else if (alphaBuffer.equals("true") || alphaBuffer.equals("false")) {
                            return new Token("B.L", "" + alphaBuffer);
                        }
                        return new Token("I.D", "" + alphaBuffer);
                    }
                    continue;
                case 8:
                    if (curr == '=') {
                        curr = read();
                        return new Token("E.Q", "==");
                    } else {
                        return new Token("A.O", "=");
                    }
                case 9:
                    if (curr == '=') {
                        curr = read();
                        return new Token("N.E", "!=");
                    } else {
                        return new Token("ERROR", "Invalid input: !");
                    }
                case 10:
                    if (curr == '&') {
                        curr = read();
                        return new Token("L.A", "&&");
                    } else {
                        return new Token("ERROR", "Invalid input: &");
                    }
                case 11:
                    if (curr == '|') {
                        curr = read();
                        return new Token("L.O", "||");
                    } else {
                        return new Token("ERROR", "Invalid input: |");
                    }
                case 13:
                    if (curr == '"') {
                        curr = read();
                        return new Token("S.T", "\"" + alphaBuffer + "\"");
                    } else if (curr == '\n' || curr == EOF) {
                        curr = read();
                        return new Token("ERROR", "Invalid string literal");
                    } else {
                        alphaBuffer += curr;
                        curr = read();
                    }
                    continue;
                case 14:
                    if (curr == '/') {
                        state = 15;
                        curr = read();
                    } else if (curr == '*') {
                        state = 16;
                        curr = read();
                    } else {
                        return new Token("D.O", "/");
                    }
                    continue;
                case 15:
                    if (curr == '\n') {
                        state = 1;
                    }
                    curr = read();
                    continue;
                case 16:
                    if (curr == '*') {
                        state = 17;
                    }
                    curr = read();
                    continue;
                case 17:
                    if (curr == '/') {
                        curr = read();
                        state = 1;
                    } else {
                        curr = read();
                        state = 16;
                    }
                    continue;
            }
        }
    }
}
