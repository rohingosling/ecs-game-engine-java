// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EntityMenuBackground
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   An entity used to represent the background of the menu system.
//   The background image changes via ComponentResourceBackgroundImage when selecting sub-menus.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.components.ComponentResourceBackgroundImage;
import rohin.gameengine.application.Constants;

public class EntityMenuBackground extends ECSEntity
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EntityMenuBackground ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        addComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE, new ComponentResourceBackgroundImage () );
    }
}
