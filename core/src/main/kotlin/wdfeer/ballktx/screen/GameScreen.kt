package wdfeer.ballktx.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.assets.disposeSafely
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.entity.Wall
import wdfeer.ballktx.system.DebugRenderSystem
import wdfeer.ballktx.system.InputSystem
import wdfeer.ballktx.system.PhysicsSystem
import wdfeer.ballktx.system.RenderSystem

class GameScreen : KtxScreen {
    private val engine = Engine()

    override fun show() {
        engine.add {
            addSystem(PhysicsSystem())
            addSystem(DebugRenderSystem())
            addSystem(RenderSystem())
            addSystem(InputSystem())
        }

        spawnEntities()
    }

    private fun spawnEntities() {
        val physics = engine.getSystem(PhysicsSystem::class.java)
        val render = engine.getSystem(RenderSystem::class.java)

        // Create and add the ball entity
        engine.addEntity(Ball(
            physics.world,
            render.camera.position.run { Vector2(x, y) }
        ))

        // Create 4 walls around the camera's viewport
        val camera = render.camera
        val camWidth = camera.viewportWidth
        val camHeight = camera.viewportHeight
        val camPosition = camera.position

        // Define wall thickness
        val wallThickness = 1f

        // Left wall
        engine.addEntity(Wall(
            physics.world,
            Vector2(camPosition.x - camWidth / 2 - wallThickness / 2, camPosition.y),  // Position
            Vector2(wallThickness, camHeight)  // Size
        ))

        // Right wall
        engine.addEntity(Wall(
            physics.world,
            Vector2(camPosition.x + camWidth / 2 + wallThickness / 2, camPosition.y),  // Position
            Vector2(wallThickness, camHeight)  // Size
        ))

        // Bottom wall
        engine.addEntity(Wall(
            physics.world,
            Vector2(camPosition.x, camPosition.y - camHeight / 2 - wallThickness / 2),  // Position
            Vector2(camWidth, wallThickness)  // Size
        ))

        // Top wall
        engine.addEntity(Wall(
            physics.world,
            Vector2(camPosition.x, camPosition.y + camHeight / 2 + wallThickness / 2),  // Position
            Vector2(camWidth, wallThickness)  // Size
        ))
    }

    override fun render(delta: Float) = engine.update(delta)

    override fun dispose() = engine.systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
}
