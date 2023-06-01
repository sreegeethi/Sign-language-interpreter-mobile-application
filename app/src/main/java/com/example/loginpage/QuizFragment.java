package com.example.loginpage;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OptionsLibrary myOptionsLibrary = new OptionsLibrary();

    private Button btnOption1;
    private Button btnOption2;
    private Button btnOption3;
    private Button btnOption4;
    private TextView qnum;

    private String correctAns;
    private int score = 0;
    private int questionNumber = 0;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String videos[] ={"force","fall","ignore","dare, challenge 2","theory"};

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void updateQues(View rootView){

        if(questionNumber==5){
            QuizScore quiz_score_f = new QuizScore();
//            Bundle args = new Bundle();
//            args.putString("Score", String.valueOf(score));
//            System.out.println("put score value as ------------"+score);
//            quiz_score_f.setArguments(args);
            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.fragment_quiz_page);

            layout.removeViews(2, layout.getChildCount() - 2);

            TextView resulttv = layout.findViewById(R.id.resultScore);
            resulttv.setVisibility(View.VISIBLE);
            resulttv.append(String.valueOf(score));

//            layout.removeAllViewsInLayout();


//            FragmentManager fragmentManager = getParentFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.setReorderingAllowed(true);
//            transaction.replace(R.id.fragment_quiz_page, quiz_score_f);
//            transaction.commit();
        }
        else{
            databaseReference = firebaseDatabase.getReference(videos[questionNumber]);
            getVideoUrl(rootView);
            btnOption1.setText(myOptionsLibrary.getOption1(questionNumber));
            btnOption2.setText(myOptionsLibrary.getOption2(questionNumber));
            btnOption3.setText(myOptionsLibrary.getOption3(questionNumber));
            btnOption4.setText(myOptionsLibrary.getOption4(questionNumber));
            correctAns = myOptionsLibrary.getCorrectAns(questionNumber);
            questionNumber++;
            qnum.setText(String.valueOf(questionNumber));
        }
    }

    private void updateScore(int s){
        //update in score view
    }

    private void getVideoUrl(View rootView) {
        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the
                // realtime updates in the data.
                // this method is called when the
                // data is changed in our Firebase console.
                // below line is for getting the data
                // from snapshot of our database.
                String videoUrl = snapshot.getValue(String.class);

//                System.out.println("The url of the video is ------------------------- "+videoUrl);

                try {

                    PlayerView videoView;
                    ExoPlayer player = new SimpleExoPlayer.Builder(getActivity()).build();
                    videoView = (PlayerView) rootView.findViewById(R.id.video_view);
                    Uri videouri = Uri.parse(videoUrl);


                    // we are creating a variable for datasource factory
                    // and setting its user agent as 'exoplayer_view'
                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

                    // we are creating a variable for extractor factory
                    // and setting it to default extractor factory.
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


                    // we are creating a media source with above variables
                    // and passing our event handler as null,

                    MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

                    // inside our exoplayer view we are setting our player
                    videoView.setPlayer(player);

                    // we are preparing our exoplayer
                    // with media source.
                    player.prepare(mediaSource);

                    // we are setting our exoplayer
                    // when it is ready.
                    player.setPlayWhenReady(true);

                } catch (Exception e) {
                    // below line is used for
                    // handling our errors.
                    Log.e("TAG", "Error : " + e.toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(getContext(), "Failed to get video url.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference(videos[0]);

        getVideoUrl(rootView);

        btnOption1 = (Button) rootView.findViewById(R.id.option1);
        btnOption2 = (Button) rootView.findViewById(R.id.option2);
        btnOption3 = (Button) rootView.findViewById(R.id.option3);
        btnOption4 = (Button) rootView.findViewById(R.id.option4);
        qnum = (TextView) rootView.findViewById(R.id.ques_num);

        updateQues(rootView);

        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnOption1.getText()== correctAns){
                    score = score + 1;
//                  updateScore(score);
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Correct",Toast.LENGTH_SHORT).show();
                }
                else{
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnOption2.getText()== correctAns){
                    score = score + 1;
//                  updateScore(score);
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Correct",Toast.LENGTH_SHORT).show();
                }
                else{
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnOption3.getText()== correctAns){
                    score = score + 1;
//                  updateScore(score);
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Correct",Toast.LENGTH_SHORT).show();
                }
                else{
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnOption4.getText()== correctAns){
                    score = score + 1;
//                  updateScore(score);
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Correct",Toast.LENGTH_SHORT).show();
                }
                else{
                    updateQues(rootView);
                    Toast.makeText(getActivity(),"Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}