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

	@FXML
	private VBox vBox = new VBox();

	@FXML
	private Button addButton;

	@FXML
	private TextField textField;
	
	@FXML
	private ScrollBar bar = new ScrollBar();

	List<CheckBox> listOfChecks = new ArrayList<>();

	@FXML
	CheckBox check = new CheckBox();

	EventHandler eh = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {

			if (event.getSource() instanceof CheckBox) {
				CheckBox chk = (CheckBox) event.getSource();
				vBox.getChildren().remove(chk);
				listOfChecks.remove(chk);

				try {
					java.sql.Connection connection = DriverManager.getConnection(url, username, passwordConnect);
					String sql = "DELETE FROM todotable WHERE tasks = '" + chk.getText() +"'";
					PreparedStatement stmt;
					stmt = connection.prepareStatement(sql);
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
		
		/*bar.setMin(0);
		bar.setOrientation(Orientation.VERTICAL);
		
		bar.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				vBox.setLayoutY(-newValue.doubleValue());
				
			}
			
		});*/
	}

	@FXML
	void add() {

		String name = textField.getText();

		check = new CheckBox(name);

		check.setOnAction(eh);

		check.setTextFill(Color.WHITE);

		listOfChecks.add(check);

		db.connect(name);

		textField.setText(null);

		addToPane();

	}

	@FXML
	void getTask(String taskName) {

		check = new CheckBox(taskName);

		check.setOnAction(eh);

		check.setTextFill(Color.WHITE);

		listOfChecks.add(check);

		addToPane();

	}

	void addToPane() {

		vBox.getChildren().clear();
		vBox.setSpacing(10);
		vBox.autosize();

		listOfChecks.forEach(box -> vBox.getChildren().add(box));

	}

}
