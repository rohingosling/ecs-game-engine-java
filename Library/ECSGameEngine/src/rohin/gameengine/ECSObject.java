//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the ECSObject base class, the root of the ECS type hierarchy.
//
//   All framework objects (entities, systems, and the engine itself) extend this class to inherit common identity
//   fields and owner tracking.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: ECSObject
//
// Description:
//
//   Base class for all objects in the ECS game engine framework.
//
//   - Provides common identity fields including a unique integer ID, a human-readable name, a family ID for
//     hierarchical grouping, and an owner reference.
//
//   - Extended by ECSEntity, ECSSystem, and ECSEngine.
//
//*********************************************************************************************************************

public class ECSObject
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    protected Integer id;           // Object ID. Should be unique.
    protected String  name;         // Internal fileName. e.g. "SOME_GAME_OBJECT".
    protected Integer family;       // The family ID is used for hierarchical association of entities.
    protected Object  owner;        // Handle to the objects' owner.

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public long   getId     () { return this.id;     }
    public String getName   () { return this.name;   }
    public long   getFamily () { return this.family; }
    public Object getOwner  () { return this.owner;  }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setId     ( Integer id     ) { this.id     = id;     }
    public void setName   ( String  name   ) { this.name   = name;   }
    public void setFamily ( Integer family ) { this.family = family; }
    public void setOwner  ( Object  owner  ) { this.owner  = owner;  }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: toString
    //
    // Description:
    //
    //   Generates a text representation of this object in the format
    //   "[family.id] name", suitable for debugging and console output.
    //
    // Returns:
    //
    //   A formatted String containing the family ID, object ID, and name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String toString ()
    {
        return "[" + this.family + "." + this.id  + "] " + this.name;
    }
}
