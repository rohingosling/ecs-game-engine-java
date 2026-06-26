// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemSimulationRenderer
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Renders the particle simulation including background image, shadows, trails,
//   sprites, wireframe circles, and HUD overlay.
//
//   Render order: background -> trails -> shadows -> sprites -> wireframe -> HUD.
//
//   Calls engine.swapBuffer() at the end of each frame.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.systems;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.TextFormat;
import rohin.gameengine.Vector2D;
import rohin.gameengine.engines.EngineParticleSimulator;
import rohin.gameengine.application.Application;
import rohin.gameengine.application.Constants;
import rohin.gameengine.application.PathResolver;
import rohin.gameengine.components.*;

public class SystemSimulationRenderer extends ECSSystem
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private EngineParticleSimulator         engine;
    private HashMap <String, BufferedImage> imageCache;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public SystemSimulationRenderer ( Integer id, String name, EngineParticleSimulator engine )
    {
        super ( id, name, engine );
        this.engine     = engine;
        this.imageCache = new HashMap <String, BufferedImage> ();
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Update.
    //
    // Multi-pass rendering: background -> trails -> shadows -> sprites -> wireframe -> HUD.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            Application application  = this.engine.getApplication ();
            Graphics2D  g            = application.getGraphicsAPI ();
            int         screenWidth  = application.getScreenWidth ();
            int         screenHeight = application.getScreenHeight ();

            // Type tokens for component filtering.

            ComponentWorld              worldToken    = new ComponentWorld ();
            ComponentParticleGroup      particleToken = new ComponentParticleGroup ();
            ComponentHud                hudToken      = new ComponentHud ();

            // Read world state.

            boolean trailsVisible    = true;
            boolean wireframeVisible = false;
            boolean paused           = false;

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentWorld world = (ComponentWorld) entity.getComponent ( Constants.COMPONENT_WORLD );
                    trailsVisible = world.trailsVisible;
                    paused        = world.paused;
                    break;
                }
            }

            // Pass 1: Background image.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( worldToken ) )
                {
                    ComponentResourceBackgroundImage bg = (ComponentResourceBackgroundImage) entity.getComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE );
                    BufferedImage bgImage = loadImage ( bg.imagePath );

                    if ( bgImage != null )
                    {
                        g.drawImage ( bgImage, 0, 0, screenWidth, screenHeight, null );
                    }
                }
            }

            // Pass 2: Trails.

            if ( trailsVisible )
            {
                for ( ECSEntity entity : this.engine.getEntities ().values () )
                {
                    if ( entity.hasComponents ( particleToken ) )
                    {
                        ComponentTranslationHistory trail      = (ComponentTranslationHistory) entity.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );
                        ComponentProjection2D       projection = (ComponentProjection2D) entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );

                        double zoom          = projection.scale.getX ();
                        double worldToScreen = screenHeight * zoom;

                        LinkedList <Vector2D> history = trail.translationHistory;

                        if ( history.size () > 1 )
                        {
                            Stroke originalStroke = g.getStroke ();
                            g.setStroke ( new BasicStroke ( trail.thickness ) );

                            int totalPoints = history.size ();

                            for ( int i = 0; i < totalPoints - 1; i++ )
                            {
                                double progress = (double) i / (double) ( totalPoints - 1 );
                                float  alpha    = (float) ( trail.opacityTail + progress * ( trail.opacityHead - trail.opacityTail ) );

                                alpha = Math.max ( 0.0f, Math.min ( 1.0f, alpha ) );

                                g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, alpha ) );
                                g.setColor ( new Color ( trail.color[0], trail.color[1], trail.color[2] ) );

                                Vector2D p1 = history.get ( i );
                                Vector2D p2 = history.get ( i + 1 );

                                g.drawLine ( (int) ( p1.getX () * worldToScreen ), (int) ( p1.getY () * worldToScreen ),
                                             (int) ( p2.getX () * worldToScreen ), (int) ( p2.getY () * worldToScreen ) );
                            }

                            g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 1.0f ) );
                            g.setStroke ( originalStroke );
                        }
                    }
                }
            }

            // Pass 3: Shadows.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    ComponentTransform      transform  = (ComponentTransform) entity.getComponent ( Constants.COMPONENT_TRANSFORM );
                    ComponentResourceShadow shadow     = (ComponentResourceShadow) entity.getComponent ( Constants.COMPONENT_RESOURCE_SHADOW );
                    ComponentGeometryCircle geometry   = (ComponentGeometryCircle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
                    ComponentProjection2D   projection = (ComponentProjection2D) entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );

                    BufferedImage shadowImage = loadImage ( shadow.imagePath );

                    if ( shadowImage != null )
                    {
                        double zoom          = projection.scale.getX ();
                        double worldToScreen = screenHeight * zoom;
                        int    diameter      = (int) ( geometry.radius * 2 * shadow.scale * worldToScreen );

                        int sx = (int) ( ( transform.translation.getX () + shadow.offset.getX () ) * worldToScreen - diameter / 2.0 );
                        int sy = (int) ( ( transform.translation.getY () + shadow.offset.getY () ) * worldToScreen - diameter / 2.0 );

                        g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, (float) shadow.opacity ) );
                        g.drawImage ( shadowImage, sx, sy, diameter, diameter, null );
                        g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 1.0f ) );
                    }
                }
            }

            // Pass 4: Sprites.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    ComponentTransform      transform  = (ComponentTransform) entity.getComponent ( Constants.COMPONENT_TRANSFORM );
                    ComponentResourceSprite sprite     = (ComponentResourceSprite) entity.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
                    ComponentGeometryCircle geometry   = (ComponentGeometryCircle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
                    ComponentProjection2D   projection = (ComponentProjection2D) entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );

                    BufferedImage spriteImage = loadImage ( sprite.imagePath );

                    if ( spriteImage != null )
                    {
                        double zoom          = projection.scale.getX ();
                        double worldToScreen = screenHeight * zoom;
                        int    diameter      = (int) ( geometry.radius * 2 * worldToScreen );

                        int sx = (int) ( transform.translation.getX () * worldToScreen - diameter / 2.0 );
                        int sy = (int) ( transform.translation.getY () * worldToScreen - diameter / 2.0 );

                        g.drawImage ( spriteImage, sx, sy, diameter, diameter, null );
                    }
                }
            }

            // Pass 5: Wireframe circles.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( particleToken ) )
                {
                    ComponentGeometryCircle geometry   = (ComponentGeometryCircle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
                    ComponentTransform      transform  = (ComponentTransform) entity.getComponent ( Constants.COMPONENT_TRANSFORM );
                    ComponentProjection2D   projection = (ComponentProjection2D) entity.getComponent ( Constants.COMPONENT_PROJECTION_2D );

                    if ( geometry.visible )
                    {
                        double zoom          = projection.scale.getX ();
                        double worldToScreen = screenHeight * zoom;
                        int    diameter      = (int) ( geometry.radius * 2 * worldToScreen );

                        int cx = (int) ( transform.translation.getX () * worldToScreen - diameter / 2.0 );
                        int cy = (int) ( transform.translation.getY () * worldToScreen - diameter / 2.0 );

                        g.setColor ( new Color ( geometry.color[0], geometry.color[1], geometry.color[2] ) );
                        g.drawOval ( cx, cy, diameter, diameter );
                    }
                }
            }

            // Pass 6: HUD overlay.
            //
            // Dynamically build HUD text from the selected particle's data.

            ComponentUserControl userControlToken = new ComponentUserControl ();
            ECSEntity selectedParticle = null;

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( userControlToken ) )
                {
                    selectedParticle = entity;
                    break;
                }
            }

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( hudToken ) )
                {
                    ComponentHud hud = (ComponentHud) entity.getComponent ( Constants.COMPONENT_HUD );

                    if ( selectedParticle != null )
                    {
                        ComponentTransform      selTransform = (ComponentTransform) selectedParticle.getComponent ( Constants.COMPONENT_TRANSFORM );
                        ComponentPhysics        selPhysics   = (ComponentPhysics) selectedParticle.getComponent ( Constants.COMPONENT_PHYSICS );
                        ComponentGeometryCircle selGeometry  = (ComponentGeometryCircle) selectedParticle.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
                        ComponentParticleGroup  selGroup     = (ComponentParticleGroup) selectedParticle.getComponent ( Constants.COMPONENT_PARTICLE_GROUP );

                        double speed = selPhysics.velocity.magnitude ();

                        StringBuilder sb = new StringBuilder ();
                        sb.append ( String.format ( "Particle: %s",     selectedParticle.getName () ) ); sb.append ( "\n" );
                        sb.append ( String.format ( "Group:    %d",     selGroup.groupId ) );            sb.append ( "\n" );
                        sb.append ( String.format ( "Mass:     %.1f",   selPhysics.mass ) );             sb.append ( "\n" );
                        sb.append ( String.format ( "Radius:   %.4f",   selGeometry.radius ) );          sb.append ( "\n" );
                        sb.append ( String.format ( "Position: (%.4f, %.4f)", selTransform.translation.getX (), selTransform.translation.getY () ) ); sb.append ( "\n" );
                        sb.append ( String.format ( "Velocity: (%.4f, %.4f)", selPhysics.velocity.getX (), selPhysics.velocity.getY () ) ); sb.append ( "\n" );
                        sb.append ( String.format ( "Speed:    %.4f",  speed ) );

                        hud.text    = sb.toString ();
                        hud.visible = true;
                    }
                    else
                    {
                        hud.visible = false;
                    }

                    if ( hud.visible && hud.text != null && !hud.text.isEmpty () )
                    {
                        Font font = new Font ( hud.font, Font.PLAIN, hud.fontSize );
                        g.setFont ( font );
                        g.setColor ( new Color ( hud.color[0], hud.color[1], hud.color[2] ) );

                        FontMetrics fm = g.getFontMetrics ();
                        int lineHeight = fm.getHeight ();

                        int x = (int) hud.position.getX ();
                        int y = (int) hud.position.getY () + fm.getAscent ();

                        String[] lines = hud.text.split ( "\n" );

                        for ( String line : lines )
                        {
                            g.drawString ( line, x, y );
                            y += lineHeight;
                        }
                    }
                }
            }

            // Paused indicator.

            if ( paused )
            {
                Font font = new Font ( "Anita  Semi-square", Font.BOLD, 24 );
                g.setFont ( font );
                g.setColor ( new Color ( 255, 255, 255 ) );
                g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 0.5f ) );

                FontMetrics fm   = g.getFontMetrics ();
                String      text = "PAUSED";
                int         tw   = fm.stringWidth ( text );

                g.drawString ( text, ( screenWidth - tw ) / 2, screenHeight - 50 );
                g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 1.0f ) );
            }

            // Swap buffer.

            this.engine.swapBuffer ();
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, true );
        }
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Load an image from file, caching for reuse.
    // ----------------------------------------------------------------------------------------------------------------

    private BufferedImage loadImage ( String path )
    {
        if ( path == null || path.isEmpty () )
        {
            return null;
        }

        if ( this.imageCache.containsKey ( path ) )
        {
            return this.imageCache.get ( path );
        }

        try
        {
            BufferedImage image = ImageIO.read ( PathResolver.resolveFile ( path ) );
            this.imageCache.put ( path, image );
            return image;
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, false );
            return null;
        }
    }
}
