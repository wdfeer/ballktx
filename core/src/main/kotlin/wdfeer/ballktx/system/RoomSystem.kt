package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import ktx.math.plus
import ktx.math.times
import wdfeer.ballktx.component.BodyComponent.Companion.body
import wdfeer.ballktx.entity.Interior
import wdfeer.ballktx.entity.Room

class RoomSystem(eng: Engine) : EntitySystem() {
    init {
        eng.addEntity(Room(eng, Vector2.Zero, roomSize))
    }

    private val interiors: List<Interior> get() = engine.entities.filterIsInstance<Interior>()
    private val rooms get() = engine.entities.filterIsInstance<Room>()

    val lastRoom: Room get() = rooms.last()
    val currentInterior: Interior? get() = engine.getSystem(BallSystem::class.java).ball.body!!.position.run {
        interiors.find { it.intersects(this) }
    }

    fun createNextRoom() =
        engine.addEntity(Room(
            engine,
            lastRoom.center + Vector2.X * ROOM_WIDTH * 2f,
            roomSize,
            true
        ).also { lastRoom.connect(it) })

    companion object {
        const val ROOM_WIDTH = 160f
        const val ROOM_HEIGHT = 100f

        private val roomSize: Vector2 get() = Vector2(ROOM_WIDTH, ROOM_HEIGHT)
    }
}
