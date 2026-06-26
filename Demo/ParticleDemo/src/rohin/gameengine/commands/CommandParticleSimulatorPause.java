// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    CommandParticleSimulatorPause
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Toggles the paused state of the particle simulation.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.commands;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentWorld;

public class CommandParticleSimulatorPause implements ICommand
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private EngineParticleSimulator engine;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public CommandParticleSimulatorPause ( EngineParticleSimulator engine )
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
        ComponentWorld worldToken = new ComponentWorld ();

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( worldToken ) )
            {
                ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );
                world.paused = !world.paused;
                break;
            }
        }
    }
}
