// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class PatronView extends View
{

    // GUI components
    protected TextField patronId;
    protected TextField name;
    protected TextField address;
    protected TextField city;
    protected TextField stateCode;
    protected TextField zip;
    protected TextField email;
    protected TextField dateOfBirth;

    protected ComboBox statusBox;

    protected Button doneButton;
    protected Button backButton;
    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public PatronView(IModel Book)
    {
        super(Book, "BookView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionErrorMessage", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Library System");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("PATRON INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        /*
        Text patronIdLabel = new Text(" Patron ID : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        patronIdLabel.setFont(myFont);
        patronIdLabel.setWrappingWidth(150);
        patronIdLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronIdLabel, 0, 1);

        patronId = new TextField();
        patronId.setEditable(false);
        grid.add(patronId, 1, 1);
         */

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Text nameLabel = new Text(" Name : ");
        nameLabel.setFont(myFont);
        nameLabel.setWrappingWidth(150);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(nameLabel, 0, 2);

        name = new TextField();
        name.setEditable(true);
        grid.add(name, 1, 2);

        Text addressLabel = new Text(" Address : ");
        addressLabel.setFont(myFont);
        addressLabel.setWrappingWidth(150);
        addressLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(addressLabel, 0, 3);

        address = new TextField();
        address.setEditable(true);
        grid.add(address, 1, 3);


        Text cityLabel = new Text(" City : ");
        cityLabel.setFont(myFont);
        cityLabel.setWrappingWidth(150);
        cityLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(cityLabel, 0, 4);

        city = new TextField();
        city.setEditable(true);
        grid.add(city, 1, 4);

        Text stateCodeLabel = new Text(" State Code : ");
        stateCodeLabel.setFont(myFont);
        stateCodeLabel.setWrappingWidth(150);
        stateCodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(stateCodeLabel, 0, 5);

        stateCode = new TextField();
        stateCode.setEditable(true);
        grid.add(stateCode, 1, 5);

        Text zipLabel = new Text(" Zip Code : ");
        zipLabel.setFont(myFont);
        zipLabel.setWrappingWidth(150);
        zipLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(zipLabel, 0, 6);

        zip = new TextField();
        zip.setEditable(true);
        grid.add(zip, 1, 6);

        Text emailLabel = new Text(" Email : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 7);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 7);

        Text dateOfBirthLabel = new Text(" Date of Birth : ");
        dateOfBirthLabel.setFont(myFont);
        dateOfBirthLabel.setWrappingWidth(150);
        dateOfBirthLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateOfBirthLabel, 0, 8);

        dateOfBirth = new TextField();
        dateOfBirth.setEditable(true);
        grid.add(dateOfBirth, 1, 8);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 9);

        statusBox = new ComboBox();
        statusBox.getItems().addAll("Active", "Inactive");
        statusBox.getSelectionModel().selectFirst();

        grid.add(statusBox, 1, 9);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);

        backButton = new Button("back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("back", null);
            }
        });
        doneCont.getChildren().add(backButton);

        doneButton = new Button("Done");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                Properties p = new Properties();

                p.put("name", name.getText());
                p.put("address", address.getText());
                p.put("city", city.getText());
                p.put("stateCode", stateCode.getText());
                p.put("zip", zip.getText());
                p.put("email", email.getText());
                p.put("dateOfBirth", dateOfBirth.getText());
                p.put("status", (String) statusBox.getValue());

                clearText();
                myModel.stateChangeRequest("patronData", p);
            }
        });
        doneCont.getChildren().add(doneButton);


        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);


        return vbox;
    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        //accountNumber.setText((String)myModel.getState("AccountNumber"));
        //acctType.setText((String)myModel.getState("Type"));
        //balance.setText((String)myModel.getState("Balance"));
        //serviceCharge.setText((String)myModel.getState("ServiceCharge"));
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("TransactionErrorMessage") == true)
        {
            String val = (String)value;
            //serviceCharge.setText(val);
            if (val.startsWith("ERR"))
                displayErrorMessage(val);
            else
                displayMessage(val);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    /**
     * Clear text
     */
    //----------------------------------------------------------
    public void clearText()
    {
        name.clear();
        address.clear();
        city.clear();
        stateCode.clear();
        zip.clear();
        email.clear();
        dateOfBirth.clear();
        statusBox.valueProperty().set("Active");
    }

}

//---------------------------------------------------------------
//	Revision History:
//
