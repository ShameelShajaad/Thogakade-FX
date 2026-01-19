package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ItemFormController {

    @FXML
    private TableColumn<?, ?> clmCode;

    @FXML
    private TableColumn<?, ?> clmDescription;

    @FXML
    private TableColumn<?, ?> clmPackSize;

    @FXML
    private TableColumn<?, ?> clmPrice;

    @FXML
    private TableColumn<?, ?> clmQuantity;

    @FXML
    private JFXComboBox<?> cmbUnit;

    @FXML
    private TableView<?> tblItems;

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
    void btnReloadOnAction(ActionEvent event) {

    }

}
