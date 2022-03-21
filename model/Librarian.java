// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/** The class containing the Librarian  for the Library System application */
//==============================================================
public class Librarian implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    //private AccountHolder myAccountHolder;

    private BookCollection myBooks;
    private PatronCollection myPatrons;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage	  	myStage;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Librarian()
    {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Librarian");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowLibrarianView();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();

        dependencies.setProperty("bookData", "TransactionErrorMessage");
        dependencies.setProperty("patronData", "TransactionErrorMessage");

        myRegistry.setDependencies(dependencies);

    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param	key	Name of database column (field) for which the client wants the value
     *
     * @return	Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {

        if(key.equals("PatronList") == true){
            return myPatrons;
        }
        if(key.equals("BookList") == true){
            return myBooks;
        }
        else
            if (key.equals("TransactionErrorMessage")) {
                return transactionErrorMessage;
            }
        else
            return "";
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);

        if (key.equals("InsertBook") == true)
        {

            createAndShowBookView();
        }
        else
        if (key.equals("insertPatron") == true)
        {

            createAndShowPatronView();

        }
        else
        if (key.equals("BookSearchView") == true)
        {
            createAndShowBookSearchView();

        }
        else
        if (key.equals("BookCollectionView") == true)
        {
            Properties p = (Properties)value;
            String titl = p.getProperty("bookTitle");
            myBooks = new BookCollection();
            myBooks.findBooksWithTitleLike(titl);
            createAndShowBookCollectionView();
        }
        else
        if (key.equals("PatronSearchView") == true)
        {
            createAndShowPatronSearchView();

        }
        else
        if (key.equals("PatronCollectionView") == true)
        {
            Properties p = (Properties)value;
            String zipCode = p.getProperty("zip");
            myPatrons = new PatronCollection();
            myPatrons.findPatronsAtZipCode(zipCode);
            createAndShowPatronCollectionView();

        }
        else
        if (key.equals("bookData") == true)
        {
            Properties props = (Properties) value;

            Book b = new Book(props);

            b.save();

            transactionErrorMessage = "The Book was successfully saved to the Database";

            // createAndShowLibrarianView();
        }
        else
        if (key.equals("patronData") == true) {
            Properties props = (Properties) value;

            Patron p = new Patron(props);

            p.save();

            transactionErrorMessage = "The Patron was succesfully saved to the Database";

           // createAndShowLibrarianView();
        }
        else
        if (key.equals("Done") == true)
        {
            createAndShowLibrarianView();

        }
        else
        if (key.equals("back") == true)
        {
            createAndShowLibrarianView();

        }

            myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }


    //------------------------------------------------------------
    private void createAndShowLibrarianView()
    {
        Scene currentScene = (Scene)myViews.get("LibrarianView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowBookView()
    {
        Scene currentScene = (Scene)myViews.get("BookView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("BookView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("BookView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowPatronView()
    {
        Scene currentScene = (Scene)myViews.get("PatronView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("PatronView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("PatronView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowBookSearchView()
    {
        Scene currentScene = (Scene)myViews.get("BookSearchView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("BookSearchView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("BookSearchView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowBookCollectionView()
    {

            // create our initial view
            View newView = ViewFactory.createView("BookCollectionView", this); // USE VIEW FACTORY
            Scene currentScene = new Scene(newView);



        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowPatronSearchView()
    {
        // create our initial view
        View newView = ViewFactory.createView("PatronCollectionView", this); // USE VIEW FACTORY
        Scene currentScene = new Scene(newView);

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowPatronCollectionView()
    {
        Scene currentScene = (Scene)myViews.get("PatronCollectionView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("PatronCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("PatronCollectionView", currentScene);
        }

        swapToView(currentScene);

    }


    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }



    //-----------------------------------------------------------------------------
    public void swapToView(Scene newScene)
    {


        if (newScene == null)
        {
            System.out.println("Teller.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }

}


