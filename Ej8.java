/*
Modifica ahora el ejemplo de la página 118 (lo tienes en el site) y que “ataca” a la
función almacenada “nombre_dep” de MySQL.
 */
package AD_T2_B_CallableStatment;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Vespertino
 */
public class Ej8 {

    public static void main(String[] args) {
        try {
            //conexion
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.209.128:3306/ejemplo", "USUARIO", "curso2122");

            //        DELIMITER //
            //CREATE FUNCTION nombre_dep(d int)RETURNS VARCHAR(15)
            //BEGIN 
            //    DECLARE nom VARCHAR(15);
            //    SET nom = 'INEXISTENTE';
            //    SELECT dnombre INTO nom FROM departamentos
            //    WHERE dept_no = d;
            //RETURN nom;
            //END;
            //
            
            // recuperar parametros de main
            String dep = args[0];   //"10"; // departamento

            String sql = "{ ? = call nombre_dep (?) } ";

            // Preparamos la llamada
            CallableStatement llamada = conexion.prepareCall(sql);
            // Damos valor a los argumentos
            llamada.registerOutParameter(1, Types.VARCHAR);
            llamada.setString(2, dep);
            
            llamada.executeUpdate(); // ejecutar el procedimiento
            //mostramos datos
	    System.out.printf(llamada.getString(1) +" "+ llamada.getString(2));

            llamada.close();
            conexion.close();

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }// fin de main

}
