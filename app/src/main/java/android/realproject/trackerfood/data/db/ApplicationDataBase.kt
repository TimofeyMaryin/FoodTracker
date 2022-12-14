package android.realproject.trackerfood.data.db

import android.app.Application
import android.realproject.trackerfood.data.db.avatar_db.AvatarDao
import android.realproject.trackerfood.data.db.avatar_db.AvatarEntity
import android.realproject.trackerfood.data.db.food_hint_db.FoodHintDao
import android.realproject.trackerfood.data.db.food_hint_db.FoodHintEntity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FoodEntity::class,
        AvatarEntity::class,
        FoodHintEntity::class
    ],
    version = 6
)
abstract class ApplicationDataBase: RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun avatarDao(): AvatarDao
    abstract fun foodHintDao(): FoodHintDao

    companion object {
        @Volatile var INSTANCE: ApplicationDataBase? = null

        fun getInstance(application: Application): ApplicationDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        application,
                        ApplicationDataBase::class.java,
                        "food.db"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }

                INSTANCE = instance
                return INSTANCE!!

            }
        }
    }
}