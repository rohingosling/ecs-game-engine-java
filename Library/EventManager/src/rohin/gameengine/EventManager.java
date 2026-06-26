//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the EventManager class, a generic event management system used to manage instances of the Event
//   class.
// 
//   Provides an event cache for pre-loaded events, a priority queue for dispatching events in chronological order, 
//   and methods for caching, registering, posting, and dispatching events.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

//*********************************************************************************************************************
// Class: EventManager
//
// Description:
//
//   EventManager is a generic event management class used to manage instances of the Event class.
//
//   Features:
//
//   - An event cache, used to store pre-loaded events for quick retrieval and event validation.
//
//   - A primary event dispatching queue, backed by a PriorityQueue that orders events by timestamp.
//
//   - Ability to cache, decache, register, and deregister events.
//
//   - A dispatchAll method that dispatches all events in the queue without clearing it. This is useful
//     for situations where one may wish to re-dispatch all events multiple times.
//
//   - A flush method used for dispatching all events and then clearing the event queue after all events
//     have been dispatched.
//
//*********************************************************************************************************************

class EventManager implements Comparator <Event>
{
    //@formatter:off

    //=================================================================================================================
    // Constants
    //=================================================================================================================

    private static final int M_DEFAULT_INITIAL_EVENT_CACHE_CAPACITY = 100;
    private static final int M_DEFAULT_INITIAL_EVENT_QUEUE_CAPACITY = 1000;

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private HashMap       <String, Event> eventCache;                   // Stores cached events for quick retrieval and validation.
    private PriorityQueue <Event>         eventQueue;                   // Events posted to the event queue are ready to be dispatched.
    private int                           initialEventQueueCapacity;    // Initial event queue capacity.
    private int                           initialEventCacheCapacity;    // Initial event cache capacity.

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: EventManager
    //
    // Description:
    //
    //   Constructs a new EventManager with default initial capacities for the event cache and event queue.
    //   Delegates to the reset method to initialize internal data structures.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public EventManager ()
    {
        reset ();
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: compare
    //
    // Description:
    //
    //   Comparator implementation used by the PriorityQueue to order events chronologically. Events with
    //   earlier timestamps are given higher priority.
    //
    // Arguments:
    //
    //   a (Event):
    //     The first event to compare.
    //
    //   b (Event):
    //     The second event to compare.
    //
    // Returns:
    //
    //   A negative integer if event a occurred before event b, a positive integer if event a occurred after
    //   event b, or zero if both events have the same timestamp.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public int compare ( Event a, Event b )
    {
        int comparisonBias = 0;

        if (a.getTime () < b.getTime () )
        {
            comparisonBias = -1;
        }

        if (a.getTime () > b.getTime () )
        {
            comparisonBias = 1;
        }

        return comparisonBias;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: reset
    //
    // Description:
    //
    //   Initializes or resets the EventManager to its default state. Creates a new event cache and event
    //   queue with default initial capacities.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void reset ()
    {
        // Set the initial capacities for the event cache and event queue.

        this.initialEventCacheCapacity = M_DEFAULT_INITIAL_EVENT_CACHE_CAPACITY;
        this.initialEventQueueCapacity = M_DEFAULT_INITIAL_EVENT_QUEUE_CAPACITY;

        // create a new event cache and event queue.

        this.eventCache = new HashMap       <String, Event> ( this.initialEventCacheCapacity );
        this.eventQueue = new PriorityQueue <Event>         ( this.initialEventQueueCapacity, this );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: cache
    //
    // Description:
    //
    //   Caches an event for later use. 
    // 
    //   - The event is stored in the event cache using its name as the key. 
    // 
    //   - If an event with the same name already exists in the cache, an exception is raised and the  duplicate event 
    //     is not added.
    //
    // Arguments:
    //
    //   event (Event):
    //     The event to cache. Its name must be unique within the event cache.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void cache ( Event event )
    {
        // Create an exception, to handel the case that the selected event fileName is not unique. Event names are 
        // used as unique identifiers, and as such are required to be unique.

        Exception exceptionNameNotUnique = new Exception ( compileExceptionString ( "Event", event.getName () ) );

        // Try to cache the event. And if the event fileName is not unique, then raise the "exceptionNameNotUnique" exception.

        try
        {
            // Determine if the event fileName is already in the list.

            Boolean uniqueKey = ! this.eventCache.containsKey ( event.getName () );

            // If the new event fileName is unique, then we add the event.
            // Otherwise we throw the exception.

            if ( uniqueKey )
            {
                this.eventCache.put ( event.getName (), event );
            }
            else
            {
                throw ( exceptionNameNotUnique );
            }
        }
        catch ( Exception e)
        {
            System.out.println ( e );
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: decache
    //
    // Description:
    //
    //   Removes an event from the event cache by its name.
    //
    // Arguments:
    //
    //   eventName (String):
    //     The name of the event to remove from the cache.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void decache ( String eventName )
    {
        this.eventCache.remove ( eventName );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: register
    //
    // Description:
    //
    //   Registers a listener with a named event. A listener is any class that implements IEventListener and overrides 
    //   the OnEvent method.
    //
    // Arguments:
    //
    //   listener (IEventListener):
    //     The listener class to register with the specified event.
    //
    //   eventName (String):
    //     The name of the event to register the listener with.
    //
    // Returns:
    //
    //   The Event object that the listener was registered with.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Event register ( IEventListener listener, String eventName )
    {
        // Get the event object for the named event, that we would like to register a listener with.

        Event event = this.eventCache.get ( eventName );

        // Register the listener with the selected event.

        event.register ( listener );

        // Return the event, just in case the caller would like to use it for anything

        return event;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: deregister
    //
    // Description:
    //
    //   Deregisters a previously registered listener from a named event.
    //
    // Arguments:
    //
    //   listener (IEventListener):
    //     The listener class to deregister from the specified event.
    //
    //   eventName (String):
    //     The name of the event to deregister the listener from.
    //
    // Returns:
    //
    //   The Event object that the listener was deregistered from.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Event deregister ( IEventListener listener, String eventName )
    {
        // Get the event object for the named event, that we would like to
        // deregister a listener from.

        Event event = this.eventCache.get ( eventName );

        // Deregister the listener from the selected event.

        event.deregister ( listener );

        // Return the event, just in case the caller would like to use it for anything.

        return event;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: post
    //
    // Description:
    //
    //   Posts a named event to the active event queue. The event timestamp is set to the current system time.
    // 
    //   - Posted events will be dispatched on the next call to dispatchAll or flush.
    //
    //   - Note:
    //     - DispatchAll dispatches all events in the queue but does not clear it. 
    //     - Subsequent calls to dispatchAll will continue to dispatch the same events until flush or clearEventQueue 
    //       is called.
    //
    // Arguments:
    //
    //   eventName (String):
    //     The name of the event to post.
    //
    // Returns:
    //
    //   The Event object that was posted to the queue.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Event post ( String eventName )
    {
        // Get the event object for the named event, that we would like to post.

        Event event = this.eventCache.get ( eventName );

        // Record the time that we are posting this event.

        event.setTime ( System.currentTimeMillis() );

        // Post the event. i.e. Add the event to the event queue.

        this.eventQueue.add ( event );

        // Return the event, just in case the caller would like to use it for anything.

        return event;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dispatchAll
    //
    // Description:
    //
    //   Dispatches all events currently in the event queue by invoking the dispatch method on each event.
    //
    //   The event queue is not cleared after dispatching, so subsequent calls will re-dispatch the same events.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void dispatchAll ()
    {
        // Call the Dispatch method of each event in the event queue.

        for ( Event event : this.eventQueue )
        {
            event.dispatch ();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush
    //
    // Description:
    //
    //   Dispatches all events in the event queue and then clears the queue. Equivalent to calling dispatchAll 
    //   followed by clearEventQueue.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void flush ()
    {
        // Dispatch all events in the event queue.

        dispatchAll ();

        // And then clear the event queue when we done.

        this.eventQueue.clear ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearEventQueue
    //
    // Description:
    //
    //   Clears all events from the event queue without dispatching them.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clearEventQueue ()
    {
        this.eventQueue.clear ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearEventCache
    //
    // Description:
    //
    //   Clears all events from the event cache.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clearEventCache ()
    {
        this.eventCache.clear ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: compileExceptionString
    //
    // Description:
    //
    //   Utility method that compiles a formatted exception message string for when a component is defined using a 
    //   name that has already been used by another component.
    //
    // Arguments:
    //
    //   componentType (String):
    //     A string representing the component type involved, e.g. "Event", "Actor", etc.
    //
    //   componentName (String):
    //     The name that is not unique. This is the name that needs to be changed to something unique.
    //
    // Returns:
    //
    //   A formatted exception message string describing the naming conflict.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private String compileExceptionString ( String componentType, String componentName )
    {
        String exceptionString = new String ();

        exceptionString += "\n\n";
        exceptionString += "EventManager - Exception ( Name not unique ):\n";
        exceptionString += "- " + componentType + " fileName, \"" + componentName + "\", already exists.\n";
        exceptionString += "- " + componentType + " names are used as entity ID's, and should be unique.\n";

        return exceptionString;
    }

    //@formatter:on
}
