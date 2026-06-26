//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   ECS system that handles terminal output for the Hello World demo.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.systems;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentMessageStatus;
import rohin.gameengine.components.ComponentText;

//*********************************************************************************************************************
// Class: SystemTerminal
//
// Description:
//
//   ECS system responsible for printing messages to the terminal. On each update cycle, iterates through all
//   entities that have both a ComponentText and a ComponentMessageStatus. Prints the message text once, then
//   displays a prompt instructing the user to press [Enter] to exit.
//
//*********************************************************************************************************************

public class SystemTerminal extends ECSSystem
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private Boolean promptPrinted;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/2: SystemTerminal
    //
    // Description:
    //
    //   Default constructor. Delegates to the parent ECSSystem default constructor and initializes the
    //   promptPrinted flag to false.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public SystemTerminal ()
    {
        super ();
        initialize ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 2/2: SystemTerminal
    //
    // Description:
    //
    //   Parameterized constructor that creates a fully identified system with the given id, name, and owning
    //   engine reference. Delegates to the parent ECSSystem constructor and initializes the promptPrinted flag
    //   to false.
    //
    // Arguments:
    //
    //   id (Integer):
    //     The unique identifier for this system instance.
    //
    //   name (String):
    //     The display name for this system.
    //
    //   owner (ECSEngine):
    //     The ECS engine instance that owns this system.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public SystemTerminal ( Integer id, String name, ECSEngine owner )
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
    //   Private initialization helper called by all constructors. Sets the promptPrinted flag to false, indicating
    //   that the exit prompt has not yet been displayed.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.promptPrinted = false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: update
    //
    // Description:
    //
    //   Updates game state on each engine tick. Iterates through all entities that contain both a ComponentText
    //   and a ComponentMessageStatus. If the message has not yet been printed, outputs it to the terminal and
    //   marks it as printed. On the following tick, displays a prompt instructing the user to press [Enter] to
    //   exit.
    //
    // Arguments:
    //
    //   t (long):
    //     The current engine tick or elapsed time value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        // Local constants.

        final String TERMINAL_MESSAGE_PRESS_ENTER_TO_EXIT = "\nPress [Enter] to exit...";

        // Local variables.

        ECSEngine engine = (ECSEngine) this.owner;

        // Type tokens for filtering.

        ComponentText          tokenText   = new ComponentText ();
        ComponentMessageStatus tokenStatus = new ComponentMessageStatus ();

        // Iterate through all entities, filtering by those that contain the specific set of components, that this system requires to work with.

        for ( ECSEntity entity : engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( tokenText, tokenStatus ) )
            {
                ComponentText          text   = (ComponentText)          entity.getComponent ( Constants.COMPONENT_TEXT );
                ComponentMessageStatus status = (ComponentMessageStatus) entity.getComponent ( Constants.COMPONENT_MESSAGE_STATUS );

                if ( !status.printed )
                {
                    System.out.println ( "\n\n" + text.text );
                    status.printed = true;
                }
                else if ( !this.promptPrinted )
                {
                    System.out.println ( TERMINAL_MESSAGE_PRESS_ENTER_TO_EXIT );
                    this.promptPrinted = true;
                }
            }
        }
    }
}
