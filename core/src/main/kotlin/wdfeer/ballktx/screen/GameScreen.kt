package wdfeer.ballktx.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.assets.disposeSafely
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.system.DebugRenderSystem
import wdfeer.ballktx.system.PhysicsSystem
import wdfeer.ballktx.system.RenderSystem

class GameScreen : KtxScreen {
    private val engine = Engine()

    override fun show() {
        engine.add {
            val physicsSystem = PhysicsSystem()
            val renderSystem = RenderSystem()

            addSystem(physicsSystem)
            addSystem(DebugRenderSystem())
            addSystem(renderSystem)

            addEntity(Ball(
                physicsSystem.world,
                renderSystem.camera.position.run { Vector2(x, y) }
            ))
        }
    }

    override fun render(delta: Float) = engine.update(delta)

    override fun dispose() = engine.systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
}
