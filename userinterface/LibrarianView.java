// specify the package
package userinterface;

// system imports

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import impresario.IModel;

/** The class containing the Teller View  for the ATM application */
//==============================================================
public class LibrarianView extends View
{

    private Button insertBookButton;
    private Button insertPatronButton;
    private Button searchBooksButton;
    private Button searchPatronsButton;
    private Button doneButton;



    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibrarianView( IModel librarian)
    {

        super(librarian, "LibrarianView");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        getChildren().add(container);

    }

    // Create the label (Text) for the title of the screen
    //-------------------------------------------------------------
    private Node createTitle()
    {

        Text titleText = new Text("       Brockport Library System          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);


        return titleText;
    }

    // Create the main form contents
    //-------------------------------------------------------------
    private GridPane createFormContents()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // button Fields

        //Insert new book button -------------------------------------
        insertBookButton = new Button("Insert New Book");
        insertBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // Add event stuff here
                myModel.stateChangeRequest("InsertBook", null);
            }
        });

        grid.add(insertBookButton, 0, 0);

        //insert new patron button ----------------------------------
        insertPatronButton = new Button("Insert New Patron");
        insertPatronButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("insertPatron", null);
            }
        });

        grid.add(insertPatronButton, 0, 1);

        //insert new patron button ----------------------------------
        searchBooksButton = new Button("Search Books");
        searchBooksButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("BookSearchView", null);
            }
        });

        grid.add(searchBooksButton, 0, 2);

        ///insert new patron button ----------------------------------
        searchPatronsButton = new Button("Search Patrons");
        searchPatronsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("PatronSearchView", null);
            }
        });

        grid.add(searchPatronsButton, 0, 3);



        doneButton = new Button("Done");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // Add event stuff here
            }
        });

        grid.add(doneButton, 0, 4);



        return grid;
    }



    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        /*
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
        }
         */
    }



}


