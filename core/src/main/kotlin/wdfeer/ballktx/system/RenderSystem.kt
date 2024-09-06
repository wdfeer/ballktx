package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import wdfeer.ballktx.component.TextureComponent

class RenderSystem : IteratingSystem(Family.all(TextureComponent::class.java).get()), Disposable {
    val camera: OrthographicCamera
    init {
        priority = -10

        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_WIDTH * h / w)

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
    }

    private val batch = SpriteBatch()

    override fun update(deltaTime: Float) {
        camera.update()
        batch.projectionMatrix = camera.combined

        clearScreen(red = 0f, green = 0f, blue = 0f)

        batch.use { super.update(deltaTime) }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        // TODO: Implement drawing texture
    }

    override fun dispose() {
        batch.disposeSafely()
    }

    companion object {
        const val VIEWPORT_WIDTH = 100f
    }
}
