// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemSimulationCollider
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Handles collision detection and response for the particle simulation.
//
//   Wall collision:     One-way inward boundary bounce. Particles are clamped
//                       inside screen bounds. Velocity component is reversed
//                       and multiplied by elasticity coefficient.
//
//   Particle collision: Pairwise circle-circle elastic collision with overlap
//                       separation. Uses impulse-based resolution along the
//                       collision normal.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.systems;

import java.util.ArrayList;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.TextFormat;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.*;

public class SystemSimulationCollider extends ECSSystem
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

    public SystemSimulationCollider ( Integer id, String name, EngineParticleSimulator engine )
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
    // Wall bounce + particle-particle elastic collision.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            // Check if paused.

            ComponentWorld worldToken = new ComponentWorld ();
            boolean elasticityEnabled = true;

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );

                    if ( world.paused )
                    {
                        return;
                    }

                    elasticityEnabled = world.elasticityEnabled;
                    break;
                }
            }

            double worldWidth  = (double) this.engine.getApplication ().getScreenWidth ()
                    / this.engine.getApplication ().getScreenHeight ();
            double worldHeight = 1.0;

            // Collect all particle entities.

            ComponentParticleGroup particleToken = new ComponentParticleGroup ();
            ArrayList <ECSEntity> particles = new ArrayList <ECSEntity> ();

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    particles.add ( entity );
                }
            }

            // Wall collision.

            for ( ECSEntity entity : particles )
            {
                ComponentTransform      transform = (ComponentTransform) entity.getComponent ( Constants.COMPONENT_TRANSFORM );
                ComponentPhysics        physics   = (ComponentPhysics) entity.getComponent ( Constants.COMPONENT_PHYSICS );
                ComponentGeometryCircle geometry  = (ComponentGeometryCircle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );

                double px = transform.translation.getX ();
                double py = transform.translation.getY ();
                double vx = physics.velocity.getX ();
                double vy = physics.velocity.getY ();
                double r  = geometry.radius;
                double e  = physics.elasticityCoefficient;

                // Left wall.

                if ( px - r < 0 )
                {
                    px = r;
                    vx = Math.abs ( vx ) * e;
                }

                // Right wall.

                if ( px + r > worldWidth )
                {
                    px = worldWidth - r;
                    vx = -Math.abs ( vx ) * e;
                }

                // Top wall.

                if ( py - r < 0 )
                {
                    py = r;
                    vy = Math.abs ( vy ) * e;
                }

                // Bottom wall.

                if ( py + r > worldHeight )
                {
                    py = worldHeight - r;
                    vy = -Math.abs ( vy ) * e;
                }

                transform.translation.setVector ( px, py );
                physics.velocity.setVector ( vx, vy );
            }

            // Particle-particle collision.
            //
            // Multiple iterations resolve cascading overlaps (e.g. separating A-B
            // pushes B into C). Impulse is only applied on the first iteration;
            // subsequent iterations perform positional separation only.

            int collisionIterations = this.engine.getApplication ().getSettings ().getInteger ( Constants.KEY_PHYSICS_COLLISION_ITERATIONS );

            for ( int iter = 0; iter < collisionIterations; iter++ )
            {
                for ( int i = 0; i < particles.size (); i++ )
                {
                    for ( int j = i + 1; j < particles.size (); j++ )
                    {
                        ECSEntity entityA = particles.get ( i );
                        ECSEntity entityB = particles.get ( j );

                        ComponentTransform      transformA = (ComponentTransform) entityA.getComponent ( Constants.COMPONENT_TRANSFORM );
                        ComponentTransform      transformB = (ComponentTransform) entityB.getComponent ( Constants.COMPONENT_TRANSFORM );
                        ComponentPhysics        physicsA   = (ComponentPhysics) entityA.getComponent ( Constants.COMPONENT_PHYSICS );
                        ComponentPhysics        physicsB   = (ComponentPhysics) entityB.getComponent ( Constants.COMPONENT_PHYSICS );
                        ComponentGeometryCircle geometryA  = (ComponentGeometryCircle) entityA.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
                        ComponentGeometryCircle geometryB  = (ComponentGeometryCircle) entityB.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );

                        double ax = transformA.translation.getX ();
                        double ay = transformA.translation.getY ();
                        double bx = transformB.translation.getX ();
                        double by = transformB.translation.getY ();

                        double dx   = bx - ax;
                        double dy   = by - ay;
                        double dist = Math.sqrt ( dx * dx + dy * dy );

                        double minDist = geometryA.radius + geometryB.radius;

                        if ( dist < minDist && dist > 0 )
                        {
                            // Normalize collision normal.

                            double nx = dx / dist;
                            double ny = dy / dist;

                            // Separate overlapping particles.

                            double overlap = minDist - dist;
                            double totalMass = physicsA.mass + physicsB.mass;

                            double separationA = overlap * ( physicsB.mass / totalMass );
                            double separationB = overlap * ( physicsA.mass / totalMass );

                            transformA.translation.setVector ( ax - nx * separationA, ay - ny * separationA );
                            transformB.translation.setVector ( bx + nx * separationB, by + ny * separationB );

                            // Apply impulse on the first iteration only, to avoid
                            // double-counting velocity exchange on correction passes.

                            if ( iter == 0 && elasticityEnabled )
                            {
                                double vax = physicsA.velocity.getX ();
                                double vay = physicsA.velocity.getY ();
                                double vbx = physicsB.velocity.getX ();
                                double vby = physicsB.velocity.getY ();

                                double relVelN = ( vbx - vax ) * nx + ( vby - vay ) * ny;

                                // Only resolve if particles are approaching.

                                if ( relVelN < 0 )
                                {
                                    // Use average elasticity.

                                    double restitution = ( physicsA.elasticityCoefficient + physicsB.elasticityCoefficient ) / 2.0;

                                    // Impulse magnitude.

                                    double impulse = -( 1 + restitution ) * relVelN / ( 1.0 / physicsA.mass + 1.0 / physicsB.mass );

                                    // Apply impulse.

                                    double impulseAx = impulse * nx / physicsA.mass;
                                    double impulseAy = impulse * ny / physicsA.mass;
                                    double impulseBx = impulse * nx / physicsB.mass;
                                    double impulseBy = impulse * ny / physicsB.mass;

                                    physicsA.velocity.setVector ( vax - impulseAx, vay - impulseAy );
                                    physicsB.velocity.setVector ( vbx + impulseBx, vby + impulseBy );
                                }
                            }
                        }
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
