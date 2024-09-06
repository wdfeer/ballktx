package wdfeer.ballktx.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import kotlin.math.min

class PhysicsSystem : EntitySystem() {
    init { priority = -5 }

    val world = World(Vector2.Zero, true)

    private var accumulator = 0f
    override fun update(deltaTime: Float) {
        // max frame time to avoid spiral of death (on slow devices)
        accumulator += min(deltaTime, 0.25f)

        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
            accumulator -= TIME_STEP
        }
    }

    companion object {
        const val TIME_STEP = 1/60f
        const val VELOCITY_ITERATIONS = 6
        const val POSITION_ITERATIONS = 2
    }
}
