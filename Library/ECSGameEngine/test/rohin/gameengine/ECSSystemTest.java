//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the ECSSystem class. Validates constructor overloads, default values, enabled state management,
//   and the update callback mechanism.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: ECSSystemTest
//
// Description:
//
//   JUnit 5 test suite for the ECSSystem class. Uses a TestSystem inner class to provide a concrete implementation
//   of the abstract update method, enabling verification of constructor behavior, enabled state toggling, and
//   update invocations.
//
//*********************************************************************************************************************

public class ECSSystemTest
{
    //*****************************************************************************************************************
    // Class: TestSystem
    //
    // Description:
    //
    //   Concrete test double extending ECSSystem. Records the last update time and total update count to enable
    //   verification of update call behavior.
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
        // Constructor 1/3: TestSystem
        //
        // Description:
        //
        //   Default constructor. Delegates to the parent ECSSystem default constructor.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestSystem ()                          { super (); }

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 2/3: TestSystem
        //
        // Description:
        //
        //   Constructs a TestSystem with the specified identifier.
        //
        // Arguments:
        //
        //   id (Integer):
        //     The unique identifier for this system.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestSystem ( Integer id )              { super ( id ); }

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 3/3: TestSystem
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

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: defaultConstructor_setsDefaults
    //
    // Description:
    //
    //   Verifies that the default constructor initializes id to 0, name to "SYSTEM", and enabled to true.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultConstructor_setsDefaults ()
    {
        TestSystem s = new TestSystem ();
        assertEquals ( 0, s.getId () );
        assertEquals ( "SYSTEM", s.getName () );
        assertTrue ( s.isEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: idConstructor_setsId
    //
    // Description:
    //
    //   Verifies that the id-only constructor sets the system identifier.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void idConstructor_setsId ()
    {
        TestSystem s = new TestSystem ( 5 );
        assertEquals ( 5, s.getId () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: idNameConstructor_setsIdAndName
    //
    // Description:
    //
    //   Verifies that the (id, name) constructor sets both the identifier and name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void idNameConstructor_setsIdAndName ()
    {
        TestSystem s = new TestSystem ( 3, "Physics" );
        assertEquals ( 3, s.getId () );
        assertEquals ( "Physics", s.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: enabled_defaultTrue
    //
    // Description:
    //
    //   Verifies that a newly constructed system is enabled by default.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void enabled_defaultTrue ()
    {
        TestSystem s = new TestSystem ();
        assertTrue ( s.isEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setEnabled_false_disablesSystem
    //
    // Description:
    //
    //   Verifies that setEnabled(false) disables the system.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setEnabled_false_disablesSystem ()
    {
        TestSystem s = new TestSystem ();
        s.setEnabled ( false );
        assertFalse ( s.isEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setEnabled_reEnable
    //
    // Description:
    //
    //   Verifies that a disabled system can be re-enabled.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setEnabled_reEnable ()
    {
        TestSystem s = new TestSystem ();
        s.setEnabled ( false );
        s.setEnabled ( true );
        assertTrue ( s.isEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: update_receivesTimeParameter
    //
    // Description:
    //
    //   Verifies that the update method receives and records the time parameter correctly.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void update_receivesTimeParameter ()
    {
        TestSystem s = new TestSystem ();
        s.update ( 16L );
        assertEquals ( 16L, s.lastUpdateTime );
        assertEquals ( 1, s.updateCount );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: update_multipleCallsIncrementCount
    //
    // Description:
    //
    //   Verifies that multiple update calls correctly increment the count and record the latest timestamp.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void update_multipleCallsIncrementCount ()
    {
        TestSystem s = new TestSystem ();
        s.update ( 10L );
        s.update ( 20L );
        s.update ( 30L );
        assertEquals ( 3, s.updateCount );
        assertEquals ( 30L, s.lastUpdateTime );
    }
}
