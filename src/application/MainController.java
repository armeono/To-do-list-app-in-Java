package application;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainController {

	String url = "jdbc:mysql://localhost:3306/todo";
	String username = "root";
	String passwordConnect = "<@pgLX3t-zRs=+xj";

	Connection db = new Connection();
	
	protected Stage stage;
	protected Scene scene;
	protected Parent root;

	@FXML
	private VBox vBox = new VBox();

	@FXML
	private Button addButton;

	@FXML
	private TextField textField;

	@FXML
	private ScrollBar bar = new ScrollBar();

	List<CheckBox> listOfChecocks = new ArrayList<>();

	

	@FXML
	CheckBox check = new CheckBox();
	
	@FXML 
	private Button switchButton;

	EventHandler eh = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {

			if (event.getSource() instanceof CheckBox) {

				

				CheckBox chk = (CheckBox) event.getSource();
				
				String doneTask = chk.getText();
				
				vBox.getChildren().remove(chk);
				
				listOfChecocks.remove(chk);
		
				
				db.completedTasks(doneTask);

				try {
					java.sql.Connection connection = DriverManager.getConnection(url, username, passwordConnect);
					String sql = "DELETE FROM todotable WHERE tasks = '" + chk.getText() + "'";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.executeUpdate();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	};

	@FXML
	public void initialize() throws SQLException {

		java.sql.Connection connection = DriverManager.getConnection(url, username, passwordConnect);
		String sql = "SELECT tasks FROM todotable";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {

			String taskID = new String();

			taskID = rs.getString("tasks");

			getTask(taskID);

		}
		
	


	}

	@FXML
	void add() {

		String name = textField.getText();

		check = new CheckBox(name);

		check.setOnAction(eh);

		check.setTextFill(Color.WHITE);
		
		

		listOfChecocks.add(check);

		db.connect(name);

		textField.setText(null);

		addToPane();

	}

	@FXML
	void getTask(String taskName) {

		check = new CheckBox(taskName);

		check.setOnAction(eh);

		check.setTextFill(Color.WHITE);

		listOfChecocks.add(check);

		addToPane();

	}

	void addToPane() {

		vBox.getChildren().clear();
		vBox.setSpacing(10);
		vBox.autosize();

		listOfChecocks.forEach(box -> vBox.getChildren().add(box));

	}
	
	@FXML
	void switchScene(ActionEvent event) throws IOException {
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("completedtasks.fxml"));
		Parent root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}

}
