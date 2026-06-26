//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the ECSEntity class. Validates constructor overloads, component management (add, get, has),
//   enabled state behavior, and component type matching.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: ECSEntityTest
//
// Description:
//
//   JUnit 5 test suite for the ECSEntity class. Uses inner component subclasses (ComponentA, ComponentB,
//   ComponentC) as test doubles to verify component storage, retrieval, type matching via hasComponents, and
//   enabled state interactions.
//
//*********************************************************************************************************************

public class ECSEntityTest
{
    //*****************************************************************************************************************
    // Class: ComponentA
    //
    // Description:
    //
    //   Test double component subclass used for type-matching verification in hasComponents tests.
    //
    //*****************************************************************************************************************

    private static class ComponentA extends ECSComponent
    {
        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/2: ComponentA
        //
        // Description:
        //
        //   Default constructor. Delegates to the parent ECSComponent default constructor.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentA ()          { super (); }

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 2/2: ComponentA
        //
        // Description:
        //
        //   Constructs a ComponentA with the specified identifier.
        //
        // Arguments:
        //
        //   id (int):
        //     The unique identifier for this component.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentA ( int id )  { super ( id ); }
    }

    //*****************************************************************************************************************
    // Class: ComponentB
    //
    // Description:
    //
    //   Test double component subclass used for multi-type matching verification in hasComponents tests.
    //
    //*****************************************************************************************************************

    private static class ComponentB extends ECSComponent
    {
        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/2: ComponentB
        //
        // Description:
        //
        //   Default constructor. Delegates to the parent ECSComponent default constructor.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentB ()          { super (); }

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 2/2: ComponentB
        //
        // Description:
        //
        //   Constructs a ComponentB with the specified identifier.
        //
        // Arguments:
        //
        //   id (int):
        //     The unique identifier for this component.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentB ( int id )  { super ( id ); }
    }

    //*****************************************************************************************************************
    // Class: ComponentC
    //
    // Description:
    //
    //   Test double component subclass used for verifying partial and superset matching in hasComponents tests.
    //
    //*****************************************************************************************************************

    private static class ComponentC extends ECSComponent
    {
        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/2: ComponentC
        //
        // Description:
        //
        //   Default constructor. Delegates to the parent ECSComponent default constructor.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentC ()          { super (); }

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 2/2: ComponentC
        //
        // Description:
        //
        //   Constructs a ComponentC with the specified identifier.
        //
        // Arguments:
        //
        //   id (int):
        //     The unique identifier for this component.
        //
        //-------------------------------------------------------------------------------------------------------------

        public ComponentC ( int id )  { super ( id ); }
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: defaultConstructor_setsDefaults
    //
    // Description:
    //
    //   Verifies that the default constructor initializes id to 0, name to "ENTITY", enabled to true, and an empty
    //   component map.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultConstructor_setsDefaults ()
    {
        ECSEntity e = new ECSEntity ();
        assertEquals ( 0, e.getId () );
        assertEquals ( "ENTITY", e.getName () );
        assertTrue ( e.isEnabled () );
        assertNotNull ( e.getComponents () );
        assertTrue ( e.getComponents ().isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: idConstructor_setsId
    //
    // Description:
    //
    //   Verifies that the id-only constructor sets the entity identifier.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void idConstructor_setsId ()
    {
        ECSEntity e = new ECSEntity ( 5 );
        assertEquals ( 5, e.getId () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: nameConstructor_setsName
    //
    // Description:
    //
    //   Verifies that the name-only constructor sets the entity name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void nameConstructor_setsName ()
    {
        ECSEntity e = new ECSEntity ( "Player" );
        assertEquals ( "Player", e.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: idNameConstructor_setsBoth
    //
    // Description:
    //
    //   Verifies that the (id, name) constructor sets both the identifier and name.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void idNameConstructor_setsBoth ()
    {
        ECSEntity e = new ECSEntity ( 3, "Enemy" );
        assertEquals ( 3, e.getId () );
        assertEquals ( "Enemy", e.getName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addComponent_and_getComponent
    //
    // Description:
    //
    //   Verifies that a component added with addComponent can be retrieved by key with getComponent.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addComponent_and_getComponent ()
    {
        ECSEntity e = new ECSEntity ();
        ComponentA comp = new ComponentA ( 1 );
        e.addComponent ( 1, comp );
        assertSame ( comp, e.getComponent ( 1 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getComponent_nonExistentKey_returnsNull
    //
    // Description:
    //
    //   Verifies that getComponent returns null for a key with no associated component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void getComponent_nonExistentKey_returnsNull ()
    {
        ECSEntity e = new ECSEntity ();
        assertNull ( e.getComponent ( 999 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addComponent_multipleComponents
    //
    // Description:
    //
    //   Verifies that multiple components can be added and retrieved independently by key.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addComponent_multipleComponents ()
    {
        ECSEntity e = new ECSEntity ();
        ComponentA a = new ComponentA ( 1 );
        ComponentB b = new ComponentB ( 2 );
        e.addComponent ( 1, a );
        e.addComponent ( 2, b );
        assertSame ( a, e.getComponent ( 1 ) );
        assertSame ( b, e.getComponent ( 2 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addComponent_sameKey_overwritesPrevious
    //
    // Description:
    //
    //   Verifies that adding a component with an existing key overwrites the previous component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addComponent_sameKey_overwritesPrevious ()
    {
        ECSEntity e = new ECSEntity ();
        ComponentA a1 = new ComponentA ( 1 );
        ComponentA a2 = new ComponentA ( 1 );
        e.addComponent ( 1, a1 );
        e.addComponent ( 1, a2 );
        assertSame ( a2, e.getComponent ( 1 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getComponents_returnsMap
    //
    // Description:
    //
    //   Verifies that getComponents returns a map containing all added components.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void getComponents_returnsMap ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        e.addComponent ( 2, new ComponentB ( 2 ) );
        assertEquals ( 2, e.getComponents ().size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_singleMatch_returnsTrue
    //
    // Description:
    //
    //   Verifies that hasComponents returns true when the entity contains a component of the queried type.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_singleMatch_returnsTrue ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        assertTrue ( e.hasComponents ( new ComponentA () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_singleNoMatch_returnsFalse
    //
    // Description:
    //
    //   Verifies that hasComponents returns false when the entity does not contain the queried component type.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_singleNoMatch_returnsFalse ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        assertFalse ( e.hasComponents ( new ComponentB () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_multipleMatch_returnsTrue
    //
    // Description:
    //
    //   Verifies that hasComponents returns true when the entity contains all queried component types.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_multipleMatch_returnsTrue ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        e.addComponent ( 2, new ComponentB ( 2 ) );
        assertTrue ( e.hasComponents ( new ComponentA (), new ComponentB () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_partialMatch_returnsFalse
    //
    // Description:
    //
    //   Verifies that hasComponents returns false when only some of the queried component types are present.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_partialMatch_returnsFalse ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        assertFalse ( e.hasComponents ( new ComponentA (), new ComponentB () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_emptyEntity_returnsFalse
    //
    // Description:
    //
    //   Verifies that hasComponents returns false on an entity with no components.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_emptyEntity_returnsFalse ()
    {
        ECSEntity e = new ECSEntity ();
        assertFalse ( e.hasComponents ( new ComponentA () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_noArgs_returnsTrue
    //
    // Description:
    //
    //   Verifies that hasComponents with no arguments returns true (vacuous truth).
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_noArgs_returnsTrue ()
    {
        ECSEntity e = new ECSEntity ();
        assertTrue ( e.hasComponents () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_usesGetClassComparison
    //
    // Description:
    //
    //   Verifies that hasComponents uses getClass comparison, matching instances of the same concrete type.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_usesGetClassComparison ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        ComponentA typeToken = new ComponentA ();
        assertTrue ( e.hasComponents ( typeToken ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_entityHasExtraComponents_stillMatches
    //
    // Description:
    //
    //   Verifies that hasComponents returns true when the entity has more components than queried (superset match).
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_entityHasExtraComponents_stillMatches ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        e.addComponent ( 2, new ComponentB ( 2 ) );
        e.addComponent ( 3, new ComponentC ( 3 ) );
        assertTrue ( e.hasComponents ( new ComponentA (), new ComponentC () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: enabled_defaultTrue
    //
    // Description:
    //
    //   Verifies that a newly constructed entity is enabled by default.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void enabled_defaultTrue ()
    {
        ECSEntity e = new ECSEntity ();
        assertTrue ( e.isEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setEnabled_false_disablesEntity
    //
    // Description:
    //
    //   Verifies that setEnabled(false) disables the entity.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setEnabled_false_disablesEntity ()
    {
        ECSEntity e = new ECSEntity ();
        e.setEnabled ( false );
        assertFalse ( e.isEnabled () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_disabledEntity_returnsFalse
    //
    // Description:
    //
    //   Verifies that hasComponents returns false when the entity is disabled, even if the component exists.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_disabledEntity_returnsFalse ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        e.setEnabled ( false );
        assertFalse ( e.hasComponents ( new ComponentA () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hasComponents_reenabledEntity_returnsTrue
    //
    // Description:
    //
    //   Verifies that hasComponents returns true again after a disabled entity is re-enabled.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hasComponents_reenabledEntity_returnsTrue ()
    {
        ECSEntity e = new ECSEntity ();
        e.addComponent ( 1, new ComponentA ( 1 ) );
        e.setEnabled ( false );
        e.setEnabled ( true );
        assertTrue ( e.hasComponents ( new ComponentA () ) );
    }
}
