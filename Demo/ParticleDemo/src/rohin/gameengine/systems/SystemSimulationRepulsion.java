// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemSimulationRepulsion
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Applies pairwise short-range repulsive force between particles.
//
//   Uses a smooth polynomial kernel that ramps from zero at the threshold edge to maximum
//   at contact. The normalized gap variable t = (dist - minDist) / (threshold - minDist)
//   maps the buffer zone to [0, 1], and the force magnitude is R * m1 * m2 * (1 - t)^2.
//
//   This avoids the singularity and steep gradient of inverse-power laws, producing a
//   gentle cushion effect rather than an elastic bounce at distance.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.systems;

import java.util.ArrayList;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.TextFormat;
import rohin.gameengine.Vector2D;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.*;

public class SystemSimulationRepulsion extends ECSSystem
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

    public SystemSimulationRepulsion ( Integer id, String name, EngineParticleSimulator engine )
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
    // Iterates all particle pairs and applies a smooth short-range repulsive force using a
    // quadratic kernel. The force is only applied when particles are within 2x the sum of
    // their radii.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            // Type tokens for component filtering.

            ComponentWorld         worldToken    = new ComponentWorld ();
            ComponentParticleGroup particleToken = new ComponentParticleGroup ();

            // Check if the world is paused.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );

                    if ( world.paused || !world.repulsionEnabled )
                    {
                        return;
                    }

                    break;
                }
            }

            // Read the repulsive constant from the world entity.

            double repulsiveConstant = 500.0;

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );
                    repulsiveConstant = world.repulsiveConstant;
                    break;
                }
            }

            // Collect all particle entities.

            ArrayList <ECSEntity> particles = new ArrayList <ECSEntity> ();

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    particles.add ( entity );
                }
            }

            // Double loop over all particle pairs.

            for ( int i = 0; i < particles.size (); i++ )
            {
                ECSEntity entity1 = particles.get ( i );

                ComponentTransform      transform1 = (ComponentTransform) entity1.getComponent ( Constants.COMPONENT_TRANSFORM );
                ComponentPhysics        physics1   = (ComponentPhysics) entity1.getComponent ( Constants.COMPONENT_PHYSICS );
                ComponentGeometryCircle geometry1  = (ComponentGeometryCircle) entity1.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );

                for ( int j = i + 1; j < particles.size (); j++ )
                {
                    ECSEntity entity2 = particles.get ( j );

                    ComponentTransform      transform2 = (ComponentTransform) entity2.getComponent ( Constants.COMPONENT_TRANSFORM );
                    ComponentPhysics        physics2   = (ComponentPhysics) entity2.getComponent ( Constants.COMPONENT_PHYSICS );
                    ComponentGeometryCircle geometry2  = (ComponentGeometryCircle) entity2.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );

                    // Calculate direction away from the other particle.

                    double dx = transform1.translation.getX () - transform2.translation.getX ();
                    double dy = transform1.translation.getY () - transform2.translation.getY ();

                    // Calculate distance.

                    double dist = Math.sqrt ( dx * dx + dy * dy );

                    // Only apply repulsion within a threshold distance, but not inside the
                    // collision overlap zone. The collider handles contact; repulsion is the
                    // soft cushion before contact.

                    double minDist   = geometry1.radius + geometry2.radius;
                    double threshold = minDist * 2.0;

                    if ( dist > minDist && dist < threshold )
                    {
                        // Normalized gap: 0 at contact (dist = minDist), 1 at threshold edge.

                        double s       = ( dist - minDist ) / ( threshold - minDist );
                        double oneMinS = 1.0 - s;

                        // Smooth quadratic kernel: F = R * (1 - s)^2.
                        // Mass-independent — repulsion is a geometric proximity effect.
                        // Newton's second law (a = F/m) naturally makes lighter particles
                        // accelerate more.

                        double forceMagnitude = repulsiveConstant * oneMinS * oneMinS;

                        // Normalize direction and scale by force magnitude.

                        double nx = dx / dist;
                        double ny = dy / dist;

                        double fx = forceMagnitude * nx;
                        double fy = forceMagnitude * ny;

                        // Add force to particle 1's accumulator (pushed away from p2).

                        physics1.forceAccumulator.setVector (
                                physics1.forceAccumulator.getX () + fx,
                                physics1.forceAccumulator.getY () + fy
                        );

                        // Add force to particle 2's accumulator (pushed away from p1).

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
