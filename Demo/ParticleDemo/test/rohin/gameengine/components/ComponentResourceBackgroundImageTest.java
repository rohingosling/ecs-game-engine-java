package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentResourceBackgroundImageTest
//
// Unit tests for ComponentResourceBackgroundImage.
//
// ========================================================================================

public class ComponentResourceBackgroundImageTest
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
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
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
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
        assertEquals ( "", component.imagePath );
    }


    @Test
    public void scale_defaultsTo1 ()
    {
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
        assertEquals ( 1.0, component.scale, EPSILON );
    }


    @Test
    public void position_defaultsToZeroVector ()
    {
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
        assertNotNull ( component.position );
        assertEquals ( 0.0, component.position.getX (), EPSILON );
        assertEquals ( 0.0, component.position.getY (), EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void imagePath_isPubliclyModifiable ()
    {
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
        component.imagePath = "background.png";
        assertEquals ( "background.png", component.imagePath );
    }


    @Test
    public void scale_isPubliclyModifiable ()
    {
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
        component.scale = 2.5;
        assertEquals ( 2.5, component.scale, EPSILON );
    }


    @Test
    public void position_isPubliclyModifiable ()
    {
        ComponentResourceBackgroundImage component = new ComponentResourceBackgroundImage ();
        component.position = new Vector2D ( 100.0, 200.0 );
        assertEquals ( 100.0, component.position.getX (), EPSILON );
        assertEquals ( 200.0, component.position.getY (), EPSILON );
    }
}
