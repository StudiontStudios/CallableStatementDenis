/*
Crea un procedimiento en MySQL, que reciba un número de departamento y
devuelva el salario medio de los empleados de ese departamento y el número de
empleados.
Si el departamento no existe debe devolver como salario medio el valor -1 y el
número de empleados será 0. Si existe y no tiene empleados debe devolver 0.
Realiza después un programa en Java que use dicha función. El programa
recorrerá la tabla departamentos y mostrará los datos del departamento,
incluyendo el número de empleados y el salario medio.
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
public class Ej9 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.209.128:3306/ejemplo", "USUARIO", "curso2122");

            // recuperar parametros de main
            String dep = args[0];   //"10"; // departamento

            //DELIMITER //
            //CREATE PROCEDURE datos_dep
            //    (d int,OUT media FLOAT(10),OUT num int(10))
            //BEGIN 
            //    SET media = 0;
            //    SET num = 0;
            //SELECT count(dept_no)INTO num,sum(salario)/count(dept_no)INTO media from empleados where dept_no = d;
            //END;
            ////
            
            // construir orden DE LLAMADA
            String sql = "{ call datos_dep (? ,? ,?) } ";

            // Preparamos la llamada
            CallableStatement llamada = conexion.prepareCall(sql);
            // Damos valor a los argumentos
            llamada.setInt(1, Integer.parseInt(dep)); // primer argumento-dep
            llamada.registerOutParameter(2, Types.VARCHAR);
            llamada.registerOutParameter(3, Types.VARCHAR);

            llamada.executeUpdate(); // ejecutar el procedimiento
            //mostramos datos
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
