// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentResourceBackgroundImage
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds the resource path, scale, and position for a background image.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentResourceBackgroundImage extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // @formatter:off
    public String   imagePath;
    public double   scale;
    public Vector2D position;
    // @formatter:on


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

    public ComponentResourceBackgroundImage ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentResourceBackgroundImage ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        // @formatter:off
        this.imagePath = "";
        this.scale     = 1.0;
        this.position  = new Vector2D ( 0.0, 0.0 );
        // @formatter:on
    }
}
