package com.example.seatchview

import android.app.Activity
import android.database.MatrixCursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


class MainActivity : AppCompatActivity() {
    private var searchDisp: Disposable? = null
    lateinit var suggestAdapter: CursorAdapter
    private val CURSOR_COLUMN_SUGGEST = "suggest"
    private val from = arrayOf(CURSOR_COLUMN_SUGGEST)
    private val to = arrayOf(R.id.textTv).toIntArray()
    private lateinit var searchSuggestions: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()

    }

    fun setupView() {
        val addressExtraEt = findViewById<EditText>(R.id.addressExtraEt)
        val addressSv = findViewById<SearchView>(R.id.addressSv)
        suggestAdapter = SimpleCursorAdapter(
            applicationContext,
            R.layout.dropdown_search_service,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        addressSv.suggestionsAdapter = suggestAdapter


            // threshold
          //  findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)?.threshold = 1

        addressSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("MyLog", "onQueryTextSubmit $query")
                    //addressExtraEt.requestFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Log.d("MyLog", "onQueryTextChange $newText")
                   /*searchDisp?.dispose()
                    searchDisp = Observable.just(listOf("a", "b" , "c"))
                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe {
//                            updateSuggests(listOf())
//                        }
                        .subscribe(
                            @Suppress("ObjectLiteralToLambda")
                            object : Consumer<List<String>> {
                                override fun accept(suggests: List<String>) {
                                    onSuggestsReceived(suggests)
                                }
                            },
                            @Suppress("ObjectLiteralToLambda")
                            object : Consumer<Throwable> {
                                override fun accept(t: Throwable?) {
                                    // do nothing on error
                                }
                            }
                        )*/
                    onSuggestsReceived(listOf("a", "b" , "c"))
                    return true
                }
            })


        addressSv.setOnSuggestionListener(object : SearchView.OnSuggestionListener {

                override fun onSuggestionSelect(position: Int): Boolean = false

                override fun onSuggestionClick(position: Int): Boolean {
                    Log.d("MyLog", "onSuggestionClick $position")
                    val service = searchSuggestions[position]

                    addressSv.apply {
                        setQuery(service, false)
                       // hideSoftKeyboard(clearFocus = true)
                    }

                    //addressSv.setQuery(service, true)

                    return true
                }
            })

    }



    private fun updateSuggests(suggests: List<String>) {
        searchSuggestions = suggests

        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, CURSOR_COLUMN_SUGGEST), suggests.size)
        for (i in suggests.indices)
            cursor.addRow(arrayOf(i, suggests[i]))

        suggestAdapter.changeCursor(cursor)
    }
    private fun onSuggestsReceived(suggests: List<String>) {
        if (suggests.isNotEmpty())
            updateSuggests(suggests)
    }
    fun View.hideSoftKeyboard(clearFocus: Boolean = true) {
        (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)

        if (clearFocus)
            clearFocus()
    }
}


