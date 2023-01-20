package com.tqlweb.tqlsaldo


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.*

class MycustomIntroActivity : AppIntro() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        //addSlide(BlankFragment.newInstance("HOLA", "PAPUS!!"));

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.tutorial_add_tarjeta))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.tutorial_consulta_tarjeta))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.tutorial_delete_tarjeta))



        setTransformer(AppIntroPageTransformerType.Depth)


        // Toggle Indicator Visibility
        isIndicatorEnabled = true

// Change Indicator Color
        setIndicatorColor(
                selectedIndicatorColor = getColor(R.color.pink1),
                unselectedIndicatorColor = getColor(R.color.quantum_cyan100)
        )

// Switch from Dotted Indicator to Progress Indicator
       //setProgressIndicator()

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        finish()
    }


}