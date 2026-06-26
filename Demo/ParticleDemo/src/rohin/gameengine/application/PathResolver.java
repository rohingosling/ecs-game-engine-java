//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Resolves relative file paths for both IDE development and jpackage deployment.
//
//   - In IDE mode (no system property set), paths are used as-is relative to the working
//     directory.
//
//   - In jpackage mode, the launcher sets -Dapp.dir=$APPDIR, and all relative paths are resolved
//     against that directory.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.application;

import java.io.File;

//*********************************************************************************************************************
// Class: PathResolver
//
// Description:
//
//   Resolves relative file paths against the application base directory.
//
//   Supports both IDE development (paths used as-is) and jpackage deployment (paths prefixed with the app.dir
//   system property).
//
//*********************************************************************************************************************

public class PathResolver
{
    //=================================================================================================================
    // Constants
    //=================================================================================================================

    private static final String APP_DIR = System.getProperty ( "app.dir", "" );

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: resolve
    //
    // Description:
    //
    //   Resolves a relative path against the application base directory.
    //
    //   Returns the path as-is when running from the IDE (app.dir not set), or prefixed with the jpackage app
    //   directory when running from a packaged installation.
    //
    // Arguments:
    //
    //   relativePath (String):
    //     The relative file path to resolve.
    //
    // Returns:
    //
    //   The resolved absolute path as a String.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static String resolve ( String relativePath )
    {
        if ( APP_DIR.isEmpty () )
        {
            return relativePath;
        }

        return APP_DIR + File.separator + relativePath;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: resolveFile
    //
    // Description:
    //
    //   Resolves a relative path and returns it as a File object.
    //
    // Arguments:
    //
    //   relativePath (String):
    //     The relative file path to resolve.
    //
    // Returns:
    //
    //   A File object pointing to the resolved path.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static File resolveFile ( String relativePath )
    {
        return new File ( resolve ( relativePath ) );
    }
}
