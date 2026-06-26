// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemSimulationGravity
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Applies pairwise gravitational attraction between all particles using Newton's law of universal gravitation.
//   A softening parameter prevents singularities when particles are very close together.
//
//   Force magnitude: F = G * m1 * m2 / (distSq + epsilon^2)
//
//   Forces are accumulated symmetrically: particle 1 is attracted toward particle 2 and vice versa.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.systems;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.TextFormat;
import rohin.gameengine.Vector2D;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.*;

import java.util.ArrayList;

public class SystemSimulationGravity extends ECSSystem
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private EngineParticleSimulator engine;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public SystemSimulationGravity ( Integer id, String name, EngineParticleSimulator engine )
    {
        super ( id, name, engine );
        this.engine = engine;
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Update.
    //
    // Applies pairwise gravitational attraction between all particles. Skips computation when the world is paused.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            // Type tokens for component filtering.

            ComponentWorld         worldToken    = new ComponentWorld ();
            ComponentParticleGroup particleToken = new ComponentParticleGroup ();

            // Check if world is paused.

            double gravitationalConstant = 1.0;

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );

                    if ( world.paused || !world.gravityEnabled )
                    {
                        return;
                    }

                    gravitationalConstant = world.gravitationalConstant;
                    break;
                }
            }

            // Read softening epsilon from settings.

            double softeningEpsilon = this.engine.getApplication ().getSettings ().getDouble ( Constants.KEY_PHYSICS_SOFTENING_EPSILON );

            // Collect all particle entities.

            ArrayList <ECSEntity> particles = new ArrayList <ECSEntity> ();

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    particles.add ( entity );
                }
            }

            // Double loop: pairwise gravitational attraction.

            int count = particles.size ();

            for ( int i = 0; i < count; i++ )
            {
                ECSEntity               e1          = particles.get ( i );
                ComponentTransform      transform1  = (ComponentTransform) e1.getComponent ( Constants.COMPONENT_TRANSFORM );
                ComponentPhysics        physics1    = (ComponentPhysics) e1.getComponent ( Constants.COMPONENT_PHYSICS );
                ComponentGeometryCircle geometry1   = (ComponentGeometryCircle) e1.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );

                for ( int j = i + 1; j < count; j++ )
                {
                    ECSEntity               e2          = particles.get ( j );
                    ComponentTransform      transform2  = (ComponentTransform) e2.getComponent ( Constants.COMPONENT_TRANSFORM );
                    ComponentPhysics        physics2    = (ComponentPhysics) e2.getComponent ( Constants.COMPONENT_PHYSICS );
                    ComponentGeometryCircle geometry2   = (ComponentGeometryCircle) e2.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );

                    // Calculate direction vector from particle 1 to particle 2.

                    double dx = transform2.translation.getX () - transform1.translation.getX ();
                    double dy = transform2.translation.getY () - transform1.translation.getY ();

                    // Calculate distance squared (unsoftened) and softened distance squared.

                    double distSq     = dx * dx + dy * dy;
                    double distSqSoft = distSq + softeningEpsilon * softeningEpsilon;

                    // Calculate force magnitude: F = G * m1 * m2 / distSqSoft.

                    double forceMagnitude = gravitationalConstant * physics1.mass * physics2.mass / distSqSoft;

                    // Calculate distance (unsoftened) for normalization.
                    // Skip gravity for overlapping pairs — the collider handles contact.
                    // This prevents the strong close-range gravitational force from injecting
                    // energy during overlap via discrete Euler integration.

                    double dist    = Math.sqrt ( distSq );
                    double minDist = geometry1.radius + geometry2.radius;

                    if ( dist > minDist )
                    {
                        // Normalize direction.

                        double nx = dx / dist;
                        double ny = dy / dist;

                        // Calculate force components.

                        double fx = forceMagnitude * nx;
                        double fy = forceMagnitude * ny;

                        // Add force to particle 1's forceAccumulator (attracted toward particle 2).

                        physics1.forceAccumulator.setVector (
                                physics1.forceAccumulator.getX () + fx,
                                physics1.forceAccumulator.getY () + fy
                        );

                        // Add force to particle 2's forceAccumulator (attracted toward particle 1).

                        physics2.forceAccumulator.setVector (
                                physics2.forceAccumulator.getX () - fx,
                                physics2.forceAccumulator.getY () - fy
                        );
                    }
                }
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }
    }
}
