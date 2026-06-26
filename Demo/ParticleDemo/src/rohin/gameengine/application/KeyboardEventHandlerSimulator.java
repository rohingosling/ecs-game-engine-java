//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Keyboard event handler for the particle simulation.
//   - Posts commands to the CommandManager in response to key events.
//
//   Key bindings:
//
//   - Arrow keys (pressed)  : Accelerate selected particle.
//   - Arrow keys (released) : Decelerate selected particle.
//   - Tab                   : Select next particle.
//   - Shift+Tab             : Select previous particle.
//   - P                     : Toggle pause.
//   - T                     : Toggle trails.
//   - W                     : Toggle wireframe.
//   - Escape                : Deselect particle, or exit simulation.
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
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.commands.*;

//*********************************************************************************************************************
// Class: KeyboardEventHandlerSimulator
//
// Description:
//
//   Keyboard event handler for the particle simulation.
//
//   - Translates key presses and releases into simulation commands (accelerate, decelerate, select particle,
//     toggle pause/trails/wireframe, escape) by posting to the EngineParticleSimulator CommandManager.
//
//*********************************************************************************************************************

public class KeyboardEventHandlerSimulator extends KeyboardEventHandlerTemplate
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private EngineParticleSimulator engine;
    private HashSet <Integer>       keysDown;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: KeyboardEventHandlerSimulator
    //
    // Description:
    //
    //   Initializes the keyboard handler with a reference to the owning EngineParticleSimulator and an empty set of
    //   currently held keys.
    //
    // Arguments:
    //
    //   engine (EngineParticleSimulator):
    //     The particle simulation engine whose CommandManager receives posted commands.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public KeyboardEventHandlerSimulator ( EngineParticleSimulator engine )
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
    //   Handles key-press events for the simulation. Ignores auto-repeat events by tracking which
    //   keys are currently held down. Maps arrow keys to particle acceleration, Tab/Shift+Tab to
    //   particle selection, P/T/W to toggle commands, and Escape to exit.
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

        // Record this key as held, then dispatch the appropriate simulation command based on the key code.

        this.keysDown.add ( keyCode );

        switch ( keyCode )
        {
            case KeyEvent.VK_UP:
                this.engine.getCommandManager ().postCommand ( new CommandParticleAccelerateUp ( this.engine ) );
                break;

            case KeyEvent.VK_DOWN:
                this.engine.getCommandManager ().postCommand ( new CommandParticleAccelerateDown ( this.engine ) );
                break;

            case KeyEvent.VK_LEFT:
                this.engine.getCommandManager ().postCommand ( new CommandParticleAccelerateLeft ( this.engine ) );
                break;

            case KeyEvent.VK_RIGHT:
                this.engine.getCommandManager ().postCommand ( new CommandParticleAccelerateRight ( this.engine ) );
                break;

            case KeyEvent.VK_TAB:
                if ( e.isShiftDown () )
                {
                    this.engine.getCommandManager ().postCommand ( new CommandParticleSelectPrevious ( this.engine ) );
                }
                else
                {
                    this.engine.getCommandManager ().postCommand ( new CommandParticleSelectNext ( this.engine ) );
                }
                break;

            case KeyEvent.VK_P:
                this.engine.getCommandManager ().postCommand ( new CommandParticleSimulatorPause ( this.engine ) );
                break;

            case KeyEvent.VK_T:
                this.engine.getCommandManager ().postCommand ( new CommandParticleSimulatorToggleTrails ( this.engine ) );
                break;

            case KeyEvent.VK_W:
                this.engine.getCommandManager ().postCommand ( new CommandParticleSimulatorToggleWireframe ( this.engine ) );
                break;

            case KeyEvent.VK_ESCAPE:
                this.engine.getCommandManager ().postCommand ( new CommandParticleSimulatorEsc ( this.engine ) );
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: keyReleased
    //
    // Description:
    //
    //   Handles key-release events for the simulation.
    //   - Removes the key from the held-keys set and posts a decelerate command when any arrow key is released.
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
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                this.engine.getCommandManager ().postCommand ( new CommandParticleDecelerate ( this.engine, keyCode ) );
                break;
        }
    }
}
