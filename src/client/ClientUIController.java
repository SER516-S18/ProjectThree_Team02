package client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientUIController implements Initializable {

    private ClientNetworkService<Void> networkThread;
    @FXML AnchorPane rootPane;
    @FXML private TextField systemStatus;
    @FXML private TextField systemUpTime;
    @FXML private ChoiceBox<String> headSet = new ChoiceBox<>();
    @FXML private ChoiceBox<String> profile = new ChoiceBox<>();
    @FXML private TextField WirelessSignal;
    @FXML private TextField BatteryPower;
    @FXML private TextField inputName;
    @FXML Button AddName= new Button();
    @FXML Button SaveName;
    @FXML MenuItem loadSecondB ;
    @FXML MenuItem close;
    @FXML Slider Blink;
    @FXML Slider RightWink;
    @FXML Slider LeftWink;
    @FXML Slider Look;
    @FXML Slider Raise;
    @FXML Slider Furrow;
    @FXML Slider Smile;
    @FXML Slider Clench;
    @FXML Slider RightSmirk;
    @FXML Slider LeftSmirk;
    @FXML Slider Laugh;
    @FXML RadioButton BlinkB;
    @FXML RadioButton RightWinkB;
    @FXML RadioButton LeftWinkB;
    @FXML RadioButton LookB;
    @FXML RadioButton RaiseB;
    @FXML RadioButton FurrowB;
    @FXML RadioButton SmileB;
    @FXML RadioButton ClenchB;
    @FXML RadioButton RightSmirkB;
    @FXML RadioButton LeftSmirkB;
    @FXML RadioButton LaughB;





    @FXML private TextField Status;
    ObservableList<String> availableChoicesForHeadset = FXCollections.observableArrayList("0","1","2");
    ObservableList<String> availableChoicesForProfile = FXCollections.observableArrayList("jw","Sanay","Dough","Mellisa","Yuan");
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        networkThread = new ClientNetworkService<>();
        networkThread.restart();
        systemStatus.setText("Emotive Engine Is Ready");
        systemUpTime.setText("442.298");
        headSet.setItems(availableChoicesForHeadset);
        headSet.getSelectionModel().selectFirst();
        profile.setItems(availableChoicesForProfile);
        profile.getSelectionModel().selectFirst();
        Status.setText("OK");

    }
    @FXML private void changeToAffectiveScene(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ClientAffective.fxml"));
        rootPane.getChildren().setAll(pane);

    }
   @FXML private  void closeApplication()
   {
       Platform.exit();
   }
   @FXML private void addName()
   {
     inputName.setVisible(true);

   }
   @FXML private void saveName()
   {
       String Name = inputName.getText();
       availableChoicesForProfile.add(Name);
       inputName.setText("");
       inputName.setVisible(false);
   }
   //Handling Sliders
   @FXML private void BlinkBSelected(ActionEvent event)
   {
       boolean isSelected = BlinkB.isSelected();
       if(isSelected == true) {
           Blink.setVisible(true);
       }
       else
       {
           Blink.setVisible(false);
       }
   }
    @FXML private void RightWinkBSelected(ActionEvent event)
    {
        boolean isSelected = RightWinkB.isSelected();
        if(isSelected==true) {
            RightWink.setVisible(true);
        }
        else
        {
            RightWink.setVisible(false);
        }
    }
    @FXML private void LeftWinkSelected(ActionEvent event)
    {
        boolean isSelected = LeftWinkB.isSelected();
        if(isSelected==true) {
            LeftWink.setVisible(true);
        }
        else
        {
            LeftWink.setVisible(false);
        }
    }
    @FXML private void LookSelected(ActionEvent event)
    {
        boolean isSelected = LookB.isSelected();
        if(isSelected==true) {
            Look.setVisible(true);
        }
        else
        {
            Look.setVisible(false);
        }
    }
    @FXML private void RaiseSelected(ActionEvent event)
    {
        boolean isSelected = RaiseB.isSelected();
        if(isSelected==true) {
            Raise.setVisible(true);
        }
        else
        {
            Raise.setVisible(false);
        }
    }
    @FXML private void FurrowSelected(ActionEvent event)
    {
        boolean isSelected = FurrowB.isSelected();
        if(isSelected==true) {
            Furrow.setVisible(true);
        }
        else
        {
            Furrow.setVisible(false);
        }
    }@FXML private void SmileSelected(ActionEvent event)
    {
        boolean isSelected = SmileB.isSelected();
        if(isSelected==true) {
            Smile.setVisible(true);
        }
        else
        {
            Smile.setVisible(false);
        }
    }
    @FXML private void ClenchSelected(ActionEvent event)
    {
        boolean isSelected = ClenchB.isSelected();
        if(isSelected==true) {
            Clench.setVisible(true);
        }
        else
        {
            Clench.setVisible(false);
        }
    }
    @FXML private void RightSmirkSelected(ActionEvent event)
    {
        boolean isSelected = RightSmirkB.isSelected();
        if(isSelected==true) {
            RightSmirk.setVisible(true);
        }
        else
        {
            RightSmirk.setVisible(false);
        }
    }
    @FXML private void LeftSmirkSelected(ActionEvent event)
    {
        boolean isSelected = LeftSmirkB.isSelected();
        if(isSelected==true) {
            LeftSmirk.setVisible(true);
        }
        else
        {
            LeftSmirk.setVisible(false);
        }
    }
    @FXML private void LaughSelected(ActionEvent event)
    {
        boolean isSelected = LaughB.isSelected();
        if(isSelected==true) {
            Laugh.setVisible(true);
        }
        else
        {
            Laugh.setVisible(false);
        }
    }





}
