// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemSimulationForceAccumulator
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Reads user control input, adds user force to the force accumulator, then integrates force
//   into velocity (a = F/m, v += a*dt) and clears the force accumulator.
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

public class SystemSimulationForceAccumulator extends ECSSystem
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

    public SystemSimulationForceAccumulator ( Integer id, String name, EngineParticleSimulator engine )
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
    // For each particle, applies user control forces to the force accumulator, integrates the
    // accumulated force into velocity using Newton's second law (F = ma), and clears the force
    // accumulator for the next frame.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            // Type tokens for component filtering.

            ComponentWorld         worldToken       = new ComponentWorld ();
            ComponentParticleGroup particleToken    = new ComponentParticleGroup ();
            ComponentUserControl   userControlToken = new ComponentUserControl ();

            // Check if the world is paused.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );

                    if ( world.paused )
                    {
                        return;
                    }

                    break;
                }
            }

            // Process each particle entity.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    ComponentPhysics physics = (ComponentPhysics) entity.getComponent ( Constants.COMPONENT_PHYSICS );

                    // If this particle has user control, apply user forces.

                    if ( entity.hasComponents ( userControlToken ) )
                    {
                        ComponentUserControl userControl = (ComponentUserControl) entity.getComponent ( Constants.COMPONENT_USER_CONTROL );

                        double fx = 0.0;
                        double fy = 0.0;

                        if ( userControl.accelerateUp )
                        {
                            fy -= userControl.accelerationMagnitude;
                        }

                        if ( userControl.accelerateDown )
                        {
                            fy += userControl.accelerationMagnitude;
                        }

                        if ( userControl.accelerateLeft )
                        {
                            fx -= userControl.accelerationMagnitude;
                        }

                        if ( userControl.accelerateRight )
                        {
                            fx += userControl.accelerationMagnitude;
                        }

                        // Add user force to the force accumulator.

                        physics.forceAccumulator.setVector (
                                physics.forceAccumulator.getX () + fx,
                                physics.forceAccumulator.getY () + fy
                        );
                    }

                    // Calculate acceleration: a = F / m.

                    double ax = physics.forceAccumulator.getX () / physics.mass;
                    double ay = physics.forceAccumulator.getY () / physics.mass;

                    // Convert dt from milliseconds to seconds.

                    double dt = t / 1000.0;

                    // Update velocity: v += a * dt.

                    physics.velocity.setVector (
                            physics.velocity.getX () + ax * dt,
                            physics.velocity.getY () + ay * dt
                    );

                    // Clear force accumulator for the next frame.

                    physics.forceAccumulator.setVector ( 0.0, 0.0 );
                }
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }
    }
}
