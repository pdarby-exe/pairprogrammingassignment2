// tabs=4
//************************************************************
//	COPYRIGHT 2010 Sandeep Mitra and students, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import javax.swing.JFrame;	
import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;

// project imports

/** The main frame for the Brockport Fast Trax application. All sub-panels are inside this one frame only. */
//==============================================================
public class MainFrame extends JFrame 
	implements ComponentListener
{
    // data members
	private JPanel mainPanel;	// primary panel placed in the frame
	
	private boolean sizeSet;	// record whether the size has been set, prevent resize
	
	private Dimension frameSize;	// frame size, prevents reset

	private static MainFrame myInstance = null;
	
	// class constructor
	//----------------------------------------------------------
	private MainFrame (String title)
	{
		super(title);
		sizeSet = false;
		// addComponentListener(this);
	}


	//----------------------------------------------------------
	public static MainFrame getInstance (String title)
	{
		if (myInstance == null)
		{
			myInstance = new MainFrame(title);
		}

		return myInstance; 
	}

	//----------------------------------------------------------
	public static JFrame getInstance ()
	{
		if (myInstance == null)
		{
			myInstance = new MainFrame("");
		}

		return myInstance; 
	}
	
	/**
	 * Prevents resize on frame
	 *
	 * @param	e	The Component Event generated by the resize request
	 */
	//--------------------------------------------------------------
	public void componentResized(ComponentEvent e) 
	{
		if (sizeSet == false) 
		{
			sizeSet = true; // allow first one in (from pack call), and save size
			frameSize = getSize();
		}
		else 
		{
			// user tried to resize, set it back
			setSize(frameSize);
			pack();
			validate();
		}
    }

	/**  Part of the Component listener interface (don't care) */
	//--------------------------------------------------------------
	public void componentHidden(ComponentEvent e) 
	{
		// dont care about this one
	}

	/**  Part of the Component listener interface (don't care) */	
	//--------------------------------------------------------------
	public void componentMoved(ComponentEvent e) 
	{
		// dont care about this one
	}
		
	/**  Part of the Component listener interface (don't care) */	
	//--------------------------------------------------------------
	public void componentShown(ComponentEvent e) 
	{
		// dont care about this one
	}
	
	
}




