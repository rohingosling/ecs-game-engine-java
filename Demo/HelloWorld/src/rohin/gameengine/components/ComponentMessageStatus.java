//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds a boolean flag used to confirm that a message has only been written to the terminal
//   once.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;

//*********************************************************************************************************************
// Class: ComponentMessageStatus
//
// Description:
//
//   ECS data component that tracks whether a message has already been printed to the terminal. This component
//   extends ECSComponent and holds a single boolean flag, printed, which systems can check to ensure a message
//   is only output once during the game loop.
//
//*********************************************************************************************************************

public class ComponentMessageStatus extends ECSComponent
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    public Boolean printed;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/2: ComponentMessageStatus
    //
    // Description:
    //
    //   Default constructor for use as a type token in hasComponents(). Delegates to the parent ECSComponent
    //   default constructor and initializes the printed flag to false.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ComponentMessageStatus ()
    {
        super ();
        initialize ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 2/2: ComponentMessageStatus
    //
    // Description:
    //
    //   Parameterized constructor that creates a fully identified component with the given id, name, and owning
    //   engine reference. Delegates to the parent ECSComponent constructor and initializes the printed flag to
    //   false.
    //
    // Arguments:
    //
    //   id (Integer):
    //     The unique identifier for this component instance.
    //
    //   name (String):
    //     The display name for this component.
    //
    //   owner (ECSEngine):
    //     The ECS engine instance that owns this component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ComponentMessageStatus ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Private initialization helper called by all constructors. Sets the printed flag to false, indicating that
    //   the message has not yet been written to the terminal.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.printed = false;
    }
}
