package com.eecs1021.PartA;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application {
    private static final int N_SAMPLES = 100;
    private static final int MAX_X = 40;
    private static final int MAX_Y = 100;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Reads in and scans three integers from the specified input stream, then returns a
     * {@code QuadraticCoefficients<Integer>} instance comprising the values.
     *
     * @param inputStream a stream to read from
     * @return the coefficient values coupled in an object.
     */
    static QuadraticCoefficients<Integer> readCoefficients(InputStream inputStream) {
        try (var scanner = new Scanner(inputStream)) {
            System.out.println("x^2 coefficient: ");
            var a = scanner.nextInt();

            System.out.println("x^1 coefficient: ");
            var b = scanner.nextInt();

            System.out.println("x^0 coefficient: ");
            var c = scanner.nextInt();

            return new QuadraticCoefficients<>(a, b, c);
        }
    }

    @Override
    public void start(Stage stage) {
        var coefficients = readCoefficients(System.in); // get the values
        var controller = new FormulaController(coefficients); // create the controller
        var data = controller.getData(N_SAMPLES); // get the computed data from the controller

        stage.setTitle("My Equation");

        var xAxis = new NumberAxis("x", 0, MAX_X, 10); // creates the x-axis
        var yAxis = new NumberAxis("y", 0, MAX_Y, 10); // creates the y-axis

        var series = new XYChart.Series<>(FXCollections.observableList(data)); // creates the series (all the data)
        var lineChart = new LineChart<>(xAxis, yAxis, FXCollections.singletonObservableList(series)); // creates the chart
        lineChart.setTitle(controller.getFormulaString());

        Scene scene = new Scene(lineChart,800,600); // creates the JavaFX window

        stage.setScene(scene);
        stage.show();
    }
}
