package example.com.akhbaar_thenewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Article> articleList=new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.new_recycler_view);
        progressIndicator=findViewById(R.id.prograss_bar);

        setuprecyclerView();
        getNews();
    }


    void setuprecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);

    }


    void changeinprogress(boolean show){
        if(show){
            progressIndicator.setVisibility(View.VISIBLE);

        }
        else{
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }
    void getNews()
    {
        changeinprogress(true);
        NewsApiClient newsApiClient =new NewsApiClient("32fe260070c6408ca9875b60b90ede32");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder().language("en").build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {

                        runOnUiThread(()-> {
                            changeinprogress(false);
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                    }

                    );

    }


}

