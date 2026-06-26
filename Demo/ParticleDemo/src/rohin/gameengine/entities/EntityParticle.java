// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EntityParticle
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   A particle that participates in the particle simulation. Particle entities
//   have all the same components as particle group entities, plus a group
//   membership component. ComponentUserControl is added dynamically when this
//   particle is selected.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.entities;

import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.components.ComponentGeometryCircle;
import rohin.gameengine.components.ComponentProjection2D;
import rohin.gameengine.components.ComponentResourceSprite;
import rohin.gameengine.components.ComponentResourceShadow;
import rohin.gameengine.components.ComponentTransform;
import rohin.gameengine.components.ComponentTranslationHistory;
import rohin.gameengine.components.ComponentPhysics;
import rohin.gameengine.components.ComponentParticleGroup;
import rohin.gameengine.application.Constants;

public class EntityParticle extends ECSEntity
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EntityParticle ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        addComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE,       new ComponentGeometryCircle () );
        addComponent ( Constants.COMPONENT_PROJECTION_2D,         new ComponentProjection2D () );
        addComponent ( Constants.COMPONENT_RESOURCE_SPRITE,       new ComponentResourceSprite () );
        addComponent ( Constants.COMPONENT_RESOURCE_SHADOW,       new ComponentResourceShadow () );
        addComponent ( Constants.COMPONENT_TRANSFORM,             new ComponentTransform () );
        addComponent ( Constants.COMPONENT_TRANSLATION_HISTORY,   new ComponentTranslationHistory () );
        addComponent ( Constants.COMPONENT_PHYSICS,               new ComponentPhysics () );
        addComponent ( Constants.COMPONENT_PARTICLE_GROUP,        new ComponentParticleGroup () );
    }
}
