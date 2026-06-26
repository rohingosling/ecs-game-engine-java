//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   A suite of various text formatting and manipulation functions.
//
// TODO:
//
//   1. No outstanding TODOs.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: TextFormat
//
// Description:
//
//   A utility class providing a suite of static text formatting and manipulation functions. Includes methods for
//   string padding with configurable justification (left, right, center), formatted exception printing with stack
//   trace output, and text indentation.
//
//*********************************************************************************************************************

public class TextFormat
{
    //=================================================================================================================
    // User Defined Data Types
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Enum: TextJustification
    //
    // Description:
    //
    //   Enumerates the supported text justification modes for use with the padString method. The available modes are
    //   CENTER, LEFT, and RIGHT alignment.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static enum TextJustification
    {
        CENTER, LEFT, RIGHT
    }

    //=================================================================================================================
    // Constants
    //=================================================================================================================

    private static final String M_EMPTY                                         = "";
    @ SuppressWarnings ( "unused" ) private static final String M_SPACE         = " ";
    @ SuppressWarnings ( "unused" ) private static final String M_TAB           = "\t";
    private static final String M_NEW_LINE                                      = "\n";
    @ SuppressWarnings ( "unused" ) private static final String M_START_STRING  = "%";
    @ SuppressWarnings ( "unused" ) private static final String M_END_STRING    = "s";
    @ SuppressWarnings ( "unused" ) private static final String M_LEFT_JUSTIFY  = "-";
    @ SuppressWarnings ( "unused" ) private static final String M_RIGHT_JUSTIFY = "";

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: TextFormat
    //
    // Description:
    //
    //   Default constructor. No initialization is required since all methods in this class are static utility
    //   functions.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public TextFormat ()
    {
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padRight
    //
    // Description:
    //
    //   Pad a string with whitespace and right-align it within the specified width.
    //
    //   Uses the Java String.format pattern "%1$<length>s", where the format specifiers are as follows.
    //
    //   - "%"       = Start string format.
    //
    //   - "1$"      = Select argument 1, the optional string to display. This is optional when only using one
    //                 argument.
    //
    //   - <length>  = The maximum length of the string after whitespace has been added.
    //
    //   - "-" or "" = Alignment specifier. "-" means left-align, and an empty string "" means right-align.
    //
    //   - "s"       = End string format, specifying the argument type as String.
    //
    //   Examples, where the column ruler is "12345678".
    //
    //   - ( "%1$8s-", "XYZ"      ) = "XYZ     "
    //
    //   - ( "%1$8s",  "XYZ"      ) = "     XYZ"
    //
    //   - ( "%8s",    "XYZ"      ) = "     XYZ"  (Optional argument specifier omitted.)
    //
    //   - ( "%1$4s,   "ABCDEFGH" ) = "ABCD"
    //
    //   - ( "%1$4s-,  "ABCDEFGH" ) = "ABCD"
    //
    // Arguments:
    //
    //   string (String):
    //     The string to pad with whitespace.
    //
    //   length (int):
    //     The total string width to pad up to.
    //
    // Returns:
    //
    //   The padded, right-aligned string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static String padRight ( String string, int length )
    {
        return String.format ( "%1$" + length + "s", string );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padLeft
    //
    // Description:
    //
    //   Pad a string with whitespace and left-align it within the specified width.
    //
    //   Uses the Java String.format pattern "%1$-<length>s", where the format specifiers are as follows.
    //
    //   - "%"       = Start string format.
    //
    //   - "1$"      = Select argument 1, the optional string to display. This is optional when only using one
    //                 argument.
    //
    //   - <length>  = The maximum length of the string after whitespace has been added.
    //
    //   - "-" or "" = Alignment specifier. "-" means left-align, and an empty string "" means right-align.
    //
    //   - "s"       = End string format, specifying the argument type as String.
    //
    //   Examples, where the column ruler is "12345678".
    //
    //   - ( "%1$8s-", "XYZ"      ) = "XYZ     "
    //
    //   - ( "%1$8s",  "XYZ"      ) = "     XYZ"
    //
    //   - ( "%8s",    "XYZ"      ) = "     XYZ"  (Optional argument specifier omitted.)
    //
    //   - ( "%1$4s,   "ABCDEFGH" ) = "ABCD"
    //
    //   - ( "%1$4s-,  "ABCDEFGH" ) = "ABCD"
    //
    // Arguments:
    //
    //   string (String):
    //     The string to pad with whitespace.
    //
    //   length (int):
    //     The total string width to pad up to.
    //
    // Returns:
    //
    //   The padded, left-aligned string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static String padLeft ( String string, int length )
    {
        return String.format ( "%1$-" + length + "s", string );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padString
    //
    // Description:
    //
    //   Pad a string with whitespace and apply text justification, supporting right, left, or center alignment.
    //
    //   This method reuses the previously defined padLeft and padRight methods to implement the justification logic.
    //   For center alignment, the string is first right-padded to center it, then left-padded to fill the remaining
    //   width.
    //
    // Arguments:
    //
    //   string (String):
    //     The string to pad with whitespace.
    //
    //   paddedLength (int):
    //     The new string width, including padding characters.
    //
    //   textJustification (TextJustification):
    //     The text justification mode to apply. One of CENTER, LEFT, or RIGHT.
    //
    // Returns:
    //
    //   The padded, justified string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static String padString ( String string, int paddedLength, TextJustification textJustification )
    {
        // Create a new string to be used to pass back to the caller.

        String justifiedString = new String ();

        // Apply text justification logic.

        switch ( textJustification )
        {
        case CENTER:
            justifiedString = padLeft ( padRight ( string, paddedLength / 2 + string.length() / 2 ), paddedLength );
            break;

        case LEFT:
            justifiedString = padLeft ( string, paddedLength );
            break;

        case RIGHT:
            justifiedString = padRight ( string, paddedLength );
            break;
        }

        // REturn the resultant justified text back to the caller.

        return justifiedString;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: printFormattedException
    //
    // Description:
    //
    //   Print a formatted exception to the console, including the exception message, type, and full stack trace.
    //   Optionally shuts down the application after printing the exception details.
    //
    // Arguments:
    //
    //   e (Exception):
    //     The exception to format and print to the console.
    //
    //   exitApplication (Boolean):
    //     If true, the application will be shut down after printing the exception details.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @ SuppressWarnings ( "unused" )
    public static void printFormattedException ( Exception e, Boolean exitApplication )
    {
        final String LABEL_EXCEPTION   = "EXCEPTION:";
        final String LABEL_APPLICATION = "APPLICATION:";
        final String LABEL_STACK_TRACE = "STACK TRACE:";
        final String MESSAGE_SHUT_DOWN = "Shutting down application.";
        final String BRACKET_OPEN      = "[";
        final String BRACKET_CLOSE     = "]: ";
        final String NEW_LINE          = "\n";
        final String TAB               = "\t";
        final String TAB_1             = TAB;
        final String TAB_2             = TAB + TAB;
        final String TAB_3             = TAB + TAB + TAB;
        final String TAB_4             = TAB + TAB + TAB + TAB;

        System.out.println ( NEW_LINE );
        if ( exitApplication )  System.out.println ( LABEL_APPLICATION + TAB_1 + MESSAGE_SHUT_DOWN );
        System.out.println ( LABEL_EXCEPTION   + TAB_2 + e );
        System.out.println ( LABEL_EXCEPTION   + TAB_2 + e.getMessage () );
        System.out.print   ( LABEL_STACK_TRACE + TAB_1 );

        int index = 0;
        for ( StackTraceElement stackTraceElement : e.getStackTrace () )
        {
            System.out.print   ( BRACKET_OPEN + index + BRACKET_CLOSE );
            System.out.println ( stackTraceElement.toString () );
            System.out.print   ( TAB_4 );
            index++;
        }

        if ( exitApplication ) System.exit (0);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: indentString
    //
    // Description:
    //
    //   Indent a body of text by a fixed number of spaces.
    //
    //   The body of text is a string with newline characters separating individual lines. The method prepends the
    //   specified number of spaces to the beginning of the string, then replaces all newline characters with a
    //   newline followed by the same indent, so that every line in the body is uniformly indented.
    //
    //   Example: Given the input string "Hello World!\nXYZ\n0123456789\n", if indent is 0 then the text will not
    //   be indented.
    //
    //   |Hello World!
    //   |XYZ
    //   |0123456789
    //
    //   If indent is 4, then each line will be indented by 4 spaces.
    //
    //   |    Hello World!
    //   |    XYZ
    //   |    0123456789
    //
    // Arguments:
    //
    //   message (String):
    //     The input body of text to indent.
    //
    //   indent (int):
    //     The number of space characters to indent by.
    //
    // Returns:
    //
    //   A new indented instance of the input text.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String indentString ( String message, int indent )
    {
        // Create a new string to store our indented output string.

        String indentedMessage = new String ();
        int    indentSize      = indent;

        // Pre-append an indent to the beginning for the string.

        indentedMessage = TextFormat.padLeft ( M_EMPTY, indentSize ) + message;

        // Replace all new line characters, with a new line character + and
        // indent.

        String substitute = M_NEW_LINE + TextFormat.padLeft ( M_EMPTY,    indentSize );
        indentedMessage   = indentedMessage.replaceAll      ( M_NEW_LINE, substitute );

        // Return the indented result to the caller.

        return indentedMessage;
    }

}
