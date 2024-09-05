package wdfeer.ballktx.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.ashley.add
import ktx.assets.disposeSafely
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.system.MovementSystem
import wdfeer.ballktx.system.RenderSystem

class GameScreen : KtxScreen {
    private val engine = Engine()

    override fun show() {
        engine.add {
            addSystem(MovementSystem())
            addSystem(RenderSystem(SpriteBatch()))

            addEntity(Ball())
        }
    }

    override fun render(delta: Float) {
        clearScreen(red = 0f, green = 0f, blue = 0f)
        engine.update(delta)
    }

    override fun dispose() {
        engine.systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
    }
}
