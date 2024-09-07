package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.entity.Enemy
import kotlin.math.min

class PhysicsSystem : EntitySystem() {
    init { priority = -5 }

    val world = World(Vector2.Zero, true).apply { setContactListener(object : ContactListener {
        override fun beginContact(contact: Contact) {}
        override fun endContact(contact: Contact) {
            val bodies = listOf(contact.fixtureA, contact.fixtureB).associateWith { it.body.userData as? Entity }

            val ball = bodies.entries.find { it.value is Ball } ?: return
            val enemy = bodies.entries.find { it.value is Enemy } ?: return

            engine.removeEntity(enemy.value)
            bodiesToDelete += enemy.key.body
        }
        override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
        override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
    }) }
    private var bodiesToDelete: MutableList<Body> = mutableListOf()

    private var accumulator = 0f
    override fun update(deltaTime: Float) {
        // max frame time to avoid spiral of death (on slow devices)
        accumulator += min(deltaTime, 0.25f)

        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)

            bodiesToDelete.forEach { world.destroyBody(it) }
            bodiesToDelete = mutableListOf()

            accumulator -= TIME_STEP
        }
    }

    companion object {
        const val TIME_STEP = 1/60f
        const val VELOCITY_ITERATIONS = 6
        const val POSITION_ITERATIONS = 2
    }
}
