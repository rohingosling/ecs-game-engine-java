//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Provides the GraphicsWindow class, an extended JFrame application window with a double-buffered AWT Canvas
//   serving as the primary drawing surface. This file encapsulates all windowing, buffer management, and platform-
//   specific title bar configuration required by the ECS Game Engine rendering pipeline.
//
// TODO:
//
//   1. Add support for configurable buffer strategies beyond double buffering.
//   2. Implement fullscreen toggle support.
//   3. Add DPI-awareness handling for high-DPI displays.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import javax.swing.JFrame;

//*********************************************************************************************************************
// Class: GraphicsWindow
//
// Description:
//
//   The GraphicsWindow class implements an extended JFrame application window with a double-buffered AWT Canvas
//   control serving as the primary drawing surface.
//
//   - It manages the creation and configuration of the graphics canvas, content pane layout, buffer strategy
//     initialization, and window lifecycle events.
//
//   - On Windows 10 version 1809 and later, the class also applies a dark title bar using the Desktop
//     Window Manager (DWM) API via the Java Foreign Function and Memory API.
//
//*********************************************************************************************************************

@ SuppressWarnings ( "serial" )
public class GraphicsWindow extends JFrame
{
    // @formatter:off

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private Canvas screen;              // AWT canvas control. Primary graphics drawing surface.
    private int    screenBufferCount;   // Number of graphics buffers. 2 for standard double buffering.

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public Canvas         getScreen       () { return this.screen;                      }
    public BufferStrategy getScreenBuffer () { return this.screen.getBufferStrategy (); }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: GraphicsWindow
    //
    // Description:
    //
    //   Initializes a new GraphicsWindow with the specified screen dimensions.
    //
    //   - Configures the JFrame content pane.
    //   - Creates and attaches an AWT Canvas as the primary drawing surface.
    //   - Sets up a double-buffer strategy for smooth rendering.
    //   - Applies a dark title bar on supported Windows platforms.
    //   - Registers a window-close listener for graceful application shutdown.
    //
    // Arguments:
    //
    //   width (int):
    //     The width of the graphics drawing surface, in pixels.
    //
    //   height (int):
    //     The height of the graphics drawing surface, in pixels.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public GraphicsWindow ( int width, int height )
    {
        // Initialize low level graphics window parameters.
        //
        // 1. We will be using a standard double buffer strategy by default.

        this.screenBufferCount = 2;

        // Get the content pane from the JFrame, configure it, and add the screen Canvas to it.
        //
        // 1. Set the dimensions.
        // 2. No layout, because we will do our own layout management.
        // 3. Set the content pane color.

        getContentPane ().setPreferredSize ( new Dimension ( width, height ) );
        getContentPane ().setLayout        ( null );
        getContentPane ().setBackground    ( Color.BLACK );

        // Initialize a new AWT Canvas to be used as a graphics screen.
        //
        // 1. Set default screen dimensions.
        // 2. Disable repainting. We will manually perform the paint operation during double buffering.
        // 3. Add the graphics canvas to the JFram content pane.

        this.screen = new Canvas ();

        this.screen.setBounds        ( 0, 0, width, height );
        this.screen.setIgnoreRepaint ( true );
        getContentPane ().add        ( this.screen );

        // Initialize our JFrame based graphics window.
        //
        // 1. Disable resizing of the window.
        // 2. Force the application window to be resized to fit the game engine drawing area.
        // 3. Set default title.
        // 4. Show the application window.
        // 5. Position the window in the center of the parent control, the desktop screen in this case.

        setResizable          ( false );
        pack                  ();
        setTitle              ( "Graphics Application" );

        // Apply dark title bar on Windows 10+.
        // Must be set after pack () creates the native window, but before
        // setVisible () so the first paint uses the dark frame.

        enableDarkTitleBar ();

        setVisible            ( true );
        setLocationRelativeTo ( null );

        // Initialize the display buffer strategy, to double buffering.

        this.screen.createBufferStrategy ( this.screenBufferCount );

        // Add  window listener.
        //
        // - Add a window listener to handle window closes, so that we can
        //   shut down the main application thread gracefully.
        //
        // - In the case where a user happens to close the application using
        //   the window close button, rather than our application and game
        //   loop control events.

        addWindowListener
        (
            new WindowAdapter ()
            {
                public void windowClosing ( WindowEvent e )
                {
                    System.exit ( 0 );
                }
            }
        );
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: enableDarkTitleBar
    //
    // Description:
    //
    //   Enables the dark title bar on Windows 10 version 1809 and later by invoking the DwmSetWindowAttribute
    //   function from the Desktop Window Manager (DWM) API through the Java Foreign Function and Memory API.
    //
    //   - Locates the native window handle via FindWindowW.
    //
    //   - Sets the DWMWA_USE_IMMERSIVE_DARK_MODE attribute.
    //
    //   - If the platform is not Windows or the DWM call fails, the method fails silently with a diagnostic message
    //     printed to standard output.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void enableDarkTitleBar ()
    {
        try ( Arena arena = Arena.ofConfined () )
        {
            // {{Add comments}}

            Linker       linker = Linker.nativeLinker ();
            SymbolLookup user32 = SymbolLookup.libraryLookup ( "user32", Arena.global () );
            SymbolLookup dwmapi = SymbolLookup.libraryLookup ( "dwmapi", Arena.global () );

            // {{Add comments}}

            MethodHandle findWindowW = linker.downcallHandle
            (
                user32.find ( "FindWindowW" ).orElseThrow (),
                FunctionDescriptor.of ( ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS )
            );

            // {{Add comments}}

            MemorySegment title = arena.allocateFrom ( getTitle () + "\0", java.nio.charset.StandardCharsets.UTF_16LE );
            MemorySegment hwnd  = (MemorySegment) findWindowW.invoke ( MemorySegment.NULL, title );

            // {{Add comments}}

            System.out.println ( "[DarkTitleBar] Looking for title: " + getTitle () );
            System.out.println ( "[DarkTitleBar] HWND = " + hwnd );

            // {{Add comments}}

            if ( hwnd.address () == 0 )
            {
                System.out.println ( "[DarkTitleBar] Window not found." );
                return;
            }

            // {{Add comments}}

            MethodHandle dwmSetWindowAttribute = linker.downcallHandle
            (
                dwmapi.find ( "DwmSetWindowAttribute" ).orElseThrow (),
                FunctionDescriptor.of
                (
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT
                )
            );

            // {{Add comments}}

            int           DWMWA_USE_IMMERSIVE_DARK_MODE = 20;
            MemorySegment value                         = arena.allocate ( ValueLayout.JAVA_INT );
            value.set ( ValueLayout.JAVA_INT, 0, 1 );

            // {{Add comments}}

            int hresult = (int) dwmSetWindowAttribute.invoke ( hwnd, DWMWA_USE_IMMERSIVE_DARK_MODE, value, 4 );

            // {{Add comments}}

            System.out.println ( "[DarkTitleBar] HRESULT = " + hresult );
        }
        catch ( Throwable e )
        {
            System.out.println ( "[DarkTitleBar] FAILED: " + e );
            e.printStackTrace ();
        }
    }

    // @formatter:on
}
