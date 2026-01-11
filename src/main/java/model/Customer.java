package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    String id;
    String title;
    String name;
    String address;
    LocalDate dob;
    double salary;
    String city;
    String province;
    String postalCode;
}
