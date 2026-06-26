package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentHudTest
//
// Unit tests for ComponentHud.
//
// ========================================================================================

public class ComponentHudTest
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
        ComponentHud component = new ComponentHud ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentHud component = new ComponentHud ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void visible_defaultsToFalse ()
    {
        ComponentHud component = new ComponentHud ();
        assertFalse ( component.visible );
    }


    @Test
    public void text_defaultsToEmptyString ()
    {
        ComponentHud component = new ComponentHud ();
        assertEquals ( "", component.text );
    }


    @Test
    public void font_defaultsToCourierNew ()
    {
        ComponentHud component = new ComponentHud ();
        assertEquals ( "Courier New", component.font );
    }


    @Test
    public void fontSize_defaultsTo14 ()
    {
        ComponentHud component = new ComponentHud ();
        assertEquals ( 14, component.fontSize );
    }


    @Test
    public void color_defaultsToGreen ()
    {
        ComponentHud component = new ComponentHud ();
        assertArrayEquals ( new int[] { 0, 255, 0 }, component.color );
    }


    @Test
    public void backgroundColor_defaultsToBlack ()
    {
        ComponentHud component = new ComponentHud ();
        assertArrayEquals ( new int[] { 0, 0, 0 }, component.backgroundColor );
    }


    @Test
    public void position_defaultsToTenTenVector ()
    {
        ComponentHud component = new ComponentHud ();
        assertNotNull ( component.position );
        assertEquals ( 10.0, component.position.getX (), EPSILON );
        assertEquals ( 10.0, component.position.getY (), EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void visible_isPubliclyModifiable ()
    {
        ComponentHud component = new ComponentHud ();
        component.visible = true;
        assertTrue ( component.visible );
    }


    @Test
    public void text_isPubliclyModifiable ()
    {
        ComponentHud component = new ComponentHud ();
        component.text = "FPS: 60";
        assertEquals ( "FPS: 60", component.text );
    }


    @Test
    public void font_isPubliclyModifiable ()
    {
        ComponentHud component = new ComponentHud ();
        component.font = "Arial";
        assertEquals ( "Arial", component.font );
    }


    @Test
    public void fontSize_isPubliclyModifiable ()
    {
        ComponentHud component = new ComponentHud ();
        component.fontSize = 24;
        assertEquals ( 24, component.fontSize );
    }


    @Test
    public void color_isPubliclyModifiable ()
    {
        ComponentHud component = new ComponentHud ();
        component.color = new int[] { 255, 255, 0 };
        assertArrayEquals ( new int[] { 255, 255, 0 }, component.color );
    }


    @Test
    public void backgroundColor_isPubliclyModifiable ()
    {
        ComponentHud component = new ComponentHud ();
        component.backgroundColor = new int[] { 32, 32, 32 };
        assertArrayEquals ( new int[] { 32, 32, 32 }, component.backgroundColor );
    }


    @Test
    public void position_isPubliclyModifiable ()
    {
        ComponentHud component = new ComponentHud ();
        component.position = new Vector2D ( 50.0, 100.0 );
        assertEquals ( 50.0, component.position.getX (), EPSILON );
        assertEquals ( 100.0, component.position.getY (), EPSILON );
    }
}
