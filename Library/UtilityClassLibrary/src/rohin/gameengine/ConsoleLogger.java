//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   The ConsoleLogger class offers a suite of features to track program execution via the Java console.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import java.text.SimpleDateFormat;
import java.util.Date;

//*********************************************************************************************************************
// Class: ConsoleLogger
//
// Description:
//
//   The ConsoleLogger class offers a suite of features to track program execution via the Java console. It provides
//   timestamped log entries with caller identification derived from stack trace introspection, allowing developers to
//   trace program flow during development and debugging.
//
//*********************************************************************************************************************

public class ConsoleLogger
{
     @ SuppressWarnings ( "unused" )

     //================================================================================================================
     // Constants
     //================================================================================================================

     private static final String  M_FORMAT_DATE              = "yyyy-MM-dd";
     private static final String  M_FORMAT_TIME              = "HH:mm:ss";
     private static final String  M_FORMAT_MS                = ".SSS";
     private static final String  M_FORMAT_EMPTY             = "";
     private static final String  M_FORMAT_NEW_LINE          = "\n";
     private static final String  M_FORMAT_SPACE             = " ";
     private static final String  M_FORMAT_TAB               = "\t";
     private static final String  M_FORMAT_MEMBER_DELIMITER  = ".";
     private static final String  M_FORMAT_ARGUMENT_TEMPLATE = "";
     private static final int     M_STACK_TRACE_CALL_DEPTH   = 4;        // 4:MothodOfInterst > 3:Log > 2:LogDepth > 1:Format > 0:callStackTrace.

     @ SuppressWarnings ( "rawtypes" )

     //================================================================================================================
     // Fields
     //================================================================================================================

     private Class   owner;
     private Boolean loggingEnabled;

     //================================================================================================================
     // Constructors
     //================================================================================================================

     public ConsoleLogger ()                                       { initialize ( null, true ); }
     public ConsoleLogger ( Object owner )                         { initialize ( owner, true ); }
     public ConsoleLogger ( Boolean loggingEnabled )               { initialize ( null, loggingEnabled ); }
     public ConsoleLogger ( Object owner, Boolean loggingEnabled ) { initialize ( owner, loggingEnabled ); }

     //================================================================================================================
     // Methods
     //================================================================================================================

     //----------------------------------------------------------------------------------------------------------------
     // Method: initialize
     //
     // Description:
     //
     //   Initializes the console logger by setting the owner class and logging enabled state. This method is called
     //   by all constructors to centralize initialization logic.
     //
     // Arguments:
     //
     //   owner (Object):
     //     The object that owns this logger instance. The owner's class is extracted and stored for use in log
     //     entry identification.
     //
     //   loggingEnabled (Boolean):
     //     Flag to enable or disable logging. When set to false, all calls to log will be suppressed.
     //
     //----------------------------------------------------------------------------------------------------------------

     private void initialize ( Object owner, Boolean loggingEnabled )
     {
         this.owner          = owner.getClass ();
         this.loggingEnabled = loggingEnabled;
     }

     //----------------------------------------------------------------------------------------------------------------
     // Method: log
     //
     // Description:
     //
     //   Logs an empty message to the console. The log entry includes a timestamp and caller identification derived
     //   from the call stack.
     //
     //----------------------------------------------------------------------------------------------------------------

     public void log ()
     {
         logDepth ( M_FORMAT_EMPTY, M_STACK_TRACE_CALL_DEPTH );
     }

     //----------------------------------------------------------------------------------------------------------------
     // Method: log
     //
     // Description:
     //
     //   Logs the specified message to the console. The log entry includes a timestamp and caller identification
     //   derived from the call stack.
     //
     // Arguments:
     //
     //   message (String):
     //     The logging message to dump to the console.
     //
     //----------------------------------------------------------------------------------------------------------------

     public void log ( String message )
     {
         logDepth ( message, M_STACK_TRACE_CALL_DEPTH );
     }

     //----------------------------------------------------------------------------------------------------------------
     // Method: logDepth
     //
     // Description:
     //
     //   Internal logging method that formats and prints a message to the console at the specified call stack depth.
     //   Delegates to the format method to build the log entry string.
     //
     // Arguments:
     //
     //   message (String):
     //     The logging message to dump to the console.
     //
     //   callDepth (int):
     //     The stack trace depth used to identify the calling method. This value determines which level of the
     //     call stack is reported as the log entry's origin.
     //
     //----------------------------------------------------------------------------------------------------------------

     private void logDepth ( String message, int callDepth )
     {
        if ( this.loggingEnabled )
        {
            if ( this.owner == null )
            {
                System.out.print ( format ( null, message, callDepth ) );
            }
            else
            {
                System.out.print ( format ( this.owner, message, callDepth ) );
            }
        }
     }

     //----------------------------------------------------------------------------------------------------------------
     // Method: format
     //
     // Description:
     //
     //   Formats an input string in preparation to be logged to the console. Builds a log entry string that includes
     //   timestamp, class name, method name, and the message, using stack trace introspection to identify the caller.
     //
     // Arguments:
     //
     //   owner (Class):
     //     The class of the owning object, used to identify log entries.
     //
     //   message (String):
     //     The logging message to dump to the console.
     //
     //   callDepth (int):
     //     The stack trace depth used to identify the calling method.
     //
     // Returns:
     //
     //   A formatted log entry string containing the timestamp, caller identification, and message, ready to be
     //   printed to the console.
     //
     //----------------------------------------------------------------------------------------------------------------

     private String format ( @ SuppressWarnings ( "rawtypes" ) Class owner, String message, int callDepth )
     {
         // @formatter:off

         // Element configuration

         Boolean showDateAndTime = true;
         Boolean showFileName    = false;
         Boolean showClassName   = true;
         Boolean showMethodName  = true;
         Boolean showMessage     = true;

         // Get the current date and configure the date and time formats.

         Date             now               = new Date ();
         SimpleDateFormat timeFormat        = new SimpleDateFormat ( M_FORMAT_TIME );
         SimpleDateFormat msFormat          = new SimpleDateFormat ( M_FORMAT_MS   );
         String           dateAndTime       = new String ();
         String           fileName          = new String ();
         String           className         = new String ();
         String           methodName        = new String ();
         String           loggingMessage    = new String ();

         // @formatter:on

         // Get the current active stack trace.
         //
         // - To get the current executing method fileName, we call getStackTrace() from the Thread class.
         // - The stack trace returned includes all method calls, up to and including getStackTrace();
         // - To get the fileName of the method we are logging we need to address the appropriate level of the trace stack.
         //
         //   - Call depth 0 = getStackTrace().
         //   - Call depth 1 = this (Format) method.
         //   - Call depth 2 = the Log method that called this (Format) method.
         //   - Call depth 3 = the caller we want to report on.

         StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

         // Compile date and time string.

         if ( showDateAndTime )
         {
             dateAndTime =  M_FORMAT_EMPTY;
             dateAndTime += "[";
             dateAndTime += timeFormat.format ( now );
             dateAndTime += msFormat.format   ( now );
             dateAndTime += "] ";
         }

         // Compile the current executing filename string.

         if ( showFileName )
         {
             fileName =  M_FORMAT_EMPTY;
             fileName += stackTrace [ callDepth ].getFileName ();
             fileName += M_FORMAT_SPACE;
             fileName += M_FORMAT_TAB;
         }

         // Compile the class fileName string.
         //
         // - We don't use the stack trace for this, because the stack trace will always return the super class of a generalization hierarchy.
         // - For logging purposes, We would like to reference use the actual derived class. Which we pass in through owner when the Logger class is initialized.

         if ( showClassName )
         {
             className = M_FORMAT_EMPTY;

             if ( owner != null )
             {
                 className += owner.getSimpleName ();
                 className += M_FORMAT_MEMBER_DELIMITER;
             }
         }

         // Get the fileName of the method we would like to reference as executing.
         // Note this is not the 'actual' method currently executing, but the one we would like to 'show' as executing for logging purposes.

         if ( showMethodName )
         {
             methodName =  M_FORMAT_EMPTY;
             methodName += stackTrace [ callDepth ].getMethodName();
             methodName += M_FORMAT_ARGUMENT_TEMPLATE;
             methodName += M_FORMAT_TAB;
         }

         // Compile logging message.

         loggingMessage =  M_FORMAT_EMPTY;
         loggingMessage += M_FORMAT_NEW_LINE;
         loggingMessage += ( showDateAndTime ) ? dateAndTime : "";
         loggingMessage += ( showFileName    ) ? fileName    : "";
         loggingMessage += ( showClassName   ) ? className   : "";
         loggingMessage += ( showMethodName  ) ? methodName  : "";
         loggingMessage += ( showMessage     ) ? message     : "";

         // Return the new log record.

         return loggingMessage;
     }

}
