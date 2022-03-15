package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class procecesexccuter {

    static ArrayList<String> mem_view = new ArrayList<>();
    private procececs_random_data p;
    int size_in_frames = Controller.sizeofmem;
    static LinkedList<memory> mem = new LinkedList<>();
    int quantom_time = Controller.timequant;

    //LinkedList<memory>memory = new LinkedList<>() ;
    public procecesexccuter(procececs_random_data p) {
        this.p = p;
    }

    //this class represent mmu
    boolean second_chance()  {

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
                mem_view.add("hit in memory , page id : " + p.traces.get(i) + ", processes id : " + p.getPid()+", at time : " + simulation.time);
                p.hits++ ;
            } else {

                p.cycles += 305;
                p.number_of_page_faults++;
                p.remainngtime--;
                p.burst_time++;
                p.pointer++;
                if (p.remainngtime == 0) {

                    return true;
                }
                mem_view.add("page fault , page id : " + p.traces.get(i) + ",processes id : " + p.getPid() + ", context switch and page " +
                        "replacement, at time : " + simulation.time);
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


                if (p.remainngtime == 0) {
                    return true;
                }
                return false ;
            }
            t += 1;
            p.remainngtime--;
            p.burst_time++;
            if (p.remainngtime == 0) {

                return true;
            }
            if (t >= quantom_time) {
                mem_view.add("Context Switch, End Of The Quantum Time, P_ID : " + p.getPid() +"at time :" +simulation.time) ;
                p.cycles+=5 ;
                if (quantom_time > p.remainngtime) {
                    p.remainngtime = 0;
                    p.burst_time += (quantom_time - p.remainngtime);

                }

                if (p.remainngtime == 0) {

                    return true;
                } else {

                    return false;
                }

            }
        }

        return true;
    }

    public boolean fifo() {


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
                simulation.ready_queue.addLast(mem.getFirst().getP());
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

                if (p.remainngtime == 0) {
                    return true;
                }
                return false;
            }
            t += 1;
            p.remainngtime--;
            p.burst_time++;
            if (p.remainngtime == 0) {

                return true;
            }
            if (t >= quantom_time) {
                if (quantom_time > p.remainngtime) {
                    p.remainngtime = 0;
                    p.burst_time += (quantom_time - p.remainngtime);
                }

                if (p.remainngtime == 0) {

                    return true;
                } else {

                    return false;
                }

            }
        }

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

    public procececs_random_data getP() {
        return p;
    }

    public void setP(procececs_random_data p) {
        this.p = p;
    }
}
