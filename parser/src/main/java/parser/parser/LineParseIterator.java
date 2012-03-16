package parser.parser;

import parser.common.ParseException;
import parser.configuration.Config;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 15.03.12
 */
public class LineParseIterator implements Iterable<Map.Entry<String, String>>, Iterator<Map.Entry<String, String>> {
    
    private int charIdx = 0;
    private String parseString;
    private Config conf;

    public LineParseIterator(Config conf, String parseString) {
        this.conf = conf;
        this.parseString = parseString;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return new LineParseIterator(conf, parseString);
    }

    @Override
    public boolean hasNext() {
        return hasStringBracketsOrComa(parseString);
    }

    private boolean hasStrBrackets(String str) {
        return str.indexOf(String.valueOf(conf.getLeftBracket()), charIdx) >= 0
                && str.indexOf(String.valueOf(conf.getRightBracket()), charIdx) >= 0;
    }
    
    private boolean hasStringBracketsOrComa(String str) {
        return hasStrBrackets(str) || str.indexOf(String.valueOf(conf.getClassDelimiter()), charIdx) >= 0;
    }

    @Override
    public Map.Entry<String, String> next() {
//        StrEntry objEntry = new StrEntry();
        if(isIndexInBounds()) {
            if(charIdx == 0) { // First class
                if(Character.isAlphabetic(parseString.charAt(charIdx))) {
                    int leftBrIndex = parseString.indexOf(conf.getLeftBracket(), charIdx);
                    charIdx += leftBrIndex; // add class name size
                    String className = parseString.substring(0, leftBrIndex).trim();
                    String classContent = getClassContent();
                    charIdx ++; // add right brackets size ")"
                    return new StrEntry(className, classContent);
                } else {
                    throw new ParseException("Class name have to begin from alphabetic character");
                }
            } else if(parseString.charAt(charIdx) == conf.getClassDelimiter()) { // Next class, comma separated
                charIdx++; // skip comma sign
                int leftBrIndex = parseString.indexOf(conf.getLeftBracket(), charIdx);
                String className = parseString.substring(charIdx, leftBrIndex).trim();
                charIdx += className.length(); // add class name size
                String classContent = getClassContent();
                charIdx ++; // add right brackets size ")"
                return new StrEntry(className, classContent);
            } else {
                throw new ParseException("Classes name have to separated by " + conf.getClassDelimiter() + " character");
            }
        }
        return null ;
    }

    private String getClassContent() {
        StringBuffer content = new StringBuffer();
        LinkedList<Character> bracketsStack = new LinkedList<Character>();

        for(; charIdx < parseString.length(); charIdx++) {
            char currChar = parseString.charAt(charIdx);
            if(currChar == conf.getLeftBracket()) {
                bracketsStack.push(currChar);
            } else if(currChar == conf.getRightBracket() && bracketsStack.peek() == conf.getLeftBracket()) {
                bracketsStack.pop();
                if(bracketsStack.isEmpty()) {
                    return content.toString();
                }
            } else {
                content.append(currChar);
            }
        }
        return content.toString();
    }

    private boolean isIndexInBounds() {
        return charIdx < parseString.length();
    }

    @Override
    public void remove() {

    }
}
