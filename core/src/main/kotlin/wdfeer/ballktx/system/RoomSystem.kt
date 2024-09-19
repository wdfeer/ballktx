package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import ktx.math.plus
import ktx.math.times
import wdfeer.ballktx.component.BodyComponent.Companion.body
import wdfeer.ballktx.entity.Corridor
import wdfeer.ballktx.entity.Interior
import wdfeer.ballktx.entity.Room
import wdfeer.ballktx.system.PhysicsSystem.Companion.world

class RoomSystem(eng: Engine) : EntitySystem() {
    private val rooms: MutableList<Room> = mutableListOf(Room(eng, eng.world, Vector2.Zero, roomSize))
    private val corridors: MutableList<Corridor> = mutableListOf()
    val currentInterior: Interior? get() = engine.getSystem(BallSystem::class.java).ball.body!!.position.let {
        rooms.find { r -> r.intersects(it) } ?: corridors.find { c -> c.intersects(it) }
    }
    val lastRoom: Room get() = rooms.last()

    fun createNextRoom() {
        val room = Room(engine, engine.world, lastRoom.center + Vector2.X * ROOM_WIDTH * 2f, roomSize, true)
        corridors.add(lastRoom.connect(room))
        rooms.add(room)
    }

    companion object {
        const val ROOM_WIDTH = 160f
        const val ROOM_HEIGHT = 100f

        private val roomSize: Vector2 get() = Vector2(ROOM_WIDTH, ROOM_HEIGHT)
    }
}
