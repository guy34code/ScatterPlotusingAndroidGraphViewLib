package com.example.scatterplotusingandroidgraphviewlib;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    PointsGraphSeries<DataPoint> xyvalues;
    GraphView mscatterplot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mscatterplot = (GraphView)findViewById(R.id.scatterplot);
        xyvalues = new PointsGraphSeries<>();
        createscatterplot();

    }

    public void createscatterplot()
    {
        ArrayList<XYValues> xypoints=new ArrayList<>();


        double start = -100;
        double end = 100;
        for(int i = 0; i<40; i++){
            double randomX = new Random().nextDouble();
            double randomY = new Random().nextDouble();
            double x = start + (randomX * (end - start));
            double y = start + (randomY * (end - start));
            xypoints.add(new XYValues(x,y));
        }

        //sort it in ASC order
        xypoints = sortArray(xypoints);

        //add the data to the series
        for(int i = 0;i <xypoints.size(); i++){
            double x = xypoints.get(i).getX();
            double y = xypoints.get(i).getY();
            xyvalues.appendData(new DataPoint(x,y),true, 1000);
        }

        //set some properties
        xyvalues.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xyvalues.setColor(Color.BLUE);
        xyvalues.setSize(20f);

        //set Scrollable and Scaleable
        mscatterplot.getViewport().setScalable(true);
        mscatterplot.getViewport().setScalableY(true);
        mscatterplot.getViewport().setScrollable(true);
        mscatterplot.getViewport().setScrollableY(true);

        //set manual x bounds
        mscatterplot.getViewport().setYAxisBoundsManual(true);
        mscatterplot.getViewport().setMaxY(150);
        mscatterplot.getViewport().setMinY(-150);

        //set manual y bounds
        mscatterplot.getViewport().setXAxisBoundsManual(true);
        mscatterplot.getViewport().setMaxX(150);
        mscatterplot.getViewport().setMinX(-150);

        mscatterplot.addSeries(xyvalues);
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValues> sortArray(ArrayList<XYValues> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");

        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try{
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m-1).getY();
                double tempX = array.get(m-1).getX();
                if(tempX > array.get(m).getX() ){
                    array.get(m-1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m-1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                }
                else if(tempY == array.get(m).getY()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if(count == factor ){
                    break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }
}
