package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;


// ========================================================================================
//
// ComponentButtonImageTest
//
// Unit tests for ComponentButtonImage.
//
// ========================================================================================

public class ComponentButtonImageTest
{
    // ========================================================================================
    //
    // Constructor Tests
    //
    // ========================================================================================

    @Test
    public void defaultConstructor_createsNonNullInstance ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void imageUp_defaultsToEmptyString ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertEquals ( "", component.imageUp );
    }


    @Test
    public void imageUpSelected_defaultsToEmptyString ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertEquals ( "", component.imageUpSelected );
    }


    @Test
    public void imageDown_defaultsToEmptyString ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertEquals ( "", component.imageDown );
    }


    @Test
    public void imageDownSelected_defaultsToEmptyString ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertEquals ( "", component.imageDownSelected );
    }


    @Test
    public void imageDisabled_defaultsToEmptyString ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertEquals ( "", component.imageDisabled );
    }


    @Test
    public void imageShadow_defaultsToEmptyString ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        assertEquals ( "", component.imageShadow );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void imageUp_isPubliclyModifiable ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        component.imageUp = "button_up.png";
        assertEquals ( "button_up.png", component.imageUp );
    }


    @Test
    public void imageUpSelected_isPubliclyModifiable ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        component.imageUpSelected = "button_up_selected.png";
        assertEquals ( "button_up_selected.png", component.imageUpSelected );
    }


    @Test
    public void imageDown_isPubliclyModifiable ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        component.imageDown = "button_down.png";
        assertEquals ( "button_down.png", component.imageDown );
    }


    @Test
    public void imageDownSelected_isPubliclyModifiable ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        component.imageDownSelected = "button_down_selected.png";
        assertEquals ( "button_down_selected.png", component.imageDownSelected );
    }


    @Test
    public void imageDisabled_isPubliclyModifiable ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        component.imageDisabled = "button_disabled.png";
        assertEquals ( "button_disabled.png", component.imageDisabled );
    }


    @Test
    public void imageShadow_isPubliclyModifiable ()
    {
        ComponentButtonImage component = new ComponentButtonImage ();
        component.imageShadow = "button_shadow.png";
        assertEquals ( "button_shadow.png", component.imageShadow );
    }
}
