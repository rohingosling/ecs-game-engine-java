// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EntityMenuButtonCounter
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   The same as EntityMenuButton, but also includes a ComponentParticleCount
//   component to store particle counts. Used for the settings menu counter
//   buttons.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.components.ComponentButtonImage;
import rohin.gameengine.components.ComponentButtonText;
import rohin.gameengine.components.ComponentButtonState;
import rohin.gameengine.components.ComponentParticleCount;
import rohin.gameengine.components.ComponentGeometryRectangle;
import rohin.gameengine.application.Constants;

public class EntityMenuButtonCounter extends ECSEntity
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EntityMenuButtonCounter ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        addComponent ( Constants.COMPONENT_BUTTON_IMAGE,       new ComponentButtonImage () );
        addComponent ( Constants.COMPONENT_BUTTON_TEXT,         new ComponentButtonText () );
        addComponent ( Constants.COMPONENT_BUTTON_STATE,        new ComponentButtonState () );
        addComponent ( Constants.COMPONENT_PARTICLE_COUNT,      new ComponentParticleCount () );
        addComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE,  new ComponentGeometryRectangle () );
    }
}
