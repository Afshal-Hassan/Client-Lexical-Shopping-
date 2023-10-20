package com.example.lexicalanalyzer.enums;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public enum Operators {



    EQUALS_TO("="),


    COMMA(","),



    GREATER_THAN(">"),



    LESS_THAN("<"),



    GREATER_THAN_EQUALS_TO(">="),



    LESS_THAN_EQUALS_TO("<="),



    EQUALS_EQUALS_TO("=="),



    NOT_EQUALS_TO("!="),




    ADDITION("+"),




    SUBTRACTION("-"),




    MULTIPLY("*"),




    DIVIDE("/"),



    ROUND_BRACKET_OPEN("("),


    ROUND_BRACKET_CLOSE(")"),


    CURLY_BRACKET_OPEN("{"),


    CURLY_BRACKET_CLOSE("}"),


    TERMINATOR(";"),




    DOT("."),





    INVALID("invalid");






    public final String type;



    Operators(String type) {
        this.type = type;
    }



    private static final Map<String, Operators> statusByTypes =
            Stream.of(values())
                    .filter(c -> c != INVALID)
                    .collect(toUnmodifiableMap(c -> c.type, identity()));




    public static Operators getByType(Integer type) {


        if (null == type) return INVALID;


        return statusByTypes.getOrDefault(type, INVALID);

    }


}
