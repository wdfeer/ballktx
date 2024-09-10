package wdfeer.ballktx.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World

class DebugRenderSystem : EntitySystem() {
    private val debugRenderer = Box2DDebugRenderer()

    override fun update(deltaTime: Float) {
        debugRenderer.render(world, CameraManager.camera.combined)
    }

    private val world: World get() = engine.getSystem(PhysicsSystem::class.java).world
}
