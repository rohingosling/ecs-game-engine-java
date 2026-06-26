package rohin.gameengine.components;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.Vector2D;


// ========================================================================================
//
// ComponentTranslationHistoryTest
//
// Unit tests for ComponentTranslationHistory.
//
// ========================================================================================

public class ComponentTranslationHistoryTest
{
    private static final double EPSILON = 1e-9;


    // ========================================================================================
    //
    // Constructor Tests
    //
    // ========================================================================================

    @Test
    public void defaultConstructor_createsNonNullInstance ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertNotNull ( component );
    }


    @Test
    public void defaultConstructor_isSubclassOfECSComponent ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertInstanceOf ( ECSComponent.class, component );
    }


    // ========================================================================================
    //
    // Default Field Value Tests
    //
    // ========================================================================================

    @Test
    public void translationHistory_defaultsToEmptyLinkedList ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertNotNull ( component.translationHistory );
        assertTrue ( component.translationHistory.isEmpty () );
    }


    @Test
    public void translationHistoryDepth_defaultsTo500 ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertEquals ( 500, component.translationHistoryDepth );
    }


    @Test
    public void color_defaultsToDarkGray ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertArrayEquals ( new int[] { 64, 64, 64 }, component.color );
    }


    @Test
    public void opacityTail_defaultsTo0 ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertEquals ( 0.0, component.opacityTail, EPSILON );
    }


    @Test
    public void opacityHead_defaultsTo0Point5 ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertEquals ( 0.5, component.opacityHead, EPSILON );
    }


    @Test
    public void thickness_defaultsTo4 ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        assertEquals ( 4, component.thickness );
    }


    // ========================================================================================
    //
    // Field Mutability Tests
    //
    // ========================================================================================

    @Test
    public void translationHistory_isPubliclyModifiable ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        LinkedList<Vector2D> newHistory = new LinkedList<Vector2D> ();
        newHistory.add ( new Vector2D ( 1.0, 2.0 ) );
        newHistory.add ( new Vector2D ( 3.0, 4.0 ) );
        component.translationHistory = newHistory;
        assertEquals ( 2, component.translationHistory.size () );
    }


    @Test
    public void translationHistoryDepth_isPubliclyModifiable ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        component.translationHistoryDepth = 100;
        assertEquals ( 100, component.translationHistoryDepth );
    }


    @Test
    public void color_isPubliclyModifiable ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        component.color = new int[] { 255, 0, 0 };
        assertArrayEquals ( new int[] { 255, 0, 0 }, component.color );
    }


    @Test
    public void opacityTail_isPubliclyModifiable ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        component.opacityTail = 0.25;
        assertEquals ( 0.25, component.opacityTail, EPSILON );
    }


    @Test
    public void opacityHead_isPubliclyModifiable ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        component.opacityHead = 1.0;
        assertEquals ( 1.0, component.opacityHead, EPSILON );
    }


    @Test
    public void thickness_isPubliclyModifiable ()
    {
        ComponentTranslationHistory component = new ComponentTranslationHistory ();
        component.thickness = 2;
        assertEquals ( 2, component.thickness );
    }
}
