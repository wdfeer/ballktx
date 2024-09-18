package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import wdfeer.ballktx.component.BodyComponent
import wdfeer.ballktx.component.DrawComponent
import wdfeer.ballktx.component.DrawComponent.Companion.draw
import wdfeer.ballktx.extension.toVector3
import wdfeer.ballktx.system.RoomSystem.Companion.ROOM_HEIGHT
import wdfeer.ballktx.system.RoomSystem.Companion.ROOM_WIDTH
import wdfeer.ballktx.util.GraphicsUtils.aspectRatio

class RenderSystem : IteratingSystem(Family.all(DrawComponent::class.java).get()), Disposable {
    val batch = SpriteBatch()

    override fun update(deltaTime: Float) {
        CameraManager.update(this)
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

object CameraManager {
    private val cameraSize: Vector2 get() = if (ROOM_WIDTH / ROOM_HEIGHT > aspectRatio)
        Vector2(ROOM_WIDTH, ROOM_WIDTH / aspectRatio)
    else
        Vector2(ROOM_HEIGHT * aspectRatio, ROOM_HEIGHT)

    val camera: OrthographicCamera = cameraSize.run { OrthographicCamera(x, y) }
    private var cameraMode = CameraMode.Fixed

    private fun getCameraPosition(engine: Engine): Vector2 = when (cameraMode) {
        CameraMode.Fixed -> engine.getSystem(RoomSystem::class.java).run { currentInterior?.center ?: lastRoom.center }
        CameraMode.Following -> engine.getSystem(BallSystem::class.java).ball.getComponent(BodyComponent::class.java).body.position
    }

    fun update(render: RenderSystem) {
        camera.position.set(getCameraPosition(render.engine).toVector3())
        camera.update()
        render.batch.projectionMatrix = camera.combined
    }

    private enum class CameraMode {
        Fixed,
        Following
    }

    fun cycleMode() {
        val modes = CameraMode.entries
        cameraMode = modes[(cameraMode.ordinal + 1) % modes.size]
    }
}
