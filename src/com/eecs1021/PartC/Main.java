package com.eecs1021.PartC;

import com.eecs1021.DataController;
import com.eecs1021.SerialPortService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.FormatStringConverter;

import java.text.DateFormat;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static TableView<XYChart.Data<Number, Number>> getTableView() {
        // TODO: Create a `TableView<XYChart.Data<Number, Number>>`.
        //  Refer to the documentation for further details.

        var table = new TableView<XYChart.Data<Number, Number>>();

        var timeColumn = new TableColumn<XYChart.Data<Number, Number>, Number>("Date");
        timeColumn.setCellValueFactory(row -> row.getValue().XValueProperty());

        var dateFormat = DateFormat.getTimeInstance();
        var converter = new FormatStringConverter<Number>(dateFormat);

        timeColumn.setCellFactory(column -> new TextFieldTableCell<>(converter));

        var valueColumn = new TableColumn<XYChart.Data<Number, Number>, Number>("Value");
        valueColumn.setCellValueFactory(row -> row.getValue().YValueProperty());

        table.getColumns().setAll(List.of(timeColumn, valueColumn));

        return table;
    }

    @Override
    public void start(Stage primaryStage) {
        var serialPort = SerialPortService.getSerialPort("/dev/cu.usbserial-1440");
        var table = getTableView();
        var controller = new DataController();

        serialPort.addDataListener(controller);
        table.setItems(controller.getDataPoints());

        var vbox = new VBox(table);
        var scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}