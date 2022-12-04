package automated__test;

import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.scene_controller.UserSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;

import static java.lang.Thread.sleep;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class UserSceneAutoTest {

    @Start
    public void start(Stage stage) throws Exception {

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Tester");
        user.setSex(User.Sex.male);
        user.setEmail("Tester@email.com");
        user.setDateOfBirth("2000-02-01");

        UserSceneController.setTempUser(user);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/panchuk/taxapp/UserScene.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("User Scene Test");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setResizable(false);
        stage.toFront();
    }

    @Test
    public void testUserSceneController(FxRobot robot) throws InterruptedException {

        Label label01 = robot.lookup("#userFullName").queryAs(Label.class);
        assertNotNull(label01);
        assertEquals("Test Tester", label01.getText());

        Label label02 = robot.lookup("#userSex").queryAs(Label.class);
        assertNotNull(label02);
        assertEquals(User.Sex.male.name().toLowerCase(), label02.getText());

        Label label03 = robot.lookup("#userEmail").queryAs(Label.class);
        assertNotNull(label03);
        assertEquals("Tester@email.com", label03.getText());

        Label label04 = robot.lookup("#userDateOfBirth").queryAs(Label.class);
        assertNotNull(label04);
        assertEquals("2000-02-01", label04.getText());

        Label label05 = robot.lookup("#userAmountOfTax").queryAs(Label.class);
        assertNotNull(label05);
        assertEquals("0,0000", label05.getText());

        TableView<TaxType> tableView = robot.lookup("#taxesTable").queryTableView();
        assertNotNull(tableView);

    }

    @Test
    public void testUserSceneControllerButton(FxRobot robot) throws InterruptedException {

        Button button02 = robot.lookup("#btnDeleteTax").queryAs(Button.class);
        assertNotNull(button02);
        robot.clickOn(button02);
        sleep(500);

        Button button03 = robot.lookup("#btnUpdateUser").queryAs(Button.class);
        assertNotNull(button03);
        robot.clickOn(button03);
        sleep(500);

        Button button04 = robot.lookup("#btnDeleteUser").queryAs(Button.class);
        assertNotNull(button04);
        robot.clickOn(button04);
        sleep(500);

        Button button01 = robot.lookup("#btnAddTax").queryAs(Button.class);
        assertNotNull(button01);
        robot.clickOn(button01);
        sleep(500);
    }
}