package sample;

import java.util.ArrayList;

public class DataArrayList extends ArrayList<DataArrayList.Data> {


    public  void put(Integer x,long y)
    {
        Data data = new Data();
        data.x=x;
        data.y=y;
        add(data);

    }



    public  static class Data
    {
        Integer x ;
        long y ;
    }

}
