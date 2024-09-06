package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import ktx.app.clearScreen
import ktx.ashley.get
import ktx.assets.disposeSafely
import ktx.graphics.use
import wdfeer.ballktx.component.PositionComponent
import wdfeer.ballktx.component.TextureComponent


class RenderSystem : IteratingSystem(Family.all(TextureComponent::class.java, PositionComponent::class.java).get()), Disposable {
    val camera: OrthographicCamera
    init {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera(30f, 30 * (h / w))

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
        val pos = entity[PositionComponent.MAPPER]!!.pos
        val texture = entity[TextureComponent.MAPPER]!!.texture
        batch.draw(texture, pos.x, pos.y)
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}
