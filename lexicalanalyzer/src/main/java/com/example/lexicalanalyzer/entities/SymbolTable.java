package com.example.lexicalanalyzer.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity
@Table(name = "symbol_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SymbolTable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    a;
    a;

    @Column(name = "variable")
    private String variable;



    @Column(name = "type")
    private String type;



    @Column(name = "line_no")
    private Integer lineNo;



    @Column(name = "error_message")
    private String errorMessage;


}
