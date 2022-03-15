package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class textarea {
    @FXML
    private TextArea memview;

    @FXML
    private Label tc;

    @FXML
    private Label tpf;
    public textarea(){


    }
    @FXML
    private Button show;
    @FXML
    private Label hit;
    @FXML
    private Label miss;
    @FXML
    void show(ActionEvent event) {
        double h =  Math.round((Controller.total_hits/(double)Controller.total_traces)*100*100.0)/100.0;
        double m =Math.round((Controller.total_page_faults/(double)Controller.total_traces)*100*100.0)/100.0 ;
        StringBuilder s = new StringBuilder();
            for (String s1 : procecesexccuter.mem_view)
                s.append(s1).append("\n");
                memview.setText(s.toString());

            tc.setText("Total cycles  : " + String.valueOf(Controller.total_cycles));
            tpf.setText("Total Page Faults : "+String.valueOf(Controller.total_page_faults));
            hit.setText("Hit Ratio : " + String.valueOf(h)+" %");
            miss.setText("Miss Ratio : " + String.valueOf(m)+" %");

    }
}
