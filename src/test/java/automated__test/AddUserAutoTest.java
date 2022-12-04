package automated__test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;

import static java.lang.Thread.sleep;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class AddUserAutoTest {

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/panchuk/taxapp/AddUserScene.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Add User Test");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setResizable(false);
        stage.toFront();
    }

    @Test
    public void testAddUser(FxRobot robot) throws InterruptedException {
        TextField textField01 = robot.lookup("#tfFirstName").queryAs(TextField.class);
        TextField textField02 = robot.lookup("#tfLastName").queryAs(TextField.class);
        TextField textField03 = robot.lookup("#tfEmail").queryAs(TextField.class);
        DatePicker datePicker = robot.lookup("#datePicker").queryAs(DatePicker.class);
        assertNotNull(textField01);
        assertNotNull(textField02);
        assertNotNull(textField03);
        assertNotNull(datePicker);

        RadioButton rb01 = robot.lookup("#rbMale").queryAs(RadioButton.class);
        RadioButton rb02 = robot.lookup("#rbFemale").queryAs(RadioButton.class);
        RadioButton rb03 = robot.lookup("#rbOther").queryAs(RadioButton.class);
        assertNotNull(rb01);
        assertNotNull(rb02);
        assertNotNull(rb03);

        robot.clickOn("#tfFirstName").write("Test");
        sleep(500);
        robot.clickOn("#tfLastName").write("Tester");
        sleep(500);
        robot.clickOn("#tfEmail").write("Tester@email.com");
        sleep(500);
        robot.clickOn("#datePicker");
        datePicker.setValue(LocalDate.EPOCH);


//        Button button = robot.lookup("#btnAdd").queryAs(Button.class);
//        assertNotNull(button);
//        robot.clickOn(button);
    }
}
