package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import wdfeer.ballktx.entity.Ball

class BallSystem(engine: Engine) : EntitySystem() {
    val ball: Ball = Ball(
        engine.getSystem(PhysicsSystem::class.java).world,
        engine.getSystem(RoomSystem::class.java).currentRoom.center
    ).also { engine.addEntity(it) }
}
