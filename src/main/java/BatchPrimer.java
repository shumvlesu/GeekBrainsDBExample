import java.sql.*;

public class BatchPrimer {
  public static void main(String[] args) {
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/testBD.s3db")) { //схема БД - testBD.s3db
      //пример использования батч запросов
      PreparedStatement statement = conn.prepareStatement("insert into students (StudID,Name,GroupName,Score) values(?,?,?,?)");

      //отключаем автокомит, чтобы транзакция выполнялась в ручном режиме, иначе батч запрос будет выполняться
      //в 5ти транзакциях
      conn.setAutoCommit(false);

      for (int i = 0; i < 5; i++) {

        statement.setInt(1,(i+1));
        statement.setString(2,"Ivanov "+i);
        statement.setString(3,"Gruppa");
        statement.setInt(4,(i*10));
        //statement.execute(); //здес каждый раз мы инсертим строку в бд и это итого 5 раз подключаемся и записываем в бд. Это медленно.
        statement.addBatch(); //а вот с батч запросом мы обратимся к бд один раз и запишем сразу 5 строк. Пока добавляем их в батч.
      }

      statement.executeBatch();//вот здесь и пишем в бд одним запросом к БД
      conn.commit();//закрываем транзакцию руками

      Statement statement1 = conn.createStatement();
      ResultSet resultSet = statement1.executeQuery("select * from students");

      while (resultSet.next()){
        System.out.println(resultSet.getString(2));
      }


    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    }
}
