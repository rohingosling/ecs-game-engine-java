// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EntityMenuTextBox
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   An entity used to display blocks of text on menu information screens
//   (Instructions, About). Text content is loaded from text files at startup.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.components.ComponentTextBox;
import rohin.gameengine.application.Constants;

public class EntityMenuTextBox extends ECSEntity
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EntityMenuTextBox ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        addComponent ( Constants.COMPONENT_TEXT_BOX, new ComponentTextBox () );
    }
}
