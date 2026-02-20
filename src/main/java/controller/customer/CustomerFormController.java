package controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
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
import service.custom.impl.CustomerServiceImpl;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        CustomerServiceImpl customerService = new CustomerServiceImpl();
        boolean result = customerService.addCustomer(customer);

        if (result) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Added").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer not Added").show();
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        CustomerServiceImpl customerService = new CustomerServiceImpl();
        boolean result = customerService.deleteCustomer(txtId.getText());

        if (result) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer not Deleted").show();
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        CustomerServiceImpl customerService = new CustomerServiceImpl();
        Customer customer = customerService.searchCustomerById(txtId.getText());
        setTextToValues(customer);

    }


    private void loadTable() {

        CustomerServiceImpl customerService = new CustomerServiceImpl();
        List<Customer> Customers = customerService.getAll();

        ArrayList<CustomerTM> CustomerArrayList = new ArrayList<>();
        Customers.forEach(customer -> {
            CustomerArrayList.add(
                    new CustomerTM(
                            customer.getId(),
                            customer.getTitle(),
                            customer.getName(),
                            customer.getDob(),
                            customer.getSalary(),
                            customer.getAddress(),
                            customer.getCity(),
                            customer.getProvince(),
                            customer.getPostalCode()
                    )
            );
        });

        tblCustomers.setItems(FXCollections.observableArrayList(CustomerArrayList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTitle.getItems().addAll(
                "Mr",
                "Ms",
                "Miss"
        );
        loadTable();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            assert newValue != null;
            setTextToValues(newValue);
        });
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

    private void setTextToValues(CustomerTM customerTM) {
        txtId.setText(customerTM.getId());
        String[] name = customerTM.getName().split(" ", 2);
        cmbTitle.setValue(name[0]);
        txtName.setText(name[1]);
        txtDob.setValue(customerTM.getDob());
        txtSalary.setText(String.valueOf((customerTM.getSalary())));
        txtAddress.setText(customerTM.getAddress());
        txtCity.setText(customerTM.getCity());
        txtProvince.setText(customerTM.getProvince());
        txtPostalCode.setText(customerTM.getPostalCode());
    }
}
