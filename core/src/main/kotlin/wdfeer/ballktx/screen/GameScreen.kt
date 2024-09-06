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
import wdfeer.ballktx.system.PhysicsSystem
import wdfeer.ballktx.system.RenderSystem

class GameScreen : KtxScreen {
    private val engine = Engine()

    override fun show() {
        engine.add {
            addSystem(PhysicsSystem())
            addSystem(DebugRenderSystem())
            addSystem(RenderSystem())
        }

        spawnEntities()
    }

    private fun spawnEntities() {
        val physics = engine.getSystem(PhysicsSystem::class.java)
        val render = engine.getSystem(RenderSystem::class.java)

        engine.addEntity(Ball(
            physics.world,
            render.camera.position.run { Vector2(x, y) }
        ))

        // TODO: create 4 walls around the camera
    }

    override fun render(delta: Float) = engine.update(delta)

    override fun dispose() = engine.systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
}
