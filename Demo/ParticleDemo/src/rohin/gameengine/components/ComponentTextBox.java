// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentTextBox
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds text box properties, including text content, font settings, alignment, color,
//   position, and dimensions.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentTextBox extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String   text        = "";
    public String   font        = "Courier New";
    public int      fontSize    = 20;
    public int      align       = 1;
    public int[]    color       = { 255, 255, 255 };
    public double   textOpacity = 1.0;
    public Vector2D position    = new Vector2D ( 0.0, 0.0 );
    public int      width       = 800;
    public int      height      = 600;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    //
    // Default constructor for use as a type token in hasComponents().
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentTextBox ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentTextBox ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.text        = "";
        this.font        = "Courier New";
        this.fontSize    = 20;
        this.align       = 1;
        this.color       = new int[] { 255, 255, 255 };
        this.textOpacity = 1.0;
        this.position    = new Vector2D ( 0.0, 0.0 );
        this.width       = 800;
        this.height      = 600;
    }
}
