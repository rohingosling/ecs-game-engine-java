// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    CommandParticleSimulatorEsc
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Handles the Escape key in the particle simulation.
//
//   - If a particle is selected, deselects all particles.
//   - If no particle is selected, exits the simulation.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.commands;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentUserControl;

public class CommandParticleSimulatorEsc implements ICommand
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

    public CommandParticleSimulatorEsc ( EngineParticleSimulator engine )
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
        // Check if any particle is selected.

        ComponentUserControl userControlToken = new ComponentUserControl ();
        boolean hasSelection = false;

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( userControlToken ) )
            {
                hasSelection = true;
                break;
            }
        }

        if ( hasSelection )
        {
            // Deselect all particles.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( userControlToken ) )
                {
                    entity.getComponents ().remove ( Constants.COMPONENT_USER_CONTROL );
                }
            }
        }
        else
        {
            // Exit simulation.

            new CommandParticleSimulatorExit ( this.engine ).execute ();
        }
    }
}
