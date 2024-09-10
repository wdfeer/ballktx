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
import wdfeer.ballktx.component.TextureComponent
import wdfeer.ballktx.extension.toVector3
import wdfeer.ballktx.system.SpawnSystem.Companion.getChamberSize

class RenderSystem : IteratingSystem(Family.all(TextureComponent::class.java).get()), Disposable {
    val batch = SpriteBatch()
    init {
        priority = -10
    }

    override fun update(deltaTime: Float) {
        CameraManager.update(this)
        clearScreen(red = 0f, green = 0f, blue = 0f)

        batch.use { super.update(deltaTime) }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        // TODO: Implement drawing texture
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}

object CameraManager {
    val camera: OrthographicCamera = getChamberSize().run { OrthographicCamera(x, y) }
        .apply { position.set(viewportWidth / 2f, viewportHeight / 2f, 0f) }
    private var cameraMode = CameraMode.Fixed

    private fun getCameraPosition(engine: Engine): Vector2 = when (cameraMode) {
        CameraMode.Fixed -> getChamberSize().run { Vector2(x / 2f, y / 2f) }
        CameraMode.Following -> engine.getSystem(SpawnSystem::class.java).ball.getComponent(BodyComponent::class.java).body.position
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
