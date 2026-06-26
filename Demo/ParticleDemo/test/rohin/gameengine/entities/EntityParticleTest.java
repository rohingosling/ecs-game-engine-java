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
import rohin.gameengine.components.ComponentParticleGroup;

public class EntityParticleTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntityParticle entity = new EntityParticle ( 1000, "Particle", null );
        assertEquals ( 1000, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntityParticle entity = new EntityParticle ( 0, "RedParticle_0", null );
        assertEquals ( "RedParticle_0", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        assertEquals ( 8, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentGeometryCircle ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentGeometryCircle.class, component );
    }

    @Test
    void entity_hasComponentProjection2D ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );
        assertNotNull ( component );
        assertInstanceOf ( ComponentProjection2D.class, component );
    }

    @Test
    void entity_hasComponentResourceSprite ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
        assertNotNull ( component );
        assertInstanceOf ( ComponentResourceSprite.class, component );
    }

    @Test
    void entity_hasComponentResourceShadow ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_RESOURCE_SHADOW );
        assertNotNull ( component );
        assertInstanceOf ( ComponentResourceShadow.class, component );
    }

    @Test
    void entity_hasComponentTransform ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_TRANSFORM );
        assertNotNull ( component );
        assertInstanceOf ( ComponentTransform.class, component );
    }

    @Test
    void entity_hasComponentTranslationHistory ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );
        assertNotNull ( component );
        assertInstanceOf ( ComponentTranslationHistory.class, component );
    }

    @Test
    void entity_hasComponentPhysics ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_PHYSICS );
        assertNotNull ( component );
        assertInstanceOf ( ComponentPhysics.class, component );
    }

    @Test
    void entity_hasComponentParticleGroup ()
    {
        EntityParticle entity = new EntityParticle ( 0, "Particle", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_PARTICLE_GROUP );
        assertNotNull ( component );
        assertInstanceOf ( ComponentParticleGroup.class, component );
    }
}
