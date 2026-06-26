//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the TextFormat utility class, covering right-justification, left-justification,
//   center-justification, and string indentation operations.
//
// TODO:
//
//   1. Add tests for padString with strings longer than the target width.
//   2. Add tests for indentString with zero and negative indent values.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: TextFormatTest
//
// Description:
//
//   JUnit 5 test suite for the TextFormat utility class. Validates padding, justification, and indentation methods
//   across a range of inputs including empty strings, exact-length strings, and multi-line content.
//
//*********************************************************************************************************************

public class TextFormatTest
{
    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padRight_padsWithLeadingSpaces
    //
    // Description:
    //
    //   Verifies that padRight prepends spaces to a string shorter than the target width.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padRight_padsWithLeadingSpaces ()
    {
        String result = TextFormat.padRight ( "XYZ", 8 );
        assertEquals ( "     XYZ", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padRight_stringExactLength_noPadding
    //
    // Description:
    //
    //   Verifies that padRight returns the original string unchanged when its length equals the target width.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padRight_stringExactLength_noPadding ()
    {
        String result = TextFormat.padRight ( "ABCD", 4 );
        assertEquals ( "ABCD", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padRight_emptyString
    //
    // Description:
    //
    //   Verifies that padRight fills the entire width with spaces when given an empty string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padRight_emptyString ()
    {
        String result = TextFormat.padRight ( "", 5 );
        assertEquals ( "     ", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padLeft_padsWithTrailingSpaces
    //
    // Description:
    //
    //   Verifies that padLeft appends trailing spaces to a string shorter than the target width.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padLeft_padsWithTrailingSpaces ()
    {
        String result = TextFormat.padLeft ( "XYZ", 8 );
        assertEquals ( "XYZ     ", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padLeft_stringExactLength_noPadding
    //
    // Description:
    //
    //   Verifies that padLeft returns the original string unchanged when its length equals the target width.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padLeft_stringExactLength_noPadding ()
    {
        String result = TextFormat.padLeft ( "ABCD", 4 );
        assertEquals ( "ABCD", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padLeft_emptyString
    //
    // Description:
    //
    //   Verifies that padLeft fills the entire width with spaces when given an empty string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padLeft_emptyString ()
    {
        String result = TextFormat.padLeft ( "", 5 );
        assertEquals ( "     ", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padString_leftJustification
    //
    // Description:
    //
    //   Verifies that padString with LEFT justification appends trailing spaces to fill the target width.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padString_leftJustification ()
    {
        String result = TextFormat.padString ( "Hi", 10, TextFormat.TextJustification.LEFT );
        assertEquals ( "Hi        ", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padString_rightJustification
    //
    // Description:
    //
    //   Verifies that padString with RIGHT justification prepends leading spaces to fill the target width.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padString_rightJustification ()
    {
        String result = TextFormat.padString ( "Hi", 10, TextFormat.TextJustification.RIGHT );
        assertEquals ( "        Hi", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padString_centerJustification
    //
    // Description:
    //
    //   Verifies that padString with CENTER justification produces a result of the correct length containing the
    //   original text.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padString_centerJustification ()
    {
        String result = TextFormat.padString ( "Hi", 10, TextFormat.TextJustification.CENTER );
        assertEquals ( 10, result.length () );
        assertTrue ( result.contains ( "Hi" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: padString_centerJustification_symmetry
    //
    // Description:
    //
    //   Verifies that padString with CENTER justification distributes leading and trailing spaces symmetrically,
    //   with at most one character of difference.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void padString_centerJustification_symmetry ()
    {
        String result = TextFormat.padString ( "AB", 10, TextFormat.TextJustification.CENTER );
        int leadingSpaces = result.indexOf ( 'A' );
        int trailingSpaces = result.length () - result.lastIndexOf ( 'B' ) - 1;
        assertTrue ( Math.abs ( leadingSpaces - trailingSpaces ) <= 1 );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: indentString_singleLine
    //
    // Description:
    //
    //   Verifies that indentString prepends the specified number of spaces to a single-line string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void indentString_singleLine ()
    {
        TextFormat tf = new TextFormat ();
        String result = tf.indentString ( "Hello", 4 );
        assertEquals ( "    Hello", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: indentString_multipleLines
    //
    // Description:
    //
    //   Verifies that indentString prepends the specified number of spaces to every line in a multi-line string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void indentString_multipleLines ()
    {
        TextFormat tf = new TextFormat ();
        String result = tf.indentString ( "Line1\nLine2\nLine3", 4 );
        assertTrue ( result.startsWith ( "    Line1" ) );
        assertTrue ( result.contains ( "\n    Line2" ) );
        assertTrue ( result.contains ( "\n    Line3" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: indentString_positiveIndent_singleLine
    //
    // Description:
    //
    //   Verifies that indentString correctly applies a small positive indent to a single-line string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void indentString_positiveIndent_singleLine ()
    {
        TextFormat tf = new TextFormat ();
        String result = tf.indentString ( "Hello", 2 );
        assertEquals ( "  Hello", result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: indentString_emptyString
    //
    // Description:
    //
    //   Verifies that indentString produces only whitespace when given an empty string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void indentString_emptyString ()
    {
        TextFormat tf = new TextFormat ();
        String result = tf.indentString ( "", 4 );
        assertEquals ( "    ", result );
    }
}
