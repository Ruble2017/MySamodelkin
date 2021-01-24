package com.tomsk.android.mysamodelkin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_new_character.*
private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY, value)

class NewCharacterActivity : AppCompatActivity() {
    private var characterData = CharacterGenerator.generate()

    // сохранение состояния
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        //savedInstanceState.putSerializable(CHARACTER_DATA_KEY, characterData)
        savedInstanceState.characterData= characterData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)

//        characterData = savedInstanceState?.let {
//            it.getSerializable(CHARACTER_DATA_KEY)
//                    as CharacterGenerator.CharacterData
//        } ?: CharacterGenerator.generate()

        characterData = savedInstanceState?.characterData ?:
                CharacterGenerator.generate()

        generateButton.setOnClickListener {
            characterData = CharacterGenerator.generate()
            displayCharacterData()
        }
        displayCharacterData()

    }


    private fun displayCharacterData() {
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}