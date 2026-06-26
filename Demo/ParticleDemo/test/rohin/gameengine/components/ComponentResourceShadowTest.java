package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentResourceShadowTest
//
// Unit tests for ComponentResourceShadow.
//
// ========================================================================================

public class ComponentResourceShadowTest
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
        ComponentResourceShadow component = new ComponentResourceShadow ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void imagePath_defaultsToEmptyString ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        assertEquals ( "", component.imagePath );
    }


    @Test
    public void offset_defaultsToTenTenVector ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        assertNotNull ( component.offset );
        assertEquals ( 10.0, component.offset.getX (), EPSILON );
        assertEquals ( 10.0, component.offset.getY (), EPSILON );
    }


    @Test
    public void opacity_defaultsTo0Point8 ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        assertEquals ( 0.8, component.opacity, EPSILON );
    }


    @Test
    public void scale_defaultsTo1Point0 ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        assertEquals ( 1.0, component.scale, EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void imagePath_isPubliclyModifiable ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        component.imagePath = "shadow.png";
        assertEquals ( "shadow.png", component.imagePath );
    }


    @Test
    public void offset_isPubliclyModifiable ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        component.offset = new Vector2D ( 5.0, 15.0 );
        assertEquals ( 5.0, component.offset.getX (), EPSILON );
        assertEquals ( 15.0, component.offset.getY (), EPSILON );
    }


    @Test
    public void opacity_isPubliclyModifiable ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        component.opacity = 0.3;
        assertEquals ( 0.3, component.opacity, EPSILON );
    }


    @Test
    public void scale_isPubliclyModifiable ()
    {
        ComponentResourceShadow component = new ComponentResourceShadow ();
        component.scale = 1.5;
        assertEquals ( 1.5, component.scale, EPSILON );
    }
}
