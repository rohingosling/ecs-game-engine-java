package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;


// ========================================================================================
//
// ComponentParticleCountTest
//
// Unit tests for ComponentParticleCount.
//
// ========================================================================================

public class ComponentParticleCountTest
{
    // ========================================================================================
    //
    // Constructor Tests
    //
    // ========================================================================================

    @Test
    public void defaultConstructor_createsNonNullInstance ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void particleCount_defaultsTo3 ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        assertEquals ( 3, component.particleCount );
    }


    @Test
    public void particleCountMin_defaultsTo0 ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        assertEquals ( 0, component.particleCountMin );
    }


    @Test
    public void particleCountMax_defaultsTo32 ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        assertEquals ( 32, component.particleCountMax );
    }


    @Test
    public void label_defaultsToEmptyString ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        assertEquals ( "", component.label );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void particleCount_isPubliclyModifiable ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        component.particleCount = 10;
        assertEquals ( 10, component.particleCount );
    }


    @Test
    public void particleCountMin_isPubliclyModifiable ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        component.particleCountMin = 5;
        assertEquals ( 5, component.particleCountMin );
    }


    @Test
    public void particleCountMax_isPubliclyModifiable ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        component.particleCountMax = 64;
        assertEquals ( 64, component.particleCountMax );
    }


    @Test
    public void label_isPubliclyModifiable ()
    {
        ComponentParticleCount component = new ComponentParticleCount ();
        component.label = "Red Particles";
        assertEquals ( "Red Particles", component.label );
    }
}
