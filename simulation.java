package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class simulation {
    int size_in_frames = Controller.sizeofmem;
    static ArrayList<String> mem_view = new ArrayList<>();
    private procececs_random_data p;
    static LinkedList<memory> mem = new LinkedList<>();
    static int quantom_time =Controller.timequant;
    static int time = 0 ;

     Lock lock = new ReentrantLock(true) ;
    int  pointer = 0 ;
    static ArrayList<procececs_random_data> proc_final_information = new ArrayList<>() ;
    static LinkedList<procececs_random_data> ready_queue ;
    ArrayList<procececs_random_data> proc  ;



    public simulation (ArrayList<procececs_random_data>proc) {
        this.proc = proc ;
        ready_queue = new LinkedList<>() ;
        System.out.println(quantom_time);
    }
     boolean fininshed = false;
    void round_robin() throws InterruptedException {

        while (ready_queue.isEmpty()){
            check_proc_ariaval();
            time++;
        }

        while (!ready_queue.isEmpty() || !proc.isEmpty()){

             p = ready_queue.getFirst();
            ready_queue.removeFirst();
            procecesexccuter r = new procecesexccuter(p) ;

            if (Controller.choice==1){

                Thread t= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock.lock();
                            fininshed = r.fifo() ;
                        }
                        finally {
                            lock.unlock();
                        }

                    }
                }) ;
               t.start();
               t.join();
            }
            else{
                Thread t= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock.lock();
                            fininshed = r.second_chance();
                        }
                        finally {
                            lock.unlock();
                        }

                    }
                }) ;
               t.start();
               t.join();

            }

            if (fininshed){
                System.out.println("sldfksldnflksdf");
                p.burst_time=p.burst_time / 1000.0 ;
                p.finished_time = Math.round((time/1000.0)*100.0)/100.0 ;
                p.turn_around_time = Math.round((p.finished_time - (p.getStrat()/1000.0))*100)/100.0;
                p.wait_time = Math.round((p.turn_around_time - (p.burst_time))*100.0)/100.0 ;
                proc_final_information.add(p);
            }
             else {
                ready_queue.addLast(p);
            }

            check_proc_ariaval();
            time++;


        }

        Collections.sort(proc_final_information);

    }


    public void setProc(ArrayList<procececs_random_data> proc) {
        this.proc = proc;
    }
    //this class represent mmu
    void second_chance()  {
        lock.lock();
        int t = 0;


        for (int i = p.pointer; i < p.traces.size(); i++) {

            p.pointer = i;
            if (size_in_frames > p.min_frames_per_proc) {
                if (!check_proc_in_mem()) {
                    mem.addLast(new memory(p));
                    size_in_frames -= p.min_frames_per_proc;
                }
            } else {
                mem.getFirst().getP().cycles += 5;

                mem.removeFirst();
                mem.addLast(new memory(p));
            }
            if (find_if_in_memory(p.traces.get(i))) {
                mem_view.add("hit in memory , page id : " + p.traces.get(i) + ", processes id : " + p.getPid()+", at time : " + time);
                p.hits++ ;
            } else {
                p.cycles += 305;
                p.number_of_page_faults++;
                p.remainngtime--;
                p.burst_time++;
                p.pointer++;
                if (p.remainngtime == 0) {
                    fininshed =true ;
                    lock.unlock();
                    return;
                }
                mem_view.add("page fault , page id : " + p.traces.get(i) + ",processes id : " + p.getPid() + ", context switch and page " +
                        "replacement, at time : " + time);
                if (p.framesinmem.size() < p.min_frames_per_proc) {
                    p.framesinmem.addLast(new frame(p.traces.get(i), 0));
                } else {
                    frame temp = null;
                    int f = 0;
                    for (frame j : p.framesinmem) {
                        if (j.bit_referance == 1) {
                            j.bit_referance = 0;
                        } else {

                            temp = j;
                            f = 1 ;
                            break;
                        }
                    }
                    if (f == 1) {
                        p.framesinmem.remove(temp);
                        p.framesinmem.addLast(new frame(p.traces.get(i), 0));

                    }
                }
                fininshed =false ;
                lock.unlock();
                return;
            }
            t += 1;
            p.remainngtime--;
            p.burst_time++;
            if (p.remainngtime == 0) {
                fininshed =true ;
                lock.unlock();
                return  ;
            }
            if (t >= quantom_time) {
                mem_view.add("Context Switch, End Of The Quantum Time, P_ID : " + p.getPid() +"at time :" +time) ;
                p.cycles+=5 ;
                if (quantom_time > p.remainngtime) {
                    p.remainngtime = 0;
                    p.burst_time += (quantom_time - p.remainngtime);

                }

                if (p.remainngtime == 0) {

                    fininshed =true ;
                    lock.unlock();
                    return ;
                } else {
                    fininshed =false ;
                    lock.unlock();
                    return ;
                }

            }
        }
        fininshed =true ;
        lock.unlock();

    }
    public boolean fifo() {

        lock.lock();
        int t = 0;


        for (int i = p.pointer; i < p.traces.size(); i++) {
            p.pointer = i;
            if (size_in_frames > p.min_frames_per_proc) {
                if (!check_proc_in_mem()) {
                    mem.addLast(new memory(p));

                    size_in_frames -= p.min_frames_per_proc;
                }
            } else {
                mem.getFirst().getP().cycles += 5;
                //ready_queue.addLast(mem.getFirst().getP());
                mem.removeFirst();
                mem.addLast(new memory(p));
            }
            if (find_if_in_memory(p.traces.get(i))) {
                mem_view.add("hit in memory , page id : " + p.traces.get(i) + "processes id : " + p.getPid());
                p.hits++;
            } else {
                p.cycles += 305;
                p.number_of_page_faults++;
                p.remainngtime--;
                p.burst_time++;
                p.pointer++;
                if (p.remainngtime==0){
                    lock.unlock();
                    return true ;
                }
                mem_view.add("page fault , page id : " + p.traces.get(i) + "processes id : " + p.getPid() + " context switch and page " +
                        "replacement");
                if (p.framesinmem.size() < p.min_frames_per_proc) {
                    p.framesinmem.addLast(new frame(p.traces.get(i), 0));
                } else {
                    p.framesinmem.removeFirst();
                    p.framesinmem.addLast(new frame(p.traces.get(i), 0));
                }
                lock.unlock();
                return false;
            }
            t += 1;
            p.remainngtime--;
            p.burst_time++;
            if (p.remainngtime == 0) {
                lock.unlock();
                return true;
            }
            if (t >= quantom_time) {
                if (quantom_time > p.remainngtime) {
                    p.remainngtime = 0;
                    p.burst_time += (quantom_time - p.remainngtime);
                }

                if (p.remainngtime == 0) {
                    lock.unlock();
                    return true;
                } else {
                    lock.unlock();
                    return false;
                }

            }
        }
       lock.unlock();
        return true;

    }
    boolean find_if_in_memory(int pagenumber)  {
        for (frame m : p.framesinmem){
            if (pagenumber == m.page_id){
                m.bit_referance = 1 ;

                return true ;
            }

        }
        return false ;
    }

    boolean check_proc_in_mem(){
        for (memory m : mem){
            if (m.getP() == p){
                return true ;
            }
        }
        return false ;
    }
    void check_proc_ariaval(){

        while (!proc.isEmpty()&&proc.get(0).getStrat() <= time)
        {
            ready_queue.add(proc.get(0));
            proc.remove(proc.get(0)) ;
        }
    }
}