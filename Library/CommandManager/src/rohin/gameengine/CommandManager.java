//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Defines the CommandManager class, which serves as a management container for ICommand objects. Commands are
//   enqueued via postCommand and executed either destructively (flush) or non-destructively (executeAll).
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.util.*;

//*********************************************************************************************************************
// Class: CommandManager
//
// Description:
//
//   Serves as a management container for ICommand objects.
//
//   Commands are posted to an internal FIFO queue and can be executed in two modes:
//   - flush: Executes and dequeues all commands.
//   - executeAll: Executes all commands without removing them from the queue.
//
//*********************************************************************************************************************

public class CommandManager
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private Queue <ICommand> commandQueue;
    private Boolean          loggingEnabled;
    private ConsoleLogger    logger;

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Mutator: setLoggingEnabled
    //
    // Description:
    //
    //   Enables or disables console logging for this command manager.
    //
    //   When the logging state changes, the internal ConsoleLogger instance is recreated with the new setting.
    //
    // Arguments:
    //
    //   loggingEnabled (Boolean):
    //     True to enable console logging, false to disable it.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void setLoggingEnabled ( Boolean loggingEnabled )
    {
        this.loggingEnabled = loggingEnabled;
        this.logger         = new ConsoleLogger ( this, this.loggingEnabled );
    }

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: CommandManager
    //
    // Description:
    //
    //   Default constructor. Initializes the command queue as an empty linked list and enables console logging by
    //   default.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public CommandManager ()
    {
        this.commandQueue   = new LinkedList < ICommand > ();
        this.loggingEnabled = true;
        this.logger         = new ConsoleLogger ( this, this.loggingEnabled );
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clear
    //
    // Description:
    //
    //   Clears the command queue, discarding all pending commands without executing them.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void clear ()
    {
        this.commandQueue.clear ();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: postCommand
    //
    // Description:
    //
    //   Posts a command to the command queue for later execution.
    //
    //   - The command is appended to the end of the FIFO queue.
    //
    //   - If logging is enabled, the command's class name is logged to the console.
    //
    // Arguments:
    //
    //   command (ICommand):
    //     The ICommand object to enqueue.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void postCommand ( ICommand command )
    {
        this.commandQueue.add ( command );

        // Console Logger.

        logger.log ( command.getClass ().getSimpleName () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: flush
    //
    // Description:
    //
    //   Executes and dequeues all commands in the queue.
    //
    //   - Commands are executed in FIFO order.
    //
    //   - After this method returns, the queue is empty.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void flush ()
    {
        while ( this.commandQueue.size () > 0 )
        {
            ICommand command = this.commandQueue.remove ();
            command.execute ();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: executeAll
    //
    // Description:
    //
    //   Executes all commands in the queue without removing them.
    //
    //   Commands remain in the queue after execution, allowing them to be executed again on subsequent calls.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void executeAll ()
    {
        for ( ICommand command : this.commandQueue )
        {
            command.execute ();
        }
    }

}
