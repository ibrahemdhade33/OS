package sample;

public class tableview {
    private int p_Id ;
    private int Arival_Time ;
    private double Burst_Time ;
    private double TA_Time ;
    private double Finished_Time ;
    private double Wating_Time ;
    private int Page_Faults ;
    private int Cycles ;

    public tableview(int p_Id, int arival_Time, double burst_Time, double TA_Time, double finished_Time, double wating_Time, int page_Faults, int cycles) {
        this.p_Id = p_Id;
        Arival_Time = arival_Time;
        Burst_Time = burst_Time;
        this.TA_Time = TA_Time;
        Finished_Time = finished_Time;
        Wating_Time = wating_Time;
        Page_Faults = page_Faults;
        Cycles = cycles;
    }

    public int getP_Id() {
        return p_Id;
    }

    public void setP_Id(int p_Id) {
        this.p_Id = p_Id;
    }

    public int getArival_Time() {
        return Arival_Time;
    }

    public void setArival_Time(int arival_Time) {
        Arival_Time = arival_Time;
    }

    public double getBurst_Time() {
        return Burst_Time;
    }

    public void setBurst_Time(double burst_Time) {
        Burst_Time = burst_Time;
    }

    public double getTA_Time() {
        return TA_Time;
    }

    public void setTA_Time(double TA_Time) {
        this.TA_Time = TA_Time;
    }

    public double getFinished_Time() {
        return Finished_Time;
    }

    public void setFinished_Time(double finished_Time) {
        Finished_Time = finished_Time;
    }

    public double getWating_Time() {
        return Wating_Time;
    }

    public void setWating_Time(double wating_Time) {
        Wating_Time = wating_Time;
    }

    public int getPage_Faults() {
        return Page_Faults;
    }

    public void setPage_Faults(int page_Faults) {
        Page_Faults = page_Faults;
    }

    public int getCycles() {
        return Cycles;
    }

    public void setCycles(int cycles) {
        Cycles = cycles;
    }
}
