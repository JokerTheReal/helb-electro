package Services;

import Models.Component;
import Models.Emplacement;
import Models.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Factory.ComponentFactory;

public class FactoryService {
    private ComponentFactory componentFactory;
    private ArrayList<Component> composants;

    public FactoryService() {
        this.componentFactory = new ComponentFactory();
        this.composants = new ArrayList<>();
    }

    public ArrayList<Component> loadComposantsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    Component newComposant = componentFactory.createComponentFromParts(parts);
                    if (newComposant != null) {
                        composants.add(newComposant);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return composants;
    }

    public void sellProduct(Emplacement emplacement) {
        createInvoice(emplacement.getProduct());
    }

    private void createInvoice(Product product) {
        String path = "C:\\temp\\factory ticket\\";

        String filename = path + product.getNom() + "_" + product.getId() + ".txt";
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(product.getId());
            out.println("\n");
            out.println(product.getNom());
            out.println("\n");
            out.println(product.getColor());
            out.println("\n");
            out.println(product.getComponentDescriptions());
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write to the file: " + e.getMessage());
        }
    }
}
