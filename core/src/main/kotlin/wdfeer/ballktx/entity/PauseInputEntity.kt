package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Input.Keys
import wdfeer.ballktx.component.InputComponent
import wdfeer.ballktx.system.PauseSystem

class PauseInputEntity(private val pauseSystem: PauseSystem) : Entity() {
    init {
        add(InputComponent {
            if (isKeyJustPressed(Keys.ESCAPE)) {
                pauseSystem.toggle()
            }
        })
    }
}
