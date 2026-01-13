package Lab3.src;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Робота з класом Меблі.
 * Створення масиву, сортування та пошук.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Furniture[] inventory = new Furniture[] {
                new Furniture("Wardrobe", "IKEA", "White", 12000.0, 210.0),
                new Furniture("Stool", "Local", "Red", 1500.0, 45.0),
                new Furniture("Sofa", "Ashley", "Grey", 12000.0, 90.0),
                new Furniture("Bookshelf", "Jysk", "Wood", 500.0, 200.0),
                new Furniture("Table", "Jysk", "Brown", 5000.0, 75.0),
                new Furniture("Bed", "SleepMaster", "White", 12000.0, 50.0),
                new Furniture("Chair", "IKEA", "White", 1500.0, 95.0),
                new Furniture("Armchair", "Ashley", "Grey", 5000.0, 105.0)
            };

            System.out.println("--- Initial array (Random order) ---");
            printArray(inventory);

            Arrays.sort(inventory, Comparator
                .comparingDouble(Furniture::getPrice) 
                .thenComparing(Comparator.comparingDouble(Furniture::getHeight).reversed()) 
            );

            System.out.println("\n--- Sorted array (Price: asc, Height: desc) ---");
            printArray(inventory);

            Furniture target = new Furniture("Sofa", "Ashley", "Grey", 12000.0, 90.0);
            System.out.println("\n--- Searching for: " + target.getName() + " ---");

            boolean found = false;
            for (Furniture item : inventory) {
                if (item.equals(target)) {
                    System.out.println("Object found!");
                    System.out.println("Details: " + item);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Object not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printArray(Furniture[] array) {
        for (Furniture item : array) {
            System.out.println(item);
        }
    }
}

/**
 * Клас Меблі.
 */
class Furniture {
    private String name;
    private String brand;
    private String color;
    private double price;
    private double height;

    /**
     * Конструктор.
     */
    public Furniture(String name, String brand, String color, double price, double height) {
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.height = height;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getHeight() { return height; }

    /**
     * Перетворення в рядок.
     */
    @Override
    public String toString() {
        return "Furniture{name='" + name + "', brand='" + brand + "', color='" + color + 
               "', price=" + price + ", height=" + height + "}";
    }

    /**
     * Порівняння об'єктів.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Furniture f = (Furniture) o;
        return Double.compare(f.price, price) == 0 &&
                Double.compare(f.height, height) == 0 &&
                Objects.equals(name, f.name) &&
                Objects.equals(brand, f.brand) &&
                Objects.equals(color, f.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, color, price, height);
    }
}