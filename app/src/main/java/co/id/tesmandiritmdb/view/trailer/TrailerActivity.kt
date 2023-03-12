package co.id.tesmandiritmdb.view.trailer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.id.tesmandiritmdb.databinding.ActivityTrailerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class TrailerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrailerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.youtubeVideoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(intent.getStringExtra("key")!!, 0f)
                youTubePlayer.pause()
            }
        })
        binding.back.setOnClickListener { finish() }
        binding.dateRelease.setText(intent.getStringExtra("date"))
        binding.title.setText(intent.getStringExtra("title"))
        binding.titleVideo.setText(intent.getStringExtra("title"))

    }
}