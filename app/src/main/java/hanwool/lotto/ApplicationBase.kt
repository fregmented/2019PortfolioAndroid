package hanwool.lotto

import android.app.Application
import io.realm.Realm

class ApplicationBase: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}