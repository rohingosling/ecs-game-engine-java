//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the Event class, a generic event object managed by the EventManager.
//
//   - Each event carries a unique name, an optional payload, and a timestamp.
//
//   - Listeners implementing IEventListener can register with an event to receive dispatch notifications via the
//     OnEvent callback.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.util.Collection;
import java.util.LinkedList;

//*********************************************************************************************************************
// Class: Event
//
// Description:
//
//   Event is a generic event class, managed by instances of the EventManager class.
//
//   - It encapsulates the data and behavior required to support an observer-pattern event system.
//
//   - It is the responsibility of the EventManager class to ensure that event names are unique.
//
//   Features:
//
//   - A name string, which serves as the unique identifier for the event.
//
//   - A payload object, which can be cast to accommodate any kind of data a consumer wishes to carry on the event.
//
//   - A timestamp, used for sequencing events in the priority queue.
//
//   - A listener list, containing all IEventListener instances registered to receive this event.
//
//*********************************************************************************************************************

public class Event
{
    //@formatter:off

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private String                      name;           // Unique identifier for this event.
    private Object                      payload;        // Generic pay load object.
    private long                        time;           // The time at which the event was posted.
    private Collection <IEventListener> listenerList;   // A list of all listeners registered with this event.

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public String getName    () { return this.name;    }
    public Object getPayload () { return this.payload; }
    public long   getTime    () { return this.time;    }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setName    ( String name    ) { this.name    = name;    }
    public void setPayload ( Object payload ) { this.payload = payload; }
    public void setTime    ( long time      ) { this.time    = time;    }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public Event ( String name )                 { reset ( name, null ); }
    public Event ( String name, Object payload ) { reset ( name, payload ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: reset
    //
    // Description:
    //
    //   Initializes or resets the event to the specified state.
    // 
    //   The name and payload are set to the given values, the timestamp is reset to zero, and the listener list is 
    //   replaced with a new empty collection.
    //
    // Arguments:
    //
    //   name (String):
    //     A string value that serves as the unique identifier of this event.
    //
    //   payload (Object):
    //     A generic payload object that can be used to carry any kind of data the consumer wishes to ship with
    //     the event.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void reset ( String name, Object payload )
    {
        this.name         = name;
        this.payload      = payload;
        this.time         = 0;
        this.listenerList = new LinkedList <IEventListener>();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: register
    //
    // Description:
    //
    //   Registers a listener with this event. A listener can be any class that implements the IEventListener
    //   interface and overrides the OnEvent method.
    //
    //   When this event is dispatched, the OnEvent method of every registered listener will be called.
    //
    // Arguments:
    //
    //   listener (IEventListener):
    //     The listener to register. The registered class must implement IEventListener and override the OnEvent
    //     method.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void register ( IEventListener listener )
    {
        this.listenerList.add ( listener );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: deregister
    //
    // Description:
    //
    //   Removes a previously registered listener from this event. After deregistration, the listener will no
    //   longer receive dispatch notifications for this event.
    //
    // Arguments:
    //
    //   listener (IEventListener):
    //     The listener to deregister from this event.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void deregister ( IEventListener listener )
    {
        this.listenerList.remove ( listener );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dispatch
    //
    // Description:
    //
    //   Dispatches this event by invoking the OnEvent callback on every registered listener. Each listener
    //   receives a reference to this event instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void dispatch ()
    {
        for ( IEventListener listener : this.listenerList )
        {
            listener.OnEvent ( this );
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clear
    //
    // Description:
    //
    //   Removes all registered listeners from this event. After clearing, dispatching this event will have no
    //   effect until new listeners are registered.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clear ()
    {
        this.listenerList.clear ();
    }

    //@formatter:on
}

