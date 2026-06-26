//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the ECSEngine class. Validates engine initialization, system and entity management, game loop
//   execution, command flushing, and the printGameObjects diagnostic output.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

//*********************************************************************************************************************
// Class: ECSEngineTest
//
// Description:
//
//   JUnit 5 test suite for the ECSEngine class. Uses inner test doubles (TestEngine, TestSystem, StopSystem,
//   ComponentA, TestCommand) to verify engine initialization, system update dispatch, game loop termination,
//   command queue flushing, and diagnostic output formatting.
//
//*********************************************************************************************************************

public class ECSEngineTest
{
    //*****************************************************************************************************************
    // Class: TestEngine
    //
    // Description:
    //
    //   Concrete test double extending ECSEngine. Provides a swapBuffer implementation that counts invocations
    //   and disables logging for clean test output.
    //
    //*****************************************************************************************************************

    private static class TestEngine extends ECSEngine
    {
        //=============================================================================================================
        // Fields
        //=============================================================================================================

        public int swapBufferCount = 0;

        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/1: TestEngine
        //
        // Description:
        //
        //   Default constructor. Disables engine and command manager logging for test isolation.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestEngine ()
        {
            super ();
            setloggingEnabled ( false );
            getCommandManager ().setLoggingEnabled ( false );
        }

        //=============================================================================================================
        // Methods
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Method: swapBuffer
        //
        // Description:
        //
        //   Increments the swap buffer counter instead of performing an actual buffer swap.
        //
        //-------------------------------------------------------------------------------------------------------------

        @Override
        protected void swapBuffer ()
        {
            swapBufferCount++;
        }
    }

    //*****************************************************************************************************************
    // Class: TestSystem
    //
    // Description:
    //
    //   Test double extending ECSSystem. Records the last update timestamp and total update count for verification.
    //
    //*****************************************************************************************************************

    private static class TestSystem extends ECSSystem
    {
        //=============================================================================================================
        // Fields
        //=============================================================================================================

        public long lastUpdateTime = -1;
        public int  updateCount    = 0;

        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/2: TestSystem
        //
        // Description:
        //
        //   Default constructor. Delegates to the parent ECSSystem default constructor.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestSystem ()                          { super (); }

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 2/2: TestSystem
        //
        // Description:
        //
        //   Constructs a TestSystem with the specified identifier and name.
        //
        // Arguments:
        //
        //   id (Integer):
        //     The unique identifier for this system.
        //
        //   name (String):
        //     The display name for this system.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestSystem ( Integer id, String name ) { super ( id, name ); }

        //=============================================================================================================
        // Methods
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Method: update
        //
        // Description:
        //
        //   Records the timestamp and increments the update count for test verification.
        //
        // Arguments:
        //
        //   t (long):
        //     The current time in milliseconds.
        //
        //-------------------------------------------------------------------------------------------------------------

        @Override
        public void update ( long t )
        {
            lastUpdateTime = t;
            updateCount++;
        }
    }

    //*****************************************************************************************************************
    // Class: StopSystem
    //
    // Description:
    //
    //   Test double extending ECSSystem that stops the engine's game loop on its first update call, enabling
    //   deterministic loop termination in tests.
    //
    //*****************************************************************************************************************

    private static class StopSystem extends ECSSystem
    {
        //=============================================================================================================
        // Fields
        //=============================================================================================================

        private ECSEngine engine;

        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/1: StopSystem
        //
        // Description:
        //
        //   Constructs a StopSystem that will stop the specified engine on its first update.
        //
        // Arguments:
        //
        //   engine (ECSEngine):
        //     The engine whose game loop will be stopped.
        //
        //-------------------------------------------------------------------------------------------------------------

        public StopSystem ( ECSEngine engine )
        {
            super ();
            this.engine = engine;
        }

        //=============================================================================================================
        // Methods
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Method: update
        //
        // Description:
        //
        //   Stops the engine's game loop by setting loopRunning to false.
        //
        // Arguments:
        //
        //   t (long):
        //     The current time in milliseconds (unused).
        //
        //-------------------------------------------------------------------------------------------------------------

        @Override
        public void update ( long t )
        {
            engine.setLoopRunning ( false );
        }
    }

    //*****************************************************************************************************************
    // Class: ComponentA
    //
    // Description:
    //
    //   Minimal test double component subclass used for verifying entity-component relationships in engine tests.
    //
    //*****************************************************************************************************************

    private static class ComponentA extends ECSComponent
    {
        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/2: ComponentA
        //
        // Description:
        //
        //   Default constructor. Delegates to the parent ECSComponent default constructor.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentA ()         { super (); }

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 2/2: ComponentA
        //
        // Description:
        //
        //   Constructs a ComponentA with the specified identifier.
        //
        // Arguments:
        //
        //   id (int):
        //     The unique identifier for this component.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentA ( int id ) { super ( id ); }
    }

    //*****************************************************************************************************************
    // Class: TestCommand
    //
    // Description:
    //
    //   Test double implementing ICommand. Records its label into a shared log list upon execution, enabling
    //   verification that the engine flushes commands during the game loop.
    //
    //*****************************************************************************************************************

    private static class TestCommand implements ICommand
    {
        //=============================================================================================================
        // Fields
        //=============================================================================================================

        private List <String> log;
        private String label;

        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/1: TestCommand
        //
        // Description:
        //
        //   Constructs a TestCommand that appends its label to the specified log when executed.
        //
        // Arguments:
        //
        //   log (List <String>):
        //     The shared log list to append the label to upon execution.
        //
        //   label (String):
        //     The identifier recorded in the log when this command executes.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestCommand ( List <String> log, String label )
        {
            this.log = log;
            this.label = label;
        }

        //=============================================================================================================
        // Methods
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Method: execute
        //
        // Description:
        //
        //   Appends this command's label to the shared log list.
        //
        //-------------------------------------------------------------------------------------------------------------

        @Override
        public void execute ()
        {
            log.add ( label );
        }
    }

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private TestEngine engine;

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setUp
    //
    // Description:
    //
    //   Initializes a fresh TestEngine instance before each test.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @BeforeEach
    void setUp ()
    {
        engine = new TestEngine ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: constructor_initializesDefaults
    //
    // Description:
    //
    //   Verifies that the engine initializes with non-null systems, entities, command manager, resource manager,
    //   and loopRunning set to true.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void constructor_initializesDefaults ()
    {
        assertNotNull ( engine.getSystems () );
        assertNotNull ( engine.getEntities () );
        assertNotNull ( engine.getCommandManager () );
        assertNotNull ( engine.getResourceManager () );
        assertTrue ( engine.isLoopRunning () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: defaultFPSTarget
    //
    // Description:
    //
    //   Verifies that the default FPS target is 90.0.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultFPSTarget ()
    {
        assertEquals ( 90.0, engine.getFPSTarget (), 1e-9 );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: defaultFPSTargetEnabled
    //
    // Description:
    //
    //   Verifies that FPS target limiting is enabled by default.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultFPSTargetEnabled ()
    {
        assertTrue ( engine.isFPSTargetEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: defaultLoopDelayMin
    //
    // Description:
    //
    //   Verifies that the default minimum loop delay is 5 milliseconds.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultLoopDelayMin ()
    {
        assertEquals ( 5, engine.getLoopDelayMin () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addSystem_and_getSystems
    //
    // Description:
    //
    //   Verifies that a system added with addSystem can be retrieved from the systems map.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addSystem_and_getSystems ()
    {
        TestSystem s = new TestSystem ( 1, "Physics" );
        engine.addSystem ( 1, s );
        assertEquals ( 1, engine.getSystems ().size () );
        assertSame ( s, engine.getSystems ().get ( 1 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addSystem_multipleSystems
    //
    // Description:
    //
    //   Verifies that multiple systems can be added to the engine.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addSystem_multipleSystems ()
    {
        engine.addSystem ( 1, new TestSystem ( 1, "Physics" ) );
        engine.addSystem ( 2, new TestSystem ( 2, "Render" ) );
        assertEquals ( 2, engine.getSystems ().size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addEntity_and_getEntity
    //
    // Description:
    //
    //   Verifies that an entity added with addEntity can be retrieved with getEntity.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addEntity_and_getEntity ()
    {
        ECSEntity entity = new ECSEntity ( 1, "Player" );
        engine.addEntity ( 1, entity );
        assertSame ( entity, engine.getEntity ( 1 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getEntity_nonExistent_returnsNull
    //
    // Description:
    //
    //   Verifies that getEntity returns null for a non-existent key.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void getEntity_nonExistent_returnsNull ()
    {
        assertNull ( engine.getEntity ( 999 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addEntity_multipleEntities
    //
    // Description:
    //
    //   Verifies that multiple entities can be added to the engine.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addEntity_multipleEntities ()
    {
        engine.addEntity ( 1, new ECSEntity ( 1, "Player" ) );
        engine.addEntity ( 2, new ECSEntity ( 2, "Enemy" ) );
        assertEquals ( 2, engine.getEntities ().size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setLoopRunning
    //
    // Description:
    //
    //   Verifies that setLoopRunning(false) stops the game loop flag.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setLoopRunning ()
    {
        engine.setLoopRunning ( false );
        assertFalse ( engine.isLoopRunning () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setFPSTarget
    //
    // Description:
    //
    //   Verifies that setFPSTarget updates the FPS target value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setFPSTarget ()
    {
        engine.setFPSTarget ( 60.0 );
        assertEquals ( 60.0, engine.getFPSTarget (), 1e-9 );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setFPSTargetEnabled
    //
    // Description:
    //
    //   Verifies that setFPSTargetEnabled(false) disables FPS target limiting.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setFPSTargetEnabled ()
    {
        engine.setFPSTargetEnabled ( false );
        assertFalse ( engine.isFPSTargetEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setLoopDelayFixed
    //
    // Description:
    //
    //   Verifies that setLoopDelayFixed updates the fixed loop delay value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setLoopDelayFixed ()
    {
        engine.setLoopDelayFixed ( 500 );
        assertEquals ( 500, engine.getLoopDelayFixed () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setLoopDelayMin
    //
    // Description:
    //
    //   Verifies that setLoopDelayMin updates the minimum loop delay value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setLoopDelayMin ()
    {
        engine.setLoopDelayMin ( 10 );
        assertEquals ( 10, engine.getLoopDelayMin () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setResourcePath
    //
    // Description:
    //
    //   Verifies that setResourcePath updates the resource path string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setResourcePath ()
    {
        engine.setResourcePath ( "/assets" );
        assertEquals ( "/assets", engine.getResourcePath () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setCommandManager
    //
    // Description:
    //
    //   Verifies that setCommandManager replaces the engine's command manager instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setCommandManager ()
    {
        CommandManager cm = new CommandManager ();
        engine.setCommandManager ( cm );
        assertSame ( cm, engine.getCommandManager () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setResourceManager
    //
    // Description:
    //
    //   Verifies that setResourceManager replaces the engine's resource manager instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setResourceManager ()
    {
        ResourceManager rm = new ResourceManager ();
        engine.setResourceManager ( rm );
        assertSame ( rm, engine.getResourceManager () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: run_executesSystemUpdate
    //
    // Description:
    //
    //   Verifies that the engine's run loop dispatches update calls to registered systems.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void run_executesSystemUpdate ()
    {
        TestSystem sys = new TestSystem ( 1, "TestSys" );
        engine.addSystem ( 1, sys );
        engine.addSystem ( 2, new StopSystem ( engine ) );
        engine.run ();
        assertTrue ( sys.updateCount >= 1 );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: run_stopsWhenLoopRunningSetToFalse
    //
    // Description:
    //
    //   Verifies that the game loop terminates when loopRunning is set to false by a system.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void run_stopsWhenLoopRunningSetToFalse ()
    {
        engine.addSystem ( 1, new StopSystem ( engine ) );
        engine.run ();
        assertFalse ( engine.isLoopRunning () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: run_flushesCommandsDuringLoop
    //
    // Description:
    //
    //   Verifies that the engine flushes the command queue during the game loop iteration.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void run_flushesCommandsDuringLoop ()
    {
        List <String> log = new ArrayList <> ();
        engine.getCommandManager ().postCommand ( new TestCommand ( log, "CMD1" ) );
        engine.addSystem ( 1, new StopSystem ( engine ) );
        engine.run ();
        assertTrue ( log.contains ( "CMD1" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: printGameObjects_includesEngineName
    //
    // Description:
    //
    //   Verifies that printGameObjects output includes the engine's class name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void printGameObjects_includesEngineName ()
    {
        String output = engine.printGameObjects ();
        assertTrue ( output.contains ( "TestEngine" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: printGameObjects_includesSystemNames
    //
    // Description:
    //
    //   Verifies that printGameObjects output includes registered system class names.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void printGameObjects_includesSystemNames ()
    {
        engine.addSystem ( 1, new TestSystem ( 1, "Physics" ) );
        String output = engine.printGameObjects ();
        assertTrue ( output.contains ( "TestSystem" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: printGameObjects_includesEntityNames
    //
    // Description:
    //
    //   Verifies that printGameObjects output includes registered entity class names.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void printGameObjects_includesEntityNames ()
    {
        ECSEntity entity = new ECSEntity ( 1, "Player" );
        engine.addEntity ( 1, entity );
        String output = engine.printGameObjects ();
        assertTrue ( output.contains ( "ECSEntity" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: printGameObjects_includesComponentNames
    //
    // Description:
    //
    //   Verifies that printGameObjects output includes component class names attached to entities.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void printGameObjects_includesComponentNames ()
    {
        ECSEntity entity = new ECSEntity ( 1, "Player" );
        entity.addComponent ( 1, new ComponentA ( 1 ) );
        engine.addEntity ( 1, entity );
        String output = engine.printGameObjects ();
        assertTrue ( output.contains ( "ComponentA" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: printGameObjects_emptyEngine
    //
    // Description:
    //
    //   Verifies that printGameObjects produces valid output with Systems and Entities sections even when the
    //   engine is empty.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void printGameObjects_emptyEngine ()
    {
        String output = engine.printGameObjects ();
        assertNotNull ( output );
        assertTrue ( output.contains ( "Systems" ) );
        assertTrue ( output.contains ( "Entities" ) );
    }
}
