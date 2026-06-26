//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the ECSObject class. Validates accessor and mutator behavior for id, name, family, and owner
//   fields, as well as the toString formatting.
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
// Class: ECSObjectTest
//
// Description:
//
//   JUnit 5 test suite for the ECSObject class. Tests accessor/mutator pairs for all fields and verifies the
//   toString output format.
//
//*********************************************************************************************************************

public class ECSObjectTest
{
    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setId_and_getId
    //
    // Description:
    //
    //   Verifies that setId and getId correctly store and retrieve the object's identifier.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setId_and_getId ()
    {
        ECSObject obj = new ECSObject ();
        obj.setId ( 42 );
        assertEquals ( 42, obj.getId () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setName_and_getName
    //
    // Description:
    //
    //   Verifies that setName and getName correctly store and retrieve the object's name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setName_and_getName ()
    {
        ECSObject obj = new ECSObject ();
        obj.setName ( "TestObject" );
        assertEquals ( "TestObject", obj.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setFamily_and_getFamily
    //
    // Description:
    //
    //   Verifies that setFamily and getFamily correctly store and retrieve the object's family identifier.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setFamily_and_getFamily ()
    {
        ECSObject obj = new ECSObject ();
        obj.setFamily ( 7 );
        assertEquals ( 7, obj.getFamily () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setOwner_and_getOwner
    //
    // Description:
    //
    //   Verifies that setOwner and getOwner correctly store and retrieve an owner reference using identity equality.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setOwner_and_getOwner ()
    {
        ECSObject obj = new ECSObject ();
        Object owner = "OwnerRef";
        obj.setOwner ( owner );
        assertSame ( owner, obj.getOwner () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: toString_format
    //
    // Description:
    //
    //   Verifies that toString produces the expected "[family.id] name" format with custom values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void toString_format ()
    {
        ECSObject obj = new ECSObject ();
        obj.setId ( 5 );
        obj.setFamily ( 2 );
        obj.setName ( "Player" );
        assertEquals ( "[2.5] Player", obj.toString () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: toString_defaultValues
    //
    // Description:
    //
    //   Verifies that toString produces the expected "[0.0] Default" format with default id and family values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void toString_defaultValues ()
    {
        ECSObject obj = new ECSObject ();
        obj.setId ( 0 );
        obj.setFamily ( 0 );
        obj.setName ( "Default" );
        assertEquals ( "[0.0] Default", obj.toString () );
    }
}
