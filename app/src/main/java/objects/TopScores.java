package objects;

import android.location.Location;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TopScores {
    private final int SCOREBOARD_SIZE = 10;  //Set scoreboard size
    private int size = 0;
    private Record [] records ;
    
    public TopScores() {
        this.records = new Record[SCOREBOARD_SIZE];
        //Fill arr with default records, -> record.getScore of each is 0
        this.initRecordsArr();
    }

    public Record[] getRecords() {
        return records;
    }

    public int getSize() {
        return this.size;
    }

    public boolean insertRecord(Record record) {
        /*
        * Method receive record, if records arr not full, Method will insert and sort the array.
        * in case that records is full check if record.getScore() is greater than one of the element score
        * in records arr, if it does -insert the record instead the lowest score record in records array and then sort.
        * Method return true if record is insert to records arr.
        * */
        boolean isInserted = false;     //Indecate if redcord is inserted
        Record minRecord = records[records.length-1];
        if(record.getScore() > minRecord.getScore()) {
            if(minRecord.getScore() == 0)
                size++;
            records[records.length-1] = record;
            isInserted = true;
        }
        this.sort();
        return isInserted;
    }

    private void initRecordsArr() {
        for (int i = 0; i < records.length ; i++) {
            //Location location = new Location("");
            //records[i] = new Record("NA",location,0);
            records[i] = new Record("NA",0,0,0);
        }
    }

    private void sort() {
        Arrays.sort(this.records, Collections.reverseOrder(new Comparator<Record>() {
            @Override
            public int compare(Record record1, Record record2) {
                return record1.getScore()-record2.getScore();
            }
        }));
    }

    @Override
    public String toString() {
        return "TopScores{" +
                "TOPTEN=" + SCOREBOARD_SIZE +
                ", size=" + size +
                ", records=\n" + Arrays.toString(records) +
                '}';
    }
}
