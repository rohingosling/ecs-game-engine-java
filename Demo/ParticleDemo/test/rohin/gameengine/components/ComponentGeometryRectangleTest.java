package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentGeometryRectangleTest
//
// Unit tests for ComponentGeometryRectangle.
//
// ========================================================================================

public class ComponentGeometryRectangleTest
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
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void width_defaultsTo1 ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertEquals ( 1.0, component.width, EPSILON );
    }


    @Test
    public void height_defaultsTo1 ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertEquals ( 1.0, component.height, EPSILON );
    }


    @Test
    public void origin_defaultsToZeroVector ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertNotNull ( component.origin );
        assertEquals ( 0.0, component.origin.getX (), EPSILON );
        assertEquals ( 0.0, component.origin.getY (), EPSILON );
    }


    @Test
    public void color_defaultsToWhite ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertArrayEquals ( new int[] { 255, 255, 255 }, component.color );
    }


    @Test
    public void crosshair_defaultsToTrue ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertTrue ( component.crosshair );
    }


    @Test
    public void visible_defaultsToFalse ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        assertFalse ( component.visible );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void width_isPubliclyModifiable ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        component.width = 100.0;
        assertEquals ( 100.0, component.width, EPSILON );
    }


    @Test
    public void height_isPubliclyModifiable ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        component.height = 50.0;
        assertEquals ( 50.0, component.height, EPSILON );
    }


    @Test
    public void origin_isPubliclyModifiable ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        component.origin = new Vector2D ( 25.0, 30.0 );
        assertEquals ( 25.0, component.origin.getX (), EPSILON );
        assertEquals ( 30.0, component.origin.getY (), EPSILON );
    }


    @Test
    public void color_isPubliclyModifiable ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        component.color = new int[] { 0, 255, 0 };
        assertArrayEquals ( new int[] { 0, 255, 0 }, component.color );
    }


    @Test
    public void crosshair_isPubliclyModifiable ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        component.crosshair = false;
        assertFalse ( component.crosshair );
    }


    @Test
    public void visible_isPubliclyModifiable ()
    {
        ComponentGeometryRectangle component = new ComponentGeometryRectangle ();
        component.visible = true;
        assertTrue ( component.visible );
    }
}
