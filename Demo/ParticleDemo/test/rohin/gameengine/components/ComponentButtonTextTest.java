package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;


// ========================================================================================
//
// ComponentButtonTextTest
//
// Unit tests for ComponentButtonText.
//
// ========================================================================================

public class ComponentButtonTextTest
{
    // ========================================================================================
    //
    // Constructor Tests
    //
    // ========================================================================================

    @Test
    public void defaultConstructor_createsNonNullInstance ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void text_defaultsToDash ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertEquals ( "-", component.text );
    }


    @Test
    public void align_defaultsToOne ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertEquals ( 1, component.align );
    }


    @Test
    public void font_defaultsToAnitaSemiSquare ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertEquals ( "Anita Semi-square", component.font );
    }


    @Test
    public void size_defaultsTo20 ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertEquals ( 20, component.size );
    }


    @Test
    public void color_defaultsToWhite ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertArrayEquals ( new int[] { 255, 255, 255 }, component.color );
    }


    @Test
    public void colorDisabled_defaultsToDarkGray ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        assertArrayEquals ( new int[] { 64, 64, 64 }, component.colorDisabled );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void text_isPubliclyModifiable ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        component.text = "Start";
        assertEquals ( "Start", component.text );
    }


    @Test
    public void align_isPubliclyModifiable ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        component.align = 2;
        assertEquals ( 2, component.align );
    }


    @Test
    public void font_isPubliclyModifiable ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        component.font = "Times New Roman";
        assertEquals ( "Times New Roman", component.font );
    }


    @Test
    public void size_isPubliclyModifiable ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        component.size = 32;
        assertEquals ( 32, component.size );
    }


    @Test
    public void color_isPubliclyModifiable ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        component.color = new int[] { 0, 128, 255 };
        assertArrayEquals ( new int[] { 0, 128, 255 }, component.color );
    }


    @Test
    public void colorDisabled_isPubliclyModifiable ()
    {
        ComponentButtonText component = new ComponentButtonText ();
        component.colorDisabled = new int[] { 100, 100, 100 };
        assertArrayEquals ( new int[] { 100, 100, 100 }, component.colorDisabled );
    }
}
