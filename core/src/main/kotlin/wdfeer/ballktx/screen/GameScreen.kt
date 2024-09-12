package wdfeer.ballktx.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.utils.Disposable
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.assets.disposeSafely
import wdfeer.ballktx.system.*

class GameScreen : KtxScreen {
    private val engine = Engine()

    override fun show() {
        engine.add {
            addSystem(PhysicsSystem())
            addSystem(DebugRenderSystem())
            addSystem(RenderSystem())
            addSystem(InputSystem())
            addSystem(SpawnSystem(engine))
            addSystem(RoomSystem(engine))
            addSystem(EnemySystem())
        }
    }


    override fun render(delta: Float) = engine.update(delta)

    override fun dispose() = engine.systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
}
