package rohin.gameengine.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentResourceBackgroundImage;

public class EntityMenuBackgroundTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntityMenuBackground entity = new EntityMenuBackground ( 1, "MenuBackground", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntityMenuBackground entity = new EntityMenuBackground ( 1, "MenuBackground", null );
        assertEquals ( 1, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntityMenuBackground entity = new EntityMenuBackground ( 1, "MenuBackground", null );
        assertEquals ( "MenuBackground", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntityMenuBackground entity = new EntityMenuBackground ( 1, "MenuBackground", null );
        assertEquals ( 1, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentResourceBackgroundImage ()
    {
        EntityMenuBackground entity = new EntityMenuBackground ( 1, "MenuBackground", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentResourceBackgroundImage.class, component );
    }
}
