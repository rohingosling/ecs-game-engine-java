//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Global constants used by all elements of the ParticleDemo project.
//
//   - Use `CONSTANT_VALUE = elementIndex++` to auto-increment after initializing elementIndex to
//     zero.
//
//   - Append new IDs at the end of each group. Never insert in the middle.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine.application;

//*********************************************************************************************************************
// Class: Constants
//
// Description:
//
//   Central registry of all compile-time and auto-incremented constants used across the
//   ParticleDemo application. Groups include application states, global-cache keys, ECS component
//   and entity IDs, system IDs, menu screen indices, and settings property keys.
//
//*********************************************************************************************************************

public class Constants
{
    // @formatter:off

    //-----------------------------------------------------------------------------------------------------------------
    // Application State Constants
    //-----------------------------------------------------------------------------------------------------------------

    public static final int APPLICATION_STATE_IDLE                       = 0;
    public static final int APPLICATION_STATE_STARTING                   = 1;
    public static final int APPLICATION_STATE_STOPPING                   = 2;
    public static final int APPLICATION_STATE_PAUSED                     = 3;
    public static final int APPLICATION_STATE_MENU_MAIN                  = 10;
    public static final int APPLICATION_STATE_MENU_SETTINGS              = 11;
    public static final int APPLICATION_STATE_MENU_INSTRUCTIONS          = 12;
    public static final int APPLICATION_STATE_MENU_ABOUT                 = 13;
    public static final int APPLICATION_STATE_LEVEL_PARTICLE_SIMULATION  = 100;

    public static final int APPLICATION_STATE_INITIAL                    = APPLICATION_STATE_IDLE;


    //-----------------------------------------------------------------------------------------------------------------
    // GlobalCache Keys
    //-----------------------------------------------------------------------------------------------------------------

    public static final String GLOBAL_CACHE_APPLICATION_STATE     = "applicationState";
    public static final String GLOBAL_CACHE_PARTICLE_COUNT_RED    = "particleCountRed";
    public static final String GLOBAL_CACHE_PARTICLE_COUNT_GREEN  = "particleCountGreen";
    public static final String GLOBAL_CACHE_PARTICLE_COUNT_BLUE   = "particleCountBlue";
    public static final String GLOBAL_CACHE_PARTICLE_COUNT_YELLOW = "particleCountYellow";


    //-----------------------------------------------------------------------------------------------------------------
    // Component IDs
    //-----------------------------------------------------------------------------------------------------------------

    private static int componentIndex = 0;

    public static final Integer COMPONENT_BUTTON_STATE              = componentIndex++;
    public static final Integer COMPONENT_BUTTON_IMAGE              = componentIndex++;
    public static final Integer COMPONENT_BUTTON_TEXT               = componentIndex++;
    public static final Integer COMPONENT_PARTICLE_COUNT            = componentIndex++;
    public static final Integer COMPONENT_PARTICLE_GROUP            = componentIndex++;
    public static final Integer COMPONENT_RESOURCE_BACKGROUND_IMAGE = componentIndex++;
    public static final Integer COMPONENT_RESOURCE_SPRITE           = componentIndex++;
    public static final Integer COMPONENT_RESOURCE_SHADOW           = componentIndex++;
    public static final Integer COMPONENT_GEOMETRY_CIRCLE           = componentIndex++;
    public static final Integer COMPONENT_GEOMETRY_RECTANGLE        = componentIndex++;
    public static final Integer COMPONENT_PROJECTION_2D             = componentIndex++;
    public static final Integer COMPONENT_TRANSFORM                 = componentIndex++;
    public static final Integer COMPONENT_TRANSLATION_HISTORY       = componentIndex++;
    public static final Integer COMPONENT_PHYSICS                   = componentIndex++;
    public static final Integer COMPONENT_WORLD                     = componentIndex++;
    public static final Integer COMPONENT_USER_CONTROL              = componentIndex++;
    public static final Integer COMPONENT_HUD                       = componentIndex++;
    public static final Integer COMPONENT_TEXT_BOX                  = componentIndex++;


    //-----------------------------------------------------------------------------------------------------------------
    // Entity IDs
    //-----------------------------------------------------------------------------------------------------------------

    private static int entityIndex = 0;

    public static final Integer ENTITY_MENU_BUTTON_START_SIMULATION               = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_SETTINGS                       = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_INSTRUCTIONS                   = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_ABOUT                          = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_EXIT                           = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_BACK                           = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_RED    = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_GREEN  = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_BLUE   = entityIndex++;
    public static final Integer ENTITY_MENU_BUTTON_SETTINGS_PARTICLE_COUNT_YELLOW = entityIndex++;
    public static final Integer ENTITY_MENU_TEXT_BOX_INSTRUCTIONS                 = entityIndex++;
    public static final Integer ENTITY_MENU_TEXT_BOX_ABOUT                        = entityIndex++;
    public static final Integer ENTITY_BACKGROUND_MENU                            = entityIndex++;
    public static final Integer ENTITY_GROUP_PARTICLES_RED                        = entityIndex++;
    public static final Integer ENTITY_GROUP_PARTICLES_GREEN                      = entityIndex++;
    public static final Integer ENTITY_GROUP_PARTICLES_BLUE                       = entityIndex++;
    public static final Integer ENTITY_GROUP_PARTICLES_YELLOW                     = entityIndex++;
    public static final Integer ENTITY_PARTICLE_WORLD                             = entityIndex++;
    public static final Integer ENTITY_HUD_SIMULATION                             = entityIndex++;

    public static final int ENTITY_SIMULATION_PARTICLE_BASE                       = 1000;


    //-----------------------------------------------------------------------------------------------------------------
    // Menu System IDs
    //-----------------------------------------------------------------------------------------------------------------

    private static int menuSystemIndex = 0;

    public static final Integer SYSTEM_MENU_MANAGER  = menuSystemIndex++;
    public static final Integer SYSTEM_MENU_RENDERER = menuSystemIndex++;


    //-----------------------------------------------------------------------------------------------------------------
    // Simulation System IDs
    //-----------------------------------------------------------------------------------------------------------------

    private static int simulationSystemIndex = 0;

    public static final Integer SYSTEM_SIMULATION_PARTICLE_GROUP_PROPAGATOR = simulationSystemIndex++;
    public static final Integer SYSTEM_SIMULATION_GRAVITY                   = simulationSystemIndex++;
    public static final Integer SYSTEM_SIMULATION_REPULSION                 = simulationSystemIndex++;
    public static final Integer SYSTEM_SIMULATION_FORCE_ACCUMULATOR         = simulationSystemIndex++;
    public static final Integer SYSTEM_SIMULATION_PHYSICS                   = simulationSystemIndex++;
    public static final Integer SYSTEM_SIMULATION_COLLIDER                  = simulationSystemIndex++;
    public static final Integer SYSTEM_SIMULATION_RENDERER                  = simulationSystemIndex++;


    //-----------------------------------------------------------------------------------------------------------------
    // Menu Screen Constants
    //-----------------------------------------------------------------------------------------------------------------

    public static final int SCREEN_MAIN         = 0;
    public static final int SCREEN_SETTINGS     = 1;
    public static final int SCREEN_INSTRUCTIONS = 2;
    public static final int SCREEN_ABOUT        = 3;


    //-----------------------------------------------------------------------------------------------------------------
    // Settings Property Keys
    //-----------------------------------------------------------------------------------------------------------------

    public static final String SETTINGS_FILE = "Demo/ParticleDemo/settings.properties";
    public static final String FONTS_DIR     = "Demo/ParticleDemo/Resources/Fonts";

    public static final String KEY_APPLICATION_NAME            = "Application.Name";
    public static final String KEY_APPLICATION_VERSION_MAJOR   = "Application.Version.Major";
    public static final String KEY_APPLICATION_VERSION_MINOR   = "Application.Version.Minor";
    public static final String KEY_APPLICATION_SCREEN_WIDTH    = "Application.Screen.Width";
    public static final String KEY_APPLICATION_SCREEN_HEIGHT   = "Application.Screen.Height";
    public static final String KEY_APPLICATION_LOGGING_ENABLED = "Application.Logging.Enabled";

    public static final String KEY_MENU_BACKGROUND_MAIN         = "Menu.Background.Main";
    public static final String KEY_MENU_BACKGROUND_SETTINGS     = "Menu.Background.Settings";
    public static final String KEY_MENU_BACKGROUND_INSTRUCTIONS = "Menu.Background.Instructions";
    public static final String KEY_MENU_BACKGROUND_ABOUT        = "Menu.Background.About";

    public static final String KEY_MENU_BUTTON_IMAGE_UP            = "Menu.Button.Image.Up";
    public static final String KEY_MENU_BUTTON_IMAGE_UP_SELECTED   = "Menu.Button.Image.UpSelected";
    public static final String KEY_MENU_BUTTON_IMAGE_DOWN          = "Menu.Button.Image.Down";
    public static final String KEY_MENU_BUTTON_IMAGE_DOWN_SELECTED = "Menu.Button.Image.DownSelected";
    public static final String KEY_MENU_BUTTON_IMAGE_DISABLED      = "Menu.Button.Image.Disabled";
    public static final String KEY_MENU_BUTTON_IMAGE_SHADOW        = "Menu.Button.Image.Shadow";
    public static final String KEY_MENU_BUTTON_SHADOW_OFFSET_X     = "Menu.Button.Shadow.Offset.X";
    public static final String KEY_MENU_BUTTON_SHADOW_OFFSET_Y     = "Menu.Button.Shadow.Offset.Y";
    public static final String KEY_MENU_BUTTON_SHADOW_OPACITY      = "Menu.Button.Shadow.Opacity";

    public static final String KEY_MENU_BUTTON_FONT_NAME             = "Menu.Button.Font.Name";
    public static final String KEY_MENU_BUTTON_FONT_SIZE             = "Menu.Button.Font.Size";
    public static final String KEY_MENU_BUTTON_FONT_COLOR_SELECTED   = "Menu.Button.Font.Color.Selected";
    public static final String KEY_MENU_BUTTON_FONT_COLOR_UNSELECTED = "Menu.Button.Font.Color.Unselected";
    public static final String KEY_MENU_BUTTON_FONT_COLOR_DISABLED   = "Menu.Button.Font.Color.Disabled";

    public static final String KEY_MENU_BUTTON_LAYOUT_X         = "Menu.Button.Layout.X";
    public static final String KEY_MENU_BUTTON_LAYOUT_Y_START   = "Menu.Button.Layout.Y.Start";
    public static final String KEY_MENU_BUTTON_LAYOUT_Y_SPACING = "Menu.Button.Layout.Y.Spacing";

    public static final String KEY_MENU_TEXTBOX_FONT_NAME    = "Menu.TextBox.Font.Name";
    public static final String KEY_MENU_TEXTBOX_FONT_SIZE    = "Menu.TextBox.Font.Size";
    public static final String KEY_MENU_TEXTBOX_FONT_COLOR   = "Menu.TextBox.Font.Color";
    public static final String KEY_MENU_TEXTBOX_X            = "Menu.TextBox.X";
    public static final String KEY_MENU_TEXTBOX_Y            = "Menu.TextBox.Y";
    public static final String KEY_MENU_TEXTBOX_WIDTH        = "Menu.TextBox.Width";
    public static final String KEY_MENU_TEXTBOX_HEIGHT       = "Menu.TextBox.Height";
    public static final String KEY_MENU_TEXTBOX_TEXT_OPACITY = "Menu.TextBox.Text.Opacity";

    public static final String KEY_MENU_TEXT_INSTRUCTIONS           = "Menu.Text.Instructions";
    public static final String KEY_MENU_TEXT_ABOUT                  = "Menu.Text.About";
    public static final String KEY_MENU_TEXT_INSTRUCTIONS_ALIGNMENT = "Menu.Text.Instructions.Alignment";
    public static final String KEY_MENU_TEXT_ABOUT_ALIGNMENT        = "Menu.Text.About.Alignment";

    public static final String KEY_PARTICLE_COUNT_RED_DEFAULT    = "Particle.Count.Red.Default";
    public static final String KEY_PARTICLE_COUNT_GREEN_DEFAULT  = "Particle.Count.Green.Default";
    public static final String KEY_PARTICLE_COUNT_BLUE_DEFAULT   = "Particle.Count.Blue.Default";
    public static final String KEY_PARTICLE_COUNT_YELLOW_DEFAULT = "Particle.Count.Yellow.Default";
    public static final String KEY_PARTICLE_COUNT_MIN            = "Particle.Count.Min";
    public static final String KEY_PARTICLE_COUNT_MAX            = "Particle.Count.Max";

    public static final String KEY_SIMULATION_BACKGROUND = "Simulation.Background";

    public static final String KEY_SPRITE_RED      = "Sprite.Red";
    public static final String KEY_SPRITE_GREEN    = "Sprite.Green";
    public static final String KEY_SPRITE_BLUE     = "Sprite.Blue";
    public static final String KEY_SPRITE_YELLOW   = "Sprite.Yellow";
    public static final String KEY_SPRITE_SELECTED = "Sprite.Selected";
    public static final String KEY_SPRITE_SHADOW   = "Sprite.Shadow";

    public static final String KEY_PHYSICS_GRAVITY_CONSTANT       = "Physics.Gravity.Constant";
    public static final String KEY_PHYSICS_REPULSION_CONSTANT     = "Physics.Repulsion.Constant";
    public static final String KEY_PHYSICS_SOFTENING_EPSILON      = "Physics.Softening.Epsilon";
    public static final String KEY_PHYSICS_FRICTION_COEFFICIENT   = "Physics.Friction.Coefficient";
    public static final String KEY_PHYSICS_ELASTICITY_COEFFICIENT = "Physics.Elasticity.Coefficient";
    public static final String KEY_PHYSICS_USER_ACCELERATION      = "Physics.User.Acceleration";
    public static final String KEY_PHYSICS_FRICTION_ANISOTROPIC   = "Physics.Friction.Anisotropic";
    public static final String KEY_PHYSICS_BOUNDARY_COLLISION     = "Physics.Boundary.Collision";
    public static final String KEY_PHYSICS_COLLISION_ITERATIONS   = "Physics.Collision.Iterations";

    public static final String KEY_PHYSICS_GRAVITY_ENABLED    = "Physics.Gravity.Enabled";
    public static final String KEY_PHYSICS_REPULSION_ENABLED  = "Physics.Repulsion.Enabled";
    public static final String KEY_PHYSICS_FRICTION_ENABLED   = "Physics.Friction.Enabled";
    public static final String KEY_PHYSICS_ELASTICITY_ENABLED = "Physics.Elasticity.Enabled";

    public static final String KEY_PARTICLE_MASS_RED      = "Particle.Mass.Red";
    public static final String KEY_PARTICLE_MASS_GREEN    = "Particle.Mass.Green";
    public static final String KEY_PARTICLE_MASS_BLUE     = "Particle.Mass.Blue";
    public static final String KEY_PARTICLE_MASS_YELLOW   = "Particle.Mass.Yellow";
    public static final String KEY_PARTICLE_RADIUS_RED    = "Particle.Radius.Red";
    public static final String KEY_PARTICLE_RADIUS_GREEN  = "Particle.Radius.Green";
    public static final String KEY_PARTICLE_RADIUS_BLUE   = "Particle.Radius.Blue";
    public static final String KEY_PARTICLE_RADIUS_YELLOW = "Particle.Radius.Yellow";

    public static final String KEY_TRAIL_COLOR_RED      = "Trail.Color.Red";
    public static final String KEY_TRAIL_COLOR_GREEN    = "Trail.Color.Green";
    public static final String KEY_TRAIL_COLOR_BLUE     = "Trail.Color.Blue";
    public static final String KEY_TRAIL_COLOR_YELLOW   = "Trail.Color.Yellow";
    public static final String KEY_TRAIL_COLOR_SELECTED = "Trail.Color.Selected";
    public static final String KEY_TRAIL_DEPTH          = "Trail.Depth";
    public static final String KEY_TRAIL_OPACITY_HEAD   = "Trail.Opacity.Head";
    public static final String KEY_TRAIL_OPACITY_TAIL   = "Trail.Opacity.Tail";
    public static final String KEY_TRAIL_THICKNESS      = "Trail.Thickness";
    public static final String KEY_TRAIL_VISIBLE        = "Trail.Visible";

    public static final String KEY_WIREFRAME_VISIBLE = "Wireframe.Visible";

    public static final String KEY_SHADOW_OFFSET_X = "Shadow.Offset.X";
    public static final String KEY_SHADOW_OFFSET_Y = "Shadow.Offset.Y";
    public static final String KEY_SHADOW_OPACITY  = "Shadow.Opacity";
    public static final String KEY_SHADOW_SCALE    = "Shadow.Scale";

    public static final String KEY_PROJECTION_ZOOM = "Projection.Zoom";

    public static final String KEY_HUD_FONT_NAME  = "Hud.Font.Name";
    public static final String KEY_HUD_FONT_SIZE  = "Hud.Font.Size";
    public static final String KEY_HUD_FONT_COLOR = "Hud.Font.Color";
    public static final String KEY_HUD_POSITION_X = "Hud.Position.X";
    public static final String KEY_HUD_POSITION_Y = "Hud.Position.Y";

    public static final String KEY_INITIAL_VELOCITY_MIN = "Initial.Velocity.Min";
    public static final String KEY_INITIAL_VELOCITY_MAX = "Initial.Velocity.Max";

    // @formatter:on
}
