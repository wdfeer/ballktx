package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Input.Keys
import wdfeer.ballktx.component.InputComponent
import wdfeer.ballktx.system.CameraSystem

class CameraInputEntity(private val cameraSystem: CameraSystem) : Entity() {
    init {
        add(InputComponent {
            if (isKeyJustPressed(Keys.V))
                cameraSystem.cycleMode()
            if (isKeyPressed(Keys.EQUALS))
                cameraSystem.camera.zoom -= 0.003f
            if (isKeyPressed(Keys.MINUS))
                cameraSystem.camera.zoom += 0.003f
        })
    }
}
