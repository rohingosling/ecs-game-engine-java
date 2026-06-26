//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the abstract ECSSystem class for game systems in the ECS framework.
//
//   Systems encapsulate discrete aspects of game logic (rendering, physics, input, etc.) and are updated once per game
//   loop iteration by the engine.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: ECSSystem
//
// Description:
//
//   Abstract base class for game systems in the ECS architecture.
//
//   - Each system encapsulates a specific aspect of game logic and is updated once per game loop iteration.
//
//   - Subclasses must implement the update method, which receives the elapsed time from the previous frame and
//     processes all relevant entities.
//
//   - Systems can be enabled or disabled at runtime.
//
//*********************************************************************************************************************

public abstract class ECSSystem extends ECSObject
{
    // @formatter:off

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private final static ECSEngine M_DEFAULT_OWNER   = null;
    private final static Integer   M_DEFAULT_ID      = 0;
    private final static String    M_DEFAULT_NAME    = "SYSTEM";
    private final static Boolean   M_DEFAULT_ENABLED = true;

    protected Boolean enabled;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public Boolean isEnabled () { return this.enabled; }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setEnabled ( Boolean enabled ) { this.enabled = enabled; }

    //=================================================================================================================
    // Constructor(s)
    //=================================================================================================================

    public ECSSystem ()                                           { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, M_DEFAULT_OWNER ); }
    public ECSSystem ( Integer id )                               { initialize ( id,           M_DEFAULT_NAME, M_DEFAULT_OWNER ); }
    public ECSSystem ( String name )                              { initialize ( M_DEFAULT_ID, name,           M_DEFAULT_OWNER ); }
    public ECSSystem ( ECSEngine owner )                          { initialize ( M_DEFAULT_ID, M_DEFAULT_NAME, owner           ); }
    public ECSSystem ( Integer id, String name )                  { initialize ( id,           name,           M_DEFAULT_OWNER ); }
    public ECSSystem ( Integer id, ECSEngine owner )              { initialize ( id,           M_DEFAULT_NAME, owner           ); }
    public ECSSystem ( Integer id, String name, ECSEngine owner ) { initialize ( id,           name,           owner           ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: update
    //
    // Description:
    //
    //   Abstract method called once per game loop iteration.
    //
    //   - Subclasses implement this to process all relevant entities and execute system-specific game logic.
    //
    //   - Override in child class to implement update functionality.
    //     t = time elapsed during last loop iteration.
    //
    // Arguments:
    //
    //   t (long):
    //     The time elapsed during the last loop iteration, in milliseconds.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public abstract void update ( long t );

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Shared initialization logic used by all constructors. Sets the owner, ID, name, and enables the system
    //   by default.
    //
    // Arguments:
    //
    //   id (Integer):
    //     The unique identifier for this system.
    //
    //   name (String):
    //     The internal name for this system.
    //
    //   owner (ECSEngine):
    //     The parent engine instance that owns this system, or null if unowned.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ( Integer id, String name, ECSEngine owner )
    {
        this.owner   = owner;
        this.id      = id;
        this.name    = name;
        this.enabled = M_DEFAULT_ENABLED;
    }

    // @formatter:on
}
