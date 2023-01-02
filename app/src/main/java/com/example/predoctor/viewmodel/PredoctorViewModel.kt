package com.example.predoctor.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.predoctor.api.NewsInstance
import com.example.predoctor.api.ProdoctorInstance
import com.example.predoctor.models.PerformanceResponse
import com.example.predoctor.models.headtohead.HeadToHeadResponse
import com.example.predoctor.models.newsresponse.NewsResponse
import com.example.predoctor.models.predictionresponse.PredictionResponse
import com.example.predoctor.models.predoctorresults.Prediction
import com.example.predoctor.repository.PredoctorRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG = "ViewModel"
enum class LoadingState{LOADING, SUCCESSFUL, ERROR}

class PredoctorViewModel(private val repository: PredoctorRepository):ViewModel() {
    val predictionData : MutableLiveData<PredictionResponse> = MutableLiveData()
    val performanceData : MutableLiveData<PerformanceResponse> = MutableLiveData()
    val headToHeadData: MutableLiveData<HeadToHeadResponse?> = MutableLiveData()
    val loadingStatePrediction: MutableLiveData<LoadingState> = MutableLiveData()
    val loadingStateH2H: MutableLiveData<LoadingState> = MutableLiveData()
    val loadingStateNews: MutableLiveData<LoadingState> = MutableLiveData()
    val newsData : MutableLiveData<NewsResponse> = MutableLiveData()

    init {
       getPredictions()
        getPerformance()
    }

    private fun getPerformance(){
        val retro = ProdoctorInstance.retrofit
        viewModelScope.launch {
            try {
                val result = retro.getPerformance()
                performanceData.postValue(result)
                //  loadingStatePrediction.postValue(LoadingState.SUCCESSFUL)
            }catch (e:Exception){
                Log.e(TAG, "getPerformance: Error $e occurred")
                // loadingStatePrediction.postValue(LoadingState.ERROR)
            }
        }
    }

    fun getPredictions(){
        val retro = ProdoctorInstance.retrofit
        viewModelScope.launch {
            loadingStatePrediction.value = LoadingState.LOADING
            delay(1000)
           try {
               val result = retro.getPredictions()
               predictionData.postValue(result)
               loadingStatePrediction.value = LoadingState.SUCCESSFUL
           }catch (e:Exception){
               Log.e(TAG, "getPredictions: Error $e occurred")
               loadingStatePrediction.value = LoadingState.ERROR
           }
        }
    }


    fun getHeadtoHead(matchId:Int){
        val retro = ProdoctorInstance.retrofit
        viewModelScope.launch {
            //headToHeadData.postValue(null)
            loadingStateH2H.postValue(LoadingState.LOADING)
            try {
                val result = retro.getHeadtoHead(matchId)
                headToHeadData.postValue(result)
                loadingStateH2H.postValue(LoadingState.SUCCESSFUL)
            } catch (e:Exception){
                Log.e(TAG, "getHeadtoHead: Error $e occurred")
                loadingStateH2H.postValue(LoadingState.ERROR)
            }
        }
    }

    fun getNews(){
        val retro = NewsInstance.retrofit
        viewModelScope.launch {
            loadingStateNews.postValue(LoadingState.LOADING)
            delay(1000)
            try {
                val result = retro.getAllnews()
                newsData.postValue(result)
                loadingStateNews.postValue(LoadingState.SUCCESSFUL)
            }catch (e:Exception){
                loadingStateNews.postValue(LoadingState.ERROR)
                Log.e(TAG, "getNews: Error $e occured")
            }
        }
    }

   fun savePrediction(prediction: Prediction){
        viewModelScope.launch {
            repository.savePrediction(prediction)
        }
    }

    fun deletePrediction(prediction: Prediction){
        viewModelScope.launch {
            repository.deletePrediction(prediction)
        }
    }

    fun clearList(){
        viewModelScope.launch { repository.deleteAll()}
    }

    fun getPredictionsList() = repository.getPrediction()

    fun getPredictionCount() = repository.getPredictionCount()

    fun getTotalOdds() = repository.getAllOdds()


}



class PredoctorViewModelFactory(private val repository: PredoctorRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PredoctorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PredoctorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}