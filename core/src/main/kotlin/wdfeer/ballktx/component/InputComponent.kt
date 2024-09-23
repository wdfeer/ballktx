package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.Input

class InputComponent(
    val handle: Input.() -> Unit
) : Component {
    companion object {
        val MAPPER: ComponentMapper<InputComponent> = ComponentMapper.getFor(InputComponent::class.java)
    }
}
