//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the CommandManager class. Validates command posting, FIFO execution order, flush and executeAll
//   semantics, queue clearing, and interleaved operation correctness.
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

import java.util.ArrayList;
import java.util.List;

//*********************************************************************************************************************
// Class: CommandManagerTest
//
// Description:
//
//   JUnit 5 test suite for the CommandManager class. Uses a TestCommand inner class as a test double to record
//   execution order and verify queue behavior.
//
//*********************************************************************************************************************

public class CommandManagerTest
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private CommandManager commandManager;
    private List <String> executionLog;

    //*****************************************************************************************************************
    // Class: TestCommand
    //
    // Description:
    //
    //   Test double implementing ICommand. Records its label into the enclosing test's executionLog upon execution,
    //   enabling verification of execution order and count.
    //
    //*****************************************************************************************************************

    private class TestCommand implements ICommand
    {
        //=============================================================================================================
        // Fields
        //=============================================================================================================

        private String label;

        //=============================================================================================================
        // Constructors
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Constructor 1/1: TestCommand
        //
        // Description:
        //
        //   Constructs a TestCommand with the specified label, which is appended to the execution log when the
        //   command is executed.
        //
        // Arguments:
        //
        //   label (String):
        //     An identifier recorded in the execution log when this command is executed.
        //
        //-------------------------------------------------------------------------------------------------------------

        public TestCommand ( String label )
        {
            this.label = label;
        }

        //=============================================================================================================
        // Methods
        //=============================================================================================================

        //-------------------------------------------------------------------------------------------------------------
        // Method: execute
        //
        // Description:
        //
        //   Appends this command's label to the enclosing test's execution log.
        //
        //-------------------------------------------------------------------------------------------------------------

        @Override
        public void execute ()
        {
            executionLog.add ( label );
        }
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setUp
    //
    // Description:
    //
    //   Initializes the CommandManager and execution log before each test. Logging is disabled to keep test output
    //   clean.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @BeforeEach
    void setUp ()
    {
        commandManager = new CommandManager ();
        commandManager.setLoggingEnabled ( false );
        executionLog = new ArrayList <> ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush_executesPostedCommand
    //
    // Description:
    //
    //   Verifies that a single posted command is executed when flush is called.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void flush_executesPostedCommand ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.flush ();
        assertEquals ( 1, executionLog.size () );
        assertEquals ( "A", executionLog.get ( 0 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush_executesInFIFOOrder
    //
    // Description:
    //
    //   Verifies that flush executes commands in FIFO order.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void flush_executesInFIFOOrder ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.postCommand ( new TestCommand ( "B" ) );
        commandManager.postCommand ( new TestCommand ( "C" ) );
        commandManager.flush ();
        assertEquals ( List.of ( "A", "B", "C" ), executionLog );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush_clearsQueueAfterExecution
    //
    // Description:
    //
    //   Verifies that the command queue is empty after flush completes, so a second flush produces no executions.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void flush_clearsQueueAfterExecution ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.flush ();
        executionLog.clear ();
        commandManager.flush ();
        assertTrue ( executionLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush_emptyQueue_doesNothing
    //
    // Description:
    //
    //   Verifies that flushing an empty queue produces no executions.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void flush_emptyQueue_doesNothing ()
    {
        commandManager.flush ();
        assertTrue ( executionLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: executeAll_executesAllWithoutRemoving
    //
    // Description:
    //
    //   Verifies that executeAll runs all queued commands without removing them, so a subsequent executeAll
    //   re-executes the same commands.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void executeAll_executesAllWithoutRemoving ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.postCommand ( new TestCommand ( "B" ) );
        commandManager.executeAll ();
        assertEquals ( 2, executionLog.size () );

        executionLog.clear ();
        commandManager.executeAll ();
        assertEquals ( 2, executionLog.size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: executeAll_emptyQueue_doesNothing
    //
    // Description:
    //
    //   Verifies that executeAll on an empty queue produces no executions.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void executeAll_emptyQueue_doesNothing ()
    {
        commandManager.executeAll ();
        assertTrue ( executionLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clear_discardsQueuedCommands
    //
    // Description:
    //
    //   Verifies that clear discards all queued commands, so a subsequent flush produces no executions.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clear_discardsQueuedCommands ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.postCommand ( new TestCommand ( "B" ) );
        commandManager.clear ();
        commandManager.flush ();
        assertTrue ( executionLog.isEmpty () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: interleavedPostAndFlush
    //
    // Description:
    //
    //   Verifies that interleaving post and flush operations produces the expected cumulative execution log.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void interleavedPostAndFlush ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.flush ();
        commandManager.postCommand ( new TestCommand ( "B" ) );
        commandManager.flush ();
        assertEquals ( List.of ( "A", "B" ), executionLog );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: multipleFlushesOnSameQueue
    //
    // Description:
    //
    //   Verifies that a second flush on an already-flushed queue does not re-execute commands.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void multipleFlushesOnSameQueue ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.flush ();
        commandManager.flush ();
        assertEquals ( 1, executionLog.size () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: largerBatch_maintainsFIFOOrder
    //
    // Description:
    //
    //   Verifies that a larger batch of 10 commands maintains FIFO ordering through flush.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void largerBatch_maintainsFIFOOrder ()
    {
        for ( int i = 0; i < 10; i++ )
        {
            commandManager.postCommand ( new TestCommand ( String.valueOf ( i ) ) );
        }
        commandManager.flush ();
        assertEquals ( 10, executionLog.size () );
        for ( int i = 0; i < 10; i++ )
        {
            assertEquals ( String.valueOf ( i ), executionLog.get ( i ) );
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: postAfterFlush_executesOnlyNewCommands
    //
    // Description:
    //
    //   Verifies that commands posted after a flush are the only ones executed on the next flush.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void postAfterFlush_executesOnlyNewCommands ()
    {
        commandManager.postCommand ( new TestCommand ( "A" ) );
        commandManager.flush ();
        executionLog.clear ();
        commandManager.postCommand ( new TestCommand ( "B" ) );
        commandManager.flush ();
        assertEquals ( 1, executionLog.size () );
        assertEquals ( "B", executionLog.get ( 0 ) );
    }
}
