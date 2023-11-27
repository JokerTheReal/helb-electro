package Views;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import Models.Component;
import Models.Emplacement;
import Models.EmplacementStatus;
import Models.Product;
import Observers.PrincipalButtonMoveResumeObserver;
import Observers.ProductSellEventListener;

public class EntrepotView {
    private GridPane gridPane;
    private VBox rightPane;
    private HBox bottomPane;
    private SplitPane splitPane;
    private BorderPane root;
    private ComboBox<String> comboBox;
    private Label inProductionProductLabel;
    private final List<ProductSellEventListener> sellEventListeners = new ArrayList<>();
    private final List<PrincipalButtonMoveResumeObserver> moveResumeButtonsListeners = new ArrayList<>();

    public EntrepotView() {
        createGridPane();
        createRightPane();
        createBottomPane();
        createSplitPane();
        createRoot();
    }

    public void addProductSellEventListener(ProductSellEventListener listener) {
        sellEventListeners.add(listener);
    }

    public void addMoveResumeButtonsListeners(PrincipalButtonMoveResumeObserver listener) {
        moveResumeButtonsListeners.add(listener);
    }

    private void emitProductSellEvent(Emplacement emplacement) {
        for (ProductSellEventListener listener : sellEventListeners) {
            listener.onProductSell(emplacement);
        }
    }

    private void createGridPane() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Image logoImage = new Image(
                "C:\\Users\\berka\\Desktop\\JavaIV\\HELBElectro\\HelbElectro\\logo.png");
        ImageView logoView = new ImageView(logoImage);
        logoView.setOpacity(0.2); // ajustez l'opacité comme vous le souhaitez

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image logoWithOpacity = logoView.snapshot(params, null);

        BackgroundImage logoBackground = new BackgroundImage(logoWithOpacity,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        gridPane.setBackground(new Background(logoBackground));

    }

    public void addProduct(Product product, int index) {
        if (index < gridPane.getChildren().size()) {
            Node node = gridPane.getChildren().get(index);
            if (node instanceof Button) {
                Button button = (Button) node;
                Emplacement emplacement = (Emplacement) button.getUserData();

                if (!product.equals(emplacement.getProduct())) {
                    emplacement.setProduct(product);
                    emplacement
                            .addHistory(product.getNom() + "_" + emplacement.getProduct().getId() + " has been added");
                    emplacement.setStatus(EmplacementStatus.Filled);

                    button.setText(product.getNom());
                    button.setStyle("-fx-background-color: " + product.getColor());
                }
            }
        }
    }

    public void showProductInfoAndHistory(Emplacement emplacement, Button button) {
        Stage newStage = new Stage();
        newStage.setTitle("Product Information");
        VBox vbox = new VBox();

        Product product = emplacement.getProduct();
        if (product != null) {
            Label idLabel = new Label("Id: " + product.getId());
            Label nameLabel = new Label("Name: " + product.getNom());
            Label colorLabel = new Label("Color: " + product.getColor());
            Label componentSpecs = new Label("Color: " + product.getComponentDescriptions());
            vbox.getChildren().addAll(idLabel, nameLabel, colorLabel, componentSpecs);
        }

        Button sellButton = new Button("Sell");
        sellButton.setOnAction(event -> {
            emitProductSellEvent(emplacement);
            button.setText("");
            button.setStyle("");
            newStage.close();
        });

        Button historyButton = new Button("History");
        historyButton.setOnAction(event -> {
            List<String> history = emplacement.getHistories();
            showHistory(history);
        });

        Label statutText = new Label("Statut : " + emplacement.getStatus().toString());

        vbox.getChildren().addAll(sellButton, historyButton, statutText);

        ScrollPane scrollPane = new ScrollPane(vbox);

        newStage.setScene(new Scene(scrollPane, 400, 300));

        newStage.show();
    }

    public void showHistory(List<String> history) {
        Stage newStage = new Stage();
        newStage.setTitle("History");

        VBox vbox = new VBox();

        Label historyLabel = new Label("History :");
        vbox.getChildren().add(historyLabel);

        for (String entry : history) {
            Label entryLabel = new Label(entry);
            vbox.getChildren().add(entryLabel);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        newStage.setScene(new Scene(scrollPane, 300, 200));
        newStage.show();
    }

    public void updateComposantList(ListView<Component> listView, Component composant) {
        ObservableList<Component> items = listView.getItems();
        items.add(composant);

        listView.setCellFactory(param -> new ListCell<Component>() {
            @Override
            protected void updateItem(Component item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getId() + " - " + item.getNom());
                    switch (item.getId()) {
                        case "C1":
                            setStyle("-fx-background-color: lightblue;");
                            break;
                        case "C2":
                            setStyle("-fx-background-color: lightgreen;");
                            break;
                        case "C3":
                            setStyle("-fx-background-color: lightsalmon;");
                            break;
                        default:
                            setStyle("");
                            break;
                    }
                }
            }
        });
    }

    public void removeComposant(String id) {
        ListView<Component> listView = getComposantListView();
        ObservableList<Component> items = listView.getItems();

        Component toRemove = null;
        for (Component c : items) {
            if (c.getId().equals(id)) {
                toRemove = c;
                break;
            }
        }
        if (toRemove != null) {
            items.remove(toRemove);
        }
    }

    private void createRightPane() {
        rightPane = new VBox();
        rightPane.setAlignment(Pos.TOP_RIGHT);
        rightPane.setSpacing(10);
        rightPane.setPadding(new Insets(10));

        Label rightLabel = new Label("Filter :");

        comboBox = new ComboBox<>();
        ObservableList<String> options = FXCollections.observableArrayList("Time", "Eco-score", "Price", "Mix");
        comboBox.setItems(options);

        HBox hBox = new HBox(rightLabel, comboBox);
        hBox.setSpacing(2);
        HBox.setHgrow(comboBox, Priority.ALWAYS);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hBox.getChildren().add(spacer);
        rightPane.getChildren().add(hBox);

        ListView<Component> listView = new ListView<>();
        listView.setOnMouseClicked(event -> {
            Component selectedComposant = listView.getSelectionModel().getSelectedItem();
            if (selectedComposant != null) {
                showComposantInfo(selectedComposant);
            }
        });

        rightPane.getChildren().add(listView);
    }

    public void showComposantInfo(Component component) {
        Stage infoStage = new Stage();
        infoStage.setTitle("Composant Information");

        VBox vbox = new VBox();
        Label idLabel = new Label("Id: " + component.getId());
        Label nameLabel = new Label("Nom: " + component.getNom());
        Label fabricationDurationLabel = new Label("Durée de fabrication: " + component.getDureeFabrication());
        Label specification = new Label("Caractéristiques: " + component.getDescriptions());

        vbox.getChildren().addAll(idLabel, nameLabel, fabricationDurationLabel, specification);

        ScrollPane scrollPane = new ScrollPane(vbox);
        infoStage.setScene(new Scene(scrollPane, 300, 200));

        infoStage.show();
    }

    private void createBottomPane() {
        bottomPane = new HBox();
        bottomPane.setPadding(new Insets(10));

        Button moveToAnotherStockButton = new Button("Move to another stock");
        moveToAnotherStockButton.setOnAction(event -> {
            for (PrincipalButtonMoveResumeObserver listener : moveResumeButtonsListeners) {
                listener.emptyTheFactory();
            }
        });

        Button resumeTheProduction = new Button("Resume the production");
        resumeTheProduction.setOnAction(event -> {
            for (PrincipalButtonMoveResumeObserver listener : moveResumeButtonsListeners) {
                listener.resumeTheProduction();
            }
        });

        inProductionProductLabel = new Label();
        inProductionProductLabel.setText("Any product in building");
        inProductionProductLabel.setTextFill(Color.RED);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox rightButtonsBox = new HBox(moveToAnotherStockButton, resumeTheProduction);
        rightButtonsBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonsBox.setSpacing(10);

        bottomPane.getChildren().addAll(inProductionProductLabel, spacer, rightButtonsBox);
    }

    public void updateTheLabelOfProductInProduction(String productName) {
        inProductionProductLabel.setText("Product building ... : " + productName);
    }

    public VBox getRightPane() {
        return rightPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ListView<Component> getComposantListView() {
        return (ListView<Component>) rightPane.getChildren().get(1);
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    private void createSplitPane() {
        splitPane = new SplitPane();
        splitPane.getItems().addAll(gridPane, rightPane);
        splitPane.setDividerPosition(0, 0.75);

    }

    private void createRoot() {
        root = new BorderPane();
        root.setCenter(splitPane);
        root.setBottom(bottomPane);
    }

    public BorderPane getRoot() {
        return root;
    }
}