// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemSimulationParticleGroupPropagator
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Copies group-level component values down to member particles. This system runs first every frame to ensure
//   all particles reflect their group's current configuration before any other systems process them.
//
//   Selected particles (those with ComponentUserControl) retain their own sprite and trail color, so that the
//   user can visually distinguish the controlled particle from the rest of the group.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.systems;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.TextFormat;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.*;

public class SystemSimulationParticleGroupPropagator extends ECSSystem
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

    public SystemSimulationParticleGroupPropagator ( Integer id, String name, EngineParticleSimulator engine )
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
    // Iterates all particle entities and copies group-level component values down to each particle. Selected
    // particles retain their own sprite and trail color.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            // Type token for component filtering.

            ComponentParticleGroup particleToken  = new ComponentParticleGroup ();
            ComponentUserControl   userToken      = new ComponentUserControl ();

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    // Get the group ID from the particle's ComponentParticleGroup.

                    ComponentParticleGroup particleGroup = (ComponentParticleGroup) entity.getComponent ( Constants.COMPONENT_PARTICLE_GROUP );
                    int groupId = particleGroup.groupId;

                    // Look up the group entity.

                    ECSEntity groupEntity = this.engine.getEntities ().get ( groupId );

                    if ( groupEntity != null )
                    {
                        boolean hasUserControl = entity.hasComponents ( userToken );

                        // Copy ComponentResourceSprite.imagePath (skip if particle has ComponentUserControl).

                        if ( !hasUserControl )
                        {
                            ComponentResourceSprite groupSprite   = (ComponentResourceSprite) groupEntity.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
                            ComponentResourceSprite particleSprite = (ComponentResourceSprite) entity.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );

                            particleSprite.imagePath = groupSprite.imagePath;
                        }

                        // Copy ComponentResourceShadow: imagePath, offset, opacity.

                        ComponentResourceShadow groupShadow    = (ComponentResourceShadow) groupEntity.getComponent ( Constants.COMPONENT_RESOURCE_SHADOW );
                        ComponentResourceShadow particleShadow = (ComponentResourceShadow) entity.getComponent ( Constants.COMPONENT_RESOURCE_SHADOW );

                        particleShadow.imagePath = groupShadow.imagePath;
                        particleShadow.offset.setVector ( groupShadow.offset.getX (), groupShadow.offset.getY () );
                        particleShadow.opacity = groupShadow.opacity;
                        particleShadow.scale   = groupShadow.scale;

                        // Copy ComponentGeometryCircle: radius, visible (keep origin and color as-is).

                        ComponentGeometryCircle groupGeometry    = (ComponentGeometryCircle) groupEntity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
                        ComponentGeometryCircle particleGeometry = (ComponentGeometryCircle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );

                        particleGeometry.radius  = groupGeometry.radius;
                        particleGeometry.visible = groupGeometry.visible;

                        // Copy ComponentPhysics: mass, frictionCoefficient, elasticityCoefficient
                        // (do NOT copy velocity or forceAccumulator).

                        ComponentPhysics groupPhysics    = (ComponentPhysics) groupEntity.getComponent ( Constants.COMPONENT_PHYSICS );
                        ComponentPhysics particlePhysics = (ComponentPhysics) entity.getComponent ( Constants.COMPONENT_PHYSICS );

                        particlePhysics.mass                  = groupPhysics.mass;
                        particlePhysics.frictionCoefficient   = groupPhysics.frictionCoefficient;
                        particlePhysics.elasticityCoefficient = groupPhysics.elasticityCoefficient;

                        // Copy ComponentTranslationHistory: color (skip if ComponentUserControl), translationHistoryDepth,
                        // opacityHead, opacityTail, thickness.

                        ComponentTranslationHistory groupTrail    = (ComponentTranslationHistory) groupEntity.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );
                        ComponentTranslationHistory particleTrail = (ComponentTranslationHistory) entity.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );

                        if ( !hasUserControl )
                        {
                            particleTrail.color = groupTrail.color;
                        }

                        particleTrail.translationHistoryDepth = groupTrail.translationHistoryDepth;
                        particleTrail.opacityHead             = groupTrail.opacityHead;
                        particleTrail.opacityTail             = groupTrail.opacityTail;
                        particleTrail.thickness               = groupTrail.thickness;

                        // Copy ComponentProjection2D: scale.

                        ComponentProjection2D groupProjection    = (ComponentProjection2D) groupEntity.getComponent ( Constants.COMPONENT_PROJECTION_2D );
                        ComponentProjection2D particleProjection = (ComponentProjection2D) entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );

                        particleProjection.scale.setVector ( groupProjection.scale.getX (), groupProjection.scale.getY () );
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
