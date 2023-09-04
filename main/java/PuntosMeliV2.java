import java.sql.*;
import java.util.Scanner;

public class PuntosMeliV2 {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("***BIENVENIDOS A MERCADO LIBRE***");

        double televisor = 3800000;
        double portatil = 2800000;
        double lavadora = 2500000;
        double nevera = 2000000;
        double estufa = 1200000;

        double puntosmeli = 0;

        System.out.print("Ingrese su documento de identidad: ");
        String documento = scanner.nextLine();

        if (documento.equals("")) {
            System.out.println("No se admiten datos vacios");
        } else {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/usuariosmeli";
            String username = "root";
            String password = "";

            try {
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(url, username, password);

                String consultaSQL = "SELECT * FROM usuarios WHERE cedula = ?";

                PreparedStatement statement = connection.prepareStatement(consultaSQL);
                statement.setString(1, documento); // Establecer el valor del parámetro

                // Ejecutar la consulta
                ResultSet resultSet = statement.executeQuery();

                // Procesar el resultado si existe
                if (resultSet.next()) {
                    String cedula = resultSet.getString("cedula");
                    String nombre = resultSet.getString("nombre");
                    double puntos = resultSet.getDouble("puntos");

                    System.out.println("señor: " + nombre + " tu total de puntos son: " + puntos);
                    puntosmeli = puntos;

                    System.out.println("Deseas adquirir un producto: ");
                    String respuesta = scanner.nextLine();

                    while (respuesta.equals("si")) {

                        System.out.print("Que producto deseas adquirir de nuestro catalogo?: ");
                        String producto = scanner.nextLine();

                        if (puntosmeli < televisor && producto.equalsIgnoreCase("televisor")) {
                            System.out.println("NO te alcanza para adquirir televisor");
                        } else if (producto.equalsIgnoreCase("televisor")) {
                            puntosmeli = puntosmeli - televisor;
                        } else if (puntosmeli < portatil && producto.equalsIgnoreCase("portatil")) {
                            System.out.println("NO te alcanza para adquirir portatil");
                        } else if (producto.equalsIgnoreCase("portatil")) {
                            puntosmeli = puntosmeli - portatil;
                        } else if (puntosmeli < lavadora && producto.equalsIgnoreCase("lavadora")) {
                            System.out.println("NO te alcanza para adquirir lavadora");
                        } else if (producto.equalsIgnoreCase("lavadora")) {
                            puntosmeli = puntosmeli - lavadora;
                        } else if (puntosmeli < nevera && producto.equalsIgnoreCase("nevera")) {
                            System.out.println("NO te alcanza para adquirir nevera");
                        } else if (producto.equalsIgnoreCase("nevera")) {
                            puntosmeli = puntosmeli - nevera;
                        } else if (puntosmeli < estufa && producto.equalsIgnoreCase("estufa")) {
                            System.out.println("NO te alcanza para adquirir estufa");
                        } else if (producto.equalsIgnoreCase("estufa")) {
                            puntosmeli = puntosmeli - estufa;
                        } else {
                            System.out.println("Ingrese una producto valido");

                        }

                        System.out.print("¿Deseas adquirir otro producto?: ");
                        respuesta = scanner.nextLine();
                        System.out.println("Te quedaron: " + puntosmeli + " puntos.");

                        String consulta = "UPDATE usuarios SET Puntos = ? WHERE Cedula = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(consulta);
                        preparedStatement.setDouble(1, puntosmeli);
                        preparedStatement.setString(2, cedula);

                        int filasActualizadas = preparedStatement.executeUpdate();
                        if (filasActualizadas > 0) {
                            System.out.println("Puntos actualizado exitosamente");
                        }
                        String driver2 = "com.mysql.cj.jdbc.Driver";
                        String url2 = "jdbc:mysql://localhost:3306/micarrito";
                        String username2 = "root";
                        String password2 = "";

                        Class.forName(driver2);
                        Connection connection2 = DriverManager.getConnection(url2, username2, password2);

                        Statement statement2 = connection2.createStatement();
                        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM compra");//TABLA


                        Insert(cedula, nombre, producto, connection2);
                        connection2.close();
                        statement2.close();
                        resultSet2.close();
                    }

                } else {
                    System.out.println("No se encontró un registro con el codigo especificado.");
                }

                // Cerrar recursos
                resultSet.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
            }
    public static void Insert(String Cedula, String Nombre, String Productos, Connection connection){

        try {
            // Sentencia INSERT
            String sql = "INSERT INTO compra (Cedula, Nombre, Productos) VALUES (?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cedula);
            preparedStatement.setString(2, Nombre);
            preparedStatement.setString(3, Productos);

            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Poducto: " + Productos + " agregado exitosamente.");
            } else {
                System.out.println("No se pudo agregar el producto.");
            }

            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}