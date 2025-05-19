package vcmsa.projects.tamagotchi.ui.notifications

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import androidx.fragment.app.Fragment
import vcmsa.projects.tamagotchi.R
import vcmsa.projects.tamagotchi.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var videoView: VideoView
    private lateinit var washButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        videoView = binding.videoView
        washButton = binding.washButton

        // Set up the video URI
        val videoUri = Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.wash)
        videoView.setVideoURI(videoUri)

        // Set up button click listener
        washButton.setOnClickListener {
            toggleVideo()
        }
    }

    private fun toggleVideo() {
        if (videoView.isPlaying) {
            // If the video is playing, pause it and hide the VideoView
            videoView.pause()
            videoView.visibility = View.GONE
        } else {
            // If the video is not playing, show the VideoView and start playback
            videoView.visibility = View.VISIBLE
            videoView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        // Pause the video when the fragment is paused
        videoView.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Stop the video when the fragment is destroyed
        videoView.stopPlayback()
        _binding = null
    }
}