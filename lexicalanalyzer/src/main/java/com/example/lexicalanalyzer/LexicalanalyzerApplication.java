package com.example.lexicalanalyzer;

import com.example.lexicalanalyzer.lexical.*;
import com.example.lexicalanalyzer.repos.LexicalAnalyzerRepo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@SpringBootApplication
@EnableJpaRepositories
public class LexicalanalyzerApplication {


    private final Logger logger = LoggerFactory.getLogger(LexicalanalyzerApplication.class);


    @Autowired
    private LexicalAnalyzer lexicalAnalyzer;

    @Autowired
    private LexicalAnalyzerRepo repo;

    @Autowired
    private IfElseAnalyzer ifElseAnalyzer;

    @Autowired
    private WhileLoopAnalyzer whileLoopAnalyzer;


    @Autowired
    private FunctionAnalyzer functionAnalyzer;



    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;


    @Autowired
    private BodyAnalyzer bodyAnalyzer;


    @Autowired
    private AssignmentStatementAnalyzer assignmentStatementAnalyzer;


    @Autowired
    private ForLoopAnalyzer forLoopAnalyzer;

    @Autowired
    private BreakAnalyzer breakAnalyzer;


    @Autowired
    private ContinueAnalyzer continueAnalyzer;

    @Autowired
    private ReturnAnalyzer returnAnalyzer;


    @Autowired
    private ParameterAnalyzer parameterAnalyzer;


    public static void main(String[] args) {
        SpringApplication.run(LexicalanalyzerApplication.class, args);

//        String input = "int x = 42"; // string to match against
//
//        Pattern pattern = Pattern.compile(";\\s*$");
//        Matcher matcher = pattern.matcher(input);
//
//        if (matcher.find()) {
//            System.out.println("Match found!");
//        } else {
//            System.out.println("No match found.");
//        }
    }


    @PostConstruct
    public void init() throws FileNotFoundException {

        lexicalAnalyzer.readFile();
        ArrayList<String> input = repo.findAllTokens();
//        for(int index = 0; index < input.size(); index++) {
//            System.out.println(input.get(index));
//        }
//        input.add("while");
//        input.add("(");
//        input.add("a");
//        input.add("<");
//        input.add("b");
//        input.add(")");
//        input.add("{");
//        input.add("while");
//        input.add("(");
//        input.add("a");
//        input.add("<");
//        input.add("b");
//        input.add(")");
//        input.add("{");
//        input.add("int");
//        input.add("a");
//        input.add("}");
//        input.add("}");




//        input.add("for");
//        input.add("(");
//        input.add("int");
//        input.add("a");
//        input.add("=");
//        input.add("3");
//        input.add(";");
//        input.add("a");
//        input.add("<");
//        input.add("b");
//        input.add(";");
//        input.add("a");
//        input.add("=");
//        input.add("a");
//        input.add("+");
//        input.add("1");
//        input.add(";");
//        input.add(")");
//        input.add("{");
//        input.add("int");
//        input.add("}");
        bodyAnalyzer.parse(input);
    }

}
