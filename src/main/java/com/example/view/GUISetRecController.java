package com.example.view;

import com.example.controller.Controller;
import com.example.model.RecEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GUISetRecController implements Initializable {
    private RecEntity rec;
    private ObservableList<Integer> olFieldCount;
    private ObservableList<Integer> olRecCount;
    private ObservableList<Integer> olFieldType;
    private ObservableList<Integer> olFieldSize;
    @FXML
    ChoiceBox<Integer> fieldcount;
    @FXML
    ChoiceBox<Integer> reccount;
    @FXML
    TextField fieldName;
    @FXML
    ChoiceBox<Integer> fieldType;
    @FXML
    ChoiceBox<Integer> fieldSize;
    @FXML
    TextField recValue;
    @FXML
    TextField recName;

    @FXML
    ChoiceBox<Integer> initfieldcount;
    @FXML
    ChoiceBox<Integer> initreccount;
    @FXML
    TextField ipReceiver;

    private ObservableList<Integer> olMatrixSize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rec = new RecEntity();
        olMatrixSize = FXCollections.observableArrayList();
        for (int i = 1; i < 11; i++)
            olMatrixSize.add(i);
        initfieldcount.setItems(olMatrixSize);
        initreccount.setItems(olMatrixSize);
        olFieldCount = FXCollections.observableArrayList();
        olRecCount = FXCollections.observableArrayList();
        olFieldType = FXCollections.observableArrayList();
        olFieldSize = FXCollections.observableArrayList();
    }

    private void initFieldType() {
        olFieldType.add(rec.DEF_FLD_SZ);
        olFieldType.add(rec.DEF_DOUBLE_SZ);
        olFieldType.add(rec.DEF_FLOAT_SZ);
        olFieldType.add(rec.DEF_INT_SZ);
        olFieldType.add(rec.DEF_SHORT_SZ);
        olFieldType.add(rec.DEF_BYTE_SZ);
        fieldType.setItems(olFieldType);
    }

    private void initFieldSize() {
        olFieldSize.add(RecEntity.FDT_STR);
        olFieldSize.add(RecEntity.FDT_DOUBLE);
        olFieldSize.add(RecEntity.FDT_FLOAT);
        olFieldSize.add(RecEntity.FDT_INT);
        olFieldSize.add(RecEntity.FDT_SHORT);
        olFieldSize.add(RecEntity.FDT_BYTE);
        fieldSize.setItems(olFieldSize);
    }

    @FXML
    public void init() {
        rec.init(initfieldcount.getValue(), initreccount.getValue());
        for (int i = 0; i < rec.getFldcount(); i++)
            olFieldCount.add(i);
        fieldcount.setItems(olFieldCount);
        for (int i = 0; i < rec.getReccount(); i++)
            olRecCount.add(i);
        reccount.setItems(olRecCount);
        initFieldType();
        initFieldSize();
    }

    @FXML
    public void saveField(ActionEvent actionEvent) {
        try {
            rec.setFldData(fieldcount.getValue(), fieldName.getText(), fieldType.getValue(), fieldSize.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveRec(ActionEvent actionEvent) {
        try {
            rec.setValue(fieldcount.getValue(), reccount.getValue(), recValue.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void done(ActionEvent event) {
        try {
            rec.setName(recName.getText());
            Controller.getInstance().getClient().postRec(rec, "https://" + ipReceiver.getText() + ":8080/");
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
