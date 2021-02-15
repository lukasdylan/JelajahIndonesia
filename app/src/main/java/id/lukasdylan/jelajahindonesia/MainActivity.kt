package id.lukasdylan.jelajahindonesia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.lukasdylan.jelajahindonesia.databinding.ActivityMainBinding
import id.lukasdylan.jelajahindonesia.utils.fadeAnim
import id.lukasdylan.jelajahindonesia.utils.makeStatusBarTransparent

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var menuIds = listOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        menuIds = activityMainBinding.bottomNavigation.menu.children.map { it.itemId }.toList()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) ?: return

        val navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            activityMainBinding.bottomNavigation.isVisible = menuIds.indexOf(destination.id) != -1
        }

        activityMainBinding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener { }
            setOnNavigationItemSelectedListener {
                if (!navController.popBackStack(it.itemId, false)) {
                    navController.navigate(it.itemId, null, navOptions { fadeAnim() })
                }
                return@setOnNavigationItemSelectedListener true
            }
        }
    }
}