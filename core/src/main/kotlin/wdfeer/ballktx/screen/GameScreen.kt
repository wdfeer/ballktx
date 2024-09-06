package wdfeer.ballktx.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.utils.Disposable
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.assets.disposeSafely
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.system.DebugRenderSystem
import wdfeer.ballktx.system.MovementSystem
import wdfeer.ballktx.system.PhysicsSystem
import wdfeer.ballktx.system.RenderSystem

class GameScreen : KtxScreen {
    private val engine = Engine()

    override fun show() {
        engine.add {
            addSystem(PhysicsSystem())
            addSystem(MovementSystem())
            addSystem(DebugRenderSystem())
            addSystem(RenderSystem())


            addEntity(Ball())
        }
    }

    override fun render(delta: Float) = engine.update(delta)

    override fun dispose() = engine.systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
}
