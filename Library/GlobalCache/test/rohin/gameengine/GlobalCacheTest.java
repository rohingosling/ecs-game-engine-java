//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the GlobalCache class. Verifies the correctness of the key-value store operations including
//   construction, storage and retrieval of various types, key existence checks, entry removal, cache clearing,
//   and value overwrite behavior.
//
// TODO:
//
//   1. Add tests for null key handling.
//   2. Add tests for concurrent access scenarios once thread-safety is implemented.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: GlobalCacheTest
//
// Description:
//
//   JUnit 5 test suite for the GlobalCache class. Each test method follows the methodUnderTest_scenario_expected
//   naming convention and exercises a single aspect of the GlobalCache API. Tests are organized into logical
//   sections corresponding to the public methods of the class under test.
//
//*********************************************************************************************************************

public class GlobalCacheTest
{
    //=================================================================================================================
    // Constructor Tests
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: constructor_newCacheIsEmpty
    //
    // Description:
    //
    //   Verifies that a newly constructed GlobalCache instance contains no entries. A contains check on an
    //   arbitrary key should return false.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void constructor_newCacheIsEmpty ()
    {
        GlobalCache cache = new GlobalCache ();
        assertFalse ( cache.contains ( "anyKey" ) );
    }

    //=================================================================================================================
    // put / get Tests
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: putGet_storeAndRetrieveString
    //
    // Description:
    //
    //   Verifies that a String value stored via put can be retrieved via get using the same key.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void putGet_storeAndRetrieveString ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "name", "player1" );
        assertEquals ( "player1", cache.get ( "name" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: putGet_storeAndRetrieveInteger
    //
    // Description:
    //
    //   Verifies that an Integer value stored via put can be retrieved via get using the same key. The integer
    //   literal is auto-boxed to an Integer object.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void putGet_storeAndRetrieveInteger ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "score", 42 );
        assertEquals ( 42, cache.get ( "score" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: putGet_storeAndRetrieveDouble
    //
    // Description:
    //
    //   Verifies that a Double value stored via put can be retrieved via get using the same key. The double
    //   literal is auto-boxed to a Double object.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void putGet_storeAndRetrieveDouble ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "speed", 3.14 );
        assertEquals ( 3.14, cache.get ( "speed" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: putGet_storeAndRetrieveNull
    //
    // Description:
    //
    //   Verifies that a null value can be stored and retrieved from the cache. Additionally confirms that the
    //   contains method returns true for a key that maps to null, distinguishing it from a missing key.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void putGet_storeAndRetrieveNull ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "nothing", null );
        assertNull ( cache.get ( "nothing" ) );
        assertTrue ( cache.contains ( "nothing" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: get_nonexistentKey_returnsNull
    //
    // Description:
    //
    //   Verifies that calling get with a key that has never been stored returns null.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void get_nonexistentKey_returnsNull ()
    {
        GlobalCache cache = new GlobalCache ();
        assertNull ( cache.get ( "missing" ) );
    }

    //=================================================================================================================
    // contains Tests
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: contains_existingKey_returnsTrue
    //
    // Description:
    //
    //   Verifies that the contains method returns true for a key that has been stored in the cache.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void contains_existingKey_returnsTrue ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "key", "value" );
        assertTrue ( cache.contains ( "key" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: contains_missingKey_returnsFalse
    //
    // Description:
    //
    //   Verifies that the contains method returns false for a key that has never been stored in the cache.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void contains_missingKey_returnsFalse ()
    {
        GlobalCache cache = new GlobalCache ();
        assertFalse ( cache.contains ( "absent" ) );
    }

    //=================================================================================================================
    // remove Tests
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: remove_removesEntry
    //
    // Description:
    //
    //   Verifies that calling remove on a stored key deletes the entry, so that a subsequent contains check
    //   returns false.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void remove_removesEntry ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "key", "value" );
        cache.remove ( "key" );
        assertFalse ( cache.contains ( "key" ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: remove_containsReturnsFalseAfter
    //
    // Description:
    //
    //   Verifies that removing one key from a cache with multiple entries only affects the targeted key. Other
    //   entries remain intact and accessible.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void remove_containsReturnsFalseAfter ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "a", 1 );
        cache.put ( "b", 2 );
        cache.remove ( "a" );
        assertFalse ( cache.contains ( "a" ) );
        assertTrue ( cache.contains ( "b" ) );
    }

    //=================================================================================================================
    // clear Tests
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clear_removesAllEntries
    //
    // Description:
    //
    //   Verifies that calling clear removes all entries from the cache. After clearing, contains returns false
    //   for every key that was previously stored.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clear_removesAllEntries ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "a", 1 );
        cache.put ( "b", 2 );
        cache.put ( "c", 3 );
        cache.clear ();
        assertFalse ( cache.contains ( "a" ) );
        assertFalse ( cache.contains ( "b" ) );
        assertFalse ( cache.contains ( "c" ) );
    }

    //=================================================================================================================
    // Overwrite Tests
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: put_sameKeyTwice_getReturnsLatest
    //
    // Description:
    //
    //   Verifies that storing a second value under the same key overwrites the first value. A subsequent get
    //   call returns the most recently stored value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void put_sameKeyTwice_getReturnsLatest ()
    {
        GlobalCache cache = new GlobalCache ();
        cache.put ( "key", "first" );
        cache.put ( "key", "second" );
        assertEquals ( "second", cache.get ( "key" ) );
    }
}
