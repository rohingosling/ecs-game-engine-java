//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Application entry point for the Hello World demo.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.application;

//*********************************************************************************************************************
// Class: Main
//
// Description:
//
//   Program entry point. Contains the static main method that creates an Application instance to launch the
//   Hello World demo, then exits cleanly.
//
//*********************************************************************************************************************

public class Main
{
    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: main
    //
    // Description:
    //
    //   Application entry point. Creates a new Application instance, which in turn creates and runs the ECS
    //   engine, then terminates the process with exit code 0.
    //
    // Arguments:
    //
    //   args (String[]):
    //     Command-line arguments. Not used by this application.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static void main ( String[] args )
    {
        Application application = new Application ();

        System.exit ( 0 );
    }
}
