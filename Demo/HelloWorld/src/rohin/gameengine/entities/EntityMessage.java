//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   A message entity that writes text to the terminal.
//
//   - Holds ComponentText and ComponentMessageStatus components.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentMessageStatus;
import rohin.gameengine.components.ComponentText;

//*********************************************************************************************************************
// Class: EntityMessage
//
// Description:
//
//   ECS entity representing a printable message. Composes a ComponentText to hold the message string and a
//   ComponentMessageStatus to track whether the message has already been printed to the terminal.
//
//*********************************************************************************************************************

public class EntityMessage extends ECSEntity
{
    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: EntityMessage
    //
    // Description:
    //
    //   Creates a new EntityMessage with the given id, name, text content, and owning engine reference. Delegates
    //   to the parent ECSEntity constructor and calls the private initialize method to attach the required
    //   components.
    //
    // Arguments:
    //
    //   id (Integer):
    //     The unique identifier for this entity.
    //
    //   name (String):
    //     The display name for this entity.
    //
    //   text (String):
    //     The message text to store in the ComponentText component.
    //
    //   owner (ECSEngine):
    //     The ECS engine instance that owns this entity.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public EntityMessage ( Integer id, String name, String text, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ( text );
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Private initialization helper called by the constructor. Attaches a ComponentText holding the message
    //   string and a ComponentMessageStatus to track whether the message has been printed.
    //
    // Arguments:
    //
    //   text (String):
    //     The message text to store in the ComponentText component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ( String text )
    {
        addComponent ( Constants.COMPONENT_TEXT,           new ComponentText          ( Constants.COMPONENT_TEXT,           "COMPONENT_TEXT",           text, (ECSEngine) this.owner ) );
        addComponent ( Constants.COMPONENT_MESSAGE_STATUS, new ComponentMessageStatus ( Constants.COMPONENT_MESSAGE_STATUS, "COMPONENT_MESSAGE_STATUS",       (ECSEngine) this.owner ) );
    }
}
