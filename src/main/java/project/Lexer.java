package project;

import java.util.ArrayList;
import java.util.List;

/**
 * The lexer works through three main functions:
 *
 *  - {@link #lex()}, which repeatedly calls lexToken() and skips whitespace
 *  - {@link #lexToken()}, which lexes the next token
 *  - {@link CharStream}, which manages the state of the lexer and literals
 *
 * If the lexer fails to parse something (such as an unterminated string) you
 * should throw a {@link ParseException} with an index at the character which is
 * invalid or missing.
 *
 * The {@link #peek(String...)} and {@link #match(String...)} functions are
 * helpers you need to use, they will make the implementation a lot easier.
 */
public final class Lexer {

    private final CharStream chars;

    public Lexer(String input) {
        chars = new CharStream(input);
    }

    /**
     * Repeatedly lexes the input using {@link #lexToken()}, also skipping over
     * whitespace where appropriate.
     */
    public List<Token> lex() {

        List<Token> tokens = new ArrayList<>();
        while(chars.has(0)){
            //match
            if(match("[ \b\n\r\t]")){
                chars.skip();
            }
            else{
                tokens.add(lexToken());

            }
        }

        //entry point of code, looks @ 1st character and checks if i have more chars that needs to be checked
        //delegate to identifier or numbers..

        //abc + 123
        //'a' --> has to go on --> lexToken --> decide what the next token to generate (check if variable or character

        //calls lexToken()
        throw new UnsupportedOperationException(); //TODO

    }
    /**
     * This method determines the type of the next token, delegating to the
     * appropriate lex method. As such, it is best for this method to not change
     * the state of the char stream (thus, use peek not match).
     *
     * The next character should start a valid token since whitespace is handled
     * by {@link #lex()}
     */
    public Token lexToken() {

        if (peek("[[+\\-]? [0-9]+ ('.' [0-9]+)?]")) {
            return lexNumber();
        }
        else if(peek("\'")){
            return lexCharacter();
        }
        else if(peek("\"")){
            return lexString();
        }
        /*else if(peek("'\\' [bnrt'\"\\\\]")){ TODO
            return lexEscape();
        } */
        else if(peek("[<>!=] '='? | 'any character'")){
            return lexOperator();
        }
        else if(peek("[A-Za-z_] [A-Za-z0-9_-]*")){
            return lexIdentifier();
        }
        else {
            //handle other? -- TODO
        }

        return lexNumber();
    }

    public Token lexIdentifier() {
        throw new UnsupportedOperationException(); //TODO
    }

    public Token lexNumber() {
        //how to access number

        match("[+-]");
        while(match("[0-9]"));
        if (match("\\.","[0-9]")  )
        {
            while(match("[0-9]")) ;
            return chars.emit(Token.Type.DECIMAL);
        }
        return chars.emit(Token.Type.INTEGER);


    }
    public Token lexCharacter() {
        Token token = new Token(Token.Type.CHARACTER, "[.]", 10);
        return token;
        //throw new UnsupportedOperationException(); //TODO
    }

    public Token lexString() {
        throw new UnsupportedOperationException(); //TODO
    }

    public void lexEscape() {
        throw new UnsupportedOperationException(); //TODO
    }

    public Token lexOperator() {
        throw new UnsupportedOperationException(); //TODO
    }

    /**
     * Returns true if the next sequence of characters match the given patterns,
     * which should be a regex. For example, {@code peek("a", "b", "c")} would
     * return true if the next characters are {@code 'a', 'b', 'c'}.
     */
    public boolean peek(String... patterns) { //TODO (in lecture)
            for ( int i = 0; i < patterns.length; i++ ) {
                if ( !chars.has(i) || !String.valueOf(chars.get(i)).matches(patterns[i]) ) {
                    return false;
                }
            }
            return true;
    }

    /**
     * Returns true in the same way as {@link #peek(String...)}, but also
     * advances the character stream past all matched characters if peek returns
     * true. Hint - it's easiest to have this method simply call peek.
     */
    public boolean match(String... patterns) {  //TODO (in lecture)
        boolean peek = peek(patterns);
        if(peek){
            for(int i=0;i<patterns.length;i++){
                chars.advance();
            }
        }
        //remove
        return peek;
    }

    /**
     * A helper class maintaining the input string, current index of the char
     * stream, and the current length of the token being matched.
     *
     * You should rely on peek/match for state management in nearly all cases.
     * The only field you need to access is {@link #index} for any {@link
     * ParseException} which is thrown.
     */
    public static final class CharStream {

        private final String input;
        private int index = 0;
        private int length = 0;

        public CharStream(String input) {
            this.input = input;
        }

        public boolean has(int offset) {
            return index + offset < input.length();
        }

        public char get(int offset) {
            return input.charAt(index + offset);
        }

        public void advance() {
            index++;
            length++;
        }

        public void skip() {
            length = 0;
        }

        public Token emit(Token.Type type) {
            //keep calling after matching
            int start = index - length;
            skip();
            return new Token(type, input.substring(start, index), start);
        }

    }

}


//what gets returned from lexNumber() etc.? is the string literal the actual contents of char stream appended?
//lex token uses regex to identify which type of token we've found, but then we also need regex in the
// individual methods for each type of token to see if we find more in char stream for same token>
//how do we deal with indexing? do you call emit once or each time you append a new character?
// confused as to what happens in lex()? we know you call lextoken but we dont really understand this methods function
