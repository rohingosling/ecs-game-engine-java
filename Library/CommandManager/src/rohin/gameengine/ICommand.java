//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the ICommand interface, a delegate to be implemented by concrete command classes for use with the
//   CommandManager.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//---------------------------------------------------------------------------------------------------------------------
// Interface: ICommand
//
// Description:
//
//   Command delegate interface to be implemented by concrete commands.
//
//   Each implementing class encapsulates a specific action in its execute method, enabling deferred and queued
//   execution through the CommandManager.
//
//---------------------------------------------------------------------------------------------------------------------

public interface ICommand
{
    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: execute
    //
    // Description:
    //
    //   Executes the action encapsulated by this command.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void execute ();
}
