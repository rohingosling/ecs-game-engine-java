//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Application layer for the Hello World demo.
//
//   - Orchestrates instances of the ECS game engine.
//
//   - In this case, we have only one instance of the game engine, that prints "Hello World!" to the console.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.application;

import rohin.gameengine.engines.EngineHelloWorld;

//*********************************************************************************************************************
// Class: Application
//
// Description:
//
//   Entry point for the Hello World demo application. Creates and runs a single instance of the
//   EngineHelloWorld game engine, which prints "Hello World!" to the console.
//
//*********************************************************************************************************************

public class Application
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    private EngineHelloWorld engine;

    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: Application
    //
    // Description:
    //
    //   Default constructor. Creates a new EngineHelloWorld instance and immediately starts the game loop by
    //   calling run.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Application ()
    {
        this.engine = new EngineHelloWorld ();
        this.engine.run ();
    }
}
