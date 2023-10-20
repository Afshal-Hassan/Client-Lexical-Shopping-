package com.example.lexicalanalyzer.enums;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public enum DataTypes {


    INT("int"),


    FLOAT("float"),


    STRING("string"),



    CLASS("class"),


    INVALID("invalid");




    public final String type;



    DataTypes(String type) {
        this.type = type;
    }



    private static final Map<String, DataTypes> statusByTypes =
            Stream.of(values())
                    .filter(c -> c != INVALID)
                    .collect(toUnmodifiableMap(c -> c.type, identity()));




    public static DataTypes getByType(Integer type) {


        if (null == type) return INVALID;


        return statusByTypes.getOrDefault(type, INVALID);

    }
}
