package wdfeer.ballktx.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

object GraphicsUtils {
    val screenSize: Vector2 get() = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    val aspectRatio: Float get() = Gdx.graphics.width.toFloat() / Gdx.graphics.height
}
