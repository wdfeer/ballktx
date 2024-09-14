package wdfeer.ballktx.screen

import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import wdfeer.ballktx.engine.GameEngine

class GameScreen : KtxScreen {
    private val engine = GameEngine()

    override fun show() = engine.initialize()

    override fun render(delta: Float) = engine.update(delta)

    override fun dispose() = engine.disposeSafely()
}
