// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    CommandMenuExitApplication
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Shuts down and exits the application.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.commands;

import rohin.gameengine.ICommand;
import rohin.gameengine.engines.EngineMenu;
import rohin.gameengine.application.Application;
import rohin.gameengine.application.Constants;

public class CommandMenuExitApplication implements ICommand
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private EngineMenu engine;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public CommandMenuExitApplication ( EngineMenu engine )
    {
        this.engine = engine;
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Execute the command.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void execute ()
    {
        Application application = this.engine.getApplication ();
        application.getGlobalCache ().put ( Constants.GLOBAL_CACHE_APPLICATION_STATE, Constants.APPLICATION_STATE_STOPPING );
        this.engine.setLoopRunning ( false );
    }
}
