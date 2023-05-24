module com.streamapi_filters {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.streamapi_filters to javafx.fxml;
    exports com.streamapi_filters;
}