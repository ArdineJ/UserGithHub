    package com.ardine.githubuser.model

    import android.util.Log
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.ardine.githubuser.data.local.DbModule
    import com.ardine.githubuser.data.local.entity.User
    import com.ardine.githubuser.data.remote.api.ApiConfig
    import com.ardine.githubuser.data.remote.response.DetailUserResponse
    import kotlinx.coroutines.launch
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class DetailViewModel(private val dbModule:DbModule):ViewModel(){

        private val _detailUser = MutableLiveData<DetailUserResponse>()
        val detailUser: LiveData<DetailUserResponse> = _detailUser

        private var isFavorite = false

        fun setFavorite(item: User?){
            viewModelScope.launch {
                item?.let {
                    if (isFavorite){
                        dbModule.userDao.delete(item)
                    } else{
                        dbModule.userDao.insert(item)
                    }
                 }
                isFavorite = !isFavorite
            }
        }

        fun findFavorite(id:Int, listenFavorite: () -> Unit){
            viewModelScope.launch {
                val user = dbModule.userDao.getFavoriteUserById(id)
                listenFavorite()
            }
        }

        fun findUser(username:String){
            val client = ApiConfig.getApiService().getUserDetails(username)
            client.enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        _detailUser.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let{ Log.d("failure", it)
                    }
                }

            })
        }
    }