package Models;

public class ComponentC2 extends Component {
    public int distance;
    public String color;

    public ComponentC2(int distance, String color, int fabricationDelay) {
        super("C2", "MovementCaptor", 10, "B", fabricationDelay);
        this.distance = distance;
        this.color = color;
    }

    @Override
    public StringBuilder getDescriptions() {
        StringBuilder composantsDetails = new StringBuilder();
        composantsDetails.append("Distance: " + (int) this.distance + "m");
        composantsDetails.append("\n");
        composantsDetails.append("Color: " + this.color);
        return composantsDetails;
    }
}
