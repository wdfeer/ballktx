package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.system.PhysicsSystem.Companion.world

class BallSystem(engine: Engine) : EntitySystem() {
    val ball: Ball = Ball(
        engine.world,
        SPAWN_POINT
    ).also { engine.addEntity(it) }

    companion object {
        val SPAWN_POINT: Vector2 = Vector2(0f, 0f)
    }
}
