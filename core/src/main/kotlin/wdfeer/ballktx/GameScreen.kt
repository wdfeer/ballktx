package wdfeer.ballktx

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use

class GameScreen : KtxScreen {
    private val font = BitmapFont()
    private val batch = SpriteBatch()

    override fun render(delta: Float) {
        clearScreen(red = 0f, green = 0f, blue = 0f)

        batch.use {
            val layout = GlyphLayout(font, "Game Scene")
            font.draw(it, layout, 320f - layout.width / 2f, 240f - layout.height / 2f)
        }
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}
