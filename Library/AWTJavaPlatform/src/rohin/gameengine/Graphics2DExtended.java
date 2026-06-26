//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Provides an extended wrapper around Java's AWT Graphics2D class, offering convenience methods for common 2D
//   rendering operations such as clearing the screen with various color models, drawing cross hairs, and rendering
//   guide grids for analysis and debugging.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;

//*********************************************************************************************************************
// Class: Graphics2DExtended
//
// Description:
//
//   Extends the functionality of Java's AWT Graphics2D by providing high-level convenience methods for common 2D
//   rendering tasks.
//
//   This class wraps a Graphics2D instance and offers simplified APIs for screen clearing with multiple color
//   models (AWT Color, HSL, RGB integer, and RGB floating point), drawing cross-hairs for geometric center
//   visualization, and rendering configurable guide grids for analysis and debugging.
//
//*********************************************************************************************************************

public class Graphics2DExtended
{
    //=================================================================================================================
    // Constants
    //=================================================================================================================

    protected final static int M_DEFAULT_VIEW_WIDTH  = 800;
    protected final static int M_DEFAULT_VIEW_HEIGHT = 600;
    protected final static int M_INDEX_HUE           = 0;
    protected final static int M_INDEX_SATURATION    = 1;
    protected final static int M_INDEX_LIGHTNESS     = 2;

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private int        viewWidth;
    private int        viewHeight;
    private Graphics2D g;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public int  getViewWidth  () { return viewWidth;  }
    public int  getViewHeight () { return viewHeight; }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setViewWidth  ( int viewWidth )  { this.viewWidth  = viewWidth;  }
    public void setViewHeight ( int viewHeight ) { this.viewHeight = viewHeight; }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public Graphics2DExtended ()               { initialize ( M_DEFAULT_VIEW_WIDTH, M_DEFAULT_VIEW_HEIGHT, null ); }
    public Graphics2DExtended ( Graphics2D g ) { initialize ( M_DEFAULT_VIEW_WIDTH, M_DEFAULT_VIEW_HEIGHT, g    ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Class initializer. Used to conveniently initialize multiple constructors with a shared setup routine.
    //
    // Arguments:
    //
    //   width (int):
    //     The initial view width in pixels.
    //
    //   height (int):
    //     The initial view height in pixels.
    //
    //   g (Graphics2D):
    //     The AWT Graphics2D rendering context to wrap. May be null.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ( int width, int height, Graphics2D g )
    {
        this.viewWidth  = width;
        this.viewHeight = height;
        this.g          = g;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearScreen
    //
    // Description:
    //
    //   Clears the screen to a specified color. Most often used to clear the screen and prepare the drawing surface
    //   for a new animation frame.
    //
    // Arguments:
    //
    //   color (Color):
    //     An AWT Color object specifying the color to clear the screen to.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clearScreen ( Color color )
    {
        g.setColor ( color );
        g.fillRect ( 0, 0, this.viewWidth, this.viewHeight );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearScreenHSL
    //
    // Description:
    //
    //   Clears the screen to a specified color, defined by HSL color model parameters. Most often used to clear the
    //   screen and prepare the drawing surface for a new animation frame.
    //
    // Arguments:
    //
    //   h (double):
    //     Hue, in the range 0.0 ≤ h ≤ 1.0.
    //
    //   s (double):
    //     Saturation, in the range 0.0 ≤ s ≤ 1.0.
    //
    //   l (double):
    //     Lightness, in the range 0.0 ≤ l ≤ 1.0.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clearScreenHSL ( double h, double s, double l )
    {
        clearScreen ( Color.getHSBColor ( ( float ) h, ( float ) s, ( float ) l ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearScreenRGB
    //
    // Description:
    //
    //   Clears the screen to a specified color, defined by 8-bit integer RGB color parameters. Most often used to
    //   clear the screen and prepare the drawing surface for a new animation frame.
    //
    //   Internally converts the RGB values to HSL and delegates to clearScreenHSL.
    //
    // Arguments:
    //
    //   r (int):
    //     Red channel, in the range 0 ≤ r ≤ 255.
    //
    //   g (int):
    //     Green channel, in the range 0 ≤ g ≤ 255.
    //
    //   b (int):
    //     Blue channel, in the range 0 ≤ b ≤ 255.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clearScreenRGB ( int r, int g, int b )
    {
        // Configure color range constraints.

        int colorRangeMin = 0;
        int colorRangeMax = 255;

        // Clip any color range overflows that may be found.

        r = ( r < colorRangeMin ) ? colorRangeMin : r;
        g = ( g < colorRangeMin ) ? colorRangeMin : g;
        b = ( b < colorRangeMin ) ? colorRangeMin : b;

        r = ( r > colorRangeMax ) ? colorRangeMax : r;
        g = ( g > colorRangeMax ) ? colorRangeMax : g;
        b = ( b > colorRangeMax ) ? colorRangeMax : b;

        // Convert RGB color model, to HSL color model.

        float[] hsl = Color.RGBtoHSB ( r, g, b, null );     // RGB to HSL.
        float   h   = hsl [ M_INDEX_HUE        ];                   // Hue.
        float   s   = hsl [ M_INDEX_SATURATION ];                   // Saturation.
        float   l   = hsl [ M_INDEX_LIGHTNESS  ];                   // Lightness.

        // Clear the screen using the HSL color model.

        clearScreenHSL ( h, s, l );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clearScreenRGB
    //
    // Description:
    //
    //   Clears the screen to a specified color, defined by unit-normalized floating point RGB color parameters.
    //
    //   - Most often used to clear the screen and prepare the drawing surface for a new animation frame.
    //
    //   - This is a floating point overloaded version of clearScreenRGB(int, int, int). Doubles are used instead of
    //     floats for convenience, avoiding the need to append 'f' suffixes to floating point literals.
    //
    // Arguments:
    //
    //   fR (double):
    //     Red channel, in the range 0.0 ≤ fR ≤ 1.0.
    //
    //   fG (double):
    //     Green channel, in the range 0.0 ≤ fG ≤ 1.0.
    //
    //   fB (double):
    //     Blue channel, in the range 0.0 ≤ fB ≤ 1.0.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clearScreenRGB ( double fR, double fG, double fB )
    {
        // Specify 8-bit integer color range.

        int integerColorRangeMax = 255;

        // Convert unit ranged real RGB color elements, to 8-bit ranged integer RGB color elements.

        int iR = (int) ( integerColorRangeMax * fR );
        int iG = (int) ( integerColorRangeMax * fG );
        int iB = (int) ( integerColorRangeMax * fB );

        // Clear the screen, using the calculated integer values.

        clearScreenRGB ( iR, iG, iB );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: drawCrossHair
    //
    // Description:
    //
    //   Draws a crosshair shape at a specific location on the screen.
    //
    //   - Most often used for visualization of an object's geometric center or center of mass.
    //
    // Arguments:
    //
    //   x (double):
    //     The x-coordinate of the crosshair center.
    //
    //   y (double):
    //     The y-coordinate of the crosshair center.
    //
    //   r (double):
    //     The radius (full extent) of the crosshair arms.
    //
    //   color (Color):
    //     The color of the cross hair.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void drawCrossHair ( double x, double y, double r, Color color )
    {
        double dr2 = r / 2.0;

        g.setColor ( color );

        g.draw ( new Line2D.Double ( x,       y - dr2, x,       y + dr2 ) );
        g.draw ( new Line2D.Double ( x - dr2, y,       x + dr2, y       ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: drawGrid
    //
    // Description:
    //
    //   Draws a configurable guide grid with major and minor subdivisions and optional axis lines.
    //
    //   - Most often used for analysis and debugging geometry.
    //
    // Arguments:
    //
    //   x0 (double):
    //     The x-coordinate of the top-left corner.
    //
    //   y0 (double):
    //     The y-coordinate of the top-left corner.
    //
    //   x1 (double):
    //     The x-coordinate of the bottom-right corner.
    //
    //   y1 (double):
    //     The y-coordinate of the bottom-right corner.
    //
    //   majorSX (double):
    //     The number of major subdivisions along the x-axis (per half-axis).
    //
    //   majorSY (double):
    //     The number of major subdivisions along the y-axis (per half-axis).
    //
    //   minorSX (double):
    //     The number of minor subdivisions along the x-axis (per half-axis).
    //
    //   minorSY (double):
    //     The number of minor subdivisions along the y-axis (per half-axis).
    //
    //   visibleAxisX (Boolean):
    //     Whether the vertical axis line (x-axis center line) is visible.
    //
    //   visibleAxisY (Boolean):
    //     Whether the horizontal axis line (y-axis center line) is visible.
    //
    //   visibleMajor (Boolean):
    //     Whether major subdivision lines are visible.
    //
    //   visibleMinor (Boolean):
    //     Whether minor subdivision lines are visible.
    //
    //   colorAxis (Color):
    //     The color of the axis lines.
    //
    //   colorMajor (Color):
    //     The color of the major subdivision lines.
    //
    //   colorMinor (Color):
    //     The color of the minor subdivision lines.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void drawGrid
    (
        double  x0,
        double  y0,
        double  x1,
        double  y1,
        double  majorSX,
        double  majorSY,
        double  minorSX,
        double  minorSY,
        Boolean visibleAxisX,
        Boolean visibleAxisY,
        Boolean visibleMajor,
        Boolean visibleMinor,
        Color   colorAxis,
        Color   colorMajor,
        Color   colorMinor
    )
    {
        // Initialize working coordinate variables.

        double x           = 0.0;
        double y           = 0.0;
        double sx2         = 0.0;
        double sy2         = 0.0;
        double columnWidth = 0.0;
        double rowHeight   = 0.0;

        // Calculate Cartesian range.

        double dx = x1 - x0;
        double dy = y1 - y0;

        // Configure line styles.

        final float       lineStyleFormatDotted[]  = { 1.0f, 4.0f };
        final BasicStroke lineStyleDefault         = (BasicStroke) g.getStroke ();
        final BasicStroke lineStyleSolid           = new BasicStroke ( 1.0f );
        final BasicStroke lineStyleSolidBold       = new BasicStroke ( 3.0f );
        final BasicStroke lineStyleDitted          = new BasicStroke ( 1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, lineStyleFormatDotted, 0.0f );


        // Draw minor column lines.

        if ( visibleMinor )
        {
            // Calculate column and row sizes.

            sx2         = 2.0 * minorSX;        // Double subdivisions, to accommodate for positive and negative side of the axis.
            sy2         = 2.0 * minorSY;        // Double subdivisions, to accommodate for positive and negative side of the axis.
            columnWidth = dx / sx2;             // Column width.
            rowHeight   = dy / sy2;             // Row height.

            // Set drawing parameters for minor subdivisions.

            g.setColor  ( colorMinor );
            g.setStroke ( lineStyleDitted );

            // Draw minor columns.

            for ( int columnCount = 0; columnCount <= sx2; columnCount++ )
            {
                x = x0 + columnWidth * columnCount;
                g.draw ( new Line2D.Double ( x, y0, x, y1 ) );
            }

            // Draw minor rows.

            for ( int rowCount = 0; rowCount <= sy2; rowCount++ )
            {
                y = x0 + rowHeight * rowCount;
                g.draw ( new Line2D.Double ( x0, y, x1, y ) );
            }
        }

        // Draw major column lines.

        if ( visibleMajor )
        {
            // Calculate column and row sizes.

            sx2         = 2.0 * majorSX;        // Double subdivisions, to accommodate for positive and negative side of the axis.
            sy2         = 2.0 * majorSY;        // Double subdivisions, to accommodate for positive and negative side of the axis.
            columnWidth = dx / sx2;             // Column width.
            rowHeight   = dy / sy2;             // Row height.

            // Set drawing parameters for major subdivisions.

            g.setColor  ( colorMajor );
            g.setStroke ( lineStyleSolid );

            // Draw major columns.

            for ( int columnCount = 0; columnCount <= sx2; columnCount++ )
            {
                x = x0 + columnWidth * columnCount;
                g.draw ( new Line2D.Double ( x, y0, x, y1 ) );
            }

            // Draw major rows.

            for ( int rowCount = 0; rowCount <= sy2; rowCount++ )
            {
                y = x0 + rowHeight * rowCount;
                g.draw ( new Line2D.Double ( x0, y, x1, y ) );
            }
        }

        // Draw axes.

        g.setColor ( colorAxis );
        g.setStroke ( lineStyleSolidBold );

        double dx2 = ( x1 - x0 ) / 2.0;
        double dy2 = ( y1 - y0 ) / 2.0;

        if ( visibleAxisX ) g.draw ( new Line2D.Double ( dx2, y0,  dx2, y1  ) );
        if ( visibleAxisY ) g.draw ( new Line2D.Double (  x0, dy2, x1,  dy2 ) );

        // Restore line style.

        g.setStroke ( lineStyleDefault );
    }
}
