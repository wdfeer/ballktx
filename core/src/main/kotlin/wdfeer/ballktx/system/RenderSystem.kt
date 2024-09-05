package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import ktx.ashley.get
import ktx.assets.disposeSafely
import ktx.graphics.use
import wdfeer.ballktx.component.PositionComponent
import wdfeer.ballktx.component.TextureComponent

class RenderSystem(private val batch: SpriteBatch) : IteratingSystem(Family.all(TextureComponent::class.java, PositionComponent::class.java).get()), Disposable {
    override fun update(deltaTime: Float) {
        batch.use { super.update(deltaTime) }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = entity[PositionComponent.MAPPER]!!.pos
        val texture = entity[TextureComponent.MAPPER]!!.texture ?: return
        batch.draw(texture, pos.x, pos.y)
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}
