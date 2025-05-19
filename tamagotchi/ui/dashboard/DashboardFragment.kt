package vcmsa.projects.tamagotchi.ui.dashboard

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import vcmsa.projects.tamagotchi.R
import vcmsa.projects.tamagotchi.databinding.FragmentDashboardBinding
import java.io.File
import java.io.FileOutputStream

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var videoView: VideoView
    private lateinit var eatButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Initialize views
        videoView = view.findViewById(R.id.videoView)
        eatButton = view.findViewById(R.id.eatButton)

        // Set up button click listener
        eatButton.setOnClickListener {
            playVideoFromDrawable()
        }

        return view
    }

    private fun playVideoFromDrawable() {
        // Copy the video from drawable to a temporary file
        val tempVideoFile = createTempVideoFile(requireContext())

        // Set the video URI to the temporary file
        videoView.setVideoURI(Uri.fromFile(tempVideoFile))

        // Start the video
        videoView.start()
    }

    private fun createTempVideoFile(context: Context): File {
        // Get the video resource from drawable
        val videoResourceId = R.raw.mouse_eat
        val videoInputStream = context.resources.openRawResource(videoResourceId)

        // Create a temporary file in the cache directory
        val tempFile = File(context.cacheDir, "temp_video.mp4")

        // Write the video data to the temporary file
        val outputStream = FileOutputStream(tempFile)
        videoInputStream.copyTo(outputStream)
        outputStream.close()
        videoInputStream.close()

        return tempFile
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
    }
}