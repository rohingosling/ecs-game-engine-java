// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    CommandParticleSelectNext
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Selects the next particle in the simulation. If no particle is currently
//   selected, selects the first particle. Wraps around from last to first.
//
//   On selection:
//   - Removes ComponentUserControl from previously selected particle.
//   - Adds ComponentUserControl to the new particle.
//   - Changes the new particle's sprite to the "selected" sprite.
//   - Changes the new particle's trail color to the "selected" trail color.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.commands;

import java.util.ArrayList;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ICommand;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.*;

public class CommandParticleSelectNext implements ICommand
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

    public CommandParticleSelectNext ( EngineParticleSimulator engine )
    {
        this.engine = engine;
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Execute the command.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void execute ()
    {
        // Collect all particle entities and find current selection.

        ComponentParticleGroup particleToken    = new ComponentParticleGroup ();
        ComponentUserControl   userControlToken = new ComponentUserControl ();

        ArrayList <ECSEntity> particles = new ArrayList <ECSEntity> ();
        int currentIndex = -1;

        for ( ECSEntity entity : this.engine.getEntities ().values () )
        {
            if ( entity.hasComponents ( particleToken ) )
            {
                if ( entity.hasComponents ( userControlToken ) )
                {
                    currentIndex = particles.size ();
                }

                particles.add ( entity );
            }
        }

        if ( particles.isEmpty () )
        {
            return;
        }

        // Deselect current particle.

        if ( currentIndex >= 0 )
        {
            ECSEntity current = particles.get ( currentIndex );
            current.getComponents ().remove ( Constants.COMPONENT_USER_CONTROL );
        }

        // Select next particle (wraps around).

        int nextIndex = ( currentIndex + 1 ) % particles.size ();
        ECSEntity next = particles.get ( nextIndex );

        // Add user control component.

        ComponentUserControl userControl = new ComponentUserControl ();
        userControl.accelerationMagnitude = this.engine.getApplication ().getSettings ().getDouble ( Constants.KEY_PHYSICS_USER_ACCELERATION );
        next.addComponent ( Constants.COMPONENT_USER_CONTROL, userControl );

        // Change sprite to selected sprite.

        ComponentResourceSprite sprite = (ComponentResourceSprite) next.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
        sprite.imagePath = this.engine.getApplication ().getSettings ().getString ( Constants.KEY_SPRITE_SELECTED );

        // Change trail color to selected trail color.

        ComponentTranslationHistory trail = (ComponentTranslationHistory) next.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );
        trail.color = parseColor ( this.engine.getApplication ().getSettings ().getString ( Constants.KEY_TRAIL_COLOR_SELECTED ) );
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Parse an RGB color string "R,G,B" into an int[3] array.
    // ----------------------------------------------------------------------------------------------------------------

    private int[] parseColor ( String rgb )
    {
        String[] parts = rgb.split ( "," );

        int r = Integer.parseInt ( parts[0].trim () );
        int g = Integer.parseInt ( parts[1].trim () );
        int b = Integer.parseInt ( parts[2].trim () );

        return new int[] { r, g, b };
    }
}
