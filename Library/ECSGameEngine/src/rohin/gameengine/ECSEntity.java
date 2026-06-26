//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the ECSEntity class, a concrete component container in the ECS framework.
//
//   - Entities are the primary data objects in the game world, holding a collection of components that describe their
//     properties and behaviors.
//
//   - Systems query entities via hasComponents to determine whether they possess the required component set
//     for processing.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.util.*;

//*********************************************************************************************************************
// Class: ECSEntity
//
// Description:
//
//   Represents a game entity in the ECS architecture.
//
//   - Each entity is a named, identifiable container for ECSComponent instances, stored in a HashMap keyed by integer
//     identifier.

//   - Entities can be enabled or disabled, and systems use hasComponents to automatically detect whether an entity
//     carries the component set they require for processing.
//
//*********************************************************************************************************************

public class ECSEntity extends ECSObject
{
    // @formatter:off

    //=================================================================================================================
    // Constants
    //=================================================================================================================

    static final ECSEngine M_DEFAULT_OWNER         = null;
    static final Integer   M_DEFAULT_ID            = 0;
    static final String    M_DEFAULT_NAME          = "ENTITY";
    static final Boolean   M_DEFAULT_ENABLED       = true;
    static final int       M_DEFAULT_HASH_MAP_SIZE = 8;

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private HashMap <Integer, ECSComponent> components;
    private Boolean                         enabled;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public HashMap <Integer, ECSComponent> getComponents (){ return this.components; }
    public Boolean                         isEnabled     () { return this.enabled;    }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setEnabled ( Boolean enabled ) { this.enabled = enabled; }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public ECSEntity ()                                           { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_OWNER  ); }
    public ECSEntity ( Integer id )                               { initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_OWNER  ); }
    public ECSEntity ( ECSEngine owner )                          { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, owner            ); }
    public ECSEntity ( String name )                              { initialize ( M_DEFAULT_ID, name,           M_DEFAULT_OWNER  ); }
    public ECSEntity ( Integer id, String name )                  { initialize ( id,           name,           M_DEFAULT_OWNER  ); }
    public ECSEntity ( Integer id, ECSEngine owner )              { initialize ( id,           M_DEFAULT_NAME, owner            ); }
    public ECSEntity ( Integer id, String name, ECSEngine owner ) { initialize ( id,           name,           owner            ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Shared initialization logic used by all constructors.
    //
    //   Sets the owner, ID, name, creates an empty component map, and enables the entity by default.
    //
    // Arguments:
    //
    //   id (Integer):
    //     The unique identifier for this entity.
    //
    //   name (String):
    //     The internal name for this entity.
    //
    //   owner (ECSEngine):
    //     The parent engine instance that owns this entity, or null if unowned.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ( Integer id, String name, ECSEngine owner )
    {
        this.owner      = owner;
        this.id         = id;
        this.name       = name;
        this.components = new HashMap <Integer, ECSComponent> ( M_DEFAULT_HASH_MAP_SIZE );
        this.enabled    = M_DEFAULT_ENABLED;
    }

    // @formatter:on

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addComponent
    //
    // Description:
    //
    //   Attaches a component to this entity under the specified key.
    //
    //   If a component already exists under the same key, it is replaced.
    //
    // Arguments:
    //
    //   key (Integer):
    //     The unique identifier for the component within this entity.
    //
    //   component (ECSComponent):
    //     The component instance to attach.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void addComponent ( Integer key, ECSComponent component )
    {
        this.components.put ( key, component );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getComponent
    //
    // Description:
    //
    //   Retrieves a component attached to this entity by its integer index.
    //
    // Arguments:
    //
    //   index (int):
    //     The key identifying the component to retrieve.
    //
    // Returns:
    //
    //   The ECSComponent at the specified index, or null if no component exists under that key.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ECSComponent getComponent ( int index )
    {
        return this.components.get ( index );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents
    //
    // Description:
    //
    //   Queries whether this entity possesses all of the specified component types.
    //
    //   - Systems use this method to automatically detect whether they can operate on a particular entity.
    //
    //   - If the entity is disabled, this method immediately returns false.
    //
    // Arguments:
    //
    //   systemComponents (ECSComponent...):
    //     Variable argument list specifying the component types the calling system requires.
    //
    // Returns:
    //
    //   True if every component type in systemComponents has a matching component attached
    //   to this entity. False if the entity is disabled or if one or more required component
    //   types are missing.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Boolean hasComponents ( ECSComponent... systemComponents )
    {
        // {{Add comments}}

        if ( !this.enabled )
        {
            return false;
        }

        // {{Add comments}}

        Collection <ECSComponent> entityComponents = this.components.values ();
        int                       componentCount   = 0;

        try
        {
            // for: Iterate through all components passed in through the variable argument list.
            //   for: Iterate through all components in this entity.
            //     if else: Compare each component from this entity, with each component passed in through the variable argument list.
            //              - If we find a match, then increment the component count.
            //              - Else just ignore this component.

            for ( ECSComponent systemComponent : systemComponents )
            {
                for ( ECSComponent entityComponent : entityComponents )
                {
                    if ( entityComponent.getClass () == systemComponent.getClass () )
                    {
                        componentCount++;
                    }
                }
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }

        // If we have matched all input components with components from the entity, then return true.
        // Else, if we never collected enough components, then return false.

        return ( componentCount == systemComponents.length ) ? true : false;
    }
}
