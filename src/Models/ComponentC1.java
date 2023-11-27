package Models;

public class ComponentC1 extends Component {
    public double pourcentOfBattery;

    public ComponentC1(double pourcentOfBattery, int fabricationDelay) {
        super("C1", "Battery", 5, "C", fabricationDelay);
        this.pourcentOfBattery = pourcentOfBattery;
    }

    @Override
    public StringBuilder getDescriptions() {
        StringBuilder composantsDetails = new StringBuilder();
        composantsDetails.append("Percentage: " + (int) this.pourcentOfBattery + "%");
        return composantsDetails;
    }
}
