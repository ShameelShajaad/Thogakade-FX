package model.TM;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString

public class CustomerTM {

    private String id;
    private String name;
    private LocalDate dob;
    private double salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;

    public CustomerTM(String id, String title, String name, LocalDate dob, double salary, String address, String city, String province, String postalCode) {
        this.id = id;
        this.name = title+" "+name;
        this.dob = dob;
        this.salary = salary;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
}
