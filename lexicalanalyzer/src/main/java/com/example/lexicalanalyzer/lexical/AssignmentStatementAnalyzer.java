package com.example.lexicalanalyzer.lexical;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;


@Service
public class AssignmentStatementAnalyzer {


    private static final String[] productions = {
            "<ASSIGN_ST>  -> <ID> <ASSIGN_OPT> <EXPRESSION> ;",
            "<ASSIGN_OPT> -> ="

    };

    private static final int[][] parserTable = {
            //         == null
            /*<ASSIGN_OPT>*/ {2, 0}
    };



    public void parse(ArrayList<String> input) {


        int lookahead = 0;
        Stack<String> parserStack = new Stack<>();
        parserStack.push("$");
        parserStack.push("<ASSIGN_ST>");



        while (lookahead < input.size()) {


            if (parserStack.peek().equals("<ID>")) {


                boolean isValidIdentifier = isValidIdentifier(input.get(lookahead));


                if (isValidIdentifier) {
                    parserStack.pop();
                    lookahead = lookahead + 1;
                } else {
                    break;
                }


            } else if (parserStack.peek().equals("<EXPRESSION>")) {


                while(lookahead < input.size()) {


                    if(input.get(lookahead).equals(";")) {
                        parserStack.pop();
                        break;
                    }
                    lookahead = lookahead + 1;
                }


            } else if (parserStack.peek().equals("<ASSIGN_ST>")) {


                String[] production = productions[0].split("->")[1].split(" ");
                Collections.reverse(Arrays.asList(production));


                parserStack.pop();


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
            case "<ASSIGN_OPT>" -> 0;
            default -> 0;
        };
    }


    private int getColumnNumber(String input) {
        return switch (input) {
            case "=" -> 0;
            default -> 1;
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
