package rohin.gameengine.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentPhysicsTest
//
// Unit tests for ComponentPhysics.
//
// ========================================================================================

public class ComponentPhysicsTest
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
        ComponentPhysics component = new ComponentPhysics ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void mass_defaultsTo1 ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        assertEquals ( 1.0, component.mass, EPSILON );
    }


    @Test
    public void velocity_defaultsToZeroVector ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        assertNotNull ( component.velocity );
        assertEquals ( 0.0, component.velocity.getX (), EPSILON );
        assertEquals ( 0.0, component.velocity.getY (), EPSILON );
    }


    @Test
    public void forceAccumulator_defaultsToZeroVector ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        assertNotNull ( component.forceAccumulator );
        assertEquals ( 0.0, component.forceAccumulator.getX (), EPSILON );
        assertEquals ( 0.0, component.forceAccumulator.getY (), EPSILON );
    }


    @Test
    public void elasticityCoefficient_defaultsTo0Point9 ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        assertEquals ( 0.9, component.elasticityCoefficient, EPSILON );
    }


    @Test
    public void frictionCoefficient_defaultsTo0Point995 ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        assertEquals ( 0.995, component.frictionCoefficient, EPSILON );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void mass_isPubliclyModifiable ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        component.mass = 5.0;
        assertEquals ( 5.0, component.mass, EPSILON );
    }


    @Test
    public void velocity_isPubliclyModifiable ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        component.velocity = new Vector2D ( 10.0, -5.0 );
        assertEquals ( 10.0, component.velocity.getX (), EPSILON );
        assertEquals ( -5.0, component.velocity.getY (), EPSILON );
    }


    @Test
    public void forceAccumulator_isPubliclyModifiable ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        component.forceAccumulator = new Vector2D ( 100.0, -50.0 );
        assertEquals ( 100.0, component.forceAccumulator.getX (), EPSILON );
        assertEquals ( -50.0, component.forceAccumulator.getY (), EPSILON );
    }


    @Test
    public void elasticityCoefficient_isPubliclyModifiable ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        component.elasticityCoefficient = 0.5;
        assertEquals ( 0.5, component.elasticityCoefficient, EPSILON );
    }


    @Test
    public void frictionCoefficient_isPubliclyModifiable ()
    {
        ComponentPhysics component = new ComponentPhysics ();
        component.frictionCoefficient = 0.8;
        assertEquals ( 0.8, component.frictionCoefficient, EPSILON );
    }


}
