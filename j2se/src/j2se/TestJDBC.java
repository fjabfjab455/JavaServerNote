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
		//��ʼ������
		try {
			//������ com.mysql.jdbc.Driver ���� mysql-connector-java-5.0.8-bin.jar �У���������˵�һ������ĵ������ͻ��׳�ClassNotFoundException
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("���ݿ��������سɹ�");
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8", "root", "chr102030");
			System.out.println("���ݿ����ӳɹ��� ��ȡ���Ӷ���" + connection);
			
			Statement statement = connection.createStatement();
			System.out.println("��ȡ Statement ����" + statement);
//			int n = 0;
//			for (int i = 0; i < 100; i++) {
//				String sql = "insert into hero values(null, "+" '��ĪĪĪ " + i + " ' "+", "+313.0f+", "+50+") ";
//				statement.execute(sql);
//			}
			
			String name = "root";
			String passwd = "chr102030";
			String sql = "  select * from user where name = '" + name + "' and password = '" + passwd + "'    ";
			String sql_name = "  select * from user where name = '" + name + "'";
			
			if (statement.executeQuery(sql_name).next() ) {
				System.out.println("�˺ųɹ���");
				if (statement.executeQuery(sql).next() ) {
					System.out.println("����ɹ���");
				} else {
					System.out.println("����ʧ�ܣ�");
				}
			} else {
				System.out.println("�˺�ʧ�ܣ�");
			}
			
			
			String queryHeroCount = " select count(*) from hero  ";
			ResultSet rSet = statement.executeQuery(queryHeroCount);
			int count = 0;
			while (rSet.next()) {
				count = rSet.getInt(1);
			}
			System.out.println("��ǰHero�����������Ϊ��" + count);
			
			String insertHero = "insert into hero values(null, ?, ?, ?)";
			PreparedStatement pStatement = connection.prepareStatement(insertHero);
			pStatement.setString(1, "����...");
			pStatement.setFloat(2, 312.0f);
			pStatement.setInt(3, 60);
			pStatement.execute();
			
			System.out.println("�������ɹ�������");
			
			
			
			/**
			 * ɾ������ǰ10�����ݣ���ɾ��ǰ���ڿ���̨������ʾ�����û�����Y��ɾ����N��ɾ�����������ظ���ʾ
			 */
	        try (Connection c = connection;
	                Statement st4Query = c.createStatement();
	                Statement st4Delete = c.createStatement();
	                Scanner s = new Scanner(System.in);) {  //����Ӧ����try-with-resource������try-catch���������󣬻��Զ��ͷŵ�try()�����Դ
	 
	            //���Զ��ύ�ر�
	            c.setAutoCommit(false);
	            //���ǰ10��
	            ResultSet rs =st4Query.executeQuery("select id from Hero order by id asc limit 0,10 ");
	            while(rs.next()){
	                int id = rs.getInt(1);
	                System.out.println("��ͼɾ��id="+id+" ������");
	                st4Delete.execute("delete from Hero where id = " +id);
	            }
	             
	            //�Ƿ�ɾ����10��
	            while(true){
	                System.out.println("�Ƿ�Ҫɾ������(Y/N)");
	                 
	                String str = s.next();
	                if ("Y".equals(str) || "y".equals(str)) {
	                    //����������Y�����ύɾ������
	                    c.commit();
	                    System.out.println("�ύɾ��");
	                    break;
	                } else if ("N".equals(str) || "n".equals(str)) {
	                    System.out.println("����ɾ��");
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
