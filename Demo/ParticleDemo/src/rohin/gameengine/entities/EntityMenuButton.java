// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EntityMenuButton
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   An entity used to represent buttons in the menu system.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.components.ComponentButtonImage;
import rohin.gameengine.components.ComponentButtonText;
import rohin.gameengine.components.ComponentButtonState;
import rohin.gameengine.components.ComponentGeometryRectangle;
import rohin.gameengine.application.Constants;

public class EntityMenuButton extends ECSEntity
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EntityMenuButton ( Integer id, String name, ECSEngine owner )
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
        addComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE,  new ComponentGeometryRectangle () );
    }
}
