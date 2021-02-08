import java.sql.*;

public class Main {
  public static void main(String[] args) {
    //http://sqliteadmin.orbmu2k.de/
    //https://github.com/xerial/sqlite-jdbc
    //https://habr.com/ru/sandbox/88039/

    //try (Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/GeekBrains/BD/testBD.s3db")) {
    //conn - Одно соединение с бд.
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/testBD.s3db")) { //схема БД - testBD.s3db
      Statement statement = conn.createStatement();
      //создаем таблицу students, с помощью execute (этот метод используют для команд создания тч - CREATE)
      //Для SQLite
      statement.execute("CREATE TABLE IF NOT EXISTS 'students' (\n"+
              "\t 'StudID' INT(10) PRIMARY KEY NOT NULL,\n"+
              "\t 'Name' VARCHAR(500) NOT NULL COLLATE NOCASE,\n"+
              "\t 'GroupName' VARCHAR(500) NOT NULL COLLATE NOCASE,\n"+
              "\t 'Score' INT(10) NOT NULL\n"+
              ");\n");

      //Для MySQL
//      statement.execute("CREATE TABLE IF NOT EXISTS 'students' (\n"+
//              "\t 'StudID' INT(10) NOT NULL,\n"+
//              "\t 'Name' VARCHAR(500) NOT NULL COLLATE 'utf8_unicode_ci',\n"+
//              "\t 'GroupName' VARCHAR(500) NOT NULL COLLATE 'utf8_unicode_ci',\n"+
//              "\t 'Score' INT(10) NOT NULL,\n"+
//              "\t PRIMARY KEY ('StudID') USING BTREE,\n"+
//              ")\n"+
//              "COLLATE='utf8_unicode_ci'\n"+
//              "ENGINE=InnoDB\n"+
//              ";\n");

      //executeUpdate используют для добаления, удаления либо изменения строк в тч бд
      //добаляем строку
      statement.executeUpdate("insert into 'students' (StudID,Name,GroupName,Score) values(1,\" Ivanov\",\"Group 1\",45)");


      //executeQuery - используется для получения данных из бд
      //ResultSet resultSet = statement.executeQuery("select * from students where Score > 5");

      //преготовленые запросы более удобны
      int score = 5;
      PreparedStatement preparedStatement = conn.prepareStatement("select * from students where Score > ?");
      preparedStatement.setInt(1, score); //подставляем параметр
      ResultSet resultSet = preparedStatement.executeQuery();

      //перебираем результат запроса
      while (resultSet.next()){
        StringBuilder builder = new StringBuilder();
        builder.append(resultSet.getString(1)).append(" ");
        builder.append(resultSet.getString("Name")).append(" ");
        builder.append(resultSet.getString(3)).append(" ");
        builder.append(resultSet.getString(4)).append(" ");
        System.out.println(builder);
        //resultSet.previous() - передвигает итератор в таблице на ход назад
        //resultSet.first() - передвигает итератор в таблице на начало таблицы
        //resultSet.last() -передвигает итератор в таблице в конец
        // причем все они возвращают boolean как и next()
      }


    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
