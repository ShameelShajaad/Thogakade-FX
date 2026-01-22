package controller.item;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        double price = Double.parseDouble(txtPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());

        Item item = new Item(code, description, packSize, price, quantity);

        ItemServiceImpl itemService = new ItemServiceImpl();
        boolean result = itemService.addItem(item);

        if (result) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item Added").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item not Added").show();
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

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM item WHERE ItemCode = ?");

            psTm.setString(1, txtItemCode.getText());
            ResultSet resultSet = psTm.executeQuery();

            resultSet.next();


            Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

            setTextToValues(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadTable() {
        ArrayList<ItemTM> ItemArrayList = new ArrayList<>();

        try {
            Connection connection = DbConnection.getInstance().getConnection();

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
        loadTable();

        clmCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        clmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            assert newValue != null;
            setTextToValues((ItemTM) newValue);
        });
    }

    private void setTextToValues(Item item) {
        txtItemCode.setText(item.getCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtPrice.setText(String.valueOf(item.getPrice()));
        txtQuantity.setText(String.valueOf(item.getQuantity()));
    }

    private void setTextToValues(ItemTM itemTM) {
        txtItemCode.setText(itemTM.getCode());
        txtDescription.setText(itemTM.getDescription());
        txtPackSize.setText(itemTM.getPackSize());
        txtPrice.setText(String.valueOf(itemTM.getPrice()));
        txtQuantity.setText(String.valueOf(itemTM.getQuantity()));
    }
}
