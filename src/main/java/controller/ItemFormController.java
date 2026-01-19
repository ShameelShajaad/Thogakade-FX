package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Item;
import model.TM.ItemTM;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<ItemTM, String> clmCode;

    @FXML
    private TableColumn<ItemTM, String> clmDescription;

    @FXML
    private TableColumn<ItemTM, String> clmPackSize;

    @FXML
    private TableColumn<ItemTM, Double> clmPrice;

    @FXML
    private TableColumn<ItemTM, Integer> clmQuantity;

    @FXML
    private JFXComboBox cmbUnit;

    @FXML
    private TableView<ItemTM> tblItems;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;


    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        String unit = cmbUnit.getValue().toString();
        double price = Double.parseDouble(txtPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());

        Item item = new Item(code, description, packSize, unit, price, quantity);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "1234");

            PreparedStatement psTm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?)");

            psTm.setString(1, item.getCode());
            psTm.setString(2, item.getDescription());
            psTm.setString(3, item.getPackSize()+item.getUnit());
            psTm.setDouble(4, item.getPrice());
            psTm.setInt(5, item.getQuantity());

            if (psTm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item not Added").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    private void loadTable() {
        clmCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        clmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ArrayList<ItemTM> ItemArrayList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "1234");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");

            while (resultSet.next()) {
                ItemArrayList.add(
                        new ItemTM(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getInt(5)
                        )
                );
            }

            tblItems.setItems(FXCollections.observableArrayList(ItemArrayList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbUnit.getItems().addAll(
                "kg",
                "g"
        );
        loadTable();
    }

    private void setTextToValues(Item item) {
        txtItemCode.setText(item.getCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        cmbUnit.setValue(item.getUnit());
        txtPrice.setText(String.valueOf(item.getPrice()));
        txtQuantity.setText(String.valueOf(item.getQuantity()));
    }
}
