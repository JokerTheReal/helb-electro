import Controllers.EntrepotController;
import Models.Entrepot;
import Views.EntrepotView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Entrepot model = new Entrepot();
        EntrepotView view = new EntrepotView();
        EntrepotController controller = new EntrepotController(model, view);
        Scene scene = new Scene(view.getRoot(), 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
