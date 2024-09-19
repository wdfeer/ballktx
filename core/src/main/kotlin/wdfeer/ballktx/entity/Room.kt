package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ktx.math.plus
import ktx.math.times
import wdfeer.ballktx.system.PhysicsSystem.Companion.world
import kotlin.math.absoluteValue
import kotlin.random.Random

class Room(val engine: Engine, center: Vector2, size: Vector2, openLeftWall: Boolean = false) : Interior(center, size) {
    init {
        walls.addAll(spawnWalls(engine, engine.world, center, size, openLeftWall))
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

    fun connect(other: Room) {
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

        engine.addEntity(Corridor(engine, engine.world, corridorCenter, Vector2(corridorWidth, corridorHeight)))
    }

    fun getRandomPosition() = center + size * Vector2(Random.nextFloat() - 0.5f, Random.nextFloat() - 0.5f)
}
