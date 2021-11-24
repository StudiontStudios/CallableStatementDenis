/*
Vamos a probar llamar desde Java a un procedimiento almacenado de MySQL ,
para ello sigue los siguientes pasos:
○ Añade el procedimiento almacenado “datos_dep” que aparece en la página
116 en la base de datos de MySQL en la BD “ejemplo”.
○ Prueba a invocar este procedimiento desde Java, usando los ejemplos
vistos en clase y subidos al site. Realiza distintas pruebas con diferentes
parámetros.
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
public class Ej7 {

    public static void main(String[] args) {
        try {
            //conexiones
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.209.128:3306/ejemplo", "USUARIO", "curso2122");

            // recuperar parametros de main
            String dep = args[0];   //"10"; // departamento

            //DELIMITER //
            //CREATE PROCEDURE datos_dep
            //    (d int,OUT nom VARCHAR(15),OUT locali VARCHAR(15))
            //BEGIN 
            //    SET locali = 'INEXISTENTE';
            //    SET nom = 'INEXISTENTE';
            //SELECT dnombre, loc into nom, locali FROM departamentos 
            //WHERE dept_no=d;
            //END;
            ////
            
            // construir orden DE LLAMADA
            String sql = "{ call datos_dep (? ,? ,?) } ";

            // Preparamos la llamada
            CallableStatement llamada = conexion.prepareCall(sql);
            // Damos valor a los argumentos "?"
            llamada.setInt(1, Integer.parseInt(dep)); // primer argumento-dep
            llamada.registerOutParameter(2, Types.VARCHAR);
            llamada.registerOutParameter(3, Types.VARCHAR);

            llamada.executeUpdate(); // ejecutar el procedimiento
            //muestra los datos
            System.out.println(llamada.getString(2) + " " + llamada.getString(3));
            System.out.println("Subida realizada....");

            llamada.close();
            conexion.close();

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }// fin de main

}
