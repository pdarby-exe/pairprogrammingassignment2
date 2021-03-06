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
public class BookView extends View
{

    // GUI components
    protected TextField bookId;
    protected TextField bookTitle;
    protected TextField author;
    protected TextField pubYear;
    protected TextField status;

    protected Button backButton;
    protected Button doneButton;
    protected ComboBox statusBox;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookView(IModel Book)
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

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        /*
        Text accNumLabel = new Text(" Book ID : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        accNumLabel.setFont(myFont);
        accNumLabel.setWrappingWidth(150);
        accNumLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(accNumLabel, 0, 1);
         */
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        bookId = new TextField();
        bookId.setEditable(true);
        grid.add(bookId, 1, 1);

        Text bookTitleLabel = new Text(" Book Title : ");
        bookTitleLabel.setFont(myFont);
        bookTitleLabel.setWrappingWidth(150);
        bookTitleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bookTitleLabel, 0, 1);

        bookTitle = new TextField();
        bookTitle.setEditable(true);
        grid.add(bookTitle, 1, 1);

        Text authorLabel = new Text(" Author : ");
        authorLabel.setFont(myFont);
        authorLabel.setWrappingWidth(150);
        authorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(authorLabel, 0, 2);

        author = new TextField();
        author.setEditable(true);
        grid.add(author, 1, 2);


        Text pubYearLabel = new Text(" Publication Year : ");
        pubYearLabel.setFont(myFont);
        pubYearLabel.setWrappingWidth(150);
        pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubYearLabel, 0, 3);

        pubYear = new TextField();
        pubYear.setEditable(true);
        grid.add(pubYear, 1, 3);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 4);

        statusBox = new ComboBox();
        statusBox.getItems().addAll("Active", "Inactive");
        statusBox.getSelectionModel().selectFirst();

        grid.add(statusBox, 1, 4);

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

        doneButton = new Button("Submit");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                Properties p = new Properties();

                p.put("bookTitle", bookTitle.getText());
                p.put("author", author.getText());
                p.put("pubYear", pubYear.getText());
                p.put("status", (String) statusBox.getValue());
                clearText();
                myModel.stateChangeRequest("bookData", p);
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
        bookId.clear();
        bookTitle.clear();
        pubYear.clear();
        author.clear();
        statusBox.valueProperty().set("Active");
    }

}

//---------------------------------------------------------------
//	Revision History:
//


