package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;


// ========================================================================================
//
// ComponentUserControlTest
//
// Unit tests for ComponentUserControl.
//
// ========================================================================================

public class ComponentUserControlTest
{
    private static final double EPSILON = 1e-9;


    // ========================================================================================
    //
    // Constructor Tests
    //
    // ========================================================================================

    @Test
    public void defaultConstructor_createsNonNullInstance ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void accelerateUp_defaultsToFalse ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        assertFalse ( component.accelerateUp );
    }


    @Test
    public void accelerateDown_defaultsToFalse ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        assertFalse ( component.accelerateDown );
    }


    @Test
    public void accelerateLeft_defaultsToFalse ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        assertFalse ( component.accelerateLeft );
    }


    @Test
    public void accelerateRight_defaultsToFalse ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        assertFalse ( component.accelerateRight );
    }


    @Test
    public void accelerationMagnitude_defaultsTo100 ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        assertEquals ( 100.0, component.accelerationMagnitude, EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void accelerateUp_isPubliclyModifiable ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        component.accelerateUp = true;
        assertTrue ( component.accelerateUp );
    }


    @Test
    public void accelerateDown_isPubliclyModifiable ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        component.accelerateDown = true;
        assertTrue ( component.accelerateDown );
    }


    @Test
    public void accelerateLeft_isPubliclyModifiable ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        component.accelerateLeft = true;
        assertTrue ( component.accelerateLeft );
    }


    @Test
    public void accelerateRight_isPubliclyModifiable ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        component.accelerateRight = true;
        assertTrue ( component.accelerateRight );
    }


    @Test
    public void accelerationMagnitude_isPubliclyModifiable ()
    {
        ComponentUserControl component = new ComponentUserControl ();
        component.accelerationMagnitude = 250.0;
        assertEquals ( 250.0, component.accelerationMagnitude, EPSILON );
    }
}
