// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    CommandParticleSimulatorToggleWireframe
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Toggles the visibility of wireframe geometry for all particle entities.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.commands;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentParticleGroup;
import rohin.gameengine.components.ComponentGeometryCircle;

public class CommandParticleSimulatorToggleWireframe implements ICommand
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

    public CommandParticleSimulatorToggleWireframe ( EngineParticleSimulator engine )
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
        ComponentParticleGroup particleGroupToken = new ComponentParticleGroup ();

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( particleGroupToken ) )
            {
                ComponentGeometryCircle geometry = (ComponentGeometryCircle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
                geometry.visible = !geometry.visible;
            }
        }
    }
}
