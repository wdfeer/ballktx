package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import wdfeer.ballktx.component.EnemyComponent
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.entity.Enemy

class SpawnSystem() : IntervalSystem(0.2f) {
    override fun updateInterval() {
        if (ball == null) spawnBall(engine)

        updateEnemySpawn()
    }

    var ball: Ball? = null
    private fun spawnBall(engine: Engine) {
        ball = Ball(
            engine.getSystem(PhysicsSystem::class.java).world,
            engine.getSystem(RoomSystem::class.java).currentRoom.center
        ).also { engine.addEntity(it) }
    }

    private val roomsComplete: MutableList<Room> = mutableListOf()
    private fun updateEnemySpawn() {
        val roomSystem = engine.getSystem(RoomSystem::class.java)

        if (!roomsComplete.contains(roomSystem.currentRoom)) {
            spawnEnemies()
            roomsComplete.add(roomSystem.currentRoom)
        }
        else if (engine.getEntitiesFor(Family.one(EnemyComponent::class.java).get()).none()) {
            roomSystem.createNextRoom()
        }
    }

    private fun spawnEnemies() {
        repeat(ENEMY_COUNT) {
            val pos = engine.getSystem(RoomSystem::class.java).currentRoom.getRandomPosition()
            engine.addEntity(Enemy(engine.getSystem(PhysicsSystem::class.java).world, pos))
        }
    }

    companion object {
        const val ENEMY_COUNT = 5
    }
}
