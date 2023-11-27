package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String nom;
    private String ecoScore;
    private ArrayList<String> requiredComponants;
    private ArrayList<Component> components;
    private int prix;
    private int duree;
    private String color;

    public Product(String nom, String ecoScore, int prix, int duree, String color,
            ArrayList<Component> productComponents) {
        this.nom = nom;
        this.ecoScore = ecoScore;
        this.prix = prix;
        this.duree = duree;
        this.requiredComponants = new ArrayList<>();
        this.components = productComponents;
        this.color = color;
        this.id = getUniqueId();
    }

    private String getUniqueId() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public Component getComposant(String componentId) {
        for (Component component : components) {
            if (component.getId().equals(componentId)) {
                return component;
            }
        }
        return null;
    }

    public String getComponentDescriptions() {
        StringBuilder composantsDetails = new StringBuilder();
        composantsDetails.append("Components specifications: ");
        composantsDetails.append("\n");
        for (Component component : this.components) {
            Component comp = getComposant(component.getId());
            composantsDetails.append(comp.getDescriptions());
        }

        return composantsDetails.toString();
    }

    public ArrayList<Component> getAllComposants() {
        return components;
    }

    public String getColor() {
        return color;
    }

    public String getNom() {
        return nom;
    }

    public String getId() {
        return id;
    }

    public String getEcoScore() {
        return ecoScore;
    }

    public int getPrice() {
        return prix;
    }

    public int getDuration() {
        return duree;
    }

    public int getBuildDuration() {
        return duree;
    }

    public void addRequiredComponant(String composantId) {
        this.requiredComponants.add(composantId);
    }

    public ArrayList<String> getRequiredComponants() {
        return this.requiredComponants;
    }

    public boolean checkRequiredComponents(List<Component> components) {
        if (requiredComponants.size() == 0)
            return true;

        if (components == null)
            return false;

        return requiredComponants.stream()
                .allMatch(id -> components.stream().anyMatch(c -> c.getId().equals(id)));
    }

}
