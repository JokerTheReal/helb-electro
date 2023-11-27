package Models;

public class ComponentC3 extends Component {
    public int power;

    public ComponentC3(int power, int fabricationDelay) {
        super("C3", "ElectricEngine", 15, "A", fabricationDelay);
        this.power = power;
    }

    @Override
    public StringBuilder getDescriptions() {
        StringBuilder composantsDetails = new StringBuilder();
        composantsDetails.append("Power: " + (int) this.power + " w");
        return composantsDetails;
    }
}
