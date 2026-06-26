//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Keyboard event handler for the menu system. Posts commands to the CommandManager in response to key events.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.application;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import rohin.gameengine.KeyboardEventHandlerTemplate;
import rohin.gameengine.engines.EngineMenu;
import rohin.gameengine.commands.*;

//*********************************************************************************************************************
// Class: KeyboardEventHandlerMenu
//
// Description:
//
//   Keyboard event handler for the menu system.
//
//   - Translates key presses and releases into menu navigation and action commands (select previous/next button,
//     increment/decrement value, button down/up, escape) by posting to the EngineMenu CommandManager.
//
//*********************************************************************************************************************

public class KeyboardEventHandlerMenu extends KeyboardEventHandlerTemplate
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private EngineMenu         engine;
    private HashSet <Integer>  keysDown;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: KeyboardEventHandlerMenu
    //
    // Description:
    //
    //   Initializes the keyboard handler with a reference to the owning EngineMenu and an empty set of currently held keys.
    //
    // Arguments:
    //
    //   engine (EngineMenu):
    //     The menu engine whose CommandManager receives posted commands.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public KeyboardEventHandlerMenu ( EngineMenu engine )
    {
        super ();
        this.engine   = engine;
        this.keysDown = new HashSet <Integer> ();
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: keyPressed
    //
    // Description:
    //
    //   Handles key-press events for the menu.
    //
    //   - Ignores auto-repeat events by tracking which keys are currently held down. Maps arrow keys to button
    //     selection and value adjustment, Enter to button activation, and Escape to menu exit.
    //
    // Arguments:
    //
    //   e (KeyEvent):
    //     The AWT key event describing which key was pressed.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @ Override
    public void keyPressed ( KeyEvent e )
    {
        // Extract the key code from the event for use in the switch statement below.

        int keyCode = e.getKeyCode ();

        // Suppress auto-repeat. AWT fires repeated keyPressed events while a key is held down. If the key is already
        // in the held-keys set, this is an auto-repeat event and should be ignored.

        if ( this.keysDown.contains ( keyCode ) )
        {
            return;
        }

        // Record this key as held, then dispatch the appropriate menu command based on the key code.

        this.keysDown.add ( keyCode );

        switch ( keyCode )
        {
            case KeyEvent.VK_UP:
                this.engine.getCommandManager ().postCommand ( new CommandMenuSelectButtonPrevious ( this.engine ) );
                break;

            case KeyEvent.VK_DOWN:
                this.engine.getCommandManager ().postCommand ( new CommandMenuSelectButtonNext ( this.engine ) );
                break;

            case KeyEvent.VK_RIGHT:
                this.engine.getCommandManager ().postCommand ( new CommandMenuIncrementValue ( this.engine ) );
                break;

            case KeyEvent.VK_LEFT:
                this.engine.getCommandManager ().postCommand ( new CommandMenuDecrementValue ( this.engine ) );
                break;

            case KeyEvent.VK_ENTER:
                this.engine.getCommandManager ().postCommand ( new CommandMenuButtonDown ( this.engine ) );
                break;

            case KeyEvent.VK_ESCAPE:
                this.engine.getCommandManager ().postCommand ( new CommandMenuEsc ( this.engine ) );
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: keyReleased
    //
    // Description:
    //
    //   Handles key-release events for the menu.
    //
    //   - Removes the key from the held-keys set and posts a button-up command when Enter is released.
    //
    // Arguments:
    //
    //   e (KeyEvent):
    //     The AWT key event describing which key was released.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @ Override
    public void keyReleased ( KeyEvent e )
    {
        int keyCode = e.getKeyCode ();

        this.keysDown.remove ( keyCode );

        switch ( keyCode )
        {
            case KeyEvent.VK_ENTER:
                this.engine.getCommandManager ().postCommand ( new CommandMenuButtonUp ( this.engine ) );
                break;
        }
    }
}
