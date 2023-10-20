package com.example.lexicalanalyzer.lexical;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;


@Service
public class ReturnAnalyzer {


    private static final String[] productions = {
            "<RETURN> -> return <M> ;",
            "<M> -> ID",
            "<M> -> constant"
    };


    private static final int[][] parserTable = {
            //         return ; null
            /*<RETURN>*/ {1, 0, 0}
    };



    public void parse(ArrayList<String> input) {


        int lookahead = 0;
        Stack<String> parserStack = new Stack<>();
        parserStack.push("$");
        parserStack.push("<RETURN>");



        while (lookahead < input.size()) {

            if(parserStack.peek().equals("<M>")) {



                if (StringUtils.isNumeric(input.get(lookahead))) {


                    parserStack.pop();
                    lookahead = lookahead + 1;


                } else {


                    boolean isValidIdentifier = isValidIdentifier(input.get(lookahead));


                    if (isValidIdentifier) {
                        parserStack.pop();
                        lookahead = lookahead + 1;
                    } else {
                        break;
                    }


                }
            } else {

                if (parserStack.peek().equals(input.get(lookahead))) {
                    parserStack.pop();
                    lookahead = lookahead + 1;
                    continue;
                }


                int rowNumber = getRowNumber(parserStack.peek());
                int columnNumber = getColumnNumber(input.get(lookahead));
                int cfgKey = parserTable[rowNumber][columnNumber];


                if (cfgKey < 1) {
                    break;
                }


                parserStack.pop();


                String[] production = productions[cfgKey - 1].split("->")[1].split(" ");
                Collections.reverse(Arrays.asList(production));


                for (int index = 0; index < production.length; index++) {


                    if (
                            production[index].isBlank() ||
                                    production[index].isEmpty() ||
                                    production[index].equals("null")
                    ) {
                        break;
                    }


                    parserStack.push(production[index]);
                }

                if (parserStack.peek().equals(input.get(lookahead))) {
                    parserStack.pop();
                    lookahead = lookahead + 1;
                }
            }
        }



        if(parserStack.peek().equals("$")) {
            System.out.println("No Error");
        } else {
            System.out.println("Error");
        }


    }



    private int getRowNumber(String symbol) {
        return switch (symbol) {
            case "<RETURN>" -> 0;
            default -> 0;
        };
    }


    private int getColumnNumber(String input) {
        return switch (input) {
            case "return" -> 0;
            case ";" -> 1;
            default -> 2;
        };
    }



    protected boolean isValidIdentifier(String identifier) {


        // Check if the identifier is null or empty


        if (identifier == null || identifier.length() == 0) {
            return false;
        }


        // Check if the first character is a letter or underscore


        if (!Character.isLetter(identifier.charAt(0)) && identifier.charAt(0) != '_') {

            return false;

        }


        // Check each subsequent character


        for (int i = 1; i < identifier.length(); i++) {


            char c = identifier.charAt(i);


            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }


        // If we haven't returned false by now, the identifier is valid


        return true;
    }
}
