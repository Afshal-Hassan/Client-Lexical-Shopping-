package com.example.lexicalanalyzer.lexical;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

@Service
public class ForLoopAnalyzer {


    @Autowired
    private AssignmentStatementAnalyzer assignmentStatementAnalyzer;

    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;


    @Autowired
    private BodyAnalyzer bodyAnalyzer;


    private static final String[] productions = {
            "<FOR>  -> for ( <COND1> <COND2> ; <COND3> ) { <BODY> }",
            "<COND2> -> <IDORCONST> <OPERATOR> <IDORCONST>",
            "<OPERATOR> -> ==",
            "<OPERATOR> -> <=",
            "<OPERATOR> -> >=",
            "<OPERATOR> -> <",
            "<OPERATOR> -> >",
            "<OPERATOR> -> !="
    };


    private static final int[][] parserTable = {
            //          for  ==  <=  >=   <   >   !=  (  )  {  }  null
            /*<FOR>*/ {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            /*<OPERATOR>*/   {0, 3, 4, 5, 6, 7, 8, 0, 0, 0, 0, 0},
    };


    public void parse(ArrayList<String> input) {





        int lookahead = 0;
        Stack<String> parserStack = new Stack<>();
        parserStack.push("$");
        parserStack.push("<FOR>");


        while (lookahead < input.size()) {


            if (parserStack.peek().equals("<COND1>")) {


                ArrayList<String> tempInput = new ArrayList<>();


                while (lookahead < input.size()) {


                    tempInput.add(input.get(lookahead));
                    lookahead = lookahead + 1;


                    if (input.get(lookahead).equals(";")) {
                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;
                        break;

                    }


                }


                if (input.get(lookahead + 1).equals("=")) {
                    assignmentStatementAnalyzer.parse(tempInput);
                    parserStack.pop();
                } else {
                    syntaxAnalyzer.parse(tempInput);
                    parserStack.pop();
                }
            }
            if (parserStack.peek().equals("<COND2>")) {


                String[] production = productions[1].split("->")[1].split(" ");
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



            } else if (parserStack.peek().equals("<IDORCONST>")) {


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


            } else if (parserStack.peek().equals("<COND3>")) {


                ArrayList<String> tempInput = new ArrayList<>();


                while (lookahead < input.size()) {


                    tempInput.add(input.get(lookahead));
                    lookahead = lookahead + 1;


                    if (input.get(lookahead).equals(";")) {
                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;
                        break;

                    }

                }

                assignmentStatementAnalyzer.parse(tempInput);
                parserStack.pop();

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
            case "<FOR>" -> 0;
            case "<OPERATOR>" -> 1;
            default -> 0;
        };
    }


    private int getColumnNumber(String input) {
        return switch (input) {
            case "for" -> 0;
            case "==" -> 1;
            case "<=" -> 2;
            case ">=" -> 3;
            case "<" -> 4;
            case ">" -> 5;
            case "!=" -> 6;
            case "(" -> 7;
            case ")" -> 8;
            case "{" -> 9;
            case "}" -> 10;
            default -> 11;
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