package Models;

import java.util.ArrayList;

public class Emplacement {
    private Product product;
    private EmplacementStatus status;
    public ArrayList<String> histories;

    public Emplacement() {
        this.histories = new ArrayList<String>();
        this.status = EmplacementStatus.Empty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product produit) {
        this.product = produit;
    }

    public void setStatus(EmplacementStatus newStatus) {
        status = newStatus;
    }

    public EmplacementStatus getStatus() {
        return status;
    }

    public void addHistory(String history) {
        histories.add(history);
    }

    public ArrayList<String> getHistories() {
        return histories;
    }
}
