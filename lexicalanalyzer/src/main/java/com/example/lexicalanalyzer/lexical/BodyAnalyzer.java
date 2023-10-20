package com.example.lexicalanalyzer.lexical;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BodyAnalyzer {


    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;

    @Autowired
    private IfElseAnalyzer ifElseAnalyzer;


    @Autowired
    private WhileLoopAnalyzer whileLoopAnalyzer;

    @Autowired
    private FunctionAnalyzer functionAnalyzer;

    @Autowired
    private ForLoopAnalyzer forLoopAnalyzer;

    @Autowired
    private AssignmentStatementAnalyzer assignmentStatementAnalyzer;


    @Autowired
    private ReturnAnalyzer returnAnalyzer;

    @Autowired
    private ContinueAnalyzer continueAnalyzer;


    @Autowired
    private BreakAnalyzer breakAnalyzer;



    private static final String[] productions = {
            "<MULTI_ST>  -> <SINGLE_ST> <MULTI_ST>",
            "<MULTI_ST>  -> null",

    };


    private static final int[][] parserTable = {
            //         while  if  int  for  break  continue  return  null
            /*<MULTI_ST>*/ {1, 1, 1, 1, 1, 1, 1, 0},
    };



    public void parse(ArrayList<String> input) {


        int lookahead = 0;
        Stack<String> parserStack = new Stack<>();
        parserStack.push("$");
        parserStack.push("<MULTI_ST>");


        while (lookahead < input.size()) {


            if (parserStack.peek().equals("<SINGLE_ST>")) {

                if (input.get(lookahead).equals("int") && input.get(lookahead + 2).equals("(")) {


                    ArrayList<String> tempInput = new ArrayList<>();
                    int numberOfOpenBrackets = 0;
                    int numberOfCloseBrackets = 0;




                    while (lookahead < input.size()) {


                        if(input.get(lookahead).equals("{")) {
                            numberOfOpenBrackets = numberOfOpenBrackets + 1;
                        }


                        if(input.get(lookahead).equals("}")) {
                            numberOfCloseBrackets = numberOfCloseBrackets + 1;
                        }



                        if(numberOfOpenBrackets == numberOfCloseBrackets && numberOfOpenBrackets > 0 && numberOfCloseBrackets > 0) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;
                        }



                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;
                    }

                    functionAnalyzer.parse(tempInput);


                } else if(input.get(lookahead).equals("int")) {

                    if(lookahead + 2 < input.size()) {


                        if (!input.get(lookahead + 2).equals("(")) {
                            ArrayList<String> tempInput = new ArrayList<>();


                            while (lookahead < input.size()) {


                                tempInput.add(input.get(lookahead));
                                lookahead = lookahead + 1;


                                if(input.get(lookahead).equals(";")) {
                                    tempInput.add(input.get(lookahead));
                                    lookahead = lookahead + 1;
                                    break;

                                }


                            }

                            syntaxAnalyzer.parse(tempInput);
                        }
                    } else {
                        System.out.println("Error");
                        break;
                    }





                } else if (input.get(lookahead).equals("if")) {


                    ArrayList<String> tempInput = new ArrayList<>();
                    int numberOfOpenBrackets = 0;
                    int numberOfCloseBrackets = 0;




                    while (lookahead < input.size()) {


                        if(input.get(lookahead).equals("{")) {
                            numberOfOpenBrackets = numberOfOpenBrackets + 1;
                        }


                        if(input.get(lookahead).equals("}")) {
                            numberOfCloseBrackets = numberOfCloseBrackets + 1;
                        }



                        if(numberOfOpenBrackets == numberOfCloseBrackets && numberOfOpenBrackets > 0 && numberOfCloseBrackets > 0) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;
                        }



                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;
                    }

                    ifElseAnalyzer.parse(tempInput);


                } else if (input.get(lookahead).equals("while")) {


                    ArrayList<String> tempInput = new ArrayList<>();
                    int numberOfOpenBrackets = 0;
                    int numberOfCloseBrackets = 0;




                    while (lookahead < input.size()) {


                        if(input.get(lookahead).equals("{")) {
                            numberOfOpenBrackets = numberOfOpenBrackets + 1;
                        }


                        if(input.get(lookahead).equals("}")) {
                            numberOfCloseBrackets = numberOfCloseBrackets + 1;
                        }



                        if(numberOfOpenBrackets == numberOfCloseBrackets && numberOfOpenBrackets > 0 && numberOfCloseBrackets > 0) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;
                        }



                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;
                    }

                    whileLoopAnalyzer.parse(tempInput);


                } else if (input.get(lookahead).equals("for")) {


                    ArrayList<String> tempInput = new ArrayList<>();
                    int numberOfOpenBrackets = 0;
                    int numberOfCloseBrackets = 0;




                    while (lookahead < input.size()) {


                        if(input.get(lookahead).equals("{")) {
                            numberOfOpenBrackets = numberOfOpenBrackets + 1;
                        }


                        if(input.get(lookahead).equals("}")) {
                            numberOfCloseBrackets = numberOfCloseBrackets + 1;
                        }



                        if(numberOfOpenBrackets == numberOfCloseBrackets && numberOfOpenBrackets > 0 && numberOfCloseBrackets > 0) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;
                        }



                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;
                    }

                    forLoopAnalyzer.parse(tempInput);


                } else if (input.get(lookahead).equals("break")) {


                    ArrayList<String> tempInput = new ArrayList<>();


                    while (lookahead < input.size()) {


                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;


                        if(input.get(lookahead).equals(";")) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;

                        }


                    }

                    breakAnalyzer.parse(tempInput);



                } else if (input.get(lookahead).equals("continue")) {

                    ArrayList<String> tempInput = new ArrayList<>();


                    while (lookahead < input.size()) {


                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;


                        if(input.get(lookahead).equals(";")) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;

                        }


                    }

                    continueAnalyzer.parse(tempInput);

                } else if (input.get(lookahead).equals("return")) {

                    ArrayList<String> tempInput = new ArrayList<>();


                    while (lookahead < input.size()) {


                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;


                        if(input.get(lookahead).equals(";")) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;

                        }


                    }

                    returnAnalyzer.parse(tempInput);


                } else {


                    ArrayList<String> tempInput = new ArrayList<>();


                    while (lookahead < input.size()) {


                        tempInput.add(input.get(lookahead));
                        lookahead = lookahead + 1;


                        if(input.get(lookahead).equals(";")) {
                            tempInput.add(input.get(lookahead));
                            lookahead = lookahead + 1;
                            break;

                        }


                    }

                    assignmentStatementAnalyzer.parse(tempInput);

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
                    System.out.println("Error");
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
    }



        private int getRowNumber(String symbol) {
        return switch (symbol) {
            case "<MULTI_ST>" -> 0;
            default -> 0;
        };
    }


    private int getColumnNumber(String input) {
        return switch (input) {
            case "while" -> 0;
            case "if" -> 1;
            case "int" -> 2;
            case "for" -> 3;
            case "break" -> 4;
            case "continue" -> 5;
            case "return" -> 6;
            default -> 7;
        };
    }

}
