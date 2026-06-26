package rohin.gameengine.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentGeometryCircle;
import rohin.gameengine.components.ComponentProjection2D;
import rohin.gameengine.components.ComponentResourceSprite;
import rohin.gameengine.components.ComponentResourceShadow;
import rohin.gameengine.components.ComponentTransform;
import rohin.gameengine.components.ComponentTranslationHistory;
import rohin.gameengine.components.ComponentPhysics;

public class EntityParticleGroupTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 13, "ParticleGroup", null );
        assertEquals ( 13, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "RedGroup", null );
        assertEquals ( "RedGroup", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        assertEquals ( 7, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentGeometryCircle ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentGeometryCircle.class, component );
    }

    @Test
    void entity_hasComponentProjection2D ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );
        assertNotNull ( component );
        assertInstanceOf ( ComponentProjection2D.class, component );
    }

    @Test
    void entity_hasComponentResourceSprite ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentResourceSprite.class, component );
    }

    @Test
    void entity_hasComponentResourceShadow ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_RESOURCE_SHADOW );
        assertNotNull ( component );
        assertInstanceOf ( ComponentResourceShadow.class, component );
    }

    @Test
    void entity_hasComponentTransform ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_TRANSFORM );
        assertNotNull ( component );
        assertInstanceOf ( ComponentTransform.class, component );
    }

    @Test
    void entity_hasComponentTranslationHistory ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );
        assertNotNull ( component );
        assertInstanceOf ( ComponentTranslationHistory.class, component );
    }

    @Test
    void entity_hasComponentPhysics ()
    {
        EntityParticleGroup entity = new EntityParticleGroup ( 0, "ParticleGroup", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_PHYSICS );
        assertNotNull ( component );
        assertInstanceOf ( ComponentPhysics.class, component );
    }
}
