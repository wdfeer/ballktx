package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.system.PhysicsSystem.Companion.world

class BallSystem(engine: Engine) : EntitySystem() {
    val ball: Ball = Ball(
        engine.world,
        engine.getSystem(RoomSystem::class.java).currentRoom.center
    ).also { engine.addEntity(it) }
}
