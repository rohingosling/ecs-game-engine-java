// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EngineMenu
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   ECS engine for the menu system. Creates all menu entities (background,
//   buttons, text boxes), loads images from settings, and registers the menu
//   systems. The swap buffer approach follows the v1 pattern — Application
//   owns the GraphicsWindow and BufferStrategy, and swapBuffer() calls
//   screenBuffer.show().
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.engines;

import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;

import rohin.gameengine.ApplicationSettings;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.ECSEntity;
import rohin.gameengine.GlobalCache;
import rohin.gameengine.TextFormat;
import rohin.gameengine.application.Application;
import rohin.gameengine.application.Constants;
import rohin.gameengine.application.KeyboardEventHandlerMenu;
import rohin.gameengine.application.PathResolver;
import rohin.gameengine.components.*;
import rohin.gameengine.entities.EntityMenuBackground;
import rohin.gameengine.entities.EntityMenuButton;
import rohin.gameengine.entities.EntityMenuButtonCounter;
import rohin.gameengine.entities.EntityMenuTextBox;
import rohin.gameengine.systems.SystemMenuManager;
import rohin.gameengine.systems.SystemMenuRenderer;

public class EngineMenu extends ECSEngine
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Application application;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Accessors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Application getApplication () { return this.application; }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Abstract method implementations
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Swap buffer.
    //
    // Follows the v1 pattern: Application owns the BufferStrategy. The engine
    // calls screenBuffer.show() to flip the double buffer.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void swapBuffer ()
    {
        BufferStrategy screenBuffer = this.application.getScreenBuffer ();
        screenBuffer.show ();
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EngineMenu ( Application application )
    {
        super ();
        this.application = application;
        getCommandManager ().setLoggingEnabled ( application.getSettings ().getBoolean ( Constants.KEY_APPLICATION_LOGGING_ENABLED ) );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        ApplicationSettings settings   = this.application.getSettings ();
        GlobalCache         globalCache = this.application.getGlobalCache ();

        // Load button image paths from settings.

        // @formatter:off

        String imageUp           = settings.getString ( Constants.KEY_MENU_BUTTON_IMAGE_UP );
        String imageUpSelected   = settings.getString ( Constants.KEY_MENU_BUTTON_IMAGE_UP_SELECTED );
        String imageDown         = settings.getString ( Constants.KEY_MENU_BUTTON_IMAGE_DOWN );
        String imageDownSelected = settings.getString ( Constants.KEY_MENU_BUTTON_IMAGE_DOWN_SELECTED );
        String imageDisabled     = settings.getString ( Constants.KEY_MENU_BUTTON_IMAGE_DISABLED );
        String imageShadow       = settings.getString ( Constants.KEY_MENU_BUTTON_IMAGE_SHADOW );

        // @formatter:on

        // Load font settings.

        String fontName           = settings.getString  ( Constants.KEY_MENU_BUTTON_FONT_NAME );
        int    fontSize           = settings.getInteger ( Constants.KEY_MENU_BUTTON_FONT_SIZE );
        int[]  fontColorSelected  = parseColor ( settings.getString ( Constants.KEY_MENU_BUTTON_FONT_COLOR_SELECTED ) );
        int[]  fontColorUnselected = parseColor ( settings.getString ( Constants.KEY_MENU_BUTTON_FONT_COLOR_UNSELECTED ) );
        int[]  fontColorDisabled  = parseColor ( settings.getString ( Constants.KEY_MENU_BUTTON_FONT_COLOR_DISABLED ) );

        // Load layout settings.

        int layoutX        = settings.getInteger ( Constants.KEY_MENU_BUTTON_LAYOUT_X );
        int layoutYStart   = settings.getInteger ( Constants.KEY_MENU_BUTTON_LAYOUT_Y_START );
        int layoutYSpacing = settings.getInteger ( Constants.KEY_MENU_BUTTON_LAYOUT_Y_SPACING );

        // Load default particle counts from GlobalCache.

        int countRed    = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_RED );
        int countGreen  = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_GREEN );
        int countBlue   = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_BLUE );
        int countYellow = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_YELLOW );
        int countMin    = settings.getInteger ( Constants.KEY_PARTICLE_COUNT_MIN );
        int countMax    = settings.getInteger ( Constants.KEY_PARTICLE_COUNT_MAX );

        // Create background entity.

        EntityMenuBackground background = new EntityMenuBackground ( Constants.ENTITY_BACKGROUND_MENU, "ENTITY_BACKGROUND_MENU", this );
        ComponentResourceBackgroundImage bgImage = (ComponentResourceBackgroundImage) background.getComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE );
        bgImage.imagePath = settings.getString ( Constants.KEY_MENU_BACKGROUND_MAIN );
        addEntity ( Constants.ENTITY_BACKGROUND_MENU, background );

        // Create main menu buttons.

        createButton ( Constants.ENTITY_MENU_BUTTON_START_SIMULATION, "ENTITY_MENU_BUTTON_START_SIMULATION", "Start Simulation",
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 0 * layoutYSpacing );

        createButton ( Constants.ENTITY_MENU_BUTTON_SETTINGS, "ENTITY_MENU_BUTTON_SETTINGS", "Settings",
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 1 * layoutYSpacing );

        createButton ( Constants.ENTITY_MENU_BUTTON_INSTRUCTIONS, "ENTITY_MENU_BUTTON_INSTRUCTIONS", "Instructions",
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 2 * layoutYSpacing );

        createButton ( Constants.ENTITY_MENU_BUTTON_ABOUT, "ENTITY_MENU_BUTTON_ABOUT", "About",
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 3 * layoutYSpacing );

        createButton ( Constants.ENTITY_MENU_BUTTON_EXIT, "ENTITY_MENU_BUTTON_EXIT", "Exit",
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 4 * layoutYSpacing );

        // Create settings counter buttons.

        createCounterButton ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED, "ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED",
                "Red Particle Count", countRed, countMin, countMax,
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 0 * layoutYSpacing );

        createCounterButton ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN, "ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN",
                "Green Particle Count", countGreen, countMin, countMax,
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 1 * layoutYSpacing );

        createCounterButton ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE, "ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE",
                "Blue Particle Count", countBlue, countMin, countMax,
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 2 * layoutYSpacing );

        createCounterButton ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW, "ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW",
                "Yellow Particle Count", countYellow, countMin, countMax,
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 3 * layoutYSpacing );

        // Create back button.

        createButton ( Constants.ENTITY_MENU_BUTTON_BACK, "ENTITY_MENU_BUTTON_BACK", "Back",
                imageUp, imageUpSelected, imageDown, imageDownSelected, imageDisabled, imageShadow,
                fontName, fontSize, fontColorSelected, fontColorUnselected, fontColorDisabled,
                layoutX, layoutYStart + 4 * layoutYSpacing );

        // Create text box entities.

        createTextBox ( Constants.ENTITY_MENU_TEXT_BOX_INSTRUCTIONS, "ENTITY_MENU_TEXT_BOX_INSTRUCTIONS",
                loadTextFile ( settings.getString ( Constants.KEY_MENU_TEXT_INSTRUCTIONS ) ),
                Constants.KEY_MENU_TEXT_INSTRUCTIONS_ALIGNMENT, settings );

        createTextBox ( Constants.ENTITY_MENU_TEXT_BOX_ABOUT, "ENTITY_MENU_TEXT_BOX_ABOUT",
                loadTextFile ( settings.getString ( Constants.KEY_MENU_TEXT_ABOUT ) ),
                Constants.KEY_MENU_TEXT_ABOUT_ALIGNMENT, settings );

        // Initially disable non-main-menu entities (counter buttons, back button, text boxes).

        getEntities ().get ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED ).setEnabled ( false );
        getEntities ().get ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN ).setEnabled ( false );
        getEntities ().get ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE ).setEnabled ( false );
        getEntities ().get ( Constants.ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW ).setEnabled ( false );
        getEntities ().get ( Constants.ENTITY_MENU_BUTTON_BACK ).setEnabled ( false );
        getEntities ().get ( Constants.ENTITY_MENU_TEXT_BOX_INSTRUCTIONS ).setEnabled ( false );
        getEntities ().get ( Constants.ENTITY_MENU_TEXT_BOX_ABOUT ).setEnabled ( false );

        // Register systems.

        addSystem ( Constants.SYSTEM_MENU_MANAGER,  new SystemMenuManager ( Constants.SYSTEM_MENU_MANAGER, "SYSTEM_MENU_MANAGER", this ) );
        addSystem ( Constants.SYSTEM_MENU_RENDERER, new SystemMenuRenderer ( Constants.SYSTEM_MENU_RENDERER, "SYSTEM_MENU_RENDERER", this ) );

        // Initialize keyboard handler.

        for ( java.awt.event.KeyListener kl : this.application.getWindow ().getScreen ().getKeyListeners () )
        {
            this.application.getWindow ().getScreen ().removeKeyListener ( kl );
        }

        KeyboardEventHandlerMenu keyboardHandler = new KeyboardEventHandlerMenu ( this );

        this.application.getWindow ().getScreen ().addKeyListener ( keyboardHandler );
        this.application.getWindow ().getScreen ().setFocusTraversalKeysEnabled ( false );
        this.application.getWindow ().getScreen ().requestFocus ();
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Create a standard menu button entity.
    // ----------------------------------------------------------------------------------------------------------------

    private void createButton ( Integer entityId, String name, String text,
            String imageUp, String imageUpSelected, String imageDown, String imageDownSelected,
            String imageDisabled, String imageShadow,
            String fontName, int fontSize, int[] fontColorSelected, int[] fontColorUnselected, int[] fontColorDisabled,
            int x, int y )
    {
        EntityMenuButton button = new EntityMenuButton ( entityId, name, this );

        ComponentButtonImage buttonImage = (ComponentButtonImage) button.getComponent ( Constants.COMPONENT_BUTTON_IMAGE );
        buttonImage.imageUp           = imageUp;
        buttonImage.imageUpSelected   = imageUpSelected;
        buttonImage.imageDown         = imageDown;
        buttonImage.imageDownSelected = imageDownSelected;
        buttonImage.imageDisabled     = imageDisabled;
        buttonImage.imageShadow       = imageShadow;

        ComponentButtonText buttonText = (ComponentButtonText) button.getComponent ( Constants.COMPONENT_BUTTON_TEXT );
        buttonText.text          = text;
        buttonText.font          = fontName;
        buttonText.size          = fontSize;
        buttonText.color         = fontColorUnselected;
        buttonText.colorDisabled = fontColorDisabled;

        ComponentGeometryRectangle geometry = (ComponentGeometryRectangle) button.getComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE );
        geometry.origin.setVector ( x, y );

        addEntity ( entityId, button );
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Create a counter button entity.
    // ----------------------------------------------------------------------------------------------------------------

    private void createCounterButton ( Integer entityId, String name, String label,
            int count, int countMin, int countMax,
            String imageUp, String imageUpSelected, String imageDown, String imageDownSelected,
            String imageDisabled, String imageShadow,
            String fontName, int fontSize, int[] fontColorSelected, int[] fontColorUnselected, int[] fontColorDisabled,
            int x, int y )
    {
        EntityMenuButtonCounter button = new EntityMenuButtonCounter ( entityId, name, this );

        ComponentButtonImage buttonImage = (ComponentButtonImage) button.getComponent ( Constants.COMPONENT_BUTTON_IMAGE );
        buttonImage.imageUp           = imageUp;
        buttonImage.imageUpSelected   = imageUpSelected;
        buttonImage.imageDown         = imageDown;
        buttonImage.imageDownSelected = imageDownSelected;
        buttonImage.imageDisabled     = imageDisabled;
        buttonImage.imageShadow       = imageShadow;

        ComponentParticleCount particleCount = (ComponentParticleCount) button.getComponent ( Constants.COMPONENT_PARTICLE_COUNT );
        particleCount.particleCount    = count;
        particleCount.particleCountMin = countMin;
        particleCount.particleCountMax = countMax;
        particleCount.label            = label;

        ComponentButtonText buttonText = (ComponentButtonText) button.getComponent ( Constants.COMPONENT_BUTTON_TEXT );
        buttonText.text          = label + ":  < " + count + " >";
        buttonText.font          = fontName;
        buttonText.size          = fontSize;
        buttonText.color         = fontColorUnselected;
        buttonText.colorDisabled = fontColorDisabled;

        ComponentGeometryRectangle geometry = (ComponentGeometryRectangle) button.getComponent ( Constants.COMPONENT_GEOMETRY_RECTANGLE );
        geometry.origin.setVector ( x, y );

        addEntity ( entityId, button );
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Create a text box entity.
    // ----------------------------------------------------------------------------------------------------------------

    private void createTextBox ( Integer entityId, String name, String text, String alignmentKey, ApplicationSettings settings )
    {
        EntityMenuTextBox textBox = new EntityMenuTextBox ( entityId, name, this );

        ComponentTextBox tb = (ComponentTextBox) textBox.getComponent ( Constants.COMPONENT_TEXT_BOX );
        tb.text        = text;
        tb.font        = settings.getString  ( Constants.KEY_MENU_TEXTBOX_FONT_NAME );
        tb.fontSize    = settings.getInteger ( Constants.KEY_MENU_TEXTBOX_FONT_SIZE );
        tb.color       = parseColor ( settings.getString ( Constants.KEY_MENU_TEXTBOX_FONT_COLOR ) );
        tb.textOpacity = settings.getDouble  ( Constants.KEY_MENU_TEXTBOX_TEXT_OPACITY );
        tb.position.setVector ( settings.getInteger ( Constants.KEY_MENU_TEXTBOX_X ), settings.getInteger ( Constants.KEY_MENU_TEXTBOX_Y ) );
        tb.width       = settings.getInteger ( Constants.KEY_MENU_TEXTBOX_WIDTH );
        tb.height      = settings.getInteger ( Constants.KEY_MENU_TEXTBOX_HEIGHT );
        tb.align       = parseAlignment ( settings.getString ( alignmentKey ) );

        addEntity ( entityId, textBox );
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Load text from a file.
    // ----------------------------------------------------------------------------------------------------------------

    private String loadTextFile ( String filePath )
    {
        StringBuilder sb = new StringBuilder ();

        try ( BufferedReader reader = new BufferedReader ( new FileReader ( PathResolver.resolve ( filePath ) ) ) )
        {
            String line;

            while ( ( line = reader.readLine () ) != null )
            {
                sb.append ( line );
                sb.append ( "\n" );
            }
        }
        catch ( Exception e )
        {
            TextFormat.printFormattedException ( e, false );
        }

        return sb.toString ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Parse an alignment string ("left", "center", "right") into an int code (0, 1, 2).
    // ----------------------------------------------------------------------------------------------------------------

    private int parseAlignment ( String alignment )
    {
        switch ( alignment.toLowerCase ().trim () )
        {
            case "left":   return 0;
            case "center": return 1;
            case "right":  return 2;
            default:       return 0;
        }
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
