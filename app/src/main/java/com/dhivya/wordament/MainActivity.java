package com.dhivya.wordament;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
   String alphabets[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    Button start,submit,shuffle,end;
    Button b[]=new Button[9];
    TextView timer,score,display;
    //ListView wordListView;
    Random random =new Random();
    String tag="tag";
    String concise="";
    String scoreDisplay;
    String done="Time up!";
    String notfound="Not  word!";
    int shuffleCount=0;
    int scores=0;
    EditText listOfWords;
    InputStream inputStream;
    HashActivity hashActivity;
    //ArrayAdapter<String> adapter;

    //String wordList[]={"whatever","hey","bye"};
    int wordCount=0;
    String tick;
    String word="";
    CountDownTimer cTimer = null;
    ArrayList<String> wordList=new ArrayList<>(50);
    //TreeSet<String> wordSet=new TreeSet<>();
    //ArrayList<String> wordSet=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //adapter = new ArrayAdapter<String>(this, R.layout.activity_listview,wordList);

        AssetManager assetManager = getAssets();
        try {
            inputStream = assetManager.open("words.txt");
            hashActivity=new HashActivity(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        //Log.i(tag,String.valueOf(wordSet.size()));
        timer=(TextView)findViewById(R.id.timer);
        score=(TextView)findViewById(R.id.score);
        start=(Button)findViewById(R.id.start);
        b[0]=(Button)findViewById(R.id.b1);
        b[1]=(Button)findViewById(R.id.b2);
        b[2]=(Button)findViewById(R.id.b3);
        b[3]=(Button)findViewById(R.id.b4);
        b[4]=(Button)findViewById(R.id.b5);
        b[5]=(Button)findViewById(R.id.b6);
        b[6]=(Button)findViewById(R.id.b7);
        b[7]=(Button)findViewById(R.id.b8);
        b[8]=(Button)findViewById(R.id.b9);
        submit=(Button)findViewById(R.id.submit);
        display=(TextView)findViewById(R.id.display);
        shuffle=(Button)findViewById(R.id.shuffle);
        end=(Button)findViewById(R.id.end);
        listOfWords=(EditText)findViewById(R.id.listOfWords);

        /*wordList.add("Hey\n");
        wordList.add("you\n");
        */


        //wordListView=(ListView)findViewById(R.id.wordListView);
        //wordList[0]="random";
        //wordList[1]="yes";
        //adapter = new ArrayAdapter<String>(this, R.layout.activity_listview,wordList);
        /*try{
            wordListView.setAdapter(adapter);
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }*/



        Log.i(tag, "Before shuffle");
        shuffleButtons();
        Log.i(tag, "After shuffle");
        for(int i=0;i<9;i++)
            b[i].setEnabled(false);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Inside start button before timer");
                for(int i=0;i<9;i++)
                    b[i].setEnabled(true);
                scores=0;
                scoreDisplay="Your Score: ";
                scoreDisplay+=String.valueOf(scores);
                score.setText(scoreDisplay);
                startTimer();
                Log.i(tag, "Inside start After timer");
                start.setEnabled(false);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag,"inside submit");
                //cancelTimer();
                display.setText("");
                //wordList[wordCount++]=word;
                /*Iterator<String> itr = wordSet.iterator();
                for(int j=0;j<10;j++)
                    if(itr.hasNext())
                    Log.i(tag,itr.next());
                    //Log.i(tag, itr.next());*/
                    if ((hashActivity.isWord(word))&&(!wordList.contains(word)))
                    {
                        String test="Found! -";
                        test+=word;
                        display.setText(test);
                        Log.i(tag,"wordset contains word");
                        scores += word.length();
                        scoreDisplay = "Your Score: ";
                        scoreDisplay += String.valueOf(scores);
                        score.setText(scoreDisplay);
                        Log.i(tag, scoreDisplay);

                        wordList.add(word);
                        for (int i = 0; i < wordList.size(); i++) {
                            concise += wordList.get(i);
                            concise += "\n";

                        }
                        listOfWords.setText(concise);

                    } else
                    if(wordList.contains(word))
                    {
                        display.setText("Word already entered!");
                    }
                     else
                    {
                        Log.i(tag, "word not found");
                        display.setText(notfound);
                    }

                word="";
                concise="";
                for(int i=0;i<9;i++)
                    b[i].setEnabled(true);
                //setListView();
            }
        });

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleButtons();
                shuffleCount++;
                if(shuffleCount>3)
                    shuffle.setEnabled(false);

            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                submit.setEnabled(false);
                shuffle.setEnabled(false);
                for(int i=0;i<9;i++)
                    b[i].setEnabled(false);
                start.setEnabled(true);
                wordList.clear();
                listOfWords.setText("");
                display.setText("");

                //score.setTextSize(40);

            }
        });



        b[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[0].getText().toString();
                b[0].setEnabled(false);
                display.setText(word);

            }
        });

        b[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[1].getText().toString();
                b[1].setEnabled(false);
                display.setText(word);
            }
        });
        b[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[2].getText().toString();
                b[2].setEnabled(false);
                display.setText(word);
            }
        });

        b[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[3].getText().toString();
                b[3].setEnabled(false);
                display.setText(word);
            }
        });

        b[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[4].getText().toString();
                b[4].setEnabled(false);
                display.setText(word);
            }
        });

        b[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[5].getText().toString();
                b[5].setEnabled(false);
                display.setText(word);
            }
        });

        b[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[6].getText().toString();
                b[6].setEnabled(false);
                display.setText(word);
            }
        });

        b[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[7].getText().toString();
                b[7].setEnabled(false);
                display.setText(word);
            }
        });

        b[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word += b[8].getText().toString();
                b[8].setEnabled(false);
                display.setText(word);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void shuffleButtons()
    {
        HashSet<Integer> hs=new HashSet<>();
        int index;
        b[0]=(Button)findViewById(R.id.b1);
        b[1]=(Button)findViewById(R.id.b2);
        b[2]=(Button)findViewById(R.id.b3);
        b[3]=(Button)findViewById(R.id.b4);
        b[4]=(Button)findViewById(R.id.b5);
        b[5]=(Button)findViewById(R.id.b6);
        b[6]=(Button)findViewById(R.id.b7);
        b[7]=(Button)findViewById(R.id.b8);
        b[8]=(Button)findViewById(R.id.b9);
        for(int i=0;i<9;i++){
            do {
                index = random.nextInt(26);
            }while(hs.contains(index));
            hs.add(index);
            b[i].setText(alphabets[index]);
        }
    }

    public void startTimer()
    {
        Log.i(tag,"Inside start timer");
        cTimer = new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Log.i(tag,"Tick");
                tick="Seconds remaining: ";
                tick+=String.valueOf(millisUntilFinished/1000);
                timer.setText(tick);
            }

            public void onFinish() {
                Log.i(tag,"Finish");
                timer.setText(done);
        }
        };
        Log.i(tag,"Inside start timer before start()");
        cTimer.start();
        Log.i(tag, "Inside start timer after start()");
    }

    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
        timer.setText(done);
    }

   /* void setListView(){
        adapter = new ArrayAdapter<String>(this, R.layout.activity_main,wordList);
        listView.setAdapter(adapter);*/

   // }
}
