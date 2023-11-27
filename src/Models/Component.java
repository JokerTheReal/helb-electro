package Models;

public class Component {
    private String id;
    private String nom;
    private double prixVente;
    private String ecoScore;
    private int dureeFabrication;

    public Component(String id, String nom, double prixVente, String ecoScore, int dureeFabrication) {
        this.id = id;
        this.nom = nom;
        this.prixVente = prixVente;
        this.ecoScore = ecoScore;
        this.dureeFabrication = dureeFabrication;
    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getDureeFabrication() {
        return dureeFabrication;
    }

    @Override
    public String toString() { // https://perso.telecom-paristech.fr/hudry/coursJava/heritage/toString.html
        return id + " - " + nom + " - " + "Sell price : " + prixVente + ", Eco-score : " + ecoScore
                + ", build duration : " + dureeFabrication + "\n";
    }

    public StringBuilder getDescriptions() {
        return new StringBuilder();
    }
}
