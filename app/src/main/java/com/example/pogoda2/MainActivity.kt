package com.example.pogoda2

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pogoda2.adapter.WeatherAdapter
import com.example.pogoda2.adapter.WeatherListener
import com.example.pogoda2.databinding.ActivityMainBinding
import com.example.pogoda2.repozitory_and_dataanswer.ForViewModelWeather
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


class MainActivity() : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var www: List<ForViewModelWeather>

//    protected val TAG = "LocationOnOff"
//
//
//    private val googleApiClient: GoogleApiClient? = null
//    val REQUEST_LOCATION = 199

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController =
            this.findNavController(R.id.myNavHostFragment1) // поиска объекта контроллера навигации:
//        NavigationUI.setupActionBarWithNavController(       // код для привязки контроллера навигации к панели приложения
//            this,
//            navController
//        )
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //obtieneLocalizacion()


        val adapter = WeatherAdapter(WeatherListener { itemelement ->
            viewModel.onClickDetail(itemelement)
        })               /// создал адаптер

        viewModel.choosendetail.observe(this, androidx.lifecycle.Observer {
            navController.navigate(R.id.weatherDetailFragment3)
        })
        binding.weatherRecyclerView.adapter = adapter

        binding.lifecycleOwner = this

        viewModel.weatherrr.observe(this, Observer {
            if(it.isNotEmpty()){
                    adapter.submitList(it)
                    viewModel.onClickDetail(it[0])
                    binding.btnMyLocationTemp.visibility = View.VISIBLE// отобр.первый элемент
                adapter.selectItemPosition(0)

            }

        })
        viewModel.errorr.observe(this, Observer {
            Toast.makeText(applicationContext, "Ошибка при загрузке данных $it", Toast.LENGTH_LONG)
                .show()
        })



        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    viewModel.saveCurrentCoordinates(location.latitude, location.longitude)
                    viewModel.refreshDataFromRepository(location.latitude, location.longitude)

                    //отобр.первый элемент
                }
            }

            override fun onLocationAvailability(var1: LocationAvailability) {
                if (var1.isLocationAvailable == false) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("GPS")
                        .setMessage("Включить геоданные?")
                        .setPositiveButton("Ок", object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }

                        }).show()
                }
            }
        }
        binding.btnMyLocationTemp.setOnClickListener() {

            viewModel.refreshDataFromRepository(
                viewModel.coordinatess?.lat!!,
                viewModel.coordinatess?.lon!!
            )

        }
        binding.btnEnterCityName.setOnClickListener() {
            var dialog = CityNameDialogFragment()


            dialog.show(supportFragmentManager, "customDialog")
            //dialog.dismiss()
        }

        binding.btnLocationOnMap.setOnClickListener() {
            this.findNavController(R.id.myNavHostFragment1).navigate(R.id.mapFragment)
            binding.btnMyLocationTemp.visibility = View.INVISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment1)
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        binding.btnMyLocationTemp.visibility =
            View.VISIBLE
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        checkPermission()
    }

    override fun onResume() {
        super.onResume()

    }


    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 1000000
            fastestInterval = 500000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun checkPermission() {
        var permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(this@MainActivity, "Вы дали разрешение", Toast.LENGTH_SHORT).show()
                startLocationUpdates()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(
                    this@MainActivity,
                    "Отказано в разрешении\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setPermissions(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()
    }


}



