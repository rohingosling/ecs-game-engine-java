//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the ECSComponent class. Validates constructor overloads, default field values, and subclass field
//   mutability.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: ECSComponentTest
//
// Description:
//
//   JUnit 5 test suite for the ECSComponent class. Exercises all constructor overloads, verifies default values,
//   and tests that subclass public fields are accessible and mutable.
//
//*********************************************************************************************************************

public class ECSComponentTest
{
    //*****************************************************************************************************************
    // Class: TestComponent
    //
    // Description:
    //
    //   Test double extending ECSComponent with public fields for health and label, used to verify that component
    //   subclasses can expose domain-specific data through public fields.
    //
    //*****************************************************************************************************************

    private static class TestComponent extends ECSComponent
    {
        //=============================================================================================================
        // Fields
        //=============================================================================================================

        public int    health;
        public String label;

        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/1: TestComponent
        //
        // Description:
        //
        //   Constructs a TestComponent with the specified id, health, and label.
        //
        // Arguments:
        //
        //   id (Integer):
        //     The unique identifier for this component.
        //
        //   health (int):
        //     The initial health value.
        //
        //   label (String):
        //     A descriptive label for this component.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestComponent ( Integer id, int health, String label )
        {
            super ( id );
            this.health = health;
            this.label  = label;
        }
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: defaultConstructor_setsDefaults
    //
    // Description:
    //
    //   Verifies that the default constructor initializes id to 0, name to "COMPONENT", family to 0, and owner to
    //   null.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultConstructor_setsDefaults ()
    {
        ECSComponent c = new ECSComponent ();
        assertEquals ( 0, c.getId () );
        assertEquals ( "COMPONENT", c.getName () );
        assertEquals ( 0, c.getFamily () );
        assertNull ( c.getOwner () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: idConstructor_setsId
    //
    // Description:
    //
    //   Verifies that the id-only constructor sets the id and retains the default name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void idConstructor_setsId ()
    {
        ECSComponent c = new ECSComponent ( 5 );
        assertEquals ( 5, c.getId () );
        assertEquals ( "COMPONENT", c.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: idNameConstructor_setsIdAndName
    //
    // Description:
    //
    //   Verifies that the (id, name) constructor sets both the id and name fields.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void idNameConstructor_setsIdAndName ()
    {
        ECSComponent c = new ECSComponent ( 3, "Health" );
        assertEquals ( 3, c.getId () );
        assertEquals ( "Health", c.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: idFamilyConstructor_setsIdAndFamily
    //
    // Description:
    //
    //   Verifies that the (id, family) constructor sets both the id and family fields.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void idFamilyConstructor_setsIdAndFamily ()
    {
        ECSComponent c = new ECSComponent ( 3, (Integer) 2 );
        assertEquals ( 3, c.getId () );
        assertEquals ( 2, c.getFamily () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: fullConstructor_setsAllFields
    //
    // Description:
    //
    //   Verifies that the full constructor sets id, name, and family.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void fullConstructor_setsAllFields ()
    {
        ECSComponent c = new ECSComponent ( 1, "Velocity", 2, null );
        assertEquals ( 1, c.getId () );
        assertEquals ( "Velocity", c.getName () );
        assertEquals ( 2, c.getFamily () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subclass_hasPublicFields
    //
    // Description:
    //
    //   Verifies that a TestComponent subclass exposes its public fields with the values provided at construction.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void subclass_hasPublicFields ()
    {
        TestComponent tc = new TestComponent ( 1, 100, "Player" );
        assertEquals ( 100, tc.health );
        assertEquals ( "Player", tc.label );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subclass_fieldsAreMutable
    //
    // Description:
    //
    //   Verifies that a TestComponent subclass's public fields can be mutated after construction.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void subclass_fieldsAreMutable ()
    {
        TestComponent tc = new TestComponent ( 1, 100, "Player" );
        tc.health = 50;
        tc.label = "Enemy";
        assertEquals ( 50, tc.health );
        assertEquals ( "Enemy", tc.label );
    }
}
