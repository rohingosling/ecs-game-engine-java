// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    CommandParticleDecelerate
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Finds the particle with user control and sets the appropriate acceleration flag to false,
//   based on the direction specified at construction time.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.commands;

import java.awt.event.KeyEvent;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentUserControl;

public class CommandParticleDecelerate implements ICommand
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private EngineParticleSimulator engine;
    private int direction;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public CommandParticleDecelerate ( EngineParticleSimulator engine, int direction )
    {
        this.engine    = engine;
        this.direction = direction;
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

                switch ( this.direction )
                {
                    case KeyEvent.VK_UP:
                        userControl.accelerateUp = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        userControl.accelerateDown = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        userControl.accelerateLeft = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        userControl.accelerateRight = false;
                        break;
                }

                break;
            }
        }
    }
}
