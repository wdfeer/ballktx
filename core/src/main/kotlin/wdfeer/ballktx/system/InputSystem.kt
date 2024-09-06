package wdfeer.ballktx.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import wdfeer.ballktx.component.BodyComponent

class InputSystem : EntitySystem() {
    init {
        priority = -100 // Highest priority
    }

    override fun update(deltaTime: Float) {
        val force: Float = 5f

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            engine.entities[0]!!.getComponent(BodyComponent::class.java).body.applyForceToCenter(0f, force, true)
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            engine.entities[0]!!.getComponent(BodyComponent::class.java).body.applyForceToCenter(-force, 0f, true)
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            engine.entities[0]!!.getComponent(BodyComponent::class.java).body.applyForceToCenter(0f, -force, true)
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            engine.entities[0]!!.getComponent(BodyComponent::class.java).body.applyForceToCenter(force, 0f, true)
    }
}
