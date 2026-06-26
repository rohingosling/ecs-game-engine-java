//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Command to stop the game loop by setting the loopRunning flag to false, triggering an orderly application exit.
//   This class implements the ICommand interface as part of the command pattern used by the ECS game engine.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.commands;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ICommand;

//*********************************************************************************************************************
// Class: CommandExitApplication
//
// Description:
//
//   Concrete command that exits the application by signaling the ECS engine to stop its main game loop. This class
//   is part of the command pattern implementation, where each user action or system event is encapsulated as a
//   command object implementing the ICommand interface.
//
//   When executed, this command sets the engine's loopRunning flag to false, causing the game loop to terminate
//   gracefully on its next iteration.
//
//*********************************************************************************************************************

public class CommandExitApplication implements ICommand
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private ECSEngine engine;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: CommandExitApplication
    //
    // Description:
    //
    //   Initializes a new CommandExitApplication instance with a reference to the ECS engine whose game loop will
    //   be terminated when this command is executed.
    //
    // Arguments:
    //
    //   engine (ECSEngine):
    //     The ECS engine instance that this command will signal to stop running.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public CommandExitApplication ( ECSEngine engine )
    {
        this.engine = engine;
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: execute
    //
    // Description:
    //
    //   Executes the exit command by setting the engine's loopRunning flag to false. This causes the main game
    //   loop to terminate gracefully on its next iteration, leading to an orderly application shutdown.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @ Override
    public void execute ()
    {
        this.engine.setLoopRunning ( false );
    }
}
