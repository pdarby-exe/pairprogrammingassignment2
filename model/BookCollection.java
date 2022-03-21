// specify the package
package model;

// system imports
import impresario.IView;

import java.util.Properties;
import java.util.Vector;

// project imports


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class BookCollection  extends EntityBase
{
    private static final String myTableName = "Book";

    private Vector bookList;
    // GUI Components

    // Blank constructor for this class
    //----------------------------------------------------------
    public BookCollection()
    {
        super(myTableName);

        bookList = new Vector<Book>();

    }

    private void queryHelper(String query, String message) {

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextAccountData = (Properties)allDataRetrieved.elementAt(cnt);

                Book book = new Book(nextAccountData);

                if (book != null)
                {
                    bookList.addElement(book);
                }
            }

        }
        else
        {
            System.out.println(message);
        }
    }

    public void findBooksOlderThanDate(String year){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear < " + year + ")";

        queryHelper(query, "There are no Books older than " + year + ".");

    }

    public void findBooksNewerThanDate(String year){
        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > " + year + ")";


        queryHelper(query, "There are no Books newer than " + year + ".");
    }

    public void findBooksWithTitleLike(String title){
        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (bookTitle like '%" + title + "%')";


        queryHelper(query, "There are no Books with title containing the word " + title + ".");
    }

    public void findBooksWithAuthorLike(String author){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (author like '%" + author + "%')";

        queryHelper(query, "There are no Books with an author's name containing the word " + author + ".");
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("books"))
            return bookList;
        else
        if (key.equals("bookList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // Class is invariant, so this method does not change any attributes
        // It does handle the request to display its view (if Impose Service Charge -3 is opted for)
        //if (key.equals("DisplayView") == true)
        //{
        //	createAndShowView();
        //}
        //else
        //if (key.equals("AccountSelected") == true
        //{
        //	String accountNumber = (String)value;
        //  Account acct = retrieve(accountNumber);
        //  acct.subscribe("AccountCancelled", this);
        //  acct.stateChangeRequest("DisplayView", this);
        //}
        //else
        //if (key.equals("AccountCancelled") == true)
        //{
        //	createAndShowView();
        //}

        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    /*
    public String toString(){


        String s = ("The Book Collection contains: ");
        for (Object b : bookList) {
            s.concat(b.toString() + "\n------------------\n");
        }

        return s;
    }

     */

    public void display(){
        for (Object b : bookList) {
            System.out.println(b.toString() + "\n------------------\n");
        }
    }

    @Override
    public void swapToView(IView viewName) {

    }
}