package com.example.lexicalanalyzer.lexical;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;


@Service
public class ParameterAnalyzer {


    private static final String[] productions = {
            "<DEC>  -> <DT> <ID> <M> <LIST>",
            "<LIST> -> , <DT> <ID> <M> <LIST>",
            "<M>    -> null",
            "<M>    -> = constant",
            "<DT>   -> int",
            "<DT>   -> float",
            "<DT>   -> string",
    };


    private static final int[][] parserTable = {
            //          int  float  string  ,   =   null
            /*<DEC>*/ {1, 1, 1, 0, 0, 0},
            /*<LIST>*/ {0, 0, 0, 2, 0, 0},
            /*<M>*/   {0, 0, 0, 3, 4, 3},
            /*<DT>*/  {5, 6, 7, 0, 0, 0}
    };


    public void parse(ArrayList<String> input) {


        int lookahead = 0;
        Stack<String> parserStack = new Stack<>();
        parserStack.push("$");
        parserStack.push("<DEC>");


        while (lookahead < input.size()) {


            if (parserStack.peek().equals("<ID>")) {


                boolean isValidIdentifier = isValidIdentifier(input.get(lookahead));


                if (isValidIdentifier) {
                    parserStack.pop();
                    lookahead = lookahead + 1;
                } else {
                    break;
                }


            } else if (parserStack.peek().equals("constant")) {
                parserStack.pop();
                lookahead = lookahead + 1;
                continue;
            } else {


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


        if (parserStack.peek().equals("$")) {
            System.out.println("No Error");
        } else if (parserStack.peek().equals("<LIST>") || parserStack.peek().equals("<M>") ) {
            System.out.println("No Error");
        } else {
            System.out.println("Error");
        }

    }


    private int getRowNumber(String symbol) {
        return switch (symbol) {
            case "<DEC>" -> 0;
            case "<LIST>" -> 1;
            case "<M>" -> 2;
            case "<DT>" -> 3;
            default -> 0;
        };
    }


    private int getColumnNumber(String input) {
        return switch (input) {
            case "int" -> 0;
            case "float" -> 1;
            case "string" -> 2;
            case "," -> 3;
            case "=" -> 4;
            default -> 5;
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
