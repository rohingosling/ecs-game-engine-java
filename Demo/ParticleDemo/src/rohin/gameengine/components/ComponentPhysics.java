// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentPhysics
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds physics properties, including mass, velocity, force accumulator, elasticity,
//   and friction.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentPhysics extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double   mass                   = 1.0;
    public Vector2D velocity               = new Vector2D ( 0.0, 0.0 );
    public Vector2D forceAccumulator       = new Vector2D ( 0.0, 0.0 );
    public double   elasticityCoefficient  = 0.9;
    public double   frictionCoefficient    = 0.995;


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

    public ComponentPhysics ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentPhysics ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.mass                  = 1.0;
        this.velocity              = new Vector2D ( 0.0, 0.0 );
        this.forceAccumulator      = new Vector2D ( 0.0, 0.0 );
        this.elasticityCoefficient = 0.9;
        this.frictionCoefficient   = 0.995;
    }
}
