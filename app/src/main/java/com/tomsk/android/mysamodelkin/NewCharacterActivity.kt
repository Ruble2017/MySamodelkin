package com.tomsk.android.mysamodelkin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch


private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"
const val TAG= "mainActivity "

private var Bundle.characterData
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY, value)

class NewCharacterActivity : AppCompatActivity() {

    lateinit var nameTextView :TextView
    lateinit var raceTextView :TextView
    lateinit var dexterityTextView :TextView
    lateinit var wisdomTextView :TextView
    lateinit var strengthTextView:TextView
    lateinit var generateButton:TextView


    //private var characterData = CharacterGenerator.generate()
    var characterData: CharacterGenerator.CharacterData? =  null

    // сохранение состояния
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        //savedInstanceState.putSerializable(CHARACTER_DATA_KEY, characterData)
        Log.d(TAG, " -----<<<< onSaveInstanceState     $characterData")
        savedInstanceState.characterData= characterData!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)
        nameTextView = findViewById<TextView>(R.id.nameTextView)
        raceTextView = findViewById<TextView>(R.id.raceTextView)
        dexterityTextView = findViewById<TextView>(R.id.dexterityTextView)
        wisdomTextView = findViewById<TextView>(R.id.wisdomTextView)
        strengthTextView = findViewById<TextView>(R.id.strengthTextView)
        generateButton = findViewById<Button>(R.id.generateButton)
        //Log.d(TAG,"onCreate ---- <<<< characterData ----- >>>>>   ${characterData?:" "}")

//        characterData = savedInstanceState?.let {
//            it.getSerializable(CHARACTER_DATA_KEY)
//                    as CharacterGenerator.CharacterData
//        } ?: CharacterGenerator.generate()

//        characterData = savedInstanceState?.characterData ?:
//                CharacterGenerator.generate()
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, " ---<<<<<< savedInstanceState?.characterData ---- ${savedInstanceState?.characterData}")
            Log.d(TAG, " ---<<<<<< fetchCharacterData().await() ---- ${fetchCharacterData().await()}")
            characterData = savedInstanceState?.characterData ?: fetchCharacterData().await()
            displayCharacterData()
        }


        generateButton.setOnClickListener {
            ////characterData = CharacterGenerator.generate()
            //characterData= fetchCharacterData()
            //displayCharacterData()
            GlobalScope.launch(Dispatchers.Main) {
                Log.d(TAG, " Start launch")
                characterData = fetchCharacterData().await()
                displayCharacterData()
            }

        }
        //displayCharacterData()

    }

    override fun onStart() {
       super.onStart()
       Log.d(TAG,"onStart ---- >>>>>  characterData ->>>>> $characterData")

    }



    private fun displayCharacterData() {
        characterData?.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}