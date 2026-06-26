//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the GlobalCache class, a simple key-value store used to share data across different screens and 
//   components within the ECS Game Engine.
// 
//   It provides a lightweight mechanism for passing configuration and runtime data between ECSEngine instances 
//   without requiring direct coupling.
//
// TODO:
//
//   1. Add generic type-safe retrieval methods to eliminate the need for casting at call sites.
//   2. Consider adding thread-safety support for concurrent access scenarios.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.util.HashMap;

//*********************************************************************************************************************
// Class: GlobalCache
//
// Description:
//
//   A simple key-value store for cross-screen data sharing within the ECS Game Engine.
//
//   - Used to pass data between ECSEngine instances, such as particle counts configured in the menu that the
//     simulator needs to read.
//
//   - The application creates a single GlobalCache instance and passes it to engines and commands that need
//     cross-screen data access.
//
//   - Values are stored as Object references, so callers must cast the retrieved value to the expected type.
//
//*********************************************************************************************************************

public class GlobalCache
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private HashMap <String, Object> cache;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: GlobalCache
    //
    // Description:
    //
    //   Default constructor. Initializes the cache by delegating to the private initialize method, which creates
    //   an empty HashMap ready to store key-value pairs.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public GlobalCache ()
    {
        initialize ();
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Private initialization helper that creates a new empty HashMap to back the cache. Called by the constructor
    //   to set up the internal data structure.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.cache = new HashMap <String, Object> ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: put
    //
    // Description:
    //
    //   Stores a value in the cache under the specified key. If the key already exists, its associated value is
    //   replaced with the new value.
    //
    // Arguments:
    //
    //   key (String):
    //     The lookup key under which the value will be stored. Must not be null.
    //
    //   value (Object):
    //     The value to associate with the key. May be any object type, including null.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void put ( String key, Object value )
    {
        this.cache.put ( key, value );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: get
    //
    // Description:
    //
    //   Retrieves the value associated with the specified key from the cache. 
    // 
    //   Returns null if the key does not exist. Callers should use the contains method to distinguish between a 
    //   missing key and a key that maps to null.
    //
    // Arguments:
    //
    //   key (String):
    //     The lookup key whose associated value is to be retrieved.
    //
    // Returns:
    //
    //   The value associated with the specified key, or null if the key is not present in the cache.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Object get ( String key )
    {
        return this.cache.get ( key );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: contains
    //
    // Description:
    //
    //   Checks whether the cache contains an entry for the specified key. Returns true even if the key maps to a null 
    //   value, allowing callers to distinguish between a missing key and a null-valued entry.
    //
    // Arguments:
    //
    //   key (String):
    //     The lookup key to test for presence in the cache.
    //
    // Returns:
    //
    //   True if the cache contains an entry for the specified key, false otherwise.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public boolean contains ( String key )
    {
        return this.cache.containsKey ( key );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: remove
    //
    // Description:
    //
    //   Removes the entry for the specified key from the cache. If the key does not exist, this method has no effect.
    //
    // Arguments:
    //
    //   key (String):
    //     The lookup key whose entry is to be removed from the cache.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void remove ( String key )
    {
        this.cache.remove ( key );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clear
    //
    // Description:
    //
    //   Removes all entries from the cache, restoring it to an empty state. This is useful for resetting shared state 
    //   between screen transitions or engine restarts.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clear ()
    {
        this.cache.clear ();
    }
}
