package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;


// ========================================================================================
//
// ComponentWorldTest
//
// Unit tests for ComponentWorld.
//
// ========================================================================================

public class ComponentWorldTest
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
        ComponentWorld component = new ComponentWorld ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void particleCountRed_defaultsTo6 ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertEquals ( 6, component.particleCountRed );
    }


    @Test
    public void particleCountGreen_defaultsTo3 ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertEquals ( 3, component.particleCountGreen );
    }


    @Test
    public void particleCountBlue_defaultsTo3 ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertEquals ( 3, component.particleCountBlue );
    }


    @Test
    public void particleCountYellow_defaultsTo9 ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertEquals ( 9, component.particleCountYellow );
    }


    @Test
    public void gravitationalConstant_defaultsTo1 ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertEquals ( 1.0, component.gravitationalConstant, EPSILON );
    }


    @Test
    public void repulsiveConstant_defaultsTo500 ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertEquals ( 500.0, component.repulsiveConstant, EPSILON );
    }


    @Test
    public void paused_defaultsToFalse ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertFalse ( component.paused );
    }


    @Test
    public void trailsVisible_defaultsToTrue ()
    {
        ComponentWorld component = new ComponentWorld ();
        assertTrue ( component.trailsVisible );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void particleCountRed_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.particleCountRed = 12;
        assertEquals ( 12, component.particleCountRed );
    }


    @Test
    public void particleCountGreen_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.particleCountGreen = 8;
        assertEquals ( 8, component.particleCountGreen );
    }


    @Test
    public void particleCountBlue_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.particleCountBlue = 15;
        assertEquals ( 15, component.particleCountBlue );
    }


    @Test
    public void particleCountYellow_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.particleCountYellow = 20;
        assertEquals ( 20, component.particleCountYellow );
    }


    @Test
    public void gravitationalConstant_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.gravitationalConstant = 9.8;
        assertEquals ( 9.8, component.gravitationalConstant, EPSILON );
    }


    @Test
    public void repulsiveConstant_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.repulsiveConstant = 1000.0;
        assertEquals ( 1000.0, component.repulsiveConstant, EPSILON );
    }


    @Test
    public void paused_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.paused = true;
        assertTrue ( component.paused );
    }


    @Test
    public void trailsVisible_isPubliclyModifiable ()
    {
        ComponentWorld component = new ComponentWorld ();
        component.trailsVisible = false;
        assertFalse ( component.trailsVisible );
    }
}
