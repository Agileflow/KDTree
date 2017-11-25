package plot;

import exception.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import support.KDTree;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Agile-flow on 3/23/2017.
 */
public final class KDChart<T extends Comparable<T>, E extends Comparable<E>> extends Application {

    private KDTree<T, E> tr;
    private final int PARTITION  = 2;
    private final String[] TITLE = {"Locked", "Unlocked"};
    private final double LOWERBOUND = 0.0;
    private final double TICK_UNIT = 0.1;
    private final double X_UPPERBOUND = 200.0;
    private final double Y_UPPERBOUND = 200.0;

    private XYChart.Series[] populateSeries(List<E[]> points, int partitions, String... title){

        XYChart.Series[] series = new XYChart.Series[partitions];
        int i = 0;
        for(int k = 0; k < partitions; i++){
            series[k] = new XYChart.Series();
            series[k].setName(title[k]);
        }

        for(E[] item : points){
            series[i%partitions].getData().add(new XYChart.Data(item[0], item[1]));
            i++;
        }
        return series;
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("KDCHART start");

        stage.setTitle("KDTree Chart Plot");
        final NumberAxis xAxis = new NumberAxis(LOWERBOUND, X_UPPERBOUND, TICK_UNIT);
        final NumberAxis yAxis = new NumberAxis(LOWERBOUND, Y_UPPERBOUND, TICK_UNIT);
        final ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("X");
        yAxis.setLabel("Y");
        sc.setTitle("KDTree Chart");

        sc.getData().addAll(populateSeries(this.tr.getPoints(), PARTITION,TITLE));
        Scene scene  = new Scene(sc, 720, 440);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws CoordinateDuplicateException, InvalidDimensionException, IndexOutOfRangeException, CoordinateNotFoundException, EmptyKDTreeException {

        KDTree<String, Integer> tree = new KDTree<>(2);

        tree.insert("Abbey", 51,75);
        tree.insert("Dare", 25,40);
        tree.insert("Sam", 70,70);
        tree.insert("Ope", 10,30);
        tree.insert("Tolu", 55,1);
        tree.insert("Solution", 35,90);
        tree.insert("Leke", 60,80);
        tree.insert("Joses", 1,10);
        tree.insert("Sandra", 50,50);

        display("Size: " + tree.size());
        tree.inOrder();
        display(tree.remove(1, 10));
        tree.inOrder();
        display("Size: " + tree.size());

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                launch(args);
            }
        });
        th.start();
    }

    public static void display(Object msg){
        System.out.println("\n" + msg);
    }
}
