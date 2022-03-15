package sample;

public class frame {
    int page_id ;
    int bit_referance ;
    public frame(int page_id , int bit_referance){
        this.page_id = page_id ;
        this.bit_referance=bit_referance ;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public int getBit_referance() {
        return bit_referance;
    }

    public void setBit_referance(int bit_referance) {
        this.bit_referance = bit_referance;
    }
}
