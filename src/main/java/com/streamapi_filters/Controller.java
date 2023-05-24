package com.streamapi_filters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView<Human> people_table;

    @FXML
    private TableColumn<Human, String> first_name, last_name, email, image_link, ip_address;

    @FXML
    private RadioButton regular_order, ascending_order, descending_order;

    @FXML
    private TextField first_name_input, last_name_input, email_input, image_link_input, ip_address_input;

    @FXML
    private Button filter_data, reset_filter;

    @FXML
    private RadioButton regular_case, lower_case, upper_case;

    @FXML
    private Label people_number;

    private String filename = "src\\main\\resources\\com\\streamapi_filters\\MOCK_DATA.csv";

    private ObservableList<Human> table_list;

    @FXML
    void filterData(ActionEvent event) {
        String first_name = first_name_input.getText();
        String last_name = last_name_input.getText();
        String email = email_input.getText();
        String image_link = image_link_input.getText();
        String ip_address = ip_address_input.getText();
    }

    @FXML
    void resetFilter(ActionEvent event) {
        first_name_input.setText("");
        last_name_input.setText("");
        email_input.setText("");
        image_link_input.setText("");
        ip_address_input.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        image_link.setCellValueFactory(new PropertyValueFactory<>("image_link"));
        ip_address.setCellValueFactory(new PropertyValueFactory<>("ip_address"));

        readData(filename);
        people_table.setItems(table_list);
    }

    void readData(String filename) {
//        List<Human> records = new ArrayList<Human>();
//        try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
//            String[] values = null;
//            while ((values = csvReader.readNext()) != null) {
//                //records.add(Arrays.asList(values));
//            }
//        }

        table_list = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                table_list.add(new Human(data[0], data[1], data[2], data[3], data[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}