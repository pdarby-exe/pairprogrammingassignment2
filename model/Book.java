// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.*;
import database.*;

import impresario.*;

import userinterface.*;
import userinterface.*;

/** The class containing the Book for the Library application */
//==============================================================
public class Book extends EntityBase implements IView
{
    private static final String myTableName = "Book";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Book(String bookId)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (bookID = " + bookId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one Book at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple books matching id : "
                        + bookId + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no book found for this bookID, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No book matching id : "
                    + bookId + " found.");
        }
    }

    //----------------------------------------------------------
    public Book(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Update", "UpdateStatusMessage");
        dependencies.setProperty("ServiceCharge", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        if (key.equals("DisplayView") == true)
        {
            createAndShowView();
        }
        else
        if (key.equals("Update") == true)
        {
            updateStateInDatabase();
        }
        else
        {
            persistentState.setProperty(key, (String)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    /** save method */
    //----------------------------------------------------------
    public void save()
    {
        updateStateInDatabase();
    }


    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase() // should be private? Should this be invoked directly or via the 'sCR(...)' method always?
    {
        try
        {
            if (persistentState.getProperty("bookID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("bookID",
                        persistentState.getProperty("bookID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "book data for bookID : " + persistentState.getProperty("bookID") + " updated successfully in database!";
            }
            else
            {
                System.out.println("b2");
                Integer bookID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bookID", "" + bookID.intValue());
                updateStatusMessage = "Book data for new book : " +  persistentState.getProperty("bookID")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing book data in database!";
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        //DEBUG
        System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    //------------------------------------------------------
    protected void createAndShowView()
    {
/*
        View localView = (View)myViews.get("AccountView");

        if (localView == null)
        {
            // create our initial view
            localView = ViewFactory.createView("AccountView", this);

            // NEEDED FOR IMPRESARIO
            localView.subscribe("ServiceCharge", this);
            localView.subscribe("AccountCancelled", this);

            myViews.put("AccountView", localView);

            // make the view visible by installing it into the frame
            swapToView(localView);
        }
        else
        {
            // make the view visible by installing it into the frame
            swapToView(localView);
        } */
    }

    /**
     * This method is needed solely to enable the Account information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector getEntryListView()
    {
        Vector v = new Vector();

        v.addElement(persistentState.getProperty("bookId"));
        v.addElement(persistentState.getProperty("bookTitle"));
        v.addElement(persistentState.getProperty("author"));
        v.addElement(persistentState.getProperty("pubYear"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

    public String toString(){

        String s = new String("bookId = " + persistentState.getProperty("bookId") +
                "\nbookTitle = " + persistentState.getProperty("bookTitle") +
                "\nauthor = " + persistentState.getProperty("author") +
                "\npubYear = " + persistentState.getProperty("pubYear") +
                "\nstatus = " + persistentState.getProperty("status"));

        return s;
    }

    public void display(){
        System.out.println(toString());
    }

    //-----------------------------------------------------------------------------------
    public static int compare(Book a, Book b)
    {
        String aNum = (String)a.getState("bookId");
        String bNum = (String)b.getState("bookId");

        return aNum.compareTo(bNum);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    @Override
    public void swapToView(IView viewName) {

    }
}