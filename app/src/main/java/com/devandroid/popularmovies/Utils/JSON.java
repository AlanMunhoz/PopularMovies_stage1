package com.devandroid.popularmovies.Utils;

import com.devandroid.popularmovies.Model.Movie;
import com.devandroid.popularmovies.Model.MoviesRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public final class JSON {

    private static final String MESSAGE_CODE = "cod";
    private static final String PAGE = "page";
    private static final String TOTAL_RESULTS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";
    private static final String LIST_RESULTS = "results";
    private static final String VOTE_COUNT = "vote_count";
    private static final String ID = "id";
    private static final String VIDEO = "video";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String ORIG_LANGUAGE = "original_language";
    private static final String ORIG_TITLE = "original_title";
    private static final String LIST_IDS = "genre_ids";
    private static final String BACKFROP_PATH = "backdrop_path";
    private static final String ADULT = "adult";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";

    /**
     * Parses JSON from web response to MovieRequest object
     * [This code is based on Udacity Nanodegree classes]
     *
     * @param JSonString JSON string from server
     * @return MovieRequest object
     * @throws JSONException if JSON data can't be parsed
     */
    public static MoviesRequest getModelFromJSON(String JSonString) throws JSONException {

        JSONObject requestJSon = new JSONObject(JSonString);
        MoviesRequest moviesRequest = new MoviesRequest();

        if (requestJSon.has(MESSAGE_CODE)) {
            int errorCode = requestJSon.getInt(MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    /* Server sent valid data */
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* URL wrong, skip method */
                    return null;
                default:
                    /* Server didn't respond, skip method */
                    return null;
            }
        }

        JSONArray movieResults = requestJSon.getJSONArray(LIST_RESULTS);
        ArrayList<Movie> mMovies;
        mMovies = new ArrayList<>();

        moviesRequest.setmStrPage(requestJSon.getString(PAGE));
        moviesRequest.setmStrTotalResuts(requestJSon.getString(TOTAL_RESULTS));
        moviesRequest.setmStrTotalPages(requestJSon.getString(TOTAL_PAGES));

        for (int i = 0; i < movieResults.length(); i++) {

            JSONObject movieJSon = movieResults.getJSONObject(i);
            Movie movie = new Movie();

            movie.setmStrVoteCount(movieJSon.getString(VOTE_COUNT));
            movie.setmStrId(movieJSon.getString(ID));
            movie.setmStrVideo(movieJSon.getString(VIDEO));
            movie.setmStrVoteAverage(movieJSon.getString(VOTE_AVERAGE));
            movie.setmStrTitle(movieJSon.getString(TITLE));
            movie.setmStrPopularity(movieJSon.getString(POPULARITY));
            movie.setmStrPosterPath(movieJSon.getString(POSTER_PATH));
            movie.setmStrOriginalLanguage(movieJSon.getString(ORIG_LANGUAGE));
            movie.setmStrOriginalTitle(movieJSon.getString(ORIG_TITLE));
            movie.setmStrBackdropPath(movieJSon.getString(BACKFROP_PATH));
            movie.setmStrAdult(movieJSon.getString(ADULT));
            movie.setmStrOverview(movieJSon.getString(OVERVIEW));
            movie.setmStrReleaseDate(movieJSon.getString(RELEASE_DATE));

            mMovies.add(movie);
        }
        moviesRequest.setmMovies(mMovies);

        return moviesRequest;
    }
}
