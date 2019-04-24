/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.dao;

import br.edu.utfpr.dto.PaisDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolas Aoki
 */

 // Espera um atributo generico
public abstract class TemplateMethodDAO {


    public TemplateMethodDAO(String sql) {
        //Conexao com banco de dados
        try ( Connection conn = DriverManager.getConnection( "jdbc:derby:memory:database;create=true" ) ) {
            conn.createStatement().executeUpdate( sql );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract boolean incluir(PaisDTO pais);
    protected abstract List<T> listarTodos();
    protected abstract boolean excluir(int id);
    protected abstract boolean alterar(PaisDTO pais);
    protected abstract PaisDTO listarPorId (int id);



}