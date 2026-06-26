package rohin.gameengine.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentButtonImage;
import rohin.gameengine.components.ComponentButtonText;
import rohin.gameengine.components.ComponentButtonState;
import rohin.gameengine.components.ComponentGeometryRectangle;

public class EntityMenuButtonTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 0, "MenuButton", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 5, "MenuButton", null );
        assertEquals ( 5, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 0, "StartButton", null );
        assertEquals ( "StartButton", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 0, "MenuButton", null );
        assertEquals ( 4, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentButtonImage ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 0, "MenuButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_BUTTON_IMAGE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentButtonImage.class, component );
    }

    @Test
    void entity_hasComponentButtonText ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 0, "MenuButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_BUTTON_TEXT );
        assertNotNull ( component );
        assertInstanceOf ( ComponentButtonText.class, component );
    }

    @Test
    void entity_hasComponentButtonState ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 0, "MenuButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentButtonState.class, component );
    }

    @Test
    void entity_hasComponentGeometryRectangle ()
    {
        EntityMenuButton entity = new EntityMenuButton ( 0, "MenuButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentGeometryRectangle.class, component );
    }
}
