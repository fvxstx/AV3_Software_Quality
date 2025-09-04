import java.util.UUID;

public class FridgeItem {
    private UUID id;
    private String name;
    private boolean isAvailableForChildren;
    private int quantidade;

    public FridgeItem(String name, boolean isAvailableForChildren, int quantidade) {
        this.name = name;
        this.isAvailableForChildren = isAvailableForChildren;
        this.quantidade = quantidade;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailableForChildren() {
        return isAvailableForChildren;
    }

    public void setAvailableForChildren(boolean availableForChildren) {
        isAvailableForChildren = availableForChildren;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
