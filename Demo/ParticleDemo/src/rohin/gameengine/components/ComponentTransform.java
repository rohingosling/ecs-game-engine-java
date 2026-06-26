// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentTransform
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds transform properties, including origin, scale, rotation, and translation.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;
import rohin.gameengine.Vector3D;

public class ComponentTransform extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Vector2D origin      = new Vector2D ( 0.0, 0.0 );
    public Vector2D scale       = new Vector2D ( 1.0, 1.0 );
    public Vector3D rotation    = new Vector3D ( 0.0, 0.0, 0.0 );
    public Vector2D translation = new Vector2D ( 0.0, 0.0 );


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

    public ComponentTransform ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentTransform ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.origin      = new Vector2D ( 0.0, 0.0 );
        this.scale       = new Vector2D ( 1.0, 1.0 );
        this.rotation    = new Vector3D ( 0.0, 0.0, 0.0 );
        this.translation = new Vector2D ( 0.0, 0.0 );
    }
}
