package com.example.kavyakanaja.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kavyakanaja.data.Poet
import java.util.Locale

@Composable
fun PoetListScreen(
    onPoetClick: (Poet) -> Unit
) {

    val context = LocalContext.current

    var searchText by remember {
        mutableStateOf("")
    }

    val searchHistory = remember {
        mutableStateListOf<String>()
    }

    val poets = listOf(

        Poet("Kuvempu"),
        Poet("D. R. Bendre"),
        Poet("K. S. Narasimhaswamy"),
        Poet("Gopalakrishna Adiga"),
        Poet("Masti Venkatesha Iyengar"),
        Poet("Panje Mangesha Rao"),
        Poet("Shivaram Karanth"),
        Poet("B. M. Srikantaiah"),
        Poet("Nisar Ahmed"),
        Poet("Kanakadasa"),
        Poet("Purandara Dasa"),
        Poet("Harihara"),
        Poet("Raghavanka"),
        Poet("Kumara Vyasa"),
        Poet("Lakshmisha"),
        Poet("Sarvajna"),
        Poet("Allama Prabhu"),
        Poet("Basavanna"),
        Poet("Akka Mahadevi"),
        Poet("Chennaveera Kanavi"),
        Poet("H. S. Venkateshamurthy"),
        Poet("G. S. Shivarudrappa"),
        Poet("Vaidehi"),
        Poet("Siddalingaiah"),
        Poet("P. Lankesh"),
        Poet("Poornachandra Tejaswi"),
        Poet("Mogalli Ganesh"),
        Poet("S. L. Bhyrappa"),
        Poet("U. R. Ananthamurthy"),
        Poet("Shantinath Desai"),
        Poet("K. T. Gatti"),
        Poet("T. P. Kailasam"),
        Poet("A. N. Krishna Rao"),
        Poet("Triveni"),
        Poet("M. Govinda Pai"),
        Poet("V. K. Gokak"),
        Poet("Chaduranga"),
        Poet("Kodagina Gouramma"),
        Poet("K. S. Nissar Ahmed"),
        Poet("P. T. Narasimhachar"),
        Poet("G. P. Rajaratnam"),
        Poet("Indira Bai"),
        Poet("Nadoja Patil Puttappa"),
        Poet("Mysore Ananthaswamy"),
        Poet("M. M. Kalburgi"),
        Poet("S. R. Ekkundi"),
        Poet("Vyasaraya"),
        Poet("Jagannatha Dasa"),
        Poet("Vadiraja Tirtha"),
        Poet("Madhvacharya"),
        Poet("Govinda Pai"),
        Poet("Shadakshari"),
        Poet("Ratnakaravarni"),
        Poet("Andayya"),
        Poet("Janna"),
        Poet("Nagachandra"),
        Poet("Asaga"),
        Poet("Gunavarma"),
        Poet("Pampa"),
        Poet("Ranna"),
        Poet("Ponna"),
        Poet("Muddana"),
        Poet("K. V. Tirumalesh"),
        Poet("B. G. L. Swamy"),
        Poet("K. Marulasiddappa"),
        Poet("Hampa Nagarajaiah"),
        Poet("Baraguru Ramachandrappa"),
        Poet("S. G. Siddaramaiah"),
        Poet("Dodarangegowda"),
        Poet("Champa"),
        Poet("Bharathi Sutha"),
        Poet("Niranjana"),
        Poet("T. N. Srikantaiah"),
        Poet("K. Sadashiva"),
        Poet("D. V. Gundappa"),
        Poet("Aravind Malagatti"),
        Poet("Boluvar Mohammed Kunhi"),
        Poet("H. Gopala Krishna"),
        Poet("M. Veerappa Moily"),
        Poet("H. S. Shivaprakash"),
        Poet("Nagesh Hegde"),
        Poet("Jayant Kaikini"),
        Poet("Mamta Sagar"),
        Poet("Ravi Belagere"),
        Poet("S. Narayan"),
        Poet("S. Diwakar"),
        Poet("Shreekrishna Alanahalli"),
        Poet("Yashwant Chittal"),
        Poet("H. L. Nage Gowda"),
        Poet("M. Chidananda Murthy"),
        Poet("Devanur Mahadeva"),
        Poet("Chandrashekhara Kambara"),
        Poet("Adya Rangacharya"),
        Poet("Kota Shivarama Karanth"),
        Poet("Masti"),
        Poet("DVG"),
        Poet("Da. Ra. Bendre"),
        Poet("K. Shivaram Karanth"),
        Poet("Gopalakrishna Adiga"),
        Poet("Chennabasavanna"),
        Poet("Madara Chennaiah"),
        Poet("Ambigara Chowdaiah"),
        Poet("Hadapada Appanna"),
        Poet("Madiwala Machideva"),
        Poet("Sule Sankavva"),
        Poet("Aydakki Lakkamma"),
        Poet("Akka Nagamma"),
        Poet("Neelambike"),
        Poet("Gangambike"),
        Poet("Kari Basavanna"),
        Poet("Kinnari Bommayya"),
        Poet("Maremma"),
        Poet("Muktayakka"),
        Poet("Rudramuni"),
        Poet("Sarpabhushana Shivayogi")
    )

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val spokenText = result.data
                ?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )

            spokenText?.get(0)?.let {

                searchText = it

                if (!searchHistory.contains(it)) {
                    searchHistory.add(it)
                }
            }
        }
    }

    val filteredPoets = poets.filter {

        it.name.contains(
            searchText,
            ignoreCase = true
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {

            OutlinedTextField(

                value = searchText,

                onValueChange = {
                    searchText = it
                },

                label = {
                    Text("Search Poet")
                },

                trailingIcon = {

                    IconButton(
                        onClick = {

                            val intent = Intent(
                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                            )

                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )

                            intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.getDefault()
                            )

                            speechLauncher.launch(intent)
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voice Search"
                        )
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (searchHistory.isNotEmpty()) {

                Text(
                    text = "Clear Search History",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 8.dp)
                        .clickable {

                            searchHistory.clear()
                            searchText = ""
                        }
                )
            }
        }

        items(filteredPoets) { poet ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .clickable {

                        onPoetClick(poet)
                    }
            ) {

                Text(
                    text = poet.name,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}