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

                        System.out.print("¿Deseas adquirir otro producto?");
                        respuesta = scanner.nextLine();
                        System.out.println("Te quedaron: " + puntosmeli + " puntos.");
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
        }
