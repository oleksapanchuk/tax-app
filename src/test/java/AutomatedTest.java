import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Objects;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class AutomatedTest {

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/panchuk/taxapp/MainWindowInterface.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Add User Test");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.setResizable(false);
        stage.toFront();
    }

    @Test
    public void testClickBtn(FxRobot robot){
        Button button = robot.lookup("#btnAddUser").queryAs(Button.class);
        assertNotNull(button);

    }
}
