// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentButtonImage
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds the image resource paths for each visual state of a button.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;

public class ComponentButtonImage extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // @formatter:off
    public String imageUp;
    public String imageUpSelected;
    public String imageDown;
    public String imageDownSelected;
    public String imageDisabled;
    public String imageShadow;
    // @formatter:on


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    //
    // Default constructor for use as a type token in hasComponents().
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentButtonImage ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentButtonImage ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        // @formatter:off
        this.imageUp           = "";
        this.imageUpSelected   = "";
        this.imageDown         = "";
        this.imageDownSelected = "";
        this.imageDisabled     = "";
        this.imageShadow       = "";
        // @formatter:on
    }
}
