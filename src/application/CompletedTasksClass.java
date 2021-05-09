package application;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CompletedTasksClass extends MainController{
	
	@FXML 
	private Button completed;
	
	@FXML 
	private VBox box2;
	
	List<CheckBox> completedList = new ArrayList<>();
	
	
	@FXML
	public void initialize() throws SQLException {

		java.sql.Connection connection = DriverManager.getConnection(url, username, passwordConnect);
		String sql = "SELECT donetasks FROM complete";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {

			String taskID = new String();

			taskID = rs.getString("donetasks");

			getTask(taskID);

		}
	
	}
	
	@Override
	void addToPane() {

		box2.getChildren().clear();
		box2.setSpacing(10);
		box2.autosize();

		completedList.forEach(box -> box2.getChildren().add(box));

	}
	
	@FXML
	@Override
	void getTask(String taskName) {

		check = new CheckBox(taskName);

		check.setOnAction(eh);

		check.setTextFill(Color.WHITE);

		completedList.add(check);

		addToPane();

	}
	
	
	@FXML

	void removeTasks() throws SQLException {
		
		java.sql.Connection connection = DriverManager.getConnection(url, username, passwordConnect);
		String sql = "DELETE FROM complete";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		box2.getChildren().removeAll(completedList);
		
		
		stmt.executeUpdate();
		
		
	}
	
	@FXML
	void switchToMain(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
		
	}

}
