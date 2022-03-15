package sample;

import java.util.PrimitiveIterator;

public class tableview1 {
    private int P_ID ;
    private int Arival_Time ;
    private int Size_in_pages ;
    private int number_of_traces ;

    public tableview1(int p_ID, int arival_Time, int size_in_pages, int number_of_traces) {
        this.P_ID = p_ID;
        this.Arival_Time = arival_Time;
        this.Size_in_pages = size_in_pages;
        this.number_of_traces = number_of_traces;
    }

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    public int getArival_Time() {
        return Arival_Time;
    }

    public void setArival_Time(int arival_Time) {
        Arival_Time = arival_Time;
    }

    public int getSize_in_pages() {
        return Size_in_pages;
    }

    public void setSize_in_pages(int size_in_pages) {
        Size_in_pages = size_in_pages;
    }

    public int getNumber_of_traces() {
        return number_of_traces;
    }

    public void setNumber_of_traces(int number_of_traces) {
        this.number_of_traces = number_of_traces;
    }
}
