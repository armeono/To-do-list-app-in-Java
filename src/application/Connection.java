package application;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.CheckBox;

public class Connection {
	
	public void connect(String task) {
		
	
	
	String url = "jdbc:mysql://localhost:3306/todo";
	String username = "root";
	String passwordConnect = "<@pgLX3t-zRs=+xj";
	
	
	
	try {
		
		java.sql.Connection connection = DriverManager.getConnection(url, username, passwordConnect);
		String sql = "INSERT INTO todotable (tasks) VALUES (?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setNString(1, task);
		
		int rows = stmt.executeUpdate();
		if(rows > 0) {
			System.out.println("Task inserted");
		}
		
		
		
	} catch (SQLException e) {
		
		System.out.println("Could not insert task");
		e.printStackTrace();
	}
	
	}
	
	public void completedTasks(String task) {
		
		
		
		String url = "jdbc:mysql://localhost:3306/todo";
		String username = "root";
		String passwordConnect = "<@pgLX3t-zRs=+xj";
		
		
		
		try {
			
			java.sql.Connection connection = DriverManager.getConnection(url, username, passwordConnect);
			String sql = "INSERT INTO complete (donetasks) VALUES (?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setNString(1, task);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) {
				System.out.println("Task inserted");
			}
			
			
			
		} catch (SQLException e) {
			
			System.out.println("Could not insert task");
			e.printStackTrace();
		}
		
		}

}
