package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import wdfeer.ballktx.component.DrawComponent
import wdfeer.ballktx.component.DrawComponent.Companion.draw
import wdfeer.ballktx.system.CameraSystem.Companion.cameraSystem
import wdfeer.ballktx.system.parent.PauseImmune

class RenderSystem : IteratingSystem(Family.all(DrawComponent::class.java).get()), Disposable, PauseImmune {
    private val batch = SpriteBatch()

    override fun update(deltaTime: Float) {
        engine.cameraSystem.update(batch)
        clearScreen(red = 0f, green = 0f, blue = 0f)

        batch.use { super.update(deltaTime) }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.draw!!(batch)
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}

