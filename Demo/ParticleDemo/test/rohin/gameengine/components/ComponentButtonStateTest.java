package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;


// ========================================================================================
//
// ComponentButtonStateTest
//
// Unit tests for ComponentButtonState.
//
// ========================================================================================

public class ComponentButtonStateTest
{
    // ========================================================================================
    //
    // Constructor Tests
    //
    // ========================================================================================

    @Test
    public void defaultConstructor_createsNonNullInstance ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void pressed_defaultsToFalse ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        assertFalse ( component.pressed );
    }


    @Test
    public void selected_defaultsToFalse ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        assertFalse ( component.selected );
    }


    @Test
    public void enabled_defaultsToTrue ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        assertTrue ( component.enabled );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void pressed_isPubliclyModifiable ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        component.pressed = true;
        assertTrue ( component.pressed );
    }


    @Test
    public void selected_isPubliclyModifiable ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        component.selected = true;
        assertTrue ( component.selected );
    }


    @Test
    public void enabled_isPubliclyModifiable ()
    {
        ComponentButtonState component = new ComponentButtonState ();
        component.enabled = false;
        assertFalse ( component.enabled );
    }
}
