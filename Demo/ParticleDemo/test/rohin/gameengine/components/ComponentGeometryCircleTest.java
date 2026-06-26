package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentGeometryCircleTest
//
// Unit tests for ComponentGeometryCircle.
//
// ========================================================================================

public class ComponentGeometryCircleTest
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
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void radius_defaultsTo1 ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        assertEquals ( 1.0, component.radius, EPSILON );
    }


    @Test
    public void origin_defaultsToZeroVector ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        assertNotNull ( component.origin );
        assertEquals ( 0.0, component.origin.getX (), EPSILON );
        assertEquals ( 0.0, component.origin.getY (), EPSILON );
    }


    @Test
    public void color_defaultsToWhite ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        assertArrayEquals ( new int[] { 255, 255, 255 }, component.color );
    }


    @Test
    public void crosshair_defaultsToTrue ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        assertTrue ( component.crosshair );
    }


    @Test
    public void visible_defaultsToFalse ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        assertFalse ( component.visible );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void radius_isPubliclyModifiable ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        component.radius = 32.0;
        assertEquals ( 32.0, component.radius, EPSILON );
    }


    @Test
    public void origin_isPubliclyModifiable ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        component.origin = new Vector2D ( 50.0, 75.0 );
        assertEquals ( 50.0, component.origin.getX (), EPSILON );
        assertEquals ( 75.0, component.origin.getY (), EPSILON );
    }


    @Test
    public void color_isPubliclyModifiable ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        component.color = new int[] { 255, 0, 0 };
        assertArrayEquals ( new int[] { 255, 0, 0 }, component.color );
    }


    @Test
    public void crosshair_isPubliclyModifiable ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        component.crosshair = false;
        assertFalse ( component.crosshair );
    }


    @Test
    public void visible_isPubliclyModifiable ()
    {
        ComponentGeometryCircle component = new ComponentGeometryCircle ();
        component.visible = true;
        assertTrue ( component.visible );
    }
}
