// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentResourceShadow
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds the resource path, offset, and opacity for a shadow image.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentResourceShadow extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // @formatter:off
    public String   imagePath;
    public Vector2D offset;
    public double   opacity;
    public double   scale;
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

    public ComponentResourceShadow ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentResourceShadow ( Integer id, String name, ECSEngine owner )
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
        this.offset    = new Vector2D ( 10.0, 10.0 );
        this.opacity   = 0.8;
        this.scale     = 1.0;
        // @formatter:on
    }
}
