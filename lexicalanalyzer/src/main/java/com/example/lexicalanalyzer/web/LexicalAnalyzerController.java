package com.example.lexicalanalyzer.web;


import com.example.lexicalanalyzer.lexical.LexicalAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;


@RestController
public class LexicalAnalyzerController {


    @Autowired
    private LexicalAnalyzer lexicalAnalyzer;




    @GetMapping("/read")
    public void readFile() throws FileNotFoundException {

        lexicalAnalyzer.readFile();

    }


}
