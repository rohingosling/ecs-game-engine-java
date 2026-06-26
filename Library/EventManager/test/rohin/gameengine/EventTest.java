//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the Event class. Verifies constructor behaviour, accessor and mutator operations,
//   listener registration, deregistration, dispatch notification, clear, and reset functionality.
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
// Class: EventTest
//
// Description:
//
//   JUnit 5 test class for the Event class. Uses a TestListener inner class as a test double to capture
//   dispatched events in a log, enabling verification of event dispatch, registration, and lifecycle
//   behaviour.
//
//*********************************************************************************************************************

public class EventTest
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private List <String> dispatchLog;

    //*****************************************************************************************************************
    // Class: TestListener
    //
    // Description:
    //
    //   A test double that implements IEventListener. Records dispatched event names prefixed with a label
    //   into the outer class dispatch log for assertion purposes.
    //
    //*****************************************************************************************************************

    private class TestListener implements IEventListener
    {
        //=============================================================================================================
        // Fields
        //=============================================================================================================

        private String label;

        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/1: TestListener
        //
        // Description:
        //
        //   Constructs a TestListener with the specified label. The label is prepended to dispatched event names
        //   in the dispatch log.
        //
        // Arguments:
        //
        //   label (String):
        //     A string label used to identify this listener in the dispatch log.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestListener ( String label )
        {
            this.label = label;
        }

        //=============================================================================================================
        // Methods
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Method: OnEvent
        //
        // Description:
        //
        //   Handles dispatched events by appending the listener label and event name to the dispatch log.
        //
        // Arguments:
        //
        //   event (Event):
        //     The dispatched event.
        //
        //-------------------------------------------------------------------------------------------------------------

        @Override
        public void OnEvent ( Event event )
        {
            dispatchLog.add ( label + ":" + event.getName () );
        }
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setUp
    //
    // Description:
    //
    //   Initialises the dispatch log before each test.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @BeforeEach
    void setUp ()
    {
        dispatchLog = new ArrayList <> ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: nameConstructor_setsNameAndNullPayload
    //
    // Description:
    //
    //   Verifies that the single-argument constructor sets the name and leaves the payload null with a zero
    //   timestamp.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void nameConstructor_setsNameAndNullPayload ()
    {
        Event e = new Event ( "TestEvent" );
        assertEquals ( "TestEvent", e.getName () );
        assertNull ( e.getPayload () );
        assertEquals ( 0, e.getTime () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: fullConstructor_setsNameAndPayload
    //
    // Description:
    //
    //   Verifies that the two-argument constructor sets both the name and the payload.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void fullConstructor_setsNameAndPayload ()
    {
        Object payload = "data";
        Event e = new Event ( "TestEvent", payload );
        assertEquals ( "TestEvent", e.getName () );
        assertEquals ( "data", e.getPayload () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setName_updatesName
    //
    // Description:
    //
    //   Verifies that setName updates the event name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setName_updatesName ()
    {
        Event e = new Event ( "OldName" );
        e.setName ( "NewName" );
        assertEquals ( "NewName", e.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setPayload_updatesPayload
    //
    // Description:
    //
    //   Verifies that setPayload updates the event payload.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setPayload_updatesPayload ()
    {
        Event e = new Event ( "Ev" );
        e.setPayload ( 42 );
        assertEquals ( 42, e.getPayload () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setTime_updatesTime
    //
    // Description:
    //
    //   Verifies that setTime updates the event timestamp.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setTime_updatesTime ()
    {
        Event e = new Event ( "Ev" );
        e.setTime ( 123456L );
        assertEquals ( 123456L, e.getTime () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dispatch_notifiesRegisteredListener
    //
    // Description:
    //
    //   Verifies that dispatching an event notifies a single registered listener.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dispatch_notifiesRegisteredListener ()
    {
        Event e = new Event ( "Click" );
        e.register ( new TestListener ( "L1" ) );
        e.dispatch ();
        assertEquals ( 1, dispatchLog.size () );
        assertEquals ( "L1:Click", dispatchLog.get ( 0 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dispatch_notifiesMultipleListeners
    //
    // Description:
    //
    //   Verifies that dispatching an event notifies all registered listeners.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dispatch_notifiesMultipleListeners ()
    {
        Event e = new Event ( "Click" );
        e.register ( new TestListener ( "L1" ) );
        e.register ( new TestListener ( "L2" ) );
        e.dispatch ();
        assertEquals ( 2, dispatchLog.size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dispatch_noListeners_doesNothing
    //
    // Description:
    //
    //   Verifies that dispatching an event with no registered listeners does not throw an exception.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dispatch_noListeners_doesNothing ()
    {
        Event e = new Event ( "Click" );
        assertDoesNotThrow ( () -> e.dispatch () );
        assertTrue ( dispatchLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: deregister_removesListener
    //
    // Description:
    //
    //   Verifies that a deregistered listener no longer receives dispatch notifications.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void deregister_removesListener ()
    {
        Event e = new Event ( "Click" );
        TestListener listener = new TestListener ( "L1" );
        e.register ( listener );
        e.deregister ( listener );
        e.dispatch ();
        assertTrue ( dispatchLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: deregister_onlyRemovesSpecifiedListener
    //
    // Description:
    //
    //   Verifies that deregistering one listener does not affect other registered listeners.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void deregister_onlyRemovesSpecifiedListener ()
    {
        Event e = new Event ( "Click" );
        TestListener l1 = new TestListener ( "L1" );
        TestListener l2 = new TestListener ( "L2" );
        e.register ( l1 );
        e.register ( l2 );
        e.deregister ( l1 );
        e.dispatch ();
        assertEquals ( 1, dispatchLog.size () );
        assertEquals ( "L2:Click", dispatchLog.get ( 0 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clear_removesAllListeners
    //
    // Description:
    //
    //   Verifies that clear removes all registered listeners from the event.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clear_removesAllListeners ()
    {
        Event e = new Event ( "Click" );
        e.register ( new TestListener ( "L1" ) );
        e.register ( new TestListener ( "L2" ) );
        e.clear ();
        e.dispatch ();
        assertTrue ( dispatchLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: reset_clearsAllState
    //
    // Description:
    //
    //   Verifies that reset clears the name, payload, timestamp, and all registered listeners.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void reset_clearsAllState ()
    {
        Event e = new Event ( "Click", "payload" );
        e.setTime ( 999L );
        e.register ( new TestListener ( "L1" ) );
        e.reset ( "NewEvent", null );
        assertEquals ( "NewEvent", e.getName () );
        assertNull ( e.getPayload () );
        assertEquals ( 0, e.getTime () );
        e.dispatch ();
        assertTrue ( dispatchLog.isEmpty () );
    }
}
