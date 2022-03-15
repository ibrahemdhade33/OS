package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class procececs_random_data implements Comparable<procececs_random_data>,Runnable{
    private int pid ;
    private int strat ;
    private int duration ;
    private int size ;
    Thread thread ;
    ArrayList<Integer>traces ;
    public  double finished_time =0 ;
    public  double turn_around_time= 0 ;
    public  double  wait_time =0 ;

    public  double burst_time = 0 ;
    public int number_of_page_faults =0 ;
    int pointer = 0 ;
    int cycles= 0 ;
    int hits =0 ;

    int min_frames_per_proc ;
    int remainngtime ;
    LinkedList<frame>framesinmem = new LinkedList<>();
    public HashMap<Integer ,ArrayList<Integer>>pagetable ;
    public procececs_random_data(int pid, int strat, int duration, int size, ArrayList<Integer> traces
            ,int min_frames_per_proc) {
        this.pid = pid;
        this.strat = strat;
        this.duration = duration;
        this.size = size;
        this.traces = traces ;
        this.pagetable=pagetable ;
        remainngtime = traces.size() ;
        this.min_frames_per_proc = min_frames_per_proc ;

    }

    @Override
    public int compareTo(procececs_random_data p) {
        if (this.strat == p.strat)
            return 0 ;
        else if (this.strat > p.strat)
            return 1 ;
        else
            return -1 ;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStrat() {
        return strat;
    }

    public void setStrat(int strat) {
        this.strat = strat;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Integer> getTraces() {
        return traces;
    }

    public void setTraces(ArrayList<Integer> traces) {
        this.traces = traces;
    }

    public HashMap<Integer, ArrayList<Integer>> getPagetable() {
        return pagetable;
    }

    public void setPagetable(HashMap<Integer, ArrayList<Integer>> pagetable) {
        this.pagetable = pagetable;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "Run for : " + pid );
    }
}