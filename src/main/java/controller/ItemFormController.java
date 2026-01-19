package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.TM.ItemTM;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn clmCode;

    @FXML
    private TableColumn clmDescription;

    @FXML
    private TableColumn clmPackSize;

    @FXML
    private TableColumn clmPrice;

    @FXML
    private TableColumn clmQuantity;

    @FXML
    private JFXComboBox cmbUnit;

    @FXML
    private TableView tblItems;

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

    private void loadTable(){
        clmCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        clmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ArrayList<ItemTM> ItemArrayList=new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "1234");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");

            while (resultSet.next()){
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
}
