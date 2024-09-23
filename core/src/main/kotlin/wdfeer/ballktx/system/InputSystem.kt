package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import ktx.ashley.get
import wdfeer.ballktx.component.InputComponent
import wdfeer.ballktx.system.parent.PauseImmune

class InputSystem : IteratingSystem(Family.one(InputComponent::class.java).get()), PauseImmune {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val component = entity[InputComponent.MAPPER] ?: return
        component.handle(Gdx.input)
    }
}
