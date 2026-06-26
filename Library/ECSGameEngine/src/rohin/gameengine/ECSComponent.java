//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the ECSComponent class, the base class for all components in the Entity-Component-System architecture.
//   Components are pure data containers that attach to entities and are processed by systems.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: ECSComponent
//
// Description:
//
//   Base class for all ECS components.
//
//   - Extends ECSObject with default values for id, name, and family.
//
//   - Provides nine constructor overloads for flexible initialization of component properties. Components are data
//     containers designed to be attached to ECSEntity instances and processed by ECSSystem instances.
//
//*********************************************************************************************************************

public class ECSComponent extends ECSObject
{
    // @formatter:off

    //=================================================================================================================
    // Constants
    //=================================================================================================================

    private static final Integer M_DEFAULT_ID     = 0;
    private static final String  M_DEFAULT_NAME   = "COMPONENT";
    private static final Integer M_DEFAULT_FAMILY = 0;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public ECSComponent ()                                                           { Initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_FAMILY, null  ); }
    public ECSComponent ( Integer id )                                               { Initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_FAMILY, null  ); }
    public ECSComponent ( ECSEngine owner )                                          { Initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_FAMILY, owner ); }
    public ECSComponent ( Integer id, ECSEngine owner )                              { Initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_FAMILY, owner ); }
    public ECSComponent ( Integer id, String name )                                  { Initialize ( id,           name,           M_DEFAULT_FAMILY, null  ); }
    public ECSComponent ( Integer id, Integer family )                               { Initialize ( id,           M_DEFAULT_NAME, family,           null  ); }
    public ECSComponent ( Integer id, Integer family, ECSEngine owner )              { Initialize ( id,           M_DEFAULT_NAME, family,           owner ); }
    public ECSComponent ( Integer id, String name, ECSEngine owner )                 { Initialize ( id,           name,           M_DEFAULT_FAMILY, owner ); }
    public ECSComponent ( Integer id, String name, Integer family, ECSEngine owner ) { Initialize ( id,           name,           family,           owner ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: Initialize
    //
    // Description:
    //
    //   Shared initializer used by all constructors to set the component's id, name, family, and owner fields.
    //
    // Arguments:
    //
    //   id (Integer):
    //     The unique identifier for this component.
    //
    //   name (String):
    //     The display name for this component.
    //
    //   family (Integer):
    //     The family group identifier for this component.
    //
    //   owner (ECSEngine):
    //     The engine that owns this component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void Initialize ( Integer id, String name, Integer family, ECSEngine owner )
    {
        this.id     = id;
        this.name   = name;
        this.family = family;
        this.owner  = owner;
    }

    // @formatter:on
}
