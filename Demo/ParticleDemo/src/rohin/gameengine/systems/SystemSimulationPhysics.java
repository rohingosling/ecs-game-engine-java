// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemSimulationPhysics
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Integrates particle positions from velocity, applies friction damping, and
//   records position history for trail rendering.
//
//   Position:  position += velocity * dt
//   Friction:  velocity *= frictionCoefficient  (per frame)
//   Anisotropic friction is applied when user input is active — the axis of
//   user acceleration gets normal friction, the perpendicular axis gets
//   stronger anisotropic friction.
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

public class SystemSimulationPhysics extends ECSSystem
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

    public SystemSimulationPhysics ( Integer id, String name, EngineParticleSimulator engine )
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
    // Integrates position, applies friction, and records translation history.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            // Check if paused.

            ComponentWorld worldToken = new ComponentWorld ();
            boolean frictionEnabled = true;

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );

                    if ( world.paused )
                    {
                        return;
                    }

                    frictionEnabled = world.frictionEnabled;
                    break;
                }
            }

            // Load anisotropic friction coefficient from settings.

            double anisotropicFriction = this.engine.getApplication ().getSettings ().getDouble ( Constants.KEY_PHYSICS_FRICTION_ANISOTROPIC );

            // Convert dt from milliseconds to seconds.

            double dt = t / 1000.0;

            // Process all particles.

            ComponentParticleGroup particleToken     = new ComponentParticleGroup ();
            ComponentUserControl   userControlToken  = new ComponentUserControl ();

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    ComponentPhysics    physics   = (ComponentPhysics) entity.getComponent ( Constants.COMPONENT_PHYSICS );
                    ComponentTransform  transform = (ComponentTransform) entity.getComponent ( Constants.COMPONENT_TRANSFORM );

                    // Integrate position: position += velocity * dt.

                    double px = transform.translation.getX () + physics.velocity.getX () * dt;
                    double py = transform.translation.getY () + physics.velocity.getY () * dt;
                    transform.translation.setVector ( px, py );

                    // Apply friction (if enabled).

                    if ( !frictionEnabled )
                    {
                        // Skip friction — velocity is unchanged.
                    }
                    else if ( entity.hasComponents ( userControlToken ) )
                    {
                        // Anisotropic friction for user-controlled particles.

                        ComponentUserControl userControl = (ComponentUserControl) entity.getComponent ( Constants.COMPONENT_USER_CONTROL );

                        boolean userInputX = userControl.accelerateLeft || userControl.accelerateRight;
                        boolean userInputY = userControl.accelerateUp   || userControl.accelerateDown;

                        double frictionX = userInputX ? physics.frictionCoefficient : anisotropicFriction;
                        double frictionY = userInputY ? physics.frictionCoefficient : anisotropicFriction;

                        physics.velocity.setVector (
                                physics.velocity.getX () * frictionX,
                                physics.velocity.getY () * frictionY
                        );
                    }
                    else
                    {
                        // Normal isotropic friction.

                        physics.velocity.setVector (
                                physics.velocity.getX () * physics.frictionCoefficient,
                                physics.velocity.getY () * physics.frictionCoefficient
                        );
                    }

                    // Record position in translation history.

                    ComponentTranslationHistory trail = (ComponentTranslationHistory) entity.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );

                    trail.translationHistory.addLast ( new Vector2D ( px, py ) );

                    while ( trail.translationHistory.size () > trail.translationHistoryDepth )
                    {
                        trail.translationHistory.removeFirst ();
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
