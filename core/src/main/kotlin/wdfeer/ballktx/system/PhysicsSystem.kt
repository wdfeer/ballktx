package wdfeer.ballktx.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

class PhysicsSystem : EntitySystem() {
    val world = World(Vector2.Zero, true)

    override fun update(deltaTime: Float) {
        world.step(1/60f, 6, 2)

    }
}
