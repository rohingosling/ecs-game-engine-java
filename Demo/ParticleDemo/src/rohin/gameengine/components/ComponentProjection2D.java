// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentProjection2D
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds 2D projection properties, including window bounds, viewport bounds, origin, scale,
//   aspect ratio, and layer depth.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentProjection2D extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double[] window   = { 0.0, 0.0, 0.0, 0.0 };
    public double[] viewPort = { 0.0, 0.0, 0.0, 0.0 };
    public Vector2D origin   = new Vector2D ( 1.0, 1.0 );
    public Vector2D scale    = new Vector2D ( 1.0, -1.0 );
    public Vector2D aspect   = new Vector2D ( 1.0, 1.0 );
    public double   layer    = 0.0;


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

    public ComponentProjection2D ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentProjection2D ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.window   = new double[] { 0.0, 0.0, 0.0, 0.0 };
        this.viewPort = new double[] { 0.0, 0.0, 0.0, 0.0 };
        this.origin   = new Vector2D ( 1.0, 1.0 );
        this.scale    = new Vector2D ( 1.0, -1.0 );
        this.aspect   = new Vector2D ( 1.0, 1.0 );
        this.layer    = 0.0;
    }
}
