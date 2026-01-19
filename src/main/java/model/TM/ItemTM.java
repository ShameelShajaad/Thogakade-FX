package model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ItemTM {

    private String code;
    private String description;
    private String packSize;
    private double price;
    private int quantity;

}
