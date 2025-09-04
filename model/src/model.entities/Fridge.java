import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Fridge {
    private UUID id;
    private String temperature;
    private boolean isOn;
    private List<FridgeItem> fridgeItens;

    public Fridge(String temperature, boolean isOn) {
        this.temperature = temperature;
        this.isOn = isOn;
        this.fridgeItens = new ArrayList<>();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
    public List<FridgeItem> addFridgeItem(FridgeItem fridgeItem) {
        this.fridgeItens.add(fridgeItem);
        return fridgeItens;
    }
    public List<FridgeItem> getFridgeItens() {
        for (int i = 0; i < fridgeItens.size(); i++) {
            FridgeItem itens = fridgeItens.get(i);
            System.out.println("\nID do item: " + itens.getId()+ "\nNome do item: " + itens.getName() + "\nDisponivel para crianças?: " + itens.isAvailableForChildren() + "\nQuantidade: " + itens.getQuantidade());
        }
        return fridgeItens;
    }
}
