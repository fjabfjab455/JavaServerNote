package j2se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class TestJDBC {

	public static void main(String[] args) {
		//初始化驱动
		try {
			//驱动类 com.mysql.jdbc.Driver 就在 mysql-connector-java-5.0.8-bin.jar 中，如果忘记了第一个步骤的导包，就会抛出ClassNotFoundException
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("数据库驱动加载成功");
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8", "root", "chr102030");
			System.out.println("数据库连接成功， 获取连接对象：" + connection);
			
			Statement statement = connection.createStatement();
			System.out.println("获取 Statement 对象：" + statement);
//			int n = 0;
//			for (int i = 0; i < 100; i++) {
//				String sql = "insert into hero values(null, "+" '提莫莫莫 " + i + " ' "+", "+313.0f+", "+50+") ";
//				statement.execute(sql);
//			}
			
			String name = "root";
			String passwd = "chr102030";
			String sql = "  select * from user where name = '" + name + "' and password = '" + passwd + "'    ";
			String sql_name = "  select * from user where name = '" + name + "'";
			
			if (statement.executeQuery(sql_name).next() ) {
				System.out.println("账号成功！");
				if (statement.executeQuery(sql).next() ) {
					System.out.println("密码成功！");
				} else {
					System.out.println("密码失败！");
				}
			} else {
				System.out.println("账号失败！");
			}
			
			
			String queryHeroCount = " select count(*) from hero  ";
			ResultSet rSet = statement.executeQuery(queryHeroCount);
			int count = 0;
			while (rSet.next()) {
				count = rSet.getInt(1);
			}
			System.out.println("当前Hero表格中总行数为：" + count);
			
			String insertHero = "insert into hero values(null, ?, ?, ?)";
			PreparedStatement pStatement = connection.prepareStatement(insertHero);
			pStatement.setString(1, "亚索...");
			pStatement.setFloat(2, 312.0f);
			pStatement.setInt(3, 60);
			pStatement.execute();
			
			System.out.println("插入语句成功！！！");
			
			
			
			/**
			 * 删除表中前10条数据，但删除前会在控制台弹出提示，若用户输入Y则删除，N则不删除，其他则重复提示
			 */
	        try (Connection c = connection;
	                Statement st4Query = c.createStatement();
	                Statement st4Delete = c.createStatement();
	                Scanner s = new Scanner(System.in);) {  //这里应该是try-with-resource方法，try-catch方法结束后，会自动释放掉try()里的资源
	 
	            //把自动提交关闭
	            c.setAutoCommit(false);
	            //查出前10条
	            ResultSet rs =st4Query.executeQuery("select id from Hero order by id asc limit 0,10 ");
	            while(rs.next()){
	                int id = rs.getInt(1);
	                System.out.println("试图删除id="+id+" 的数据");
	                st4Delete.execute("delete from Hero where id = " +id);
	            }
	             
	            //是否删除这10条
	            while(true){
	                System.out.println("是否要删除数据(Y/N)");
	                 
	                String str = s.next();
	                if ("Y".equals(str) || "y".equals(str)) {
	                    //如果输入的是Y，则提交删除操作
	                    c.commit();
	                    System.out.println("提交删除");
	                    break;
	                } else if ("N".equals(str) || "n".equals(str)) {
	                    System.out.println("放弃删除");
	                    break;
	                }
	            }
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
