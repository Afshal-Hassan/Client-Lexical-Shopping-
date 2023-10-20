package com.example.lexicalanalyzer.enums;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public enum Keywords {



    IF("if"),


    ELSE("else"),


    FOR("for"),


    WHILE("while"),



    FUNCTION("function"),




    PRINT("print"),




    INPUT("input"),


    BREAK("break"),

    CONTINUE("continue"),




    NEW("new"),




    INVALID("invalid");





    public final String type;



    Keywords(String type) {
        this.type = type;
    }



    private static final Map<String, Keywords> statusByTypes =
            Stream.of(values())
                    .filter(c -> c != INVALID)
                    .collect(toUnmodifiableMap(c -> c.type, identity()));




    public static Keywords getByType(Integer type) {


        if (null == type) return INVALID;


        return statusByTypes.getOrDefault(type, INVALID);

    }
}
