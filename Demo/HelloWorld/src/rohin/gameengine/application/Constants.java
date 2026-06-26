//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Global constants used by all elements of the project.
//
//   - Use CONSTANT_VALUE = elementIndex++ to auto-increment after initializing elementIndex to zero.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.application;

//*********************************************************************************************************************
// Class: Constants
//
// Description:
//
//   Central registry of integer identifiers for all ECS components, entities, and systems used in the Hello World
//   demo. Each category uses a private auto-incrementing index to assign unique sequential IDs.
//
//*********************************************************************************************************************

public class Constants
{
    // @formatter:off

    //=================================================================================================================
    // Constants
    //=================================================================================================================

    private static int componentIndex = 0;

    public static final Integer COMPONENT_TEXT           = componentIndex++;
    public static final Integer COMPONENT_MESSAGE_STATUS = componentIndex++;

    private static int entityIndex = 0;

    public static final Integer ENTITY_MESSAGE = entityIndex++;

    private static int systemIndex = 0;

    public static final Integer SYSTEM_TERMINAL = systemIndex++;

    // @formatter:on
}
