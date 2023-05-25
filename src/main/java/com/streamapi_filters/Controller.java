package com.streamapi_filters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    private ObservableList<Human> original_data_list;

    private ObservableList<Human> filtered_data_list;

    Map<String, List<Human>> map;

    @FXML
    void filterData() {
        String first_name = first_name_input.getText();
        String last_name = last_name_input.getText();
        String email = email_input.getText();
        String image_link = image_link_input.getText();
        String ip_address = ip_address_input.getText();

        Predicate<Human> first_name_predicate = human -> human.getFirst_name().toLowerCase().contains(first_name.toLowerCase());
        Predicate<Human> last_name_predicate = human -> human.getLast_name().toLowerCase().contains(last_name.toLowerCase());
        Predicate<Human> email_predicate = human -> human.getEmail().toLowerCase().contains(email.toLowerCase());
        Predicate<Human> image_link_predicate = human -> human.getImage_link().toLowerCase().contains(image_link.toLowerCase());
        Predicate<Human> ip_address_predicate = human -> human.getIp_address().toLowerCase().contains(ip_address.toLowerCase());


        filtered_data_list = original_data_list
                .stream()
                .filter(first_name_predicate)
                .filter(last_name_predicate)
                .filter(email_predicate)
                .filter(image_link_predicate)
                .filter(ip_address_predicate)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (lower_case.isSelected()) {
            filtered_data_list = filtered_data_list
                    .stream()
                    .map(human -> {
                        human.setFirst_name(human.getFirst_name().toLowerCase());
                        human.setLast_name(human.getLast_name().toLowerCase());
                        return human;
                    }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        } else if (upper_case.isSelected()) {
            filtered_data_list = filtered_data_list
                    .stream()
                    .map(human -> {
                        human.setFirst_name(human.getFirst_name().toUpperCase());
                        human.setLast_name(human.getLast_name().toUpperCase());
                        return human;
                    }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        if (ascending_order.isSelected()) {
            filtered_data_list = filtered_data_list.stream().sorted(Comparator.comparing(Human::getFirst_name))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } else if (descending_order.isSelected()) {
            filtered_data_list = filtered_data_list.stream().sorted(Comparator.comparing(Human::getFirst_name).reversed())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }


        people_table.setItems(filtered_data_list);
        long people_number = filtered_data_list.size();
        this.people_number.setText(people_number + " people found.");
    }

    @FXML
    void resetFilter() {
        first_name_input.setText("");
        last_name_input.setText("");
        email_input.setText("");
        image_link_input.setText("");
        ip_address_input.setText("");

        people_table.setItems(original_data_list);
        people_number.setText("1000 people found.");
        regular_order.setSelected(true);
        regular_case.setSelected(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        image_link.setCellValueFactory(new PropertyValueFactory<>("image_link"));
        ip_address.setCellValueFactory(new PropertyValueFactory<>("ip_address"));

        String filename = "src\\main\\resources\\com\\streamapi_filters\\MOCK_DATA.csv";
        readData(filename);
        people_table.setItems(original_data_list);
        filtered_data_list = original_data_list;
    }

    void readData(String filename) {
        original_data_list = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                original_data_list.add(new Human(data[0], data[1], data[2], data[3], data[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mapData() {
        map = new HashMap<>();

        for (Human data : filtered_data_list) {
            String ip_address_current = data.getIp_address();
            String key = ip_address_current.substring(0, ip_address_current.indexOf('.'));
            List<Human> values = map.getOrDefault(key, new ArrayList<>());
            values.add(data);
            map.put(key, values);
        }

        ListView<String> listView = new ListView<>();
        populateListView(listView);

        VBox root = new VBox(listView);

        Scene scene = new Scene(root, 1000, 400);
        Stage map_stage = new Stage();
        map_stage.setScene(scene);
        map_stage.setTitle("Map Display");
        map_stage.show();
    }

    private void populateListView(ListView<String> listView) {
        String previous_key = "";
        for (String key : map.keySet()) {
            if (!previous_key.equals(key)) {
                listView.getItems().add("Key: " + key);
                previous_key = key;
            }
            List<Human> values = map.get(key);
            for (Human value : values) {
                listView.getItems().add("       Value: " + value.toString());
            }
        }
    }
}