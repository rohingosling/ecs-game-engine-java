// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EntityParticleWorld
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   The simulation world entity. Stores global simulation properties and the
//   simulation background image.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.components.ComponentWorld;
import rohin.gameengine.components.ComponentResourceBackgroundImage;
import rohin.gameengine.application.Constants;

public class EntityParticleWorld extends ECSEntity
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EntityParticleWorld ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        addComponent ( Constants.COMPONENT_WORLD,                      new ComponentWorld () );
        addComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE,  new ComponentResourceBackgroundImage () );
    }
}
