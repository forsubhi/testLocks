package sample;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main extends Application {


    TimingUtils timingUtils = new TimingUtils();
    ArrayList<WorkerThread> workerThreads = new ArrayList<WorkerThread>();

    DataArrayList  bakeryTimes = new DataArrayList ();
    DataArrayList filterTimes = new  DataArrayList ();
    DataArrayList  reentrantTimes = new  DataArrayList ();


    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new  NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of threads");
        yAxis.setLabel("Time");
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);


        lineChart.setTitle("Thread Charts, 2015");

      /*  int n1 =11 ;
        Bakery bakery1 = new Bakery(n1);
        long timeOfBakery1 = testLock(bakery1, n1);
        Filter filter1 = new Filter(n1);
        long timeOfFilter1 = testLock(filter1, n1);

        Filter filter2 = new Filter(22);
        long timeOfFilter2 = testLock(filter1, 22);

        ReentrantLock reentrantLock1 = new ReentrantLock();
        long timeOfReentran1 = testLock(reentrantLock1, n1);
*/
        for  (int n =10;n<50;n=n+3)

        {
            double[] timesOfBakery = new double[2];
            for(int i=0 ;i<timesOfBakery.length;i++) {

                Bakery bakery = new Bakery(n);
                timesOfBakery[i] = testLock(bakery, n);
            }
            DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(timesOfBakery);
            long timeOfBakery = (long) descriptiveStatistics.getMean();
            long diff = (long) descriptiveStatistics.getStandardDeviation();

            bakeryTimes.put(n, timeOfBakery);
            bakeryTimes.put(n, timeOfBakery-diff);
            bakeryTimes.put(n, timeOfBakery+diff);
            bakeryTimes.put(n, timeOfBakery);
        }

        for  (int n =10;n<50;n=n+3)

        {

            double[] timesOfFilter = new double[2];
            for(int i=0 ;i<timesOfFilter.length;i++) {

                Filter filter = new Filter(n);
                timesOfFilter[i] = testLock(filter, n);
            }
            DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(timesOfFilter);
            long timeOfFilter = (long) descriptiveStatistics.getMean();
            long diff = (long) descriptiveStatistics.getStandardDeviation();

            filterTimes.put(n, timeOfFilter);
            filterTimes.put(n, timeOfFilter-diff);
            filterTimes.put(n, timeOfFilter+diff);
            filterTimes.put(n, timeOfFilter);

        }


        for  (int n =10;n<50;n=n+3)

        {


            double[] timesOfReentrant = new double[2];
            for(int i=0 ;i<timesOfReentrant.length;i++) {

                ReentrantLock reentrantLock = new ReentrantLock();
                timesOfReentrant[i] = testLock(reentrantLock, n);
            }
            DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(timesOfReentrant);
            long timeOfReentrant = (long) descriptiveStatistics.getMean();
            long diff = (long) descriptiveStatistics.getStandardDeviation();

            reentrantTimes.put(n, timeOfReentrant);
            reentrantTimes.put(n, timeOfReentrant-diff);
            reentrantTimes.put(n, timeOfReentrant+diff);
            reentrantTimes.put(n, timeOfReentrant);


        }







        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Bakery ");
        XYChart.Series<Number, Number> series = series1 ;


        for (DataArrayList.Data entry : bakeryTimes) {
            series1.getData().add(new XYChart.Data(entry.x, entry.y));
        }

        XYChart.Series series2 = new XYChart.Series();

        series2.setName("Filter ");

        for (DataArrayList.Data entry : filterTimes) {
            series2.getData().add(new XYChart.Data(entry.x, entry.y));
        }

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Reentran ");
        for (DataArrayList.Data entry : reentrantTimes) {
            series3.getData().add(new XYChart.Data(entry.x, entry.y));
        }



        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(series1, series2, series3);

        stage.setScene(scene);
        stage.show();
      //   series.nodeProperty().get().setStyle("-fx-stroke-width: 10px;");
    }


    public static void main(String[] args) {
        launch(args);
    }



    private long testLock(Lock b,int n)  {

        timingUtils.startTiming(b.getClass().getName()+" N="+n);
        for (int i = 1; i < n; i++) {
            WorkerThread workerThread = new WorkerThread(b);
            workerThread.setName("worker="+String.valueOf(i));
            workerThread.setID(i);
            workerThread.start();
            workerThreads.add(workerThread);

        }

        for (WorkerThread workerThread : workerThreads) {
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error"+e);
            }
        }

        workerThreads.clear();

       return timingUtils.stopTiming();
    }


}
