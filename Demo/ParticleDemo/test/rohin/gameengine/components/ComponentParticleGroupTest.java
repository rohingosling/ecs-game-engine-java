package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;


// ========================================================================================
//
// ComponentParticleGroupTest
//
// Unit tests for ComponentParticleGroup.
//
// ========================================================================================

public class ComponentParticleGroupTest
{
    // ========================================================================================
    //
    // Constructor Tests
    //
    // ========================================================================================

    @Test
    public void defaultConstructor_createsNonNullInstance ()
    {
        ComponentParticleGroup component = new ComponentParticleGroup ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentParticleGroup component = new ComponentParticleGroup ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void groupId_defaultsToNegativeOne ()
    {
        ComponentParticleGroup component = new ComponentParticleGroup ();
        assertEquals ( -1, component.groupId );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void groupId_isPubliclyModifiable ()
    {
        ComponentParticleGroup component = new ComponentParticleGroup ();
        component.groupId = 42;
        assertEquals ( 42, component.groupId );
    }
}
