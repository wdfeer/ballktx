package wdfeer.ballktx

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync
import wdfeer.ballktx.screen.GameScreen
import wdfeer.ballktx.screen.SplashScreen

class Main : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(SplashScreen())
        setScreen<SplashScreen>()

        addScreen(GameScreen())
        KtxAsync.async {
            delay(1000)
            setScreen<GameScreen>()
        }
    }
}

