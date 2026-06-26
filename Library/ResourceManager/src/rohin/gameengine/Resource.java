//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the Resource class, which encapsulates a loadable resource with a file name and an associated object.
//   Subclasses override load and unload to implement resource-specific loading behavior.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: Resource
//
// Description:
//
//   Base class for loadable resources in the ECS Game Engine. 
//
//   Each resource associates a file name with a runtime object. Subclasses override the load and unload methods to 
//   implement resource-specific loading and cleanup behavior.
//
//*********************************************************************************************************************

public class Resource
{
    //@formatter:off

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    protected final static Object M_DEFAULT_OBJECT    = null;
    protected final static String M_DEFAULT_FILE_NAME = null;

    protected Object object;
    protected String fileName;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public Object getObject   () { return this.object;   }
    public String getFileName () { return this.fileName; }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setObject   ( Object object   ) { this.object   = object;   }
    public void setFileName ( String fileName ) { this.fileName = fileName; }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public Resource ()                                 { initialize ( M_DEFAULT_FILE_NAME, M_DEFAULT_OBJECT ); }
    public Resource ( String fileName )                { initialize ( fileName,            M_DEFAULT_OBJECT ); }
    public Resource ( String fileName, Object object ) { initialize ( fileName,            object           ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Shared initializer used by all constructors to set the file name and object fields.
    //
    // Arguments:
    //
    //   fileName (String):
    //     The file name to associate with this resource.
    //
    //   object (Object):
    //     The runtime object to associate with this resource.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ( String fileName, Object object )
    {
        this.object   = object;
        this.fileName = fileName;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: load
    //
    // Description:
    //
    //   Loads the resource. Subclasses override this method to implement resource-specific loading behavior.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void load ()
    {
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: unload
    //
    // Description:
    //
    //   Unloads the resource. Subclasses override this method to implement resource-specific cleanup behavior.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void unload ()
    {
    }

    //@formatter:on
}
