// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    CommandParticleAccelerateLeft
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Finds the particle with user control and sets accelerateLeft to true.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.commands;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentUserControl;

public class CommandParticleAccelerateLeft implements ICommand
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

    public CommandParticleAccelerateLeft ( EngineParticleSimulator engine )
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
        ComponentUserControl userControlToken = new ComponentUserControl ();

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( userControlToken ) )
            {
                ComponentUserControl userControl = (ComponentUserControl) entity.getComponent ( Constants.COMPONENT_USER_CONTROL );
                userControl.accelerateLeft = true;
                break;
            }
        }
    }
}
