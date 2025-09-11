public class Main {
    public static void main(String[] args) {
        User usuario = new User("vini", "vini.lacerda@hotmail.com", "12345");
        usuario.showInfo();
        FridgeItem fridgeItem1 = new FridgeItem("maçã", true, 10);
        Fridge fridge = new Fridge("18", true);
        fridge.addFridgeItem(fridgeItem1);
        fridge.getFridgeItens();
    }
}