//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds the text string for a message.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;

//*********************************************************************************************************************
// Class: ComponentText
//
// Description:
//
//   ECS data component that stores a text string for a message entity. This component extends ECSComponent and
//   holds a single public field, text, which systems can read to retrieve the message content.
//
//*********************************************************************************************************************

public class ComponentText extends ECSComponent
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    public String text;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/2: ComponentText
    //
    // Description:
    //
    //   Default constructor for use as a type token in hasComponents(). Delegates to the parent ECSComponent
    //   default constructor and initializes the text field to an empty string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ComponentText ()
    {
        super ();
        initialize ( "" );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 2/2: ComponentText
    //
    // Description:
    //
    //   Parameterized constructor that creates a fully identified component with the given id, name, text content,
    //   and owning engine reference. Delegates to the parent ECSComponent constructor and stores the provided
    //   text string.
    //
    // Arguments:
    //
    //   id (Integer):
    //     The unique identifier for this component instance.
    //
    //   name (String):
    //     The display name for this component.
    //
    //   text (String):
    //     The message text to store in this component.
    //
    //   owner (ECSEngine):
    //     The ECS engine instance that owns this component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ComponentText ( Integer id, String name, String text, ECSEngine owner )
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
    //   Private initialization helper called by all constructors. Sets the text field to the provided string
    //   value.
    //
    // Arguments:
    //
    //   text (String):
    //     The message text to assign to this component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ( String text )
    {
        this.text = text;
    }
}
