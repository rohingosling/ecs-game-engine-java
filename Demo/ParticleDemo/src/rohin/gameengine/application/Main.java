//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Program entry point for the ParticleDemo application.
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
//   JVM entry point for the ParticleDemo application.
//
//   - Creates an Application instance, runs the main loop, and terminates the process on completion.
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
    //   JVM entry point. Creates and runs the Application, then calls System.exit to ensure all AWT
    //   threads are terminated.
    //
    // Arguments:
    //
    //   args (String[]):
    //     Command-line arguments (unused).
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static void main ( String[] args )
    {
        Application application = new Application ();
        application.run ();

        System.exit ( 0 );
    }
}
