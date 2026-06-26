package rohin.gameengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest
{
    // ========================================================================================
    // Test Double
    // ========================================================================================

    private static class TestResource extends Resource
    {
        public boolean loaded   = false;
        public boolean unloaded = false;

        public TestResource ( String fileName )
        {
            super ( fileName );
        }

        @Override
        public void load ()
        {
            loaded = true;
            setObject ( "loaded-data" );
        }

        @Override
        public void unload ()
        {
            unloaded = true;
            setObject ( null );
        }
    }


    // ========================================================================================
    // Constructor Tests
    // ========================================================================================

    @Test
    void defaultConstructor_fieldsAreNull ()
    {
        Resource r = new Resource ();
        assertNull ( r.getFileName () );
        assertNull ( r.getObject () );
    }

    @Test
    void fileNameConstructor_setsFileName ()
    {
        Resource r = new Resource ( "test.png" );
        assertEquals ( "test.png", r.getFileName () );
        assertNull ( r.getObject () );
    }

    @Test
    void fullConstructor_setsBothFields ()
    {
        Object obj = new Object ();
        Resource r = new Resource ( "test.png", obj );
        assertEquals ( "test.png", r.getFileName () );
        assertSame ( obj, r.getObject () );
    }


    // ========================================================================================
    // Accessor / Mutator Tests
    // ========================================================================================

    @Test
    void setFileName_updatesFileName ()
    {
        Resource r = new Resource ();
        r.setFileName ( "updated.png" );
        assertEquals ( "updated.png", r.getFileName () );
    }

    @Test
    void setObject_updatesObject ()
    {
        Resource r = new Resource ();
        Object obj = "myObject";
        r.setObject ( obj );
        assertSame ( obj, r.getObject () );
    }


    // ========================================================================================
    // Base Class Load/Unload No-Op Tests
    // ========================================================================================

    @Test
    void baseLoad_doesNotThrow ()
    {
        Resource r = new Resource ( "test.png" );
        assertDoesNotThrow ( () -> r.load () );
    }

    @Test
    void baseUnload_doesNotThrow ()
    {
        Resource r = new Resource ( "test.png" );
        assertDoesNotThrow ( () -> r.unload () );
    }


    // ========================================================================================
    // Subclass Lifecycle Tests
    // ========================================================================================

    @Test
    void subclassLoad_setsLoadedFlag ()
    {
        TestResource r = new TestResource ( "sprite.png" );
        r.load ();
        assertTrue ( r.loaded );
        assertEquals ( "loaded-data", r.getObject () );
    }

    @Test
    void subclassUnload_setsUnloadedFlag ()
    {
        TestResource r = new TestResource ( "sprite.png" );
        r.load ();
        r.unload ();
        assertTrue ( r.unloaded );
        assertNull ( r.getObject () );
    }

    @Test
    void subclassLoadUnloadCycle ()
    {
        TestResource r = new TestResource ( "sprite.png" );
        assertFalse ( r.loaded );
        r.load ();
        assertTrue ( r.loaded );
        assertNotNull ( r.getObject () );
        r.unload ();
        assertTrue ( r.unloaded );
        assertNull ( r.getObject () );
    }
}
