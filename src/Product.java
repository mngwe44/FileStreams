import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String description;
    private String id;
    private double cost;

    public static final int NAME_LENGTH = 35;
    public static final int DESC_LENGTH = 75;
    public static final int ID_LENGTH = 6;
    public static final int RECORD_SIZE = (NAME_LENGTH + DESC_LENGTH + ID_LENGTH) * 2 + 8;

    public Product(String name, String description, String id, double cost) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.cost = cost;
    }

    // Padding utility
    public static String pad(String s, int length) {
        if (s.length() > length) return s.substring(0, length);
        return String.format("%-" + length + "s", s);
    }

    public String getFormattedName() { return pad(name, NAME_LENGTH); }
    public String getFormattedDescription() { return pad(description, DESC_LENGTH); }
    public String getFormattedId() { return pad(id, ID_LENGTH); }

    public String getName() { return name.trim(); }
    public String getDescription() { return description.trim(); }
    public String getId() { return id.trim(); }
    public double getCost() { return cost; }
}
