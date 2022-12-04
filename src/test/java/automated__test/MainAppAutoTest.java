package automated__test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static java.lang.Thread.sleep;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class MainAppAutoTest {

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/panchuk/taxapp/MainWindowInterface.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Main App Test");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setResizable(false);
        stage.toFront();
    }

    @Test
    public void testClickOnButton(FxRobot robot) throws InterruptedException {
        Button button01 = robot.lookup("#btnAddUser").queryAs(Button.class);
        assertNotNull(button01);
        robot.clickOn(button01);
        sleep(500);

        Button button02 = robot.lookup("#btnGetUser").queryAs(Button.class);
        assertNotNull(button02);
        robot.clickOn(button02);
        sleep(500);

        Button button03 = robot.lookup("#btnShowUser").queryAs(Button.class);
        assertNotNull(button03);
        robot.clickOn(button03);
        sleep(500);

        Button button04 = robot.lookup("#btnFindUser").queryAs(Button.class);
        assertNotNull(button04);
        robot.clickOn(button04);
        sleep(500);

        Button button05 = robot.lookup("#btnSortUser").queryAs(Button.class);
        assertNotNull(button05);
        robot.clickOn(button05);
        sleep(500);

        Button button06 = robot.lookup("#btnFindTax").queryAs(Button.class);
        assertNotNull(button06);
        robot.clickOn(button06);
        sleep(500);

        ImageView button07 = robot.lookup("#btnClose").queryAs(ImageView.class);
        assertNotNull(button07);
        robot.clickOn(button07);

    }
}
