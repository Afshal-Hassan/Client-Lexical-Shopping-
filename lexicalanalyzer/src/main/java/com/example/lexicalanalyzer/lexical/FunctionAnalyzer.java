package com.example.lexicalanalyzer.lexical;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

@Service
public class FunctionAnalyzer {


    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;


    @Autowired
    private BodyAnalyzer bodyAnalyzer;


    @Autowired
    private ParameterAnalyzer parameterAnalyzer;




    private static final String[] productions = {
            "<FUNC>  -> <RETURN_TYPE> <ID> ( <PARAMETER> ) { <BODY> }",
            "<RETURN_TYPE>  -> int",
            "<RETURN_TYPE>  -> float",
            "<RETURN_TYPE>  -> string",

    };


    private static final int[][] parserTable = {
            //          int  float  char  null
            /*<FUNC>*/ {1, 1, 1, 0},
            /*<RETURN_TYPE>*/   {2, 3, 4, 0}

    };



    public void parse(ArrayList<String> input) {


        int lookahead = 0;
        Stack<String> parserStack = new Stack<>();
        parserStack.push("$");
        parserStack.push("<FUNC>");


        while (lookahead < input.size()) {


           if (parserStack.peek().equals("<ID>")) {


               boolean isValidIdentifier = isValidIdentifier(input.get(lookahead));


               if (isValidIdentifier) {
                   parserStack.pop();
                   lookahead = lookahead + 1;
               } else {
                        break;
               }




            } else if (parserStack.peek().equals("<BODY>")) {


               ArrayList<String> tempInput = new ArrayList<>();
               int numberOfOpenBrackets = 1;
               int numberOfCloseBrackets = 0;


               while (lookahead < input.size()) {


                   if(input.get(lookahead).equals("{")) {
                       numberOfOpenBrackets = numberOfOpenBrackets + 1;
                   }


                   if(input.get(lookahead).equals("}")) {
                       numberOfCloseBrackets = numberOfCloseBrackets + 1;
                   }



                   if(numberOfOpenBrackets == numberOfCloseBrackets && numberOfOpenBrackets > 0 && numberOfCloseBrackets > 0) {
                       break;
                   }



                   tempInput.add(input.get(lookahead));
                   lookahead = lookahead + 1;
               }


               bodyAnalyzer.parse(tempInput);
               parserStack.pop();




            } else if (parserStack.peek().equals("<PARAMETER>")) {

               if(input.get(lookahead).equals(")")) {
                   parserStack.pop();
               } else {


                   ArrayList<String> tempInput = new ArrayList<>();


                   while (lookahead < input.size()) {


                       tempInput.add(input.get(lookahead));
                       lookahead = lookahead + 1;


                       if(input.get(lookahead).equals(")")) {
                           break;

                       }


                   }

                   parameterAnalyzer.parse(tempInput);
                   parserStack.pop();
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


        if (parserStack.peek().equals("$")) {
            System.out.println("No Error");
        } else {
            System.out.println("Error");
        }
    }



    private int getRowNumber(String symbol) {
        return switch (symbol) {
            case "<FUNC>" -> 0;
            case "<RETURN_TYPE>" -> 1;
            default -> 0;
        };
    }


    private int getColumnNumber(String input) {
        return switch (input) {
            case "int" -> 0;
            case "float" -> 1;
            case "char" -> 2;
            default -> 3;
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
