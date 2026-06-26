//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   The StringTable class implements a multi-language key-based string mapping table. Support for hotkeys associated
//   with particular strings is also supported.
//
//   Strings are loaded in from an XML file. To support multiple languages, one creates a file for each language, using
//   the same string IDs for each equivalent string. An application then simply loads in the file for the language it
//   wishes to use.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

//*********************************************************************************************************************
// Class: StringTable
//
// Description:
//
//   The StringTable class implements a multi-language key-based string mapping table. Support for hotkeys associated
//   with particular strings is also supported.
//
//   Strings are loaded in from an XML file. To support multiple languages, one creates a file for each language, using
//   the same string IDs for each equivalent string. An application then simply loads in the file for the language it
//   wishes to use.
//
//   Example:
//
//   File: string_table_english.xml
//
//     <?xml version="1.0" encoding="UTF-8"?>
//     <StringTable language="English" languageNativeName="English" >
//
//         <String id="IDS_YES"         value="Yes"          hotkey="Y" />
//         <String id="IDS_NO"          value="No"           hotkey="N" />
//         <String id="IDS_HELLO_WORLD" value="Hello World!" hotkey=""  />
//
//     </StringTable>
//
//   File: string_table_klingon.xml
//
//     <?xml version="1.0" encoding="UTF-8"?>
//     <StringTable language="English" languageNativeName="English" >
//
//         <String id="IDS_YES"              value="HIja"      hotkey="H" />
//         <String id="IDS_NO"               value="Qo"        hotkey="Q" />
//         <String id="IDS_TEST_HELLO_WORLD" value="qo' vIvan" hotkey=""  />
//
//     </StringTable>
//
//*********************************************************************************************************************

public class StringTable
{
    //@formatter:off

    //=================================================================================================================
    // Constants
    //=================================================================================================================

    static final String XML_TAG_STRING_TABLE                            = "StringTable";
    static final String XML_ATTRIBUTE_STRING_TABLE_LANGUAGE_NATIVE_NAME = "languageNativeName";
    static final String XML_ATTRIBUTE_STRING_TABLE_LANGUAGE             = "language";
    static final String XML_TAG_STRING                                  = "String";
    static final String XML_ATTRIBUTE_STRING_ID                         = "id";
    static final String XML_ATTRIBUTE_STRING_VALUE                      = "value";
    static final String XML_ATTRIBUTE_STRING_HOT_KEY                    = "hotkey";

    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private String                      fileName;           // The string table file fileName.
    private String                      language;           // Language fileName loaded in with the string table file.
    private String                      languageNativeName; // Native language fileName loaded in with the string table file.
    private String                      nullString;         // Default string returned, if no string is found in the string table.
    private HashMap < String, String >  stringMap;          // Hash table based string map. Populated from an XML string table file.
    private HashMap < String, String >  hotkeyMap;          // Hash table based hotkey map. Populated from an XML string table file.

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: StringTable
    //
    // Description:
    //
    //   Default constructor. Initializes the string table by calling the initialize method, which sets up the string
    //   and hotkey hash maps and assigns default values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public StringTable ()
    {
        initialize ();
    }

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getFileName
    //
    // Description:
    //
    //   Return the file name of the currently loaded string table.
    //
    // Returns:
    //
    //   The string table file name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String getFileName           () { return this.fileName; }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getLanguage
    //
    // Description:
    //
    //   Return the language name associated with the currently loaded string table.
    //
    // Returns:
    //
    //   The language name string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String getLanguage           () { return this.language; }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getLanguageNativeName
    //
    // Description:
    //
    //   Return the native language name associated with the currently loaded string table.
    //
    // Returns:
    //
    //   The native language name string.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String getLanguageNativeName () { return this.languageNativeName; }

    //@formatter:on

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Clear and reset the string table. Initializes or clears both the string map and the hotkey map, and resets
    //   the default null string value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        // Initialize of clear the string map.

        if ( this.stringMap == null ) this.stringMap = new HashMap <String, String> ();
        else                          this.stringMap.clear ();

        // Initialize or clear the hot key map.

        if ( this.hotkeyMap == null ) this.hotkeyMap = new HashMap <String, String> ();
        else                          this.stringMap.clear ();

        // Initialize default values.

        this.nullString = "NULL";
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getHotkey
    //
    // Description:
    //
    //   Get the hotkey for a particular string. Looks up the hotkey map using the specified string ID and returns the
    //   associated hotkey. If the string ID is not found, the default null string is returned.
    //
    // Arguments:
    //
    //   ids (String):
    //     The string ID for the string to search for.
    //
    // Returns:
    //
    //   The hotkey string referenced by the string ID.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String getHotkey ( String ids )
    {
        String string = this.hotkeyMap.get ( ids );        // Get the string from the string map.
        if ( string == null ) string = this.nullString;    // If the string can not be found, then return the default null string.
        return string;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getString
    //
    // Description:
    //
    //   Get the string for a particular string ID. Looks up the string map using the specified string ID and returns
    //   the associated value. If the string ID is not found, the default null string is returned.
    //
    // Arguments:
    //
    //   ids (String):
    //     The string ID for the string to search for.
    //
    // Returns:
    //
    //   The string referenced by the string ID.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String getString ( String ids )
    {
        String string = this.stringMap.get ( ids );        // Get the string from the string map.
        if ( string == null ) string = this.nullString;    // If the string can not be found, then return the default null string.
        return string;                                     // Return what we have to the caller.
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: xmlGetLanguage
    //
    // Description:
    //
    //   Retrieve the language from an XML file. Parses the given XML event for the StringTable start element and
    //   extracts the language and native language name attributes.
    //
    // Arguments:
    //
    //   xmlEvent (XMLEvent):
    //     The XML event required to begin searching for the desired XML element.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void xmlGetLanguage ( XMLEvent xmlEvent )
    {
        if ( xmlEvent.isStartElement () )
        {
            // Start parsing the XML file at the first element.

            StartElement startElement = xmlEvent.asStartElement ();

            // Parse the main StringTable tag.

            if ( startElement.getName ().getLocalPart () == XML_TAG_STRING_TABLE )
            {
                // Read the attributes from the StringTable tag, and update the language names.

                @SuppressWarnings ( "unchecked" )
                Iterator < Attribute > attributes = startElement.getAttributes();

                while ( attributes.hasNext () )
                {
                    Attribute attribute     = attributes.next ();
                    String    attributeName = attribute.getName ().toString ();

                    switch ( attributeName )
                    {
                        // Get the language fileName from the StringTable tag.

                        case XML_ATTRIBUTE_STRING_TABLE_LANGUAGE: this.language =  attribute.getValue (); break;

                        // Get the native language fileName from the StringTable tag.

                        case XML_ATTRIBUTE_STRING_TABLE_LANGUAGE_NATIVE_NAME: this.languageNativeName = attribute.getValue (); break;

                    } // switch

                } // while

            } // if

        } // if
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: xmlGetStringData
    //
    // Description:
    //
    //   Retrieve the string ID, value, and hotkey from an open XML file. Parses the given XML event for String start
    //   elements and extracts the id, value, and hotkey attributes, then stores them in the string and hotkey hash
    //   maps.
    //
    // Arguments:
    //
    //   xmlEvent (XMLEvent):
    //     The XML event required to begin searching for the desired XML element.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void xmlGetStringData ( XMLEvent xmlEvent )
    {
        if ( xmlEvent.isStartElement () )
        {
            // Start parsing the XML file at the first element.

            StartElement startElement = xmlEvent.asStartElement ();

            // Parse the main StringTable tag.

            if ( startElement.getName ().getLocalPart () == XML_TAG_STRING )
            {
                // Initialize strings that we will use to collect the attribute values.

                String ids    = this.nullString;
                String value  = this.nullString;
                String hotkey = this.nullString;

                // Initialize attribute

                Attribute attribute     = null;
                String    attributeName = null;

                // Read the attributes from a String tag, and update the string data.

                @SuppressWarnings ( "unchecked" )
                Iterator < Attribute > attributes = startElement.getAttributes();

                while ( attributes.hasNext () )
                {
                    attribute     = attributes.next ();
                    attributeName = attribute.getName ().toString ();

                    switch ( attributeName )
                    {
                        case XML_ATTRIBUTE_STRING_ID:      ids    = attribute.getValue (); break;   // Get the ID string.
                        case XML_ATTRIBUTE_STRING_VALUE:   value  = attribute.getValue (); break;   // Get the string.
                        case XML_ATTRIBUTE_STRING_HOT_KEY: hotkey = attribute.getValue (); break;   // Get the hot-key.
                    }
                } // while

                // Load Hash maps.

                if ( attributeName != null)
                {
                    this.stringMap.put ( ids, value  );
                    this.hotkeyMap.put ( ids, hotkey );
                }
            } // if
        } // if
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: loadStringTable
    //
    // Description:
    //
    //   Load an XML string table. Clears the current string table, then reads the specified XML file, parsing the
    //   language attributes and all string entries with their associated IDs, values, and hotkeys.
    //
    //   Example XML File:
    //
    //   File: string_table_english.xml
    //
    //     <?xml version="1.0" encoding="UTF-8"?>
    //     <StringTable language="English" languageNativeName="English" >
    //
    //         <String id="IDS_YES"         value="Yes"          hotkey="Y" />
    //         <String id="IDS_NO"          value="No"           hotkey="N" />
    //         <String id="IDS_HELLO_WORLD" value="Hello World!" hotkey=""  />
    //
    //     </StringTable>
    //
    // Arguments:
    //
    //   fileName (String):
    //     File name of the XML file to open.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void loadStringTable ( String fileName )
    {
        // clear the string table.

        initialize ();

        // Load in the file.

        try
        {
            // Create a new XML input factory.

            XMLInputFactory inputFactory = XMLInputFactory.newInstance ();

            // Setup a new XML event reader.

            InputStream    inputStream    = new FileInputStream               ( fileName    );
            XMLEventReader xmlEventReader = inputFactory.createXMLEventReader ( inputStream );

            // Get the language fileName out of the StringTable tag.

            while ( xmlEventReader.hasNext () )
            {
                XMLEvent xmlEvent = xmlEventReader.nextEvent ();

                xmlGetLanguage   ( xmlEvent );
                xmlGetStringData ( xmlEvent );

            } // while

            // Update the file fileName of the last successfully loaded string table.

            this.fileName = fileName;
        }
        catch ( FileNotFoundException e )
        {
            TextFormat.printFormattedException ( e, true );
        }
        catch ( XMLStreamException e )
        {
            TextFormat.printFormattedException ( e, true );
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: toString
    //
    // Description:
    //
    //   Override of the standard Java object toString method. Returns a string representation of this StringTable
    //   instance, including the file name of the loaded string table.
    //
    // Returns:
    //
    //   A string representation of this StringTable object.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public String toString ()
    {
        return "File fileName = " + this.fileName;
    }

}
