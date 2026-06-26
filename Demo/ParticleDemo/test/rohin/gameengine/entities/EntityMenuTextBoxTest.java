package rohin.gameengine.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentTextBox;

public class EntityMenuTextBoxTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntityMenuTextBox entity = new EntityMenuTextBox ( 0, "TextBox", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntityMenuTextBox entity = new EntityMenuTextBox ( 10, "TextBox", null );
        assertEquals ( 10, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntityMenuTextBox entity = new EntityMenuTextBox ( 0, "InstructionsTextBox", null );
        assertEquals ( "InstructionsTextBox", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntityMenuTextBox entity = new EntityMenuTextBox ( 0, "TextBox", null );
        assertEquals ( 1, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentTextBox ()
    {
        EntityMenuTextBox entity = new EntityMenuTextBox ( 0, "TextBox", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_TEXT_BOX );
        assertNotNull ( component );
        assertInstanceOf ( ComponentTextBox.class, component );
    }
}
