//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Application orchestrator for the Particle Simulator.
//
//   - Creates and manages GlobalCache, GraphicsWindow, and ApplicationSettings.
//
//   - Orchestrates transitions between EngineMenu and EngineParticleSimulator.
//
//   - No game or user interface logic lives here.
//
// TODO:
//
//   1. Think about a way to demonstrate use of the event manager `Library/EventManager/`.
//      Ideas:
//      - Add collision sounds. An audio system can listen for collision events.
//      - Some kind of pseudo scoring, where entities that have collided raise an event, and a scoring system
//        increments a collision score for them. Something like that.
//
//   2. Create new sub-menus under settings for application settings, graphics, sound, global physics settings, and
//      particle group settings.
//      - Try to see if we can get everything that can be controlled from `settings.properties` to be configurable from
//        the user interface.
//
//   3. Add mouse support for menu system and particle selection.
//      - Add support for dragging particles with the mouse.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.application;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.File;

import rohin.gameengine.ApplicationSettings;
import rohin.gameengine.ConsoleLogger;
import rohin.gameengine.GlobalCache;
import rohin.gameengine.GraphicsWindow;
import rohin.gameengine.TextFormat;
import rohin.gameengine.engines.EngineMenu;
import rohin.gameengine.engines.EngineParticleSimulator;

//*********************************************************************************************************************
// Class: Application
//
// Description:
//
//   Application orchestrator for the Particle Simulator. Creates and manages the shared resources
//   (ApplicationSettings, GlobalCache, GraphicsWindow) and runs the top-level state machine that transitions between
//   the menu and particle simulation engines.
//
//*********************************************************************************************************************

public class Application
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    // @formatter:off

    private ApplicationSettings settings;
    private GlobalCache         globalCache;
    private GraphicsWindow      applicationWindow;
    private BufferStrategy      screenBuffer;
    private Graphics2D          graphicsAPI;
    private ConsoleLogger       logger;
    private Boolean             loggingEnabled;
    private String              applicationName;
    private int                 versionMajor;
    private int                 versionMinor;
    private int                 screenWidth;
    private int                 screenHeight;
    private int                 applicationState;

    // @formatter:on

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: Application
    //
    // Description:
    //
    //   Default constructor. Delegates to the private initialize method to set up application settings,
    //   custom fonts, the global cache, and the graphics window.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Application ()
    {
        initialize ();
    }

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    // @formatter:off

    public BufferStrategy      getScreenBuffer  () { return this.screenBuffer;      }
    public Graphics2D          getGraphicsAPI   () { return this.graphicsAPI;       }
    public int                 getScreenWidth   () { return this.screenWidth;       }
    public int                 getScreenHeight  () { return this.screenHeight;      }
    public ApplicationSettings getSettings      () { return this.settings;          }
    public GlobalCache         getGlobalCache   () { return this.globalCache;       }
    public GraphicsWindow      getWindow        () { return this.applicationWindow; }

    // @formatter:on

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Private initialization sequence. Calls each subsystem initializer in order: application
    //   settings, custom fonts, global cache, and graphics window. Logs the final settings state.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        initializeApplicationSettings ();
        initializeCustomFonts         ();
        initializeGlobalCache         ();
        initializeGraphicsWindow      ();

        logger.log ( this.settings.toString () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initializeApplicationSettings
    //
    // Description:
    //
    //   Loads application settings from the properties file and extracts core configuration values
    //   (application name, version, screen dimensions, logging flag). Creates the ConsoleLogger
    //   instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initializeApplicationSettings ()
    {
        this.settings = new ApplicationSettings ( PathResolver.resolve ( Constants.SETTINGS_FILE ) );

        this.applicationName = this.settings.getString  ( Constants.KEY_APPLICATION_NAME );
        this.versionMajor    = this.settings.getInteger ( Constants.KEY_APPLICATION_VERSION_MAJOR );
        this.versionMinor    = this.settings.getInteger ( Constants.KEY_APPLICATION_VERSION_MINOR );
        this.screenWidth     = this.settings.getInteger ( Constants.KEY_APPLICATION_SCREEN_WIDTH );
        this.screenHeight    = this.settings.getInteger ( Constants.KEY_APPLICATION_SCREEN_HEIGHT );
        this.loggingEnabled  = this.settings.getBoolean ( Constants.KEY_APPLICATION_LOGGING_ENABLED );

        this.logger = new ConsoleLogger ( this, this.loggingEnabled );

        // Log to terminal.

        logger.log ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initializeCustomFonts
    //
    // Description:
    //
    //   Loads and registers custom TrueType and OpenType fonts from the Resources/Fonts directory
    //   into the local GraphicsEnvironment. Logs each successfully registered font.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initializeCustomFonts ()
    {
        try
        {
            // Resolve the fonts directory path. PathResolver handles differences between running in
            // the IDE (relative to project root) and running from a jpackage install.

            String fontPath = Constants.FONTS_DIR;
            File fontDir = PathResolver.resolveFile ( fontPath );

            if ( fontDir.exists () && fontDir.isDirectory () )
            {
                // Java only knows about fonts installed in the operating system's font directory
                // (e.g., C:\Windows\Fonts on Windows).
                //
                // - Any custom font files bundled with the application must be explicitly loaded and registered at
                //   runtime before they can be used by name in Font constructors or Graphics2D draw calls.
                //
                // - GraphicsEnvironment represents the collection of all fonts and display devices available to the
                //   JVM. Registering a font here makes it available application-wide, just as if it were installed on
                //   the system.

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();

                for ( File file : fontDir.listFiles () )
                {
                    String name = file.getName ().toLowerCase ();

                    // TrueType (.ttf) and OpenType (.otf) are the two standard font file formats.
                    // Java's Font.createFont () supports both under the TRUETYPE_FONT constant, despite the name, it
                    // handles OpenType files as well.

                    if ( name.endsWith ( ".ttf" ) || name.endsWith ( ".otf" ) )
                    {
                        // Font.createFont () reads the raw font file and returns a Font object set to size 1.
                        //
                        // - The font is not yet usable by name until registerFont () adds it to the graphics
                        //   environment's font registry.
                        //
                        // - After registration, the font's family and face names can be used anywhere (e.g., new Font
                        //   ("MyFont", Font.PLAIN, 14)).

                        Font font = Font.createFont ( Font.TRUETYPE_FONT, file );
                        ge.registerFont ( font );
                        logger.log ( "Registered font: " + font.getFontName () + " from " + file.getName () );
                    }
                }
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, false );
        }

        // Log to terminal.

        logger.log ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initializeGlobalCache
    //
    // Description:
    //
    //   Initializes the global cache and populates it with default particle counts for each color
    //   group (red, green, blue, yellow) from the application settings.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initializeGlobalCache ()
    {
        this.globalCache = new GlobalCache ();

        this.globalCache.put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_RED,    this.settings.getInteger ( Constants.KEY_PARTICLE_COUNT_RED_DEFAULT ) );
        this.globalCache.put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_GREEN,  this.settings.getInteger ( Constants.KEY_PARTICLE_COUNT_GREEN_DEFAULT ) );
        this.globalCache.put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_BLUE,   this.settings.getInteger ( Constants.KEY_PARTICLE_COUNT_BLUE_DEFAULT ) );
        this.globalCache.put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_YELLOW, this.settings.getInteger ( Constants.KEY_PARTICLE_COUNT_YELLOW_DEFAULT ) );

        // Log to terminal.

        logger.log ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initializeGraphicsWindow
    //
    // Description:
    //
    //   Creates the GraphicsWindow at the configured screen dimensions, sets the window title with
    //   the application name and version, and obtains the BufferStrategy and Graphics2D rendering
    //   context.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initializeGraphicsWindow ()
    {
        try
        {
            // Create the application window at the configured screen dimensions.

            this.applicationWindow = new GraphicsWindow ( this.screenWidth, this.screenHeight );

            // Build the window title string from the application name and version number, then apply it to the title bar.

            String title = this.applicationName + " (Version " + this.versionMajor + "." + this.versionMinor + ")";
            this.applicationWindow.setTitle ( title );

            // Obtain the double-buffered screen buffer and its Graphics2D rendering context.
            // All drawing operations will go through this graphics API.

            this.screenBuffer = this.applicationWindow.getScreenBuffer ();
            this.graphicsAPI  = (Graphics2D) this.screenBuffer.getDrawGraphics ();
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }

        // Log to terminal.

        logger.log ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: run
    //
    // Description:
    //
    //   Runs the top-level application state machine. Cycles through states (STARTING, MENU_MAIN,
    //   PARTICLE_SIMULATION, STOPPING) until the application reaches IDLE, then disposes the
    //   graphics window.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @ SuppressWarnings ( "incomplete-switch" )
    public void run ()
    {
        // Log to terminal.

        logger.log ();

        // Set the initial application state to STARTING, which triggers the state machine's first transition.

        this.applicationState = Constants.APPLICATION_STATE_STARTING;

        // Run the main application loop. Each iteration processes the current state and transitions to the next.
        // The loop exits when the application reaches the IDLE state.

        while ( this.applicationState != Constants.APPLICATION_STATE_IDLE )
        {
            switch ( this.applicationState )
            {
                case Constants.APPLICATION_STATE_STARTING:
                {
                    logger.log ( "State = STARTING" );
                    this.applicationState = Constants.APPLICATION_STATE_MENU_MAIN;
                    break;
                }

                case Constants.APPLICATION_STATE_MENU_MAIN:
                {
                    logger.log ( "State = MENU_MAIN" );
                    runMenu ();
                    break;
                }

                case Constants.APPLICATION_STATE_LEVEL_PARTICLE_SIMULATION:
                {
                    logger.log ( "State = PARTICLE_SIMULATION" );
                    runSimulation ();
                    break;
                }

                case Constants.APPLICATION_STATE_STOPPING:
                {
                    logger.log ( "State = STOPPING" );
                    this.applicationState = Constants.APPLICATION_STATE_IDLE;
                    break;
                }
            }
        }

        // The state machine has exited. Log the final state before cleanup.

        logger.log ( "State = EXIT" );

        // Dispose of the graphics window to release native display resources and allow the JVM to shut down cleanly.

        this.applicationWindow.dispose ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: runMenu
    //
    // Description:
    //
    //   Creates and runs the EngineMenu instance.
    //
    //   - After the menu engine exits, reads the next application state from the GlobalCache.
    //
    //   - Falls back to STOPPING if no state is found or an exception occurs.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void runMenu ()
    {
        try
        {
            // Update the window title bar to indicate the menu is active.

            this.applicationWindow.setTitle ( this.applicationName + " - Menu" );

            // Create and run the menu engine. Control returns here once the user makes a selection or exits the menu.

            EngineMenu engineMenu = new EngineMenu ( this );
            engineMenu.run ();

            // Read the next application state from the global cache. The menu engine stores its result there before
            // exiting. If no state was written, default to STOPPING to gracefully shut down.

            if ( this.globalCache.contains ( Constants.GLOBAL_CACHE_APPLICATION_STATE ) )
            {
                this.applicationState = (int) this.globalCache.get ( Constants.GLOBAL_CACHE_APPLICATION_STATE );
            }
            else
            {
                this.applicationState = Constants.APPLICATION_STATE_STOPPING;
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
            this.applicationState = Constants.APPLICATION_STATE_STOPPING;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: runSimulation
    //
    // Description:
    //
    //   Creates and runs the EngineParticleSimulator instance.
    //
    //   - After the simulation engine exits, reads the next application state from the GlobalCache.
    //
    //   - Falls back to STOPPING if no state is found or an exception occurs.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void runSimulation ()
    {
        try
        {
            // Update the window title bar to indicate the particle simulation is active.

            this.applicationWindow.setTitle ( this.applicationName + " - Particle Simulation" );

            // Create and run the particle simulation engine. Control returns here once the simulation exits.

            EngineParticleSimulator engineSimulator = new EngineParticleSimulator ( this );
            engineSimulator.run ();

            // Read the next application state from the global cache. The simulation engine stores its result there
            // before exiting. If no state was written, default to STOPPING to gracefully shut down.

            if ( this.globalCache.contains ( Constants.GLOBAL_CACHE_APPLICATION_STATE ) )
            {
                this.applicationState = (int) this.globalCache.get ( Constants.GLOBAL_CACHE_APPLICATION_STATE );
            }
            else
            {
                this.applicationState = Constants.APPLICATION_STATE_STOPPING;
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
            this.applicationState = Constants.APPLICATION_STATE_STOPPING;
        }
    }
}
