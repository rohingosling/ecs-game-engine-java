// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentWorld
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds world simulation properties, including particle counts per color group,
//   gravitational and repulsive constants, and global state flags.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;

public class ComponentWorld extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int     particleCountRed      = 6;
    public int     particleCountGreen    = 3;
    public int     particleCountBlue     = 3;
    public int     particleCountYellow   = 9;
    public double  gravitationalConstant = 1.0;
    public double  repulsiveConstant     = 500.0;
    public boolean paused                = false;
    public boolean trailsVisible         = true;
    public boolean gravityEnabled        = true;
    public boolean repulsionEnabled      = true;
    public boolean frictionEnabled       = true;
    public boolean elasticityEnabled     = true;


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

    public ComponentWorld ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentWorld ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.particleCountRed      = 6;
        this.particleCountGreen    = 3;
        this.particleCountBlue     = 3;
        this.particleCountYellow   = 9;
        this.gravitationalConstant = 1.0;
        this.repulsiveConstant     = 500.0;
        this.paused                = false;
        this.trailsVisible         = true;
        this.gravityEnabled        = true;
        this.repulsionEnabled      = true;
        this.frictionEnabled       = true;
        this.elasticityEnabled     = true;
    }
}
