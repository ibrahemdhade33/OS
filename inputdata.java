package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class inputdata {
    private  int number_of_proceces;
    private  int min_frames_per_proces;
    public inputdata (int number_of_proceces, int min_frames_per_proces){
        this.number_of_proceces = number_of_proceces ;
        this.min_frames_per_proces = min_frames_per_proces ;
    }

    public ArrayList<procececs_random_data> creat_random_data(){
        Random r = new Random() ;
        ArrayList<procececs_random_data>All_proc = new ArrayList<>() ;
        for(int i =0 ; i < number_of_proceces ; i++) {
            HashMap<Integer ,ArrayList<Integer>>pagetable = new HashMap<>() ;
            ArrayList<Integer>traces = new ArrayList<>() ;
            int start = r.ints(0,50 ).findFirst().getAsInt();


            int size = r.ints(1,100).findFirst().getAsInt();
            int tracenumber = r.ints(1000,5000).findFirst().getAsInt();
            //int tracenumber = r.ints(1000,10000).findFirst().getAsInt();
            for (int j = 0 ; j < tracenumber ; j++){
                int t=r.ints(0,size).findFirst().getAsInt();
                traces.add(t) ;

            }

            int duration = r.ints(1,10).findFirst().getAsInt();
            duration*=3600;

            All_proc.add(new procececs_random_data(i , start,duration,size,traces,min_frames_per_proces)) ;

        }
        Collections.sort(All_proc);

        return All_proc ;
    }
}
