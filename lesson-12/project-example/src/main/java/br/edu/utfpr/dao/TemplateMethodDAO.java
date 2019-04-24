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
public abstract class TemplateMethodDAO<T> {

    public abstract String getStringSQL();
    public abstract void setPreparedStatement(PreparedStatement statement,T entidade);
    public abstract String getStringSQLLista();
    
    public abstract T getEntidade(ResultSet result);
    public abstract String getStringSQLExcluir();
    public abstract String getStringSQLAlterar();
    public abstract void setPreparedStatementAlterar(PreparedStatement statement,T entidade);
    public  boolean incluir(T entidade) {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            String sql = getStringSQL();

            PreparedStatement statement = conn.prepareStatement(sql);

            setPreparedStatement(statement,entidade);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    protected  List<T> listarTodos() {

        List<T> resultado = new ArrayList<>();

        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            String sql = getStringSQLLista();

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                resultado.add(getEntidade(result));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }
    protected  boolean excluir(int id) {

        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            String sql = getStringSQLExcluir();

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public  boolean alterar(T entidade) {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            String sql = getStringSQLAlterar();

            PreparedStatement statement = conn.prepareStatement(sql);

            setPreparedStatementAlterar(statement,entidade);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) 
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    //Deve ser feito com FOR ao inves de stream
    //por ser entidade generica
//    public abstract T listarPorId (int id) {
//         return this.listarTodos().stream().filter(p -> p.getId() == id).findAny().orElseThrow(RuntimeException::new);
//     }

}