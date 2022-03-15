package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.MissingFormatArgumentException;


public class Random_data {
     static int numberofp ;
      static int sizeofmem ;
      static int min ;

    @FXML
    private TextField timequant;
    @FXML
    private TextField framesinmem;

    @FXML
    private TextField minimumframesperproc;

    @FXML
    private TextField numberofproc;
    @FXML
    private Button ok;


    public Random_data() {
    }
    @FXML
    void ok(ActionEvent event) {
        try{
        if (numberofproc.getText().equals("") || minimumframesperproc.getText().equals("") || framesinmem.getText().equals("")
               )
            throw new MissingFormatArgumentException("you should fill the requirements") ;
        else if (!check(minimumframesperproc.getText()) || !check(numberofproc.getText())|| !check(framesinmem.getText()))
            throw new MissingFormatArgumentException("the data should be digits only") ;
        else if (Integer.parseInt(minimumframesperproc.getText()) > Integer.parseInt(framesinmem.getText()))
            throw new MissingFormatArgumentException("the minimum frame per processes should be less than the memory size") ;
        else {
            numberofp = Integer.parseInt(numberofproc.getText().trim()) ;
            min =Integer.parseInt(minimumframesperproc.getText().trim()) ;
            sizeofmem = Integer.parseInt(framesinmem.getText().trim()) ;

            Controller.stage2.close();
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

   boolean check(String s){
        if (s.matches("[0-9]+"))
            return true ;
        else
            return false ;
   }
}
