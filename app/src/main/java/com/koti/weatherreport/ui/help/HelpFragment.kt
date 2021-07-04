package com.koti.weatherreport.ui.help

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.koti.weatherreport.R

class HelpFragment : Fragment(R.layout.help_fragment) {

    companion object {
        fun newInstance() = HelpFragment()
    }

    private lateinit var viewModel: HelpViewModel

    val htmlHelpContent="<html>\n" +
            "<head>\n" +
            "<title>Page Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h3>How To Use</h3>\n" +
            "<p>1.Install application</p>\n" +
            "<p>2.Find location icon in home screen to add location</p>\n" +
            "<p>3.After clicking add location button you can see Screen with google maps. Choose required location</p>\n" +
            "<p>4.After slecting required location click on save button to save</p>\n" +
            "<p>5.No you can see new added location home screen </p>\n" +
            "<p>6.If you want see location forecast just tap create location card</p>\n" +
            "<p>7.After clicking on location you will be redirected to screen where you can able to see different weather details</p>\n" +
            "\n" +
            "<h3>Login</h3>\n" +
            "<p>1.It is open to every one. So not required any Login or account to use</p>\n" +
            "\n" +
            "<h3>How To Add New lcoation</h3>\n" +
            "<p>1.Install application</p>\n" +
            "<p>2.Find location icon in home screen to add location</p>\n" +
            "<p>3.After clicking add location button you can see Screen with google maps. Choose required location</p>\n" +
            "<p>4.After slecting required location click on save button to save</p>\n" +
            "<p>5.No you can see new added location home screen </p>\n" +
            "\n" +
            "<h3>How To See Location Forecast</h3>\n" +
            "<p>1.If you want see location forecast just tap create location card from home screen</p>\n" +
            "<p>2.After clicking on location you will be redirected to screen where you can able to see different weather details</p>\n" +
            "\n" +
            "<h3>How To Delete Added Location</h3>\n" +
            "<p>1.If you want to delete location go to home screen. Here you can added location card. At bottom right you can see trash icon. Jsut click on it to delete</p>\n" +
            "\n" +
            "\n" +
            "</body>\n" +
            "</html>"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.help_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<WebView>(R.id.helpContent).apply {
            loadData(htmlHelpContent,"text/html","UTF-8")
        }
    }
}