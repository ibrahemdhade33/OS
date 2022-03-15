package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements Initializable {
    static Lock lock = new ReentrantLock(true) ;
    ObservableList<tableview> list = FXCollections.observableArrayList();
    ObservableList<tableview1> list2 = FXCollections.observableArrayList();
    ArrayList<procececs_random_data> proc ;
    static int total_page_faults =0 ;
    static int total_cycles =0;
    static int total_traces = 0 ;
    static int total_hits =0 ;
    static int total_miss =0 ;
    @FXML
    private Button browse_file;

    @FXML
    private TextField file_path;

    @FXML
    private ComboBox comb;

    @FXML
    private TableView<tableview> tableview;

    @FXML
    private Button random;

    @FXML
    private TableColumn<tableview, Integer> Arival_Time;

    @FXML
    private TableColumn<tableview, Double> Burst_Time;

    @FXML
    private TableColumn<tableview, Integer> Cycles;

    @FXML
    private TableColumn<tableview, Double> Finished_Time;

    @FXML
    private TableColumn<tableview, Integer> P_ID;

    @FXML
    private TableColumn<tableview, Integer> Page_Faults;

    @FXML
    private TableColumn<tableview, Double> TA_Time;

    @FXML
    private TableColumn<tableview, Double> Waiting_Time;
    @FXML
    private TableColumn<tableview1, Integer> numberoftraces;

    @FXML
    private TableColumn<tableview1, Integer> pid1;
    @FXML
    private TableColumn<tableview1, Integer> sizepages;
    @FXML
    private TableView<tableview1> tableview1;
    @FXML
    private TableColumn<tableview1, Integer> arive1;

    @FXML
    void browse(ActionEvent event) {
        FileChooser fileChooserShares = new FileChooser();
        fileChooserShares.setTitle("Select project file ");
        fileChooserShares.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File selectedFile = fileChooserShares.showOpenDialog(null);
        if (String.valueOf(selectedFile).equals("null")) {
            return;
        } else {
            file_path.setText(selectedFile.toString());
        }
    }
    static int choice =0 ;
    static Stage stage2 ;
    static int sizeofmem= 0 ;
    int min =0 ;
    int numberofp =0 ;
    boolean pushed =false ;
    simulation s ;
    @FXML
    void run_random(ActionEvent event) throws IOException, InterruptedException {
        pushed =true ;
        Parent root = FXMLLoader.load(getClass().getResource("random.fxml"));
        stage2 = new Stage();
        stage2.setScene(new Scene(root));
        stage2.setTitle("Random Data");
        stage2.showAndWait();
    }
    static int size_in_frames ;
    static int timequant =0 ;
    @FXML
    private Button submitfile ;
    @FXML
    private TextField timequantom ;
    @FXML
    void submitfile(ActionEvent event)  {
        try {
            list.clear();
            list2.clear();
            if(file_path.getText().equals(""))
                throw new MissingFormatArgumentException("Browse a File") ;


            proc = readfile() ;
            try {
                if (!timequantom.getText().matches("[0-9]+") || timequantom.getText().equals(""))
                    throw new MissingFormatArgumentException("error in format for time quantum") ;
                timequant =Integer.parseInt(timequantom.getText()) ;
            }catch (MissingFormatArgumentException e){
                Alert alertCreat = new Alert(Alert.AlertType.ERROR);
                alertCreat.setTitle("Error");
                alertCreat.setHeaderText(null);
                alertCreat.setContentText(e.getMessage());
                alertCreat.showAndWait();
            }





            ExecutorService e = Executors.newFixedThreadPool(proc.size());
            for (procececs_random_data p : proc){
                e.execute(p);
            }


            sizeofmem =size_in_frames;
            if (choice==1){
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        s = new simulation(proc );
                        try {
                            s.round_robin();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }) ;
                t.start();
            }
            else{
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        s = new simulation(proc );
                        try {
                            s.round_robin();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }) ;
                t.start();

            }

            view_tables();

            simulation.proc_final_information.clear();
        }
        catch (MissingFormatArgumentException | IOException e){
            Alert alertCreat = new Alert(Alert.AlertType.ERROR);
            alertCreat.setTitle("Error");
            alertCreat.setHeaderText(null);
            alertCreat.setContentText(e.getMessage());
            alertCreat.showAndWait();
        }

    }
    @FXML
    private Button submit;

    @FXML
    void sumbit(ActionEvent event) throws InterruptedException {
        try {
            if (!timequantom.getText().matches("[0-9]+") || timequantom.getText().equals(""))
                throw new MissingFormatArgumentException("error in format for time quantum") ;
            timequant =Integer.parseInt(timequantom.getText()) ;
        }catch (MissingFormatArgumentException e){
            Alert alertCreat = new Alert(Alert.AlertType.ERROR);
            alertCreat.setTitle("Error");
            alertCreat.setHeaderText(null);
            alertCreat.setContentText(e.getMessage());
            alertCreat.showAndWait();
        }
        try {
            list2.clear();
            list.clear();
            if (!pushed)
                throw new MissingFormatArgumentException("please choose the data") ;

            numberofp =Random_data.numberofp ;
            sizeofmem =Random_data.sizeofmem;
            min =Random_data.min;
            inputdata inputdata = new inputdata(numberofp,min) ;

            proc = inputdata.creat_random_data();
            ExecutorService e = Executors.newFixedThreadPool(proc.size());
            for (procececs_random_data p : proc){
                e.execute(p);
            }

            Thread t ;
            if (choice==1){

                 t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        s = new simulation(proc );
                        try {
                            s.round_robin();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            view_tables();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        simulation.proc_final_information.clear();
                    }
                }) ;
                t.start();
            }
            else{
                 t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        s = new simulation(proc );
                        try {
                            s.round_robin();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            view_tables();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        simulation.proc_final_information.clear();
                    }
                }) ;
                t.start();

            }





        }
        catch (MissingFormatArgumentException e){
            Alert alertCreat = new Alert(Alert.AlertType.ERROR);
            alertCreat.setTitle("Error");
            alertCreat.setHeaderText(null);
            alertCreat.setContentText(e.getMessage());
            alertCreat.showAndWait();
        }

    }
    @FXML
    void select(ActionEvent event) throws InterruptedException {

        if (comb.getSelectionModel().getSelectedItem().toString().equals("FIFO")){
            choice =1 ;
        }
        else
           choice =2 ;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list =  FXCollections.observableArrayList("FIFO" ,"Second Chance") ;
        comb.setItems(list);
    }


    static ArrayList<procececs_random_data> readfile() throws IOException {
        lock.lock();
        ArrayList<procececs_random_data>proc = new ArrayList<>() ;
        File file = new File("C:\\Users\\Ibrah\\os_project\\src\\test.txt");


        BufferedReader br = new BufferedReader(new FileReader(file));


        String st;
        int proc_number = Integer.parseInt(br.readLine()) ;
         size_in_frames =Integer.parseInt(br.readLine()) ;
         int min_frames_per_proc = Integer.parseInt(br.readLine()) ;
        while ((st = br.readLine()) != null)
        {
            Random r = new Random();

            String[]s =st.split(" ") ;
            int pid = Integer.parseInt(s[0]) ;
            int start =Integer.parseInt(s[1]) ;
            int duration = Integer.parseInt(s[2]);
            int size= Integer.parseInt(s[3]) ;
            int mem_traces = Integer.parseInt(s[4]) ;

            ArrayList<Integer>traces = new ArrayList<>() ;
            for (int i = 0 ; i < mem_traces;i++){
                int t=r.ints(0,size).findFirst().getAsInt();
                traces.add(t);
            }

            proc.add(new procececs_random_data(pid,start,duration,size,traces,min_frames_per_proc));
        }

        lock.unlock();
        return proc;
    }

     void view_tables() throws IOException {

        list2.clear();
        list.clear();
        for (procececs_random_data p : simulation.proc_final_information){
            list.add(new tableview(p.getPid() ,p.getStrat(),p.burst_time,p.turn_around_time,p.finished_time,
                    p.wait_time,p.number_of_page_faults, p.cycles)) ;
            total_page_faults+=p.number_of_page_faults ;
            total_cycles+=p.cycles ;
            total_hits+=p.hits ;
            total_traces+=p.traces.size();


        }

         for (procececs_random_data p : simulation.proc_final_information){

             list2.add(new tableview1(p.getPid() ,p.getStrat(),p.getSize(),p.traces.size())) ;
         }
        Arival_Time.setCellValueFactory(new PropertyValueFactory<tableview,Integer>("Arival_Time"));
        P_ID.setCellValueFactory(new PropertyValueFactory<tableview,Integer>("p_Id"));
        Burst_Time.setCellValueFactory(new PropertyValueFactory<tableview,Double>("Burst_Time"));
        Waiting_Time.setCellValueFactory(new PropertyValueFactory<tableview,Double>("Wating_Time"));
        TA_Time.setCellValueFactory(new PropertyValueFactory<tableview,Double>("TA_Time"));
        Finished_Time.setCellValueFactory(new PropertyValueFactory<tableview,Double>("Finished_Time"));
        Page_Faults.setCellValueFactory(new PropertyValueFactory<tableview,Integer>("Page_Faults"));
        Cycles.setCellValueFactory(new PropertyValueFactory<tableview,Integer>("Cycles"));
         tableview.setItems(list);
        pid1.setCellValueFactory(new PropertyValueFactory<tableview1,Integer>("P_ID"));
         arive1.setCellValueFactory(new PropertyValueFactory<tableview1,Integer>("Arival_Time"));
         sizepages.setCellValueFactory(new PropertyValueFactory<tableview1,Integer>("Size_in_pages"));
         numberoftraces.setCellValueFactory(new PropertyValueFactory<tableview1,Integer>("number_of_traces"));
         tableview1.setItems(list2);




    }
    @FXML
    void memview(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("textarea.fxml"));
        Stage stage3 = new Stage();
        stage3.setScene(new Scene(root));
        stage3.setTitle("Memory View");

        stage3.showAndWait();
        procecesexccuter.mem_view.clear();
        total_hits =0 ;

        total_traces =0 ;
        total_page_faults=0;
        total_cycles =0;
    }
}