package com.example.lexicalanalyzer.repos;

import com.example.lexicalanalyzer.entities.SymbolTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface LexicalAnalyzerRepo extends JpaRepository<SymbolTable , Integer> {




    @Query(value = "select st.variable from symbol_table st order by st.id desc limit 1 ",nativeQuery = true)
    String getDataType();

    @Query(value = "select st.variable from symbol_table st", nativeQuery = true)
    ArrayList<String> findAllTokens();


}
