package rohin.gameengine.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import rohin.gameengine.ECSComponent;
import rohin.gameengine.application.Constants;
import rohin.gameengine.components.ComponentHud;

public class EntitySimulationHudTest
{
    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void constructor_createsNonNullInstance ()
    {
        EntitySimulationHud entity = new EntitySimulationHud ( 0, "SimulationHud", null );
        assertNotNull ( entity );
    }

    @Test
    void constructor_setsId ()
    {
        EntitySimulationHud entity = new EntitySimulationHud ( 18, "SimulationHud", null );
        assertEquals ( 18, entity.getId () );
    }

    @Test
    void constructor_setsName ()
    {
        EntitySimulationHud entity = new EntitySimulationHud ( 0, "HUD", null );
        assertEquals ( "HUD", entity.getName () );
    }


    // ========================================================================================
    // Component Count Tests
    // ========================================================================================

    @Test
    void entity_hasCorrectNumberOfComponents ()
    {
        EntitySimulationHud entity = new EntitySimulationHud ( 0, "SimulationHud", null );
        assertEquals ( 1, entity.getComponents ().size () );
    }


    // ========================================================================================
    // Component Presence Tests
    // ========================================================================================

    @Test
    void entity_hasComponentHud ()
    {
        EntitySimulationHud entity = new EntitySimulationHud ( 0, "SimulationHud", null );
        ECSComponent component = entity.getComponent ( Constants.COMPONENT_HUD );
        assertNotNull ( component );
        assertInstanceOf ( ComponentHud.class, component );
    }
}
