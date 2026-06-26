// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    SystemMenuRenderer
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Renders the menu screens including background image, button shadows,
//   button images, button text, and text boxes.
//
//   Render order: background -> button shadows -> button images -> button text -> text boxes.
//
//   Calls engine.swapBuffer() at the end of each frame.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.systems;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

import rohin.gameengine.ECSEntity;
import rohin.gameengine.ECSSystem;
import rohin.gameengine.TextFormat;
import rohin.gameengine.engines.EngineMenu;
import rohin.gameengine.application.Application;
import rohin.gameengine.application.Constants;
import rohin.gameengine.application.PathResolver;
import rohin.gameengine.components.*;

public class SystemMenuRenderer extends ECSSystem
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private EngineMenu                    engine;
    private HashMap <String, BufferedImage> imageCache;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public SystemMenuRenderer ( Integer id, String name, EngineMenu engine )
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
    // Multi-pass rendering: background -> shadows -> buttons -> text -> text boxes.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void update ( long t )
    {
        try
        {
            Application application = this.engine.getApplication ();
            Graphics2D  g           = application.getGraphicsAPI ();
            int         screenWidth  = application.getScreenWidth ();
            int         screenHeight = application.getScreenHeight ();

            // Type tokens for component filtering.

            ComponentResourceBackgroundImage bgToken     = new ComponentResourceBackgroundImage ();
            ComponentButtonState             stateToken  = new ComponentButtonState ();
            ComponentTextBox                 textBoxToken = new ComponentTextBox ();

            // Pass 1: Background image.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( bgToken ) )
                {
                    ComponentResourceBackgroundImage bg = (ComponentResourceBackgroundImage) entity.getComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE );
                    BufferedImage bgImage = loadImage ( bg.imagePath );

                    if ( bgImage != null )
                    {
                        g.drawImage ( bgImage, 0, 0, screenWidth, screenHeight, null );
                    }
                }
            }

            // Pass 2: Button shadows.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( stateToken ) )
                {
                    ComponentButtonImage   buttonImage = (ComponentButtonImage) entity.getComponent ( Constants.COMPONENT_BUTTON_IMAGE );
                    ComponentGeometryRectangle geometry = (ComponentGeometryRectangle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE );

                    int    shadowOffsetX = application.getSettings ().getInteger ( Constants.KEY_MENU_BUTTON_SHADOW_OFFSET_X );
                    int    shadowOffsetY = application.getSettings ().getInteger ( Constants.KEY_MENU_BUTTON_SHADOW_OFFSET_Y );
                    double shadowOpacity = application.getSettings ().getDouble  ( Constants.KEY_MENU_BUTTON_SHADOW_OPACITY );

                    BufferedImage shadowImage = loadImage ( buttonImage.imageShadow );

                    if ( shadowImage != null )
                    {
                        int x = (int) geometry.origin.getX () + shadowOffsetX;
                        int y = (int) geometry.origin.getY () + shadowOffsetY;

                        g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, (float) shadowOpacity ) );
                        g.drawImage ( shadowImage, x, y, null );
                        g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 1.0f ) );
                    }
                }
            }

            // Pass 3: Button images (state-dependent).

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( stateToken ) )
                {
                    ComponentButtonState   state       = (ComponentButtonState) entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );
                    ComponentButtonImage   buttonImage = (ComponentButtonImage) entity.getComponent ( Constants.COMPONENT_BUTTON_IMAGE );
                    ComponentGeometryRectangle geometry = (ComponentGeometryRectangle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE );

                    String imagePath;

                    if ( !state.enabled )
                    {
                        imagePath = buttonImage.imageDisabled;
                    }
                    else if ( state.pressed && state.selected )
                    {
                        imagePath = buttonImage.imageDownSelected;
                    }
                    else if ( state.selected )
                    {
                        imagePath = buttonImage.imageUpSelected;
                    }
                    else
                    {
                        imagePath = buttonImage.imageUp;
                    }

                    BufferedImage img = loadImage ( imagePath );

                    if ( img != null )
                    {
                        int x = (int) geometry.origin.getX ();
                        int y = (int) geometry.origin.getY ();

                        g.drawImage ( img, x, y, null );
                    }
                }
            }

            // Pass 4: Button text.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( stateToken ) )
                {
                    ComponentButtonState        state       = (ComponentButtonState) entity.getComponent ( Constants.COMPONENT_BUTTON_STATE );
                    ComponentButtonText         buttonText  = (ComponentButtonText) entity.getComponent ( Constants.COMPONENT_BUTTON_TEXT );
                    ComponentButtonImage        buttonImage = (ComponentButtonImage) entity.getComponent ( Constants.COMPONENT_BUTTON_IMAGE );
                    ComponentGeometryRectangle  geometry    = (ComponentGeometryRectangle) entity.getComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE );

                    // Determine text color.

                    int[] textColor;

                    if ( !state.enabled )
                    {
                        textColor = buttonText.colorDisabled;
                    }
                    else if ( state.selected )
                    {
                        textColor = parseSelectedColor ();
                    }
                    else
                    {
                        textColor = buttonText.color;
                    }

                    // Draw text centered on button.

                    Font font = new Font ( buttonText.font, Font.PLAIN, buttonText.size );
                    g.setFont ( font );
                    g.setColor ( new Color ( textColor[0], textColor[1], textColor[2] ) );

                    FontMetrics fm = g.getFontMetrics ();

                    BufferedImage btnImg = loadImage ( buttonImage.imageUp );
                    int buttonWidth  = ( btnImg != null ) ? btnImg.getWidth () : 600;
                    int buttonHeight = ( btnImg != null ) ? btnImg.getHeight () : 60;

                    int bx = (int) geometry.origin.getX ();
                    int by = (int) geometry.origin.getY ();

                    int textWidth = fm.stringWidth ( buttonText.text );
                    int textX     = bx + ( buttonWidth - textWidth ) / 2;
                    int textY     = by + ( buttonHeight + fm.getAscent () - fm.getDescent () ) / 2;

                    g.drawString ( buttonText.text, textX, textY );
                }
            }

            // Pass 5: Text boxes.

            for ( ECSEntity entity : this.engine.getEntities ().values () )
            {
                if ( entity.hasComponents ( textBoxToken ) )
                {
                    ComponentTextBox tb = (ComponentTextBox) entity.getComponent ( Constants.COMPONENT_TEXT_BOX );

                    Font font = new Font ( tb.font, Font.PLAIN, tb.fontSize );
                    g.setFont ( font );
                    g.setColor ( new Color ( tb.color[0], tb.color[1], tb.color[2] ) );

                    g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, (float) tb.textOpacity ) );

                    FontMetrics fm = g.getFontMetrics ();
                    int lineHeight = fm.getHeight ();

                    int x = (int) tb.position.getX ();
                    int y = (int) tb.position.getY () + fm.getAscent ();

                    String[] lines = tb.text.split ( "\n" );

                    for ( String line : lines )
                    {
                        int lineWidth = fm.stringWidth ( line );
                        int drawX;

                        switch ( tb.align )
                        {
                            case 1:  drawX = x + ( tb.width - lineWidth ) / 2; break;
                            case 2:  drawX = x + tb.width - lineWidth;         break;
                            default: drawX = x;                                break;
                        }

                        g.drawString ( line, drawX, y );
                        y += lineHeight;
                    }

                    g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 1.0f ) );
                }
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


    // ----------------------------------------------------------------------------------------------------------------
    // Parse selected button font color from settings.
    // ----------------------------------------------------------------------------------------------------------------

    private int[] parseSelectedColor ()
    {
        String colorStr = this.engine.getApplication ().getSettings ().getString ( Constants.KEY_MENU_BUTTON_FONT_COLOR_SELECTED );
        String[] parts  = colorStr.split ( "," );

        int r = Integer.parseInt ( parts[0].trim () );
        int g = Integer.parseInt ( parts[1].trim () );
        int b = Integer.parseInt ( parts[2].trim () );

        return new int[] { r, g, b };
    }
}
