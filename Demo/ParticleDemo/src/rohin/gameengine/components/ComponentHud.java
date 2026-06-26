// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentHud
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds HUD (heads-up display) properties, including visibility, text content, font
//   settings, colors, and screen position.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentHud extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean  visible         = false;
    public String   text            = "";
    public String   font            = "Courier New";
    public int      fontSize        = 14;
    public int[]    color           = { 0, 255, 0 };
    public int[]    backgroundColor = { 0, 0, 0 };
    public Vector2D position        = new Vector2D ( 10.0, 10.0 );


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

    public ComponentHud ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentHud ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.visible         = false;
        this.text            = "";
        this.font            = "Courier New";
        this.fontSize        = 14;
        this.color           = new int[] { 0, 255, 0 };
        this.backgroundColor = new int[] { 0, 0, 0 };
        this.position        = new Vector2D ( 10.0, 10.0 );
    }
}
