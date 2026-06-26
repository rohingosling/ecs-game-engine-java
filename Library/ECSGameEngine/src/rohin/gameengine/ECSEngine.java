//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the abstract ECSEngine class, the core of the Entity-Component-System game engine
//   framework.
//
//   - ECSEngine provides the main game loop, system and entity management, command queue processing, resource caching,
//     and frame rate regulation.
//
//   - Concrete subclasses must implement the swapBuffer method for platform-specific double-buffer rendering.
//
// TODO:
//
//   1. Add event management using Library/EventManager.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.util.*;

//*********************************************************************************************************************
// Class: ECSEngine
//
// Description:
//
//   Abstract base class for the ECS game engine. Manages the lifecycle of game systems and
//   entities through a centralized game loop. Provides frame rate regulation in both fixed-delay
//   and target-FPS modes, command queue processing via CommandManager, and resource caching via
//   ResourceManager. Subclasses must implement swapBuffer to integrate platform-specific
//   rendering.
//
//*********************************************************************************************************************

public abstract class ECSEngine extends ECSObject
{
    // @formatter:off

    //=================================================================================================================
    // Constants
    //=================================================================================================================

    public static final Boolean M_GAME_LOOP_DEFAULT_TARGET_ENABLED   = true;
    public static final double  M_GAME_LOOP_DEFAULT_FPS_TARGET       = 90;
    public static final int     M_GAME_LOOP_DEFAULT_LOOP_DELAY_FIXED = 1000;
    public static final int     M_GAME_LOOP_DEFAULT_LOOP_DELAY_MIN   = 5;
    public static final int     M_DEFAULT_SYSTEM_HASH_MAP_SIZE       = 16;      // Number of systems before the hash map needs to be resized.
    public static final int     M_DEFAULT_ENTITY_HASH_MAP_SIZE       = 1024;    // Number of entities before the hash map needs to be resized.

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private HashMap <Integer, ECSSystem> systems;           // List of game entities.
    private HashMap <Integer, ECSEntity> entities;          // List of game systems.
    private CommandManager               commandManager;    // Command invocation manager.
    private ResourceManager              resourceManager;   // Resource cache manager.
    private Boolean                      loggingEnabled;    // logging switch.
    private ConsoleLogger                logger;            // Console logger.
    private String                       resourcePath;      // Disk location for all game assets. e.g. Images, sounds, vector models, etc.

    private Boolean                     loopRunning;        // Loop state. Set to true to run the game loop. Set too false to exit the game loop.
    private int                         loopDelayFixed;     // Game loop, frame rate regulator. Fixed delay, measured in ms.
    private int                         loopDelayVariable;  // Game loop, frame rate regulator. Dynamic delay, that adjusts to maintain target FPS.
    private int                         loopDelayMin;       // Minimum loop iteration delay.
    private double                      fpsTarget;          // Target Frames Per Second.
    private Boolean                     fpsTargetEnabled;   // Choose whether to use a fixed loop delay, or a target FPS.

    private double                      fps;                // Measured frames Per Second. (Animation frequency).
    private long                        loopIterationTime;  // Loop iteration time. Used for display and debugging.

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public HashMap <Integer, ECSSystem> getSystems         () { return this.systems;          }
    public HashMap <Integer, ECSEntity> getEntities        () { return this.entities;         }
    public Boolean                      isloggingEnabled   () { return this.loggingEnabled;   }
    public Boolean                      isLoopRunning      () { return this.loopRunning;      }
    public Boolean                      isFPSTargetEnabled () { return this.fpsTargetEnabled; }
    public int                          getLoopDelayFixed  () { return this.loopDelayFixed;   }
    public int                          getLoopDelayMin    () { return this.loopDelayMin;     }
    public double                       getFPSTarget       () { return this.fpsTarget;        }
    public CommandManager               getCommandManager  () { return this.commandManager;   }
    public String                       getResourcePath    () { return this.resourcePath;     }
    public ResourceManager              getResourceManager () { return this.resourceManager;  }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setCommandManager   ( CommandManager  commandManager   ) { this.commandManager   = commandManager;   }
    public void setloggingEnabled   ( Boolean         loggingEnabled   ) { this.loggingEnabled   = loggingEnabled;   }
    public void setLoopRunning      ( Boolean         loopRunning      ) { this.loopRunning      = loopRunning;      }
    public void setLoopDelayFixed   ( int             loopDelayFixed   ) { this.loopDelayFixed   = loopDelayFixed;   }
    public void setLoopDelayMin     ( int             loopDelayMin     ) { this.loopDelayMin     = loopDelayMin;     }
    public void setFPSTargetEnabled ( Boolean         fpsTargetEnabled ) { this.fpsTargetEnabled = fpsTargetEnabled; }
    public void setFPSTarget        ( double          fpsTarget        ) { this.fpsTarget        = fpsTarget;        }
    public void setResourcePath     ( String          resourcePath     ) { this.resourcePath     = resourcePath;     }
    public void setResourceManager  ( ResourceManager resourceManager  ) { this.resourceManager  = resourceManager;  }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public ECSEngine ()                  { initialize ( null  ); }
    public ECSEngine ( ECSEngine owner ) { initialize ( owner ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: swapBuffer
    //
    // Description:
    //
    //   Abstract method that swaps the double buffers to display the most recently rendered animation frame.
    //   Subclasses must implement this to provide platform-specific rendering support.
    //
    //-----------------------------------------------------------------------------------------------------------------

    // Swap the double buffers in order to show the most recently drawn animation frame.

    protected abstract void swapBuffer ();

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Shared initialization logic used by all constructors. Sets up the system and entity
    //   registries, command manager, resource manager, console logger, and all game loop
    //   parameters to their default values.
    //
    // Arguments:
    //
    //   owner (ECSEngine):
    //     The parent engine instance that owns this engine, or null if this is the root
    //     engine.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ( ECSEngine owner )
    {
        // Initialize game engine parameters.

        this.owner           = owner;
        this.systems         = new HashMap <Integer, ECSSystem> ( M_DEFAULT_SYSTEM_HASH_MAP_SIZE );
        this.entities        = new HashMap <Integer, ECSEntity> ( M_DEFAULT_ENTITY_HASH_MAP_SIZE );
        this.commandManager  = new CommandManager ();
        this.resourceManager = new ResourceManager ();
        this.loggingEnabled  = true;
        this.logger          = new ConsoleLogger ( this, this.loggingEnabled );

        // Initialize game loop parameters.

        this.loopRunning       = true;
        this.fpsTargetEnabled  = M_GAME_LOOP_DEFAULT_TARGET_ENABLED;
        this.fpsTarget         = M_GAME_LOOP_DEFAULT_FPS_TARGET;
        this.loopDelayFixed    = M_GAME_LOOP_DEFAULT_LOOP_DELAY_FIXED;
        this.loopDelayMin      = M_GAME_LOOP_DEFAULT_LOOP_DELAY_MIN;
        this.loopDelayVariable = 0;

        // Initialize loop monitoring points.

        this.loopIterationTime = 0;
        this.fps               = 0;
    }

    // @formatter:on

    //-----------------------------------------------------------------------------------------------------------------
    // Method: run
    //
    // Description:
    //
    //   Executes the main game loop. The loop continues until loopRunning is set to false.
    //
    //   Each iteration of the main game loop performs the following operations:
    //
    //   - Flushes the command queue.
    //   - Updates all registered ECS systems.
    //   - Regulates the frame rate.
    //   - Measures frame timing.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void run ()
    {
        // Log the engine state to the console before entering the game loop.

        this.logger.log ();

        // Initialize frame timing variables.

        long start = 0;
        long stop  = 0;
        long t     = 0;

        // Ensure the game loop flag is set before entering the main loop.

        this.loopRunning = true;

        while ( this.loopRunning )
        {
            // Start the clock.

            start = System.currentTimeMillis ();

            // Flush and execute commands in the command queue.

            this.commandManager.flush ();

            // Update all registered ECS systems for this frame.
            // - Loop through all game systems.
            // - Call Update on each of them.

            for ( ECSSystem system : this.systems.values () )
            {
                system.update ( t );
            }

            // Give the CPU some time to do other things other than spin this loop.

            regulateFrameRate ();

            // Stop the clock and calculate the lap time for this iteration.

            stop = System.currentTimeMillis ();
            t    = stop - start;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addSystem
    //
    // Description:
    //
    //   Registers an ECS system with the engine under the specified key. The system will be
    //   updated on each iteration of the game loop.
    //
    // Arguments:
    //
    //   key (Integer):
    //     The unique identifier for the system.
    //
    //   system (ECSSystem):
    //     The ECS system instance to register.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void addSystem ( Integer key, ECSSystem system )
    {
        this.systems.put ( key, system );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addEntity
    //
    // Description:
    //
    //   Registers an ECS entity with the engine under the specified key.
    //
    // Arguments:
    //
    //   key (Integer):
    //     The unique identifier for the entity.
    //
    //   entity (ECSEntity):
    //     The ECS entity instance to register.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void addEntity ( Integer key, ECSEntity entity )
    {
        this.entities.put ( key, entity );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getEntity
    //
    // Description:
    //
    //   Retrieves a registered ECS entity by its integer identifier.
    //
    // Arguments:
    //
    //   entityID (int):
    //     The unique identifier of the entity to retrieve.
    //
    // Returns:
    //
    //   The ECSEntity associated with the given ID, or null if no entity is registered
    //   under that key.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ECSEntity getEntity ( int entityID)
    {
        return this.entities.get ( entityID );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: regulateFrameRate
    //
    // Description:
    //
    //   Regulates the game loop frame rate using either a variable or fixed delay strategy.
    //   When target-FPS mode is enabled, calculates a dynamic delay period based on the
    //   target frame rate. When disabled, applies the configured fixed delay. This prevents
    //   the game loop from consuming excessive CPU time.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void regulateFrameRate ()
    {
        if ( this.fpsTargetEnabled )
        {
            // Calculate dynamic period loop delay.
            //
            // - If we are spinning faster than the target FPS, then delay the loop for the remaining time, until
            //   target FPS is reached.
            //
            // - If we are spinning slower than the target FPS, then just force the minimum delay.
            //
            // Note:
            //   We don't allow a minimum delay of zero, for risk of the game loop hogging the CPU and potentially
            //   hanging the main application thread.

            if ( this.fpsTarget > 0.0 )
            {
                this.loopDelayVariable = ( int ) ( 1000.0 / this.fpsTarget );
            }
            else
            {
                this.loopDelayVariable = this.loopDelayMin;
            }

            delay ( this.loopDelayVariable );
        }
        else
        {
            // Fixed period loop delay.

            delay ( this.loopDelayFixed );
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: delay
    //
    // Description:
    //
    //   Pauses the current thread for the specified duration. Used by the frame rate regulator
    //   to throttle the game loop.
    //
    // Arguments:
    //
    //   period (long):
    //     The delay duration in milliseconds.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void delay ( long period )
    {
        try
        {
            Thread.sleep ( period );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace ();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: printGameObjects
    //
    // Description:
    //
    //   Builds a formatted string representation of all game objects managed by this engine,
    //   including systems and entities with their attached components.
    //
    //   Useful for debugging and console output.
    //
    // Returns:
    //
    //   A formatted multi-line String listing all systems, entities, and their components.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String printGameObjects ()
    {
        // Define formatting constants for building the output string.

        final String EMPTY             = "";
        final String SPACE             = " ";
        final String NEW_LINE          = "\n";
        final String INDENT            = SPACE + SPACE;
        final String BULLET1           = "- ";
        final String BULLET2           = "+ ";
        final String PARENTHESUS_OPEN  = " ( ";
        final String PARENTHESUS_CLOSE = " )";
        final String DELIMITER         = ", ";
        final String LABEL_TERMINATOR  = ": ";
        final String LABEL_GAME_ENGINE = "ECSEngine" + LABEL_TERMINATOR;
        final String LABEL_SYSTEMS     = "Systems"   + LABEL_TERMINATOR;
        final String LABEL_ENTITIES    = "Entities"  + LABEL_TERMINATOR;

        // Build the formatted game object listing.

        String gameObjects = new String ();

        // List header and title.

        gameObjects =  EMPTY;
        gameObjects += NEW_LINE + NEW_LINE;
        gameObjects += INDENT + LABEL_GAME_ENGINE + this.getClass ().getSimpleName ();
        gameObjects += NEW_LINE + NEW_LINE;
        gameObjects += INDENT + BULLET1 + LABEL_SYSTEMS;
        gameObjects += NEW_LINE;

        // List systems.

        for ( ECSSystem system : this.systems.values () )
        {
            gameObjects += INDENT + INDENT + BULLET2 + system.getClass ().getSimpleName ();
            gameObjects += NEW_LINE;
        }

        // List Entities and their components.

        gameObjects += NEW_LINE;
        gameObjects += INDENT + BULLET1 + LABEL_ENTITIES;
        gameObjects += NEW_LINE;

        for ( ECSEntity entity : this.entities.values () )
        {
            // Get the component count and append the entity name to the output.

            int componentCount = entity.getComponents ().size ();

            gameObjects += INDENT + INDENT + BULLET2 + entity.getClass ().getSimpleName ();

            // Append the entity's component list, if any.

            if ( componentCount > 0)
            {
                // List components.

                int i = 0;
                gameObjects += PARENTHESUS_OPEN;

                for ( ECSComponent component : entity.getComponents ().values () )
                {
                    gameObjects += component.getClass ().getSimpleName ();

                    if ( i < componentCount - 1 )
                    {
                        gameObjects += DELIMITER;           // If there are still more components, add a delimiter, and continue.
                    }
                    else
                    {
                        gameObjects += PARENTHESUS_CLOSE;   // If there are no more components to show, then close the parentheses, and exit the loop.
                    }
                    ++i;
                }
            }
            gameObjects += NEW_LINE;
        }

        // Return our nicely formatted string.

        return gameObjects;
    }
}
