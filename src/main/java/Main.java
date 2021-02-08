import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) {
    //https://github.com/xerial/sqlite-jdbc
    //https://habr.com/ru/sandbox/88039/
    //одно соединение с бд
    //try (Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/GeekBrains/BD/testBD.s3db")) {
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/testBD.s3db")) {
      Statement statement = conn.createStatement();


    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
