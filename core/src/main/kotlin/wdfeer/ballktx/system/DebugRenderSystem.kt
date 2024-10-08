package wdfeer.ballktx.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import wdfeer.ballktx.system.CameraSystem.Companion.cameraSystem
import wdfeer.ballktx.system.PhysicsSystem.Companion.world
import wdfeer.ballktx.system.parent.PauseImmune

class DebugRenderSystem : EntitySystem(), PauseImmune {
    private val debugRenderer = Box2DDebugRenderer()

    override fun update(deltaTime: Float) {
        debugRenderer.render(engine.world, engine.cameraSystem.camera.combined)
    }
}
