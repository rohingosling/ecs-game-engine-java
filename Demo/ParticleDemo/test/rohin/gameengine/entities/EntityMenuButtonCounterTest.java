package rohin.gameengine.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentButtonImage;
import rohin.gameengine.components.ComponentButtonText;
import rohin.gameengine.components.ComponentButtonState;
import rohin.gameengine.components.ComponentParticleCount;
import rohin.gameengine.components.ComponentGeometryRectangle;

public class EntityMenuButtonCounterTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "CounterButton", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 7, "CounterButton", null );
        assertEquals ( 7, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "RedCounter", null );
        assertEquals ( "RedCounter", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "CounterButton", null );
        assertEquals ( 5, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentButtonImage ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "CounterButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_BUTTON_IMAGE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentButtonImage.class, component );
    }

    @Test
    void entity_hasComponentButtonText ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "CounterButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_BUTTON_TEXT );
        assertNotNull ( component );
        assertInstanceOf ( ComponentButtonText.class, component );
    }

    @Test
    void entity_hasComponentButtonState ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "CounterButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentButtonState.class, component );
    }

    @Test
    void entity_hasComponentParticleCount ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "CounterButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_PARTICLE_COUNT );
        assertNotNull ( component );
        assertInstanceOf ( ComponentParticleCount.class, component );
    }

    @Test
    void entity_hasComponentGeometryRectangle ()
    {
        EntityMenuButtonCounter entity = new EntityMenuButtonCounter ( 0, "CounterButton", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentGeometryRectangle.class, component );
    }
}
