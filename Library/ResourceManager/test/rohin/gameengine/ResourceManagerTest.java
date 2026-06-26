package rohin.gameengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceManagerTest
{
    private ResourceManager manager;


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
            setObject ( "loaded:" + getFileName () );
        }

        @Override
        public void unload ()
        {
            unloaded = true;
            setObject ( null );
        }
    }


    // ========================================================================================
    // Setup
    // ========================================================================================

    @BeforeEach
    void setUp ()
    {
        manager = new ResourceManager ();
    }


    // ========================================================================================
    // Add / Get Tests
    // ========================================================================================

    @Test
    void add_and_get_returnsResource ()
    {
        Resource r = new Resource ( "test.png" );
        manager.add ( 1, r );
        assertSame ( r, manager.get ( 1 ) );
    }

    @Test
    void get_nonExistentId_returnsNull ()
    {
        assertNull ( manager.get ( 999 ) );
    }

    @Test
    void add_sameId_overwritesPreviousResource ()
    {
        Resource r1 = new Resource ( "first.png" );
        Resource r2 = new Resource ( "second.png" );
        manager.add ( 1, r1 );
        manager.add ( 1, r2 );
        assertSame ( r2, manager.get ( 1 ) );
    }

    @Test
    void add_multipleResources ()
    {
        Resource r1 = new Resource ( "a.png" );
        Resource r2 = new Resource ( "b.png" );
        Resource r3 = new Resource ( "c.png" );
        manager.add ( 1, r1 );
        manager.add ( 2, r2 );
        manager.add ( 3, r3 );
        assertSame ( r1, manager.get ( 1 ) );
        assertSame ( r2, manager.get ( 2 ) );
        assertSame ( r3, manager.get ( 3 ) );
    }


    // ========================================================================================
    // Load Tests
    // ========================================================================================

    @Test
    void load_callsResourceLoad ()
    {
        TestResource r = new TestResource ( "sprite.png" );
        manager.add ( 1, r );
        manager.load ( 1 );
        assertTrue ( r.loaded );
    }

    @Test
    void load_withFileName_overridesAndLoads ()
    {
        TestResource r = new TestResource ( "original.png" );
        manager.add ( 1, r );
        manager.load ( 1, "override.png" );
        assertTrue ( r.loaded );
        assertEquals ( "override.png", r.getFileName () );
    }

    @Test
    void loadAll_loadsAllResources ()
    {
        TestResource r1 = new TestResource ( "a.png" );
        TestResource r2 = new TestResource ( "b.png" );
        manager.add ( 1, r1 );
        manager.add ( 2, r2 );
        manager.loadAll ();
        assertTrue ( r1.loaded );
        assertTrue ( r2.loaded );
    }


    // ========================================================================================
    // Unload Tests
    // ========================================================================================

    @Test
    void unload_callsResourceUnload ()
    {
        TestResource r = new TestResource ( "sprite.png" );
        manager.add ( 1, r );
        manager.load ( 1 );
        manager.unload ( 1 );
        assertTrue ( r.unloaded );
    }

    @Test
    void unloadAll_unloadsAllResources ()
    {
        TestResource r1 = new TestResource ( "a.png" );
        TestResource r2 = new TestResource ( "b.png" );
        manager.add ( 1, r1 );
        manager.add ( 2, r2 );
        manager.loadAll ();
        manager.unloadAll ();
        assertTrue ( r1.unloaded );
        assertTrue ( r2.unloaded );
    }


    // ========================================================================================
    // GetResources Collection Tests
    // ========================================================================================

    @Test
    void getResources_returnsAllValues ()
    {
        manager.add ( 1, new Resource ( "a.png" ) );
        manager.add ( 2, new Resource ( "b.png" ) );
        assertEquals ( 2, manager.getResources ().size () );
    }

    @Test
    void getResources_emptyManager_returnsEmptyCollection ()
    {
        assertTrue ( manager.getResources ().isEmpty () );
    }
}
