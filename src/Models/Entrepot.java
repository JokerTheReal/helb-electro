package Models;

import java.util.ArrayList;
import java.util.List;

public class Entrepot {
    private List<Emplacement> emplacements = new ArrayList<>();
    private ArrayList<Component> components;
    private List<Product> produits;
    private List<Product> produitsFini;

    public Entrepot() {
        emplacements = new ArrayList<>();
        this.components = new ArrayList<>();
        this.produits = new ArrayList<>();
        this.produitsFini = new ArrayList<>();
    }

    public boolean areAllEmplacementsFull() {
        for (Emplacement emplacement : this.getEmplacements()) {
            if (emplacement.getStatus() == EmplacementStatus.Empty) {
                return false;
            }
        }
        return true;
    }

    public List<Product> getProduits() {
        return this.produits;
    }

    public void ajouterProduitFini(Product produit) {
        produitsFini.add(produit);
    }

    public List<Product> getProduitsFini() {
        return this.produitsFini;
    }

    public List<Emplacement> getEmplacements() {
        return emplacements;
    }

    public void addEmplacements(Emplacement emplacement) {
        emplacements.add(emplacement);
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComposants(ArrayList<Component> components) {
        this.components = components;
    }

    public void addComposant(Component component) {
        this.components.add(component);
    }
}
