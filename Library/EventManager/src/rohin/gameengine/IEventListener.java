//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the IEventListener interface, the event subscriber contract for the ECS Game Engine event system.
//
//   Classes that wish to receive event notifications must implement this interface and override the OnEvent method.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//---------------------------------------------------------------------------------------------------------------------
// Interface: IEventListener
//
// Description:
//
//   IEventListener is the event subscriber interface, used to register event handlers for specified Event
//   objects. 
//
//   - Any class that implements this interface can be registered as a listener with an Event instance via 
//     the EventManager.
// 
//   - When the event is dispatched, the OnEvent method of all registered listeners will be invoked.
//
//---------------------------------------------------------------------------------------------------------------------

interface IEventListener
{
  //===================================================================================================================
  // Methods
  //===================================================================================================================

  //-------------------------------------------------------------------------------------------------------------------
  // Method: OnEvent
  //
  // Description:
  //
  //   Callback method invoked when a registered event is dispatched.
  // 
  //   - Implementing classes should override this method to define their event-handling behavior.
  //
  // Arguments:
  //
  //   event (Event):
  //     The event that was dispatched. Contains the event name, payload, and timestamp.
  //
  //-------------------------------------------------------------------------------------------------------------------

  public void OnEvent ( Event event );
}

