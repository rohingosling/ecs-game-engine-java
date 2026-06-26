//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the EventManager class. Verifies caching, decaching, registration, deregistration,
//   posting, dispatching, flushing, clearing, comparator ordering, and reset behaviour.
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
// Class: EventManagerTest
//
// Description:
//
//   JUnit 5 test class for the EventManager. Uses a TestListener inner class as a test double to capture
//   dispatched events in a log, enabling verification of event dispatch behaviour across all EventManager
//   operations.
//
//*********************************************************************************************************************

public class EventManagerTest
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private EventManager eventManager;
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
    //   Initialises the EventManager and dispatch log before each test.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @BeforeEach
    void setUp ()
    {
        eventManager = new EventManager ();
        dispatchLog = new ArrayList <> ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: cache_addsEventToCache
    //
    // Description:
    //
    //   Verifies that caching an event makes it available for posting and retrieval.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void cache_addsEventToCache ()
    {
        Event e = new Event ( "TestEvent" );
        eventManager.cache ( e );
        Event posted = eventManager.post ( "TestEvent" );
        assertNotNull ( posted );
        assertEquals ( "TestEvent", posted.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: cache_duplicateName_doesNotOverwrite
    //
    // Description:
    //
    //   Verifies that caching an event with a duplicate name does not overwrite the original event.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void cache_duplicateName_doesNotOverwrite ()
    {
        Event e1 = new Event ( "Dup", "payload1" );
        Event e2 = new Event ( "Dup", "payload2" );
        eventManager.cache ( e1 );
        eventManager.cache ( e2 );
        Event posted = eventManager.post ( "Dup" );
        assertEquals ( "payload1", posted.getPayload () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: decache_removesEvent
    //
    // Description:
    //
    //   Verifies that decaching an event removes it from the cache, causing subsequent posts to fail.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void decache_removesEvent ()
    {
        Event e = new Event ( "ToRemove" );
        eventManager.cache ( e );
        eventManager.decache ( "ToRemove" );
        assertThrows ( NullPointerException.class, () -> eventManager.post ( "ToRemove" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: decache_nonExistent_doesNotThrow
    //
    // Description:
    //
    //   Verifies that decaching a non-existent event does not throw an exception.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void decache_nonExistent_doesNotThrow ()
    {
        assertDoesNotThrow ( () -> eventManager.decache ( "NonExistent" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: register_attachesListenerToEvent
    //
    // Description:
    //
    //   Verifies that a registered listener receives event dispatch notifications.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void register_attachesListenerToEvent ()
    {
        Event e = new Event ( "Click" );
        eventManager.cache ( e );
        TestListener listener = new TestListener ( "L1" );
        eventManager.register ( listener, "Click" );
        eventManager.post ( "Click" );
        eventManager.flush ();
        assertTrue ( dispatchLog.contains ( "L1:Click" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: register_returnsEvent
    //
    // Description:
    //
    //   Verifies that the register method returns the event the listener was registered with.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void register_returnsEvent ()
    {
        Event e = new Event ( "Click" );
        eventManager.cache ( e );
        Event returned = eventManager.register ( new TestListener ( "L1" ), "Click" );
        assertSame ( e, returned );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: deregister_removesListenerFromEvent
    //
    // Description:
    //
    //   Verifies that a deregistered listener no longer receives event dispatch notifications.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void deregister_removesListenerFromEvent ()
    {
        Event e = new Event ( "Click" );
        eventManager.cache ( e );
        TestListener listener = new TestListener ( "L1" );
        eventManager.register ( listener, "Click" );
        eventManager.deregister ( listener, "Click" );
        eventManager.post ( "Click" );
        eventManager.flush ();
        assertTrue ( dispatchLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: deregister_returnsEvent
    //
    // Description:
    //
    //   Verifies that the deregister method returns the event the listener was deregistered from.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void deregister_returnsEvent ()
    {
        Event e = new Event ( "Click" );
        eventManager.cache ( e );
        TestListener listener = new TestListener ( "L1" );
        eventManager.register ( listener, "Click" );
        Event returned = eventManager.deregister ( listener, "Click" );
        assertSame ( e, returned );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: post_setsTimestamp
    //
    // Description:
    //
    //   Verifies that posting an event sets its timestamp to the current system time.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void post_setsTimestamp ()
    {
        Event e = new Event ( "Tick" );
        eventManager.cache ( e );
        long before = System.currentTimeMillis ();
        Event posted = eventManager.post ( "Tick" );
        long after = System.currentTimeMillis ();
        assertTrue ( posted.getTime () >= before );
        assertTrue ( posted.getTime () <= after );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: post_returnsEvent
    //
    // Description:
    //
    //   Verifies that the post method returns the event that was posted.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void post_returnsEvent ()
    {
        Event e = new Event ( "Tick" );
        eventManager.cache ( e );
        Event posted = eventManager.post ( "Tick" );
        assertSame ( e, posted );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dispatchAll_dispatchesAllPostedEvents
    //
    // Description:
    //
    //   Verifies that dispatchAll dispatches all events currently in the queue.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dispatchAll_dispatchesAllPostedEvents ()
    {
        Event e1 = new Event ( "A" );
        Event e2 = new Event ( "B" );
        eventManager.cache ( e1 );
        eventManager.cache ( e2 );
        eventManager.register ( new TestListener ( "L1" ), "A" );
        eventManager.register ( new TestListener ( "L2" ), "B" );
        eventManager.post ( "A" );
        eventManager.post ( "B" );
        eventManager.dispatchAll ();
        assertEquals ( 2, dispatchLog.size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dispatchAll_doesNotClearQueue
    //
    // Description:
    //
    //   Verifies that dispatchAll does not clear the event queue after dispatching.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dispatchAll_doesNotClearQueue ()
    {
        Event e = new Event ( "A" );
        eventManager.cache ( e );
        eventManager.register ( new TestListener ( "L1" ), "A" );
        eventManager.post ( "A" );
        eventManager.dispatchAll ();
        dispatchLog.clear ();
        eventManager.dispatchAll ();
        assertEquals ( 1, dispatchLog.size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush_dispatchesAndClearsQueue
    //
    // Description:
    //
    //   Verifies that flush dispatches all events and then clears the queue.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void flush_dispatchesAndClearsQueue ()
    {
        Event e = new Event ( "A" );
        eventManager.cache ( e );
        eventManager.register ( new TestListener ( "L1" ), "A" );
        eventManager.post ( "A" );
        eventManager.flush ();
        assertEquals ( 1, dispatchLog.size () );
        dispatchLog.clear ();
        eventManager.flush ();
        assertTrue ( dispatchLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush_emptyQueue_doesNothing
    //
    // Description:
    //
    //   Verifies that flushing an empty queue does not throw an exception.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void flush_emptyQueue_doesNothing ()
    {
        assertDoesNotThrow ( () -> eventManager.flush () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearEventQueue_removesAllPostedEvents
    //
    // Description:
    //
    //   Verifies that clearEventQueue removes all posted events from the queue.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clearEventQueue_removesAllPostedEvents ()
    {
        Event e = new Event ( "A" );
        eventManager.cache ( e );
        eventManager.register ( new TestListener ( "L1" ), "A" );
        eventManager.post ( "A" );
        eventManager.clearEventQueue ();
        eventManager.dispatchAll ();
        assertTrue ( dispatchLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearEventCache_removesAllCachedEvents
    //
    // Description:
    //
    //   Verifies that clearEventCache removes all cached events, causing subsequent posts to fail.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clearEventCache_removesAllCachedEvents ()
    {
        Event e = new Event ( "A" );
        eventManager.cache ( e );
        eventManager.clearEventCache ();
        assertThrows ( NullPointerException.class, () -> eventManager.post ( "A" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: compare_earlierTime_returnsNegative
    //
    // Description:
    //
    //   Verifies that the comparator returns a negative value when event a has an earlier timestamp.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void compare_earlierTime_returnsNegative ()
    {
        Event a = new Event ( "A" );
        Event b = new Event ( "B" );
        a.setTime ( 100 );
        b.setTime ( 200 );
        assertTrue ( eventManager.compare ( a, b ) < 0 );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: compare_laterTime_returnsPositive
    //
    // Description:
    //
    //   Verifies that the comparator returns a positive value when event a has a later timestamp.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void compare_laterTime_returnsPositive ()
    {
        Event a = new Event ( "A" );
        Event b = new Event ( "B" );
        a.setTime ( 200 );
        b.setTime ( 100 );
        assertTrue ( eventManager.compare ( a, b ) > 0 );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: compare_sameTime_returnsZero
    //
    // Description:
    //
    //   Verifies that the comparator returns zero when both events have the same timestamp.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void compare_sameTime_returnsZero ()
    {
        Event a = new Event ( "A" );
        Event b = new Event ( "B" );
        a.setTime ( 100 );
        b.setTime ( 100 );
        assertEquals ( 0, eventManager.compare ( a, b ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: reset_clearsAll
    //
    // Description:
    //
    //   Verifies that reset clears both the event cache and event queue.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void reset_clearsAll ()
    {
        Event e = new Event ( "A" );
        eventManager.cache ( e );
        eventManager.post ( "A" );
        eventManager.reset ();
        assertThrows ( NullPointerException.class, () -> eventManager.post ( "A" ) );
    }
}
