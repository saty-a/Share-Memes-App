package com.example.sharememes

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var urladdress:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Loadmemes()

        next.setOnClickListener {
            Loadmemes()
        }
        share.setOnClickListener {
            sharememe()
        }

        meme.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                Loadmemes()
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                Loadmemes()
            }
        })

        rl.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                Loadmemes()
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                Loadmemes()
            }
        })


    }

    fun Loadmemes(){
        // Instantiate the RequestQueue.
        meme.visibility=View.GONE
        progressbar.visibility= View.VISIBLE

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string
                meme.visibility=View.VISIBLE
                progressbar.visibility= View.GONE
                urladdress=response.getString("url")
                Glide.with(this).load(urladdress).into(meme)

            },
            Response.ErrorListener { Toast.makeText(this,"That didn't work!",Toast.LENGTH_SHORT).show()})


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun sharememe(){
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this cool meme \n $urladdress")
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(intent)
    }

}