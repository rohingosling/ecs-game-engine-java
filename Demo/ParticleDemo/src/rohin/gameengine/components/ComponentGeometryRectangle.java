// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentGeometryRectangle
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds the geometry properties for a rectangle, including dimensions, origin, color, and
//   visibility flags.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentGeometryRectangle extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double   width     = 1.0;
    public double   height    = 1.0;
    public Vector2D origin    = new Vector2D ( 0.0, 0.0 );
    public int[]    color     = { 255, 255, 255 };
    public boolean  crosshair = true;
    public boolean  visible   = false;


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

    public ComponentGeometryRectangle ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentGeometryRectangle ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.width     = 1.0;
        this.height    = 1.0;
        this.origin    = new Vector2D ( 0.0, 0.0 );
        this.color     = new int[] { 255, 255, 255 };
        this.crosshair = true;
        this.visible   = false;
    }
}
