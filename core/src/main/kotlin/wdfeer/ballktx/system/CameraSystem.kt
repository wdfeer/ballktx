package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import wdfeer.ballktx.component.BodyComponent
import wdfeer.ballktx.entity.CameraInputEntity
import wdfeer.ballktx.extension.toVector3
import wdfeer.ballktx.system.parent.PauseImmune
import wdfeer.ballktx.util.GraphicsUtils

class CameraSystem(eng: Engine) : EntitySystem(), PauseImmune {
    init {
        eng.addEntity(CameraInputEntity(this))
    }

    private val cameraSize: Vector2
        get() = if (RoomSystem.ROOM_WIDTH / RoomSystem.ROOM_HEIGHT > GraphicsUtils.aspectRatio)
            Vector2(RoomSystem.ROOM_WIDTH, RoomSystem.ROOM_WIDTH / GraphicsUtils.aspectRatio)
        else
            Vector2(RoomSystem.ROOM_HEIGHT * GraphicsUtils.aspectRatio, RoomSystem.ROOM_HEIGHT)

    val camera: OrthographicCamera = cameraSize.run { OrthographicCamera(x, y) }
    private var cameraMode = CameraMode.Fixed

    private fun getCameraPosition(engine: Engine): Vector2 = when (cameraMode) {
        CameraMode.Fixed -> engine.getSystem(RoomSystem::class.java).run { currentInterior?.center ?: lastRoom.center }
        CameraMode.Following -> engine.getSystem(BallSystem::class.java).ball.getComponent(BodyComponent::class.java).body.position
    }

    fun update(batch: SpriteBatch) {
        camera.position.set(getCameraPosition(engine).toVector3())
        camera.update()
        batch.projectionMatrix = camera.combined
    }

    private enum class CameraMode {
        Fixed,
        Following
    }

    fun cycleMode() {
        val modes = CameraMode.entries
        cameraMode = modes[(cameraMode.ordinal + 1) % modes.size]
    }

    companion object {
        val Engine.cameraSystem: CameraSystem get() = getSystem(CameraSystem::class.java)
    }
}
