package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentProjection2DTest
//
// Unit tests for ComponentProjection2D.
//
// ========================================================================================

public class ComponentProjection2DTest
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
        ComponentProjection2D component = new ComponentProjection2D ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void window_defaultsToAllZeros ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        assertNotNull ( component.window );
        assertArrayEquals ( new double[] { 0.0, 0.0, 0.0, 0.0 }, component.window, EPSILON );
    }


    @Test
    public void viewPort_defaultsToAllZeros ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        assertNotNull ( component.viewPort );
        assertArrayEquals ( new double[] { 0.0, 0.0, 0.0, 0.0 }, component.viewPort, EPSILON );
    }


    @Test
    public void origin_defaultsToOneOneVector ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        assertNotNull ( component.origin );
        assertEquals ( 1.0, component.origin.getX (), EPSILON );
        assertEquals ( 1.0, component.origin.getY (), EPSILON );
    }


    @Test
    public void scale_defaultsToOneNegativeOneVector ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        assertNotNull ( component.scale );
        assertEquals ( 1.0, component.scale.getX (), EPSILON );
        assertEquals ( -1.0, component.scale.getY (), EPSILON );
    }


    @Test
    public void aspect_defaultsToOneOneVector ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        assertNotNull ( component.aspect );
        assertEquals ( 1.0, component.aspect.getX (), EPSILON );
        assertEquals ( 1.0, component.aspect.getY (), EPSILON );
    }


    @Test
    public void layer_defaultsTo0 ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        assertEquals ( 0.0, component.layer, EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void window_isPubliclyModifiable ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        component.window = new double[] { -400.0, -300.0, 400.0, 300.0 };
        assertArrayEquals ( new double[] { -400.0, -300.0, 400.0, 300.0 }, component.window, EPSILON );
    }


    @Test
    public void viewPort_isPubliclyModifiable ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        component.viewPort = new double[] { 0.0, 0.0, 800.0, 600.0 };
        assertArrayEquals ( new double[] { 0.0, 0.0, 800.0, 600.0 }, component.viewPort, EPSILON );
    }


    @Test
    public void origin_isPubliclyModifiable ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        component.origin = new Vector2D ( 0.5, 0.5 );
        assertEquals ( 0.5, component.origin.getX (), EPSILON );
        assertEquals ( 0.5, component.origin.getY (), EPSILON );
    }


    @Test
    public void scale_isPubliclyModifiable ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        component.scale = new Vector2D ( 2.0, 2.0 );
        assertEquals ( 2.0, component.scale.getX (), EPSILON );
        assertEquals ( 2.0, component.scale.getY (), EPSILON );
    }


    @Test
    public void aspect_isPubliclyModifiable ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        component.aspect = new Vector2D ( 16.0, 9.0 );
        assertEquals ( 16.0, component.aspect.getX (), EPSILON );
        assertEquals ( 9.0, component.aspect.getY (), EPSILON );
    }


    @Test
    public void layer_isPubliclyModifiable ()
    {
        ComponentProjection2D component = new ComponentProjection2D ();
        component.layer = 5.0;
        assertEquals ( 5.0, component.layer, EPSILON );
    }
}
