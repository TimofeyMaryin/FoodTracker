package android.realproject.trackerfood.data.viewModel

import android.app.Application
import android.realproject.trackerfood.data.db.ApplicationDataBase
import android.realproject.trackerfood.data.db.FoodDaoImpl
import android.realproject.trackerfood.data.db.FoodEntity
import android.realproject.trackerfood.data.db.avatar_db.AvatarDaoImpl
import android.realproject.trackerfood.data.db.avatar_db.AvatarEntity
import android.realproject.trackerfood.data.db.food_hint_db.FoodHintDaoImpl
import android.realproject.trackerfood.data.db.food_hint_db.FoodHintEntity
import android.realproject.trackerfood.model.HintCaloricModel
import android.realproject.trackerfood.model.SortCalByDayCount
import android.realproject.trackerfood.model.date.Date
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel(
    application: Application
): ViewModel(), FoodDaoImpl, AvatarDaoImpl, FoodHintDaoImpl {

    private val foodDao = ApplicationDataBase.getInstance(application = application).foodDao()
    private val avatarDao = ApplicationDataBase.getInstance(application).avatarDao()
    private val foodHintDao = ApplicationDataBase.getInstance(application = application).foodHintDao()

    override suspend fun insertFood(food: FoodEntity) = foodDao.insertFood(food)
    override fun getFoodByData(data: String): MutableList<FoodEntity> = foodDao.getFoodByData(data)
    override fun deleteTable() = foodDao.deleteTable()
    override suspend fun deleteFood(food: FoodEntity) = foodDao.deleteFood(food)
    fun getAllAvatar(): MutableList<AvatarEntity> = avatarDao.getAllAvatar()

    override fun getAllHint(): MutableList<FoodHintEntity> = foodHintDao.getAllHint()
    override fun insertProductHint(foodHintEntity: FoodHintEntity) = foodHintDao.insertProductHint(foodHintEntity)
    override fun deleteProductHint(foodHintEntity: FoodHintEntity) = foodHintDao.deleteProductHint(foodHintEntity)


    fun calculateCaloric(data: String): Int = calcCaloricByDay(data = data)


    var showOverview by mutableStateOf(false)

    private fun calcCaloricByDay(data: String): Int {
        var res by mutableStateOf(0)
        for (i in 0 until getFoodByData(data = data).size) {
            res += getFoodByData(data = data)[i].calories
        }

        return res
    }


    fun checkAvailabilityAvatar() = getAllAvatar().size == 0


    override fun deleteAvatar(avatarEntity: AvatarEntity) = avatarDao.deleteAvatar(avatarEntity)
    override fun insertAvatar(avatarModel: AvatarEntity) = avatarDao.insertAvatar(avatarModel)
    override fun deleteAllAvatar() = avatarDao.deleteAllAvatar()


    var indexTouchProductIndex by mutableStateOf(0)
    fun changeIndexTouchProductIndex(index: Int) = run { indexTouchProductIndex = index }


}

