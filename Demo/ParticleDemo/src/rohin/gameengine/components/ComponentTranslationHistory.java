// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    ComponentTranslationHistory
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Data component that holds translation history for rendering motion trails, including trail depth, color,
//   opacity gradient, and line thickness.
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.components;

import java.util.LinkedList;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.Vector2D;

public class ComponentTranslationHistory extends ECSComponent
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LinkedList<Vector2D> translationHistory      = new LinkedList<Vector2D> ();
    public int                  translationHistoryDepth = 500;
    public int[]                color                   = { 64, 64, 64 };
    public double               opacityTail             = 0.0;
    public double               opacityHead             = 0.5;
    public int                  thickness               = 4;


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

    public ComponentTranslationHistory ()
    {
        super ();
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 2
    // ----------------------------------------------------------------------------------------------------------------

    public ComponentTranslationHistory ( Integer id, String name, ECSEngine owner )
    {
        super ( id, name, owner );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        this.translationHistory      = new LinkedList<Vector2D> ();
        this.translationHistoryDepth = 500;
        this.color                   = new int[] { 64, 64, 64 };
        this.opacityTail             = 0.0;
        this.opacityHead             = 0.5;
        this.thickness               = 4;
    }
}
