package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentResourceSpriteTest
//
// Unit tests for ComponentResourceSprite.
//
// ========================================================================================

public class ComponentResourceSpriteTest
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
        ComponentResourceSprite component = new ComponentResourceSprite ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentResourceSprite component = new ComponentResourceSprite ();
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
        ComponentResourceSprite component = new ComponentResourceSprite ();
        assertEquals ( "", component.imagePath );
    }


    @Test
    public void scale_defaultsToOneOneVector ()
    {
        ComponentResourceSprite component = new ComponentResourceSprite ();
        assertNotNull ( component.scale );
        assertEquals ( 1.0, component.scale.getX (), EPSILON );
        assertEquals ( 1.0, component.scale.getY (), EPSILON );
    }


    @Test
    public void opacity_defaultsTo1 ()
    {
        ComponentResourceSprite component = new ComponentResourceSprite ();
        assertEquals ( 1.0, component.opacity, EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void imagePath_isPubliclyModifiable ()
    {
        ComponentResourceSprite component = new ComponentResourceSprite ();
        component.imagePath = "sprite.png";
        assertEquals ( "sprite.png", component.imagePath );
    }


    @Test
    public void scale_isPubliclyModifiable ()
    {
        ComponentResourceSprite component = new ComponentResourceSprite ();
        component.scale = new Vector2D ( 2.0, 3.0 );
        assertEquals ( 2.0, component.scale.getX (), EPSILON );
        assertEquals ( 3.0, component.scale.getY (), EPSILON );
    }


    @Test
    public void opacity_isPubliclyModifiable ()
    {
        ComponentResourceSprite component = new ComponentResourceSprite ();
        component.opacity = 0.5;
        assertEquals ( 0.5, component.opacity, EPSILON );
    }
}
