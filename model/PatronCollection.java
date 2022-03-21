// specify the package
package model;

// system imports
import impresario.IView;

import java.util.Properties;
import java.util.Vector;

// project imports


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class PatronCollection  extends EntityBase
{
    private static final String myTableName = "Patron";

    private Vector patronList;

    // GUI Components

    // Blank constructor for this class
    //----------------------------------------------------------
    public PatronCollection()
    {
        super(myTableName);

        patronList = new Vector<Patron>();

    }

    private void queryHelper(String query, String message) {

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextAccountData = (Properties)allDataRetrieved.elementAt(cnt);

                Patron patron = new Patron(nextAccountData);

                if (patron != null)
                {
                    patronList.addElement(patron);
                }
            }

        }
        else
        {
            System.out.println(message);
        }
    }

    public void findPatronsOlderThan(String date){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < " + date + ")";

        queryHelper(query, "There are no Patrons born before " + date + ".");


    }

    public void findPatronsYoungerThan(String date){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < " + date + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        queryHelper(query, "There are no Patrons born before " + date + ".");

    }

    public void findPatronsAtZipCode(String zip){
        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (zip = " + zip + ")";

        queryHelper(query, "There are no Patrons who live in " + zip + ".");
    }

    public void findPatronsWithNameLike(String name){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (name like '%" + name + "%')";

        queryHelper(query, "There are no Patrons who have a name that contains " + name + ".");
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("patrons"))
            return patronList;
        else
        if (key.equals("patronList"))
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

        String s = ("The Patron Collection contains: ");

        for (Object b : patronList) {
            s.concat(b + " ");
        }

        return s;
    }

     */

    public void display() {
        for (Object b : patronList) {
            System.out.println(b.toString() + "\n------------------\n");
        }
    }

    @Override
    public void swapToView(IView viewName) {

    }
}