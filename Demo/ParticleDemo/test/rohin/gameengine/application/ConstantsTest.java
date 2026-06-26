package rohin.gameengine.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ConstantsTest
{
    // ========================================================================================
    // Helper Methods
    // ========================================================================================

    /**
     * Collects all public static final Integer or int fields whose names start
     * with the given prefix.
     */
    private List <Integer> collectIntConstants ( String prefix ) throws IllegalAccessException
    {
        List <Integer> values = new ArrayList <> ();

        for ( Field field : Constants.class.getDeclaredFields () )
        {
            int modifiers = field.getModifiers ();

            if ( Modifier.isPublic ( modifiers )
                    && Modifier.isStatic ( modifiers )
                    && Modifier.isFinal ( modifiers )
                    && field.getName ().startsWith ( prefix )
                    && ( field.getType () == int.class || field.getType () == Integer.class ) )
            {
                values.add ( ( (Number) field.get ( null ) ).intValue () );
            }
        }

        return values;
    }


    // ========================================================================================
    // Component ID Uniqueness Tests
    // ========================================================================================

    @Test
    void componentIds_areAllUnique () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "COMPONENT_" );
        HashSet <Integer> unique = new HashSet <> ( values );

        assertFalse ( values.isEmpty (), "Expected at least one COMPONENT_ constant" );
        assertEquals ( values.size (), unique.size (),
                "Duplicate component IDs detected" );
    }

    @Test
    void componentIds_areAllNonNegative () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "COMPONENT_" );

        for ( Integer value : values )
        {
            assertTrue ( value >= 0, "Component ID should be non-negative but was: " + value );
        }
    }


    // ========================================================================================
    // Entity ID Uniqueness Tests
    // ========================================================================================

    @Test
    void entityIds_areAllUnique () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "ENTITY_" );
        HashSet <Integer> unique = new HashSet <> ( values );

        assertFalse ( values.isEmpty (), "Expected at least one ENTITY_ constant" );
        assertEquals ( values.size (), unique.size (),
                "Duplicate entity IDs detected" );
    }

    @Test
    void entityIds_areAllNonNegative () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "ENTITY_" );

        for ( Integer value : values )
        {
            assertTrue ( value >= 0, "Entity ID should be non-negative but was: " + value );
        }
    }


    // ========================================================================================
    // System ID Uniqueness Tests
    // ========================================================================================

    @Test
    void menuSystemIds_areAllUnique () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "SYSTEM_MENU_" );
        HashSet <Integer> unique = new HashSet <> ( values );

        assertFalse ( values.isEmpty (), "Expected at least one SYSTEM_MENU_ constant" );
        assertEquals ( values.size (), unique.size (),
                "Duplicate menu system IDs detected" );
    }

    @Test
    void simulationSystemIds_areAllUnique () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "SYSTEM_SIMULATION_" );
        HashSet <Integer> unique = new HashSet <> ( values );

        assertFalse ( values.isEmpty (), "Expected at least one SYSTEM_SIMULATION_ constant" );
        assertEquals ( values.size (), unique.size (),
                "Duplicate simulation system IDs detected" );
    }

    @Test
    void systemIds_areAllNonNegative () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "SYSTEM_" );

        for ( Integer value : values )
        {
            assertTrue ( value >= 0, "System ID should be non-negative but was: " + value );
        }
    }


    // ========================================================================================
    // Application State Uniqueness Tests
    // ========================================================================================

    @Test
    void applicationStateIds_areAllUnique () throws IllegalAccessException
    {
        // Exclude APPLICATION_STATE_INITIAL which is an intentional alias for APPLICATION_STATE_IDLE.

        List <Integer> values = new ArrayList <> ();

        for ( Field field : Constants.class.getDeclaredFields () )
        {
            int modifiers = field.getModifiers ();

            if ( Modifier.isPublic ( modifiers )
                    && Modifier.isStatic ( modifiers )
                    && Modifier.isFinal ( modifiers )
                    && field.getName ().startsWith ( "APPLICATION_STATE_" )
                    && !field.getName ().equals ( "APPLICATION_STATE_INITIAL" )
                    && ( field.getType () == int.class || field.getType () == Integer.class ) )
            {
                values.add ( ( (Number) field.get ( null ) ).intValue () );
            }
        }

        HashSet <Integer> unique = new HashSet <> ( values );

        assertFalse ( values.isEmpty (), "Expected at least one APPLICATION_STATE_ constant" );
        assertEquals ( values.size (), unique.size (),
                "Duplicate application state IDs detected" );
    }

    @Test
    void applicationStateIds_areAllNonNegative () throws IllegalAccessException
    {
        List <Integer> values = collectIntConstants ( "APPLICATION_STATE_" );

        for ( Integer value : values )
        {
            assertTrue ( value >= 0, "Application state ID should be non-negative but was: " + value );
        }
    }
}
