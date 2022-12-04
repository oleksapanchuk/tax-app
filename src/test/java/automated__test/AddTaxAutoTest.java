package automated__test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
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
public class AddTaxAutoTest {

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/panchuk/taxapp/AddTaxScene.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Add Tax Test");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setResizable(false);
        stage.toFront();
    }

    @Test
    public void testTax(FxRobot robot) throws InterruptedException {
        TextField textField01 = robot.lookup("#tfPayment").queryAs(TextField.class);
        TextField textField02 = robot.lookup("#tfType").queryAs(TextField.class);
        TextField textField03 = robot.lookup("#tfValue").queryAs(TextField.class);
        DatePicker datePicker = robot.lookup("#datePayment").queryAs(DatePicker.class);
        assertNotNull(textField01);
        assertNotNull(textField02);
        assertNotNull(textField03);
        assertNotNull(datePicker);

        robot.clickOn("#tfPayment").write("11111");
        sleep(500);
        robot.clickOn("#tfType").write("1");
        sleep(500);
        robot.clickOn("#tfValue").write("4123.21");
        sleep(500);
        robot.clickOn("#datePayment");
        datePicker.setValue(LocalDate.EPOCH);

    }
}
