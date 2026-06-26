//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   ECS engine for the Hello World demo.
//
//   - Creates an EntityMessage with the text "Hello World!".
//   - Registers a SystemTerminal to print it.
//   - The engine keeps spinning until the user presses [Enter].
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.engines;

import java.io.IOException;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.TextFormat;
import rohin.gameengine.application.Constants;
import rohin.gameengine.commands.CommandExitApplication;
import rohin.gameengine.entities.EntityMessage;
import rohin.gameengine.systems.SystemTerminal;

//*********************************************************************************************************************
// Class: EngineHelloWorld
//
// Description:
//
//   Concrete ECS engine subclass for the Hello World demo. Wires up an EntityMessage containing the text
//   "Hello World!" and a SystemTerminal to print it. A background thread listens for console input and posts a
//   CommandExitApplication to terminate the game loop when the user presses [Enter].
//
//*********************************************************************************************************************

public class EngineHelloWorld extends ECSEngine
{
    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: swapBuffer
    //
    // Description:
    //
    //   Swap buffer. Nothing to do for this demo, as it is a terminal application and we do not need a graphics
    //   window.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @ Override
    protected void swapBuffer ()
    {
        // No rendering required for this console-output demo.
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: EngineHelloWorld
    //
    // Description:
    //
    //   Default constructor. Delegates to the parent ECSEngine constructor and then calls the private initialize
    //   method to wire up entities, systems, and the console input listener.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public EngineHelloWorld ()
    {
        super ();
        initialize ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Private initialization helper called by the constructor. Creates the EntityMessage with "Hello World!"
    //   text, registers a SystemTerminal to display it, and starts a daemon thread that waits for console input
    //   before posting a CommandExitApplication to stop the game loop.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        // Create the message entity.

        EntityMessage entityMessage = new EntityMessage ( Constants.ENTITY_MESSAGE, "ENTITY_MESSAGE", "Hello World!", this );

        addEntity ( Constants.ENTITY_MESSAGE, entityMessage );

        // Create and register the terminal system.

        SystemTerminal systemTerminal = new SystemTerminal ( Constants.SYSTEM_TERMINAL, "SYSTEM_TERMINAL", this );

        addSystem ( Constants.SYSTEM_TERMINAL, systemTerminal );

        // Start a background thread that waits for console input, then
        // posts a CommandExitApplication via the CommandManager.

        Thread inputThread = new Thread ( () ->
        {
            try
            {
                System.in.read ();
                getCommandManager ().postCommand ( new CommandExitApplication ( this ) );
            }
            catch ( IOException e )
            {
                TextFormat.printFormattedException ( e, false );
            }
        });

        inputThread.setDaemon ( true );
        inputThread.start ();
    }
}
