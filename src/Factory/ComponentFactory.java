package Factory;

import Models.Component;
import Models.ComponentC1;
import Models.ComponentC2;
import Models.ComponentC3;

public class ComponentFactory {

    private int partsBatteryAndEngineLength = 3;
    private int partsCaptorLength = 4;
    private int indexOfNameInTheTxt = 1;

    public ComponentFactory() {
    }

    public Component createComponentFromParts(String[] parts) throws IllegalArgumentException, NumberFormatException {
        if (parts == null || parts.length < partsBatteryAndEngineLength)
            throw new IllegalArgumentException("Insufficient data to create a component");

        String componentName = parts[indexOfNameInTheTxt].trim();
        int fabricationDelay = (int) Long.parseLong(parts[0].trim());
        switch (componentName) {
            case "Batterie":
                if (parts.length == partsBatteryAndEngineLength) {
                    double pourcentOfBattery = Double.parseDouble(parts[2].trim().replace("%", ""));
                    return new ComponentC1(pourcentOfBattery, fabricationDelay);
                }
                break;
            case "Capteur":
                if (parts.length == partsCaptorLength) {
                    int distance = Integer.parseInt(parts[2].trim().replace("m", ""));
                    String color = parts[3].trim();
                    return new ComponentC2(distance, color, fabricationDelay);
                }
                break;
            case "Moteur":
                if (parts.length == partsBatteryAndEngineLength) {
                    int puissance = Integer.parseInt(parts[2].trim().replace("W", ""));
                    return new ComponentC3(puissance, fabricationDelay);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown component type: " + componentName);
        }

        throw new IllegalArgumentException("Insufficient data to create a component of type: " + componentName);
    }
}
