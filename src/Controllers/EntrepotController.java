package Controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Models.Component;
import Models.Emplacement;
import Models.EmplacementStatus;
import Models.Entrepot;
import Models.Product;
import Observers.PrincipalButtonMoveResumeObserver;
import Observers.ProductSellEventListener;
import Optimization.OptimisationStartegyContext;
import Services.FactoryService;
import Views.EntrepotView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class EntrepotController
        implements ProductSellEventListener, PrincipalButtonMoveResumeObserver {
    private Entrepot model;
    private FactoryService factoryService;
    private EntrepotView view;
    private OptimisationStartegyContext optimisationStartegyContext;
    private Queue<Component> composantQueue = new LinkedList<>();
    private Timeline productCreationTimeline;
    private String fileName = "C:\\Users\\berka\\Desktop\\JavaIV\\HELBElectro\\HelbElectro\\composant.txt";
    private int rows = 3;
    private int cols = 2;

    public EntrepotController(Entrepot model, EntrepotView view) {
        this.model = model;
        this.view = view;
        this.factoryService = new FactoryService();
        createEmplacements();
        initialize();
        view.addProductSellEventListener(this);
        view.addMoveResumeButtonsListeners(this);
        this.optimisationStartegyContext = new OptimisationStartegyContext();
        loadComposantsFromFile(fileName);
    }

    @Override
    public void onProductSell(Emplacement emplacement) {
        model.getProduitsFini().remove(emplacement.getProduct());
        emplacement.setStatus(EmplacementStatus.Empty);
        factoryService.sellProduct(emplacement);
        emplacement.addHistory(
                emplacement.getProduct().getNom() + "_" + emplacement.getProduct().getId() + " has been sold");
        emplacement.setProduct(null);
    }

    private void initialize() {
        view.getComboBox().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (productCreationTimeline != null && productCreationTimeline.getStatus() != Timeline.Status.STOPPED) {
                productCreationTimeline.stop();
            }

            productCreationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (areAllEmplacementsFull()) {
                    productCreationTimeline.stop();
                    view.updateTheLabelOfProductInProduction(
                            "0 empty emplacements, please empty an emplacement");
                    return;
                }
                // save initial components list
                ArrayList<Component> removedComponents = new ArrayList<>(model.getComponents());

                List<Product> productsToBeBuilt = optimisationStartegyContext.optimize(newValue, model.getComponents());

                // get used component and remove them from the view (components list)
                removedComponents.removeAll(model.getComponents());

                if (removedComponents.size() > 0) {
                    for (Component c : removedComponents) {
                        view.removeComposant(c.getId());
                    }
                }

                if (productsToBeBuilt != null && !productsToBeBuilt.isEmpty()) {
                    for (Product productToBeBuilt : productsToBeBuilt) {
                        Timeline productBuildTimeline = new Timeline(
                                new KeyFrame(Duration.seconds(productToBeBuilt.getBuildDuration()), buildEvent -> {
                                    model.ajouterProduitFini(productToBeBuilt);
                                    System.out.println("Produit construit: " + productToBeBuilt.getNom());
                                    updateView();
                                }));
                        view.updateTheLabelOfProductInProduction(productToBeBuilt.getNom());
                        productBuildTimeline.play();
                        // productBuildTimelines.add(productBuildTimeline);
                    }
                }
            }));
            productCreationTimeline.setCycleCount(Timeline.INDEFINITE);
            productCreationTimeline.play();
        });
    }

    public void updateView() {
        List<Product> produitsFini = model.getProduitsFini();

        // Update products in the view based on the model.
        for (int i = 0; i < produitsFini.size(); i++) {
            Product produit = produitsFini.get(i);
            view.addProduct(produit, i);
        }
    }

    public void loadComposantsFromFile(String fileName) {
        ArrayList<Component> components = factoryService
                .loadComposantsFromFile(fileName);

        Timeline timeline = new Timeline();
        long totalDelay = 0;
        for (Component composant : components) {
            totalDelay += composant.getDureeFabrication();

            EventHandler<ActionEvent> eventHandler = event -> {
                model.addComposant(composant);
                composantQueue.add(composant);
                updateComposantListWithQueue();
            };

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(totalDelay), eventHandler);
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    public void updateComposantListWithQueue() {
        ListView<Component> listView = view.getComposantListView();

        if (listView.getItems().size() >= 5) {
            return;
        }

        if (composantQueue.isEmpty()) {
            return;
        }

        Component composant = composantQueue.poll();
        view.updateComposantList(listView, composant);
    }

    public void createEmplacements() {
        GridPane gridPane = view.getGridPane();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button button = new Button();
                button.setPrefSize(100, 70);

                Emplacement emplacement = new Emplacement();
                model.addEmplacements(emplacement);
                button.setUserData(emplacement);
                button.setOnAction(event -> {
                    Emplacement emplacementButton = (Emplacement) button.getUserData();
                    view.showProductInfoAndHistory(emplacementButton, button);
                });

                gridPane.add(button, col, row);
            }
        }
    }

    private boolean areAllEmplacementsFull() {
        List<Emplacement> emplacements = model.getEmplacements();
        for (Emplacement emplacement : emplacements) {
            if (emplacement.getStatus() == EmplacementStatus.Empty) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void emptyTheFactory() {
        List<Emplacement> emplacements = model.getEmplacements();
        GridPane gridPane = view.getGridPane();
        model.getProduitsFini().clear();

        for (Emplacement emplacement : emplacements) {
            if (emplacement.getProduct() != null) {
                emplacement.addHistory("Le produit " + emplacement.getProduct().getNom() + "_"
                        + emplacement.getProduct().getId() + "a été déplacé vers un autre stockage");
            }
        }

        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Button button = (Button) gridPane.getChildren().get(i);
            Emplacement emplacement = emplacements.get(i);
            emplacement.setProduct(null);
            emplacement.setStatus(EmplacementStatus.Empty);
            button.setText("");
            button.setStyle("");
            button.setDisable(false);
        }
    }

    @Override
    public void resumeTheProduction() {
        if (productCreationTimeline != null && productCreationTimeline.getStatus() == Timeline.Status.STOPPED) {
            productCreationTimeline.play();
        }
    }
}