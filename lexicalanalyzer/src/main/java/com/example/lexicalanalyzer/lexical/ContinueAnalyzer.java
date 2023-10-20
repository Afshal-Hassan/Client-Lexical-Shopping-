package com.example.lexicalanalyzer.lexical;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

@Service
public class ContinueAnalyzer {


    private static final String[] productions = {
            "<CONTINUE>  -> continue ;"
    };


    private static final int[][] parserTable = {
            //         continue ; null
            /*<CONTINUE>*/ {1, 0, 0}
    };



    public void parse(ArrayList<String> input) {


        int lookahead = 0;
        Stack<String> parserStack = new Stack<>();
        parserStack.push("$");
        parserStack.push("<CONTINUE>");



        while (lookahead < input.size()) {


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



        if(parserStack.peek().equals("$")) {
            System.out.println("No Error");
        } else {
            System.out.println("Error");
        }
    }



    private int getRowNumber(String symbol) {
        return switch (symbol) {
            case "<CONTINUE>" -> 0;
            default -> 0;
        };
    }


    private int getColumnNumber(String input) {
        return switch (input) {
            case "continue" -> 0;
            case ";" -> 1;
            default -> 2;
        };
    }
}
