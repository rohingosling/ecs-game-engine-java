//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Application settings utility class. Provides a convenient wrapper around the standard Java Properties class for
//   loading and accessing key-value configuration data from .properties files.
//
//   Supports typed accessors for String, int, Boolean, long, and double values, as well as a formatted toString
//   method that produces an aligned, optionally sorted listing of all loaded settings.
//
// TODO:
//
//   1. Add error handling for malformed property values in typed accessors.
//   2. Add support for default values when a key is not found.
//   3. Consider adding a save or export method for writing settings back to disk.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

//*********************************************************************************************************************
// Class: ApplicationSettings
//
// Description:
//
//   A wrapper around the Java Properties class that simplifies loading and retrieving application configuration from
//   *.properties files. 
// 
//   - The class provides two constructors: a default no-argument constructor for deferred loading, and a 
//     parameterized constructor that loads a properties file immediately on creation.
//
//   - Typed accessor methods (getString, getInteger, getBoolean, getLong, getDouble) delegate to a central getSetting
//     method that retrieves the raw string value and throws a formatted exception if the key is not found. 
// 
//   - The toString method produces a human-readable, column-aligned listing of all loaded settings, with optional 
//     sorting and value bracketing.
//
//*********************************************************************************************************************

public class ApplicationSettings
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private Properties settings;
    private String     fileName;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: GetSettings
    //
    // Description:
    //
    //   Returns the underlying Properties object containing all loaded application settings. This accessor provides
    //   direct access to the internal properties collection for cases where the caller needs to iterate or inspect
    //   the full set of key-value pairs.
    //
    // Returns:
    //
    //   The Properties object holding the loaded application settings, or null if no settings file has been loaded.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Properties GetSettings () { return this.settings; }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/2: ApplicationSettings
    //
    // Description:
    //
    //   Default constructor. Initializes the settings field to null and the file name to an empty string. Use the
    //   load method to populate settings after construction.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ApplicationSettings ()
    {
        this.settings = null;
        this.fileName = "";
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 2/2: ApplicationSettings
    //
    // Description:
    //
    //   Parameterized constructor. Opens and loads the specified .properties file immediately by delegating to the
    //   load method.
    //
    // Arguments:
    //
    //   fileName (String):
    //     The file path of the .properties file to load.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public ApplicationSettings ( String fileName)
    {
        load ( fileName );
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: load
    //
    // Description:
    //
    //   Opens and loads a .properties file into the internal Properties object. If the file cannot be read, an
    //   IOException stack trace is printed to standard error.
    //
    // Arguments:
    //
    //   fileName (String):
    //     The file path of the .properties file to open and load.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void load ( String fileName )
    {
        // Load the properties file.

        try
        {
            this.fileName = fileName;
            this.settings = new Properties ();
            this.settings.load ( new FileInputStream ( fileName ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace ();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getSetting
    //
    // Description:
    //
    //   Retrieves an application setting value from the loaded properties by its key. If the key is not found, a
    //   NullPointerException is thrown and formatted via TextFormat.printFormattedException, which terminates the
    //   application.
    //
    // Arguments:
    //
    //   key (String):
    //     The property key used to look up the requested application setting.
    //
    // Returns:
    //
    //   The setting value string associated with the given key, or null if the key was not found and exception
    //   handling did not terminate the application.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String getSetting ( String key )
    {
        // We will set to null, in order to trigger a null pointer exception by default.
        //
        // - Unless we find a valid settings value string in the application properties file, in which case we will 
        //   return what we have retrieved from the file.

        String value = null;

        // Attempt to retrieve a setting value from the settings file.

        try
        {
            // Attempt to retrieve settings value string from properties file.
            // Throw a null pointer exception if we couldn't find the requested setting string.

            value = this.settings.getProperty ( key );                  
            if ( value == null ) throw new NullPointerException ();     
        }
        catch ( Exception e )
        {
            Boolean exitApplicationOnException = true;
            TextFormat.printFormattedException ( e, exitApplicationOnException );
        }

        // Return the setting value string that we retrieved, or null if we failed to find the requested setting in 
        // the application properties file.

        return value;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getString
    //
    // Description:
    //
    //   Retrieves a string application setting from the loaded properties. This is a convenience wrapper that
    //   delegates directly to getSetting.
    //
    // Arguments:
    //
    //   key (String):
    //     The property key used to look up the requested string setting.
    //
    // Returns:
    //
    //   The setting value as a String.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String getString ( String key )
    {
        return getSetting ( key );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getInteger
    //
    // Description:
    //
    //   Retrieves an integer application setting from the loaded properties. The raw string value is parsed via
    //   Integer.parseInt.
    //
    // Arguments:
    //
    //   key (String):
    //     The property key used to look up the requested integer setting.
    //
    // Returns:
    //
    //   The setting value parsed as an int.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public int getInteger ( String key )
    {
        return Integer.parseInt ( getSetting ( key ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getBoolean
    //
    // Description:
    //
    //   Retrieves a boolean application setting from the loaded properties. The raw string value is parsed via
    //   Boolean.valueOf.
    //
    // Arguments:
    //
    //   key (String):
    //     The property key used to look up the requested boolean setting.
    //
    // Returns:
    //
    //   The setting value parsed as a Boolean.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Boolean getBoolean ( String key )
    {
        return Boolean.valueOf ( getSetting ( key ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getLong
    //
    // Description:
    //
    //   Retrieves a long application setting from the loaded properties. The raw string value is parsed via
    //   Long.parseLong.
    //
    // Arguments:
    //
    //   key (String):
    //     The property key used to look up the requested long setting.
    //
    // Returns:
    //
    //   The setting value parsed as a long.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public long getLong ( String key )
    {
        return Long.parseLong ( getSetting ( key ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getDouble
    //
    // Description:
    //
    //   Retrieves a double application setting from the loaded properties. The raw string value is parsed via
    //   Double.parseDouble.
    //
    // Arguments:
    //
    //   key (String):
    //     The property key used to look up the requested double setting.
    //
    // Returns:
    //
    //   The setting value parsed as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double getDouble ( String key )
    {
        return Double.parseDouble ( getSetting ( key ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: toString
    //
    // Description:
    //
    //   Generates a formatted, human-readable listing of all application settings loaded from the .properties file.
    //   Property keys are sorted alphabetically by default and displayed in a column-aligned format with their
    //   values.
    //
    //   Example output:
    //
    //     Application settings file: "game_engine_1.0.properties"
    //
    //       GameEngine.Application.Name           = Game Engine
    //       GameEngine.DataOverlay.Visible        = true
    //       GameEngine.FPS.Target                 = 90
    //       GameEngine.Screen.Height              = 600
    //       GameEngine.Screen.Width               = 800
    //
    //   The method supports optional right-alignment of property keys and optional bracketing of property values.
    //
    // Returns:
    //
    //   A formatted string containing the complete, column-aligned settings listing.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String toString ()
    {
        // Constants.

        final String S_EMPTY                     = "";
        final String S_NEW_LINE                  = "\n";
        final String S_QUOTE_OPEN                = "\"";
        final String S_QUOTE_CLOSE               = "\"";
        final String S_BRACKET_OPEN              = "(";
        final String S_BRACKET_CLOSE             = ")";
        final String S_ASSIGNMENT                = " = ";
        final String S_INDENT                    = "  ";
        final String S_START_STRING              = "%";
        final String S_END_STRING                = "s";
        final String S_LEFT_JUSTIFY              = "-";
        final String S_RIGHT_JUSTIFY             = "";
        final String S_TEXT_APPLICATION_SETTINGS = "Application settings file: ";

        // Message configuration.

        Boolean sortPropertyList      = true;               // Do we want to sort the list or not.
        Boolean enclosePropertyValues = false;              // Enclose property values within user specified bracketing characters.
        Boolean rightAlign            = false;              // Right align the property fileName column. We will make the default, left alignment

        // Configure tabulation and column alignment.

        String alignmentFlag            = S_LEFT_JUSTIFY;   // Set to left alignment by default. "-" = Left Align, Empty string "" = Right Align.
        if ( rightAlign ) alignmentFlag = S_RIGHT_JUSTIFY;  // Set to right align if flagged to do so.

        // Configure property value bracketing format.

        String bracketOpen;
        String bracketClose;

        if ( enclosePropertyValues )
        {
            bracketOpen  = S_BRACKET_OPEN;  // Set to an open bracket string, e.g "'", "(", "( ", "< ", "[ ", etc.
            bracketClose = S_BRACKET_CLOSE; // Set to an close bracket string, e.g "'", ")", " )", " >", " ]", etc.
        }
        else
        {
            bracketOpen  = S_EMPTY;         // Set to an empty string.
            bracketClose = S_EMPTY;         // Set to an empty string.
        }

        // Initialize the settings list header.

        String textBuffer = S_EMPTY;

        textBuffer += S_NEW_LINE + S_NEW_LINE;
        textBuffer += S_INDENT + S_TEXT_APPLICATION_SETTINGS;
        textBuffer += S_QUOTE_OPEN + this.fileName + S_QUOTE_CLOSE;
        textBuffer += S_NEW_LINE + S_NEW_LINE;

        // It easer to sort an array of strings, than a collection, a map or a set.
        // Therefore, we shall get a set of property names from the collection, and then
        // convert the set into an array of strings.

        Set <String> keySet = this.settings.stringPropertyNames ();                 // Get set of property fileName strings.
        String [] keyArray  = ( String [] ) keySet.toArray ( new String [ 0 ] );    // Convert the set into an array of strings, so that we can easily sort the array.

        // Sort the application settings property names.

        if ( sortPropertyList ) Arrays.sort ( keyArray );

        // Get the string length of the longest property fileName, so that we know where to place the aligned property column.
        // At the end of the loop, longestKey will be the value the longest property fileName.

        int longestKey = 0;

        for ( String key : keyArray )
        {
         // Get the length of the current property fileName.

            int keyLength = key.length ();

            // If its longer than any of the others, then keep its length.

            if ( keyLength > longestKey ) longestKey = keyLength;
        }

        // Add the formatted list of property names (keys) and their property values to the output text list..

        for ( String key : keyArray )
        {
            String formatedkey = S_INDENT;

            // Align text and pad with white space up to the length of the longest property fileName.

            formatedkey += String.format ( S_START_STRING + alignmentFlag + longestKey + S_END_STRING, key );

            // Add the formatted property fileName with white space padding.

            textBuffer += formatedkey + S_ASSIGNMENT;

            // Add the property value.

            textBuffer += bracketOpen + getSetting ( key ) + bracketClose;
            textBuffer += S_NEW_LINE;
        }

        // Send the completed logger message back to the caller.

        return textBuffer;
    }
}
