package rohin.gameengine.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentWorld;
import rohin.gameengine.components.ComponentResourceBackgroundImage;

public class EntityParticleWorldTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntityParticleWorld entity = new EntityParticleWorld ( 0, "ParticleWorld", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntityParticleWorld entity = new EntityParticleWorld ( 17, "ParticleWorld", null );
        assertEquals ( 17, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntityParticleWorld entity = new EntityParticleWorld ( 0, "SimulationWorld", null );
        assertEquals ( "SimulationWorld", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntityParticleWorld entity = new EntityParticleWorld ( 0, "ParticleWorld", null );
        assertEquals ( 2, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentWorld ()
    {
        EntityParticleWorld entity = new EntityParticleWorld ( 0, "ParticleWorld", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_WORLD );
        assertNotNull ( component );
        assertInstanceOf ( ComponentWorld.class, component );
    }

    @Test
    void entity_hasComponentResourceBackgroundImage ()
    {
        EntityParticleWorld entity = new EntityParticleWorld ( 0, "ParticleWorld", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentResourceBackgroundImage.class, component );
    }
}
