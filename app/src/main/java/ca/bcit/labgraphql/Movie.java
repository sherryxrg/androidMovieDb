package ca.bcit.labgraphql;

public class Movie {

        String movieTitle;
        String movieLink;
        String movieDescription;

        public Movie() {

        }

        public Movie(String movieTitle, String movieLink, String movieDescription) {
            this.movieTitle = movieTitle;
            this.movieLink = movieLink;
            this.movieDescription = movieDescription;
        }

        public String getMovieTitle() {
            return movieTitle;
        }

        public String getMovieLink() {
            return movieLink;
        }

        public String getMovieDescription() {
        return movieDescription;
    }
}
