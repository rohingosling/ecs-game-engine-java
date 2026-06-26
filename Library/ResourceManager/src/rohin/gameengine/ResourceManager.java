//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the ResourceManager class, a management container for Resource objects.
// 
//   Provides methods to add, retrieve, load, and unload resources by integer ID. Used by the game engine to 
//   centralize asset lifecycle management.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.util.*;

//*********************************************************************************************************************
// Class: ResourceManager
//
// Description:
//
//   Manages a collection of Resource objects stored in a HashMap keyed by integer identifier. 
//
//   Provides methods to add resources to the cache, load and unload individual resources by ID, load or unload all 
//   resources in bulk, and retrieve resources for direct access.
//
//*********************************************************************************************************************

public class ResourceManager
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private Map <Integer, Resource> resources;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public Collection <Resource> getResources () { return this.resources.values (); }

    //=================================================================================================================
    // Constructor(s)
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: ResourceManager
    //
    // Description:
    //
    //   Default constructor. Initializes the internal resource map by delegating to the shared initialize method.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ResourceManager ()
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
    //   Shared initialization logic used by all constructors.
    // 
    //   Creates an empty HashMap to store Resource objects keyed by integer identifier.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.resources = new HashMap <Integer, Resource> ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: add
    //
    // Description:
    //
    //   Registers a Resource instance in the resource map under the specified integer ID. 
    // 
    //   The resource must have been constructed and configured before being added to the manager.
    //
    // Arguments:
    //
    //   id (int):
    //     The unique integer identifier under which the resource will be stored in the map.
    //
    //   resource (Resource):
    //     The Resource instance to register in the resource map.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void add ( int id, Resource resource )
    {
        this.resources.put ( id, resource );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: load
    //
    // Description:
    //
    //   Loads a resource by its integer ID, using the file name already configured on the Resource instance.
    //
    // Arguments:
    //
    //   id (int):
    //     The unique identifier of the resource to load.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void load ( int id )
    {
        Resource resource = this.resources.get ( id );
        resource.load ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: load
    //
    // Description:
    //
    //   Overloaded load method that sets the file name on the resource before loading. 
    // 
    //  Retrieves the resource from the map by its integer ID, assigns the given file path, and then delegates to the 
    //  resource's load method.
    //
    // Arguments:
    //
    //   id (int):
    //     The unique identifier of the resource to load.
    //
    //   fileName (String):
    //     The file path from which the resource data will be loaded.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void load ( int id, String fileName )
    {
        Resource resource = this.resources.get ( id );
        resource.setFileName ( fileName );
        resource.load ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: loadAll
    //
    // Description:
    //
    //   Loads every resource currently registered in the resource map by iterating over the map's value collection and
    //   calling load on each Resource instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void loadAll ()
    {
        Collection <Resource> resources = this.resources.values ();

        for ( Resource resource : resources )
        {
            resource.load ();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: unloadAll
    //
    // Description:
    //
    //   Releases every resource currently registered in the resource map. Iterates over the map's value collection and
    //   calls unload on each Resource instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void unloadAll ()
    {
        Collection <Resource> resources = this.resources.values ();

        for ( Resource resource : resources )
        {
            resource.unload ();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: get
    //
    // Description:
    //
    //   Retrieves a resource from the map by its integer ID, providing direct access to the Resource instance.
    //
    // Arguments:
    //
    //   id (int):
    //     The unique identifier of the resource to retrieve.
    //
    // Returns:
    //
    //   The Resource associated with the given ID, or null if no resource is registered under that key.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Resource get ( int id )
    {
        return this.resources.get ( id );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: unload
    //
    // Description:
    //
    //   Releases a single resource identified by its integer ID. Retrieves the resource from the map and delegates to
    //   its unload method to free the associated data.
    //
    // Arguments:
    //
    //   id (int):
    //     The unique identifier of the resource to release.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void unload ( int id )
    {
        Resource resource = this.resources.get ( id );
        resource.unload ();
    }
}
