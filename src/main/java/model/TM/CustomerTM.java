package model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTM {

    private String id;
    private String name;
    private Date dob;
    private double salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;

    public CustomerTM(String id, String name, Date dob, double salary, String address, String city, String province, String postalCode) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.salary = salary;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
}
