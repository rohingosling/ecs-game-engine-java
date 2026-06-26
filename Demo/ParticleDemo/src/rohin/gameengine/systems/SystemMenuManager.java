// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemMenuManager
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Processes user input commands, updates particle-group counts, manages
//   background image and button state for the menu system.
//
//   Commands set pendingAction. This system reads and processes it each frame.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.systems;

import java.util.ArrayList;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.TextFormat;
import rohin.gameengine.engines.EngineMenu;
import rohin.gameengine.application.Application;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.*;

public class SystemMenuManager extends ECSSystem
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constants
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // @formatter:off

    public static final int ACTION_NONE         = 0;
    public static final int ACTION_SELECT_NEXT  = 1;
    public static final int ACTION_SELECT_PREV  = 2;
    public static final int ACTION_ENTER        = 3;
    public static final int ACTION_BACK         = 4;
    public static final int ACTION_INCREMENT    = 5;
    public static final int ACTION_DECREMENT    = 6;
    public static final int ACTION_ESC          = 7;
    public static final int ACTION_BUTTON_DOWN  = 8;
    public static final int ACTION_BUTTON_UP    = 9;
    public static final int ACTION_EXIT_APP     = 10;

    // @formatter:on


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private EngineMenu engine;
    public  int        currentScreen;
    public  int        pendingAction;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public SystemMenuManager ( Integer id, String name, EngineMenu engine )
    {
        super ( id, name, engine );
        this.engine        = engine;
        this.currentScreen = Constants.SCREEN_MAIN;
        this.pendingAction = ACTION_NONE;
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Update.
    //
    // Process pending actions from commands.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            if ( this.pendingAction == ACTION_NONE )
            {
                return;
            }

            int action        = this.pendingAction;
            this.pendingAction = ACTION_NONE;

            switch ( action )
            {
                case ACTION_SELECT_NEXT:  selectNext ();      break;
                case ACTION_SELECT_PREV:  selectPrevious ();  break;
                case ACTION_ENTER:        enterButton ();     break;
                case ACTION_BACK:         goBack ();          break;
                case ACTION_INCREMENT:    incrementValue ();  break;
                case ACTION_DECREMENT:    decrementValue ();  break;
                case ACTION_ESC:          escAction ();       break;
                case ACTION_BUTTON_DOWN:  buttonDown ();      break;
                case ACTION_BUTTON_UP:    buttonUp ();        break;
                case ACTION_EXIT_APP:     exitApplication (); break;
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Get the list of enabled button entity IDs for the current screen.
    // ----------------------------------------------------------------------------------------------------------------

    private ArrayList <Integer> getActiveButtonIds ()
    {
        ArrayList <Integer> ids = new ArrayList <Integer> ();

        switch ( this.currentScreen )
        {
            case Constants.SCREEN_MAIN:
                ids.add ( Constants.ENTITY_MENU_BUTTON_START_SIMULATION );
                ids.add ( Constants.ENTITY_MENU_BUTTON_SETTINGS );
                ids.add ( Constants.ENTITY_MENU_BUTTON_INSTRUCTIONS );
                ids.add ( Constants.ENTITY_MENU_BUTTON_ABOUT );
                ids.add ( Constants.ENTITY_MENU_BUTTON_EXIT );
                break;

            case Constants.SCREEN_SETTINGS:
                ids.add ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED );
                ids.add ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN );
                ids.add ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE );
                ids.add ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW );
                ids.add ( Constants.ENTITY_MENU_BUTTON_BACK );
                break;

            case Constants.SCREEN_INSTRUCTIONS:
            case Constants.SCREEN_ABOUT:
                ids.add ( Constants.ENTITY_MENU_BUTTON_BACK );
                break;
        }

        return ids;
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Find the currently selected button ID. Returns -1 if none selected.
    // ----------------------------------------------------------------------------------------------------------------

    private int findSelectedButtonId ()
    {
        // Iterate over all entities and find the one whose ButtonState component has `selected` set to true.
        // A temporary ComponentButtonState instance is used as a type token for the hasComponents check.

        ComponentButtonState buttonState = new ComponentButtonState ();

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( buttonState ) )
            {
                ComponentButtonState state = (ComponentButtonState) entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );

                if ( state.selected )
                {
                    return (int) entity.getId ();
                }
            }
        }

        // No entity with a selected button was found. Return -1 as a sentinel value.

        return -1;
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Deselect all buttons.
    // ----------------------------------------------------------------------------------------------------------------

    private void deselectAll ()
    {
        // Iterate over all entities that have a ButtonState component and clear their selected and pressed flags.
        // This ensures a clean state before selecting a new button.

        ComponentButtonState buttonState = new ComponentButtonState ();

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( buttonState ) )
            {
                ComponentButtonState state = (ComponentButtonState) entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );
                state.selected = false;
                state.pressed  = false;
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Select a button by entity ID.
    // ----------------------------------------------------------------------------------------------------------------

    private void selectButton ( int entityId )
    {
        // Clear any existing selection so that only one button is selected at a time.

        deselectAll ();

        // Look up the target entity by ID and set its ButtonState to selected. The null checks guard against invalid
        // entity IDs or entities that lack a ButtonState component.

        ECSEntity entity = this.engine.getEntity ( entityId );

        if ( entity != null )
        {
            ComponentButtonState state = (ComponentButtonState) entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );

            if ( state != null )
            {
                state.selected = true;
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Select next button.
    // ----------------------------------------------------------------------------------------------------------------

    private void selectNext ()
    {
        // Get the list of button IDs for the current screen and find which one is currently selected. If no buttons
        // are active, there is nothing to navigate.

        ArrayList <Integer> activeIds = getActiveButtonIds ();

        if ( activeIds.isEmpty () ) return;

        int selectedId = findSelectedButtonId ();

        // If no button is selected, select the first one. Otherwise, advance to the next button in the list, stopping
        // at the last button (no wrap-around).

        if ( selectedId == -1 )
        {
            // No button selected — select first.

            selectButton ( activeIds.get ( 0 ) );
        }
        else
        {
            int index = activeIds.indexOf ( selectedId );

            if ( index < activeIds.size () - 1 )
            {
                selectButton ( activeIds.get ( index + 1 ) );
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Select previous button.
    // ----------------------------------------------------------------------------------------------------------------

    private void selectPrevious ()
    {
        // Get the list of button IDs for the current screen and find which one is currently selected. If no buttons
        // are active, there is nothing to navigate. If no button is selected, select the first one. Otherwise, move to
        // the previous button in the list, stopping at the first button (no wrap-around).

        ArrayList <Integer> activeIds = getActiveButtonIds ();

        if ( activeIds.isEmpty () ) return;

        int selectedId = findSelectedButtonId ();

        // If no button is selected, select the first one. Otherwise, move to the previous button in the list, stopping
        // at the first button (no wrap-around).

        if ( selectedId == -1 )
        {
            // No button selected — select first.

            selectButton ( activeIds.get ( 0 ) );
        }
        else
        {
            int index = activeIds.indexOf ( selectedId );

            if ( index > 0 )
            {
                selectButton ( activeIds.get ( index - 1 ) );
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Enter button action (dispatch based on which button is selected).
    // ----------------------------------------------------------------------------------------------------------------

    private void enterButton ()
    {
        // Find the currently selected button and dispatch its action. Each button maps to a specific operation:
        // Start Simulation launches the particle simulator, Settings/Instructions/About switch to their respective
        // screens, Exit shuts down the application, and Back returns to the main menu. If no button is selected, do
        // nothing.

        int selectedId = findSelectedButtonId ();

        if ( selectedId == -1 ) return;

        if ( selectedId == Constants.ENTITY_MENU_BUTTON_START_SIMULATION )
        {
            startSimulation ();
        }
        else if ( selectedId == Constants.ENTITY_MENU_BUTTON_SETTINGS )
        {
            switchScreen ( Constants.SCREEN_SETTINGS );
        }
        else if ( selectedId == Constants.ENTITY_MENU_BUTTON_INSTRUCTIONS )
        {
            switchScreen ( Constants.SCREEN_INSTRUCTIONS );
        }
        else if ( selectedId == Constants.ENTITY_MENU_BUTTON_ABOUT )
        {
            switchScreen ( Constants.SCREEN_ABOUT );
        }
        else if ( selectedId == Constants.ENTITY_MENU_BUTTON_EXIT )
        {
            exitApplication ();
        }
        else if ( selectedId == Constants.ENTITY_MENU_BUTTON_BACK )
        {
            goBack ();
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Button down (visual feedback — pressed state).
    // ----------------------------------------------------------------------------------------------------------------

    private void buttonDown ()
    {
        // Find the currently selected button and set its pressed flag to true for visual feedback. The rendering
        // system uses this flag to draw the button in its pressed appearance. If no button is selected, do nothing.

        int selectedId = findSelectedButtonId ();

        if ( selectedId == -1 ) return;

        ECSEntity entity = this.engine.getEntity ( selectedId );
        ComponentButtonState state = (ComponentButtonState) entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );
        state.pressed = true;
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Button up (revert pressed state, then execute action).
    // ----------------------------------------------------------------------------------------------------------------

    private void buttonUp ()
    {
        // Find the currently selected button, clear its pressed flag to revert the visual state, then execute the
        // button's action via enterButton. This two-step sequence provides a press-release visual effect before the
        // action takes place. If no button is selected, do nothing.

        int selectedId = findSelectedButtonId ();

        if ( selectedId == -1 ) return;

        ECSEntity entity = this.engine.getEntity ( selectedId );
        ComponentButtonState state = (ComponentButtonState) entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );
        state.pressed = false;

        // Execute the button's action.

        enterButton ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Escape action (context-dependent).
    // ----------------------------------------------------------------------------------------------------------------

    private void escAction ()
    {
        // Handle the Escape key based on context. On the main screen, if the Exit button is already selected, execute
        // exit immediately; otherwise, select the Exit button. On any sub-screen, if the Back button is already
        // selected, go back immediately; otherwise, select the Back button. This gives Escape a two-press behavior:
        // first press highlights the escape action, second press confirms it.

        if ( this.currentScreen == Constants.SCREEN_MAIN )
        {
            int selectedId = findSelectedButtonId ();

            if ( selectedId == Constants.ENTITY_MENU_BUTTON_EXIT )
            {
                // Exit is already selected — execute the action.

                exitApplication ();
            }
            else
            {
                // Select the Exit button.

                selectButton ( Constants.ENTITY_MENU_BUTTON_EXIT );
            }
        }
        else
        {
            int selectedId = findSelectedButtonId ();

            if ( selectedId == Constants.ENTITY_MENU_BUTTON_BACK )
            {
                // Back is already selected — execute the action.

                goBack ();
            }
            else
            {
                // Select the Back button.

                selectButton ( Constants.ENTITY_MENU_BUTTON_BACK );
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Go back to main menu.
    // ----------------------------------------------------------------------------------------------------------------

    private void goBack ()
    {
        switchScreen ( Constants.SCREEN_MAIN );
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Switch to a different screen.
    // ----------------------------------------------------------------------------------------------------------------

    private void switchScreen ( int screen )
    {
        // Disable all non-main entities.

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            entity.setEnabled ( true );
        }

        // Disable entities not belonging to the target screen.

        switch ( screen )
        {
            case Constants.SCREEN_MAIN:
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_BACK ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_TEXT_BOX_INSTRUCTIONS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_TEXT_BOX_ABOUT ).setEnabled ( false );
                break;

            case Constants.SCREEN_SETTINGS:
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_START_SIMULATION ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_INSTRUCTIONS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_ABOUT ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_EXIT ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_TEXT_BOX_INSTRUCTIONS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_TEXT_BOX_ABOUT ).setEnabled ( false );
                break;

            case Constants.SCREEN_INSTRUCTIONS:
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_START_SIMULATION ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_INSTRUCTIONS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_ABOUT ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_EXIT ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_TEXT_BOX_ABOUT ).setEnabled ( false );
                break;

            case Constants.SCREEN_ABOUT:
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_START_SIMULATION ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_INSTRUCTIONS ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_ABOUT ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_EXIT ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW ).setEnabled ( false );
                this.engine.getEntity ( Constants.ENTITY_MENU_TEXT_BOX_INSTRUCTIONS ).setEnabled ( false );
                break;
        }

        // Update background image.

        Application application = this.engine.getApplication ();
        ComponentResourceBackgroundImage bg = (ComponentResourceBackgroundImage) this.engine.getEntity ( Constants.ENTITY_BACKGROUND_MENU ).getComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE );

        switch ( screen )
        {
            case Constants.SCREEN_MAIN:         bg.imagePath = application.getSettings ().getString ( Constants.KEY_MENU_BACKGROUND_MAIN );         break;
            case Constants.SCREEN_SETTINGS:     bg.imagePath = application.getSettings ().getString ( Constants.KEY_MENU_BACKGROUND_SETTINGS );     break;
            case Constants.SCREEN_INSTRUCTIONS: bg.imagePath = application.getSettings ().getString ( Constants.KEY_MENU_BACKGROUND_INSTRUCTIONS ); break;
            case Constants.SCREEN_ABOUT:        bg.imagePath = application.getSettings ().getString ( Constants.KEY_MENU_BACKGROUND_ABOUT );        break;
        }

        this.currentScreen = screen;

        // Deselect all buttons when switching screens.

        deselectAll ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Increment counter value.
    // ----------------------------------------------------------------------------------------------------------------

    private void incrementValue ()
    {
        // Find the currently selected button and increment its particle count if it has one. Look up the selected
        // button entity and verify it has a ComponentParticleCount. If the current count is below the maximum, increment
        // it, update the button's display text, and write the new value to the global cache. If no button is selected
        // or the entity is missing, do nothing.

        int selectedId = findSelectedButtonId ();

        if ( selectedId == -1 ) return;

        ECSEntity entity = this.engine.getEntity ( selectedId );

        if ( entity == null ) return;

        // Check if the entity has a particle count component.

        ComponentParticleCount countToken = new ComponentParticleCount ();

        if ( entity.hasComponents ( countToken ) )
        {
            ComponentParticleCount count = (ComponentParticleCount) entity.getComponent ( Constants.COMPONENT_PARTICLE_COUNT );

            if ( count.particleCount < count.particleCountMax )
            {
                count.particleCount++;
                updateCounterButtonText ( entity, count );
                updateGlobalCache ();
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Decrement counter value.
    // ----------------------------------------------------------------------------------------------------------------

    private void decrementValue ()
    {
        int selectedId = findSelectedButtonId ();

        if ( selectedId == -1 ) return;

        ECSEntity entity = this.engine.getEntity ( selectedId );

        if ( entity == null ) return;

        ComponentParticleCount countToken = new ComponentParticleCount ();

        if ( entity.hasComponents ( countToken ) )
        {
            ComponentParticleCount count = (ComponentParticleCount) entity.getComponent ( Constants.COMPONENT_PARTICLE_COUNT );

            if ( count.particleCount > count.particleCountMin )
            {
                count.particleCount--;
                updateCounterButtonText ( entity, count );
                updateGlobalCache ();
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Update counter button text after increment/decrement.
    // ----------------------------------------------------------------------------------------------------------------

    private void updateCounterButtonText ( ECSEntity entity, ComponentParticleCount count )
    {
        ComponentButtonText buttonText = (ComponentButtonText) entity.getComponent ( Constants.COMPONENT_BUTTON_TEXT );
        buttonText.text = count.label + ":  < " + count.particleCount + " >";
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Write current particle counts to GlobalCache.
    // ----------------------------------------------------------------------------------------------------------------

    private void updateGlobalCache ()
    {
        Application application = this.engine.getApplication ();

        // @formatter:off

        ComponentParticleCount red    = (ComponentParticleCount) this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED ).getComponent    ( Constants.COMPONENT_PARTICLE_COUNT );
        ComponentParticleCount green  = (ComponentParticleCount) this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN ).getComponent  ( Constants.COMPONENT_PARTICLE_COUNT );
        ComponentParticleCount blue   = (ComponentParticleCount) this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE ).getComponent   ( Constants.COMPONENT_PARTICLE_COUNT );
        ComponentParticleCount yellow = (ComponentParticleCount) this.engine.getEntity ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW ).getComponent ( Constants.COMPONENT_PARTICLE_COUNT );

        application.getGlobalCache ().put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_RED,    red.particleCount );
        application.getGlobalCache ().put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_GREEN,  green.particleCount );
        application.getGlobalCache ().put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_BLUE,   blue.particleCount );
        application.getGlobalCache ().put ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_YELLOW, yellow.particleCount );

        // @formatter:on
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Start simulation.
    // ----------------------------------------------------------------------------------------------------------------

    private void startSimulation ()
    {
        updateGlobalCache ();

        Application application = this.engine.getApplication ();
        application.getGlobalCache ().put ( Constants.GLOBAL_CACHE_APPLICATION_STATE, Constants.APPLICATION_STATE_LEVEL_PARTICLE_SIMULATION );
        this.engine.setLoopRunning ( false );
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Exit application.
    // ----------------------------------------------------------------------------------------------------------------

    private void exitApplication ()
    {
        Application application = this.engine.getApplication ();
        application.getGlobalCache ().put ( Constants.GLOBAL_CACHE_APPLICATION_STATE, Constants.APPLICATION_STATE_STOPPING );
        this.engine.setLoopRunning ( false );
    }
}
