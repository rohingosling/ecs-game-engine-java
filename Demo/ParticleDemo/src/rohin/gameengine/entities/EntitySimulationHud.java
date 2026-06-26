// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EntitySimulationHud
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   An entity used to render the HUD (Heads Up Display) overlay during the
//   particle simulation. Displays properties of the currently selected particle.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.components.ComponentHud;
import rohin.gameengine.application.Constants;

public class EntitySimulationHud extends ECSEntity
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EntitySimulationHud ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        addComponent ( Constants.COMPONENT_HUD, new ComponentHud () );
    }
}
