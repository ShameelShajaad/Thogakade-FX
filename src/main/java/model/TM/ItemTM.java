package model.TM;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString

public class ItemTM {

    private String code;
    private String description;
    private String packSize;
    private double price;
    private int quantity;

    public ItemTM(String code, String description, String packSize, String unit, double price, int quantity) {
        this.code = code;
        this.description = description;
        this.packSize = packSize + unit;
        this.price = price;
        this.quantity = quantity;
    }
}
