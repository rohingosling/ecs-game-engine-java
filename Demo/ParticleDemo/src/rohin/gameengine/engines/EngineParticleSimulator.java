// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Name:    EngineParticleSimulator
// Version: 1.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   ECS engine for the particle simulation. Creates all simulation entities
//   (world, particle groups, individual particles, HUD), loads sprite resources
//   from settings, and registers the simulation systems.
//
//   The swap buffer approach follows the v1 pattern — Application owns the
//   GraphicsWindow and BufferStrategy, and swapBuffer() calls
//   screenBuffer.show().
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package rohin.gameengine.engines;

import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import rohin.gameengine.ApplicationSettings;
import rohin.gameengine.ECSEngine;
import rohin.gameengine.GMath;
import rohin.gameengine.GlobalCache;
import rohin.gameengine.TextFormat;
import rohin.gameengine.application.Application;
import rohin.gameengine.application.Constants;
import rohin.gameengine.application.KeyboardEventHandlerSimulator;
import rohin.gameengine.components.*;
import rohin.gameengine.entities.EntityParticle;
import rohin.gameengine.entities.EntityParticleGroup;
import rohin.gameengine.entities.EntityParticleWorld;
import rohin.gameengine.entities.EntitySimulationHud;
import rohin.gameengine.systems.SystemSimulationCollider;
import rohin.gameengine.systems.SystemSimulationForceAccumulator;
import rohin.gameengine.systems.SystemSimulationGravity;
import rohin.gameengine.systems.SystemSimulationParticleGroupPropagator;
import rohin.gameengine.systems.SystemSimulationPhysics;
import rohin.gameengine.systems.SystemSimulationRenderer;
import rohin.gameengine.systems.SystemSimulationRepulsion;

public class EngineParticleSimulator extends ECSEngine
{
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Fields
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Application application;


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Accessors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Application getApplication () { return this.application; }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Abstract method implementations
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Swap buffer.
    //
    // Follows the v1 pattern: Application owns the BufferStrategy. The engine
    // calls screenBuffer.show() to flip the double buffer.
    // ----------------------------------------------------------------------------------------------------------------

    @ Override
    public void swapBuffer ()
    {
        BufferStrategy screenBuffer = this.application.getScreenBuffer ();
        screenBuffer.show ();
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Constructor 1
    // ----------------------------------------------------------------------------------------------------------------

    public EngineParticleSimulator ( Application application )
    {
        super ();
        this.application = application;
        getCommandManager ().setLoggingEnabled ( application.getSettings ().getBoolean ( Constants.KEY_APPLICATION_LOGGING_ENABLED ) );
        initialize ();
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Private initialization.
    // ----------------------------------------------------------------------------------------------------------------

    private void initialize ()
    {
        ApplicationSettings settings   = this.application.getSettings ();
        GlobalCache         globalCache = this.application.getGlobalCache ();

        // Read particle counts from GlobalCache.

        int countRed    = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_RED );
        int countGreen  = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_GREEN );
        int countBlue   = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_BLUE );
        int countYellow = (int) globalCache.get ( Constants.GLOBAL_CACHE_PARTICLE_COUNT_YELLOW );

        // Load sprite image paths from settings.

        // @formatter:off

        String spriteRed      = settings.getString ( Constants.KEY_SPRITE_RED );
        String spriteGreen    = settings.getString ( Constants.KEY_SPRITE_GREEN );
        String spriteBlue     = settings.getString ( Constants.KEY_SPRITE_BLUE );
        String spriteYellow   = settings.getString ( Constants.KEY_SPRITE_YELLOW );
        String spriteSelected = settings.getString ( Constants.KEY_SPRITE_SELECTED );
        String spriteShadow   = settings.getString ( Constants.KEY_SPRITE_SHADOW );

        // @formatter:on

        // Load particle mass and radius settings.

        // @formatter:off

        double massRed      = settings.getDouble ( Constants.KEY_PARTICLE_MASS_RED );
        double massGreen    = settings.getDouble ( Constants.KEY_PARTICLE_MASS_GREEN );
        double massBlue     = settings.getDouble ( Constants.KEY_PARTICLE_MASS_BLUE );
        double massYellow   = settings.getDouble ( Constants.KEY_PARTICLE_MASS_YELLOW );

        double radiusRed    = settings.getDouble ( Constants.KEY_PARTICLE_RADIUS_RED );
        double radiusGreen  = settings.getDouble ( Constants.KEY_PARTICLE_RADIUS_GREEN );
        double radiusBlue   = settings.getDouble ( Constants.KEY_PARTICLE_RADIUS_BLUE );
        double radiusYellow = settings.getDouble ( Constants.KEY_PARTICLE_RADIUS_YELLOW );

        // @formatter:on

        // Load physics settings.

        // @formatter:off

        double gravityConstant  = settings.getDouble  ( Constants.KEY_PHYSICS_GRAVITY_CONSTANT );
        double repulsionConstant = settings.getDouble ( Constants.KEY_PHYSICS_REPULSION_CONSTANT );
        double frictionCoeff    = settings.getDouble  ( Constants.KEY_PHYSICS_FRICTION_COEFFICIENT );
        double elasticityCoeff  = settings.getDouble  ( Constants.KEY_PHYSICS_ELASTICITY_COEFFICIENT );
        double userAcceleration = settings.getDouble  ( Constants.KEY_PHYSICS_USER_ACCELERATION );

        // @formatter:on

        // Load shadow settings.

        // @formatter:off

        double shadowOffsetX = settings.getDouble ( Constants.KEY_SHADOW_OFFSET_X );
        double shadowOffsetY = settings.getDouble ( Constants.KEY_SHADOW_OFFSET_Y );
        double shadowOpacity = settings.getDouble ( Constants.KEY_SHADOW_OPACITY );
        double shadowScale   = settings.getDouble ( Constants.KEY_SHADOW_SCALE );

        // @formatter:on

        // Load trail settings.

        // @formatter:off

        int[]   trailColorRed    = parseColor ( settings.getString ( Constants.KEY_TRAIL_COLOR_RED ) );
        int[]   trailColorGreen  = parseColor ( settings.getString ( Constants.KEY_TRAIL_COLOR_GREEN ) );
        int[]   trailColorBlue   = parseColor ( settings.getString ( Constants.KEY_TRAIL_COLOR_BLUE ) );
        int[]   trailColorYellow = parseColor ( settings.getString ( Constants.KEY_TRAIL_COLOR_YELLOW ) );
        int     trailDepth       = settings.getInteger ( Constants.KEY_TRAIL_DEPTH );
        double  trailOpacityHead = settings.getDouble  ( Constants.KEY_TRAIL_OPACITY_HEAD );
        double  trailOpacityTail = settings.getDouble  ( Constants.KEY_TRAIL_OPACITY_TAIL );
        int     trailThickness   = settings.getInteger ( Constants.KEY_TRAIL_THICKNESS );
        boolean trailsVisible    = settings.getBoolean ( Constants.KEY_TRAIL_VISIBLE );

        // @formatter:on

        // Load wireframe, projection, and velocity settings.

        // @formatter:off

        boolean wireframeVisible = settings.getBoolean ( Constants.KEY_WIREFRAME_VISIBLE );
        double  projectionZoom   = settings.getDouble  ( Constants.KEY_PROJECTION_ZOOM );
        double  velocityMin      = settings.getDouble  ( Constants.KEY_INITIAL_VELOCITY_MIN );
        double  velocityMax      = settings.getDouble  ( Constants.KEY_INITIAL_VELOCITY_MAX );

        // @formatter:on

        int screenWidth  = this.application.getScreenWidth ();
        int screenHeight = this.application.getScreenHeight ();

        // Create world entity.

        EntityParticleWorld world = new EntityParticleWorld ( Constants.ENTITY_PARTICLE_WORLD, "ENTITY_PARTICLE_WORLD", this );

        ComponentWorld worldComp = (ComponentWorld) world.getComponent ( Constants.COMPONENT_WORLD );
        worldComp.particleCountRed      = countRed;
        worldComp.particleCountGreen    = countGreen;
        worldComp.particleCountBlue     = countBlue;
        worldComp.particleCountYellow   = countYellow;
        worldComp.gravitationalConstant = gravityConstant;
        worldComp.repulsiveConstant     = repulsionConstant;
        worldComp.trailsVisible         = trailsVisible;
        worldComp.gravityEnabled        = settings.getBoolean ( Constants.KEY_PHYSICS_GRAVITY_ENABLED );
        worldComp.repulsionEnabled      = settings.getBoolean ( Constants.KEY_PHYSICS_REPULSION_ENABLED );
        worldComp.frictionEnabled       = settings.getBoolean ( Constants.KEY_PHYSICS_FRICTION_ENABLED );
        worldComp.elasticityEnabled     = settings.getBoolean ( Constants.KEY_PHYSICS_ELASTICITY_ENABLED );

        ComponentResourceBackgroundImage bgImage = (ComponentResourceBackgroundImage) world.getComponent ( Constants.COMPONENT_RESOURCE_BACKGROUND_IMAGE );
        bgImage.imagePath = settings.getString ( Constants.KEY_SIMULATION_BACKGROUND );

        addEntity ( Constants.ENTITY_PARTICLE_WORLD, world );

        // Create particle group entities (templates for propagation).

        createParticleGroup ( Constants.ENTITY_GROUP_PARTICLES_RED, "ENTITY_GROUP_PARTICLES_RED",
                spriteRed, spriteShadow, massRed, radiusRed, trailColorRed,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible );

        createParticleGroup ( Constants.ENTITY_GROUP_PARTICLES_GREEN, "ENTITY_GROUP_PARTICLES_GREEN",
                spriteGreen, spriteShadow, massGreen, radiusGreen, trailColorGreen,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible );

        createParticleGroup ( Constants.ENTITY_GROUP_PARTICLES_BLUE, "ENTITY_GROUP_PARTICLES_BLUE",
                spriteBlue, spriteShadow, massBlue, radiusBlue, trailColorBlue,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible );

        createParticleGroup ( Constants.ENTITY_GROUP_PARTICLES_YELLOW, "ENTITY_GROUP_PARTICLES_YELLOW",
                spriteYellow, spriteShadow, massYellow, radiusYellow, trailColorYellow,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible );

        // Create individual particle entities.

        double worldWidth  = (double) screenWidth / (double) screenHeight;
        double worldHeight = 1.0;
        double margin      = 0.05;
        int particleId     = Constants.ENTITY_SIMULATION_PARTICLE_BASE;

        particleId = createParticles ( particleId, countRed, Constants.ENTITY_GROUP_PARTICLES_RED,
                spriteRed, spriteShadow, massRed, radiusRed, trailColorRed,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible,
                velocityMin, velocityMax, margin, worldWidth, worldHeight );

        particleId = createParticles ( particleId, countGreen, Constants.ENTITY_GROUP_PARTICLES_GREEN,
                spriteGreen, spriteShadow, massGreen, radiusGreen, trailColorGreen,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible,
                velocityMin, velocityMax, margin, worldWidth, worldHeight );

        particleId = createParticles ( particleId, countBlue, Constants.ENTITY_GROUP_PARTICLES_BLUE,
                spriteBlue, spriteShadow, massBlue, radiusBlue, trailColorBlue,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible,
                velocityMin, velocityMax, margin, worldWidth, worldHeight );

        particleId = createParticles ( particleId, countYellow, Constants.ENTITY_GROUP_PARTICLES_YELLOW,
                spriteYellow, spriteShadow, massYellow, radiusYellow, trailColorYellow,
                shadowOffsetX, shadowOffsetY, shadowOpacity, shadowScale,
                trailDepth, trailOpacityHead, trailOpacityTail, trailThickness,
                frictionCoeff, elasticityCoeff, projectionZoom, wireframeVisible,
                velocityMin, velocityMax, margin, worldWidth, worldHeight );

        // Create HUD entity.

        EntitySimulationHud hud = new EntitySimulationHud ( Constants.ENTITY_HUD_SIMULATION, "ENTITY_HUD_SIMULATION", this );

        ComponentHud hudComp = (ComponentHud) hud.getComponent ( Constants.COMPONENT_HUD );
        hudComp.font     = settings.getString  ( Constants.KEY_HUD_FONT_NAME );
        hudComp.fontSize = settings.getInteger ( Constants.KEY_HUD_FONT_SIZE );
        hudComp.color    = parseColor ( settings.getString ( Constants.KEY_HUD_FONT_COLOR ) );
        hudComp.position.setVector ( settings.getDouble ( Constants.KEY_HUD_POSITION_X ), settings.getDouble ( Constants.KEY_HUD_POSITION_Y ) );

        addEntity ( Constants.ENTITY_HUD_SIMULATION, hud );

        // Register systems (order matters: propagator -> gravity -> repulsion -> force -> physics -> collider -> renderer).

        addSystem ( Constants.SYSTEM_SIMULATION_PARTICLE_GROUP_PROPAGATOR, new SystemSimulationParticleGroupPropagator ( Constants.SYSTEM_SIMULATION_PARTICLE_GROUP_PROPAGATOR, "SYSTEM_SIMULATION_PARTICLE_GROUP_PROPAGATOR", this ) );
        addSystem ( Constants.SYSTEM_SIMULATION_GRAVITY,                   new SystemSimulationGravity ( Constants.SYSTEM_SIMULATION_GRAVITY, "SYSTEM_SIMULATION_GRAVITY", this ) );
        addSystem ( Constants.SYSTEM_SIMULATION_REPULSION,                 new SystemSimulationRepulsion ( Constants.SYSTEM_SIMULATION_REPULSION, "SYSTEM_SIMULATION_REPULSION", this ) );
        addSystem ( Constants.SYSTEM_SIMULATION_FORCE_ACCUMULATOR,         new SystemSimulationForceAccumulator ( Constants.SYSTEM_SIMULATION_FORCE_ACCUMULATOR, "SYSTEM_SIMULATION_FORCE_ACCUMULATOR", this ) );
        addSystem ( Constants.SYSTEM_SIMULATION_PHYSICS,                   new SystemSimulationPhysics ( Constants.SYSTEM_SIMULATION_PHYSICS, "SYSTEM_SIMULATION_PHYSICS", this ) );
        addSystem ( Constants.SYSTEM_SIMULATION_COLLIDER,                  new SystemSimulationCollider ( Constants.SYSTEM_SIMULATION_COLLIDER, "SYSTEM_SIMULATION_COLLIDER", this ) );
        addSystem ( Constants.SYSTEM_SIMULATION_RENDERER,                  new SystemSimulationRenderer ( Constants.SYSTEM_SIMULATION_RENDERER, "SYSTEM_SIMULATION_RENDERER", this ) );

        // Initialize keyboard handler.

        for ( KeyListener kl : this.application.getWindow ().getScreen ().getKeyListeners () )
        {
            this.application.getWindow ().getScreen ().removeKeyListener ( kl );
        }

        KeyboardEventHandlerSimulator keyboardHandler = new KeyboardEventHandlerSimulator ( this );

        this.application.getWindow ().getScreen ().addKeyListener ( keyboardHandler );
        this.application.getWindow ().getScreen ().setFocusTraversalKeysEnabled ( false );
        this.application.getWindow ().getScreen ().requestFocus ();
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ----------------------------------------------------------------------------------------------------------------
    // Create a particle group entity (template for propagation to particles).
    // ----------------------------------------------------------------------------------------------------------------

    private void createParticleGroup ( Integer entityId, String name,
            String spritePath, String shadowPath, double mass, double radius, int[] trailColor,
            double shadowOffsetX, double shadowOffsetY, double shadowOpacity, double shadowScale,
            int trailDepth, double trailOpacityHead, double trailOpacityTail, int trailThickness,
            double frictionCoeff, double elasticityCoeff,
            double projectionZoom, boolean wireframeVisible )
    {
        EntityParticleGroup group = new EntityParticleGroup ( entityId, name, this );

        ComponentResourceSprite sprite = (ComponentResourceSprite) group.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
        sprite.imagePath = spritePath;

        ComponentResourceShadow shadow = (ComponentResourceShadow) group.getComponent ( Constants.COMPONENT_RESOURCE_SHADOW );
        shadow.imagePath = shadowPath;
        shadow.offset.setVector ( shadowOffsetX, shadowOffsetY );
        shadow.opacity = shadowOpacity;
        shadow.scale   = shadowScale;

        ComponentGeometryCircle geometry = (ComponentGeometryCircle) group.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
        geometry.radius  = radius;
        geometry.visible = wireframeVisible;

        ComponentPhysics physics = (ComponentPhysics) group.getComponent ( Constants.COMPONENT_PHYSICS );
        physics.mass                  = mass;
        physics.frictionCoefficient   = frictionCoeff;
        physics.elasticityCoefficient = elasticityCoeff;

        ComponentTranslationHistory trail = (ComponentTranslationHistory) group.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );
        trail.color                   = trailColor;
        trail.translationHistoryDepth = trailDepth;
        trail.opacityHead             = trailOpacityHead;
        trail.opacityTail             = trailOpacityTail;
        trail.thickness               = trailThickness;

        ComponentProjection2D projection = (ComponentProjection2D) group.getComponent ( Constants.COMPONENT_PROJECTION_2D );
        projection.scale.setVector ( projectionZoom, projectionZoom );

        addEntity ( entityId, group );
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Create individual particle entities for one color group.
    //
    // Returns the next available particle ID.
    // ----------------------------------------------------------------------------------------------------------------

    private int createParticles ( int startId, int count, int groupEntityId,
            String spritePath, String shadowPath, double mass, double radius, int[] trailColor,
            double shadowOffsetX, double shadowOffsetY, double shadowOpacity, double shadowScale,
            int trailDepth, double trailOpacityHead, double trailOpacityTail, int trailThickness,
            double frictionCoeff, double elasticityCoeff,
            double projectionZoom, boolean wireframeVisible,
            double velocityMin, double velocityMax,
            double margin, double worldWidth, double worldHeight )
    {
        for ( int i = 0; i < count; i++ )
        {
            int particleId = startId + i;

            EntityParticle particle = new EntityParticle ( particleId, "ENTITY_PARTICLE_" + particleId, this );

            // Set group membership.

            ComponentParticleGroup groupComp = (ComponentParticleGroup) particle.getComponent ( Constants.COMPONENT_PARTICLE_GROUP );
            groupComp.groupId = groupEntityId;

            // Set sprite.

            ComponentResourceSprite sprite = (ComponentResourceSprite) particle.getComponent ( Constants.COMPONENT_RESOURCE_SPRITE );
            sprite.imagePath = spritePath;

            // Set shadow.

            ComponentResourceShadow shadow = (ComponentResourceShadow) particle.getComponent ( Constants.COMPONENT_RESOURCE_SHADOW );
            shadow.imagePath = shadowPath;
            shadow.offset.setVector ( shadowOffsetX, shadowOffsetY );
            shadow.opacity = shadowOpacity;
            shadow.scale   = shadowScale;

            // Set geometry.

            ComponentGeometryCircle geometry = (ComponentGeometryCircle) particle.getComponent ( Constants.COMPONENT_GEOMETRY_CIRCLE );
            geometry.radius  = radius;
            geometry.visible = wireframeVisible;

            // Set physics.

            ComponentPhysics physics = (ComponentPhysics) particle.getComponent ( Constants.COMPONENT_PHYSICS );
            physics.mass                  = mass;
            physics.frictionCoefficient   = frictionCoeff;
            physics.elasticityCoefficient = elasticityCoeff;

            // Set random initial velocity.

            double vx = GMath.randomInRange ( -velocityMax, velocityMax );
            double vy = GMath.randomInRange ( -velocityMax, velocityMax );
            physics.velocity.setVector ( vx, vy );

            // Set random initial position within world bounds.

            double px = GMath.randomInRange ( margin, worldWidth - margin );
            double py = GMath.randomInRange ( margin, worldHeight - margin );

            ComponentTransform transform = (ComponentTransform) particle.getComponent ( Constants.COMPONENT_TRANSFORM );
            transform.translation.setVector ( px, py );

            // Set trail properties.

            ComponentTranslationHistory trail = (ComponentTranslationHistory) particle.getComponent ( Constants.COMPONENT_TRANSLATION_HISTORY );
            trail.color                   = trailColor;
            trail.translationHistoryDepth = trailDepth;
            trail.opacityHead             = trailOpacityHead;
            trail.opacityTail             = trailOpacityTail;
            trail.thickness               = trailThickness;

            // Set projection.

            ComponentProjection2D projection = (ComponentProjection2D) particle.getComponent ( Constants.COMPONENT_PROJECTION_2D );
            projection.scale.setVector ( projectionZoom, projectionZoom );

            addEntity ( particleId, particle );
        }

        return startId + count;
    }


    // ----------------------------------------------------------------------------------------------------------------
    // Parse an RGB color string "R,G,B" into an int[3] array.
    // ----------------------------------------------------------------------------------------------------------------

    private int[] parseColor ( String rgb )
    {
        String[] parts = rgb.split ( "," );

        int r = Integer.parseInt ( parts[0].trim () );
        int g = Integer.parseInt ( parts[1].trim () );
        int b = Integer.parseInt ( parts[2].trim () );

        return new int[] { r, g, b };
    }
}
