package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;
import rohin.gameengine.Vector3D;


// ========================================================================================
//
// ComponentTransformTest
//
// Unit tests for ComponentTransform.
//
// ========================================================================================

public class ComponentTransformTest
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
        ComponentTransform component = new ComponentTransform ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentTransform component = new ComponentTransform ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void origin_defaultsToZeroVector ()
    {
        ComponentTransform component = new ComponentTransform ();
        assertNotNull ( component.origin );
        assertEquals ( 0.0, component.origin.getX (), EPSILON );
        assertEquals ( 0.0, component.origin.getY (), EPSILON );
    }


    @Test
    public void scale_defaultsToOneOneVector ()
    {
        ComponentTransform component = new ComponentTransform ();
        assertNotNull ( component.scale );
        assertEquals ( 1.0, component.scale.getX (), EPSILON );
        assertEquals ( 1.0, component.scale.getY (), EPSILON );
    }


    @Test
    public void rotation_defaultsToZeroVector3D ()
    {
        ComponentTransform component = new ComponentTransform ();
        assertNotNull ( component.rotation );
        assertEquals ( 0.0, component.rotation.getX (), EPSILON );
        assertEquals ( 0.0, component.rotation.getY (), EPSILON );
        assertEquals ( 0.0, component.rotation.getZ (), EPSILON );
    }


    @Test
    public void translation_defaultsToZeroVector ()
    {
        ComponentTransform component = new ComponentTransform ();
        assertNotNull ( component.translation );
        assertEquals ( 0.0, component.translation.getX (), EPSILON );
        assertEquals ( 0.0, component.translation.getY (), EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void origin_isPubliclyModifiable ()
    {
        ComponentTransform component = new ComponentTransform ();
        component.origin = new Vector2D ( 10.0, 20.0 );
        assertEquals ( 10.0, component.origin.getX (), EPSILON );
        assertEquals ( 20.0, component.origin.getY (), EPSILON );
    }


    @Test
    public void scale_isPubliclyModifiable ()
    {
        ComponentTransform component = new ComponentTransform ();
        component.scale = new Vector2D ( 2.0, 3.0 );
        assertEquals ( 2.0, component.scale.getX (), EPSILON );
        assertEquals ( 3.0, component.scale.getY (), EPSILON );
    }


    @Test
    public void rotation_isPubliclyModifiable ()
    {
        ComponentTransform component = new ComponentTransform ();
        component.rotation = new Vector3D ( 45.0, 90.0, 180.0 );
        assertEquals ( 45.0, component.rotation.getX (), EPSILON );
        assertEquals ( 90.0, component.rotation.getY (), EPSILON );
        assertEquals ( 180.0, component.rotation.getZ (), EPSILON );
    }


    @Test
    public void translation_isPubliclyModifiable ()
    {
        ComponentTransform component = new ComponentTransform ();
        component.translation = new Vector2D ( 100.0, 200.0 );
        assertEquals ( 100.0, component.translation.getX (), EPSILON );
        assertEquals ( 200.0, component.translation.getY (), EPSILON );
    }
}
