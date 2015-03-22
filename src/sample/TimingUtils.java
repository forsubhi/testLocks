package sample;

import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by MohammedSubhi on 2/9/2015.
 */
public class TimingUtils {




    String sectionName ="";

    long t ;

    Stack<TimeSection> timeSection = new Stack<TimeSection>();


    public  TimingUtils ()
    {

       // logger = Logger.getLogger("imonaTiming");



    }

    public  void startTiming(String sectionName)
    {
        this.sectionName= sectionName;
        t = System.currentTimeMillis();

        timeSection.push(new TimeSection(sectionName,t));

        // logger.info("section " + sectionName + " started");
        System.out.println("section " + sectionName + " started");

    }


    public long stopTiming()
    {
        TimeSection timeSection1 = timeSection.pop();
        t = System.currentTimeMillis() - timeSection1.t;
        //  logger.info("section " + timeSection1.sectionName + " ended" + "=" + t);
        System.out.println("section " + timeSection1.sectionName + " ended" + "=" + t);
        return t;
    }



    public class   TimeSection
    {
        public TimeSection (String sectionName,Long t)
        {
            this.sectionName=sectionName;
            this.t=t;

        }

        long t ;
        String sectionName ;
    }

}