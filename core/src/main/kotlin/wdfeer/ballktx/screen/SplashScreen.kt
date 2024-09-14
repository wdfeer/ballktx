package wdfeer.ballktx.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.graphics.use

class SplashScreen : KtxScreen {
    private val image = Texture("logo.png".toInternalFile(), true).apply { setFilter(
        Texture.TextureFilter.Linear,
        Texture.TextureFilter.Linear
    ) }
    private val batch = SpriteBatch()

    override fun render(delta: Float) {
        clearScreen(red = 0.1f, green = 0.1f, blue = 0.1f)
        batch.use {
            it.draw(image, Gdx.graphics.width / 2f - LOGO_WIDTH / 2f, Gdx.graphics.height / 2f - LOGO_HEIGHT / 2f)
        }
    }

    override fun dispose() {
        image.disposeSafely()
        batch.disposeSafely()
    }

    companion object {
        private const val LOGO_WIDTH = 440
        private const val LOGO_HEIGHT = 160
    }
}
