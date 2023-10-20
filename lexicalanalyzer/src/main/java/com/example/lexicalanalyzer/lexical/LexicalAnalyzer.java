package com.example.lexicalanalyzer.lexical;


import com.example.lexicalanalyzer.entities.SymbolTable;
import com.example.lexicalanalyzer.enums.DataTypes;
import com.example.lexicalanalyzer.enums.Keywords;
import com.example.lexicalanalyzer.enums.Operators;
import com.example.lexicalanalyzer.repos.LexicalAnalyzerRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;





@Service
public class LexicalAnalyzer {



    private String untestedKeywords = "";



    private boolean isInitialized;



    private String lastInsertedDataType = "";



    private File file;



    @Autowired
    private LexicalAnalyzerRepo repo;





    public LexicalAnalyzer () {

        this.file = new File("C:/Client/lexicalanalyzer/src/main/java/com/example/lexicalanalyzer/lexical/Test.txt");

    }





    public String readFile() throws FileNotFoundException {



        Scanner scanner = new Scanner(file);

        Integer lineNo = 0;




        while ( scanner.hasNextLine() )
        {



            lineNo = lineNo + 1;

            String line = scanner.nextLine();
            this.untestedKeywords = "";





            for ( int index = 0 ; index < line.length() ; index ++ )
            {




                if( String.valueOf(line.charAt(index)).equals(" ")) {



                    if(!untestedKeywords.equals(""))
                    {


                        if(Character.isLetter(line.charAt(index + 1)))
                        {
                            save(untestedKeywords, "dynamic_datatype", lineNo, "No Error");
                            lastInsertedDataType = repo.getDataType();
                            this.untestedKeywords = "";
                        }


                    }
                    continue;
                }





                if
                (
                        String.valueOf( line.charAt( index )).equals( Operators.EQUALS_TO.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.ROUND_BRACKET_OPEN.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.ROUND_BRACKET_CLOSE.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.CURLY_BRACKET_OPEN.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.CURLY_BRACKET_CLOSE.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.TERMINATOR.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.COMMA.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.GREATER_THAN.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.LESS_THAN.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.GREATER_THAN_EQUALS_TO.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.LESS_THAN_EQUALS_TO.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.EQUALS_EQUALS_TO.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.NOT_EQUALS_TO.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.ADDITION.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.SUBTRACTION.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.MULTIPLY.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.DIVIDE.type ) ||
                                String.valueOf( line.charAt( index )).equals( Operators.DOT.type )
                ) {




                    if( !untestedKeywords.equals("")) {




                        if( StringUtils.isNumeric(untestedKeywords))
                        {


                            save( untestedKeywords , "value" , lineNo , "No Error");
                            this.untestedKeywords = "";


                        } else {


                            boolean responseOfValidIdentifier = isValidIdentifier(untestedKeywords);
                            save
                                    (
                                            untestedKeywords ,
                                            "variable" ,
                                            lineNo ,
                                            responseOfValidIdentifier == false ?
                                                    "Invalid Variable initialization" :
                                                    untestedKeywords.length() > 255 ? "variable should be of max 255 characters"
                                                            : "No Error"
                                    );
                            this.untestedKeywords = "";

                        }



                    }

                    save
                            (
                                String.valueOf( line.charAt( index )) ,
                                "operator" ,
                                lineNo ,
                                "No Error"
                            );




                }



                else
                {



                    untestedKeywords += line.charAt(index);



                    if (
                            untestedKeywords.equals(Keywords.IF.type) ||
                                    untestedKeywords.equals(Keywords.FOR.type) ||
                                    untestedKeywords.equals(Keywords.ELSE.type) ||
                                    untestedKeywords.equals(Keywords.WHILE.type) ||
                                    untestedKeywords.equals(Keywords.FUNCTION.type) ||
                                    untestedKeywords.equals(Keywords.PRINT.type) ||
                                    untestedKeywords.equals(Keywords.INPUT.type) ||
                                    untestedKeywords.equals(Keywords.NEW.type) ||
                                    untestedKeywords.equals(Keywords.BREAK.type) ||
                                    untestedKeywords.equals(Keywords.CONTINUE.type)



                    ) {


                        save( untestedKeywords , "keyword ", lineNo , "No Error");
                        this.untestedKeywords = "";

                        // untestedKeywords = ""




                    } else if
                    (

                            untestedKeywords.equals(DataTypes.INT.type) ||
                                    untestedKeywords.equals(DataTypes.FLOAT.type) ||
                                    untestedKeywords.equals(DataTypes.STRING.type) ||
                                    untestedKeywords.equals(DataTypes.CLASS.type)

                    ) {




                        save( untestedKeywords , "keyword ", lineNo , "No Error");
                        lastInsertedDataType = repo.getDataType();
                        this.untestedKeywords = "";




                    }
                }

            }


        }


        scanner.close();

        return "Process of reading a file is completed";

    }





    protected void save(String variable, String type , Integer lineNo , String errorMessage ) {









        if( isInitialized == true ) {



            SymbolTable symbolTable = new SymbolTable();
            symbolTable.setVariable(variable);
            symbolTable.setType(type);
            symbolTable.setLineNo(lineNo);




            if( lastInsertedDataType.equals( DataTypes.INT.type ) )
            {




                if( StringUtils.isNumeric( variable ) )
                {




                    Long isInteger = Long.parseLong(variable);




                    if (isInteger >= Integer.MIN_VALUE && isInteger <= Integer.MAX_VALUE)
                    {


                        symbolTable.setErrorMessage("No Error");


                    } else {


                        symbolTable.setErrorMessage("Integer value is out of range");


                    }





                } else {


                    symbolTable.setErrorMessage("Integer value is out of range");


                }


                repo.save(symbolTable);
                isInitialized=false;



            }





            if( lastInsertedDataType.equals( DataTypes.FLOAT ))
            {



                if( StringUtils.isNumeric( variable ) )
                {




                    Double isFloat = Double.parseDouble( variable );




                    if( isFloat >= Float.MIN_VALUE && isFloat <= Float.MAX_VALUE ) {



                        symbolTable.setErrorMessage("No Error");



                    }  else {


                        symbolTable.setErrorMessage("Float value is out of range");

                    }






                } else {


                    symbolTable.setErrorMessage("Float value is out of range");

                }



                repo.save(symbolTable);
                isInitialized = false;


            }






            if( lastInsertedDataType.equals( DataTypes.STRING.type ))
            {





                if( variable.charAt(0) == '\"' && variable.charAt(variable.length() - 1) == '\"')
                {


                    symbolTable.setErrorMessage("No Error");



                } else {


                    symbolTable.setErrorMessage("String value must be in paranthesis");

                }



                repo.save(symbolTable);
                isInitialized = false;

            }



            if(lastInsertedDataType.getClass() == String.class) {


                symbolTable.setErrorMessage("No Error");

                repo.save(symbolTable);


                if(variable.equals(Operators.TERMINATOR.type))
                {
                    isInitialized = false;
                }

            }





        }




        else {





            if( variable.equals( Operators.EQUALS_TO.type ) )
            {

                isInitialized = true;

            }




            SymbolTable symbolTable = new SymbolTable();
            symbolTable.setVariable(variable);
            symbolTable.setType(type);
            symbolTable.setLineNo(lineNo);
            symbolTable.setErrorMessage(errorMessage);
            repo.save(symbolTable);


        }


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
