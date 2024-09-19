package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

abstract class Interior(val center: Vector2, val size: Vector2) : Entity() {
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

    val walls: MutableList<Wall> = mutableListOf()
    protected fun Engine.addWall(world: World, position: Vector2, size: Vector2): Wall =
        Wall(world, position, size).also { this.addEntity(it) }

    companion object {
        const val WALL_THICKNESS = 1f
    }
}
