package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.TM.CustomerTM;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox cmbTitle;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colCity;

    @FXML
    private TableColumn<CustomerTM, Date> colDob;

    @FXML
    private TableColumn<CustomerTM, String> colId;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colPostalCode;

    @FXML
    private TableColumn<CustomerTM, String> colProvince;

    @FXML
    private TableColumn<CustomerTM, Double> colSalary;

    @FXML
    private TableView<CustomerTM> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private DatePicker txtDob;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = cmbTitle.getValue().toString();
        String name = txtName.getText();
        LocalDate dob = txtDob.getValue();
        double salary = Double.parseDouble(txtSalary.getText());
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        Customer customer = new Customer(id, title, name, dob, salary, address, city, province, postalCode);

        System.out.println(customer);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "1234");

            PreparedStatement psTm = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)");

            psTm.setString(1, customer.getId());
            psTm.setString(2, customer.getTitle());
            psTm.setString(3, customer.getName());
            psTm.setObject(4, customer.getDob());
            psTm.setDouble(5, customer.getSalary());
            psTm.setString(6, customer.getAddress());
            psTm.setString(7, customer.getCity());
            psTm.setString(8, customer.getProvince());
            psTm.setString(9, customer.getPostalCode());

            if (psTm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer not added");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "1234");

            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM customer WHERE CustID = ?");

            psTm.setString(1, txtId.getText());
            ResultSet resultSet = psTm.executeQuery();

            resultSet.next();

            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );

            setTextToValues(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadTable() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        ArrayList<CustomerTM> CustomerArrayList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "1234");
            System.out.println(connection);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");

            while (resultSet.next()) {
                CustomerArrayList.add(
                        new CustomerTM(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDate(4),
                                resultSet.getDouble(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9)
                        )
                );
            }

            tblCustomers.setItems(FXCollections.observableArrayList(CustomerArrayList));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTitle.getItems().addAll(
                "Mr",
                "Ms",
                "Miss"
        );
        loadTable();
    }

    private void setTextToValues(Customer customer) {
        txtId.setText(customer.getId());
        cmbTitle.setValue(customer.getTitle());
        txtName.setText(customer.getName());
        txtDob.setValue(customer.getDob());
        txtSalary.setText(String.valueOf((customer.getSalary())));
        txtAddress.setText(customer.getAddress());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPostalCode.setText(customer.getPostalCode());
    }
}
