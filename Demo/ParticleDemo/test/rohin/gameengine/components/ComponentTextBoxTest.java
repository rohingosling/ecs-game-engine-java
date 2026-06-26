package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentTextBoxTest
//
// Unit tests for ComponentTextBox.
//
// ========================================================================================

public class ComponentTextBoxTest
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
        ComponentTextBox component = new ComponentTextBox ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void text_defaultsToEmptyString ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertEquals ( "", component.text );
    }


    @Test
    public void font_defaultsToCourierNew ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertEquals ( "Courier New", component.font );
    }


    @Test
    public void fontSize_defaultsTo20 ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertEquals ( 20, component.fontSize );
    }


    @Test
    public void align_defaultsTo1 ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertEquals ( 1, component.align );
    }


    @Test
    public void color_defaultsToWhite ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertArrayEquals ( new int[] { 255, 255, 255 }, component.color );
    }


    @Test
    public void position_defaultsToZeroVector ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertNotNull ( component.position );
        assertEquals ( 0.0, component.position.getX (), EPSILON );
        assertEquals ( 0.0, component.position.getY (), EPSILON );
    }


    @Test
    public void width_defaultsTo800 ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertEquals ( 800, component.width );
    }


    @Test
    public void height_defaultsTo600 ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        assertEquals ( 600, component.height );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void text_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.text = "Hello World";
        assertEquals ( "Hello World", component.text );
    }


    @Test
    public void font_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.font = "Arial";
        assertEquals ( "Arial", component.font );
    }


    @Test
    public void fontSize_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.fontSize = 32;
        assertEquals ( 32, component.fontSize );
    }


    @Test
    public void align_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.align = 2;
        assertEquals ( 2, component.align );
    }


    @Test
    public void color_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.color = new int[] { 128, 0, 255 };
        assertArrayEquals ( new int[] { 128, 0, 255 }, component.color );
    }


    @Test
    public void position_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.position = new Vector2D ( 50.0, 75.0 );
        assertEquals ( 50.0, component.position.getX (), EPSILON );
        assertEquals ( 75.0, component.position.getY (), EPSILON );
    }


    @Test
    public void width_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.width = 1024;
        assertEquals ( 1024, component.width );
    }


    @Test
    public void height_isPubliclyModifiable ()
    {
        ComponentTextBox component = new ComponentTextBox ();
        component.height = 768;
        assertEquals ( 768, component.height );
    }
}
