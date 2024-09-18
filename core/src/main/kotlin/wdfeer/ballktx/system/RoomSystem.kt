package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ktx.math.plus
import ktx.math.times
import wdfeer.ballktx.component.BodyComponent.Companion.body
import wdfeer.ballktx.entity.Wall
import wdfeer.ballktx.system.PhysicsSystem.Companion.world
import kotlin.math.absoluteValue
import kotlin.random.Random

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

abstract class Interior(val center: Vector2, val size: Vector2) {
    fun intersects(point: Vector2): Boolean {
        // Calculate the bounds of the interior
        val halfSizeX = size.x / 2
        val halfSizeY = size.y / 2

        val left = center.x - halfSizeX
        val right = center.x + halfSizeX
        val bottom = center.y - halfSizeY
        val top = center.y + halfSizeY

        // Check if the 'other' point lies within the bounds of the interior
        return point.x in left..right && point.y in bottom..top
    }

    protected fun Engine.addWall(world: World, position: Vector2, size: Vector2): Wall =
        Wall(world, position, size).also { this.addEntity(it) }
    val walls: MutableList<Wall> = mutableListOf()

    companion object {
        const val WALL_THICKNESS = 1f
    }
}

class Room(val engine: Engine, private val world: World, center: Vector2, size: Vector2, openLeftWall: Boolean = false) : Interior(center, size) {
    init {
        walls.addAll(spawnWalls(engine, world, center, size, openLeftWall))
    }

    private fun removeWall(wall: Wall) {
        walls.remove(wall)
        engine.removeEntity(wall)
    }

    private fun spawnWalls(engine: Engine, world: World, center: Vector2, size: Vector2, openLeftWall: Boolean): List<Wall> {
        val width = size.x
        val height = size.y
        val wallList = mutableListOf<Wall>()

        // Left wall
        if (openLeftWall) {
            val wallHeight = height / 3
            wallList.add(engine.addWall(
                world,
                Vector2(center.x - width / 2 - WALL_THICKNESS / 2, center.y - height / 3),
                Vector2(WALL_THICKNESS, wallHeight)
            ))
            wallList.add(engine.addWall(
                world,
                Vector2(center.x - width / 2 - WALL_THICKNESS / 2, center.y + height / 3),
                Vector2(WALL_THICKNESS, wallHeight)
            ))
        } else {
            wallList.add(engine.addWall(
                world,
                Vector2(center.x - width / 2 - WALL_THICKNESS / 2, center.y),
                Vector2(WALL_THICKNESS, height)
            ))
        }

        // Right wall
        val rightWallHeight = height / 3
        wallList.add(engine.addWall(
            world,
            Vector2(center.x + width / 2 + WALL_THICKNESS / 2, center.y - height / 3),
            Vector2(WALL_THICKNESS, rightWallHeight)
        ))
        wallList.add(engine.addWall(
            world,
            Vector2(center.x + width / 2 + WALL_THICKNESS / 2, center.y),
            Vector2(WALL_THICKNESS, rightWallHeight)
        ))
        wallList.add(engine.addWall(
            world,
            Vector2(center.x + width / 2 + WALL_THICKNESS / 2, center.y + height / 3),
            Vector2(WALL_THICKNESS, rightWallHeight)
        ))

        // Bottom wall
        wallList.add(engine.addWall(
            world,
            Vector2(center.x, center.y - height / 2 - WALL_THICKNESS / 2),
            Vector2(width, WALL_THICKNESS)
        ))

        // Top wall
        wallList.add(engine.addWall(
            world,
            Vector2(center.x, center.y + height / 2 + WALL_THICKNESS / 2),
            Vector2(width, WALL_THICKNESS)
        ))

        return wallList
    }

    fun connect(other: Room): Corridor {
        // Remove middle right wall of this room
        val middleRightWall = walls.find { it.position.x > center.x && it.position.y == center.y }
        if (middleRightWall != null) {
            removeWall(middleRightWall)
        }

        val corridorWidth = ((center.x + size.x / 2) - (other.center.x - other.size.x / 2)).absoluteValue
        val corridorHeight = size.y / 3
        val corridorCenter = Vector2(
            (center.x + other.center.x) / 2,
            center.y
        )

        return Corridor(engine, world, corridorCenter, Vector2(corridorWidth, corridorHeight))
    }

    fun getRandomPosition() = center + size * Vector2(Random.nextFloat() - 0.5f, Random.nextFloat() - 0.5f)
}
class Corridor(val engine: Engine, private val world: World, center: Vector2, size: Vector2) : Interior(center, size) {
    init {
        walls.apply {
            add(engine.addWall(
                world,
                Vector2(center.x, center.y + size.y / 2 + WALL_THICKNESS / 2),
                Vector2(size.x, WALL_THICKNESS)
            ))

            add(engine.addWall(
                world,
                Vector2(center.x, center.y - size.y / 2 - WALL_THICKNESS / 2),
                Vector2(size.x, WALL_THICKNESS)
            ))
        }
    }
}
